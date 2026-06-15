from pathlib import Path
import os
import logging
from typing import List

from common.data_types import OdgPath, FamilyName, StyleDisplayName
from common.style_copier import StyleCopier

logging.basicConfig(level=logging.INFO)

src: OdgPath = OdgPath(
    Path("/home/aleks/tmp/draw_copy_styles/out1/LibreOfficeDraw/templates/SchematizationTemplate.otg"))
dest_dir: Path = Path("/home/aleks/tmp/draw_copy_styles/out1/LibreOfficeDraw")
family: FamilyName = FamilyName('graphic')
display_names: List[StyleDisplayName] = [
    StyleDisplayName('Text: Dash: top, many'),
    StyleDisplayName('Text: Dash: fit, many')
]

odg_files: List[OdgPath] = [OdgPath(Path(root)) / file for root, _, files in os.walk(dest_dir)
                            for file in files if file.endswith('.odg')]

for dest in odg_files:
    StyleCopier.copy_style_file(src, dest, family, display_names)
