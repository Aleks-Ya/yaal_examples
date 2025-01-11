def test_cython():
    import pyximport
    pyximport.install()
    from helloworld import get_str
    assert get_str() == "Hello World!"
