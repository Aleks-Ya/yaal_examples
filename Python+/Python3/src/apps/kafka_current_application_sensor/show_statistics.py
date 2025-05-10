import json
from json.decoder import JSONDecodeError
from typing import Dict

from confluent_kafka import Consumer

c = Consumer({
    'bootstrap.servers': 'localhost:32092,localhost:32093,localhost:32094',
    'group.id': 'show-statistics-8',
    'client.id': 'show_statistics',
    'auto.offset.reset': 'earliest'
})

c.subscribe(['active_process'])

data: Dict[str, float] = {}

message_counter = 0
error_counter = 0
interval_sec = 10.0
while True:
    messages = c.consume(num_messages=5000, timeout=interval_sec)

    for msg in messages:
        if msg is None:
            continue
        if msg.error():
            print("Consumer error: {}".format(msg.error()))
            continue

        try:
            value = msg.value().decode('utf-8')
            event_info = json.loads(value)
            process_name = event_info['process_name']
            begin_time = event_info['begin_time']
            end_time = event_info['end_time']
            duration = round((end_time - begin_time) / 1000)
            if process_name in data:
                current_duration = data[process_name]
                new_duration = round(current_duration + duration)
            else:
                new_duration = round(duration)
            data[process_name] = new_duration
            message_counter += 1
        except JSONDecodeError:
            error_counter += 1
    print(f'message_counter: {message_counter}, error_counter: {error_counter},data: {data}')
