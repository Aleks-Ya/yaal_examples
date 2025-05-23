import base64


def test_base64_encode():
    raw_str: str = 'abc'
    raw_bytes: bytes = raw_str.encode("utf-8")
    base64_bytes: bytes = base64.b64encode(raw_bytes)
    base64_str: str = str(base64_bytes, "utf-8")
    assert base64_str == "YWJj"
