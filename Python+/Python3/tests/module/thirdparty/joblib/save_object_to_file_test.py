import os
import tempfile

import joblib


def test_save_object_to_file():
    _, filename = tempfile.mkstemp()
    exp_data: list[int] = [1, 3, 5]
    joblib.dump(exp_data, filename)

    act_data = joblib.load(filename)

    os.remove(filename)

    assert exp_data == act_data
