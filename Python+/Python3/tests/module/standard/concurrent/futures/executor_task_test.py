import time
from concurrent.futures import ThreadPoolExecutor, Future


def test_execute_custom_task_on_executor():
    with ThreadPoolExecutor(max_workers=1) as executor:
        task: SlowTask = SlowTask(seconds=1)
        future: Future = executor.submit(task)
        result: str = future.result()
        assert result == "Waited for 1 seconds"


class SlowTask:
    def __init__(self, seconds: int):
        self.seconds = seconds

    def __call__(self) -> str:
        time.sleep(self.seconds)
        return f"Waited for {self.seconds} seconds"
