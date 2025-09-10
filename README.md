**README FILE FOR PRICE MANAGER PROJECT**
- Author: Diego Hernández

**DOMAIN MODEL CHOSEN**

- The project has been structured around the Model Aggregated Root
  class of Price, as it has been identified as the major actor 
  for this exercise.

- Several auxiliary classes have been created related to Price
  to isolate and decouple interactions between Infrastructure layer 
  and Application layer. Those are the following:
  - PriceDto to handle REST requests.
  - PriceDtoMapper to map Price -> PriceDto.
  - PriceData to handle H2 Database interaction.
  - PriceFactory to build Price model from PriceData.
  Those classes are created to provide the following advantages:
  - Ensure boundary protection.
  - Promote decoupling between layers.
  - Keep the domain model isolated.
  - Prepare the system for future complexity.

- A PriceEntity was created in Infrastructure layer to map to the 
  Database via JPA/Hibernate

- There is also a Service in the Application Layer, that
  orchestrates the main behavior for the endpoint requested. The 
  function on the service receives the parameters received
  by Controller in Infrastructure layer, and then it uses the Mapper
  to get information from Database, and transform it into a DTO
  to send response to Infrastructure layer.

- In the Infrastructure Layer, it has been included a Controller to
  receive the input on the endpoint, validating previously the
  integrity of the data, just to provide a first layer of validation
  over the data received. This validation has been attached only to
  the required fields by simplicity. Endpoint implemented on the
  Controller execute the calculations by calling the Service function
  in the Application layer.

- Finally, the entry point for the whole program has been set as a
  Main class called PriceManagerApplication, tagging it as a
  SpringBootApplication bean.

**GENERAL ASSUMPTIONS**

- Security has not been considered for this exercise for simplicity.
  For real environments, it would be necessary to add into the Controller
  class some kind of security layer to validate the headers of the
  requests, having authorization token validations, or even prevent
  common vulnerabilities such as CSRF, CORS, XSS, etc. Also, additional
  security will be required on the Database access, as it is configured
  without any password or method to authenticate properly the access.

- Due to the simplicity of the solution, major Exceptions treatment and 
  logging has been excluded from the implementation.

- Performant for big inputs has not been considered for simplicity, adding
  instead input data consistency validation.

- Swagger implementation has not been considered for simplicity.

**HOW TO BUILD AND RUN PROJECT USING DOCKER**

- To offer simplicity and easy execution, a Docker container has
  been created to deploy the whole project inside a VM with Java
  and Maven autoinstalled to run the application.

- Create and run the Docker image:

  **docker compose up --build**

- Java SDK used: openjdk-24
- Maven version: 3.9.9