import torch
from torch import Tensor


def test_random():
    x: Tensor = torch.rand(5, 3)
    print(x)
