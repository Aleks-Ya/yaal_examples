from __future__ import annotations

from odfdo import Document, DrawFillImage, DrawMarker, StyleMasterPage, StylePageLayout
from odfdo.style_base import StyleBase

from common.data_types import StyleInfo, FamilyName, OdgPath, OdgStyles, StyleName, StyleDisplayName


class StyleCollector:
    """Collect styles from a LibreOffice Draw (.odg) file using odfdo."""

    def collect_styles(self, odg_path: OdgPath) -> OdgStyles:
        if not odg_path.exists():
            raise FileNotFoundError(f"ODG file not found: {odg_path}")
        doc: Document = Document(str(odg_path))
        styles_map: OdgStyles = OdgStyles({})
        style_pairs: list[tuple[object, StyleInfo]] = []
        styles: list[StyleBase | DrawFillImage | DrawMarker] = doc.get_styles()
        base_styles: list[StyleBase] = [s for s in styles if isinstance(s, StyleBase)]
        for style in base_styles:
            si: StyleInfo | None = self._create_style_info(style, doc)
            if si:
                key: FamilyName = FamilyName(si.family or "unknown")
                styles_map.setdefault(key, [])
                existing: list[StyleInfo] = styles_map[key]
                if si.name is not None and any(s.name == si.name for s in existing):
                    continue
                existing.append(si)
                style_pairs.append((style, si))
        return styles_map

    def _create_style_info(self, style: StyleBase | None, doc: Document) -> StyleInfo | None:
        if style is None:
            return None
        family: str | None = style.get_attribute_string('style:family')
        name: str | None = style.get_attribute_string('style:name')
        display_name: str | None = style.get_attribute_string('style:display-name')
        parent_info: StyleInfo | None = self.__get_parent_style(style, doc)

        return StyleInfo(name=StyleName(name) if name else None,
                         display_name=StyleDisplayName(display_name) if display_name else None,
                         family=FamilyName(family) if family else None,
                         parent=parent_info)

    def __get_parent_style(self, style: StyleBase | None, doc: Document) -> StyleInfo | None:
        if (style is None or
                not isinstance(style, StyleBase) or
                isinstance(style, StylePageLayout) or
                isinstance(style, StyleMasterPage)):
            return None
        parent: StyleBase | DrawFillImage | DrawMarker | None = doc.get_parent_style(style)
        parent_base: StyleBase | None = parent if isinstance(parent, StyleBase) else None
        parent_info: StyleInfo | None = self._create_style_info(parent_base, doc) if parent else None
        return parent_info


__all__ = ["StyleCollector", "StyleInfo"]
