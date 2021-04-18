package jgit.clone_fetch;

import jgit.Helper;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PushResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Executing fetch.
 */
public class Fetch {
    private static final String remoteRepoName = "remoteRepo";

    @Test
    public void main() throws IOException, GitAPIException {
        Repository localRepo = Helper.makeLocalRepo();
        Repository remoteRepo = Helper.makeRemoteBareRepo();

//        StoredConfig config = localRepo.getConfig();
//        RemoteConfig remoteConfig = new RemoteConfig(config, remoteRepo.getDirectory().getAbsolutePath());

        Git git = new Git(localRepo);

        CommitCommand commit = git.commit();
        commit.setMessage("initial commit").call();

        PushCommand pushCommand = git.push();
        pushCommand.setRemote(remoteRepoName);
        Iterable<PushResult> pushResults = pushCommand.call();

//        AddCommand addCommand = git.add();
//        addCommand.addFilepattern("*.*");
//        DirCache dirCache = addCommand.call();

//        CommitCommand commitCommand = git.commit();
//        RevCommit revCommit = commitCommand.call();

        ObjectId head = localRepo.resolve("HEAD");
        System.out.println(head);

//
//        CredentialsProvider credentials = new UsernamePasswordCredentialsProvider("", "");
//        FetchCommand fetchCommand = git.fetch();
//        fetchCommand.setCredentialsProvider(credentials);
//        FetchResult fetchResult = fetchCommand.call();
//        System.out.println(fetchResult);
    }

}
