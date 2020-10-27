# Bridge
The local server based on Tomcat is the bridge between Java and Meta Trader 5 via HTTP requests.

Port `80`. Port can't be changed by specific Meta Trader 5 reason, see more in MQL5 documentation: [https://www.mql5.com/en/docs/network/webrequest][webrequest].

MetaTrader 5 required permission in settings for send http requests to localhost, see more in MQL5 documentation: [https://www.mql5.com/en/docs/network][settings]

Endpoints:

**POST** `http://127.0.0.1/api/advisor/add` - save advisor and get UUID identifier.

_Request:_
```json
{
    "magic": 100000,
    "inputs": [
        { "key": "propName1", "value": "str", "type": "STRING" },
        { "key": "propName2", "value": 10, "type": "NUMBER" }
    ]
}
```

_Response:_
```json
{
    "id": "c4eb34e4-c9c3-4b7e-856d-d5d00588464d",
    "magic": 100000,
        "inputs": [
            { "key": "propName1", "value": "str", "type": "STRING" },
            { "key": "propName2", "value": 10, "type": "NUMBER" }
        ]
}
```


**GET** `http://127.0.0.1/api/signal` - resolve indicator's data and return signal generated by wired strategy.

_Request:_
```json
{
    "advisorId": "c4eb34e4-c9c3-4b7e-856d-d5d00588464d",
    "strategyName": "EXAMPLE",
    "indicators": [
        {
            "number": 1,
            "timeframe": "M_1",
            "type": "STOCHASTIC",
            "buffer": [
                { "value": 37.38, "date": 1603488613812 },
                { "value": 39.23, "date": 1603488639826 },
                { "value": 42.07, "date": 1603488656337 }
            ]
        }
    ]
}
```

_Response:_

###### Open Position Signal Response:
```json
{
    "type": "BUY | SELL",
    "lot": 0.01,
    "stopLoss": 100,
    "takeProfit": 100
}
```
###### Update Position Signal Response:
```json
{
    "type": "UPDATE",
    "positionId": 10000000,
    "lot": 0.01,
    "stopLoss": 100,
    "takeProfit": 100
}
```
###### Close Position Signal Response:
```json
{
    "type": "CLOSE",
    "positionId": 1000000000
}
```
###### No Action Signal Response:
```json
{
    "type": "NO_ACTION"
}
```


**Contribution**

    1. For bug reports welcome to GitHub Issue.
    2. Pull Request should be supported GitHub Issue.
    


[webrequest]: https://www.mql5.com/en/docs/network/webrequest
[settings]:https://www.mql5.com/en/docs/network