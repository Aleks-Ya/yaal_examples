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
 - Line number - 12
 - Artists number - 10
 - Alias duplicates - 2


`artists_sample_100000.xml`:
 - Line number - 100 001
 - Artists number - 59 504
 - Alias duplicates - 22 156
 
`discogs_20190101_artists.xml`:
- Line number - 6 797 333
- Artists number - 6 034 590
- Alias duplicates - ?

Command: grep -o '<artist>' discogs_20190101_artists.xml | wc -l
