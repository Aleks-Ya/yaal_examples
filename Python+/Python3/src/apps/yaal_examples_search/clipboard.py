import pyperclip
from apps.yaal_examples_search.formatter.rich_tree_formatter import FormatResults, FormatResultId
from pathlib import Path
from typing import Optional


class Clipboard:

    @staticmethod
    def copy_path_to_clipboard(format_results: FormatResults) -> None:
        if len(format_results.results) == 0:
            return
        while True:
            try:
                selected_id: str = input("Enter the ID to copy full path to clipboard (or press Enter to exit): ")
                if selected_id.isdigit():
                    format_result_id: FormatResultId = FormatResultId(int(selected_id))
                    path: Optional[Path] = format_results.results.get(format_result_id)
                    if path:
                        copy_path: str = str(path.absolute())
                        print(f"Copying path: {copy_path}")
                        Clipboard.__copy(copy_path)
                    else:
                        print("Invalid ID selected.")
                else:
                    break
            except KeyboardInterrupt:
                print()
                break

    @staticmethod
    def __copy(text: str) -> None:
        try:
            pyperclip.copy(text)
        except pyperclip.PyperclipException:
            print("Could not access the clipboard. Install a backend for your session type "
                  "(check it with `echo $XDG_SESSION_TYPE`): "
                  "wayland -> 'sudo apt install wl-clipboard', x11 -> 'sudo apt install xclip'.")
