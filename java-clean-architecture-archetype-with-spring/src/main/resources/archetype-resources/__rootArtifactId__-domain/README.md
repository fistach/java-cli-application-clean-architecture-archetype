# domain

Core domain model of the application.

This module contains the fundamental business concepts and domain objects.
It should remain independent from infrastructure and frameworks.
It should be plain old Java.

Typical contents:
- domain entities
- value objects
- domain services
- domain rules and invariants

Other modules depend on this module, but it must not depend on them.