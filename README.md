# ElasticSearch for Hadoop Book Source Code

### Check Prerequisites
  - JDK 1.8

    ```sh 
    $ java -version 
    ```
  - Maven 

    ```sh 
    $ mvn -version 
    ```
  - Make sure HDFS and YARN are running

    ```sh 
    $ jps
    
    13386 SecondaryNameNode
    13059 NameNode
    13179 DataNode
    13649 NodeManager
    13528 ResourceManager
    ```
  - Make sure Elasticsearch 1.7+ is up and running at 9200
    ```sh
    $ curl -XGET http://localhost:9200
    
    {
        "status" : 200,
        "name" : "ES Hadoop Node",
        "cluster_name" : "eshadoopcluster",
        "version" : {
            "number" : "1.7.2",
            "build_hash" : "e43676b1385b8125d647f593f7202acbd816e8ec",
            "build_timestamp" : "2015-09-14T09:49:53Z",
            "build_snapshot" : false,
            "lucene_version" : "4.10.4"
        },
        "tagline" : "You Know, for Search"
    }

    ```

### Build
  - Open terminal and switch to the chapter directory you want to build
  - Execute 
    ```sh 
    $ cd ch01
    $ mvn clean package
    ```
  - Verify that file with xxx-job.jar pattern is generated
    ```sh 
    $ ls target
    ```
