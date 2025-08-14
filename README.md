#  Box Dispatch Service

A Java Spring Boot REST API for managing delivery boxes, loading items, checking battery levels, and retrieving available boxes.  
Uses **MongoDB Atlas** for data storage.

--------------------------------------------------------

##  Tech Stack
- **Java 21**
  - **Spring Boot 3.3.2**
  - **Spring Web**
  - **Spring Data MongoDB**
  - **Lombok**
  - **MongoDB Atlas**

--------------------------------------------------------

##  Project Structure
src/main/java/org/example/BoxServiceApplication
├── controller
│ └── BoxController.java
├── entity
│ ├── Box.java
│ └── Item.java
├── repository
│ └── BoxRepository.java
├── service
│ └── BoxService.java
└── Main.java

-------------------------------------------------------

##  **Build Instructions**
1. **Clone the repository**
    * git clone https://github.com/donneyo/BoxDispatchProject.git
    * cd BoxDispatchProject

2 .Ensure Java 21 is installed

3 .Build the project using Maven "mvn clean install

-------------------------------------------------------

## **Run Instructions**
1 .Configure MongoDB connection in src/main/resources/application.properties:

    spring.application.name=project-box-dispatch
    server.port=8080
    spring.data.mongodb.uri=mongodb+srv://root:root@cluster0.yq0a92r.mongodb.net/boxdb?retryWrites=true&w=majority
    spring.data.mongodb.database=boxdb
    logging.level.org.springframework=INFO
    logging.level.com.example.boxservice=DEBUG
    spring.messages.basename=messages
    server.error.include-message=always
    spring.main.allow-bean-definition-overriding=true`
    

2 .Run the application: Via IntelliJ IDEA or any other IDE → Run Main class. 

3 .Test Instructions :You can test the API using Postman, cURL, or any REST client.



# Here are API Endpoints you can try on postman:

## 1. Create a Box

      POST http://localhost:8080/boxes
      `{
         "txref": "BOX123",
         "weightLimit": 500,
         "batteryCapacity": 80,
         "state": "IDLE"
      }`

## 2.  Load Items into a Box

    POST /boxes/{boxId}/items

    http://localhost:8080/boxes/689bac5b57b9016e883959f7/items 
    

   ` [
    {
    "name": "ItemOne",
    "weight": 10,
    "code": "ITEM_001"
    },
    {
    "name": "ItemTwo",
    "weight": 15,
    "code": "ITEM_002"
    }
    ]`

## 3. Get Items in a Box
      GET /boxes/{boxId}/items   //Try this on postman http://localhost:8080/boxes/689bac5b57b9016e883959f7/items

## 4.  Get Available Boxes
      GET /boxes/available       //Try this on postman http://localhost:8080/boxes/available

## 5.  Get Battery Level
       GET /boxes/{boxId}/battery //Try this postman http://localhost:8080/boxes/689bac5b57b9016e883959f7/battery


# My Design Assumption

## Persistence
    * MongoDB Atlas is used as the database for its flexibility, easy setup, and cloud accessibility.
    * The boxes collection stores both box details and their loaded items in an embedded document structure for simplicity.

## State Management
    * A box can have one of the following states: IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING.
    * State transitions are not automatically handled by the backend except when logically required (e.g., after loading items, the state may be set to LOADING if not already).
    * The backend validates that battery level ≥ 25% when attempting to load items.

## Weight Limit
    * The maximum allowed weight for a box is 500 grams.
    * The system checks the total weight (existing items + new items) before allowing loading.

## Battery Limit
    * Loading a box is blocked if the battery capacity is below 25%.

## Item Validation
      * Name: Only letters, numbers, hyphens (-), and underscores (_) are allowed.
      * Code: Only uppercase letters, underscores (_), and numbers are allowed.
      * Validation is enforced via Spring annotations in the entity classes.

## API Responses
    * Returns 404 if a box is not found.
    * Returns 400 if validation rules (weight, battery, state) are violated.
    * Error messages are descriptive to guide API consumers.

## Testing
    * All endpoints tested with Postman using both valid and invalid inputs to confirm rule enforcement.
    * Once it’s running on http://localhost:8080
    * Create a box (POST /boxes)
    * Load items (POST /boxes/{id}/items)
    * Get loaded items (GET /boxes/{id}/items)
    * Get available boxes (GET /boxes/available)
    * Get battery level (GET /boxes/{id}/battery)



