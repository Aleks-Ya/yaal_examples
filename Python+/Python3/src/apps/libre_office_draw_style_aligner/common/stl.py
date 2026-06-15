from __future__ import annotations

from odfdo.style_base import StyleBase, PropDict

from common.data_types import StyleDisplayName, StyleName, FamilyName


class Stl:
    def __init__(self, style: StyleBase) -> None:
        self.style = style

    def base(self):
        return self.style

    def get_family(self) -> FamilyName:
        family: str | None = self.style.get_attribute_string('style:family')
        if family is None:
            raise ValueError("Style family not found")
        return FamilyName(family)

    def get_name(self) -> StyleName:
        name: str | None = self.style.get_attribute_string('style:name')
        if name is None:
            raise ValueError("Style name not found")
        return StyleName(name)

    def get_display_name(self) -> StyleDisplayName:
        display_name: str | None = self.style.get_attribute_string('style:display-name')
        if display_name is None:
            raise ValueError("Style display name not found")
        return StyleDisplayName(display_name)

    def get_properties(self) -> PropDict:
        properties: PropDict | None = self.style.get_properties()
        if properties is None:
            raise ValueError("Style properties not found")
        return properties

    def has_display_name(self) -> bool:
        return self.style.get_attribute_string('style:display-name') is not None

    def is_custom(self) -> bool:
        is_standard: bool = self.get_name() == 'standard'
        return not is_standard

    def __str__(self) -> str:
        return self.__repr__()

    def __repr__(self) -> str:
        return f"Stl({self.get_family()}-{self.get_display_name()})"
