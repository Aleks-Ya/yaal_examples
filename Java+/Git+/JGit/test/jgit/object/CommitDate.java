package jgit.object;

import jgit.Helper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

/**
 * Working with commit dates.
 */
public class CommitDate {

    @Test
    public void readCommitDate() throws IOException, GitAPIException {
        Repository repo = Helper.makeLocalRepo();
        Git git = new Git(repo);

        RevCommit commit = git.commit()
                .setMessage("initial commit")
                .call();

        int commitTime = commit.getCommitTime();
        System.out.println(commitTime);
        Instant commitInstant = Instant.ofEpochSecond(commitTime);
        System.out.println(commitInstant);
    }

}
