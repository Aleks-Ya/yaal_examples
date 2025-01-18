from langid import classify
import fasttext


# NOT WORKING
def test_detect_language():
    model = fasttext.load_model('lid.176.bin')
    text: str = "Hola, ¿cómo estás?"
    predictions = model.predict(text)
    print(predictions)
    language, confidence = classify('This is a test.')
    assert language == 'en'
