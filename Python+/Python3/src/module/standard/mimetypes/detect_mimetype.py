import mimetypes
import unittest


class TestDetectMimeType(unittest.TestCase):
    def test_guess_type(self):
        txt_type: tuple[str, str] = mimetypes.guess_type("abc.txt")
        self.assertEqual(('text/plain', None), txt_type)

    def test_guess_types(self):
        self.assertEqual(('text/plain', None), mimetypes.guess_type("abc.txt"))
        self.assertEqual(('text/html', None), mimetypes.guess_type("abc.html"))
        self.assertEqual(('text/csv', None), mimetypes.guess_type("abc.csv"))
        self.assertEqual(('image/png', None), mimetypes.guess_type("abc.png"))
        self.assertEqual(('image/jpeg', None), mimetypes.guess_type("abc.jpg"))
        self.assertEqual(('image/gif', None), mimetypes.guess_type("abc.gif"))
        self.assertEqual(('audio/mpeg', None), mimetypes.guess_type("abc.mp3"))
        self.assertEqual(('video/x-msvideo', None), mimetypes.guess_type("abc.avi"))
        self.assertEqual(('application/x-tar', 'gzip'), mimetypes.guess_type("abc.tz"))
        self.assertEqual(('application/vnd.adobe.flash.movie', None), mimetypes.guess_type("abc.swf"))
        self.assertEqual(('application/pdf', None), mimetypes.guess_type("abc.pdf"))

    def test_guess_empty_types(self):
        self.assertEqual((None, None), mimetypes.guess_type(""))
        self.assertEqual((None, None), mimetypes.guess_type("without_extension"))
        self.assertEqual((None, None), mimetypes.guess_type("wrong_extension.no1"))
        self.assertEqual((None, None), mimetypes.guess_type(".png"))


if __name__ == '__main__':
    unittest.main()
