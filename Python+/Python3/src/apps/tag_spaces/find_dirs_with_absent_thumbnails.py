# 1) Find and list folders in TagSpaces location directory which has videos without thumbnails.
# 2) Delete excess (not having appropriate mp4 file) files in ".ts" directories.
# Run: python find_dirs_with_absent_thumbnails.py /home/aleks/yandex-disk/video/Wakeboard
import os
import sys
from typing import Any, List, Tuple, Set


def on_error(error: Any):
    raise error


root_dir: str = sys.argv[1]
ts_dir_name: str = ".ts"


def is_ts_dir(dir_tuple: Tuple[str, List[str], List[str]]) -> bool:
    return dir_tuple[0] == ts_dir_name or dir_tuple[0].endswith(os.sep + ts_dir_name)


def is_dir_contains_video_files(dir_tuple: Tuple[str, List[str], List[str]]) -> bool:
    video_files = [file for file in dir_tuple[2] if file.endswith('.mp4')]
    return len(video_files) > 0


def dir_has_ts_subdir(dir_tuple: Tuple[str, List[str], List[str]]) -> bool:
    dir_path: str = dir_tuple[0]
    files: List[str] = os.listdir(dir_path)
    ts_dir_full_path: str = os.path.join(dir_path, ts_dir_name)
    return ts_dir_name in files and os.path.isdir(ts_dir_full_path)


dir_contain_files_list: List[Tuple[str, List[str], List[str]]] = \
    [dir_tuple for dir_tuple in os.walk(root_dir) if
     is_dir_contains_video_files(dir_tuple) and not is_ts_dir(dir_tuple)]

no_ts_dir_list: List[str] = []

all_dirs_to_visit: Set[str] = set()
all_dirs_to_visit.update(no_ts_dir_list)

for dir_contain_files in dir_contain_files_list:
    has_ts_dir: bool = dir_has_ts_subdir(dir_contain_files)
    if not has_ts_dir:
        no_ts_dir_list.append(dir_contain_files[0])
print(f'No .ts dirs: {no_ts_dir_list}')

dir_contain_files_has_ts_list: List[Tuple[str, List[str], List[str]]] = \
    [dir_contain_files for dir_contain_files in dir_contain_files_list if dir_has_ts_subdir(dir_contain_files)]

all_absent_file_list: List[str] = []
all_excess_file_list: List[str] = []
for dir_contain_files_has_ts in dir_contain_files_has_ts_list:
    content_dir_list: List[str] = [file for file in dir_contain_files_has_ts[2] if file.endswith('.mp4')]
    content_dir_path = dir_contain_files_has_ts[0]
    ts_dir_path: str = os.path.join(content_dir_path, ts_dir_name)
    ts_dir_files_act_list: List[str] = [os.path.join(ts_dir_path, file) for file in os.listdir(ts_dir_path)
                                        if file.endswith('.mp4.jpg')]
    ts_dir_files_exp_list: List[str] = [os.path.join(ts_dir_path, file + '.jpg') for file in content_dir_list]
    absent_file_list: List[str] = [file for file in ts_dir_files_exp_list if file not in ts_dir_files_act_list]
    excess_file_list: List[str] = [file for file in ts_dir_files_act_list if file not in ts_dir_files_exp_list]
    all_absent_file_list += absent_file_list
    all_excess_file_list += excess_file_list
    if len(absent_file_list) > 0:
        all_dirs_to_visit.add(content_dir_path)

print(f'Absent: {all_absent_file_list}')
print()
print(f'Excess: {all_excess_file_list}')
print()
print(f'All dirs to visit:')
for dir_to_visit in sorted(all_dirs_to_visit):
    print(dir_to_visit)
print()
print(f'Absent number: {len(all_absent_file_list)}')
print(f'Excess number: {len(all_excess_file_list)}')
print(f'Dirs for visit number: {len(all_dirs_to_visit)}')

for excess_file in all_excess_file_list:
    print(f'Deleting an excess file: "{excess_file}"...', end='')
    os.remove(excess_file)
    print('OK')
