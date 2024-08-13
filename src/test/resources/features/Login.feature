
Feature: login


  Background:
    Given the user is on "https://www.saucedemo.com/"

  @positive
Scenario: user login for sausedemo
When user enter the username "standard_user" and password "secret_sauce"
And user click on the login button