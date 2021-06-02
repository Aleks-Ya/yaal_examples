# Convert MTS video files to MP4 with "ffmpeg" tool.
import os
import subprocess
import sys
from pathlib import Path
from subprocess import DEVNULL
from typing import List, Dict


def parse_bool(s: str) -> bool:
    return s.lower() in ("yes", "true", "t", "1")


def check_dir_exits(directory: str):
    if not os.path.isdir(directory):
        raise IOError(f"Directory absents: {directory}")


def check_ffmpeg_availability():
    completed_process = subprocess.run(['ffmpeg', '-version'], stdout=DEVNULL, stderr=DEVNULL)
    exit_code = completed_process.returncode
    if exit_code != 0:
        raise IOError("ffmpeg is not found")


def find_mts_files(src_dir_arg: str):
    mts_files_result = []
    for subdir, dirs, files in os.walk(src_dir_arg):
        for file in files:
            if file.lower().endswith("mts"):
                mts_files_result.append(os.path.join(subdir, file))
    return mts_files_result


def compose_dest_file_names(dest_dir_arg: str, mts_files_arg: List[str]):
    result: Dict[str, str] = {}
    for mts in mts_files_arg:
        p: Path = Path(mts)
        name: str = p.stem + ".mp4"
        dest: Path = Path(dest_dir_arg, name)
        result[mts] = str(dest)
    return result


def convert_file(mts_file: str, dest_file: str):
    print(f"Converting '{mts_file}' to '{dest_file}'...")
    completed_process = subprocess.run(['ffmpeg', '-i', mts_file, '-vcodec', 'copy', '-an', dest_file],
                                       stdout=DEVNULL, stderr=DEVNULL)
    exit_code = completed_process.returncode
    if exit_code != 0:
        raise IOError("ffmpeg error")


def convert_files(mts_dict: Dict[str, str]):
    for mts_file, dest_file in mts_dict.items():
        convert_file(mts_file, dest_file)


src_dir: str = sys.argv[1]
dest_dir: str = sys.argv[2]
delete_src_files: bool = parse_bool(sys.argv[3])

print(f"Source directory: {src_dir}")
print(f"Destination directory: {dest_dir}")
print(f"Delete source files: {delete_src_files}")
check_dir_exits(src_dir)
check_dir_exits(dest_dir)
check_ffmpeg_availability()
mts_files = find_mts_files(src_dir)
print(mts_files)
dest_files = compose_dest_file_names(dest_dir, mts_files)
print(dest_files)
convert_files(dest_files)
print("Done.")
