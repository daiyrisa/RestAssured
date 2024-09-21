Feature: Update Teacher

  @positive2
  Scenario: verify tag can be updated
    Given base url "https://backend.cashwise.us/"
    When I provide "VALID TOKEN" authorization token
    And I provide "name_tag" with "updated tag"
    And I provide "description" with "updated description"
    And I hit PUT endpoint "/api/myaccount/sellers/{id}"
    Then verify status code is 200
    Then verify response body conatins "name_tag" with "updated name-tag"
    Then verify request body conatins "description" with "updated description"