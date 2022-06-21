@stores
Feature: As a user I would like to preform CURD test on stores endpoint

  Scenario: I create a new store on stores end point
    Given I am on homepage of stores endpoint
    When I send a POST request with a valid payload to the stores endpoint
    Then I must get a valid response code 201 from stores endpoint

  Scenario: I read a newly created store on stores end point
    Given I am on homepage of stores endpoint
    When I send a GET request to newly created store with product ID
    Then I must get a valid response code 201 from stores endpoint
    And I verify if the store is created with correct details

  Scenario: I update a newly created store on stores end point
    Given I am on homepage of stores endpoint
    When I send a PUT request to newly created store with product ID
    Then I must get a valid response code 201 from stores endpoint
    And I verify if the store details is updated with correct details

  Scenario: I delete a newly created store on stores end point
    Given I am on homepage of stores endpoint
    When I send a DELETE request to newly created store with product ID
    Then I must get a valid response code 201 from stores endpoint
    And I verify if the store is deleted

  Scenario: I read all stores data from the application
    Given I am on homepage of stores endpoint
    When I send a GET request to the stores endpoint
    Then I must get a valid response code 200 from stores endpoint
    And I verify the if the total is equal to 1563
    And I verify the if the stores of limit is equal to 10
    And I Check the single name "Inver Grove Heights"
    And I Check the multiple names "Roseville", "Burnsville", "Maplewood"
    And I Verify the storied 7 inside storeservices of the third store of second services
    And I Check hash map values createdAt inside storeservices map where store name is "Roseville"
    And I Verify the state "MN" of forth store
    And I Verify the store name "Rochester" of ninth store
    And I Verify the storeId is 11 for the sixth store
    And Verify the serviceId is 4 for the seventh store of forth service

