from faker import Faker

fake = Faker()

Faker.seed(4321)  # Repeat the same result each test

name = fake.name()
address = fake.address()
text = fake.text()

print(name)
print(address)
print(text)

assert name == 'Jason Brown'
assert address == """02907 Jenkins Fort Suite 672
Smithside, NV 31205"""
assert text == """Personal decision front politics summer little fast then. Interesting blue single either crime gas.
House laugh health price its. Federal lot next senior."""
