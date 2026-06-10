from dataclasses import dataclass

from odfdo import Document, Body, Paragraph, Span, DrawPage

from apps.libre_office_draw_search.data_types import OdgPath, Text, PageName


@dataclass
class OdgFileData:
    page_names: list[str]
    texts: list[Text]


class OdgParser:

    @staticmethod
    def parse(odg_file: OdgPath) -> OdgFileData:
        document: Document = Document(str(odg_file))
        texts: list[Text] = OdgParser.__extract_texts(document)
        page_names: list[str] = OdgParser.__extract_page_names(document)
        return OdgFileData(page_names, texts)

    @staticmethod
    def __extract_texts(document: Document) -> list[Text]:
        body: Body = document.body
        paragraphs: list[Paragraph] = body.get_paragraphs()
        texts: list[Text] = []
        for paragraph in paragraphs:
            text_content: str = paragraph.text
            if text_content:
                texts.append(Text(text_content))

        spans: list[Span] = body.get_spans()
        for span in spans:
            text_content = span.text
            if text_content:
                texts.append(Text(text_content))

        return texts

    @staticmethod
    def __extract_page_names(document: Document) -> list[PageName]:
        body: Body = document.body
        pages: list[DrawPage] = body.get_draw_pages()
        page_names: list[PageName] = [PageName(page.name) for page in pages if page.name is not None]
        return page_names
