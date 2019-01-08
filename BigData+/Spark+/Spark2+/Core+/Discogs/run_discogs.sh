spark-submit --class artist.Main \
    --master yarn \
    --deploy-mode cluster \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    --queue default \
    discogs.jar \
    $1

# Run examples:
# ./run_discogs.sh hdfs://master-service:8020/discogs/artists_sample_10.xml.gz
# ./run_discogs.sh hdfs://master-service:8020/discogs/artists_sample_100000.xml.gz
# ./run_discogs.sh hdfs://master-service:8020/discogs/discogs_20190101_artists.xml.gz
