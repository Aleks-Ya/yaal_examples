from odf.element import Element
from odf.opendocument import OpenDocumentText, OpenDocument
from odf.style import Style, TextProperties
from odf.text import H, P, Span
from odf.opendocument import load
from odf import text, draw


# Based on: https://github.com/eea/odfpy/wiki/Introduction#example
def test_create_text_document():
    doc: OpenDocumentText = OpenDocumentText()

    # Styles
    s: Element = doc.styles
    h1style: Element = Style(name="Heading 1", family="paragraph")
    h1style.addElement(TextProperties(attributes={'fontsize': "24pt", 'fontweight': "bold"}))
    s.addElement(h1style)

    # An automatic style
    bold_style: Element = Style(name="Bold", family="text")
    bold_prop: Element = TextProperties(fontweight="bold")
    bold_style.addElement(bold_prop)
    doc.automaticstyles.addElement(bold_style)

    # Text
    h: Element = H(outlinelevel=1, stylename=h1style, text="My first text")
    doc.text.addElement(h)
    p: Element = P(text="Hello world. ")
    bold_part: Element = Span(stylename=bold_style, text="This part is bold. ")
    p.addElement(bold_part)
    p.addText("This is after bold.")
    doc.text.addElement(p)

    doc.save("out_text_doc.odt")


# Based on: https://github.com/eea/odfpy/wiki/Introduction#example-1
def test_edit_text_document():
    infile: str = 'edit_in.odt'
    outfile: str = 'edit_out.odt'

    doc: OpenDocument = load(infile)
    for item in doc.getElementsByType(draw.TextBox):
        print(item.getAttribute('id'))
        for child in item.childNodes:
            print("\tchild:\t{}".format(child))

    for item in doc.getElementsByType(draw.TextBox):
        for child in item.getElementsByType(text.Span):
            print("Text-span:\t{}".format(child))
            if str(child) == "magic string":
                child.setAttribute('id', 'some-id')

    doc.save(outfile)
