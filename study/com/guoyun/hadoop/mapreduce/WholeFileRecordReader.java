package com.guoyun.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class WholeFileRecordReader extends RecordReader<NullWritable, BytesWritable> {
	private FileSplit fileSplit;
	private Configuration configuration;
	private boolean processed;
	
	public WholeFileRecordReader(FileSplit fileSplit,
			Configuration configuration) {
		super();
		this.fileSplit = fileSplit;
		this.configuration = configuration;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NullWritable getCurrentKey() throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BytesWritable getCurrentValue() throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void initialize(InputSplit arg0, TaskAttemptContext arg1)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}
	
	


	/*@Override
	public long getPos() throws IOException {
		return 0;
	}

	@Override
	public float getProgress() throws IOException {
		return processed?fileSplit.getLength():0;
	}

	@Override
	public boolean next(NullWritable key, BytesWritable value)
			throws IOException {
		if(!processed){
			byte[] content=new byte[(int)fileSplit.getLength()];
			FileSystem fs=FileSystem.get(configuration);
			Path file=fileSplit.getPath();
			FSDataInputStream in=null;
			
			try {
				in=fs.open(file);
				IOUtils.readFully(in, content, 0, content.length);
				value.set(content, 0, content.length);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(in!=null){
					in.close();
				}
			}
			processed=true;
			return true;
		}
		return false;
	}*/
	
	
}
