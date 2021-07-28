from faker import Faker

fake = Faker()

Faker.seed(4321)  # Repeat the same result each test

name = fake.name()
address = fake.address()
text = fake.text()
date_time = fake.date_time()
unix_time = fake.unix_time()
iso8601 = fake.iso8601()

print(f"name={name}")
print(f"address={address}")
print(f"text={text}")
print(f"date_time={date_time}")
print(f"unix_time={unix_time}")
print(f"iso8601={iso8601}")

assert name == 'Jason Brown'
assert address == """02907 Jenkins Fort Suite 672
Smithside, NV 31205"""
assert text == """Personal decision front politics summer little fast then. Interesting blue single either crime gas.
House laugh health price its. Federal lot next senior."""
