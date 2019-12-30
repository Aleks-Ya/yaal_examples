package app.ahml;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PrintNotDeliveredTags {
    private static final String ORIGIN_PROD_BRANCH = "origin/prod";
    private static final String ORIGIN_DEVELOP_BRANCH = "origin/develop";

    public static void main(String[] args) throws IOException, GitAPIException {
        List<File> gitRepoList = findGitRepos();
        List<RepoInfo> repoInfoList = processRepos(gitRepoList);
        printProdBranchNotFound(repoInfoList);
        printRepoWithoutNewTags(repoInfoList);
        printRepoWithNewTags(repoInfoList);
        System.out.println();
    }

    private static void printProdBranchNotFound(List<RepoInfo> repoInfoList) {
        List<String> repos = repoInfoList.stream()
                .filter(repoInfo -> !repoInfo.originBranchFound)
                .map(repoInfo -> repoInfo.repoName)
                .collect(Collectors.toList());
        System.out.printf("Branch %s not found in %d repos: %s\n\n", ORIGIN_PROD_BRANCH, repos.size(), repos);
    }

    private static void printRepoWithoutNewTags(List<RepoInfo> repoInfoList) {
        List<String> repos = repoInfoList.stream()
                .filter(repoInfo -> repoInfo.originBranchFound)
                .filter(repoInfo -> repoInfo.tags.isEmpty())
                .map(repoInfo -> repoInfo.repoName)
                .collect(Collectors.toList());
        System.out.printf("No new tags found in %d repos: %s\n\n", repos.size(), repos);
    }

    private static void printRepoWithNewTags(List<RepoInfo> repoInfoList) {
        List<RepoInfo> repos = repoInfoList.stream()
                .filter(repoInfo -> repoInfo.originBranchFound)
                .filter(repoInfo -> !repoInfo.tags.isEmpty())
                .collect(Collectors.toList());
        System.out.printf("New tags found in %d repos\n", repos.size());
        for (RepoInfo repoInfo : repos) {
            System.out.printf("\nRepo: %s\n", repoInfo.repoName);
            System.out.printf("Date of origin/prod branch: %s\n", repoInfo.originBranchCreated);
            for (TagInfo tagInfo : repoInfo.tags) {
                String mergedInDevelop = tagInfo.isInOriginDevelopBranch ?
                        "merged in " + ORIGIN_DEVELOP_BRANCH : "not merged in " + ORIGIN_DEVELOP_BRANCH;
                System.out.printf("Tag %s: %s (%s)\n", tagInfo.tagName, tagInfo.tagCreated, mergedInDevelop);
            }
        }
    }

    private static List<RepoInfo> processRepos(List<File> gitRepoList) throws IOException, GitAPIException {
        List<RepoInfo> repoInfoList = new ArrayList<>();
        for (File gitRepo : gitRepoList) {
            Repository repo = new FileRepositoryBuilder()
                    .setWorkTree(gitRepo)
                    .build();
            Git git = new Git(repo);

            ObjectId originProdObjectId = repo.resolve(ORIGIN_PROD_BRANCH);
            if (originProdObjectId == null) {
                repoInfoList.add(new RepoInfo(gitRepo.getName(), null, false, new ArrayList<>()));
                continue;
            }
            RevCommit originProdCommit = repo.parseCommit(originProdObjectId);
            Instant originProdCommitInstant = readCommitTime(originProdCommit);

            List<Ref> tagList = git.tagList().call();
            List<TagInfo> tagInfoList = new ArrayList<>();
            for (Ref tag : tagList) {
                RevCommit commit = repo.parseCommit(tag.getObjectId());
                Instant commitInstant = readCommitTime(commit);
                if (commitInstant.isAfter(originProdCommitInstant)) {
                    ObjectId devBranchId = repo.resolve(ORIGIN_DEVELOP_BRANCH);
                    Iterable<RevCommit> devBranchLog = git.log().add(devBranchId).call();
                    List<ObjectId> devBranchCommits = StreamSupport.stream(devBranchLog.spliterator(), false)
                            .map(ObjectId::toObjectId)
                            .collect(Collectors.toList());
                    boolean isInOriginDevelopBranch = devBranchCommits.contains(commit);
                    tagInfoList.add(new TagInfo(tag.getName(), commitInstant, isInOriginDevelopBranch));
                }
            }

            repoInfoList.add(new RepoInfo(gitRepo.getName(), originProdCommitInstant, true, tagInfoList));
        }
        return repoInfoList;
    }

    private static List<File> findGitRepos() throws IOException {
        String rootGitDirectory = "/home/aleks/pr/ahml";
        Path dir = Paths.get(rootGitDirectory);
        GitFileVisitor visitor = new GitFileVisitor();
        Files.walkFileTree(dir, visitor);
        List<File> gitRepoList = visitor.getGitRepoDirs();
        System.out.printf("Found %d Git repos: %s\n\n", gitRepoList.size(), gitRepoList);
        return gitRepoList;
    }

    private static class GitFileVisitor extends SimpleFileVisitor<Path> {
        private List<File> gitRepoDirs = new ArrayList<>();

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            if (dir.endsWith(".git")) {
                gitRepoDirs.add(dir.getParent().toFile());
            }
            return FileVisitResult.CONTINUE;
        }

        public List<File> getGitRepoDirs() {
            return gitRepoDirs;
        }
    }

    private static Instant readCommitTime(RevCommit commit) {
        return Instant.ofEpochSecond(commit.getCommitTime());
    }

    private static class RepoInfo {
        private final String repoName;
        private final Instant originBranchCreated;
        private final boolean originBranchFound;
        private final List<TagInfo> tags;

        private RepoInfo(String repoName, Instant originBranchCreated, boolean originBranchFound, List<TagInfo> tags) {
            this.repoName = repoName;
            this.originBranchCreated = originBranchCreated;
            this.originBranchFound = originBranchFound;
            this.tags = tags;
        }
    }

    private static class TagInfo {
        private final String tagName;
        private final Instant tagCreated;
        private final boolean isInOriginDevelopBranch;

        private TagInfo(String tagName, Instant tagCreated, boolean isInOriginDevelopBranch) {
            this.tagName = tagName;
            this.tagCreated = tagCreated;
            this.isInOriginDevelopBranch = isInOriginDevelopBranch;
        }
    }

}
