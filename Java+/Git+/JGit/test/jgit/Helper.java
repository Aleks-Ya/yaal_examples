package jgit;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Helper {
    private static final String remoteRepoName = "remoteRepo";

    public static Repository makeLocalRepo() throws IOException {
        File repoDir = Files.createTempDirectory("JGit").toFile();
        Repository repo = new FileRepositoryBuilder()
                .setWorkTree(repoDir)
                .build();
        repo.create();
        return repo;
    }

    public static Repository makeRemoteBareRepo() throws IOException {
        DfsRepositoryDescription description = new DfsRepositoryDescription(remoteRepoName);
        Repository repo = new InMemoryRepository(description);
        repo.create(false);
        return repo;
    }
}