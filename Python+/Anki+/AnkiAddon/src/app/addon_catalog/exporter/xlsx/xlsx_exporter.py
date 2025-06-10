from pathlib import Path

from xlsxwriter import Workbook
from xlsxwriter.format import Format
from xlsxwriter.worksheet import Worksheet

from app.addon_catalog.common.data_types import AddonDetails


class XlsxExporter:
    __header_row_top: int = 0
    __header_row_bottom: int = 1
    __id_col: int = 0
    __title_col: int = 1
    __rating_col: int = 2
    __anki_web_url_col: int = 3
    __anki_forum_url_col: int = 4
    __stars_col: int = 5
    __updated_col: int = 6
    __last_commit_col: int = 7
    __github_url_col: int = 8
    __languages_col: int = 9
    __actions_count_col: int = 10
    __tests_count_col: int = 10

    def __init__(self, output_dir: Path):
        self.output_dir: Path = output_dir
        self.output_dir.mkdir(parents=True, exist_ok=True)

    def export(self, details_list: list[AddonDetails]):
        output_file: Path = self.output_dir / "anki-addon-catalog.xlsx"
        workbook: Workbook = Workbook(output_file)
        worksheet: Worksheet = workbook.add_worksheet(name="Addons")
        self.__set_column_width(worksheet)
        self.__add_header(workbook, worksheet)
        self.__add_rows(details_list, worksheet)
        workbook.close()
        print(f"Write XLSX to file: {output_file}")

    @staticmethod
    def __set_column_width(worksheet: Worksheet) -> None:
        worksheet.set_column(XlsxExporter.__id_col, XlsxExporter.__id_col, 12)
        worksheet.set_column(XlsxExporter.__title_col, XlsxExporter.__title_col, 80)
        worksheet.set_column(XlsxExporter.__rating_col, XlsxExporter.__rating_col, 10)
        worksheet.set_column(XlsxExporter.__anki_web_url_col, XlsxExporter.__anki_web_url_col, 10)
        worksheet.set_column(XlsxExporter.__anki_forum_url_col, XlsxExporter.__anki_forum_url_col, 8)
        worksheet.set_column(XlsxExporter.__stars_col, XlsxExporter.__stars_col, 8)
        worksheet.set_column(XlsxExporter.__updated_col, XlsxExporter.__updated_col, 10)
        worksheet.set_column(XlsxExporter.__last_commit_col, XlsxExporter.__last_commit_col, 13)
        worksheet.set_column(XlsxExporter.__github_url_col, XlsxExporter.__github_url_col, 8)
        worksheet.set_column(XlsxExporter.__languages_col, XlsxExporter.__languages_col, 50)
        worksheet.set_column(XlsxExporter.__actions_count_col, XlsxExporter.__actions_count_col, 10)
        worksheet.set_column(XlsxExporter.__tests_count_col, XlsxExporter.__tests_count_col, 8)

    @staticmethod
    def __add_header(workbook: Workbook, worksheet: Worksheet) -> None:
        header_format: Format = workbook.add_format({"bold": True, 'align': 'center'})

        worksheet.merge_range(data="Anki Web", cell_format=header_format,
                              first_row=XlsxExporter.__header_row_top, last_row=XlsxExporter.__header_row_top,
                              first_col=XlsxExporter.__id_col, last_col=XlsxExporter.__anki_web_url_col)
        worksheet.write_string(XlsxExporter.__header_row_bottom, XlsxExporter.__id_col, "ID", header_format)
        worksheet.write_string(XlsxExporter.__header_row_bottom, XlsxExporter.__title_col, "Title", header_format)
        worksheet.write_string(XlsxExporter.__header_row_bottom, XlsxExporter.__rating_col, "Rating", header_format)
        worksheet.write_string(XlsxExporter.__header_row_bottom, XlsxExporter.__anki_web_url_col, "Page",
                               header_format)

        worksheet.write_string(XlsxExporter.__header_row_top, XlsxExporter.__anki_forum_url_col,
                               "Forum", header_format)
        worksheet.write_string(XlsxExporter.__header_row_bottom, XlsxExporter.__anki_forum_url_col,
                               "Page", header_format)

        worksheet.merge_range(data="GitHub", cell_format=header_format,
                              first_row=XlsxExporter.__header_row_top, last_row=XlsxExporter.__header_row_top,
                              first_col=XlsxExporter.__stars_col, last_col=XlsxExporter.__languages_col)
        worksheet.write_string(XlsxExporter.__header_row_bottom, XlsxExporter.__stars_col, "Stars",
                               header_format)
        worksheet.write_string(XlsxExporter.__header_row_bottom, XlsxExporter.__updated_col, "Updated", header_format)
        worksheet.write_string(XlsxExporter.__header_row_bottom, XlsxExporter.__last_commit_col,
                               "Last commit", header_format)
        worksheet.write_string(XlsxExporter.__header_row_bottom, XlsxExporter.__github_url_col, "Repo", header_format)
        worksheet.write_string(XlsxExporter.__header_row_bottom, XlsxExporter.__languages_col, "Languages",
                               header_format)
        worksheet.write_string(XlsxExporter.__header_row_bottom, XlsxExporter.__actions_count_col, "Actions",
                               header_format)
        worksheet.write_string(XlsxExporter.__header_row_bottom, XlsxExporter.__tests_count_col, "Tests",
                               header_format)

        worksheet.freeze_panes(XlsxExporter.__header_row_bottom + 1, XlsxExporter.__id_col)
        worksheet.autofilter(first_row=XlsxExporter.__header_row_bottom, last_row=XlsxExporter.__header_row_bottom,
                             first_col=XlsxExporter.__id_col, last_col=XlsxExporter.__tests_count_col)

    @staticmethod
    def __add_rows(details_list: list[AddonDetails], worksheet: Worksheet) -> None:
        for i, addon in enumerate(details_list):
            row: int = i + XlsxExporter.__header_row_bottom + 1
            worksheet.write_number(row, XlsxExporter.__id_col, addon.header.id)
            worksheet.write_string(row, XlsxExporter.__title_col, addon.header.title)
            worksheet.write_number(row, XlsxExporter.__rating_col, addon.header.rating)
            worksheet.write_url(row, XlsxExporter.__anki_web_url_col, addon.header.addon_page, string='link')
            if addon.anki_forum_url:
                worksheet.write_url(row, XlsxExporter.__anki_forum_url_col, addon.anki_forum_url, string='link')
            if addon.stars:
                worksheet.write_number(row, XlsxExporter.__stars_col, addon.stars)
            worksheet.write_string(row, XlsxExporter.__updated_col, addon.header.update_date)
            worksheet.write_string(row, XlsxExporter.__last_commit_col,
                                   addon.last_commit.strftime("%Y-%m-%d") if addon.last_commit else "")
            if addon.github_repo:
                worksheet.write_url(row, XlsxExporter.__github_url_col, addon.github_repo.get_url(), string='link')
            languages_str: str = ", ".join(addon.languages)
            worksheet.write_string(row, XlsxExporter.__languages_col, languages_str)
            if addon.action_count:
                worksheet.write_number(row, XlsxExporter.__actions_count_col, addon.action_count)
            if addon.tests_count:
                worksheet.write_number(row, XlsxExporter.__tests_count_col, addon.tests_count)
