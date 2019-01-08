# Processing Discogs dataset with Spark
Source: http://data.discogs.com/

Build: `sbt build`

Upload artists_sample.xml.gz:
```
hdfs dfs -mkdir -p /discogs
hdfs dfs -put /tmp/host-bind/artists_sample_10.xml.gz /discogs/
```

Pack: ` sbt assembly && cp target/scala-2.11/discogs.jar /tmp/hadoop-cluster-client-bind/`

`artists_sample_10.xml`:
 - Artists number - 10
 - Alias duplicates - 2


`artists_sample_100000.xml`:
 - Artists number - 59504
 - Alias duplicates - 22156
 
`discogs_20190101_artists.xml`:
- Artists number - 6034590
- Alias duplicates - ?

Command: grep -o '<artist>' discogs_20190101_artists.xml | wc -l
