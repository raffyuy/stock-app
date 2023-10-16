Feature: Stock Monthly Summary

  Scenario: As a grazier, I want to view the summary for a month-year
    Given I have loaded the monthly summary app
    When I view the summary for stockId 2
    Then I should see the following details for Dec-2020 on the first row of the table:
      | Month     | Average Rating | Final Head | Final Weight | Final Price | Number of Updates | Month Head Change |
      | Dec-2020  | 8.33165        | 310        | 295.829      | 1617.68     | 1                 | 0                 |
    And I should see a list of the remaining 6 month-years for stockId 2 with their respective summary data

