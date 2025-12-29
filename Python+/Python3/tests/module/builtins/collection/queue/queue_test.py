from queue import Queue, Empty

from pytest import raises


def test_put():
    q: Queue[int] = Queue()
    assert list(q.queue) == []
    q.put(1)
    q.put(2)
    assert list(q.queue) == [1, 2]


def test_get():
    q: Queue[int] = Queue()
    q.put(1)
    q.put(2)
    assert q.get() == 1
    assert q.get() == 2
    with raises(Empty):
        q.get(block=False)


def test_get_nowait():
    q: Queue[int] = Queue()
    q.put(1)
    q.put(2)
    assert q.get_nowait() == 1
    assert q.get_nowait() == 2
    with raises(Empty):
        q.get_nowait()


def test_full():
    q: Queue[int] = Queue(maxsize=2)
    assert not q.full()
    q.put(1)
    q.put(2)
    assert q.full()


def test_size():
    q: Queue[int] = Queue()
    assert q.qsize() == 0
    q.put(1)
    q.put(2)
    assert q.qsize() == 2
