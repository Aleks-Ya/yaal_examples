# https://www.gradio.app/docs/gradio/markdown
import gradio as gr

with gr.Blocks() as demo:
    gr.Markdown("## Parquet File Viewer")
    gr.Markdown("Hyperlink: [Gradio Docs](https://www.gradio.app/docs)")
    gr.Markdown("""
        1. [Gradio Site](https://www.gradio.app) 
        1. [Gradio Docs](https://www.gradio.app/docs)
        1. [Gradio Guides](https://www.gradio.app/guides)
    """)

demo.launch()
