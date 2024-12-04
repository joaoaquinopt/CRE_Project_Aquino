Feature: User Registration into MagePlaza WebSite

  Background:
    Given I am on the homepage

  Scenario Outline: Create a new user account with <browser>
    When I navigate to the Create an account page
    When I fill in account registration with valid values
    And I click the Create an Account button
    Then I should see a success message

    Examples:
      | browser |
      | chrome  |
      | firefox |