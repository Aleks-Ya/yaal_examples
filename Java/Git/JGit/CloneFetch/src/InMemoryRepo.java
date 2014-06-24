import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

/**
 * Работа с репозитарием-в-ОЗУ (InMemoryRepository).
 * TODO неготово
 */
public class InMemoryRepo {
    private static final String remoteRepoName = "remoteRepo";

    public static void main(String[] args) throws IOException, GitAPIException, URISyntaxException {
        Repository repo = makeRemoteRepo();

        Git git = new Git(repo);

        CommitCommand commit = git.commit();
        commit.setMessage("initial commit").call();

//        AddCommand addCommand = git.add();
//        addCommand.addFilepattern("*.*");
//        DirCache dirCache = addCommand.call();

//        CommitCommand commitCommand = git.commit();
//        RevCommit revCommit = commitCommand.call();

        ObjectId head = repo.resolve("HEAD");
        System.out.println(head);
    }

    private static Repository makeLocalRepo() throws IOException {
        File repoDir = Files.createTempDirectory("JGit").toFile();
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        builder.setWorkTree(repoDir);
        Repository repo = builder.build();
        repo.create();
        return repo;
    }

    private static Repository makeRemoteRepo() throws IOException {
        DfsRepositoryDescription description = new DfsRepositoryDescription(remoteRepoName);
        Repository repo = new InMemoryRepository(description);
        repo.create(false);
        return repo;
    }
}