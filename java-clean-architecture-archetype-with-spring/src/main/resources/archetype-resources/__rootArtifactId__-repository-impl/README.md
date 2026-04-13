# repository-impl

Persistence implementation layer.

This module provides concrete implementations of repository interfaces
defined in `repository-api`. It contains the actual integration with
the persistence technology (e.g. database, ORM, external storage).

Typical responsibilities:
- repository implementations
- database access
- ORM mappings
- persistence configuration

This module depends on the `repository-api` module.