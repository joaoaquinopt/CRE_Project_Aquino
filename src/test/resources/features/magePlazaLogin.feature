Feature: Login on the Mage Plaza WebSite

  Background:
    Given I am on the homepage

  Scenario Outline: Login on the mage plaza with <browser>
    When I navigate to the Sign In page
    And I fill in the "<email>" and "<password>"
    Then I click on the Sign In button
    And I should be logged in successfully
    And I click on the logOut button

    Examples:
      | browser | email                     | password  |
      | chrome  | kesaralija@mailinator.com | Pa$$w0rd! |
      | firefox | gugeja@mailinator.com     | Pa$$w0rd! |