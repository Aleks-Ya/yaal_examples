import tempfile

tmp = tempfile.mkstemp()[1]

exp_content = 'Русский'

with open(tmp, 'w', encoding='utf8') as f:
    f.write(exp_content)

with open(tmp, encoding='utf8') as f:
    act_content = f.readline()

print(act_content)
assert act_content == exp_content
