
# Stock API Coding Exercise

This is Rafael Uy's submission for the Atlas Carbon coding exercise. Note that I have moved the
original [README.md](./project-brief.md)

The challenge involves implementing an API backend for creating monthly summaries 
for stocks that will integrate with the provided UI as described in [feature-file.md](./feature-file.md)

Note: Assumptions have been made and are all listed in [feature-file.md](./feature-file.md)

----
## Design decisions
### Tech stack
- Java 17
- Spring Boot
- H2 Database
- Maven
- Docker
- Cucumber
- JUnit


### Application Structure
The project structure follows a standard Spring Boot application layout, 
including controllers, services, repositories, domain models, and DTOs.

### Risks taken
I've taken some risks by reading up on DDD and using its design principles despite
my lack of experience in it. I hope it shows that I'm willing and capable of learning new
concepts, and I hope it pays off.

I've also decided to refresh my knowledge of Cucumber since it has been some time since I last used it.
I hope I have used it satisfactorily to demonstrate BDD concepts.

### JUnit Tests
I've provided a test for the main algorithm in the Stock domain class. 
As for the controller, I've de-prioritised that in favour of cucumber tests

### Domain Modeling
The `Stock` and `StockRecord` classes form the core of the domain model. I've identified that the `Stock` would be 
the aggregate root and `StockRecord` an entity within the context of the `Stock` aggregate. 

I've kept the `generateMonthlySummaryList` method in the `Stock` class to calculate the monthly
summaries as it a stock related function and depends on its `stockRecordList` 

### CORS Configuration
I've configured Cross-origin resource sharing (CORS) to allow access from the frontend UI, 
which is from http://localhost:3000. I've parameterised this via the application.yml to allow for environment
based configuration. 

### Database Initialization
The H2 database is initialised using the data from an input CSV file containing stock records. This happens
at @PostConstruct of the `DataInitialisationService`. I understand that perhaps csv based initialisation might not 
be appropriate for other environments, so I've set it to only initialise with the dev profile, which is the default
active profile. 

### Error Handling
I haven't implemented custom error handling, but it could potentially be something to work on in the future.

-----
## Building the Application
To build and verify the application: 
```
./mvnw clean verify 
```

## Running the Application
To run the application: 
```
docker compose up -d --build 
```

You may access the UI via http://localhost:3000


## API Endpoints

The application provides the following API endpoint:
- `/monthly-summary/{stockId}`: Retrieve monthly summaries for a specific stock based on its `stockId`.

The output will be in the following format:

```
{
    "summaries": [
        {
            "month": "Dec-2020",
            "averageMonthlyRating": 8.33165,
            "headChange": 0,
            "recordCount": 1,
            "finalReadings": {
                "head": 310,
                "weight": 295.829,
                "price": 1617.68
            }
        },
        {
            "month": "Nov-2020",
            "averageMonthlyRating": 8.5403625,
            "headChange": -156,
            "recordCount": 4,
            "finalReadings": {
                "head": 244,
                "weight": 300.504,
                "price": 1623.09
            }
        }
    ]
}
```