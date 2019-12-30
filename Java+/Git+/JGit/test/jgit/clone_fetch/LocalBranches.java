package jgit.clone_fetch;

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

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Working with local branches.
 */
public class LocalBranches {

    @Test
    public void main() throws IOException, GitAPIException {
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

}
