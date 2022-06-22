package jgit.object;

import jgit.Helper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

/**
 * Working with commit dates.
 */
class CommitDateTest {

    @Test
    void readCommitDate() throws IOException, GitAPIException {
        var repo = Helper.makeLocalRepo();
        var git = new Git(repo);

        var commit = git.commit()
                .setMessage("initial commit")
                .call();

        var commitTime = commit.getCommitTime();
        System.out.println(commitTime);
        var commitInstant = Instant.ofEpochSecond(commitTime);
        System.out.println(commitInstant);
    }

}
