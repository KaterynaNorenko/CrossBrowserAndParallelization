            Feature: As a user I want to be able to open the home page

              @smoke
              Scenario: Open web page in parallel in different browsers and check title
                Given I open the "Bookdepository" home page
                When I verify the "Bookdepository" logo
                Then I close the browser