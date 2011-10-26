package com.guoyun.hadoop.mapreduce;

import java.io.IOException;  
import java.util.Random;  
import java.util.StringTokenizer;  
  
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.fs.FileSystem;  
import org.apache.hadoop.fs.Path;  
import org.apache.hadoop.io.IntWritable;  
import org.apache.hadoop.io.Text;  
import org.apache.hadoop.io.WritableComparator;  
import org.apache.hadoop.mapreduce.Job;  
import org.apache.hadoop.mapreduce.Mapper;  
import org.apache.hadoop.mapreduce.Reducer;  
import org.apache.hadoop.mapreduce.Reducer.Context;  
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;  
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;  
import org.apache.hadoop.util.GenericOptionsParser;  
  
public class AdvancedWordCount {  
  
    public static class TokenizerMapper extends  
        Mapper<Object, Text, Text, IntWritable> {  
          
        private final static IntWritable one = new IntWritable(1);  
        private Text word = new Text();  
        private String pattern = "[^\\w]";  
          
        @Override  
        protected void map(Object key, Text value, Context context)  
                throws IOException, InterruptedException {  
            String line = value.toString();  
            System.out.println("-------line todo: " + line);  
            line = line.replaceAll(pattern, " ");  
            System.out.println("-------line done: " + line);  
              
            StringTokenizer itr = new StringTokenizer(line.toString());  
              
            while (itr.hasMoreTokens()) {  
                word.set(itr.nextToken());  
                context.write(word, one);  
            }  
        }     
    }  
      
    public static class IntSumReducer extends  
        Reducer<Text, IntWritable, Text, IntWritable> {  
  
        private IntWritable result = new IntWritable();  
        @Override  
        protected void reduce(Text key, Iterable<IntWritable> values,  
                Context context) throws IOException, InterruptedException {  
            // TODO Auto-generated method stub  
            int sum = 0;  
            for (IntWritable val : values) {  
                sum += val.get();  
            }  
            result.set(sum);  
            context.write(key, result);  
        }     
  
          
          
    }  
      
    public static class MyInverseMapper extends  
        Mapper<Object, Text, IntWritable, Text> {  
  
        @Override  
        protected void map(Object key, Text value, Context context)  
                throws IOException, InterruptedException {  
            String[] keyAndValue = value.toString().split("\t");  
            System.out.println("---------------->" + value);  
            System.out.println("--------0------->" + keyAndValue[0]);  
            System.out.println("--------1------->" + keyAndValue[1]);  
              
            context.write(new IntWritable(Integer.parseInt(keyAndValue[1])), new Text(keyAndValue[0]));  
        }  
          
          
    }  
      
    public static class IntWritableDecreasingComparator extends  
        IntWritable.Comparator {  
  
        @Override  
        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {  
            // TODO Auto-generated method stub  
            return -super.compare(b1, s1, l1, b2, s2, l2);  
        }  
          
        public int compare(WritableComparator a, WritableComparator b) {  
            // TODO Auto-generated method stub  
            return -super.compare(a, b);  
        }  
    }  
      
      
    public static boolean countingJob(Configuration conf, Path in, Path out) throws IOException, InterruptedException, ClassNotFoundException {  
        Job job = new Job(conf, "wordcount");  
        job.setJarByClass(AdvancedWordCount.class);  
        job.setMapperClass(TokenizerMapper.class);  
        job.setCombinerClass(IntSumReducer.class);  
        job.setReducerClass(IntSumReducer.class);  
        job.setOutputKeyClass(Text.class);  
        job.setOutputValueClass(IntWritable.class);  
        FileInputFormat.addInputPath(job, in);  
        FileOutputFormat.setOutputPath(job, out);  
          
        return job.waitForCompletion(true);  
    }  
      
      
    public static boolean sortingJob(Configuration conf, Path in, Path out) throws IOException, InterruptedException, ClassNotFoundException {  
        Job job = new Job(conf, "sort");  
        job.setJarByClass(AdvancedWordCount.class);  
        job.setMapperClass(MyInverseMapper.class);  
          
        job.setOutputKeyClass(IntWritable.class);  
        job.setOutputValueClass(Text.class);  
          
        job.setSortComparatorClass(IntWritableDecreasingComparator.class);  
          
        FileInputFormat.addInputPath(job, in);  
        FileOutputFormat.setOutputPath(job, out);  
          
        return job.waitForCompletion(true);  
    }  
      
    public static void main(String[] args) throws IOException {  
        Configuration conf = new Configuration();  
        conf.set("fs.defaultFS", "file:///");
        FileSystem fs = FileSystem.getLocal(conf);
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();  
          
        Path temp = new Path("/tmp/wordcount-temp-" + Integer.toString(new Random().nextInt(Integer.MAX_VALUE)));  
        boolean a = false, b = false;  
          
        Path in = new Path(otherArgs[0]);  
        Path out = new Path(otherArgs[1]);  
        fs.delete(out, true);
          
        if(otherArgs.length != 2)  
            System.exit(2);  
          
        try {  
            a = AdvancedWordCount.countingJob(conf, in, temp);  
            b = AdvancedWordCount.sortingJob(conf, temp, out);  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (InterruptedException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } finally {  
            try {  
                fs.delete(temp, true);  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
            if (!a || !b)  
                try {  
                    fs.delete(out, true);  
                } catch (IOException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }     
        }  
    }  
}  
