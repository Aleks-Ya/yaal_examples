from pathlib import Path

from odfdo import Document, Header, Paragraph, Span, Style

from current_path import get_file_in_current_dir


def test_create_text_document():
    doc: Document = Document('text')

    # Styles
    h1style: Style = Style('paragraph', name="Heading 1")
    h1style.set_properties({'fo:font-size': "24pt", 'fo:font-weight': "bold"})
    doc.insert_style(h1style)

    # An automatic style
    bold_style: Style = Style('text', name="Bold")
    bold_style.set_properties({'fo:font-weight': "bold"})
    doc.insert_style(bold_style, automatic=True)

    # Text
    h: Header = Header(1, style="Heading 1", text="My first text")
    doc.body.append(h)
    p: Paragraph = Paragraph(text="Hello world. ")
    bold_part: Span = Span(text="This part is bold. ", style="Bold")
    p.append(bold_part)
    p.append_plain_text("This is after bold.")
    doc.body.append(p)

    out_file: Path = get_file_in_current_dir("out_text_doc.odt")
    doc.save(out_file)


def test_edit_text_document():
    infile: Path = get_file_in_current_dir('edit_in.odt')
    outfile: Path = get_file_in_current_dir('edit_out.odt')

    doc: Document = Document(infile)
    for item in doc.body.get_elements('//draw:text-box'):
        print(item.get_attribute('draw:id'))
        for child in item.children:
            print("\tchild:\t{}".format(child))

    for item in doc.body.get_elements('//draw:text-box'):
        for child in item.get_elements('.//text:span'):
            print("Text-span:\t{}".format(child))
            if child.text == "magic string":
                child.set_attribute('text:id', 'some-id')

    doc.save(outfile)
