import yaml

names_yaml = """
- 'eric'
- 'justin'
- 'mary-kate'
"""

names = yaml.safe_load(names_yaml)
assert names == ['eric', 'justin', 'mary-kate']
