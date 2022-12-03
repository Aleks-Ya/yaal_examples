# Pandoc

## Evernote HTML to MarkDown
### Code blocks
Original:
`pandoc --from html-native_divs-native_spans --to markdown-escaped_line_breaks evernote_code_block.xml -o out/evernote_code_block.md`
Adapted:
`pandoc --from html-native_divs-native_spans+backtick_code_blocks --to markdown-escaped_line_breaks evernote_code_block_adapted.xml -o out/evernote_code_block_adapted.md`
### Tables
`pandoc --from html --to markdown in/evernote_table.xml -o out/evernote_table.md`
`pandoc --from html --to markdown+pipe_tables in/evernote_table.xml -o out/evernote_table.md`
`pandoc --from html-native_divs-native_spans --to markdown-escaped_line_breaks+grid_tables in/evernote_table.xml -o out/evernote_table.md`
