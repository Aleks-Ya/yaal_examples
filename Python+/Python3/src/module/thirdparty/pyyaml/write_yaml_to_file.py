import tempfile

import yaml

exp_names = yaml.safe_load("""
- 'eric'
- 'justin'
- 'mary-kate'
""")

_, full_name = tempfile.mkstemp()
with open(full_name, 'w') as file:
    yaml.dump(exp_names, file)

act_names = yaml.safe_load(open(full_name).read())
assert act_names == exp_names
