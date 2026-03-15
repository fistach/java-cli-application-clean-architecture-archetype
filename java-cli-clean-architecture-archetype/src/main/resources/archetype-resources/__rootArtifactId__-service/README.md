# service

Business logic layer.

This module implements application use cases and orchestrates domain operations.
It contains the core business workflows and coordinates interactions
between the domain model and persistence layer.

Typical responsibilities:
- implementing business use cases
- transaction boundaries
- domain orchestration
- calling repository interfaces to get or persist the domain data

This module depends on:
- `repository-api`
- `domain` (because repository-api depends on domain)