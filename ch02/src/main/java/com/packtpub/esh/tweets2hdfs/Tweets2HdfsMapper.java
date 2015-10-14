package com.packtpub.esh.tweets2hdfs;

import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tweets2HdfsMapper extends Mapper<Object, MapWritable, Text, Text> {


    public void map(Object key, MapWritable value, Context context) throws IOException, InterruptedException {
        StringBuilder mappedValueBuilder = new StringBuilder();
        mappedValueBuilder.append(getQuotedValue(value.get(new Text("tweetId")))+", ");
        mappedValueBuilder.append(getQuotedValue(value.get(new Text("text")))+", ");
        mappedValueBuilder.append(getQuotedValue(value.get(new Text("user")))+", ");
        mappedValueBuilder.append(getQuotedTimeValue(value.get(new Text("@timestamp"))));

        Text mappedValue = new Text(mappedValueBuilder.toString());
        context.write(mappedValue, mappedValue);
    }

    private String getQuotedTimeValue(Writable writable) {
        Date timestamp = new Date(Long.parseLong(writable.toString()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz YYYY");
        return "\""+dateFormat.format(timestamp)+"\"";
    }

    private String getQuotedValue(Writable value) {
        return "\""+value.toString()+"\"";
    }

}
