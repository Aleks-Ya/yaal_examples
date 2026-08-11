# https://www.gradio.app/docs/gradio/label
import gradio as gr

with gr.Blocks() as demo:
    gr.Label("hello world")

demo.launch()
