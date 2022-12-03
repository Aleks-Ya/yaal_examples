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
INSERT INTO "notes" ("id","parent_id","title","body","created_time","updated_time","is_conflict","latitude","longitude","altitude","author","source_url","is_todo","todo_due","todo_completed","source","source_application","application_data","order","user_created_time","user_updated_time","encryption_cipher_text","encryption_applied","markup_language","is_shared","share_id","conflict_original_id","master_key_id") VALUES ('e6900575a9724851bdd8b02d2411967d','4b2503c75de64099b68262878dc240b8','Meal shopping list','- [x] Egg

- [x] Coffee

- [x] Wine',1669098050723,1669110282135,0,15.1449853,120.5887029,0,'','',0,0,0,'joplin-desktop','net.cozic.joplin-desktop','',0,1669098050723,1669110282135,'',0,1,0,'','','');
INSERT INTO "notes" ("id","parent_id","title","body","created_time","updated_time","is_conflict","latitude","longitude","altitude","author","source_url","is_todo","todo_due","todo_completed","source","source_application","application_data","order","user_created_time","user_updated_time","encryption_cipher_text","encryption_applied","markup_language","is_shared","share_id","conflict_original_id","master_key_id") VALUES ('6ded77a0daca4ff3828a9241dd0ae0ed','4b2503c75de64099b68262878dc240b8','2016-09-26 email',
'<en-note><div> <p STYLE="margin-bottom: 0in; line-height: 100%">Hello John,</p> <p STYLE="margin-bottom: 0in; line-height: 100%"><br CLEAR="none"/> </p> <p STYLE="margin-bottom: 0in; line-height: 100%">Bye John</p> <p STYLE="margin-bottom: 0in; line-height: 100%">Paragraph #2</p> <p STYLE="margin-bottom: 0in; line-height: 100%"> Another paragraph </p> <br CLEAR="none"/></div></en-note>',
1474922269000,1669478641200,0,0,0,0,'aleks_ya','',0,0,0,'evernote','net.cozic.joplin-desktop','',0,1474922269000,1669478641200,'',0,2,0,'','','');
INSERT INTO "notes" ("id","parent_id","title","body","created_time","updated_time","is_conflict","latitude","longitude","altitude","author","source_url","is_todo","todo_due","todo_completed","source","source_application","application_data","order","user_created_time","user_updated_time","encryption_cipher_text","encryption_applied","markup_language","is_shared","share_id","conflict_original_id","master_key_id") VALUES ('7e65ab82bf7841a09368df73a5114453','3730aed2438f48b6a92965addadd7aba','2015.09.24ЧТ','<p><en-note></en-note></p>
<div>Git pull</div>
<div></div>
<div></div>
<div></div>
<div>Git merge</div>
<div></div>
<div>John''s title</div>
<div></div>
<div>Git push</div>
<div></div>
' ,1485501730000,1669342691118,0,0,0,0,'mail@bk.ru','',0,0,0,'evernote.desktop.win','net.cozic.joplin-desktop','',0,1485501730000,1669342691118,'',0,2,0,'','','');
