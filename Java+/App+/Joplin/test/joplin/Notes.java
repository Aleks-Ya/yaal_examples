package joplin;

import joplin.common.link.Link;
import joplin.common.note.MarkupLanguage;
import joplin.common.note.Note;
import joplin.common.note.NoteId;
import joplin.common.note.NotebookId;
import joplin.common.resource.Resource;
import joplin.common.resource.ResourceId;

import java.io.File;
import java.util.List;

public class Notes {
    public static final Note NOTE_1 = new Note(new NoteId("3ce4eb6d45d741718772f16c343b8ddd"),
            new NotebookId("87c985dca11b47f0bf9c39520d22e1cd"),
            "SSH install",
            "**Linux:**\nsudo apt-get install -y ssh\n\nstfp-клиент с GUI\n\n* * *\n[Joplin link    1   ](:/db65929324925ccbfa789f95cdd293ba)\n[Русская ссылка 1](:/ddc9f19456f64ade383ecdd76cf5b90d)\nWindows:[](https://winscp.net)\n[WinSCP](:/da4added37344f07a5ff2b9b2e1fdef3)[](evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/)",
            MarkupLanguage.MD,
            1670763796685L,
            1670589608896L,
            List.of(
                    new Link("[Joplin link    1   ](:/db65929324925ccbfa789f95cdd293ba)",
                            "Joplin link    1   ", ":/db65929324925ccbfa789f95cdd293ba",
                            new Resource(
                                    new ResourceId("db65929324925ccbfa789f95cdd293ba"),
                                    new File("/home/aleks/pr/home/yaal_examples/Java+/App+/Joplin/build/resources/main/joplin/common/resource/resources/db65929324925ccbfa789f95cdd293ba.pdf"))
                    ),
                    new Link("[WinSCP](:/da4added37344f07a5ff2b9b2e1fdef3)", "WinSCP",
                            ":/da4added37344f07a5ff2b9b2e1fdef3",
                            new Resource(
                                    new ResourceId("da4added37344f07a5ff2b9b2e1fdef3"),
                                    new File("/home/aleks/pr/home/yaal_examples/Java+/App+/Joplin/build/resources/main/joplin/common/resource/resources/da4added37344f07a5ff2b9b2e1fdef3.txt"))
                    ),
                    new Link("[](evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/)",
                            "",
                            "evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/",
                            null),
                    new Link("[](https://winscp.net)",
                            "",
                            "https://winscp.net",
                            null),
                    new Link("[Русская ссылка 1](:/ddc9f19456f64ade383ecdd76cf5b90d)",
                            "Русская ссылка 1", ":/ddc9f19456f64ade383ecdd76cf5b90d",
                            null
                    )
            )
    );

    public static final Note NOTE_2 = new Note(new NoteId("bf941399ecf6497b98693f618d2798bd"),
            new NotebookId("dbe1644c85c34e8092b2779e5f78e99e"),
            "Русское название статьи 2, запятая",
            "Русский\nтекст 2\n{width=100\nheight=200}\n[Word Document](:/014ad7b70d5ba80ff06b897cb3dd8db5)\n  ",
            MarkupLanguage.MD,
            1670335098337L,
            1471150622000L,
            List.of(new Link(
                    "[Word Document](:/014ad7b70d5ba80ff06b897cb3dd8db5)",
                    "Word Document", ":/014ad7b70d5ba80ff06b897cb3dd8db5",
                    new Resource(
                            new ResourceId("014ad7b70d5ba80ff06b897cb3dd8db5"),
                            new File("/home/aleks/pr/home/yaal_examples/Java+/App+/Joplin/build/resources/main/joplin/common/resource/resources/014ad7b70d5ba80ff06b897cb3dd8db5.docx")
                    ))
            ));

    public static final Note NOTE_3 = new Note(new NoteId("ba9bdb7bc5444d5b85bdabfd9a211337"),
            new NotebookId("0d0acacf67a540bd83d5eb3428f2b3a7"),
            "Русский заголовок 1",
            "Русский\nтекст 1\n\n[Русское название\nстатьи 2](evernote:///view/48821034/s241/87b5042e-7d6e-40bc-b434-2f4daf68722a/87b5042e-7d6e-40bc-b434-2f4daf68722a/)\n\n  ",
            MarkupLanguage.MD,
            1547501955001L,
            1547501955000L,
            List.of(new Link("[Русское название\nстатьи 2, запятая](evernote:///view/48821034/s241/87b5042e-7d6e-40bc-b434-2f4daf68722a/87b5042e-7d6e-40bc-b434-2f4daf68722a/)",
                            "Русское название\nстатьи 2, запятая", "evernote:///view/48821034/s241/87b5042e-7d6e-40bc-b434-2f4daf68722a/87b5042e-7d6e-40bc-b434-2f4daf68722a/",
                            null),
                    new Link("[Android\napp](https://play.google.com/store/apps/details?id=net.cubux.android_v2&hl=ru&gl=US)",
                            "Android\napp", "https://play.google.com/store/apps/details?id=net.cubux.android_v2&hl=ru&gl=US",
                            null),
                    new Link("[web\nversion](https://app.pomodoneapp.com \"https://app.pomodoneapp.com\")",
                            "web\nversion", "https://app.pomodoneapp.com",
                            null),
                    new Link("[ticket is in<br>progress](https://github.com/laurent22/joplin/issues/375)",
                            "ticket is in<br>progress", "https://github.com/laurent22/joplin/issues/375",
                            null),
                    new Link("[Список аккредитованных<br>УЦ](https://digital.gov.ru/ru/activity/govservices/2/#section-list-of-accredited-organizations)",
                            "Список аккредитованных<br>УЦ", "https://digital.gov.ru/ru/activity/govservices/2/#section-list-of-accredited-organizations",
                            null),
                    new Link("[Удаленная работа --- это не\n«фриланс»](http://habrahabr.ru/post/199114/)",
                            "Удаленная работа --- это не\n«фриланс»", "http://habrahabr.ru/post/199114/",
                            null),
                    new Link("[\"Cross Country\"<br>(Эстосадок)](https://cross-country.ru/%d0%bd%d0%b0%d1%87%d0%b0%d1%82%d1%8c/)",
                            "\"Cross Country\"<br>(Эстосадок)", "https://cross-country.ru/%d0%bd%d0%b0%d1%87%d0%b0%d1%82%d1%8c/",
                            null),
                    new Link("[Gulp - Hydrate & track<br>water](https://play.google.com/store/apps/details?id=info.andrewdahm.gulp)",
                            "Gulp - Hydrate & track<br>water", "https://play.google.com/store/apps/details?id=info.andrewdahm.gulp",
                            null)
            ));
}
