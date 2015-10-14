package com.packtpub.esh;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.elasticsearch.hadoop.mr.EsOutputFormat;


public class Driver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
        // ElasticSearch Server nodes to point to
		conf.set("es.nodes", "localhost:9200");
        // ElasticSearch index and type name in {indexName}/{typeName} format
		conf.set("es.resource", "eshadoop/wordcount");

        // Create Job instance
		Job job = new Job(conf, "word count");
        // set Driver class
		job.setJarByClass(Driver.class);
        job.setMapperClass(WordsMapper.class);
        job.setReducerClass(WordsReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        // set OutputFormat to EsOutputFormat provided by ElasticSearch-Hadoop jar
	    job.setOutputFormatClass(EsOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
