import pytest
from odfdo import Document, DrawFillImage, DrawMarker, StylePageLayout, StyleMasterPage
from odfdo.style_base import StyleBase
from rich.console import Console
from rich.tree import Tree


def test_list_style_families_1(draw_doc: Document):
    styles: list[StyleBase | DrawFillImage | DrawMarker] = draw_doc.get_styles()
    families: set[str | None] = {style.family for style in styles if getattr(style, 'family', None)}
    assert families == {'drawing-page', 'font-face', 'graphic', 'list', 'master-page', 'page-layout', 'paragraph'}


def test_list_style_families_2(draw_doc: Document):
    styles: list[StyleBase | DrawFillImage | DrawMarker] = draw_doc.get_styles()
    families: set[str | None] = {style.get_attribute_string('style:family') for style in styles}
    assert families == {None, 'drawing-page', 'graphic', 'paragraph'}


def test_list_styles_all(draw_doc: Document):
    styles: list[StyleBase | DrawFillImage | DrawMarker] = draw_doc.get_styles()
    for style in styles:
        if not isinstance(style, StyleBase) or isinstance(style, StylePageLayout) or isinstance(style, StyleMasterPage):
            print(f'Style is not a StyleBase: {style}')
            continue
        parent: StyleBase | DrawFillImage | DrawMarker | None = draw_doc.get_parent_style(style)
        parent_str: str | None = parent.get_attribute_string('style:name') if parent is not None else '---'

        family: str | None = style.get_attribute_string('style:family')
        name: str | None = style.get_attribute_string('style:name')
        display_name: str | None = style.get_attribute_string('style:display-name')

        print(f'Display name: {display_name}, Name: {name}, Parent: {parent_str}, Family: {family}, Tag: {style.tag}\n')


def test_list_styles_paragraph(draw_doc: Document):
    styles: list[StyleBase | DrawFillImage | DrawMarker] = draw_doc.get_styles(family='paragraph')
    for style in styles:
        family: str | None = style.get_attribute_string('style:family')
        name: str | None = style.get_attribute_string('style:name')
        display_name: str | None = style.get_attribute_string('style:display-name')
        print(f'Display name: {display_name}, Name: {name}, Family: {family}, Tag: {style.tag}\n')


def test_list_styles_graphic(draw_doc: Document):
    styles: list[StyleBase | DrawFillImage | DrawMarker] = draw_doc.get_styles(family='graphic')
    for style in styles:
        family: str | None = style.get_attribute_string('style:family')
        name: str | None = style.get_attribute_string('style:name')
        display_name: str | None = style.get_attribute_string('style:display-name')
        print(f'Display name: {display_name}, Name: {name}, Family: {family}, Tag: {style.tag}\n')


@pytest.mark.skip(reason="fix it")
def test_list_hidden_styles(draw_doc: Document):
    hidden_styles: list[StyleBase | DrawFillImage | DrawMarker] = draw_doc.get_styles(visible=False)
    for style in hidden_styles:
        parent_style: str | None = getattr(style, 'parent_style', None)
        parent_str: str = parent_style if parent_style is not None else '---'

        family: str | None = style.get_attribute_string('style:family')
        name: str | None = style.get_attribute_string('style:name')
        display_name: str | None = style.get_attribute_string('style:display-name')

        print(
            f'Hidden Display name: {display_name}, Name: {name}, Parent: {parent_str}, Family: {family}, Tag: {style.tag}\n')


def test_print_styles_tree(draw_doc: Document):
    tree: Tree = Tree("Styles")

    styles: dict[str, Tree] = {}
    for style in draw_doc.get_styles():
        display_name: str = getattr(style, 'display_name', '---')
        name: str = getattr(style, 'name', '---')
        family: str = getattr(style, 'family', '---')
        branch_label: str = f"[{family}] {display_name} ({name})"
        styles[name] = Tree(branch_label)

    for style in draw_doc.get_styles():
        name: str = getattr(style, 'name', '---')
        parent_name: str | None = getattr(style, 'parent_style', None)

        if parent_name and parent_name in styles:
            styles[parent_name].add(styles[name])
        else:
            tree.add(styles[name])

    console: Console = Console()
    console.print(tree)
