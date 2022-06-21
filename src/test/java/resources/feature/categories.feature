@categories
Feature: As a user I would like to preform CURD test on categories endpoint

  Scenario: I read all categories data from the application
    Given I am on homepage of categories endpoint
    When I send a GET request to the categories endpoint
    Then I must get a valid response code 200 from categories endpoint

  Scenario: I create a new category on categories end point
    Given I am on homepage of categories endpoint
    When I send a POST request with a valid payload to the categories endpoint
    Then I must get a valid response code 201 from categories endpoint

  Scenario: I read a newly created category on categories end point
    Given I am on homepage of categories endpoint
    When I send a GET request to newly created category with product ID
    Then I must get a valid response code 201 from categories endpoint
    And I verify if the category is created with correct details

  Scenario: I update a newly created category on categories end point
    Given I am on homepage of categories endpoint
    When I send a PUT request to newly created category with product ID
    Then I must get a valid response code 201 from categories endpoint
    And I verify if the category details is updated with correct details

  Scenario: I delete a newly created category on categories end point
    Given I am on homepage of categories endpoint
    When I send a DELETE request to newly created category with product ID
    Then I must get a valid response code 201 from categories endpoint
    And I verify if the category is deleted

