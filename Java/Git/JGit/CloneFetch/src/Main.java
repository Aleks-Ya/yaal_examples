import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, GitAPIException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setGitDir(new File("/home/alexx/examples/.git"))
                .readEnvironment()
                .findGitDir()
                .build();

        ObjectId head = repository.resolve("HEAD");
        System.out.println(head);

        Git git = new Git(repository);

        CredentialsProvider credentials = new UsernamePasswordCredentialsProvider("", "");
        FetchCommand fetchCommand = git.fetch();
        fetchCommand.setCredentialsProvider(credentials);
        FetchResult fetchResult = fetchCommand.call();
        System.out.println(fetchResult);
    }
}
