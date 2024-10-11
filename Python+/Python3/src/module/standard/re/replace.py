import re


def test_replace_several_matches():
    orig_line: str = "Cats are smarter than smarter dogs"
    replaced_line: str = re.sub(r'\s\w+ter\s', ' smaller ', orig_line)
    assert replaced_line == 'Cats are smaller than smaller dogs'


def test_replace_with_groups():
    orig_line: str = "Everybody was amazed at how modern everything was."
    replaced_line: str = re.sub(r'(every\w*)', r'<b>\1</b>', orig_line, flags=re.IGNORECASE)
    assert replaced_line == "<b>Everybody</b> was amazed at how modern <b>everything</b> was."
