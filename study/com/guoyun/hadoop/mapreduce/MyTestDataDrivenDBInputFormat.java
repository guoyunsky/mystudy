package com.guoyun.hadoop.mapreduce;

import java.sql.*;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.HadoopTestCase;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.db.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.StringUtils;

/**
 * Test aspects of DataDrivenDBInputFormat.
 */
public class MyTestDataDrivenDBInputFormat extends HadoopTestCase {

  private static final Log LOG = LogFactory.getLog(
      TestDataDrivenDBInputFormat.class);

  private static final String DB_NAME = "sqoop_datas";
  private static final String DB_URL =
    "jdbc:mysql://127.0.0.1:3306/" + DB_NAME;
  private static final String USER_NAME="root";
  private static final String USER_PWD="123456";
  private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";

  private Connection connection;

  private static final String OUT_DIR;

  public MyTestDataDrivenDBInputFormat() throws IOException {
    super(LOCAL_MR, LOCAL_FS, 1, 1);
  }

  static {
    OUT_DIR = System.getProperty("test.build.data", "/tmp") + "/dddbifout";
  }

  private void createConnection(String driverClassName,
      String url,String userName,String userPwd) throws Exception {

    Class.forName(driverClassName);
    connection = DriverManager.getConnection(url,userName,userPwd);
    connection.setAutoCommit(false);
  }

  private void shutdown() {
    try {
      connection.commit();
      connection.close();
      connection = null;
    } catch (Throwable ex) {
      LOG.warn("Exception occurred while closing connection :"
          + StringUtils.stringifyException(ex));
    }
  }

  private void initialize(String driverClassName, String url)
      throws Exception {
    createConnection(driverClassName, url,USER_NAME,USER_PWD);
  }

  public void setUp() throws Exception {
    try {
		initialize(DRIVER_CLASS, DB_URL);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    super.setUp();
  }

  public void tearDown() throws Exception {
    super.tearDown();
    shutdown();
  }



  /**
   * DBWritable class for a table that holds a single SQL date value.
   */
  public static class DateCol implements DBWritable, WritableComparable {
    private Date date;

    public Date getDate() {
      return date;
    }

    public void setDate(Date dt) {
      this.date = dt;
    }

    public String toString() {
      return date.toString();
    }

    public void readFields(ResultSet rs) throws SQLException {
      date = rs.getDate(1);
    }

    public void write(PreparedStatement ps) {
      // not needed.
    }

    public void readFields(DataInput in) throws IOException {
      long v = in.readLong();
      date = new Date(v);
    }

    public void write(DataOutput out) throws IOException {
      out.writeLong(date.getTime());
    }

    @Override
    /** {@inheritDoc} */
    public int hashCode() {
      return (int) date.getTime();
    }

    @Override
    /** {@inheritDoc} */
    public int compareTo(Object o) {
      if (o instanceof DateCol) {
        Long v = Long.valueOf(date.getTime());
        Long other = Long.valueOf(((DateCol) o).date.getTime());
        return v.compareTo(other);
      } else {
        return -1;
      }
    }

    @Override
    /** {@inheritDoc} */
    public boolean equals(Object o) {
      return (o instanceof DateCol) && compareTo(o) == 0;
    }
  }

  /**
   * Mapper that emits its input value as its output key.
   */
  public static class ValMapper
      extends Mapper<Object, Object, Object, NullWritable> {
    public void map(Object k, Object v, Context c)
        throws IOException, InterruptedException {
      c.write(v, NullWritable.get());
    }
  }
  
  public static class ValReducer extends Reducer{

	@Override
	protected void reduce(Object key, Iterable values,
			org.apache.hadoop.mapreduce.Reducer.Context context)
			throws IOException, InterruptedException {
		int count=0;
		for(Object val:values){
			count++;
		}
		context.write(key, new LongWritable(count));
		System.out.println(key+"->"+count);
	}
	  
  }
  

  public void testDateSplits() throws Exception {
    Statement s = connection.createStatement();
    final String DATE_TABLE = "datetable";
    final String COL = "foo";
    try {
      try {
        // delete the table if it already exists.
        s.executeUpdate("DROP TABLE IF EXISTS " + DATE_TABLE);
      } catch (SQLException e) {
        // Ignored; proceed regardless of whether we deleted the table;
        // it may have simply not existed.
      }

      // Create the table.
      s.executeUpdate("CREATE TABLE " + DATE_TABLE + "(" + COL + " TIMESTAMP)");
      s.executeUpdate("INSERT INTO " + DATE_TABLE + " VALUES('2010-04-01')");
      s.executeUpdate("INSERT INTO " + DATE_TABLE + " VALUES('2010-04-02')");
      s.executeUpdate("INSERT INTO " + DATE_TABLE + " VALUES('2010-05-01')");
      s.executeUpdate("INSERT INTO " + DATE_TABLE + " VALUES('2011-04-01')");
      s.executeUpdate("INSERT INTO " + DATE_TABLE + " VALUES('2011-04-01')");
      s.executeUpdate("INSERT INTO " + DATE_TABLE + " VALUES('2011-04-01')");
      s.executeUpdate("INSERT INTO " + DATE_TABLE + " VALUES('2011-04-01')");

      // commit this tx.
      connection.commit();

      Configuration conf = new Configuration();
      conf.set("fs.defaultFS", "file:///");
      FileSystem fs = FileSystem.getLocal(conf);
      fs.delete(new Path(OUT_DIR), true);

      // now do a dd import
      Job job = new Job(conf);
      job.setMapperClass(ValMapper.class);
      job.setReducerClass(ValReducer.class);
      
      job.setMapOutputKeyClass(DateCol.class);
      job.setMapOutputValueClass(NullWritable.class);
      
      job.setOutputKeyClass(DateCol.class);
      job.setOutputValueClass(LongWritable.class);
      
      job.setNumReduceTasks(1);
      job.getConfiguration().setInt("mapreduce.map.tasks", 2);
      FileOutputFormat.setOutputPath(job, new Path(OUT_DIR));
      DBConfiguration.configureDB(job.getConfiguration(), DRIVER_CLASS,
          DB_URL, USER_NAME, USER_PWD);
      DataDrivenDBInputFormat.setInput(job, DateCol.class, DATE_TABLE, null,
          COL, COL);

      boolean ret = job.waitForCompletion(true);
      System.out.println("job is "+ret);

      // Check to see that we imported as much as we thought we did.
      System.out.println("records number:"+
          job.getCounters().findCounter("org.apache.hadoop.mapred.Task$Counter",
          "REDUCE_OUTPUT_RECORDS").getValue());
    } finally {
      s.close();
    }
  }
}