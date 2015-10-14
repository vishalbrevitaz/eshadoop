DROP TABLE IF EXISTS source;
DROP TABLE IF EXISTS crimes;

CREATE EXTERNAL TABLE source (
    id      STRING,
    caseNumber STRING,
    eventDate DATE,
    block STRING,
    iucr STRING,
    primaryType STRING,
    description STRING,
    location STRING,
    arrest BOOLEAN,
    domestic BOOLEAN,
    lat DOUBLE,
    lon DOUBLE)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
STORED AS TEXTFILE
LOCATION '/ch07';

CREATE EXTERNAL TABLE crimes (
        id STRING,
        caseNumber STRING,
        eventDate DATE,
        block STRING,
        iucr STRING,
        primaryType STRING,
        description STRING,
        location STRING,
        arrest BOOLEAN,
        domestic BOOLEAN,
        geoLocation   STRUCT<lat:DOUBLE, lon:DOUBLE>)
STORED BY 'org.elasticsearch.hadoop.hive.EsStorageHandler'
TBLPROPERTIES('es.resource' = 'esh_hive/crimes');

-- insert data to Elasticsearch from another table called 'source'
INSERT OVERWRITE TABLE crimes
    SELECT s.id, s.caseNumber, s.eventDate, s.block, s.iucr, s.primaryType, s.description, s.location, s.arrest, s.domestic, named_struct('lat', cast(s.lat AS DOUBLE), 'lon', cast(s.lon AS DOUBLE))
                    FROM source s;


