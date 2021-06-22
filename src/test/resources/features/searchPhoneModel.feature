Feature: search mobile

  Background:
    Given Open yandex.market page
    And Go to electronics page
    And Go to smartphones page

  Scenario Outline: Sort mobile by maker "<maker>"
    When User search by maker "<maker>"
    Then The list contains only sorted maker "<maker>"
    Examples:
      | maker   |
      | Samsung |
      | Xiaomi  |


    @smoke
    Scenario: Sort mobile by price ascending
      When User click by prise one time
      Then The list contains phones are sorted by price ascending