# Use Fernet to encrypt a string
from cryptography.fernet import Fernet

cipher_key = Fernet.generate_key()
print("cipher_key:", cipher_key)

cipher = Fernet(cipher_key)

text = b'My super secret message'
print("text:", text)

encrypted_text = cipher.encrypt(text)
print("encrypted_text:", encrypted_text)

decrypted_text = cipher.decrypt(encrypted_text)
print("decrypted_text:", decrypted_text)

assert text == decrypted_text
