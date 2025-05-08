import tempfile
from pathlib import Path

import requests
from requests import Response


def test_download_file_via_memory():
    output_file: Path = Path(tempfile.mkstemp(".jpeg")[1])
    print(output_file)
    response: Response = requests.get('https://httpbin.io/image/jpeg')
    response.raise_for_status()
    assert output_file.stat().st_size == 0
    with open(output_file, 'wb') as f:
        f.write(response.content)
    assert output_file.stat().st_size > 0


def test_download_file_to_disk():
    output_file: Path = Path(tempfile.mkstemp(".jpeg")[1])
    print(output_file)
    assert output_file.stat().st_size == 0
    with requests.get('https://httpbin.io/image/jpeg', stream=True) as response:
        response.raise_for_status()
        with open(output_file, 'wb') as f:
            for chunk in response.iter_content(chunk_size=8192):
                f.write(chunk)
    assert output_file.stat().st_size > 0
