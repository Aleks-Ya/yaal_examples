import unittest

from pydantic import ByteSize


class TestByteSize(unittest.TestCase):

    def test_human_readable(self):
        byte_size: ByteSize = ByteSize(0)
        self.assertEqual(byte_size.human_readable(), "0B")
        self.assertEqual(byte_size.human_readable(True), "0B")

        byte_size: ByteSize = ByteSize(400)
        self.assertEqual(byte_size.human_readable(), "400B")
        self.assertEqual(byte_size.human_readable(True), "400B")

        byte_size: ByteSize = ByteSize(1_400)
        self.assertEqual(byte_size.human_readable(), "1.4KiB")
        self.assertEqual(byte_size.human_readable(True), "1.4KB")

        byte_size: ByteSize = ByteSize(1_600_400)
        self.assertEqual(byte_size.human_readable(), "1.5MiB")
        self.assertEqual(byte_size.human_readable(True), "1.6MB")

        byte_size: ByteSize = ByteSize(1_700_600_400)
        self.assertEqual(byte_size.human_readable(), "1.6GiB")
        self.assertEqual(byte_size.human_readable(True), "1.7GB")


if __name__ == '__main__':
    unittest.main()
