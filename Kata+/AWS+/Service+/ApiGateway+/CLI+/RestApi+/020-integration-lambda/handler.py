import time

def lambda_handler(event, context):
    return f"Hello from Lambda at {time.strftime('%H:%M:%S')}"