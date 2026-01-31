# https://www.gradio.app/docs/gradio/column
import gradio as gr
from gradio import Blocks
from gradio import Textbox, Button, Markdown, Column

text1: Textbox = Textbox("AAA")
text2: Textbox = Textbox("BBB")

btn1: Button = Button("Button 1")
btn2: Button = Button("Button 2")

md1: Markdown = Markdown("[Gradio Site](https://www.gradio.app)")
md2: Markdown = Markdown("[Gradio Docs](https://www.gradio.app/docs)")
md3: Markdown = Markdown("[Gradio Guides](https://www.gradio.app/guides)")

md4: Markdown = Markdown("""
                        1. [Gradio Site](https://www.gradio.app) 
                        1. [Gradio Docs](https://www.gradio.app/docs)
                        1. [Gradio Guides](https://www.gradio.app/guides)
                        """)

textboxes: list[Textbox] = [Textbox(f"Row: {i}") for i in range(3)]

with Blocks() as demo:
    with gr.Row():
        with Column():
            text1.render()
            text2.render()
        with Column():
            btn1.render()
            btn2.render()
    with gr.Row():
        with Column():
            md1.render()
            md2.render()
            md3.render()
        with Column():
            md4.render()
        with Column():
            for t in textboxes:
                t.render()
    with gr.Row():
        for c in range(3):
            with Column():
                textboxes: list[Textbox] = [Textbox(f"Col: {c}, Row: {i}") for i in range(3)]

demo.launch()
