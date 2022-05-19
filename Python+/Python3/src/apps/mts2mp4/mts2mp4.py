# Convert MTS video files to MP4 with "ffmpeg" tool.
# Run (remove audio): "python3 mts2mp4.py /media/JVCCAM_SD /home/aleks/tmp"
# Run (preserve audio): "python3 mts2mp4.py /media/JVCCAM_SD /home/aleks/tmp --preserve-audio"
import datetime
import os
import shutil
import subprocess
import sys
from pathlib import Path
from subprocess import DEVNULL
from typing import List, Dict, Optional


class FileData:
    def __init__(self, src_file: Path, dest_file: Path, src_file_size: int):
        self.src_file = src_file
        self.dest_file = dest_file
        self.src_file_size = src_file_size

    def __repr__(self):
        size = str(round(self.src_file_size / 1000 / 1000)) + 'Mb'
        return f'FileData[src_file={self.src_file}, dest_file={self.dest_file}, src_file_size={size}]'


class FilesData:
    def __init__(self, file_data_list: List[FileData]):
        self.file_data_list = file_data_list
        self.src_file_size_total = 0
        for file_data in file_data_list:
            self.src_file_size_total = self.src_file_size_total + file_data.src_file_size

    def get_file_size_percent(self, file_data: FileData) -> float:
        return file_data.src_file_size / self.src_file_size_total

    def __repr__(self):
        return str(self.file_data_list)


def parse_bool(s: str) -> bool:
    return s.lower() in ("yes", "true", "t", "1")


def check_dir_exits(directory: Path):
    if not directory.exists():
        raise IOError(f"Directory absents: {directory}")


def check_ffmpeg_availability():
    completed_process = subprocess.run(['ffmpeg', '-version'], stdout=DEVNULL, stderr=DEVNULL)
    exit_code = completed_process.returncode
    if exit_code != 0:
        raise IOError("ffmpeg is not found")


def compose_dest_file_name(dest_dir_arg: Path, mts_file: Path, modified: datetime.date) -> Path:
    name: str = mts_file.stem + ".mp4"
    parent = str(modified)
    return Path(dest_dir_arg, parent, name)


def find_mts_files(src_dir_arg: Path, dest_dir_arg: Path) -> FilesData:
    file_data_list: List[FileData] = []
    for subdir, dirs, files in os.walk(src_dir_arg):
        for file in files:
            if file.lower().endswith("mts"):
                src_file = Path(subdir, file)
                modified = datetime.date.fromtimestamp(os.path.getmtime(Path(subdir, file)))
                dest_file = compose_dest_file_name(dest_dir_arg, src_file, modified)
                src_file_size = os.path.getsize(src_file)
                file_data = FileData(src_file, dest_file, src_file_size)
                file_data_list.append(file_data)
    return FilesData(file_data_list)


def compose_dest_file_names(dest_dir_arg: Path, mts_files_arg: List[str]):
    result: Dict[str, str] = {}
    for mts in mts_files_arg:
        p: Path = Path(mts)
        name: str = p.stem + ".mp4"
        dest: Path = Path(dest_dir_arg, name)
        result[mts] = str(dest)
    return result


def format_size_percent(size_percent: float):
    return str(round(size_percent * 100)) + '%'


def convert_file(file_data: FileData, file_size_percent: float, finished_percent: float):
    src_file: Path = file_data.src_file
    dest_file: Path = file_data.dest_file
    create_dir(dest_file.parent)
    percent: str = format_size_percent(file_size_percent)
    finished_percent_str: str = format_size_percent(finished_percent)
    print(f"Converting '{src_file}' to '{dest_file}' ({percent}, total done {finished_percent_str})...")
    command = ['ffmpeg', '-i', str(src_file), '-vcodec', 'copy']
    if preserve_audio:
        command.append('-c:a')
        command.append('copy')
    else:
        command.append('-an')
    command.append(str(dest_file))
    completed_process = subprocess.run(command, stdout=DEVNULL, stderr=DEVNULL)
    exit_code = completed_process.returncode
    if exit_code != 0:
        raise IOError("ffmpeg error")


