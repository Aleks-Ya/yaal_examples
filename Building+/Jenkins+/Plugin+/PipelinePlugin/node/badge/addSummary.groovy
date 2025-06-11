node {
    env.MY_NAME = 'John'
    addSummary(text: "My informative summary simple")
    addSummary(text: "My summary with env vars: MY_NAME=${env.MY_NAME}")
    addSummary(text: "Different color", color: "red")
    addSummary(text: "HTML tags do NOT work: <b>bold</b>")
    addSummary(text: "# MarkDown formatting does NOT work")
    addSummary(text: "Link to DuckDuckGo", link: "https://duckduckgo.com")
    addSummary(text: "Multi-line text in double-quotes\nThe second line")
    addSummary(text: """Multi-line text in triple-quotes\nThe second line""")
    addSummary(text: """
                        Multi-line text block (without indent stripping)
                        The second line if the block
                        """)
    addSummary(text: """
                        Multi-line text block (with indent stripping)
                        The second line if the block
                        """.stripIndent())
    addSummary(text: """\
                        Multi-line text block: with indent stripping and with escaping empty first line
                        The second line if the block
                        """.stripIndent())
    addSummary(text: "My summary with an icon", icon: 'symbol-alert-circle-outline plugin-ionicons-api')

    echo 'Hello, Jenkins Pipeline!'
}