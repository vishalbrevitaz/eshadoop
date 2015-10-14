
REGISTER hdfs://localhost:9000/lib/elasticsearch-hadoop-2.1.1.jar;

-- Match the reducer parallelism to the number of shards available
SET default_parallel 5;

-- Disable combining input splits
SET pig.noSplitCombination TRUE;

-- Load CSV file into SOURCE
SOURCE = load '/ch07/crimes_dataset.csv' using PigStorage(',') as (id:chararray, caseNumber:chararray,
    date:datetime, block:chararray, iucr:chararray, primaryType:chararray, description:chararray,
    location:chararray, arrest:boolean, domestic:boolean, lat:double,lon:double);

TARGET = foreach SOURCE generate id, caseNumber,
    date, block, iucr, primaryType, description,
    location, arrest, domestic, TOTUPLE(lon, lat) AS geoLocation;

-- Store to ES index
STORE TARGET INTO 'esh_pig/crimes'
    USING org.elasticsearch.hadoop.pig.EsStorage('es.http.timeout = 5m',
        'es.index.auto.create = true',
        'es.mapping.names=arrest:isArrest, domestic:isDomestic',
        'es.mapping.id=id');

