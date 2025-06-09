from xlsxwriter import Workbook
from xlsxwriter.format import Format
from xlsxwriter.worksheet import Worksheet


def test_cell_format(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    bold: Format = workbook.add_format({"bold": True})
    worksheet.write("A2", "World", bold)
    workbook.close()


def test_column_width(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    worksheet.set_column("A:A", width=20)
    worksheet.write_string("A1", "Hello Hello Hello Hello Hello Hello Hello Hello")
    worksheet.write_string("A2", "World World World")
    workbook.close()


def test_merge_range_string(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    worksheet.merge_range('A1:B1', 'GitHub')
    worksheet.write_string("A2", "Username")
    worksheet.write_string("B2", "Repository")
    workbook.close()


def test_merge_range_number(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    worksheet.merge_range(first_row=0, last_row=0, first_col=0, last_col=1, data="GitHub")
    worksheet.write_string(row=1, col=0, string="Username")
    worksheet.write_string(row=1, col=1, string="Repository")
    workbook.close()


def test_freeze_top_row(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    worksheet.write_string("A1", "Username")
    worksheet.write_string("B1", "Age")
    worksheet.freeze_panes(1, 0)
    row_number: int = 100
    for row in range(1, row_number):
        worksheet.write_string(row=row, col=0, string=f"John {row}")
        worksheet.write_number(row=row, col=1, number=30)
    workbook.close()
