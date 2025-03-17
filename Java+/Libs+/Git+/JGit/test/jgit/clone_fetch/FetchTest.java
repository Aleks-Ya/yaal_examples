package jgit.clone_fetch;

import jgit.Helper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Executing fetch.
 */
class FetchTest {
    private static final String remoteRepoName = "remoteRepo";

    @Test
    @Disabled("fail")
    void main() throws IOException, GitAPIException {
        var localRepo = Helper.makeLocalRepo();
        var remoteRepo = Helper.makeRemoteBareRepo();

//        StoredConfig config = localRepo.getConfig();
//        RemoteConfig remoteConfig = new RemoteConfig(config, remoteRepo.getDirectory().getAbsolutePath());

        var git = new Git(localRepo);

        var commit = git.commit();
        commit.setMessage("initial commit").call();

        var pushCommand = git.push();
        pushCommand.setRemote(remoteRepoName);
        var pushResults = pushCommand.call();

//        AddCommand addCommand = git.add();
//        addCommand.addFilepattern("*.*");
//        DirCache dirCache = addCommand.call();

//        CommitCommand commitCommand = git.commit();
//        RevCommit revCommit = commitCommand.call();

        var head = localRepo.resolve("HEAD");
        System.out.println(head);

//
//        CredentialsProvider credentials = new UsernamePasswordCredentialsProvider("", "");
//        FetchCommand fetchCommand = git.fetch();
//        fetchCommand.setCredentialsProvider(credentials);
//        FetchResult fetchResult = fetchCommand.call();
//        System.out.println(fetchResult);
    }

}
