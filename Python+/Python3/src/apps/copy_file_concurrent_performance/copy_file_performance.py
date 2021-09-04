import datetime
import shutil
from concurrent.futures import ThreadPoolExecutor, Future
from pathlib import Path
from typing import List

source_files = ['/media/aleks/JVCCAM_SD/PRIVATE/AVCHD/BDMV/STREAM/00185.MTS',
                '/media/aleks/JVCCAM_SD/PRIVATE/AVCHD/BDMV/STREAM/00186.MTS',
                '/media/aleks/JVCCAM_SD/PRIVATE/AVCHD/BDMV/STREAM/00187.MTS']
dest_dir = '/home/aleks/tmp/copy_file_performance'


def copy_parallel():
    print('Copying files in parallel')
    start_time = datetime.datetime.now()
    with ThreadPoolExecutor(max_workers=len(source_files)) as e:
        futures: List[Future] = []
        for source_file in source_files:
            source_path = Path(source_file)
            dest_file = Path(dest_dir, source_path.name)
            print(f"Submitting copying '{source_path}' to '{dest_file}'...")
            future = e.submit(shutil.copyfile, source_file, dest_file)
            futures.append(future)
        for future in futures:
            future.result()
    end_time = datetime.datetime.now()
    duration = end_time - start_time
    print(f"Parallel duration: '{duration}'")


def copy_sequentially():
    print('Copying files sequentially')
    start_time = datetime.datetime.now()
    for source_file in source_files:
        source_path = Path(source_file)
        dest_file = Path(dest_dir, source_path.name)
        print(f"Copying '{source_path}' to '{dest_file}'...")
        shutil.copyfile(source_file, dest_file)
    end_time = datetime.datetime.now()
    duration = end_time - start_time
    print(f"Sequential duration: '{duration}'")


copy_parallel()
copy_sequentially()
