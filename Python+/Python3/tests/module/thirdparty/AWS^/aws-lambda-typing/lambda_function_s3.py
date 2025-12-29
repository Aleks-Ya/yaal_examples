from typing import Any

from aws_lambda_typing.events import S3Event
from aws_lambda_typing.context import Context


def lambda_handler(event: S3Event, context: Context) -> dict[str, Any]:
    items = event.items()
    function: str = context.function_name
    return {"address": f"{items} ({function})"}
