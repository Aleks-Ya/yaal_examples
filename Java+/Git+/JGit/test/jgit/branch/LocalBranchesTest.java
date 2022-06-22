package jgit.branch;

import jgit.Helper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Working with local branches.
 */
class LocalBranchesTest {

    @Test
    void createBranch() throws IOException, GitAPIException {
        var repo = Helper.makeLocalRepo();

        var git = new Git(repo);

        var commit = git.commit();
        var initCommit = commit.setMessage("initial commit").call();

        var createBranchCommand = git.branchCreate();
        createBranchCommand.setName("dev");
        createBranchCommand.setStartPoint(initCommit);
        var devBranch = createBranchCommand.call();
        assertThat(devBranch.getName()).isEqualTo("refs/heads/dev");

        var listBranchCommand = git.branchList();
        var refs = listBranchCommand.call();
        var refNames = refs.stream().map(Ref::getName).collect(Collectors.toList());
        assertThat(refNames).contains("refs/heads/dev", "refs/heads/master");

        var head = repo.resolve("HEAD");
        System.out.println(head);
    }

    @Test
    void isCommitInBranch() throws IOException, GitAPIException {
        var repo = Helper.makeLocalRepo();

        var git = new Git(repo);

        var initCommit = git.commit().setMessage("initial commit").call();
        var devCommit = git.commit().setMessage("commit in dev").call();
        var featCommit = git.commit().setMessage("commit in feat branch").call();

        var devBranchName = "dev";
        git.branchCreate()
                .setName(devBranchName)
                .setStartPoint(devCommit)
                .call();

        var featBranchName = "feat";
        git.branchCreate()
                .setName(featBranchName)
                .setStartPoint(featCommit)
                .call();

        var devBranchId = repo.resolve(devBranchName);
        var devBranchLog = git.log().add(devBranchId).call();
        var devBranchCommits = StreamSupport.stream(devBranchLog.spliterator(), false)
                .map(ObjectId::toObjectId)
                .collect(Collectors.toList());

        assertThat(devBranchCommits).contains(initCommit.toObjectId(), devCommit.toObjectId());
        assertThat(devBranchCommits).doesNotContain(featCommit.toObjectId());
    }

}

