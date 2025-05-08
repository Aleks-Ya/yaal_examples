from pathlib import Path

from gherkin import Compiler, Parser
from gherkin.parser_types import GherkinDocument
from gherkin.pickles.compiler import Pickle, GherkinDocumentWithURI


def test_read_feature_file():
    file: Path = Path("hello.feature")
    feature: str = file.read_text()
    gherkin_document: GherkinDocument = Parser().parse(feature)
    gherkin_document_with_uri: GherkinDocumentWithURI = gherkin_document.copy()
    gherkin_document_with_uri["uri"] = str(file)
    pickles: list[Pickle] = Compiler().compile(gherkin_document_with_uri)
    print(pickles)
