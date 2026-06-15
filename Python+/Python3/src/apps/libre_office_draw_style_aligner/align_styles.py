from pathlib import Path

from common.data_types import OdgPath, StylesHierarchyStr, OdgStyles
from common.style_collector import StyleCollector
from common.styles_printer import StylesPrinter

style_collector: StyleCollector = StyleCollector()
odg: OdgPath = OdgPath(Path("/home/aleks/tmp/GraphML.odg"))
styles: OdgStyles = style_collector.collect_styles(odg)

styles_printer: StylesPrinter = StylesPrinter()
hierarchy: StylesHierarchyStr = styles_printer.format_styles(styles)
print(hierarchy)
