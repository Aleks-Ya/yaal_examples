import time

from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.webdriver import WebDriver


def test_render():
    options: Options = Options()
    options.add_argument('--headless')
    driver: WebDriver = webdriver.Chrome(options=options)
    driver.get('https://ankiweb.net/shared/addons')
    time.sleep(3)
    final_html: str = driver.page_source
    print(final_html)
    driver.quit()
