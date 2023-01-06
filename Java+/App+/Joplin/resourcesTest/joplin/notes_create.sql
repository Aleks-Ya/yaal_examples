CREATE TABLE IF NOT EXISTS "notes" (
	"id"	TEXT,
	"parent_id"	TEXT NOT NULL DEFAULT "",
	"title"	TEXT NOT NULL DEFAULT "",
	"body"	TEXT NOT NULL DEFAULT "",
	"created_time"	INT NOT NULL,
	"updated_time"	INT NOT NULL,
	"is_conflict"	INT NOT NULL DEFAULT 0,
	"latitude"	NUMERIC NOT NULL DEFAULT 0,
	"longitude"	NUMERIC NOT NULL DEFAULT 0,
	"altitude"	NUMERIC NOT NULL DEFAULT 0,
	"author"	TEXT NOT NULL DEFAULT "",
	"source_url"	TEXT NOT NULL DEFAULT "",
	"is_todo"	INT NOT NULL DEFAULT 0,
	"todo_due"	INT NOT NULL DEFAULT 0,
	"todo_completed"	INT NOT NULL DEFAULT 0,
	"source"	TEXT NOT NULL DEFAULT "",
	"source_application"	TEXT NOT NULL DEFAULT "",
	"application_data"	TEXT NOT NULL DEFAULT "",
	"order"	NUMERIC NOT NULL DEFAULT 0,
	"user_created_time"	INT NOT NULL DEFAULT 0,
	"user_updated_time"	INT NOT NULL DEFAULT 0,
	"encryption_cipher_text"	TEXT NOT NULL DEFAULT "",
	"encryption_applied"	INT NOT NULL DEFAULT 0,
	"markup_language"	INT NOT NULL DEFAULT 1,
	"is_shared"	INT NOT NULL DEFAULT 0,
	"share_id"	TEXT NOT NULL DEFAULT "",
	"conflict_original_id"	TEXT NOT NULL DEFAULT "",
	"master_key_id"	TEXT NOT NULL DEFAULT "",
	PRIMARY KEY("id")
);

