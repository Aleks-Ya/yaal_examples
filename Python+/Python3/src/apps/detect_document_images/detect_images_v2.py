# NOT WORK - THE MODEL DOES NOT SUPPORT IMAGE CLASSIFICATION

import os

from PIL import Image
from transformers import LayoutLMv3FeatureExtractor, LayoutLMv3Tokenizer, LayoutLMv3ForSequenceClassification
from transformers import pipeline
import pytesseract

# --------- CONFIGURATION ---------
input_folder = "/home/aleks/tmp/documents_recognition"
output_file = "/tmp/document_photos_v2.txt"
threshold = 0.7  # adjust based on confidence
# --------------------------------

# Load model & tokenizer
model_name = "microsoft/layoutlmv3-base"
tokenizer = LayoutLMv3Tokenizer.from_pretrained(model_name)
feature_extractor = LayoutLMv3FeatureExtractor.from_pretrained(model_name)
model = LayoutLMv3ForSequenceClassification.from_pretrained(model_name, num_labels=2)

# Build a pipeline
classifier = pipeline(
    "image-classification",
    model=model,
    feature_extractor=feature_extractor,
    tokenizer=tokenizer
)


def detect_text_blocks(image):
    """Extracts words & bounding boxes using Tesseract OCR."""
    data = pytesseract.image_to_data(image, output_type=pytesseract.Output.DICT)
    words, boxes = [], []
    for i in range(len(data["text"])):
        if int(data["conf"][i]) > 50 and data["text"][i].strip():
            (x, y, w, h) = (data["left"][i], data["top"][i], data["width"][i], data["height"][i])
            words.append(data["text"][i])
            boxes.append([x, y, x + w, y + h])
    return words, boxes


with open(output_file, "w") as f:
    for filename in os.listdir(input_folder):
        if not filename.lower().endswith((".jpg", ".jpeg", ".png")):
            continue

        path = os.path.join(input_folder, filename)
        print(f"Processing {filename}")
        try:
            image = Image.open(path).convert("RGB")
            words, boxes = detect_text_blocks(image)

            # Skip images with almost no text
            if len(words) < 5:
                continue

            encoding = feature_extractor(image, return_tensors="pt")
            outputs = model(**encoding)
            probs = outputs.logits.softmax(dim=-1).detach().cpu().numpy()[0]
            doc_conf = probs[1]  # assume label 1 = document-like

            if doc_conf > threshold:
                f.write(path + "\n")
                print(f"[DOC] {filename} ({doc_conf:.2f})")
            else:
                print(f"[NON-DOC] {filename} ({doc_conf:.2f})")

        except Exception as e:
            print(f"Skipping {filename}: {e}")
