package com.packtpub.esh.tweets2es;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tweets2EsMapper extends Mapper<Object, Text, Text, MapWritable> {


    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        MapWritable map = new MapWritable();
        getMapWritable(value, map);
        context.write(value, map);
    }

    private void getMapWritable(Text value, MapWritable map) {
        String line = value.toString().trim();
        String[] tokens = line.split("\",");
        String id = null;
        String text = null;
        String user = null;
        Long timestamp = null;

        for(int i=0;i<tokens.length;i++) {
            String token = tokens[i];
            switch (i) {
                case 0:
                    id = token.replaceAll("\"", "");
                    break;
                case 1:
                    text = token.replaceAll("\"", "");
                    break;
                case 2:
                    String timeStr = token.replaceAll("\"", "");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz YYYY");
                    try {
                        timestamp = dateFormat.parse(timeStr).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    break;
                case 3:
                    user = token.replaceAll("\"", "");
                    break;
                case 4:
                    break;
            }

        }
        map.put(new Text("tweetId"), new Text(id));
        map.put(new Text("text"), new Text(text));
        map.put(new Text("user"), new Text(user));
        map.put(new Text("@timestamp"), new LongWritable(timestamp));
    }

}
