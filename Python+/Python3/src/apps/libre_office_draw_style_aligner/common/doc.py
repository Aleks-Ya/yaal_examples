from __future__ import annotations

from odfdo import Document, DrawFillImage, DrawMarker
from odfdo.style_base import StyleBase

from common.data_types import StyleDisplayName, FamilyName, StyleName
from common.stl import Stl


class Doc:
    def __init__(self, doc: Document) -> None:
        self.doc = doc

    def get_styles(self, family: FamilyName | None) -> list[Stl]:
        styles: list[StyleBase | DrawFillImage | DrawMarker] = self.doc.get_styles(family=family) \
            if family else self.doc.get_styles()
        base_styles: list[StyleBase] = [s for s in styles if isinstance(s, StyleBase)]
        return [Stl(style) for style in base_styles]

    def get_style(self, family: FamilyName, name_or_display_name: StyleName | StyleDisplayName) -> Stl:
        if self.is_style_exist_by_name(family, name_or_display_name):
            return self.get_style_by_name(family, name_or_display_name)
        elif self.is_style_exist_by_display_name(family, name_or_display_name):
            return self.get_style_by_display_name(family, name_or_display_name)
        else:
            raise ValueError(f"Style not found: '{family}/{name_or_display_name}' in '{self.doc.path}'")

    def get_style_by_display_name(self, family: FamilyName, display_name: StyleDisplayName) -> Stl:
        style: StyleBase | DrawFillImage | DrawMarker | None = self.doc.get_style(
            family=family, display_name=display_name)
        if style is None:
            raise ValueError(f"Style not found: {family}/{display_name}")
        if not isinstance(style, StyleBase):
            raise TypeError(f"Expected StyleBase, got {type(style)}")
        return Stl(style)

    def get_style_by_name(self, family: FamilyName, name: StyleName) -> Stl:
        style: StyleBase | DrawFillImage | DrawMarker | None = self.doc.get_style(
            family=family, name_or_element=name)
        if style is None:
            raise ValueError(f"Style not found: {family}/{name}")
        if not isinstance(style, StyleBase):
            raise TypeError(f"Expected StyleBase, got {type(style)}")
        return Stl(style)

    def get_parent_style(self, style: Stl) -> Stl:
        parent_style: StyleBase | DrawFillImage | DrawMarker | None = self.doc.get_parent_style(style.base())
        if parent_style is None:
            raise ValueError(f"Parent style not found for {style.get_name()}")
        if not isinstance(parent_style, StyleBase):
            raise TypeError(f"Expected StyleBase, got {type(parent_style)}")
        return Stl(parent_style)

    def is_style_exist_by_display_name(self, family: FamilyName, display_name: StyleDisplayName) -> bool:
        style: StyleBase | DrawFillImage | DrawMarker | None = self.doc.get_style(
            family=family, display_name=display_name)
        return style is not None

    def is_style_exist_by_name(self, family: FamilyName, name: StyleName) -> bool:
        style: StyleBase | DrawFillImage | DrawMarker | None = self.doc.get_style(
            family=family, name_or_element=name)
        return style is not None

    def has_parent_style(self, style: Stl) -> bool:
        return self.doc.get_parent_style(style.base()) is not None
