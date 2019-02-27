[![Build Status](https://travis-ci.com/joaoabrodrigues/reclameaqui-complains.svg?token=2WXQAHfxSwzqWyLWSKr7&branch=master)](https://travis-ci.com/joaoabrodrigues/reclameaqui-complains)
[![codecov](https://codecov.io/gh/joaoabrodrigues/reclameaqui-complains/branch/master/graph/badge.svg?token=O18oyaRiif)](https://codecov.io/gh/joaoabrodrigues/reclameaqui-complains)

# Backend Engineer Challenge #

### Solution ###

 - Prerequisites:
    - Java 8
    - Maven 3.5.2
    - Docker 18.x

 - Routes
    - Create new: POST /api/complaint
    - Find all: GET /api/complaint
    - Find by ID: GET /api/complaint/{id}
    - Update: PUT /api/complaint/{id}
    - Delete: DELETE /api/complaint/{id}
    - Find complaints by city and company: GET /api/complaint/search/company-city?city={city}&companyName={companyName}
    - Find complaints by city: GET /api/complaint/search/company?companyName={companyName}
    - Find complaints by company: GET /api/complaint/search/city?city={city}

 - Stack
    - Java 8
    - Spring Boot 2.1.3
    - Undertow
    - [MongoDB](https://www.mongodb.com/cloud/atlas)
    - Spring Data Rest
    - [Lombok](https://projectlombok.org/)
    - [OpenCage Geocoding](https://opencagedata.com/)
    - [CodeCoverage](https://codecov.io/)
    - Docker
    
 - Implemented Tests:
    - Unit
    - Integration

To run:
```bash
    ./run.sh
```
 

### Problem ###

We need to research about locales where consumer complains are made. That complains should have at least the attributes described bellow:

 - Title
 - Description
 - Locale
 - Company

Can you provide some services to ingest complains and get some data about its geolocation? For example, to find how many complains a specific company has in specific city?


### Recommendations ###
 - Use Restful instead Rest
 - Use microservice design if possible
 - Use a NoSql Database (if you use a database in your purpose)
 - We need to scale your services, decouple your modules if possible
 - Use devops mindset
 - Use your preferred language and patterns

### Definition Of Done ###
 - A repository with read access to laercio.filho@reclameaqui.com.br, michel@reclameaqui.com.br, guilherme.branco@reclameaqui.com.br,  willian.miranda@reclameaqui.com.br ( feel free to choose your provider )
 - Documented, clean and testable/tested code
 - Documented strategy to deploy and run your code ( on cloud if possible )

### Questions? ###
 - Email me : willian.miranda@reclameaqui.com.br

