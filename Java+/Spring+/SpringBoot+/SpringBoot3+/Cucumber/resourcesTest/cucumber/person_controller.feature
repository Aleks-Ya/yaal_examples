Feature: the person name can be retrieved

  Background:
    Given create person "John" 30
    And create person "Mary" 25

  Scenario: client makes call to GET /person
    When the client calls person name
    Then the client receives status code 200
    And the client receives client name John

  Scenario: client gets John age
    When the client calls age for person "John"
    Then the client receives status code of /age 200
    And the client receives client age 30

  Scenario: client gets Mary age
    When the client calls age for person "Mary"
    Then the client receives status code of /age 200
    And the client receives client age 25

  Scenario: client get Mike
    When the client calls age for person "Mike"
    Then the client receives status code of /age 200
    And the client receives client age -1

  Scenario Outline: client gets person's age (using Examples)
    When the client calls age for person "<name>"
    Then the client receives status code of /age 200
    And the client receives client age <age>
    Examples:
      | name | age |
      | John | 30  |
      | Mary | 25  |
      | Mike | -1  |