from datetime import datetime

from faker import Faker


def test_fake_data():
    fake: Faker = Faker()
    Faker.seed(4321)  # Repeat the same result each test

    name: str = fake.name()
    address: str = fake.address()
    text: str = fake.text()
    date_time: datetime = fake.date_time()
    unix_time: float = fake.unix_time()
    iso8601: str = fake.iso8601()

    print(f"name={name}")
    print(f"address={address}")
    print(f"text={text}")
    print(f"date_time={date_time}")
    print(f"unix_time={unix_time}")
    print(f"iso8601={iso8601}")

    assert name == 'Jason Brown'
    assert address == """02907 Jenkins Fort Suite 672\nSmithside, FL 57896"""
    assert text == """World personal decision front politics. Little fast then go hope attention friend peace.\nEach ask film radio rate you. Suffer federal lot next senior statement."""
