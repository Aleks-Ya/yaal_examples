# https://www.gradio.app/docs/gradio/textbox
import gradio as gr

text: str = """\
Line 1
Line 2
Line 3
"""

with gr.Blocks() as demo:
    gr.Textbox(label="Output", value=text)

demo.launch()
