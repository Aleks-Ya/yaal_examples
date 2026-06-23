from pathlib import Path

from tabulate import tabulate
from odfdo import Document, DrawMarker, DrawFillImage
from odfdo.style_base import StyleBase

src: Path = Path("/home/aleks/DocsVault/LibreOfficeDraw/templates/SchematizationTemplate.otg")
doc: Document = Document(src)
styles: list[StyleBase | DrawFillImage | DrawMarker] = doc.get_styles(family='graphic')
data = []
for style in styles:
    name: str | None = style.get_attribute_string('style:name')
    display_name: str | None = style.get_attribute_string('style:display-name')
    if display_name is not None:
        data.append([display_name, name])
print(tabulate(data, headers=["Display Name", "Name"]))
