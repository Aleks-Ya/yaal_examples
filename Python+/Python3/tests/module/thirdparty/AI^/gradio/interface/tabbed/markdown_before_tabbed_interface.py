import gradio as gr
from gradio import Interface, ChatInterface

hello_world: Interface = gr.Interface(lambda name: "Hello " + name, "text", "text", api_name="predict")
bye_world: Interface = gr.Interface(lambda name: "Bye " + name, "text", "text", api_name="predict")
chat: ChatInterface = gr.ChatInterface(lambda *args: "Hello " + args[0], api_name="chat")

with gr.Blocks() as demo:
    gr.Markdown("# Intro text shown above the tabs")
    gr.TabbedInterface([hello_world, bye_world, chat], ["Hello World", "Bye World", "Chat"])

demo.launch()
