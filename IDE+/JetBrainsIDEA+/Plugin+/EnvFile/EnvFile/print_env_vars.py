import os

for key, value in os.environ.items():
    print(f"{key}={value}")
print()

print("Vars from EnvFile:")
vars: list[str] = ["CITY", "CITY_RANDOM", "CITY_USER"]
for var in vars:
    print(f"{var}={os.environ.get(var)}")
