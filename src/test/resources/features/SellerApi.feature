Feature: test seller api
  @getSellerVerifyEmailNotEmpty
  Scenario: get single seller and verify seller email is not empty
    Given user hits get single seller api with "/api/myaccount/sellers/5747"
    Then verify seller email is not empty


  @getSeller
    Scenario:  get all sellers and verify seller id is not 0
      Given user get all seller api with "/api/myaccount/sellers"
      Then verify seller ids are not equal to 0


    @updateSeller
    Scenario: get single seller, update the same seller, verify seller was updated
      Given user hits get single seller api with "/api/myaccount/sellers/5747"
      Then verify seller email is not empty
      Then user hits put api with "/api/myaccount/sellers/"
      Then verify useremail wes updated
      And verify user first name was updated

      @getSellerAndArchived
      Scenario: get seller and archive and verify seller archived
        Given user hits get single seller api with "/api/myaccount/sellers"
        Then user hits archive seller api "/api/myaccount/sellers/archive/unarchive"
        And user hits get single seller api with "/api/myaccount/sellers"


  @getSellerAnd
        Scenario: create a seller , delete a seller, verify seller was deleted
          Given user hits post api with "/api/myaccount/sellers"
          Then verify seller id was genereted
          Then verify seller email is not empty
          And verify seller email is not empty
          Then delete the seller with "/api/myaccount/sellers/"
          Then verify that delte in no in list


