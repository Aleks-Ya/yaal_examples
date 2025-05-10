from openai import OpenAI
from openai.types.responses import Response


def test_response(client: OpenAI):
    response: Response = client.responses.create(
        model="gpt-4.1",
        input="Write a one-sentence bedtime story about a unicorn."
    )
    print(response.output_text)
