import tempfile

tmp = tempfile.mkstemp()[1]

with open(tmp, 'w') as f:
    exp_content = 'abc'
    f.write(exp_content)

with open(tmp) as f:
    act_content = f.readline()

assert act_content == exp_content
