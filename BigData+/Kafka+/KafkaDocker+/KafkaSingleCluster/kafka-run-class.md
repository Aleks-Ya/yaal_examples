# kafka-run-class CLI

## Show message count in topic (sum all partitions manually)
`kafka-run-class.sh kafka.tools.GetOffsetShell --broker-list $(broker-list.sh) --topic my-topic --time -1`