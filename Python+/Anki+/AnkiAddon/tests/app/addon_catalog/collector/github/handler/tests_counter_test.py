from app.addon_catalog.collector.github.handler.tests_counter import TestsCounter


def test_count_tests():
    files: list[str] = [
        "service.py",
        "service_test.py",
        "test_service.py",
        "src/data.py",
        "src/data_test.py",
        "src/test_data.py",
        "src/service.test.js",
        "src/service.spec.js",
        "src/service.spec.ts",
        "src/service.test.ts",
        "src/service_test.rs",
        "src/data_test.c",
        "src/test_data.c",
    ]
    count: int = TestsCounter.count_tests(files)
    assert count == 11


def test_count_tests_empty():
    assert TestsCounter.count_tests([]) == 0
