Feature: Tag feature 1
  Using Cucumber Tags for filtering Scenarios

  @Smoke
  Scenario: Handle a String parameter
    Given TAGS my string is "peace"
    When TAGS Convert my string
    Then TAGS Converted string should be "PEACE"

  @Smoke
  Scenario: Handle a String parameter
    Given TAGS my string is "city"
    When TAGS Convert my string
    Then TAGS Converted string should be "CITY"

  @Heavy
  Scenario: Handle a String parameter
    Given TAGS my string is "country"
    When TAGS Convert my string
    Then TAGS Converted string should be "COUNTRY"