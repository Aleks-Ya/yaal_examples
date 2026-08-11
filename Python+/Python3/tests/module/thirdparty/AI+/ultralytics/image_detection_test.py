# test_yolo_hello.py
import os
from ultralytics import YOLO
from ultralytics.engine.results import Results


def test_yolo26n_loads_and_detects_person():
    # Directory to store the YOLO models
    cache_dir: str = os.path.expanduser("~/.cache/yolo_models/")
    os.makedirs(cache_dir, exist_ok=True)

    # Auto-downloads yolo26n.pt on first run (~5 MB)
    model: YOLO = YOLO(os.path.join(cache_dir, "yolo26n.pt"))

    # Ultralytics sample image; contains people. verbose=False keeps test output clean.
    results: list[Results] = model.predict(
        "https://ultralytics.com/images/bus.jpg",
        conf=0.25,
        verbose=False,
    )

    # One image in, one result out
    assert len(results) == 1

    # COCO class 0 == "person"; bus.jpg reliably has people
    detected_class_ids: list = results[0].boxes.cls.int().tolist()
    print(detected_class_ids)
    print(results[0].names)
    assert "bus" in results[0].names.values()
    assert 0 in detected_class_ids
