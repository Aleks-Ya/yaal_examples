import json
import time
from subprocess import CalledProcessError


def get_active_application_pid():
    from subprocess import check_output
    cmd = ['xdotool', 'getwindowfocus', 'getwindowpid']
    output = check_output(cmd)
    output_str = output.decode("utf-8")
    return int(output_str.strip())


def get_process_name():
    from psutil import Process
    p = Process(active_process_pid)
    return p.name()


def init_kafka_producer():
    from confluent_kafka import Producer
    bootstrap_servers = 'localhost:32769,localhost:32770'
    return Producer({'bootstrap.servers': bootstrap_servers})


class EventInfo:
    def __init__(self, process_name, begin_time, end_time, error):
        self.process_name = process_name
        self.begin_time = begin_time
        self.end_time = end_time
        self.error = error


producer = init_kafka_producer()

topic = "active_process"


def get_current_time_ms():
    return int(round(time.time() * 1000))


end_time = get_current_time_ms()
while True:
    producer.poll(0)
    begin_time = end_time
    try:
        active_process_pid = get_active_application_pid()
        active_process_name = get_process_name()
        end_time = get_current_time_ms()
        event_info = EventInfo(active_process_name, begin_time, end_time, '')
    except CalledProcessError as e:
        end_time = get_current_time_ms()
        error = str(e)
        event_info = EventInfo('error', begin_time, end_time, error)
    event_info_json = json.dumps(event_info.__dict__)
    print(f"{event_info_json}")
    producer.produce(topic, event_info_json.encode('utf-8'))
    time.sleep(2)
