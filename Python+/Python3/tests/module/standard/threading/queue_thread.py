from threading import current_thread
from queue import Queue
from threading import Thread


def log(msg: str):
    print(f"[{current_thread().name}] {msg}")


class QueueThread(Thread):
    def __init__(self):
        super().__init__(daemon=True)
        self.__queue: Queue[object] = Queue()
        self.__sentinel: object = object()

    def run(self) -> None:
        log("Enter run")
        while True:
            log(f"Getting item. Queue size: {self.__queue.qsize()}")
            item: object = self.__queue.get()
            if item is self.__sentinel:
                log("Finishing by sentinel")
                self.__queue.task_done()
                break
            try:
                self.__process_item(item)
            except Exception as e:
                raise RuntimeError(f"Error processing item: {item}", e)
            self.__queue.task_done()
        log("Exit run")

    @staticmethod
    def __process_item(item: object):
        log(f"Processing: {item}")

    def add(self, item: str) -> None:
        log(f"Add: {item}")
        self.__queue.put(item)

    def wait_finish(self) -> None:
        log("Wait finish")
        if self.is_alive():
            log("Waiting for finish")
            self.__queue.put(self.__sentinel)
            self.__queue.join()


def test_queue_thread():
    log("Start")
    thread: QueueThread = QueueThread()
    thread.start()
    thread.add("Item 1")
    thread.add("Item 2")
    thread.wait_finish()
    log("Finished")


def test_wait_finish_empty_started():
    thread: QueueThread = QueueThread()
    thread.start()
    thread.wait_finish()


def test_wait_finish_not_started():
    thread: QueueThread = QueueThread()
    thread.wait_finish()
