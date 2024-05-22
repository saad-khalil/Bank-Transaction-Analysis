@run
Feature: statistics
  #testing statistics page features

  Background: User is Logged In
    Given i am on the "login" page
    When i enter the "correct" credentials
    Then i am logged in
    And i am on the "upload" page
    Then i upload "SWIFT_MT940_Standard_27May.940" file
    Then i am prompted with data page of the "data" file
    Then i click on the "statistics" button


  @integration
  Scenario: User selects the 1 Apr 2014  transaction
    Given i am on the "statistics" page
    And i select the transactions on "1 Apr 2014"
    Then the selection "tr[1]" turn blue


  @integration
  Scenario: User selects the 1 May 2014  transaction
    Given i am on the "statistics" page
    And i select the transactions on "1 May 2014"
    Then the selection "tr[2]" turn blue


  @integration
  Scenario: User selects the 2 May 2016  transaction
    Given i am on the "statistics" page
    And i select the transactions on "2 May 2016"
    Then the selection "tr[3]" turn blue


  @integration
  Scenario: User selects the 1 Apr 2014 and 1 May 2014  transaction
    Given i am on the "statistics" page
    And i select the transactions on "1 Apr 2014"
    And i select the transactions on "1 May 2014"
    Then the selections "tr[1]" and "tr[2]" turn blue
    Then the selected table will display information about "tr[1]" and "tr[2]"  properly


  @ignore
  Scenario: User selects transactions between 1 Apr 2014 and 1 May 2014
    Given i am on the "statistics" page
    And i input the  interval "1 Apr 2014" and "1 May 2014"
    Then the selections "tr[1]" and "tr[2]" turn blue



