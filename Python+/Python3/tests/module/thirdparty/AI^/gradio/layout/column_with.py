# https://www.gradio.app/docs/gradio/column
import gradio as gr

with gr.Blocks() as demo:
    with gr.Row():
        with gr.Column(scale=1):
            text1 = gr.Textbox("AAA")
            text2 = gr.Textbox("BBB")
        with gr.Column(scale=4):
            btn1 = gr.Button("Button 1")
            btn2 = gr.Button("Button 2")
        with gr.Column(scale=4):
            gr.Markdown("[Gradio Site](https://www.gradio.app)")
            gr.Markdown("[Gradio Docs](https://www.gradio.app/docs)")
            gr.Markdown("[Gradio Guides](https://www.gradio.app/guides)")
        with gr.Column(scale=4):
            gr.Markdown("""
                        1. [Gradio Site](https://www.gradio.app) 
                        1. [Gradio Docs](https://www.gradio.app/docs)
                        1. [Gradio Guides](https://www.gradio.app/guides)
                        """)

demo.launch()
