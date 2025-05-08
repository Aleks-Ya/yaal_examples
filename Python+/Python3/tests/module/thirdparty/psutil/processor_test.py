import psutil


def test_cpu_count():
    cpu_count: int = psutil.cpu_count()
    print(f"cpu_count: {cpu_count}")
    assert cpu_count > 0
