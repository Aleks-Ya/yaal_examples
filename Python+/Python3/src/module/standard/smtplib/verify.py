# Verify that email exists (GMail returns success in any case)
import smtplib

smtp_server = "smtp.gmail.com"
smtp_port = 587
mail_to = "239hzdppq082p8tbj@mail.com"

server = smtplib.SMTP(host=smtp_server, port=smtp_port)

result = server.verify(mail_to)
print(result)

server.quit()
