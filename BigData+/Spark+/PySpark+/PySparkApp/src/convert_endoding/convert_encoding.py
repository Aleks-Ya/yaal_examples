from __future__ import print_function

import os
import tempfile

from util import create_spark_session

if __name__ == "__main__":
    input_file = os.path.join(os.getcwd(), 'win1251.csv')
    temp_dir: str = tempfile.mkdtemp()
    output_file = os.path.join(temp_dir, 'utf8.csv')
    print(f'Output file: {output_file}')

    ss = create_spark_session("ConvertEncoding")

    lines = ss.read.csv(input_file, encoding='Windows-1251')
    lines.write.csv(output_file, encoding='UTF-8')

    ss.stop()
