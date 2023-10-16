# Requirements
## Assumptions
- stock_id: long
- Input files will be only in development, and will be loaded with a `dev` profile. Create a new profile for 
other environments and create different data ingestion methods for other envs. Default active profile is set to `dev`.
- Input files will always be valid. (eg. no formatting errors)
- We can allow Stock aggregate root creation when the stock_id referenced in Stock Records in input.csv does not exist. 
  My preference for this coding challenge is to allow this level of leniency over throwing errors. 
  In a real world scenario, I am not sure whether the Stocks will be pre-defined or if they are defined as these 
  Stock record come in as events. 
- Higher index = later event.
- I found some scenarios where there are multiple records with dates and indexes that repeat. I assume that
these are valid records that must be counted for averages, and in terms of other aggregations where order matters,
we will follow the natural order of these records. 

    | date       | head  | rating  | weight  | price   | stockId | index |
    |------------|-------|---------|---------|---------|---------|-------|   
    | 2020-07-01 | 270   | 9.86875 | 319.192 | 1423.56 | 2       | 100   |
    | 2020-07-01 | 157   | 9.86875 | 319.192 | 1423.56 | 2       | 100   |
    | 2020-07-01 | 270   | 9.86875 | 319.192 | 1423.56 | 2       | 100   |

- I have made a slight correction to the UI as the final weight and final price values were interchanged.



## Description 

As a grazier, I would like to see a list of monthly summaries for stock when I search by a stockId. 

## Functional

Each summary should include:

1. The month (In format `MMM-YYYY`. Eg: Jan-2021)

2. The average rating for the month

3. The final reading of head, weight, and price in separate columns

4. The number of updates (records) for the month

5. The change in head from first record in the month to the last record in the month

## Business Rules

1. Stock items are identified by stockId

2. The summaries should be ordered by date, with the most recent one shown first.

## Technical

1. An API backend running on the JVM.

    1.1 Written in Kotlin (preferred) but Java 8 or higher is allowed

    1.2 Built with Maven

2. Responds to REST endpoint `http://localhost:8080/monthly-summary/{stockId}`.

3. Backend accepts input data as follows:

    See the [input.csv](./input.csv) file of livestock records, which describes groups of livestock over time.

    * `date`: The date the record is describing
    * `head`: The number of animals on that date
    * `rating`, `weight`, `price`: Attributes of stock on that day
    * `stockId`: Unique identifier of a group of animals
    * `index`: Order of records when more than one fall on a day

# Acceptance Criteria

## Scenario: As a grazier, I want to view the summary for a month-year

- Given I have loaded the monthly summary app
- When I view the summary for `stockId 2`
- Then I should see the following details for `Dec-2020` on the first row of the table with the following details:

| Month    | Average Rating | Final Head | Final Weight | Final Price | Number of Updates  | Month Head Change |
| -------- | -------------- | ---------- | ------------ | ----------- | ------------------ | ----------------- |
| Dec-2020 | 8.33165        | 310        | 295.829 kg   | $1617.68    | 1                  | 0                 |

- And I should see a list of the remaining `6` month-years for stockId 2 with their respective summary data

