# controller

Application interface layer.

This module exposes the application's functionality to external clients 
(e.g. HTTP APIs, REST controllers, or a command-line interface).
It is responsible for handling incoming requests, collecting and parsing 
user input, performing basic validation, and translating external data 
formats into application commands or domain objects understood by the service 
layer.

Typical responsibilities:
- request handling
- input validation
- mapping between transport models and domain models
- delegating business logic to the service layer

This module depends on the `service` module.