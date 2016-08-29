package clone_fetch;

import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

/**
 * Работа с локальными ветками: список, создание, удаление, переименование.
 */
public class LocalBranches {

    public static void main(String[] args) throws IOException, GitAPIException, URISyntaxException {
        Repository repo = makeRepo();

        Git git = new Git(repo);

        CommitCommand commit = git.commit();
        RevCommit initCommit = commit.setMessage("initial commit").call();

        CreateBranchCommand createBranchCommand = git.branchCreate();
        createBranchCommand.setName("dev");
        createBranchCommand.setStartPoint(initCommit);
        Ref devBranch = createBranchCommand.call();
        System.out.printf("Branch created: %s\n", devBranch.getName());

        System.out.println("ALL BRANCHES: ");
        ListBranchCommand listBranchCommand = git.branchList();
        List<Ref> refs = listBranchCommand.call();
        for (Ref ref : refs) {
            System.out.println(ref.getName());
        }

        ObjectId head = repo.resolve("HEAD");
        System.out.println(head);
    }

    private static Repository makeRepo() throws IOException {
        File repoDir = Files.createTempDirectory("JGit").toFile();
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        builder.setWorkTree(repoDir);
        Repository repo = builder.build();
        repo.create();
        return repo;
    }
}
