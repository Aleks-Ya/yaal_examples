import re


class TestsCounter:

    @staticmethod
    def count_tests(files: list[str]) -> int:
        count: int = len([file for file in files if
                          TestsCounter.__is_python_test(file) or
                          TestsCounter.__is_javascript_test(file) or
                          TestsCounter.__is_rust_test(file) or
                          TestsCounter.__is_typescript_test(file) or
                          TestsCounter.__is_c_test(file)
                          ])
        return count

    @staticmethod
    def __is_python_test(file: str) -> bool:
        return file.endswith("_test.py") or re.match("^\w*/?test_\w+.py", file)

    @staticmethod
    def __is_javascript_test(file: str) -> bool:
        return file.endswith(".test.js") or file.endswith(".spec.js") or file.endswith(".spec.ts")

    @staticmethod
    def __is_typescript_test(file: str) -> bool:
        return file.endswith(".test.ts") or file.endswith(".spec.ts")

    @staticmethod
    def __is_rust_test(file: str) -> bool:
        return file.endswith("_test.rs")

    @staticmethod
    def __is_c_test(file: str) -> bool:
        return file.endswith("_test.c") or re.match("^\w*/?test_\w+.c", file)
