from __future__ import annotations

from odfdo import Document, DrawFillImage, DrawMarker
from odfdo.style_base import StyleBase

from common.data_types import FamilyName, StyleName, StyleDisplayName


class StyleRenamer:

    @staticmethod
    def rename_style(doc: Document, family: FamilyName, old_style_display_name: StyleDisplayName,
                     new_style_name: StyleName, new_style_display_name: StyleDisplayName) -> None:
        style: StyleBase | DrawFillImage | DrawMarker | None = doc.get_style(
            family=family, display_name=old_style_display_name)
        if style is None:
            raise ValueError(f"Style not found: family={family}, display_name={old_style_display_name}")
        assert style is not None
        print(f"Current family: {style.get_attribute_string('style:family')}")
        print(f"Current name: {style.get_attribute_string('style:name')}")
        print(f"Current display name: {style.get_attribute_string('style:display-name')}")

        style.set_style_attribute('style:name', new_style_name)
        style.set_style_attribute('style:display-name', new_style_display_name)

        print(f"New family: {style.get_attribute_string('style:family')}")
        print(f"New name: {style.get_attribute_string('style:name')}")
        print(f"New display name: {style.get_attribute_string('style:display-name')}")

    @staticmethod
    def rename_style_display_name(doc: Document, family: FamilyName, old_style_display_name: StyleDisplayName,
                                  new_style_display_name: StyleDisplayName) -> None:
        style: StyleBase | DrawFillImage | DrawMarker | None = doc.get_style(
            family=family, display_name=old_style_display_name)
        if style is None:
            raise ValueError(f"Style not found: family={family}, display_name={old_style_display_name}")
        assert style is not None
        print(f"Current family: {style.get_attribute_string('style:family')}")
        print(f"Current name: {style.get_attribute_string('style:name')}")
        print(f"Current display name: {style.get_attribute_string('style:display-name')}")

        style.set_style_attribute('style:display-name', new_style_display_name)

        print(f"New family: {style.get_attribute_string('style:family')}")
        print(f"New name: {style.get_attribute_string('style:name')}")
        print(f"New display name: {style.get_attribute_string('style:display-name')}")

    @staticmethod
    def rename_style_name(doc: Document, family: FamilyName, old_name: StyleName, new_name: StyleName):
        """Rename a style and update all elements that reference it."""

        # 1. Get the style
        style = doc.get_style(family=family, name_or_element=old_name)
        if style is None:
            raise ValueError(f"Style '{old_name}' not found in family '{family}'")

        # 2. Rename the style itself
        style.set_style_attribute('style:name', new_name)

        # 3. Update all references in the document
        # For each element that uses this style, update the reference
        for element in doc.body.get_elements("//*[@style:style]"):
            # Check for style:style attribute (paragraph, character, etc. styles)
            if element.get_attribute('style:style') == old_name:
                element.set_attribute('style:style', new_name)

            # Check for draw:style attribute (drawing objects)
            if element.get_attribute('draw:style') == old_name:
                element.set_attribute('draw:style', new_name)

        # 4. Update style inheritance (parent styles)
        for style_elem in doc.body.get_elements("//*[@style:parent-style-name]"):
            parent_name = style_elem.get_attribute_string('style:parent-style-name')
            if parent_name == old_name:
                style_elem.set_style_attribute('style:parent-style-name', new_name)

        doc.save()
