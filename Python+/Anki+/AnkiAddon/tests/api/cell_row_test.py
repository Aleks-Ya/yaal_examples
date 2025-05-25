from aqt.browser import CellRow, Cell


def test_placeholder():
    cell_row: CellRow = CellRow.placeholder(2)
    exp_cells: tuple[Cell, Cell] = (
        Cell('...', False),
        Cell('...', False)
    )
    assert exp_cells == cell_row.cells


def test_generic():
    placeholder: str = "abc"
    cell_row: CellRow = CellRow.generic(2, placeholder)
    exp_cells: tuple[Cell, Cell] = (
        Cell(placeholder, False),
        Cell(placeholder, False)
    )
    assert exp_cells == cell_row.cells


def test_disabled():
    placeholder: str = "abc"
    cell_row: CellRow = CellRow.disabled(2, placeholder)
    assert cell_row.is_disabled
    exp_cells: tuple[Cell, Cell] = (
        Cell(placeholder, False),
        Cell(placeholder, False)
    )
    assert exp_cells == cell_row.cells
