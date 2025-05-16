from sudachipy import Dictionary, SplitMode, MorphemeList, Tokenizer


def test_tokenize():
    tokenizer: Tokenizer = Dictionary().create()
    morphemes: MorphemeList = tokenizer.tokenize("国家公務員", SplitMode.C)
    [m.surface() for m in morphemes]
