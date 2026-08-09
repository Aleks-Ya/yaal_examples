from datetime import datetime
from typing import Any

from anthropic import Anthropic
from anthropic.types import Message, ToolParam, ContentBlock, TextBlock, MessageParam, ToolResultBlockParam, ModelParam


class Agent:
    __max_tokens: int = 1024
    __get_current_datetime_schema: ToolParam = ToolParam(
        name="get_current_datetime",
        description="Get the current date and time as a formatted string.",
        input_schema={
            "type": "object",
            "properties": {
                "date_format": {
                    "type": "string",
                    "description": "A strftime format string (e.g. '%Y-%m-%d %H:%M:%S') controlling how the datetime is rendered. "
                                   "Defaults to '%Y-%m-%d %H:%M:%S' if not provided."
                }
            },
            "required": []
        })
    __sum_schema: ToolParam = ToolParam(
        name="sum",
        description="Adds two numbers together and returns the result.",
        input_schema={
            "type": "object",
            "properties": {
                "left": {
                    "type": "number",
                    "description": "The first number to add.",
                },
                "right": {
                    "type": "number",
                    "description": "The second number to add.",
                },
            },
            "required": ["left", "right"]
        }
    )

    def __init__(self, client: Anthropic, model: ModelParam):
        self.client = client
        self.model = model
        self.messages: list[MessageParam] = []

    def send_message(self, user_message: str) -> str:
        content: ContentBlock = TextBlock(text=user_message, type="text")
        message_param: MessageParam = MessageParam(role="user", content=[content])
        self.messages.append(message_param)

        message: Message = self.__send_messages()
        while message.stop_reason == "tool_use":
            message = self.__send_messages()
        texts: str = "\n".join([content.text for content in message.content if content.type == "text"])
        return texts

    def __send_messages(self) -> Message:
        message: Message = self.client.messages.create(
            model=self.model,
            max_tokens=self.__max_tokens,
            messages=self.messages,
            tools=[self.__get_current_datetime_schema, self.__sum_schema]
        )
        self.messages.append(MessageParam(role="assistant", content=message.content))
        blocks: list[ContentBlock | ToolResultBlockParam] = []
        for content in message.content:
            if content.type == "tool_use":
                try:
                    tool_result: str = self.__run_tool(content.name, content.input)
                    tool_result_block_param: ToolResultBlockParam = ToolResultBlockParam(
                        type="tool_result",
                        tool_use_id=content.id,
                        content=tool_result,
                        is_error=False
                    )
                except Exception as exc:
                    tool_result_block_param: ToolResultBlockParam = ToolResultBlockParam(
                        type="tool_result",
                        tool_use_id=content.id,
                        content=str(exc),
                        is_error=True
                    )
                blocks.append(tool_result_block_param)
        self.messages.append(MessageParam(role="user", content=blocks))
        return message

    @staticmethod
    def __get_current_datetime(date_format="%Y-%m-%d %H:%M:%S") -> str:
        if not date_format:
            raise ValueError("date_format cannot be empty")
        return datetime.now().strftime(date_format)

    @staticmethod
    def __sum(left: int, right: int) -> str:
        return str(left + right)

    def __run_tool(self, tool_name: str, tool_input: dict[str, Any]) -> str:
        if tool_name == self.__get_current_datetime_schema["name"]:
            return self.__get_current_datetime(**tool_input)
        elif tool_name == self.__sum_schema["name"]:
            return self.__sum(**tool_input)
        else:
            raise ValueError(f"Unknown tool name: {tool_name}")
