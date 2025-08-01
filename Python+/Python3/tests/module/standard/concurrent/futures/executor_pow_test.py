from concurrent.futures import ThreadPoolExecutor, Future


def test_execute_pow_on_executor():
    with ThreadPoolExecutor(max_workers=1) as executor:
        future: Future = executor.submit(pow, 323, 1235)
        result: int = future.result()
        print(result)
