import json,httplib
connection = httplib.HTTPSConnection('api.parse.com', 443)
connection.connect()
connection.request('POST', '/1/batch', json.dumps({
       "requests": [
         {
           "method": "POST",
           "path": "/1/classes/GameScore",
           "body": {
             "score": 1337,
             "playerName": "Sean Plott"
           }
         },
         {
           "method": "POST",
           "path": "/1/classes/GameScore",
           "body": {
             "score": 1338,
             "playerName": "ZeroCool"
           }
         }
       ]
     }), {
       "X-Parse-Application-Id": "xTzPEGb9UXNKHH6lEphikPyDpfXeSinJ9HoIqODU",
       "X-Parse-REST-API-Key": "7LO1Je4sQxoSEWaWkKvssV12LbBHhnhoB1T4vUn6",
       "Content-Type": "application/json"
     })
result = json.loads(connection.getresponse().read())
print result