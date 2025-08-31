Feature: Basic Flight Search and Time Filter

  @basic_flight_search
  Scenario Outline: Verify flight search with time filtering
    Given I am on Enuygun homepage "Türkiye’nin Seyahat Sitesi"
    When I search for round-trip flights from "Istanbul", "ISTA" to "Ankara", "ESB" "<departureDate>" "<returnDate>"
    And I apply departure time filter between "10:00 AM" and "6:00 PM"
    Then all displayed flights should have departure times within the specified range
    And flight list should match the selected route "Istanbul - Ankara"

    Examples:
      | departureDate | returnDate |
      | 2025-09-10    | 2025-09-22 |