INSERT INTO "notes" ("id","parent_id","title","body","created_time","updated_time","is_conflict","latitude","longitude","altitude","author","source_url","is_todo","todo_due","todo_completed","source","source_application","application_data","order","user_created_time","user_updated_time","encryption_cipher_text","encryption_applied","markup_language","is_shared","share_id","conflict_original_id","master_key_id")
VALUES ('e6900575a9724851bdd8b02d2411967d','4b2503c75de64099b68262878dc240b8','Meal''s "shopping" list','- [x] Egg

- [x] Coffee

- [x] Wine',1669098050723,1669110282135,0,15.1449853,120.5887029,0,'','',0,0,0,'joplin-desktop','net.cozic.joplin-desktop','',0,1669098050723,1669110282135,'',0,1,0,'','','');

INSERT INTO "notes" ("id","parent_id","title","body","created_time","updated_time","is_conflict","latitude","longitude","altitude","author","source_url","is_todo","todo_due","todo_completed","source","source_application","application_data","order","user_created_time","user_updated_time","encryption_cipher_text","encryption_applied","markup_language","is_shared","share_id","conflict_original_id","master_key_id")
VALUES ('6ded77a0daca4ff3828a9241dd0ae0ed','4b2503c75de64099b68262878dc240b8','2016-09-26 email',
'<en-note><div> <p STYLE="margin-bottom: 0in; line-height: 100%">Hello John,</p> <p STYLE="margin-bottom: 0in; line-height: 100%"><br CLEAR="none"/> </p> <p STYLE="margin-bottom: 0in; line-height: 100%">Bye John</p> <p STYLE="margin-bottom: 0in; line-height: 100%">Paragraph #2</p> <p STYLE="margin-bottom: 0in; line-height: 100%"> Another paragraph </p> <br CLEAR="none"/></div></en-note>',
1474922269000,1669478641200,0,0,0,0,'aleks_ya','',0,0,0,'evernote','net.cozic.joplin-desktop','',0,1474922269000,1669478641200,'',0,2,0,'','','');

INSERT INTO "notes" ("id","parent_id","title","body","created_time","updated_time","is_conflict","latitude","longitude","altitude","author","source_url","is_todo","todo_due","todo_completed","source","source_application","application_data","order","user_created_time","user_updated_time","encryption_cipher_text","encryption_applied","markup_language","is_shared","share_id","conflict_original_id","master_key_id")
VALUES ('7e65ab82bf7841a09368df73a5114453','3730aed2438f48b6a92965addadd7aba','2015.09.24ЧТ','<p><en-note></en-note></p>
<div>Git pull</div>
<div></div>
<div>Git push</div>
<div></div>
' ,1485501730000,1669342691118,0,0,0,0,'mail@bk.ru','',0,0,0,'evernote.desktop.win','net.cozic.joplin-desktop','',0,1485501730000,1669342691118,'',0,2,0,'','','');

INSERT INTO "notes" ("id","parent_id","title","body","created_time","updated_time","is_conflict","latitude","longitude","altitude","author","source_url","is_todo","todo_due","todo_completed","source","source_application","application_data","order","user_created_time","user_updated_time","encryption_cipher_text","encryption_applied","markup_language","is_shared","share_id","conflict_original_id","master_key_id")
VALUES ('3ce4eb6d45d741718772f16c343b8ddd','87c985dca11b47f0bf9c39520d22e1cd','SSH install','**Linux:**
sudo apt-get install -y ssh

stfp-клиент с GUI

* * *
[Joplin link    1   ](:/db65929324925ccbfa789f95cdd293ba)
[Русская ссылка 1](:/ddc9f19456f64ade383ecdd76cf5b90d)
Windows:[](https://winscp.net)
[WinSCP](:/da4added37344f07a5ff2b9b2e1fdef3)[](evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/)',1410288598000,1670763796685,0,0,0,0,'aleks_ya','',0,0,0,'evernote','net.cozic.joplin-desktop','',0,1410288598000,1670589608896,'',0,1,0,'','','');

INSERT INTO "notes" ("id","parent_id","title","body","created_time","updated_time","is_conflict","latitude","longitude","altitude","author","source_url","is_todo","todo_due","todo_completed","source","source_application","application_data","order","user_created_time","user_updated_time","encryption_cipher_text","encryption_applied","markup_language","is_shared","share_id","conflict_original_id","master_key_id")
VALUES ('63551b448cf64362a1d747561b737c83','288e394997d34857a89df78f68970b46','Risk Management stages','1.  [Plan Risk
    Management](evernote:///view/48821034/s241/2fb4b9dd-7fbd-414c-831c-fc4c75c25d90/2fb4b9dd-7fbd-414c-831c-fc4c75c25d90/)
2.  Identify risks
3.  Control risks',1545604134000,1669447516001,0,0,0,0,'aleks_ya','',0,0,0,'evernote','net.cozic.joplin-desktop','',0,1545604134000,1669447516000,'',0,1,0,'','','');

INSERT INTO "notes" ("id","parent_id","title","body","created_time","updated_time","is_conflict","latitude","longitude","altitude","author","source_url","is_todo","todo_due","todo_completed","source","source_application","application_data","order","user_created_time","user_updated_time","encryption_cipher_text","encryption_applied","markup_language","is_shared","share_id","conflict_original_id","master_key_id")
VALUES ('a2d7d7efe84a47bf8ffde18121477efd','2d6bf8a32304447596089a871ea529a1','Discourse marker list','See also:

* [(ADDING) /discourse?markers:picture](evernote:///view/48821034/s241/7ad1af77-cd94-4c8d-9798-88fb41d984c7/e4ace13e-bf3d-41c9-a971-e8f3b9a823e3)

* [SUMMARY\''s discourse@markers-picture#2](evernote:///view/48821034/s241/9e683802-9dab-4dfb-9daa-0608853230cb/e4ace13e-bf3d-41c9-a971-e8f3b9a823e3)

* [CONTRAST'' "discourse" markers.picture \"quotes\"](evernote:///view/48821034/s241/c438c7df-d101-4172-bedd-0c47d67cc636/e4ace13e-bf3d-41c9-a971-e8f3b9a823e3)

* [Meal\''s \"shopping\"
list](evernote:///view/48821034/s241/f6970881-a927-49dc-a73b-a7cc5c9348b3/f6970881-a927-49dc-a73b-a7cc5c9348b3/)
### Separated by formality

|     |     |     |     |
| --- | --- | --- | --- |
| cell 11 | cell 12 | cell 13 | cell 14 |
| cell 21 | cell 22 | cell 23 | cell 24 |
| cell 31 | cell 32 | cell 33 | cell 34 |

![image.png](:/e88f157f496c121b78b7bcd562cac7e7)
[Source](https://www.academia.edu/6888756/DISCOURSE_MARKERS_CONNECTORS_A_LIST_OF_DISCOURSE_MARKERS_WITH_EXAMPLES)
',1580392257000,1670158519744,0,0,0,0,'','',0,0,0,'joplin-desktop','net.cozic.joplin-desktop','',1669460702671,1580392257000,1670158519743,'',0,1,0,'','','');

INSERT INTO "notes" ("id","parent_id","title","body","created_time","updated_time","is_conflict","latitude","longitude","altitude","author","source_url","is_todo","todo_due","todo_completed","source","source_application","application_data","order","user_created_time","user_updated_time","encryption_cipher_text","encryption_applied","markup_language","is_shared","share_id","conflict_original_id","master_key_id")
VALUES ('ba9bdb7bc5444d5b85bdabfd9a211337','0d0acacf67a540bd83d5eb3428f2b3a7','Русский заголовок 1','Русский
текст 1

[Русское название
статьи 2, запятая](evernote:///view/48821034/s241/87b5042e-7d6e-40bc-b434-2f4daf68722a/87b5042e-7d6e-40bc-b434-2f4daf68722a/)
[Android
app](https://play.google.com/store/apps/details?id=net.cubux.android_v2&hl=ru&gl=US)
[web
version](https://app.pomodoneapp.com "https://app.pomodoneapp.com")
[ticket is in<br>progress](https://github.com/laurent22/joplin/issues/375)
[Список аккредитованных<br>УЦ](https://digital.gov.ru/ru/activity/govservices/2/#section-list-of-accredited-organizations)
  ',1547501767000,1547501955001,0,0,0,0,'aleks_ya','',0,0,0,'evernote','net.cozic.joplin-desktop','',0,1547501767000,1547501955000,'',0,1,0,'','','');

INSERT INTO "notes" ("id","parent_id","title","body","created_time","updated_time","is_conflict","latitude","longitude","altitude","author","source_url","is_todo","todo_due","todo_completed","source","source_application","application_data","order","user_created_time","user_updated_time","encryption_cipher_text","encryption_applied","markup_language","is_shared","share_id","conflict_original_id","master_key_id")
VALUES ('bf941399ecf6497b98693f618d2798bd','dbe1644c85c34e8092b2779e5f78e99e','Русское название статьи 2, запятая','Русский
текст 2
{width=100
height=200}
[Word Document](:/014ad7b70d5ba80ff06b897cb3dd8db5)
  ',1398929637000,1670335098337,0,0,0,0,'','',0,0,0,'joplin-desktop','net.cozic.joplin-desktop','',1669470821852,1398929637000,1471150622000,'',0,1,0,'','','');

INSERT INTO "notes" ("id","parent_id","title","body","created_time","updated_time","is_conflict","latitude","longitude","altitude","author","source_url","is_todo","todo_due","todo_completed","source","source_application","application_data","order","user_created_time","user_updated_time","encryption_cipher_text","encryption_applied","markup_language","is_shared","share_id","conflict_original_id","master_key_id")
VALUES ('373b03cd772f451db9a96972d81ac6f6','67bc56efd5544524b7a22e17ce3273d9','Title with date 27.08.16',
'The body of the note with a date in title',1471742195000,1669349290001,0,0,0,0,'aleks_ya','',0,0,0,'evernote','net.cozic.joplin-desktop','',0,1471742195000,1669349290000,'',0,1,0,'','','');
