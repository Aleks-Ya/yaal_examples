package evernote;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;


class EvernoteTest {
    @Test
    void listNotebooks() throws TException, EDAMSystemException, EDAMUserException {
        var tokenFile = new File(FileUtil.getUserHome(), ".evernote-api/evernote-token.txt");
        var token = FileUtil.fileToString(tokenFile);
        assertThat(token).isNotBlank();

        var evernoteAuth = new EvernoteAuth(EvernoteService.SANDBOX, token);
        var factory = new ClientFactory(evernoteAuth);
        var userStore = factory.createUserStoreClient();

        var versionOk = userStore.checkVersion("Evernote EDAMDemo (Java)",
                com.evernote.edam.userstore.Constants.EDAM_VERSION_MAJOR,
                com.evernote.edam.userstore.Constants.EDAM_VERSION_MINOR);
        assertThat(versionOk).isTrue();

        // Set up the NoteStore client
        var noteStore = factory.createNoteStoreClient();
        var notebooks = noteStore.listNotebooks();
        System.out.println(notebooks);
    }
}
