from openai import OpenAI
from openai.types.responses import Response, ResponseInputParam, EasyInputMessageParam


def test_response_input_str(client: OpenAI):
    response: Response = client.responses.create(
        model="gpt-4.1",
        input="Write a one-sentence bedtime story about a unicorn."
    )
    print(response.output_text)


def test_response_input_object(client: OpenAI):
    response_input_param: ResponseInputParam = [
        EasyInputMessageParam(
            role="user",
            content="Write a one-sentence bedtime story about a unicorn."
        )
    ]
    response: Response = client.responses.create(
        model="gpt-4.1",
        input=response_input_param
    )
    print(response.output_text)
