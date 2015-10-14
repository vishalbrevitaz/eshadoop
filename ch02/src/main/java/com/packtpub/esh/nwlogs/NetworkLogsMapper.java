package com.packtpub.esh.nwlogs;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class NetworkLogsMapper extends Mapper<Object, Text, Text, MapWritable> {


    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        MapWritable map = new MapWritable();
        String line = value.toString().trim();
        String[] parts = line.split("\" \\(");
        String keyVals = parts[0].substring(15, parts[0].length()).trim();

        String srcIp = null;
        String destIp = null;
        String category = null;
        String action = null;
        String target = null;
        String serial = null;
        String ip = null;
        String timezone = null;
        Long time = null;

        int i = 0;
        StringTokenizer part1tokenizer = new StringTokenizer(keyVals);
        while (part1tokenizer.hasMoreTokens()) {
            String token = part1tokenizer.nextToken();
            String keyPart = getKeyValue(token)[0];
            String valuePart = getKeyValue(token)[1];

            switch (keyPart) {
                case "src":
                    srcIp = valuePart;
                    break;
                case "dst":
                    destIp = valuePart;
                    break;
                case "id":
                    category = valuePart;
                    break;
                case "act":
                    action = valuePart != null ? valuePart.toUpperCase() : null;
                    break;
                case "msg":
                    target = valuePart;
                    break;
            }
            i++;
        }

        i = 0;
        if (parts.length > 1) {
            StringTokenizer part2Tokenizer = new StringTokenizer(parts[1], ",");
            while (part2Tokenizer.hasMoreTokens()) {
                String token = part2Tokenizer.nextToken();
                String keyPart = getKeyValue(token)[0].trim();
                String valuePart = getKeyValue(token)[1];

                switch (keyPart) {
                    case "sn":
                        serial = valuePart;
                        break;
                    case "ip":
                        ip = valuePart;
                        break;
                    case "tz":
                        timezone = valuePart;
                        break;
                    case "time":
                        String timeStr = valuePart;
                        timeStr = timeStr.replaceAll("\\)", "");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy");
                        try {
                            time = dateFormat.parse(timeStr).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                i++;
            }
        }
        map.put(new Text("srcIp"), getWritableValue(srcIp));
        map.put(new Text("destIp"), getWritableValue(destIp));
        map.put(new Text("action"), getWritableValue(action));
        map.put(new Text("category"), getWritableValue(category));
        map.put(new Text("target"), getWritableValue(target));
        map.put(new Text("serial"), getWritableValue(serial));
        map.put(new Text("timezone"), getWritableValue(timezone));
        map.put(new Text("ip"), getWritableValue(ip));
        map.put(new Text("domain"),getWritableValue(getDomainName(target)));
        map.put(new Text("@timestamp"), time != null ? new LongWritable(time) : new LongWritable(new Date().getTime()));


        context.write(value, map);
    }

    private static WritableComparable getWritableValue(String value) {
        return value != null ? new Text(value) : NullWritable.get();
    }

    public static String getDomainName(String url) {
        if(url==null)
            return null;
        return DomainUtil.getBaseDomain(url);
    }

    private static String[] getKeyValue(String token) {
        String[] values = token.split("=");
        String val = null;
        if (values.length >= 2) {
            val = values[1].trim();
            val = val.replaceAll("\"", "");
        }

        return new String[]{values[0],val};
    }

}
