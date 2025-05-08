import random

all_elements = [1, 2, 3]
random_element = random.choice(all_elements)
print(random_element)
assert random_element in all_elements
