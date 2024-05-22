@run
Feature: login
  #testing login page features

  @smoke
  Scenario: login without credentials
    Given i am on the "login" page
    When i click login
    Then i can not login


  @smoke
  Scenario: login with correct credentials
    Given i am on the "login" page
    And i enter the "correct" credentials
    Then i successfully logged in

  @smoke
  Scenario: login with incorrect credentials
    Given i am on the "login" page
    And i enter the "incorrect" credentials
    Then i can not login


  @regression
  Scenario: access rest resource without login
    Given i am on the "login" page
    And i am on the "upload" page
    Then i will be prompted with unauthorized




