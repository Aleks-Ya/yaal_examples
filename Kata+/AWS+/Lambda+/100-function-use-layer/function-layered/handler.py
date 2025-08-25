import json
from text import to_upper_case

def lambda_handler(event, context):
    s: str = to_upper_case("hello")
    return {
        'statusCode': 200,
        'body': json.dumps(f'{s} from Lambda Layer!')
    }