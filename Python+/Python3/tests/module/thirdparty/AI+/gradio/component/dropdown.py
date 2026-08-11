# https://www.gradio.app/docs/gradio/dropdown
import gradio as gr


def sentence_builder(animal: str, activities: list[str]) -> str:
    return f"""{animal} is {" and ".join(activities)}."""


demo: gr.Interface = gr.Interface(
    sentence_builder,
    [
        gr.Dropdown(
            ["cat", "dog", "bird"], label="Animal", info="Will add more animals later!"
        ),
        gr.Dropdown(
            ["ran", "swam", "ate", "slept"], value=["swam", "slept"], multiselect=True, label="Activity",
            info="Information about this Dropdown"
        )
    ],
    "text"
)
demo.launch()
