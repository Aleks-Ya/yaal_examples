import inspect
from inspect import FrameInfo
from pathlib import Path


def get_file_in_current_dir(filename: str) -> Path:
    caller_frame: FrameInfo = inspect.stack()[1]
    caller_file: str = caller_frame.filename
    return Path(caller_file).parent / filename

