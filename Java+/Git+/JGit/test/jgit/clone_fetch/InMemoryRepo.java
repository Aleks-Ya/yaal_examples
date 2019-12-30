package jgit.clone_fetch;

import jgit.Helper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Using InMemoryRepository.
 */
public class InMemoryRepo {
    private static final String remoteRepoName = "remoteRepo";

    @Test
    public void main() throws IOException, GitAPIException {
        Repository repo = Helper.makeRemoteBareRepo();

        Git git = new Git(repo);

        ListBranchCommand branches = git.branchList();
        List<Ref> branchList = branches.call();
        System.out.println(branchList);
//        CommitCommand commit = git.commit();
//        commit.setMessage("initial commit").call();

//        AddCommand addCommand = git.add();
//        addCommand.addFilepattern("*.*");
//        DirCache dirCache = addCommand.call();

//        CommitCommand commitCommand = git.commit();
//        RevCommit revCommit = commitCommand.call();

//        ObjectId head = repo.resolve("HEAD");
//        System.out.println(head);
    }

}