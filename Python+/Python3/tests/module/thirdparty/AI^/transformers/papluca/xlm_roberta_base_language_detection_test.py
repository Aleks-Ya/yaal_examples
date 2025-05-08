from transformers import pipeline, Pipeline


# https://huggingface.co/papluca/xlm-roberta-base-language-detection
def test_use_model():
    text: list[str] = [
        "Brevity is the soul of wit.",
        "Amor, ch'a nullo amato amar perdona."
    ]

    model_ckpt: str = "papluca/xlm-roberta-base-language-detection"
    pipe: Pipeline = pipeline("text-classification", model=model_ckpt)
    for v in pipe(text, top_k=1, truncation=True):
        print(v)
