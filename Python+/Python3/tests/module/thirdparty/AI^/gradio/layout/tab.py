# https://www.gradio.app/docs/gradio/tab
import gradio as gr

with gr.Blocks() as demo:
    with gr.Tab("Lion"):
        gr.Textbox(label="First")
        gr.Button("New Lion")
    with gr.Tab("Tiger"):
        gr.Textbox(label="Last")
        gr.Button("New Tiger")

demo.launch()
