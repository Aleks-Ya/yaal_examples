from pathlib import Path

from openai import OpenAI
from openai.types import FileObject


def test_file_upload(client: OpenAI):
    # DOES NOT WORK
    uploaded_file: FileObject = client.files.create(
        file=Path("data.json"),
        purpose="assistants",
    )
    print(uploaded_file)
