Feature: User should Search and Visualize Itens

  Background:
    Given I am on the homepage

  Scenario Outline: Successful search and view product details on "<browser>"
    When I enter "<itemName>" in the search bar
    And I click the Search button icon
    Then I should see a list of products related to "<itemName>"
    When I click on the item that contain "<name>"
    Then I should be redirected to the product details page for "<name>"
    And I should see the "<name>", "<price>", "<description>"

    Examples:
      | browser | itemName | name                | price  | description |
      | chrome  | running  | Sinbad Fitness Tank | $29.00 | Details     |
      | firefox | running  | Sinbad Fitness Tank | $29.00 | Details     |