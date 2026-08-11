# https://www.gradio.app/docs/gradio/interface
import gradio as gr


def show_int(n: int) -> int:
    return int(n)


gr.Interface(
    fn=show_int,
    inputs=gr.Number(label="Enter an integer", precision=0),
    outputs=gr.Number(label="Result", precision=0)
).launch()
