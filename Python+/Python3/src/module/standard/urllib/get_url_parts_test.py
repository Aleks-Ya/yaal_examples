from urllib.parse import urlparse, ParseResult


def test_parse_s3_bucket_and_prefix():
    url: str = 's3://my-bucket/prefix1/file.txt'
    parsed_url: ParseResult = urlparse(url)
    bucket_name: str = parsed_url.netloc
    s3_prefix: str = parsed_url.path.lstrip('/')
    assert bucket_name == 'my-bucket'
    assert s3_prefix == 'prefix1/file.txt'


def test_get_anchor():
    url: str = 'https://me.com/data/info.html#table-of-contents'
    parsed_url: ParseResult = urlparse(url)
    anchor: str = parsed_url.fragment
    assert anchor == 'table-of-contents'

    url_absent_anchor: ParseResult = urlparse('https://me.com/data/info.html')
    assert url_absent_anchor.fragment == ''
    url_empty_anchor: ParseResult = urlparse('https://me.com/data/info.html#')
    assert url_empty_anchor.fragment == ''
