set -e

AMBARI_HOST=172.17.0.2
AMBARI_PORT=8080
#User must have "Cluster User" role (minimal) or higher
AMBARI_USER=maria_dev
AMBARI_PASS=maria_dev
COMPONENT_NAME=HDFS
OUTPUT_FILE=/tmp/ambari_configs.tar.gz

CLUSTERS_API_URL="http://${AMBARI_HOST}:${AMBARI_PORT}/api/v1/clusters"

CLUSTER_NAME=$(curl -u "${AMBARI_USER}:${AMBARI_PASS}" -s "$CLUSTERS_API_URL" | sed -n 's/.*"cluster_name" : "\([^\"]*\)".*/\1/p')

rm -f "$OUTPUT_FILE"

curl -u "${AMBARI_USER}:${AMBARI_PASS}" -s -o "$OUTPUT_FILE" \
	"${CLUSTERS_API_URL}/${CLUSTER_NAME}/services/${COMPONENT_NAME}/components/${COMPONENT_NAME}_CLIENT?format=client_config_tar"

echo "Content of '$OUTPUT_FILE':"
tar -tf "$OUTPUT_FILE"
