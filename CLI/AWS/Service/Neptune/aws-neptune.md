# AWS Neptune

List clusters: `aws neptune describe-db-clusters`


awscurl --service neptune-db \
  -X POST "https://db-neptune-1.cluster-cjsfzeowk6y5.us-east-1.neptune.amazonaws.com/gremlin" \
  -H "Content-Type: application/json" \
  -d '{"gremlin":"g.V().limit(1)"}'