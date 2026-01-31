# https://www.gradio.app/docs/gradio/row
import gradio as gr

with gr.Blocks() as demo:
    with gr.Row():
        gr.Textbox(label="First")
        gr.Textbox(label="Last")

demo.launch()
