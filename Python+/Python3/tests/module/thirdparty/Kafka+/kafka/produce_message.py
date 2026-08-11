# Works only on Python <=3.6 because of "SyntaxError '<SimpleProducer batch=%s>' % self.async"
from kafka import KafkaProducer

producer = KafkaProducer(bootstrap_servers='localhost:32768,localhost:32769,localhost:32770')
producer.send('foobar', b'some_message_bytes')
