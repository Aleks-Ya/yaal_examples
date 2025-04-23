import mimetypes


def test_guess_type():
    txt_type: tuple[str, str] = mimetypes.guess_type("abc.txt")
    assert ('text/plain', None) == txt_type


def test_guess_types():
    assert ('text/plain', None) == mimetypes.guess_type("abc.txt")
    assert ('text/html', None) == mimetypes.guess_type("abc.html")
    assert ('text/csv', None) == mimetypes.guess_type("abc.csv")
    assert ('image/png', None) == mimetypes.guess_type("abc.png")
    assert ('image/jpeg', None) == mimetypes.guess_type("abc.jpg")
    assert ('image/gif', None) == mimetypes.guess_type("abc.gif")
    assert ('audio/mpeg', None) == mimetypes.guess_type("abc.mp3")
    assert ('video/x-msvideo', None) == mimetypes.guess_type("abc.avi")
    assert ('application/x-tar', 'gzip') == mimetypes.guess_type("abc.tz")
    assert ('application/vnd.adobe.flash.movie', None) == mimetypes.guess_type("abc.swf")
    assert ('application/pdf', None) == mimetypes.guess_type("abc.pdf")


def test_guess_empty_types():
    assert (None, None) == mimetypes.guess_type("")
    assert (None, None) == mimetypes.guess_type("without_extension")
    assert (None, None) == mimetypes.guess_type("wrong_extension.no1")
    assert (None, None) == mimetypes.guess_type(".png")
