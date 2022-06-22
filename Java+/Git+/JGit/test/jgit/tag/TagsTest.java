package jgit.tag;

import jgit.Helper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Working with tags.
 */
class TagsTest {

    @Test
    void createTag() throws IOException, GitAPIException {
        var repo = Helper.makeLocalRepo();
        var git = new Git(repo);

        var initCommit = git.commit().setMessage("initial commit").call();

        var tagCommand = git.tag();
        var tagShortName = "my_tag_1";
        tagCommand.setName(tagShortName);
        tagCommand.setObjectId(initCommit);
        var expTag = tagCommand.call();

        var tagList = git.tagList().call();
        assertThat(tagList).hasSize(1);
        var actTag = tagList.get(0);
        assertThat(actTag).isEqualTo(expTag);
        assertThat(actTag.getName()).isEqualTo("refs/tags/" + tagShortName);
    }

    @Test
    void tagToCommit() throws IOException, GitAPIException {
        var repo = Helper.makeLocalRepo();
        var git = new Git(repo);

        var expCommit = git.commit().setMessage("initial commit").call();

        var tagShortName = "my_tag_1";
        var tag = git.tag()
                .setName(tagShortName)
                .setObjectId(expCommit)
                .call();

        var actCommit = repo.parseCommit(tag.getObjectId());
        assertThat(actCommit).isEqualTo(expCommit);
    }

}
