import gradio as gr

with gr.Blocks() as demo:
    gr.Number(value=42, precision=0, label="Integer")

demo.launch()
