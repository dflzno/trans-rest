# Money transfer API

### How to run the app:

#### 1. Build the executable JAR with command

```
mvn clean package
```

#### Or, use available jar [here](https://github.com/dlozanoc/trans-rest/releases/tag/v1)

#### 2. Execute the app
```
java -jar {path_to_jar}/trans-rest-api-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### How to test the app:

Note: There is no seed data.

#### To add an account
```
POST  http://localhost:7070/api/account

Body example:
{
   "balance":10000
}
```

#### To retrieve an account
```
GET http://localhost:7070/api/account/:account-number

Example:
http://localhost:7070/api/account/4e523876-73b5-4f4b-9080-574581cd7e88 
```

#### To execute a transfer
```
POST  http://localhost:7070/api/transfer

Body example:
{
   "senderAccountId":"4e523876-73b5-4f4b-9080-574581cd7e88",
   "recipientAccountId":"8fd5f57c-9e51-4832-88f1-97173af45a6b",
   "amount":1500
}
```

#### To retrieve a transfer
```
GET http://localhost:7070/api/transfer/:transfer-id

Example:
http://localhost:7070/api/transfer/2857216e-f6db-4d69-982c-f58c8e9a34d7
```