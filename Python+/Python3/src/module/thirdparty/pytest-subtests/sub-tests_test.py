def test(subtests):
    for i in range(5):
        with subtests.test(msg="custom message", i=i):
            assert i % 2 == 0
