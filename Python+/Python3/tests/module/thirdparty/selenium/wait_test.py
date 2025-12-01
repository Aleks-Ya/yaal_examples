from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.webdriver import WebDriver
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webdriver import WebDriver
from selenium.webdriver.support import expected_conditions
from selenium.webdriver.support.wait import WebDriverWait


def test_wait_xpath():
    options: Options = Options()
    options.add_argument('--headless')
    driver: WebDriver = webdriver.Chrome(options=options)
    driver.get('https://ankiweb.net/shared/info/1188705668')

    wait: WebDriverWait = WebDriverWait(driver, 10)
    wait.until(
        expected_conditions.presence_of_element_located((By.XPATH, "/html/body/div/main"))
    )

    html: str = driver.page_source
    print(html)
    driver.quit()
