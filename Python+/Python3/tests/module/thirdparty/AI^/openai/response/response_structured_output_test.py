from openai import OpenAI
from pydantic import BaseModel
from openai.types.responses import ParsedResponse, ResponseInputItemParam, EasyInputMessageParam


class CalendarEvent(BaseModel):
    name: str
    date: str
    participants: list[str]


def test_structured_output(client: OpenAI):
    response: ParsedResponse[CalendarEvent] = client.responses.parse(
        model="gpt-4o-2024-08-06",
        input=[
            {"role": "system", "content": "Extract the event information."},
            {
                "role": "user",
                "content": "Alice and Bob are going to a science fair on Friday.",
            },
        ],
        text_format=CalendarEvent,
    )

    event: CalendarEvent = response.output_parsed
    print(event)


def test_structured_output_no_warnings(client: OpenAI):
    input_list: list[ResponseInputItemParam] = [
        EasyInputMessageParam(role="system", content="Extract the event information."),
        EasyInputMessageParam(role="user", content="Alice and Bob are going to a science fair on Friday.")
    ]
    response: ParsedResponse[CalendarEvent] = client.responses.parse(
        model="gpt-4o-2024-08-06",
        input=input_list,
        text_format=CalendarEvent,
    )
    event: CalendarEvent = response.output_parsed
    print(event)
