@utilities
Feature: As a user I would like to preform CURD test on utilities endpoint

  Scenario: I check version of utilities endpoint
    Given I am on homepage of utilities endpoint
    When I send a GET request to check version of the utilities endpoint
    Then I must get a valid response code 200 from utilities endpoint

  Scenario: I do health check of utilities endpoint
    Given I am on homepage of utilities endpoint
    When I send a GET request to check health of the utilities endpoint
    Then I must get a valid response code 200 from utilities endpoint

