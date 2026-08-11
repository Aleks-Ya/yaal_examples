import os
import tempfile
from pathlib import Path
from urllib.parse import urlparse, ParseResult

import boto3


def test_download_object():
    _, dest_file = tempfile.mkstemp()
    os.remove(dest_file)
    print(f"Dest file: {dest_file}")

    bucket_name: str = "kata-shared1"
    object_key: str = "data.txt"

    s3 = boto3.resource('s3')
    bucket = s3.Bucket(bucket_name)
    bucket.download_file(object_key, dest_file)
    assert Path(dest_file).read_text() == "abc\n"


def test_download_prefix_with_intermediate_dirs():
    with tempfile.TemporaryDirectory() as td:
        output_dir: Path = Path(td)
        print(f"Dest dir: {output_dir}")

        bucket_name: str = "kata-shared1"
        prefix: str = "dir_for_downloading"

        s3 = boto3.resource('s3')
        bucket = s3.Bucket(bucket_name)

        for obj in bucket.objects.filter(Prefix=prefix):
            key: str = obj.key
            if key.endswith("/"):
                output_dir.joinpath(key).mkdir(parents=True, exist_ok=True)
            else:
                dest_file: Path = output_dir / key
                bucket.download_file(key, dest_file)

        assert output_dir.joinpath(prefix).joinpath("a.txt").read_text() == "aaa\n"
        assert output_dir.joinpath(prefix).joinpath("b.txt").read_text() == "bbb\n"
        assert output_dir.joinpath(prefix).joinpath("sub_dir_1").joinpath("c.txt").read_text() == "ccc\n"


def test_download_prefix_without_intermediate_dirs():
    with tempfile.TemporaryDirectory() as td:
        output_dir: Path = Path(td)
        print(f"Dest dir: {output_dir}")

        bucket_name: str = "kata-shared1"
        prefix: str = "dir_for_downloading"
        if not prefix.endswith("/"):
            prefix += "/"

        s3 = boto3.resource('s3')
        bucket = s3.Bucket(bucket_name)

        for obj in bucket.objects.filter(Prefix=prefix):
            key: str = obj.key
            sub_key: str = key.replace(prefix, "", 1)
            key_split: tuple[str, str] = os.path.split(sub_key)
            path: str = key_split[0]
            file: str = key_split[1]
            if file != "":
                full_path: Path = output_dir / path
                full_path.mkdir(parents=True, exist_ok=True)
                dest_file: Path = full_path / file
                bucket.download_file(key, dest_file)

        assert output_dir.joinpath("a.txt").read_text() == "aaa\n"
        assert output_dir.joinpath("b.txt").read_text() == "bbb\n"
        assert output_dir.joinpath("sub_dir_1").joinpath("c.txt").read_text() == "ccc\n"


def test_download_folder_uri_with_intermediate_dirs():
    with tempfile.TemporaryDirectory() as td:
        output_dir: Path = Path(td)
        print(f"Dest dir: {output_dir}")

        s3_uri: str = "s3://kata-shared1/dir_for_downloading"
        parsed_url: ParseResult = urlparse(s3_uri)
        bucket_name: str = parsed_url.netloc
        prefix: str = parsed_url.path.lstrip('/')

        s3 = boto3.resource('s3')
        bucket = s3.Bucket(bucket_name)

        for obj in bucket.objects.filter(Prefix=prefix):
            key: str = obj.key
            if key.endswith("/"):
                output_dir.joinpath(key).mkdir(parents=True, exist_ok=True)
            else:
                dest_file: Path = output_dir / key
                bucket.download_file(key, dest_file)

        assert output_dir.joinpath(prefix).joinpath("a.txt").read_text() == "aaa\n"
        assert output_dir.joinpath(prefix).joinpath("b.txt").read_text() == "bbb\n"
        assert output_dir.joinpath(prefix).joinpath("sub_dir_1").joinpath("c.txt").read_text() == "ccc\n"
