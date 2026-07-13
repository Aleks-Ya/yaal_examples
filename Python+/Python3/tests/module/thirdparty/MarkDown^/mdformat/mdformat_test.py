import mdformat


def test_format_markdown_string():
    unformatted_markdown: str = "# title\ncontent"
    formatted_markdown: str = mdformat.text(unformatted_markdown)
    assert formatted_markdown == "# title\n\ncontent\n"