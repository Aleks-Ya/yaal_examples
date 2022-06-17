# 1) Find and rename video files in TagSpaces location directory according to the target pattern.
# Run: python rename_files.py /home/aleks/yandex-disk/video/Wakeboard
import os
import re
import sys
from pathlib import Path
from re import Pattern, Match
from typing import Any, List, Tuple
from typing import Optional

dry_run: bool = False


def on_error(error: Any):
    raise error


root_dir: str = sys.argv[1]
ts_dir_name: str = ".ts"


def is_ts_dir(dir_tuple: Tuple[str, List[str], List[str]]) -> bool:
    return dir_tuple[0] == ts_dir_name or dir_tuple[0].endswith(os.sep + ts_dir_name)


def is_video_file(f: str) -> bool:
    return f.endswith('.mp4')


def is_dir_contains_video_files(dir_tuple: Tuple[str, List[str], List[str]]) -> bool:
    video_files = [file for file in dir_tuple[2] if is_video_file(file)]
    return len(video_files) > 0


target_file_name_pattern: Pattern = re.compile('\d\d \d\d \[[-\w\d\s]+\]\.mp4')
parsable_file_name_pattern: Pattern = re.compile('(\d{1,2}) ?(\d{1,2})? ?(\[[-\w\d\s]+\])(\.mp4(.jpg)?)')


def is_file_has_target_name(f: str):
    return target_file_name_pattern.match(f)


def is_file_has_parsable_name(f: str):
    return parsable_file_name_pattern.match(f)


def make_correct_name(f: str):
    m: Match = re.match(parsable_file_name_pattern, f)
    digit1: str = m.group(1)
    digit2: str = m.group(2)
    tags: str = m.group(3)
    extension: str = m.group(4)
    if len(digit1) == 1:
        digit1 = '0' + digit1
    if digit2 is not None:
        if len(digit2) == 1:
            digit2 = '0' + digit2
    else:
        digit2 = '01'
    return f'{digit1} {digit2} {tags}{extension}'


def get_thumbnail(dir_path: str, f: str) -> Optional[Path]:
    thumbnail_name: str = f + '.jpg'
    thumbnail_full_path: str = os.path.join(dir_path, ts_dir_name, thumbnail_name)
    if os.path.exists(thumbnail_full_path):
        return Path(thumbnail_full_path)
    else:
        return None


# Tests "is_file_has_target_name()"
assert is_file_has_target_name('05 01 [GasfortHolidayPark StartDeck].mp4')
assert not is_file_has_target_name('2 1[GasfortHolidayPark Kicker Large Right Grab Nose Success Dirty Instructor].mp4')
assert not is_file_has_target_name('4 10 [GasfortHolidayPark Flat HS BS D180 Blind].mp4')
assert not is_file_has_target_name('4 7 [GasfortHolidayPark Flat HS BS D180 Blind].mp4')
assert not is_file_has_target_name('13 [GasfortHolidayPark StartDeck].mp4')
assert not is_file_has_target_name('01 [Kicker Large Left GasfortHolidayPark D360 HS FS Success].mp4')
assert not is_file_has_target_name('19[GasfortHolidayPark Kicker Large Right Tail Grab D180 HS FS Instructor].mp4')
assert not is_file_has_target_name('5 [GasfortHolidayPark StartDeck].mp4')
assert not is_file_has_target_name('1 4[SlidersCablePark RainBow Transfer Success TS LandToRight D0 50-50].mp4')

# Tests "is_file_has_parsable_name()"
assert is_file_has_parsable_name('2 1[GasfortHolidayPark Kicker Large Right Grab Nose Success Dirty Instructor].mp4')
assert is_file_has_parsable_name('4 10 [GasfortHolidayPark Flat HS BS D180 Blind].mp4')
assert is_file_has_parsable_name('4 7 [GasfortHolidayPark Flat HS BS D180 Blind].mp4')
assert is_file_has_parsable_name('13 [GasfortHolidayPark StartDeck].mp4')
assert is_file_has_parsable_name('01 [Kicker Large Left GasfortHolidayPark D360 HS FS Success].mp4')
assert is_file_has_parsable_name('19[GasfortHolidayPark Kicker Large Right Tail Grab D180 HS FS Instructor].mp4')
assert is_file_has_parsable_name('5 [GasfortHolidayPark StartDeck].mp4')
assert is_file_has_parsable_name('1 4[SlidersCablePark RainBow Transfer Success TS LandToRight D0 50-50].mp4')

