from pathlib import Path

import gradio as gr
import pandas as pd
from gradio import Dataframe
from pandas import DataFrame

from temp_helper import TempPath

file: Path = TempPath.temp_path_absent(suffix='.parquet')
DataFrame({'Name': ['John', 'Mary'], 'Age': [30, 25]}).to_parquet(file)


def load_parquet_data():
    df: DataFrame = pd.read_parquet(file)
    return df


with gr.Blocks() as demo:
    gr.Markdown("## Parquet File Viewer")
    output_dataframe: Dataframe = gr.DataFrame()
    demo.load(load_parquet_data, outputs=output_dataframe)

demo.launch()
