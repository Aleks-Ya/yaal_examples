import gradio as gr
import pandas as pd


def make_lineplot(n: int) -> pd.DataFrame:
    n = int(n or 0)
    n = max(0, min(n, 100))  # keep the UI responsive
    xs = list(range(n))
    ys = [x * x for x in xs]  # example series: y = x^2
    return pd.DataFrame({"x": xs, "y": ys})


gr.Interface(
    fn=make_lineplot,
    inputs=gr.Number(label="Number of points", precision=0, maximum=100, value=50),
    outputs=gr.LinePlot(x="x", y="y", title="Line Plot", x_title="x", y_title="y"),
).launch()
