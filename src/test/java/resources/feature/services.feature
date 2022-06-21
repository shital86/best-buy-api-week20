@services
Feature: As a user I would like to preform CURD test on services endpoint

  Scenario: I read all services data from the application
    Given I am on homepage of services endpoint
    When I send a GET request to the services endpoint
    Then I must get a valid response code 200 from services endpoint

  Scenario: I create a new service on services end point
    Given I am on homepage of services endpoint
    When I send a POST request with a valid payload to the services endpoint
    Then I must get a valid response code 201 from services endpoint

  Scenario: I read a newly created service on services end point
    Given I am on homepage of services endpoint
    When I send a GET request to newly created service with product ID
    Then I must get a valid response code 201 from services endpoint
    And I verify if the service is created with correct details

  Scenario: I update a newly created service on services end point
    Given I am on homepage of services endpoint
    When I send a PUT request to newly created service with product ID
    Then I must get a valid response code 201 from services endpoint
    And I verify if the service details is updated with correct details

  Scenario: I delete a newly created service on services end point
    Given I am on homepage of services endpoint
    When I send a DELETE request to newly created service with product ID
    Then I must get a valid response code 201 from services endpoint
    And I verify if the service is deleted

