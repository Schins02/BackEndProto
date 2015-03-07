import json,httplib
connection = httplib.HTTPSConnection('api.parse.com', 443)
connection.connect()
connection.request('POST', '/1/files/pic.jpg', open('myPicture.jpg', 'rb').read(), {
       "X-Parse-Application-Id": "xTzPEGb9UXNKHH6lEphikPyDpfXeSinJ9HoIqODU",
       "X-Parse-REST-API-Key": "7LO1Je4sQxoSEWaWkKvssV12LbBHhnhoB1T4vUn6",
       "Content-Type": "image/jpeg"
     })
result = json.loads(connection.getresponse().read())
print result