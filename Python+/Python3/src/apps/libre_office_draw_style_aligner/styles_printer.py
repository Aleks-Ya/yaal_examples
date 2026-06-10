from dataclasses import dataclass
from rich.console import Console
from rich.tree import Tree

from .data_types import OdgStyles, StylesHierarchyStr


@dataclass
class StylesPrinter:
    """Prints ODG styles hierarchy using rich Tree for a nicer output.

    The output groups styles by family. For each style we show the
    display name (or <no-display-name>), the internal name (or <unnamed>)
    and the parent chain (following the StyleInfo.parent links).
    """

    def format_styles(self, styles: OdgStyles) -> StylesHierarchyStr:
        """Return a human-readable (rich-rendered) representation of styles.

        We render into a textual tree using rich and return the captured
        plain-text representation wrapped in StylesHierarchyStr.
        """
        if not styles:
            return StylesHierarchyStr("")

        root = Tree("Styles")

        # styles is a mapping from FamilyName to list[StyleInfo]
        for family in sorted(styles.keys(), key=lambda f: str(f)):
            fam_node = root.add(f"Family: {family}")
            family_styles = styles.get(family) or []

            # sort styles by display name then by name for stable ordering
            def sort_key(si):
                dn = si.display_name or ""
                nm = si.name or ""
                return (str(dn), str(nm))

            for si in sorted(family_styles, key=sort_key):
                name = str(si.name) if si.name else "<unnamed>"
                display = str(si.display_name) if si.display_name else "<no-display-name>"

                # create node for this style
                style_node = fam_node.add(f"{display} ({name})")

                # Follow the parent chain using StyleInfo.parent and render as nested nodes
                p = si.parent
                prev = style_node
                while p is not None:
                    pname = (
                        str(p.display_name)
                        if p.display_name
                        else (str(p.name) if p.name else "<unnamed>")
                    )
                    prev = prev.add(f"parent: {pname}")
                    p = p.parent

        console = Console(record=True, width=120)
        console.print(root)
        rendered = console.export_text()
        return StylesHierarchyStr(rendered)
