REST service on Spring Framework to get currency rates
======================================

It gets data about currency rates from [Central Bank of Russia](http://www.cbr.ru/scripts/Root.asp).


### Request
    GET /api/rate/{code}/{date}
        code - three-character currency code (USD, EUR)
        date - date of actuality in format yyyy-MM-dd (optional)
    
### Response

```json
{
    "code": "<valute-code>",
    "rate": "<currency-rate>",
    "date": "<date-of-actuality>"
}
```

### Request examples
    GET /api/rate/USD/2015-09-29
    GET /api/rate/USD


### Response example

```json
{
    "code": "USD",
    "rate": "66.0410",
    "date": "2015-09-24"
}
```

### Installation
    1. mvn package
    2. java -jar target/Currency.jar 