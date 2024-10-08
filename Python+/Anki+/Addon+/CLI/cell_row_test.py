import tempfile
import unittest

from anki.collection import Collection
from aqt.browser import CellRow, Cell


class TestCellRow(unittest.TestCase):
    def setUp(self):
        self.col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])

    def test_placeholder(self):
        cell_row: CellRow = CellRow.placeholder(2)
        exp_cells: tuple[Cell, Cell] = (
            Cell('...', False),
            Cell('...', False)
        )
        self.assertTupleEqual(exp_cells, cell_row.cells)

    def test_generic(self):
        placeholder: str = "abc"
        cell_row: CellRow = CellRow.generic(2, placeholder)
        exp_cells: tuple[Cell, Cell] = (
            Cell(placeholder, False),
            Cell(placeholder, False)
        )
        self.assertTupleEqual(exp_cells, cell_row.cells)

    def test_disabled(self):
        placeholder: str = "abc"
        cell_row: CellRow = CellRow.disabled(2, placeholder)
        self.assertTrue(cell_row.is_disabled)
        exp_cells: tuple[Cell, Cell] = (
            Cell(placeholder, False),
            Cell(placeholder, False)
        )
        self.assertTupleEqual(exp_cells, cell_row.cells)

    def tearDown(self):
        self.col.close()


if __name__ == '__main__':
    unittest.main()
