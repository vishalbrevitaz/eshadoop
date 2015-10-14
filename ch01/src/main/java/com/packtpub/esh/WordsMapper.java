package com.packtpub.esh;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class WordsMapper extends Mapper<Object, Text, Text, IntWritable> {

	private final static IntWritable one = new IntWritable(1);
	
	public void map(Object key, Text value, Context context)	throws IOException, InterruptedException {
		StringTokenizer itr = new StringTokenizer(value.toString());

		while (itr.hasMoreTokens()) {
            Text word = new Text();
			word.set(itr.nextToken());
			context.write(word, one);
		}
	}
}
