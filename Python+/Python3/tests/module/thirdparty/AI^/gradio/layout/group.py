# https://www.gradio.app/docs/gradio/group
import gradio as gr

with gr.Blocks() as demo:
    with gr.Group():
        gr.Textbox(label="First A")
        gr.Textbox(label="Last A")
    with gr.Group():
        gr.Textbox(label="First B")
        gr.Textbox(label="Last B")

demo.launch()
