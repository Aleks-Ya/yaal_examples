-- Send messeges to DebugView app
function main()
	PrintDbgStr("test1")
	PrintDbgStr("test2")
	PrintDbgStr("dbg from " ..getScriptPath())
end