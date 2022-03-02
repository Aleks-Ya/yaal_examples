# scp CLI

via scp
Copy files to remote machine

Команда копрования папки files на сервер qa.trs (порт 6668) в папку /opt под пользователем voltdb утилитой scp:
scp -r files voltdb@qa.trs:/opt -P 6668