import time

def lambda_handler(event, context):
    now: str = time.strftime('%H:%M:%S')
    return {
        "statusCode": 200,
        "headers": { "Content-Type": "application/json" },
        "body": f"""{{"message":"ok at {now}"}}""",
        "isBase64Encoded": False
    }