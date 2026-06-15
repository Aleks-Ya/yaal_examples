from __future__ import annotations

from dataclasses import dataclass
from pathlib import Path
from typing import Dict, List, NewType

StyleName = NewType("StyleName", str)
StyleDisplayName = NewType("StyleDisplayName", str)
FamilyName = NewType("FamilyName", str)
OdgPath = NewType("OdgPath", Path)
StylesHierarchyStr = NewType("StylesHierarchyStr", str)


@dataclass
class StyleInfo:
    name: StyleName | None
    display_name: StyleDisplayName | None
    family: FamilyName | None
    parent: StyleInfo | None
    custom: bool


OdgStyles = NewType("OdgStyles", Dict[FamilyName, List[StyleInfo]])
