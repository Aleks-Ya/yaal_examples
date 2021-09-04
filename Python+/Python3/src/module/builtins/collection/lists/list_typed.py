# Strongly typed List

# Strongly typed list
from typing import List

typed_list: List[RuntimeError] = [RuntimeError()]

# Append
new_element = RuntimeError("#2")
typed_list.append(new_element)
print(typed_list)
