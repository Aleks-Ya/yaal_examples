from timeit import Timer


def test_function_as_string():
    timer: Timer = Timer('sum(range(100))')
    execution_time: float = timer.timeit(number=1000)
    print(f"Execution time: {execution_time} seconds")
