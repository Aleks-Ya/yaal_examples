for i in {1..20}; do
  echo "Process $i"
  # aws dynamodb batch-write-item --return-consumed-capacity TOTAL --request-items file://batch-write.json
  aws dynamodb batch-get-item --return-consumed-capacity TOTAL --request-items file://batch-get.json
done