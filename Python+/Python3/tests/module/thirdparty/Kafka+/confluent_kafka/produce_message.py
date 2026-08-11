from confluent_kafka import Producer

bootstrap_servers = 'localhost:32769,localhost:32770'
p = Producer({'bootstrap.servers': bootstrap_servers})

data = "my message"
topic = "confluent_kafka_python"
p.produce(topic, data.encode('utf-8'))
p.flush()
