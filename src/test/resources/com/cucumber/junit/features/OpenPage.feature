            Feature: As a user I want to be able to get the home page

              @smoke
              Scenario: Open web page in parallel and check title
                Given I open the "Rozetka" home page
                When I verify the "Rozetka" logo
                Then I close the browser