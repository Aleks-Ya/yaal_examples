# Run from tests/module/thirdparty/AI+/gradio: uv run mcp/hello_world_mcp_server.py
# Requires Gradio's MCP extra: gradio[mcp]
# MCP endpoint: http://127.0.0.1:7860/gradio_api/mcp/sse
# Source: https://www.gradio.app/guides/building-mcp-server-with-gradio
import gradio as gr


def greet(name: str) -> str:
    """Return a hello-world greeting for the given name.

    Args:
        name: The name of the person to greet.
    """
    return f"Hello, {name}!"


demo = gr.Interface(
    fn=greet,
    inputs=gr.Textbox(label="Name", value="World"),
    outputs=gr.Textbox(label="Greeting"),
    title="Hello World MCP Server",
    api_name="greet",
)

if __name__ == "__main__":
    demo.launch(mcp_server=True, server_port=7860)
