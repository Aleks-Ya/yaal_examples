# https://www.gradio.app/docs/gradio/walkthrough
import gradio as gr

with gr.Blocks() as demo:
    with gr.Walkthrough(selected=1) as walkthrough:
        with gr.Step("Step 1", id=1):
            btn = gr.Button("go to Step 2")
            btn.click(lambda: gr.Walkthrough(selected=2), outputs=walkthrough)
        with gr.Step("Step 2", id=2):
            txt = gr.Textbox("Welcome to Step 2")

demo.launch()
