Feature: User Add Item to Shopping Cart and Purchase the product

  Background:
    Given I am on the homepage

  Scenario Outline: Successful purchase of a product with <browser>
    When I am authenticated with "<email>" and "<password>"
    And I enter "<itemName>" in the search bar
    And I click the Search button icon
    And I click on the item that contain "<name>"
    And I click on the Add to cart button
    And I click on the cart icon
    And I should see a clickable proceed to checkout button
    And I already have my account details filled
    And I select a payment method
    And I confirm the order
    Then I should see a confirmation message
    And I click on the logOut button

    Examples:
      | browser | email                  | password  | itemName | name                |
      | chrome  | pubodug@mailinator.com | Pa$$w0rd! | running  | Sinbad Fitness Tank |
      | firefox | gucycy@mailinator.com  | Pa$$w0rd! | running  | Sinbad Fitness Tank |