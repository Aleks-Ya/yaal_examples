from authlib.integrations.requests_client import OAuth2Session

def authenticate():
    client: OAuth2Session = OAuth2Session(
        client_id='xxx',
        client_secret='xxx',
        scope='openid profile email',
        redirect_uri=''
    )