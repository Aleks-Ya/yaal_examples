import xml.etree.ElementTree as ET
from dataclasses import dataclass
from zipfile import ZipFile
from xml.etree.ElementTree import Element, ElementTree

from apps.libre_office_draw_search.data_types import OdgPath, Text, PageName


@dataclass
class OdgFileData:
    page_names: list[str]
    texts: list[Text]


class OdgParser:

    @staticmethod
    def parse(odg_file: OdgPath) -> OdgFileData:
        with ZipFile(odg_file) as odg_zip:
            with odg_zip.open('content.xml') as content_xml:
                namespaces: dict[str, str] = OdgParser.__get_namespaces(content_xml)
            with odg_zip.open('content.xml') as content_xml:
                tree: ElementTree = ET.parse(content_xml)

        root: Element = tree.getroot()
        texts: list[Text] = OdgParser.__extract_texts(root, namespaces)
        page_names: list[str] = OdgParser.__extract_page_names(root, namespaces)
        return OdgFileData(page_names, texts)

    @staticmethod
    def __extract_texts(root: Element, namespaces: dict[str, str]) -> list[Text]:
        p_elements: list[Element] = root.findall('.//text:p', namespaces)
        snap_elements2: list[Element] = root.findall('.//text:span', namespaces)
        text_elements: list[Element] = p_elements + snap_elements2
        texts: list[Text] = [Text(element.text) for element in text_elements if element.text is not None]
        return texts

    @staticmethod
    def __extract_page_names(root, namespaces) -> list[PageName]:
        pages: list[Element] = root.findall('.//draw:page', namespaces)
        namespace: str = namespaces['draw']
        attribute_name: str = f'{{{namespace}}}name'
        page_names: list[PageName] = [PageName(page.get(attribute_name)) for page in pages]
        return page_names

    @staticmethod
    def __get_namespaces(content_xml) -> dict[str, str]:
        namespaces: dict[str, str] = {}
        for event, elem in ET.iterparse(content_xml, events=['start-ns']):
            prefix, uri = elem
            namespaces[prefix] = uri
        return namespaces