def convert_files(files_data_arg: FilesData):
    finished_percent: float = 0
    for file_data in files_data_arg.file_data_list:
        file_size_percent: float = files_data_arg.get_file_size_percent(file_data)
        convert_file(file_data, file_size_percent, finished_percent)
        finished_percent = finished_percent + file_size_percent


def create_dir(dir_arg: Path):
    if not dir_arg.exists():
        print(f'Creating directory: {dir_arg}')
        dir_arg.mkdir()


def check_files_absent(files_data_arg: FilesData):
    existing_files: List[str] = []
    for file_data in files_data_arg.file_data_list:
        dest_file = file_data.dest_file
        if dest_file.exists():
            existing_files.append(str(dest_file))

    if existing_files:
        raise IOError(f"Destination files already exist: {existing_files}")


def to_mb(size_bytes: int) -> int:
    return size_bytes // 1024 // 1024


def check_enough_disk_space(files_data_arg: FilesData, dest_dir_arg: Path):
    total_file_size = files_data_arg.src_file_size_total
    available_disk_space_bytes = shutil.disk_usage(dest_dir_arg).free
    reserve_disk_space_bytes = 1 * 1024 * 1024 * 1024  # 1Gb
    if (total_file_size + reserve_disk_space_bytes) > available_disk_space_bytes:
        raise IOError(f"Not enough disk space: (source files total size {to_mb(total_file_size)}MB, "
                      f"reserve space {to_mb(reserve_disk_space_bytes)}MB, "
                      f"destination available space {to_mb(available_disk_space_bytes)}MB).")


def format_date_time(date_time: datetime.datetime) -> str:
    return date_time.strftime('%Y-%m-%d %H:%M:%S')


def get_last_performance_file() -> Path:
    user_home_dir: Path = Path.home()
    app_dir: Path = Path(user_home_dir, '.mts2mp4')
    if not app_dir.exists():
        app_dir.mkdir()
    return Path(app_dir, 'last_performance_bytes_per_second.txt')


def get_last_performance(performance_file: Path) -> Optional[int]:
    performance: Optional[int] = None
    if performance_file.exists():
        with open(performance_file, 'r') as f:
            performance = int(f.read())
    return performance


def write_last_performance(performance: int, performance_file: Path) -> None:
    with open(performance_file, 'w') as f:
        f.write(str(performance))


def calc_exp_finish_date_time(performance: Optional[int], files_data_: FilesData) -> Optional[datetime.datetime]:
    if performance:
        exp_duration_sec: int = files_data_.src_file_size_total / performance
        return start_date_time + datetime.timedelta(seconds=exp_duration_sec)
    else:
        return None


start_date_time: datetime.datetime = datetime.datetime.now()
print("Start time: ", format_date_time(start_date_time))

src_dir: Path = Path(sys.argv[1])
print(f"Source directory: {src_dir}")
check_dir_exits(src_dir)

dest_dir: Path = Path(sys.argv[2])
print(f"Destination directory: {dest_dir}")

preserve_audio: bool = '--preserve-audio' in sys.argv
print(f"Preserve audio: {preserve_audio}")

check_ffmpeg_availability()

files_data: FilesData = find_mts_files(src_dir, dest_dir)
print(files_data)
check_files_absent(files_data)

last_performance_file: Path = get_last_performance_file()
last_performance: Optional[int] = get_last_performance(last_performance_file)
exp_finish_date_time: Optional[datetime.datetime] = calc_exp_finish_date_time(last_performance, files_data)
print(f"Expected finish time: "
      f"{format_date_time(exp_finish_date_time) if exp_finish_date_time is not None else 'Unknown'}")

check_enough_disk_space(files_data, dest_dir)

convert_files(files_data)

print("Done.")
end_date_time: datetime.datetime = datetime.datetime.now()
print("Finish time: ", format_date_time(end_date_time))

act_duration_sec: int = (end_date_time - start_date_time).seconds
act_performance: int = int(files_data.src_file_size_total / act_duration_sec)
print(f"Actual performance: {act_performance} bytes/sec")
write_last_performance(act_performance, last_performance_file)
