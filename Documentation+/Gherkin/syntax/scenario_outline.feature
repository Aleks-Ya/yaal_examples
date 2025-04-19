#noinspection CucumberUndefinedStep
Feature: Login functionality

  Scenario Outline: Successful login with valid credentials
    Given the user is on the login page
    When the user enters "<username>" and "<password>"
    And clicks the login button
    Then the user should be redirected to the dashboard

    Examples:
      | username | password |
      | user1    | pass123  |
      | user2    | pass456  |
      | user3    | pass789  |