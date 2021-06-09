import os
import pickle
import tempfile

_, filename = tempfile.mkstemp()
exp_data = [1, 3, 5]
pickle.dump(exp_data, open(filename, 'wb'))

act_data = pickle.load(open(filename, 'rb'))

os.remove(filename)

assert exp_data == act_data
