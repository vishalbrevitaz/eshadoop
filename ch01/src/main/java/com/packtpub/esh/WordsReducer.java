package com.packtpub.esh;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordsReducer extends Reducer<Text,IntWritable,Text,MapWritable> {

	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		// This represents our ES document
        MapWritable result = new MapWritable();
		int sum = 0;
		for (IntWritable val : values) {
			sum += val.get();
		}
        // Add "word" field to ES document
		result.put(new Text("word"), key);
        // Add "count" field to ES document
		result.put(new Text("count"), new IntWritable(sum));
		context.write(key, result);
	}
	
}
