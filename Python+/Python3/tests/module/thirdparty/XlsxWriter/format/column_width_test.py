from xlsxwriter import Workbook
from xlsxwriter.worksheet import Worksheet


def test_column_width(workbook: Workbook):
    worksheet: Worksheet = workbook.add_worksheet()
    worksheet.set_column("A:A", width=20)
    worksheet.write_string("A1", "Hello Hello Hello Hello Hello Hello Hello Hello")
    worksheet.write_string("A2", "World World World")
    workbook.close()
