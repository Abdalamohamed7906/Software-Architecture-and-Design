 Sprint 3 – SOA Principles Implementation

Overview
The system implements Service-Oriented Architecture (SOA) principles through a layered and modular design. The application is divided into presentation, service, data, and domain layers, allowing each part of the system to operate independently while interacting through well-defined interfaces.

Architecture Structure
Presentation Layer
The presentation layer is implemented in `MainUIController.java`. This component is responsible for handling user interaction and coordinating system operations.
It uses:
 `IInputHandler` to collect user input
 `IStockDataManager` to process business logic
 `IChartDisplay` to present results
This ensures the presentation layer does not directly handle data access or business logic.
Service Layer
The service layer includes components such as `StockDataManager`, `ComparisonService`, and `PriceAnalyzer`. These classes handle the core business logic of the system.
The `IStockDataManager` interface is used in the controller, which promotes loose coupling and allows the implementation to be changed without affecting the presentation layer.
Data Layer
The data layer includes:
 `APIService` for fetching stock data
`Repository` for managing stored data
 `CacheManager` for caching data
 `StockAdapter` for adapting raw data into domain objects
This layer is responsible for all data access and external communication, ensuring separation from business logic.
 Domain Layer
The domain layer contains core models such as:
 `StockData`
 `DateRange`
These classes represent the core data structures used throughout the application.
SOA Principles Applied
Loose Coupling
Loose coupling is achieved through the use of interfaces such as `IStockDataManager`, `IInputHandler`, and `IChartDisplay`. The presentation layer depends on abstractions rather than concrete implementations.
 Separation of Concerns
Each layer has a clearly defined responsibility:
 Presentation handles user interaction
 Service handles business logic
 Data handles data access
 Domain represents core data
 Modularity
The system is divided into independent components, making it easier to maintain and extend. For example, data access logic can be modified without affecting the UI or service logic.
Interoperability
The use of the `StockAdapter` allows the system to convert raw data into domain objects, improving compatibility between components.
 Conclusion
The system successfully demonstrates SOA principles through its layered architecture, use of interfaces, and clear separation of responsibilities. This design improves scalability, maintainability, and flexibility, allowing components to evolve independently.