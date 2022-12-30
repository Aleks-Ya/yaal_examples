package joplin.apps.html_to_markdown;

import util.InputStreamUtil;

import java.io.IOException;

class PandocService {
    private static final int SUCCESS_EXIT_CODE = 0;

    String convertHtmlToMarkdown(String htmlBody) throws IOException, InterruptedException {
        var process = new ProcessBuilder("pandoc",
                "--from", "html-native_divs-native_spans",
                "--to", "markdown-escaped_line_breaks")
                .redirectInput(ProcessBuilder.Redirect.PIPE)
                .start();
        try (var stdIn = process.outputWriter()) {
            stdIn.append(htmlBody);
        }
        var exitCode = process.waitFor();
        if (exitCode != SUCCESS_EXIT_CODE) {
            throw new IllegalStateException("Wrong exit code: " + exitCode);
        }
        return InputStreamUtil.inputStreamToString(process.getInputStream());
    }
}
