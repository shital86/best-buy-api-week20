@products
Feature: As a user I would like to preform CURD test on products endpoint

  Scenario: I read all products data from the application
    Given I am on homepage of products endpoint
    When I send a GET request to the products endpoint
    Then I must get a valid response code 200 from products endpoint

  Scenario: I create a new product on products end point
    Given I am on homepage of products endpoint
    When I send a POST request with a valid payload to the products endpoint
    Then I must get a valid response code 200 from products endpoint

  Scenario: I read a newly created product on products end point
    Given I am on homepage of products endpoint
    When I send a GET request to newly created product with product ID
    Then I must get a valid response code 200 from products endpoint
    And I verify if the product is created with correct details

  Scenario: I update a newly created product on products end point
    Given I am on homepage of products endpoint
    When I send a PUT request to newly created product with product ID
    Then I must get a valid response code 200 from products endpoint
    And I verify if the product details is updated with correct details

  Scenario: I delete a newly created product on products end point
    Given I am on homepage of products endpoint
    When I send a DELETE request to newly created product with product ID
    Then I must get a valid response code 200 from products endpoint
    And I verify if the product is deleted

