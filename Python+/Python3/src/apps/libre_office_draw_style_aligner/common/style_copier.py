from __future__ import annotations

import logging

from odfdo import Document

from common.data_types import StyleDisplayName, FamilyName, OdgPath
from common.doc import Doc
from common.stl import Stl

logger = logging.getLogger(__name__)


class StyleCopier:

    @staticmethod
    def copy_style_document(src_document: Document, dest_document: Document, family: FamilyName,
                            display_name: StyleDisplayName) -> list[StyleDisplayName]:
        logger.info(f"Copying style: family={family}, display_name={display_name}")
        src: Doc = Doc(src_document)
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
                    src_document=src_document, dest_document=dest_document, family=parent_family,
                    display_name=parent_display_name)
                copied_styles.extend(copied)
        name: str | None = src_style.get_name()
        if name is None:
            raise ValueError(f"Style name is None: family={family}, display_name={display_name}")
        dest_document.insert_style(style=src_style.base().clone, name=name)
        copied_styles.append(display_name)
        logger.info(f"Style copied: family={family}, display_name={display_name}")
        return copied_styles

    @staticmethod
    def copy_style_file(src: OdgPath, dest: OdgPath, family: FamilyName,
                        display_names: list[StyleDisplayName]) -> list[StyleDisplayName]:
        src_document: Document = Document(src)
        dest_document: Document = Document(dest)
        copied_styles: list[StyleDisplayName] = []
        copied_styles_set: set[StyleDisplayName] = set()
        for display_name in display_names:
            copied: list[StyleDisplayName] = StyleCopier.copy_style_document(src_document, dest_document, family, display_name)
            for copied_style in copied:
                if copied_style in copied_styles_set:
                    continue
                copied_styles.append(copied_style)
                copied_styles_set.add(copied_style)
        dest_document.save(dest)
        return copied_styles

    @staticmethod
    def copy_style_files(src_path: OdgPath, dest_paths: list[OdgPath], family: FamilyName,
                         display_names: list[StyleDisplayName]) -> list[StyleDisplayName]:
        src_document: Document = Document(src_path)
        copied_styles: list[StyleDisplayName] = []
        copied_styles_set: set[StyleDisplayName] = set()
        for dest in dest_paths:
            dest_document: Document = Document(dest)
            for display_name in display_names:
                copied: list[StyleDisplayName] = StyleCopier.copy_style_document(
                    src_document, dest_document, family, display_name)
                for copied_style in copied:
                    if copied_style in copied_styles_set:
                        continue
                    copied_styles.append(copied_style)
                    copied_styles_set.add(copied_style)
            dest_document.save(dest)
        return copied_styles
