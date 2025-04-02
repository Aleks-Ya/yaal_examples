import tempfile
from collections import OrderedDict
from pathlib import Path
from urllib import request

import torch


# https://huggingface.co/google-bert/bert-base-uncased
def test_load_model_from_file():
    path: Path = Path(tempfile.gettempdir()) / "google-bert_bert-base-uncased_pytorch_model.bin"
    if not path.exists():
        url: str = 'https://huggingface.co/google-bert/bert-base-uncased/resolve/main/pytorch_model.bin'
        request.urlretrieve(url, path)
    assert path.exists()
    assert path.stat().st_size > 400 * 1024 * 1024
    model: OrderedDict = torch.load(path)
    print(len(model))

