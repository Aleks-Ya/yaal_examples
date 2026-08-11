# https://www.gradio.app/docs/gradio/state
import gradio as gr


def update_state(current_state: list[str], new_input: str) -> tuple[list, str]:
    if current_state is None:
        current_state = []
    current_state.append(new_input)
    return current_state, f"State contains: {current_state}"


with gr.Blocks() as demo:
    state: gr.State = gr.State([])
    input_text: gr.Textbox = gr.Textbox(label="Enter text to add to state")
    submit_btn: gr.Button = gr.Button("Add to State")
    output_text: gr.Textbox = gr.Textbox(label="Current State", interactive=False)

    submit_btn.click(
        fn=update_state,
        inputs=[state, input_text],
        outputs=[state, output_text]
    )

demo.launch()
