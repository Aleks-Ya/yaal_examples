from fugashi import Tagger


def test_fugashi():
    tagger: Tagger = Tagger('-Owakati')
    text: str = "麩菓子は、麩を主材料とした日本の菓子。"
    tagger.parse(text)
    # => '麩 菓子 は 、 麩 を 主材 料 と し た 日本 の 菓子 。'
    for word in tagger(text):
        print(word, word.feature.lemma, word.pos, sep='\t')
        # "feature" is the Unidic feature data as a named tuple
