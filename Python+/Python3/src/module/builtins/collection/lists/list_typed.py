# Strongly typed List

# Strongly typed list
from typing import List

typed_list: List[RuntimeError] = [RuntimeError()]

print('Append')
new_element = RuntimeError("#2")
typed_list.append(new_element)
print(typed_list)
print()

print('Iterate')
str_list: List[str] = ['a', 'b']
for s in str_list:
    print(s)
