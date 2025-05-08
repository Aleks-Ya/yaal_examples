import textwrap


def test_shorten():
    s: str = "A long long long long long text"
    act_str: str = textwrap.shorten(s, width=10, placeholder="...")
    exp_str: str = "A long..."
    assert exp_str == act_str
