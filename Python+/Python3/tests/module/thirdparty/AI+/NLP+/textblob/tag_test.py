from textblob import TextBlob, WordList


def test_tag():
    text: str = "The titular threat of The Blob has always struck me as the ultimate movie monster."
    blob: TextBlob = TextBlob(text)
    assert blob.tags == [('The', 'DT'),
                         ('titular', 'JJ'),
                         ('threat', 'NN'),
                         ('of', 'IN'),
                         ('The', 'DT'),
                         ('Blob', 'NNP'),
                         ('has', 'VBZ'),
                         ('always', 'RB'),
                         ('struck', 'VBN'),
                         ('me', 'PRP'),
                         ('as', 'IN'),
                         ('the', 'DT'),
                         ('ultimate', 'JJ'),
                         ('movie', 'NN'),
                         ('monster', 'NN')]

    assert blob.noun_phrases == WordList(['titular threat', 'blob', 'ultimate movie monster'])

    for sentence in blob.sentences:
        print(sentence.sentiment.polarity)
