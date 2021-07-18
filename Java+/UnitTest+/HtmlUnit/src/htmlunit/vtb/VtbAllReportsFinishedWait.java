package htmlunit.vtb;

import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

class VtbAllReportsFinishedWait {
    private final AuthData authData;
    private final String agreement;

    VtbAllReportsFinishedWait(AuthData authData, String agreement) {
        this.authData = authData;
        this.agreement = agreement;
    }

    void waitUntilFinished() {
        System.out.printf("Waiting all reports are finished for agreement %s...\n", agreement);
        try (var webClient = WebClientFactory.create(authData)) {
            var done = false;
            var timer = new Timer();
            timer.start();
            while (!done) {
                timer.waitForTime();
                done = areAllReportsDone(webClient);
                timer.done();
            }
            System.out.println("All requests are finished.");
        } catch (Exception e) {
            throw new WaitReportsException(e);
        }
    }

    private boolean areAllReportsDone(com.gargoylesoftware.htmlunit.WebClient webClient) throws IOException {
        var table = WebClientHelper.getReportTable(webClient, agreement);
        var headerRow = table.getRow(0);
        var columnHeader = "Отчет";
        var reportCellIndex = headerRow.getCells().stream()
                .filter(cell -> columnHeader.equalsIgnoreCase(cell.getFirstChild().getNodeValue()))
                .findFirst()
                .orElseThrow(() -> new WaitReportsException("Column header not found: " + columnHeader))
                .getIndex();
        var dataRows = new ArrayList<>(table.getRows());
        dataRows.remove(0);
        var allDone = true;
        for (HtmlTableRow row : dataRows) {
            var reportCell = row.getCell(reportCellIndex);
            var content = reportCell.getTextContent();
            if ("В обработке".equalsIgnoreCase(content) || "Новый".equalsIgnoreCase(content)) {
                allDone = false;
                System.out.println("Not ready row " + row.getIndex());
            } else if ("Отчет готов".equalsIgnoreCase(content)) {
                System.out.println("Ready row " + row.getIndex());
            } else {
                throw new WaitReportsException(String.format("Unknown %d row status: %s", row.getIndex(), content));
            }
        }
        return allDone;
    }

    static class WaitReportsException extends RuntimeException {
        public WaitReportsException(Throwable cause) {
            super(cause);
        }

        public WaitReportsException(String message) {
            super(message);
        }
    }

    private static class Timer {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
                .withZone(ZoneId.of("Europe/Moscow"));
        private Instant start;
        private Instant next;

        void start() {
            start = Instant.now();
            next = start;
        }

        private boolean isTime() {
            return Instant.now().isAfter(next);
        }

        void waitForTime() {
            while (!isTime()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        void done() {
            var now = Instant.now();
            var durationMin = Duration.between(start, now).toMinutes();
            long incrementMin;
            if (durationMin < 30) {
                incrementMin = 5;
            } else if (durationMin < 60) {
                incrementMin = 15;
            } else {
                incrementMin = 30;
            }
            next = now.plus(incrementMin, ChronoUnit.MINUTES);
            System.out.println("Next run at " + formatter.format(next));
        }
    }

}
