from google.genai import Client
from google.genai.types import GenerateContentResponse

from pydantic import BaseModel


class Recipe(BaseModel):
    recipe_name: str
    ingredients: list[str]


def test_structured_output(client: Client):
    response: GenerateContentResponse = client.models.generate_content(
        model="gemini-2.0-flash",
        contents="List a few popular cookie recipes, and include the amounts of ingredients.",
        config={
            "response_mime_type": "application/json",
            "response_schema": list[Recipe],
        },
    )
    # Use the response as a JSON string.
    print(response.text)

    # Use instantiated objects.
    my_recipes: list[Recipe] = response.parsed
    print(my_recipes)
