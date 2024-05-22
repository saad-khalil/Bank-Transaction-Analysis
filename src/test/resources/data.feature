@run
Feature: data
  #testing data page features

  Background: User is Logged In
    Given i am on the "login" page
    When i enter the "correct" credentials
    Then i am logged in
    And i am on the "upload" page
    Then i upload "SWIFT_MT940_Standard_27May.940" file
    Then i am prompted with data page of the "data" file


  @integration
  Scenario: User expands a transaction
    Given i am on the "data" page
    And i click on the "1st" transaction
    Then the first transaction expands

  @integration
  Scenario: User expands all transactions
    Given i am on the "data" page
    And i click on the "Expand All" button
    Then all transactions wil expand

  @integration
  Scenario: User searches a keyword
    Given i am on the "data" page
    And i search for "interest" keyword
    Then transactions will contain the "interest" keyword


