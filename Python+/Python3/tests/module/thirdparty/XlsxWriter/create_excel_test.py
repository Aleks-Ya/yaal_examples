from zipfile import Path

from xlsxwriter import Workbook
from xlsxwriter.format import Format
from xlsxwriter.worksheet import Worksheet

from conftest import TempPath


def test_create_excel():
    file: Path = TempPath.temp_path_absent(suffix=".xlsx")
    workbook: Workbook = Workbook(file)
    worksheet: Worksheet = workbook.add_worksheet()
    worksheet.set_column("A:A", 20)
    bold: Format = workbook.add_format({"bold": True, 'align': 'center'})
    worksheet.write("A1", "Hello")
    worksheet.write("A2", "World", bold)
    worksheet.write(2, 0, 123)
    worksheet.write(3, 0, 123.456)
    workbook.close()


def test_cell_format():
    file: Path = TempPath.temp_path_absent(suffix=".xlsx")
    workbook: Workbook = Workbook(file)
    worksheet: Worksheet = workbook.add_worksheet()
    bold: Format = workbook.add_format({"bold": True})
    worksheet.write("A2", "World", bold)
    workbook.close()


def test_column_width():
    file: Path = TempPath.temp_path_absent(suffix=".xlsx")
    workbook: Workbook = Workbook(file)
    worksheet: Worksheet = workbook.add_worksheet()
    worksheet.set_column("A:A", width=20)
    worksheet.write("A1", "Hello Hello Hello Hello Hello Hello Hello Hello")
    worksheet.write("A2", "World World World")
    workbook.close()


def test_url():
    file: Path = TempPath.temp_path_absent(suffix=".xlsx")
    workbook: Workbook = Workbook(file)
    worksheet: Worksheet = workbook.add_worksheet()
    worksheet.write_url('A1', 'http://www.example.com', string='Example Link')
    workbook.close()
