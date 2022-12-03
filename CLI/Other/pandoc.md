# PanDoc
https://pandoc.org
Help: `pandoc --help`

Convert HTML to MarkDown: `pandoc --from html --to markdown doc.html -o doc.md`
Convert with enabled extensions: `pandoc --from html --to markdown+pipe_tables+backtick_code_blocks-markdown_attribute doc.html -o doc.md`


`pandoc --from html-native_divs-native_spans --to markdown-escaped_line_breaks Sail.html -o Sail3.md`

https://pandoc.org/MANUAL.html#raw-htmltex
Without `<div>` in output: `-native_divs`



## Using pipe
Output to std out:
`pandoc --from html --to markdown Sail.html`
Read from std in and output to std out:
`cat Sail.html | pandoc --from html --to markdown`

## Convert all HTML-files in a dir
find . -iname "*.html" -exec echo "{} -> basename {}" \;
```
find . -maxdepth 1 -iname "*.html" \
	| xargs -I{} basename -s .html {} \
	| xargs -L1 -I{} pandoc --from html-native_divs-native_spans --to markdown-escaped_line_breaks "{}.html" -o "{}.md"
```