# Tests "make_correct_name()"
assert make_correct_name('2 1[GasfortHolidayPark Kicker Large Right Grab Nose Success Dirty Instructor].mp4') \
       == '02 01 [GasfortHolidayPark Kicker Large Right Grab Nose Success Dirty Instructor].mp4'
assert make_correct_name('4 10 [GasfortHolidayPark Flat HS BS D180 Blind].mp4') \
       == '04 10 [GasfortHolidayPark Flat HS BS D180 Blind].mp4'
assert make_correct_name('4 7 [GasfortHolidayPark Flat HS BS D180 Blind].mp4') \
       == '04 07 [GasfortHolidayPark Flat HS BS D180 Blind].mp4'
assert make_correct_name('1 4[SlidersCablePark RainBow Transfer Success TS LandToRight D0 50-50].mp4') \
       == '01 04 [SlidersCablePark RainBow Transfer Success TS LandToRight D0 50-50].mp4'
assert make_correct_name('09 1[HipNotics RoofTopDoubleKicker Transfer HS BoardSlide NotMe ToLeft Left].mp4.jpg') \
       == '09 01 [HipNotics RoofTopDoubleKicker Transfer HS BoardSlide NotMe ToLeft Left].mp4.jpg'

assert make_correct_name('13 [GasfortHolidayPark StartDeck].mp4') \
       == '13 01 [GasfortHolidayPark StartDeck].mp4'
assert make_correct_name('01 [Kicker Large Left GasfortHolidayPark D360 HS FS Success].mp4') \
       == '01 01 [Kicker Large Left GasfortHolidayPark D360 HS FS Success].mp4'
assert make_correct_name('19[GasfortHolidayPark Kicker Large Right Tail Grab D180 Success HS FS Instructor].mp4') \
       == '19 01 [GasfortHolidayPark Kicker Large Right Tail Grab D180 Success HS FS Instructor].mp4'
assert make_correct_name('5 [GasfortHolidayPark StartDeck].mp4') \
       == '05 01 [GasfortHolidayPark StartDeck].mp4'
assert make_correct_name('5 [GasfortHolidayPark StartDeck].mp4.jpg') \
       == '05 01 [GasfortHolidayPark StartDeck].mp4.jpg'

dir_contain_files_list: List[Tuple[str, List[str], List[str]]] = \
    [dir_tuple for dir_tuple in os.walk(root_dir) if
     is_dir_contains_video_files(dir_tuple) and not is_ts_dir(dir_tuple)]

video_match_list: List[str] = []
video_not_parsable_list: List[str] = []
video_parsable_list: List[str] = []
thumbnail_not_match_list: List[Path] = []

for dir_contain_files in dir_contain_files_list:
    dir_path: str = dir_contain_files[0]
    files: List[str] = dir_contain_files[2]
    for file in files:
        if is_video_file(file):
            video_full: str = os.path.join(dir_path, file)
            if not is_file_has_target_name(file):
                thumbnail_full: Path = get_thumbnail(dir_path, file)
                if is_file_has_parsable_name(file):
                    video_correct: str = make_correct_name(file)
                    video_correct_full: str = os.path.join(dir_path, video_correct)
                    video_parsable_list.append(video_correct_full)
                    print(f'Rename "{video_full}" to "{video_correct_full}')
                    if not dry_run:
                        os.rename(video_full, video_correct_full)
                    if thumbnail_full is not None:
                        thumbnail_correct: str = make_correct_name(thumbnail_full.name)
                        thumbnail_correct_full: str = os.path.join(dir_path, ts_dir_name, thumbnail_correct)
                        thumbnail_not_match_list.append(thumbnail_full)
                        print(f'Rename "{thumbnail_full}" to "{thumbnail_correct_full}')
                        if not dry_run:
                            os.rename(thumbnail_full, thumbnail_correct_full)
                else:
                    video_not_parsable_list.append(video_full)
            else:
                video_match_list.append(video_full)

print(f'Match count: {len(video_match_list)}')
print(f'Parsable count: {len(video_parsable_list)}')
print(f'Not parsable count: {len(video_not_parsable_list)}')
print(f'Thumbnail not match count: {len(thumbnail_not_match_list)}')
