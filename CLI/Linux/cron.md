# cron CLI

Display crontab file for current user: `crontab -l`
Display crontab file for root:
```
sudo crontab -lu root
#or
sudo crontab -l
```
Edit crontab file: `crontab -e`
Distplay cron history:
```
grep CRON /var/log/syslog
#or
sudo service cron status
```
