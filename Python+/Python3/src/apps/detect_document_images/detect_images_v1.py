import os
from PIL import Image
from transformers import pipeline

# Initialize classifier (uses a lightweight model from Hugging Face)
# model_name = "microsoft/resnet-50"
model_name = "google/vit-base-patch16-224"
classifier = pipeline("image-classification", model=model_name)

input_folder = "/home/aleks/tmp/documents_recognition"
output_file = "/tmp/document_photos_v1.txt"

doc_keywords = ["document", "receipt", "paper", "text", "invoice", "form", "screen", "menu", "envelope"]

with open(output_file, "w") as f:
    for filename in os.listdir(input_folder):
        if filename.lower().endswith((".jpg", ".jpeg", ".png")):
            path = os.path.join(input_folder, filename)
            print(f"Processing {filename}")
            try:
                image = Image.open(path)
                results = classifier(image)
                labels = [r["label"].lower() for r in results[:3]]
                print(f"Labels: {labels}")
                if any(k in labels for k in doc_keywords):
                    f.write(path + "\n")
                    print(f"Document detected: {filename}")
            except Exception as e:
                print(f"Skipping {filename}: {e}")
