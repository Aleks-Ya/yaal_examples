# https://www.gradio.app/docs/gradio/barplot
import gradio as gr
from pandas import DataFrame

df: DataFrame = DataFrame({"x": [1, 2, 3], "y": [2, 5, 4]})
with gr.Blocks() as demo:
    gr.BarPlot(df, x="x", y="y", title="My Chart")

demo.launch()
