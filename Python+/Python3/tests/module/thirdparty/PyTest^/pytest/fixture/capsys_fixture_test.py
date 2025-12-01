from _pytest.capture import CaptureResult
from pytest import CaptureFixture


def test_stdout(capsys: CaptureFixture):
    print('A test message')
    out: CaptureResult[str] = capsys.readouterr()
    assert out.out == 'A test message\n'
