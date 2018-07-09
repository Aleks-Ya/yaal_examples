# Send an email via GMail SMTP server
import datetime
import smtplib

smtp_server = "smtp.gmail.com"
smtp_port = 587
smtp_user = "ahml.notify@gmail.com"
smtp_password = "<enter>"
mail_from = smtp_user
mail_to = smtp_user
subject = "Hi, SMTP!"
text = "I'm your postman " + str(datetime.datetime.now().time())

server = smtplib.SMTP(host=smtp_server, port=smtp_port)
server.starttls()
server.login(smtp_user, smtp_password)

result = server.verify(mail_to)
print(result)

message = 'Subject: {}\n\n{}'.format(subject, text)
server.sendmail(mail_from, mail_to, message)
server.quit()
