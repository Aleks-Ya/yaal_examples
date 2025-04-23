import base64


def test_base64_decode():
    base64_str: str = 'YWJj'
    base64_bytes: bytes = base64_str.encode("utf-8")
    raw_bytes: bytes = base64.b64decode(base64_bytes)
    raw_str: str = str(raw_bytes, "utf-8")
    assert raw_str == "abc"
