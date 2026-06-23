import os
from pathlib import Path
import logging

from odfdo import Document

from common.data_types import OdgPath, FamilyName, StyleDisplayName
from common.doc import Doc
from common.stl import Stl
from common.style_copier import StyleCopier

logging.basicConfig(level=logging.INFO)

src_template: OdgPath = OdgPath(Path("/home/aleks/DocsVault/LibreOfficeDraw/templates/SchematizationTemplate.otg"))
family: FamilyName = FamilyName('graphic')
src_doc: Doc = Doc(Document(src_template))
all_styles: list[Stl] = src_doc.get_styles(family)
my_display_name_prefixes: list[str] = ['Image:', 'Line:', 'Page:', 'Text:']
my_styles: list[Stl] = [s for s in all_styles
                        if any(s.has_display_name() and s.get_display_name().startswith(prefix)
                               for prefix in my_display_name_prefixes)]
my_styles_display_names: list[StyleDisplayName] = [s.get_display_name() for s in my_styles]
print(f"Styles to copy ({len(my_styles)}): {my_styles_display_names}")

dest_dir: Path = Path("/home/aleks/tmp/draw_copy_styles/out1/LibreOfficeDraw")
dest_files: list[OdgPath] = [OdgPath(Path(root)) / file for root, _, files in os.walk(dest_dir)
                             for file in files if file.endswith('.odg')]
print(f"Dest files to copy ({len(dest_files)}): {[str(f) for f in dest_files]}")

StyleCopier.copy_style_files(src_template, dest_files, family, my_styles_display_names)
