from xlsxwriter import Workbook
from xlsxwriter.format import Format
from xlsxwriter.worksheet import Worksheet


def test_format_as_dict(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    cell_format: Format = workbook.add_format({"bold": True, "border": 5})
    worksheet.write("A2", "Hello", cell_format)
    worksheet.write("A3", "World", cell_format)
    workbook.close()


def test_bold(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    cell_format: Format = workbook.add_format()
    cell_format.set_bold()
    worksheet.write("A2", "World", cell_format)
    workbook.close()


def test_border(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    cell_format: Format = workbook.add_format()
    cell_format.set_border()
    worksheet.write("A2", "World", cell_format)
    workbook.close()


def test_right_column_border(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    column_format: Format = workbook.add_format()
    column_format.set_right()
    worksheet.set_column('A:A', None, column_format)
    worksheet.write("A2", "World", column_format)
    workbook.close()


def test_yellow_background(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    cell_format: Format = workbook.add_format({'bg_color': 'yellow'})
    worksheet.write("A2", "Hello", cell_format)
    worksheet.write("A3", "World", cell_format)
    workbook.close()
