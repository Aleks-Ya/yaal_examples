import re


def test_replace_several_matches():
    orig_line: str = "Cats are smarter than smarter dogs"
    replaced_line: str = re.sub(r'\s\w+ter\s', ' smaller ', orig_line)
    assert replaced_line == 'Cats are smaller than smaller dogs'


def test_replace_with_groups():
    orig_line: str = "Everybody was amazed at how modern everything was."
    replaced_line: str = re.sub(r'(every\w*)', r'<b>\1</b>', orig_line, flags=re.IGNORECASE)
    assert replaced_line == "<b>Everybody</b> was amazed at how modern <b>everything</b> was."


def test_replace_b_tag():
    phrase: str = "every"
    orig_line: str = '<b>Everybody</b> was amazed at how modern <b>everything</b> was.'
    replaced_line: str = re.sub(fr'<b>({phrase}\w*)</b>', r'\1', orig_line, flags=re.IGNORECASE)
    assert replaced_line == "Everybody was amazed at how modern everything was."


def test_replace_b_tag_with_class():
    phrase: str = "every"
    prefix: str = '<b class="mark">'
    suffix: str = '</b>'
    orig_line: str = '<b class="mark">Everybody</b> was <b>amazed</b> at how modern <b class="mark">everything</b> was.'
    replaced_line: str = re.sub(fr'{prefix}({phrase}\w*){suffix}', r'\1', orig_line, flags=re.IGNORECASE)
    assert replaced_line == "Everybody was <b>amazed</b> at how modern everything was."


def test_replace_any_in_b_tag_with_class():
    prefix: str = '<b class="mark">'
    suffix: str = '</b>'
    orig_line: str = '<b class="mark">Everybody</b> was <b>amazed</b> at how modern <b class="mark">everything</b> was.'
    replaced_line: str = re.sub(fr'{prefix}(\w*){suffix}', r'\1', orig_line, flags=re.IGNORECASE)
    assert replaced_line == "Everybody was <b>amazed</b> at how modern everything was."
