from xlsxwriter.utility import xl_col_to_name, xl_rowcol_to_cell


def test_col_to_name():
    col_name_0: str = xl_col_to_name(0)
    assert col_name_0 == "A"

    col_name_26: str = xl_col_to_name(26)
    assert col_name_26 == "AA"


def test_rowcol_to_cell():
    cell_0_0: str = xl_rowcol_to_cell(0, 0)
    assert cell_0_0 == "A1"

    col_name_26_50: str = xl_rowcol_to_cell(50, 26)
    assert col_name_26_50 == "AA51"
