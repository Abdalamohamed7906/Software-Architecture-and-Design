StockCompare — Sprint 3
Module: Software Architecture and Design
University: University of Roehampton
Team: Anwar Ali, Abdala Mohamed, Omran, Meshari, Ismail
Deadline: 23/04/2026
Code Review: 24/04/2026

Overview
StockCompare is a Java-based stock price comparison application. Sprint 3 introduces a full JavaFX graphical user interface, compound components, domain-independent architectural styles, and Service-Oriented Architecture (SOA) principles.

How to Run
Prerequisites: Java 17, Maven 3
bashgit clone https://github.com/Abdalamohamed7906/Software-Architecture-and-Design.git
cd Software-Architecture-and-Design
git checkout sprint3
mvn javafx:run
Run tests:
bashmvn test
Expected: Tests run: 59, Failures: 0, Errors: 0, Skipped: 1

Sprint 3 Features
JavaFX GUI — 7 Compound Components
PanelUse CasesServices UsedLoginPanelUC1, UC2IAccountServiceSearchPanelUC3, UC4, UC6, UC8IStockService, IStockAnalysisService, ISavedStockServiceComparisonPanelUC9IStockAnalysisServiceSavedStocksPanelUC7, UC10ISavedStockServiceExportPanelUC12IExportService, IStockServiceAdminPanelAdmin mgmtIAdminServiceUserProfilePanelUC2IAccountService
New in Sprint 3

IAdminService — Admin Management as its own service sphere (fixes Sprint 2 design gap)
JavaFX GUI — replaces Sprint 2 console interface
XLSX export with chart — Export panel now supports CSV, JSON, and Excel with embedded line chart
Bloomberg dark theme — domain-independent CSS applied across all components


Architecture
Presentation Layer    →    MainWindow + 7 compound panels (JavaFX)
Service Layer         →    IAccountService, IAdminService, IStockService,
                           IStockAnalysisService, ISavedStockService, IExportService
Repository Layer      →    IUserRepository, IStockRepository, ISavedStockRepository
Data Layer            →    SQLiteDatabase + StockAPIClient (Yahoo Finance API)
Architectural patterns applied:

N-Tiered Layered Architecture
Model-View-Controller (MVC)
Adapter Pattern (StockAPIClient → Yahoo Finance API)
Pipes and Filters (SearchPanel, ComparisonPanel, ExportPanel)


Technology Stack
TechnologyVersionJava17JavaFX21SQLite (sqlite-jdbc)3.45.1Maven3JUnit 55.xMockito5.5.0Gson2.10.1Apache POI5.2.5

Testing
59 test cases across 4 test classes:
Test ClassTestsCasesUserServiceTest12TC01–TC12StockAnalysisServiceTest7TC13–TC19ExportServiceTest7TC20–TC26Sprint2Tests (legacy)33Legacy

Project Structure
src/
├── main/java/com/stockcompare/
│   ├── presentation/
│   │   ├── StockCompareApp.java
│   │   ├── MainWindow.java
│   │   ├── AppContainer.java
│   │   └── components/          ← 7 compound panels
│   ├── domain/
│   │   ├── interfaces/          ← 6 service interfaces
│   │   └── model/               ← domain models
│   ├── service/                 ← service implementations
│   ├── repository/              ← repository implementations
│   └── data/                    ← SQLiteDatabase, StockAPIClient
└── test/java/com/stockcompare/
    ├── service/                 ← Sprint 3 test files
    └── Sprint2Tests.java        ← legacy tests

Team
MemberRoleAnwar AliProject lead, all GUI panels, architecture, IAdminService fix, CSS themeAbdala MohamedIAdminService, AdminService implementationOmranBackend servicesMeshariTest casesIsmailReport writing
