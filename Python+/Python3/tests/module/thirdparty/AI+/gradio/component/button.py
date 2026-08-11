# https://www.gradio.app/docs/gradio/button
import gradio as gr


def show_int(num: int) -> int:
    return int(num)


with gr.Blocks() as demo:
    gr.Markdown("## Integer Converter")
    n = gr.Number(label="Enter an integer", precision=0)
    out = gr.Number(label="Result", precision=0)
    gr.Button("Run").click(fn=show_int, inputs=n, outputs=out)

demo.launch()
