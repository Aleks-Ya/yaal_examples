import textwrap


def test_dedent():
    s: str = textwrap.dedent("""\
    Line 1
    Line 2
    """)
    assert s == "Line 1\nLine 2\n"
