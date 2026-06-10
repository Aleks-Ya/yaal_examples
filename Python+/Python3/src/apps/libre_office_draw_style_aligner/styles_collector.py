from __future__ import annotations

from pathlib import Path
from typing import Dict
import logging

from apps.libre_office_draw_style_aligner.data_types import OdgPath, OdgStyles
from apps.libre_office_draw_style_aligner.style_collector import StyleCollector

logger = logging.getLogger(__name__)


class StylesCollector:
    """Collect styles from all .odg files under a root folder.

    Usage:
        sc = StylesCollector()
        result = sc.collect_from_root(Path("/some/dir"))

    Returns:
        Dict[Path, OdgStyles]: mapping from odg Path to styles grouped by family
    """

    def collect_from_root(self, root: Path) -> Dict[OdgPath, OdgStyles]:
        if not isinstance(root, Path):
            root = Path(root)

        if not root.exists():
            raise FileNotFoundError(f"Root path does not exist: {root}")

        collector = StyleCollector()
        result: Dict[OdgPath, OdgStyles] = {}

        for odg_path in root.rglob("*.odg"):
            try:
                if not odg_path.is_file():
                    continue
                odg: OdgPath = OdgPath(odg_path)
                styles_by_family = collector.collect_styles(odg)
                result[odg] = styles_by_family
            except Exception as exc:  # pragma: no cover - defensive logging
                logger.exception("Failed to collect styles from %s: %s", odg_path, exc)

        return result


__all__ = ["StylesCollector"]
