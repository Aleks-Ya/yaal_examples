package jgit.tag;

import jgit.Helper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TagCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Working with tags.
 */
public class Tags {

    @Test
    public void createTag() throws IOException, GitAPIException {
        Repository repo = Helper.makeLocalRepo();
        Git git = new Git(repo);

        RevCommit initCommit = git.commit().setMessage("initial commit").call();

        TagCommand tagCommand = git.tag();
        String tagShortName = "my_tag_1";
        tagCommand.setName(tagShortName);
        tagCommand.setObjectId(initCommit);
        Ref expTag = tagCommand.call();

        List<Ref> tagList = git.tagList().call();
        assertThat(tagList, hasSize(1));
        Ref actTag = tagList.get(0);
        assertThat(actTag, equalTo(expTag));
        assertThat(actTag.getName(), equalTo("refs/tags/" + tagShortName));
    }

    @Test
    public void tagToCommit() throws IOException, GitAPIException {
        Repository repo = Helper.makeLocalRepo();
        Git git = new Git(repo);

        RevCommit expCommit = git.commit().setMessage("initial commit").call();

        String tagShortName = "my_tag_1";
        Ref tag = git.tag()
                .setName(tagShortName)
                .setObjectId(expCommit)
                .call();

        RevCommit actCommit = repo.parseCommit(tag.getObjectId());
        assertThat(actCommit, equalTo(expCommit));
    }

}
