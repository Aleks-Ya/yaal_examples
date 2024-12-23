import mistune
from mistune import Markdown
from mistune.renderers.markdown import MarkdownRenderer


def test_render_md():
    markdown: Markdown = mistune.create_markdown(renderer=MarkdownRenderer())
    md: str = markdown("Hello **World**!")
    assert md == "Hello **World**!\n"
