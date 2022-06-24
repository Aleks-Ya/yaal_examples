package feign;

import feign.gson.GsonDecoder;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Source: https://github.com/OpenFeign/feign#basics
 */
class GitHubContributorTest {

    @Test
    void listContributors() {
        var github = Feign.builder()
                .decoder(new GsonDecoder())
                .target(GitHub.class, "https://api.github.com");

        // Fetch and print a list of the contributors to this library.
        var contributors = github.contributors("OpenFeign", "feign");
        for (var contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
    }

    interface GitHub {
        @RequestLine("GET /repos/{owner}/{repo}/contributors")
        List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);

        @RequestLine("POST /repos/{owner}/{repo}/issues")
        void createIssue(Issue issue, @Param("owner") String owner, @Param("repo") String repo);

    }

    static class Contributor {
        String login;
        int contributions;
    }

    static class Issue {
        String title;
        String body;
        List<String> assignees;
        int milestone;
        List<String> labels;
    }


}
