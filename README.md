# Drones practical Task for Edgar Perez

### The project uses should be run as follows

#### For build
``
./mvnw clean install
``

#### For run

``./mvnw spring-boot:run``

### Test Instructions

#### registering a drone

```json
curl --location --request POST 'localhost:8888/drones' \
--header 'Content-Type: application/json' \
--data-raw '{
"serialNumber": "7c73a0ad-8fa7-4f0a-bcfb-85435f2311c7",
"model": "Cruiserweight",
"weight": 500,
"batteryCapacity": 100,
"state": "IDLE"
}'
```

#### loading a drone with medication items

```json
curl --location --request PUT 'localhost:8888/drones/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Azitromicina",
    "weight": 2,
    "code": "AZ_500",
    "image": ""
}'
```

#### checking loaded medication items for a given drone

```json 
curl --location --request GET 'localhost:8888/drones/1/medications' \
--header 'Content-Type: text/plain' \
--data-raw 'https://www.baeldung.com/java-md5
'
```

#### checking available drones for loading

```json
curl --location --request GET 'localhost:8888/drones/available' \
--header 'Content-Type: text/plain' \
--data-raw 'https://www.baeldung.com/java-md5
'
```

#### check drone battery level for a given drone;

```json
curl --location --request GET 'localhost:8888/drones/1/battery' \
--header 'Content-Type: text/plain' \
--data-raw 'https://www.baeldung.com/java-md5
'
```