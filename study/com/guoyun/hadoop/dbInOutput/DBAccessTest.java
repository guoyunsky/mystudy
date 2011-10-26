/*package com.guoyun.hadoop.dbInOutput;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider.Text;

public class DBAccessTest {

	*//**
	 * @param args
	 *//*
	public static void main(String[] args) {
		Job job=null;
		Configuration con=null;
		
		con=new Configuration();
		job=new Job(con);
		job.setMapperClass(DBAccessMapper.class);
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(theClass);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);
		job.setInputFormat(DBInputFormat.class);
	
		FileOutputFormat.setOutputPath(job, new Path("test"));
		
		
	}

}
*/