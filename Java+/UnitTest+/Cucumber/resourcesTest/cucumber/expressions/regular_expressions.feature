Feature: Regular Expressions
  Everybody wants to know when it's Friday

  Scenario: Handle a String parameter
    Given REGULAR my string is "peace"
    When REGULAR Convert my string
    Then REGULAR Converted string should be "PEACE"