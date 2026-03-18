# repository-api

Persistence abstraction layer.

This module defines repository interfaces used to access and store
domain objects. It abstracts the underlying persistence mechanism.

Typical responsibilities:
- defining repository interfaces
- persistence contracts
- query abstractions

Implementations are provided by the `repository-impl` module.

This module depends on the `domain` module.