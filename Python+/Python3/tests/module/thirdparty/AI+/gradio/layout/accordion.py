# https://www.gradio.app/docs/gradio/accordion
import gradio as gr

with gr.Blocks() as demo:
    with gr.Accordion("See Details"):
        gr.Markdown("lorem ipsum")

demo.launch()
