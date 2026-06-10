from odfdo import Document, Element


def test_get_styled_elements_all(draw_doc: Document):
    elements: list[Element] = draw_doc.get_styled_elements()
    for element in elements:
        print(element)


def test_get_styled_elements_by_name(draw_doc: Document):
    elements: list[Element] = draw_doc.get_styled_elements('My_20_style_20_1')
    for element in elements:
        print(element)
