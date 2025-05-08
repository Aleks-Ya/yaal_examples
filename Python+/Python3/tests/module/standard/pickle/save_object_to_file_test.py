import pickle
import tempfile


def test_pickle_dump_load():
    filename: str = tempfile.mktemp()
    exp_data: list[int] = [1, 3, 5]
    pickle.dump(exp_data, open(filename, 'wb'))
    act_data: list[int] = pickle.load(open(filename, 'rb'))
    assert act_data == exp_data
