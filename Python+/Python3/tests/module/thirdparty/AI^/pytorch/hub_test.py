import tempfile

import torch
from transformers import BertTokenizerFast, PreTrainedTokenizer
from directory_tree import DisplayTree


def test_load_model_from_hub():
    tokenizer: BertTokenizerFast = torch.hub.load('huggingface/pytorch-transformers', 'tokenizer', 'bert-base-uncased')
    tokens: list[str] = tokenizer.tokenize("Hello World!")
    assert tokens == ['hello', 'world', '!']


def test_load_model_from_hub_to_local_file():
    model_dir: str = tempfile.mkdtemp()
    print(f"Dir: {model_dir}")
    model: BertTokenizerFast = torch.hub.load('huggingface/pytorch-transformers', 'tokenizer', 'bert-base-uncased')
    model.save_pretrained(model_dir)
    DisplayTree(model_dir)
    loaded_model: PreTrainedTokenizer = BertTokenizerFast.from_pretrained(model_dir)
    tokens: list[str] = loaded_model.tokenize("Hello World!")
    assert tokens == ['hello', 'world', '!']
