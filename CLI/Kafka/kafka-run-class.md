# kafka-run-class CLI

## Show message count in topic (sum all partitions manually)
`kafka-run-class kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic my-topic --time -1`
