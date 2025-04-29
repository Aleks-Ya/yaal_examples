from pydantic import ByteSize


def test_human_readable():
    byte_size: ByteSize = ByteSize(0)
    assert byte_size.human_readable() == "0B"
    assert byte_size.human_readable(True) == "0B"

    byte_size: ByteSize = ByteSize(400)
    assert byte_size.human_readable() == "400B"
    assert byte_size.human_readable(True) == "400B"

    byte_size: ByteSize = ByteSize(1_400)
    assert byte_size.human_readable() == "1.4KiB"
    assert byte_size.human_readable(True) == "1.4KB"

    byte_size: ByteSize = ByteSize(1_600_400)
    assert byte_size.human_readable() == "1.5MiB"
    assert byte_size.human_readable(True) == "1.6MB"

    byte_size: ByteSize = ByteSize(1_700_600_400)
    assert byte_size.human_readable() == "1.6GiB"
    assert byte_size.human_readable(True) == "1.7GB"
