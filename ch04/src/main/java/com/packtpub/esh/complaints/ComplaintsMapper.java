package com.packtpub.esh.complaints;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class ComplaintsMapper extends Mapper<Object, Text, Text, MapWritable> {

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        MapWritable map = new MapWritable();
        String line = value.toString().trim();
        CSVParser parser = CSVParser.parse(line, CSVFormat.RFC4180);
        for (CSVRecord csvRecord : parser) {
            String zip  = csvRecord.get(0);
            Long complaintId = Long.parseLong(csvRecord.get(1));
            String product = csvRecord.get(2);
            String subproduct = csvRecord.get(3);
            String issue = csvRecord.get(4);
            String subissue = csvRecord.get(5);
            String state = csvRecord.get(6);
            String submittedVia = csvRecord.get(7);
            String dateReceived = csvRecord.get(8);
            String dateSent = csvRecord.get(9);
            String company = csvRecord.get(10);
            String companyResponse = csvRecord.get(11);
            String timelyResponse = csvRecord.get(12);
            String consumerDisputed = csvRecord.get(13);
            String latitude = csvRecord.get(14);
            String longitude = csvRecord.get(15);

            map.put(new Text("zip"), getWritableValue(zip));
            map.put(new Text("complaintId"), getWritableLongValue(complaintId));
            map.put(new Text("product"), getWritableValue(product));
            map.put(new Text("subproduct"), getWritableValue(subproduct));
            map.put(new Text("issue"), getWritableValue(issue));
            map.put(new Text("issue.raw"), getWritableValue(issue));
            map.put(new Text("subissue"), getWritableValue(subissue));
            map.put(new Text("state"), getWritableValue(state));
            map.put(new Text("submittedVia"), getWritableValue(submittedVia));
            map.put(new Text("dateReceived"), getWritableValue(dateReceived));
            map.put(new Text("dateSent"), getWritableValue(dateSent));
            map.put(new Text("company"), getWritableValue(company));
            map.put(new Text("companyResponse"), getWritableValue(companyResponse));
            map.put(new Text("timelyResponse"), getWritableBooleanValue(timelyResponse));
            map.put(new Text("consumerDisputed"), getWritableBooleanValue(consumerDisputed));
            if("na".equalsIgnoreCase(latitude) || "na".equalsIgnoreCase(longitude)){
                map.put(new Text("location"), NullWritable.get());
            }else{
                map.put(new Text("location"), getWritableValue(latitude+", "+longitude));
            }

        }

        context.write(value, map);
    }

    private static WritableComparable getWritableValue(String value) {
        return value != null ? new Text(value) : NullWritable.get();
    }

    private Writable getWritableLongValue(Long value) {
        return value!=null ? new LongWritable(value) : NullWritable.get();
    }

    private Writable getWritableBooleanValue(String value) {
        if(StringUtils.isEmpty(value) || "na".equalsIgnoreCase(value)){
            return NullWritable.get();
        }
        return "yes".equalsIgnoreCase(value) ? new BooleanWritable(true) : new BooleanWritable(false);
    }

}
