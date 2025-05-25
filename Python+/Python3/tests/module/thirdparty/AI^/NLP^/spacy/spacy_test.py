import spacy
from spacy import Language
from spacy.tokens import Doc
from spacy.symbols import VERB


def test_spycy():
    # Download data first: python -m spacy download en_core_web_sm

    # Load English tokenizer, tagger, parser and NER
    nlp: Language = spacy.load("en_core_web_sm")

    # Process whole documents
    text: str = ("When Sebastian Thrun started working on self-driving cars at "
                 "Google in 2007, few people outside of the company took him "
                 "seriously. “I can tell you very senior CEOs of major American "
                 "car companies would shake my hand and turn away because I wasn’t "
                 "worth talking to,” said Thrun, in an interview with Recode earlier "
                 "this week.")
    doc: Doc = nlp(text)

    # Analyze syntax
    print("Noun phrases:", [chunk.text for chunk in doc.noun_chunks])
    print("Verbs:", [token.lemma_ for token in doc if token.pos == VERB])

    # Find named entities, phrases and concepts
    for entity in doc.ents:
        print(entity.text, entity.label_)
