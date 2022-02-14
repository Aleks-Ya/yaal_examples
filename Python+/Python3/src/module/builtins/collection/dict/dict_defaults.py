# Create dictionary
from collections import defaultdict

# Single default value for absent keys
price_dict = defaultdict(lambda: 42)
price_dict['car'] = 1000
price_dict['house'] = 5000
assert price_dict['car'] == 1000
assert price_dict['house'] == 5000
assert price_dict['airplane'] == 42

# Dict as default values
default_dict = {'car': 1000, 'house': 5000}
actual_dict = {'car': 3000, 'airplane': 8000}
backed_dict = default_dict.copy()
backed_dict.update(actual_dict)
assert backed_dict['car'] == 3000
assert backed_dict['house'] == 5000
assert backed_dict['airplane'] == 8000

# Get or default
d = {'car': 1000}
assert d.get('car', 42) == 1000
assert d.get('airplane', 42) == 42

# Chain of dicts
absent = 42
d1 = {'car': 1000, 'house': 5000, 'airplane': 10000}
d2 = {'car': 2000, 'house': 6000}
d3 = {'car': 3000}
# Get syntax:
assert d3.get('car') or d2.get('car') == 3000
assert d3.get('house') or d2.get('house') == 6000
assert d3.get('airplane') or d2.get('airplane') or d1.get('airplane') == 10000
assert d3.get('absent_key') or d2.get('absent_key') or d1.get('absent_key') or absent == 42
# Braces syntax (not work):
assert d3['car'] or d2['car'] == 3000
# assert d3['house'] or d2['house'] == 6000
# assert d3['airplane'] or d2['airplane'] or d1['airplane'] == 10000
# assert d3['absent_key'] or d2['absent_key'] or d1['absent_key'] or absent == 42
