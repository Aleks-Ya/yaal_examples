import unittest


class BytesToHumanStrCase(unittest.TestCase):

    def test_bytes_to_human_str(self):
        self.assertEqual(self._bytes_to_human_str(0), "0.0 B")
        self.assertEqual(self._bytes_to_human_str(1), "1.0 B")
        self.assertEqual(self._bytes_to_human_str(1_100), "1.1 KB")
        self.assertEqual(self._bytes_to_human_str(2_690_000), "2.6 MB")
        self.assertEqual(self._bytes_to_human_str(5_412_690_000), "5.0 GB")
        self.assertEqual(self._bytes_to_human_str(5_738_412_690_000), "5344.3 GB")

    @staticmethod
    def _bytes_to_human_str(bytes_size: int) -> str:
        for unit in ['B', 'KB', 'MB', 'GB']:
            if bytes_size < 1024.0 or unit == 'GB':
                return f"{bytes_size:.1f} {unit}"
            bytes_size /= 1024.0
        return f"{bytes_size:.1f} GB"


if __name__ == '__main__':
    unittest.main()
