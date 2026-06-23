from __future__ import annotations

import logging

from odfdo import Document

from common.data_types import StyleDisplayName, FamilyName, OdgPath
from common.doc import Doc
from common.stl import Stl

logger = logging.getLogger(__name__)


class StyleCopier:

    @staticmethod
    def copy_style_document(src_doc: Document, dest_doc: Document, family: FamilyName,
                            display_name: StyleDisplayName) -> list[StyleDisplayName]:
        logger.info(f"Copying style: family={family}, display_name={display_name}")
        src: Doc = Doc(src_doc)
        src_style: Stl = src.get_style(family, display_name)
        if src_style is None:
            raise ValueError(f"Style not found: family='{family}', display_name='{display_name}'")

        copied_styles: list[StyleDisplayName] = []
        if src.has_parent_style(src_style):
            parent_style: Stl = src.get_parent_style(src_style)
            if parent_style.is_custom():
                parent_family: FamilyName = parent_style.get_family()
                parent_display_name: StyleDisplayName = parent_style.get_display_name()
                logger.info(f"Copying parent style: {parent_display_name}")
                copied: list[StyleDisplayName] = StyleCopier.copy_style_document(
                    src_doc=src_doc, dest_doc=dest_doc, family=parent_family, display_name=parent_display_name)
                copied_styles.extend(copied)
        name: str | None = src_style.get_name()
        if name is None:
            raise ValueError(f"Style name is None: family={family}, display_name={display_name}")
        dest_doc.insert_style(style=src_style.base(), name=name)
        copied_styles.append(display_name)
        logger.info(f"Style copied: family={family}, display_name={display_name}")
        return copied_styles

    @staticmethod
    def copy_style_file(src: OdgPath, dest: OdgPath, family: FamilyName,
                        display_names: list[StyleDisplayName]) -> list[StyleDisplayName]:
        src_doc: Document = Document(src)
        dest_doc: Document = Document(dest)
        copied_styles: list[StyleDisplayName] = []
        for display_name in display_names:
            copied: list[StyleDisplayName] = StyleCopier.copy_style_document(src_doc, dest_doc, family, display_name)
            copied_styles.extend(copied)
        dest_doc.save(dest)
        return copied_styles

    @staticmethod
    def copy_style_files(src_path: OdgPath, dest_paths: list[OdgPath], family: FamilyName,
                         display_names: list[StyleDisplayName]) -> list[StyleDisplayName]:
        src_doc: Document = Document(src_path)
        copied_styles: list[StyleDisplayName] = []
        for dest in dest_paths:
            dest_doc: Document = Document(dest)
            for display_name in display_names:
                copied: list[StyleDisplayName] = StyleCopier.copy_style_document(
                    src_doc, dest_doc, family, display_name)
                copied_styles.extend(copied)
            dest_doc.save(dest)
        return copied_styles
