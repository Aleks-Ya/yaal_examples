# https://github.com/openai/tiktoken
import tiktoken
from tiktoken import Encoding


def test_tokenize():
    enc: Encoding = tiktoken.get_encoding("o200k_base")
    encoded: list[int] = enc.encode("hello world")
    assert encoded == [24912, 2375]
    decoded: str = enc.decode(encoded)
    assert decoded == "hello world"


# To get the tokeniser corresponding to a specific model in the OpenAI API
def test_encoding_for_model():
    enc: Encoding = tiktoken.encoding_for_model("gpt-4o")
    encoded: list[int] = enc.encode("hello world")
    assert encoded == [24912, 2375]
