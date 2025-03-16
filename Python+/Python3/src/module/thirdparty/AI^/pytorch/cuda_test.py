import torch


def test_is_available():
    assert not torch.cuda.is_available()
