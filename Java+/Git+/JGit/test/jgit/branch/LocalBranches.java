package jgit.branch;

import jgit.Helper;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Working with local branches.
 */
public class LocalBranches {

    @Test
    public void createBranch() throws IOException, GitAPIException {
        Repository repo = Helper.makeLocalRepo();

        Git git = new Git(repo);

        CommitCommand commit = git.commit();
        RevCommit initCommit = commit.setMessage("initial commit").call();

        CreateBranchCommand createBranchCommand = git.branchCreate();
        createBranchCommand.setName("dev");
        createBranchCommand.setStartPoint(initCommit);
        Ref devBranch = createBranchCommand.call();
        assertThat(devBranch.getName(), equalTo("refs/heads/dev"));

        ListBranchCommand listBranchCommand = git.branchList();
        List<Ref> refs = listBranchCommand.call();
        List<String> refNames = refs.stream().map(Ref::getName).collect(Collectors.toList());
        assertThat(refNames, contains("refs/heads/dev", "refs/heads/master"));

        ObjectId head = repo.resolve("HEAD");
        System.out.println(head);
    }

    @Test
    public void isCommitInBranch() throws IOException, GitAPIException {
        Repository repo = Helper.makeLocalRepo();

        Git git = new Git(repo);

        RevCommit initCommit = git.commit().setMessage("initial commit").call();
        RevCommit devCommit = git.commit().setMessage("commit in dev").call();
        RevCommit featCommit = git.commit().setMessage("commit in feat branch").call();

        String devBranchName = "dev";
        git.branchCreate()
                .setName(devBranchName)
                .setStartPoint(devCommit)
                .call();

        String featBranchName = "feat";
        git.branchCreate()
                .setName(featBranchName)
                .setStartPoint(featCommit)
                .call();

        ObjectId devBranchId = repo.resolve(devBranchName);
        Iterable<RevCommit> devBranchLog = git.log().add(devBranchId).call();
        List<ObjectId> devBranchCommits = StreamSupport.stream(devBranchLog.spliterator(), false)
                .map(ObjectId::toObjectId)
                .collect(Collectors.toList());

        assertThat(devBranchCommits, hasItems(initCommit.toObjectId(), devCommit.toObjectId()));
        assertThat(devBranchCommits, not(hasItem(featCommit.toObjectId())));
    }

}

