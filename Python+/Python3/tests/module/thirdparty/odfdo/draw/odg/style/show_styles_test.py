from odfdo import Document


def test_show_styles(draw_doc: Document):
    styles_str: str = draw_doc.show_styles(properties=True)
    print()
    print(styles_str)
