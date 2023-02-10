Feature: Tag feature 2
  Using Cucumber Tags for filtering Scenarios

  @Smoke
  Scenario: Handle a String parameter
    Given TAGS my string is "table"
    When TAGS Convert my string
    Then TAGS Converted string should be "TABLE"

  @Heavy
  Scenario: Handle a String parameter
    Given TAGS my string is "world"
    When TAGS Convert my string
    Then TAGS Converted string should be "WORLD"