from pathlib import Path
import logging

from odfdo import Document

logging.basicConfig(level=logging.INFO)

src: Path = Path("/home/aleks/DocsVault/LibreOfficeDraw/templates/SchematizationTemplate.otg")
doc: Document = Document(src)
styles_str: str = doc.show_styles(properties=False, automatic=False, common=True)
print()
print(styles_str)
