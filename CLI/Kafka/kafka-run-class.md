# kafka-run-class CLI

Help: `kafka-run-class.sh -h`

## Show message count in topic (sum all partitions manually)
`kafka-run-class.sh kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic my-topic --time -1`
