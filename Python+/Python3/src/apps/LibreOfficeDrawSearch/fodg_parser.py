import xml.etree.ElementTree as ET
from dataclasses import dataclass
from xml.etree.ElementTree import Element, ElementTree

from data_types import FodgPath, Text, PageName


@dataclass
class FodgFileData:
    page_names: list[str]
    texts: list[Text]


class FodgParser:

    @staticmethod
    def parse(xml_file: FodgPath) -> FodgFileData:
        namespaces: dict[str, str] = FodgParser.__get_namespaces(xml_file)
        tree: ElementTree = ET.parse(xml_file)
        root: Element = tree.getroot()
        texts: list[Text] = FodgParser.__extract_texts(root, namespaces)
        page_names: list[str] = FodgParser.__extract_page_names(root, namespaces)
        return FodgFileData(page_names, texts)

    @staticmethod
    def __extract_texts(root: Element, namespaces: dict[str, str]) -> list[Text]:
        text_elements: list[Element] = root.findall('.//text:p', namespaces)
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
    def __get_namespaces(xml_file_path: FodgPath) -> dict[str, str]:
        namespaces: dict[str, str] = {}
        for event, elem in ET.iterparse(xml_file_path, events=['start-ns']):
            prefix, uri = elem
            namespaces[prefix] = uri
        return namespaces
