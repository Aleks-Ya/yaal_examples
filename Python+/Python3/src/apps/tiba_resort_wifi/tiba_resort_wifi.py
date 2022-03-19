# Login into Wi-Fi of Tiba Resort hotel

import hashlib
import os
import re
from configparser import ConfigParser
from pathlib import Path
from re import Pattern, Match

import requests
from requests import Response

print('Authenticating in Tiba Resort Wi-Fi...')
user_home_dir: Path = Path.home()
tiba_dir: Path = user_home_dir.joinpath('.tiba')
if not os.path.isdir(tiba_dir):
    raise IOError(f'Tiba dir absents: {tiba_dir}')
properties_file: Path = user_home_dir.joinpath('.tiba/wifi.ini')
if not os.path.isfile(properties_file):
    raise IOError(f'Properties file absents: {properties_file}')
config: ConfigParser = ConfigParser()
config.read(properties_file)
username: str = config['DEFAULT']['username']
password: str = config['DEFAULT']['password']

base_url: str = 'http://www.redseway.com'
status_url: str = base_url + '/status'
login_url: str = base_url + '/login'
google_url: str = 'https://google.com'

status_response: Response = requests.get(status_url, allow_redirects=False)
if status_response.status_code == 200:
    print('Already authorized')
if status_response.status_code == 302:
    location: str = status_response.headers['Location']
    if '/login' in location:
        login_get_response: Response = requests.get(login_url)
        body: str = str(login_get_response.content)
        pattern: Pattern = re.compile("hexMD5\(\\\\'(.*)' \+ document.login.password.value \+ \\\\'(.*)'\);")
        match: Match = re.search(pattern, body)
        group1: str = match.group(1)
        group2: str = match.group(2)
        password_salt_bytes: bytes = (group1 + password + group2).encode()
        password_salt: str = hashlib.md5(password_salt_bytes).hexdigest()
        login_post_response: Response = requests.post(login_url,
                                                      headers={'Content-Type': 'application/x-www-form-urlencoded'},
                                                      data={'username': username, 'password': password, 'dst': '',
                                                            'popup': 'true'})
        print(f'Authentication result: {login_post_response}')
        if login_post_response.status_code == 200:
            print(f'Authenticated')
            google_response: Response = requests.get(google_url)
            if google_response.status_code == 200:
                print('Google is available')
            else:
                print(f'Google is NOT available: {google_response}')
        else:
            print(f'Authentication error: {login_post_response}')
    elif '/status' in location:
        print('Connected to another Wi-Fi (not to Tiba)')
