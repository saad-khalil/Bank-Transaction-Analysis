@run
Feature: upload
  #testing upload page features

  Background: User is Logged In
    Given i am on the "login" page
    When i enter the "correct" credentials
    Then i am logged in


  @regression
  Scenario: User deletes all files
    Given i am on the "upload" page
    And i click delete all button
    Then all files will be deleted


  @regression
  Scenario: User uploads a valid MT940 file
    Given i am on the "upload" page
    When i upload "SWIFT_MT940_Standard_27May.940" file
    Then i am prompted with data page of the "SWIFT_MT940_Standard_27May.940" file


  @regression
  Scenario: User deletes a specific transaction
    Given i am on the "upload" page
    And i delete the "1st file" file
    Then the "1st file" should be deleted


