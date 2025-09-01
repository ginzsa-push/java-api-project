# java sample test project

Dusting off java, springboot and DescriptiveStatistics math library

## API service Requirements

A company has an inventory system for recording machine IDs and machine ages within a fleet.
Age is stored as a string in the form of ‘<number> <time unit>’, e.g. ‘1 year’, ’16 months’.

NOTE: The data and requirements are reflected on integration test.

After a period of use, it was pointed out that some of the time units had been entered incorrectly resulting in unreasonably long ages e.g. ’90 days’ was incorrectly recorded as ’90 years’.
You’ve been asked to develop a standalone REST micro service to validate a list of machine ages that are sent up to your service.
The service will accept a list of {<machine ID>, <age string>} and return a list of machines whose ages are unreasonably long compared to the other machines in the request body.
Think of this as an outlier detection problem. For an intro into the subject, see https://towardsdatascience.com/a-brief-overview-of-outlier-detection-techniques-1e0b2c19e561.
You’re welcome to use any relevant algorithm. Getting extremely high degrees of accuracy is not the goal of the task.

You will need to
    1. Implement a micro service that must be implemented in Java. Any REST/web framework can be used for plumbing
    2. Generate your own data to send as the request body and send through the file or program that generates the request body with your submission


## maven build
```
mvn clean package
```

## maven test
```
mvn test
```

## docker build
```
docker build -t test-java-api .
```


## docker run
```
docker run -p 8080:8080 test-java-api
```

## app test
```
curl -X POST http://localhost:8080/validate \
     -H "Content-Type: application/json" \
     -d '[{"id": "4eb2404d-1707-4ca0-80cc-03c78689ece7", "time spent": "13 months"},
{"id": "54bd5c53-cbd1-49fb-a728-70097f2a2372", "time spent": "701 days"},
{"id": "3a169022-e9bd-4720-be37-73adeb83249a", "time spent": "37 days"},
{"id": "3ff144c2-73a6-42e6-b785-0027ec286f9b", "time spent": "1200 months"},
{"id": "b9ee7d27-f719-4fd8-bf7f-40877fee9e2c", "time spent": "1 months"},
{"id": "cd2de7ee-0841-4628-9525-ae81577692aa", "time spent": "302 days"}]'
```