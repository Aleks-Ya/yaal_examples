import gradio as gr


def prepare_markdown(n: int) -> str:
    return f"Your number is **{n}**"


gr.Interface(
    fn=prepare_markdown,
    inputs=gr.Number(label="Enter an integer", precision=0),
    outputs=gr.Markdown(label="Result")
).launch()
