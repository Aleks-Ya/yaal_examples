# Processing Discogs dataset with Spark
Source: http://data.discogs.com/

Build: `sbt build`

Upload artists_sample.xml.gz: 
```
hdfs dfs -mkdir -p /discogs
hdfs dfs -put /tmp/host-bind/artists_sample_10.xml.gz /discogs/
```

Pack: ` sbt assembly && cp target/scala-2.11/discogs.jar /tmp/hadoop-cluster-client-bind/`