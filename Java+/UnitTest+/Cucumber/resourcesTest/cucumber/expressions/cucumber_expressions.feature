Feature: Cucumber Expressions
  Everybody wants to know when it's Friday

  Scenario: Handle a String parameter
    Given CUCUMBER my string is "peace"
    When CUCUMBER Convert my string
    Then CUCUMBER Converted string should be "PEACE"