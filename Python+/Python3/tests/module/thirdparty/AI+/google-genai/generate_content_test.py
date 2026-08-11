from google.genai import Client
from google.genai.types import GenerateContentResponse


def test_generate_content(client: Client):
    response: GenerateContentResponse = client.models.generate_content(
        model="gemini-2.0-flash",
        contents=["How does AI work?"]
    )
    print(response.text)
