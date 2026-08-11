import spacy
from spacy import Language
from spacy.tokens import Doc


def test_sents():
    nlp: Language = spacy.load('ja_ginza_electra')
    doc: Doc = nlp('銀座でランチをご一緒しましょう。')
    for sent in doc.sents:
        for token in sent:
            print(
                token.i,
                token.orth_,
                token.lemma_,
                token.norm_,
                token.morph.get("Reading"),
                token.pos_,
                token.morph.get("Inflection"),
                token.tag_,
                token.dep_,
                token.head.i,
            )
    print('EOS')
