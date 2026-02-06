# Share Price Comparison Application

> A robust and scalable Java application for comparing stock prices over time, demonstrating software architecture and design principles

[![Sprint 1](https://img.shields.io/badge/Sprint-1-blue)](docs/SPRINT1_TASK_ALLOCATION.md)
[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Build-Maven-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Build Instructions](#build-instructions)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [Documentation](#documentation)
- [Team](#team)
- [Sprint Progress](#sprint-progress)

---

## 🎯 Project Overview

This project is a coursework submission for the Software Architecture and Design module. It demonstrates the application of various architectural principles including:

- **Simple Architecture** - Clean separation of concerns across layers
- **Clean Architecture** - Dependency inversion and independence
- **Design Patterns** - Repository, Factory, Strategy, Adapter, Facade
- **Service-Oriented Architecture** - Modularity and interoperability

The application enables users to:
- Fetch daily stock price data for any symbol within a 2-year range
- Store data locally for offline functionality
- Visualize and compare stock performance graphically
- Work seamlessly with or without internet connectivity

---

## ✨ Features

### Current (Sprint 1) ✅
- ✅ Complete domain model (StockPrice, DateRange)
- ✅ Service layer with caching logic
- ✅ Repository pattern with mock implementation
- ✅ External data source abstraction
- ✅ Factory pattern for component creation
- ✅ Comprehensive documentation
- ✅ Layered architecture design

### Planned (Sprint 2) 📅
- SQLite database integration
- Yahoo Finance API integration
- Use case and business type models
- Clean architecture implementation

### Planned (Sprint 3) 📅
- User interface with chart visualization
- Two-stock comparison functionality
- Offline mode indicator
- Domain-independent architectural styles
- SOA principles implementation

---

## 🏗 Architecture

### Four-Layer Architecture

```
┌─────────────────────────────────────┐
│     Presentation Layer (UI)         │  ← User Interaction
├─────────────────────────────────────┤
│     Service Layer (Business Logic)  │  ← Orchestration
├─────────────────────────────────────┤
│     Repository Layer (Data Access)  │  ← Persistence
├─────────────────────────────────────┤
│     Domain Model Layer (Entities)   │  ← Business Objects
└─────────────────────────────────────┘
```

### Key Design Patterns

- **Repository Pattern**: Abstracts data access logic
- **Factory Pattern**: Centralizes object creation
- **Strategy Pattern**: Runtime selection of persistence mechanism
- **Adapter Pattern**: Converts external API data to domain models
- **Facade Pattern**: Simplified service interface

### Architectural Principles

1. **Separation of Concerns** - Each layer has a single, well-defined responsibility
2. **Dependency Inversion** - High-level modules don't depend on low-level modules
3. **Single Responsibility** - Each component has one reason to change
4. **Open/Closed Principle** - Open for extension, closed for modification
5. **Interface Segregation** - Clients depend on specific interfaces

For detailed architecture documentation, see [ARCHITECTURAL_DESIGN.md](docs/ARCHITECTURAL_DESIGN.md)

---

## 📦 Prerequisites

### Required
- **Java**: JDK 11 or higher
  - [Download Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
  - [Download OpenJDK](https://adoptium.net/)
- **Maven**: 3.6 or higher
  - [Download Maven](https://maven.apache.org/download.cgi)
  - [Installation Guide](https://maven.apache.org/install.html)

### Optional
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code with Java extensions
- **Git**: For version control

### Verify Installation

```bash
# Check Java version
java -version
# Should show: java version "11" or higher

# Check Maven version
mvn -version
# Should show: Apache Maven 3.6.x or higher
```

---

## 🔨 Build Instructions

### Clone the Repository

```bash
git clone https://github.com/[your-org]/share-price-comparison-app.git
cd share-price-comparison-app
```

### Build the Project

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package application
mvn package

# Complete build (clean, compile, test, package)
mvn clean install
```

### Expected Output

```
[INFO] BUILD SUCCESS
[INFO] Total time:  X seconds
[INFO] Finished at: YYYY-MM-DD HH:MM:SS
```

---

## ▶️ Running the Application

### Method 1: Using Maven

```bash
mvn exec:java -Dexec.mainClass="com.shareapp.SharePriceApp"
```

### Method 2: Using Java (after building)

```bash
java -cp target/share-price-app-1.0-SNAPSHOT.jar com.shareapp.SharePriceApp
```

### Method 3: Using IDE

1. Open project in your IDE
2. Navigate to `src/main/java/com/shareapp/SharePriceApp.java`
3. Right-click → Run 'SharePriceApp.main()'

### Expected Demo Output

```
================================================================================
SHARE PRICE COMPARISON APPLICATION - SPRINT 1 DEMO
Demonstrating Simple Architecture Principles
================================================================================

--- SETUP: Creating Components ---
[Repository] MockStockRepository initialized
[DataSource] MockExternalDataSource initialized

--- DEMO 1: Fetch and Display Stock Data ---
...
```

---

## 📁 Project Structure

```
share-price-comparison-app/
├── src/
│   ├── main/
│   │   ├── java/com/shareapp/
│   │   │   ├── model/                # Domain Model Layer
│   │   │   │   ├── StockPrice.java
│   │   │   │   └── DateRange.java
│   │   │   ├── service/              # Service Layer
│   │   │   │   ├── IStockPriceService.java
│   │   │   │   └── StockPriceServiceImpl.java
│   │   │   ├── repository/           # Repository Layer
│   │   │   │   ├── IStockRepository.java
│   │   │   │   ├── IExternalDataSource.java
│   │   │   │   ├── MockStockRepository.java
│   │   │   │   └── MockExternalDataSource.java
│   │   │   ├── architecture/         # Architectural Patterns
│   │   │   │   └── RepositoryFactory.java
│   │   │   └── SharePriceApp.java    # Main Application
│   │   └── resources/
│   └── test/
│       └── java/com/shareapp/        # Unit Tests (Sprint 2)
├── docs/
│   ├── REQUIREMENTS.md               # Requirements Specification
│   ├── ARCHITECTURAL_DESIGN.md       # Architecture Documentation
│   ├── CODE_OF_CONDUCT.md           # Team Code of Conduct
│   ├── SPRINT1_TASK_ALLOCATION.md   # Task Distribution
│   └── GITHUB_SETUP.md              # Git Workflow Guide
├── diagrams/
│   └── component-specification-diagram.mmd
├── pom.xml                           # Maven Build Configuration
├── README.md                         # This file
└── LICENSE
```

---

## 📚 Documentation

| Document | Description | Link |
|----------|-------------|------|
| Requirements | Functional and non-functional requirements | [REQUIREMENTS.md](docs/REQUIREMENTS.md) |
| Architecture | Detailed architectural design and rationale | [ARCHITECTURAL_DESIGN.md](docs/ARCHITECTURAL_DESIGN.md) |
| Code of Conduct | Team collaboration guidelines | [CODE_OF_CONDUCT.md](docs/CODE_OF_CONDUCT.md) |
| Task Allocation | Sprint 1 task breakdown and assignments | [SPRINT1_TASK_ALLOCATION.md](docs/SPRINT1_TASK_ALLOCATION.md) |
| GitHub Setup | Branching strategy and Git workflow | [GITHUB_SETUP.md](docs/GITHUB_SETUP.md) |

---

## 👥 Team

| Name | Role | Responsibilities |
|------|------|-----------------|
| Anwar | Technical Lead & Architecture | Architecture design, technical decisions, code review, domain model, GitHub setup |
| Omran | Repository & Data Lead | Data persistence layer, repository implementation, external data sources |
| Meshari | Service & Business Logic Lead | Business logic, service layer, API integration, orchestration |
| Abdala | Documentation & Testing Lead | Technical writing, diagrams, requirements, quality assurance, project management |

---

## 📊 Sprint Progress

### Sprint 1: Introduction to Architectural Principles ✅
**Duration**: Jan 30 - Feb 19, 2026  
**Status**: Complete  
**Code Review**: Feb 20, 2026

**Deliverables**:
- ✅ Requirements identification and documentation
- ✅ Architectural design with component specification diagrams
- ✅ Abstract implementation of architectural elements
- ✅ GitHub project and branching strategy
- ✅ Code of Conduct
- ✅ Task allocation

### Sprint 2: Develop Software Architecture from Requirements 📅
**Duration**: Feb 20 - Mar 19, 2026  
**Status**: Not Started  
**Code Review**: Mar 20, 2026

**Planned Deliverables**:
- Business concept model
- Use case model
- System interfaces
- Business type model
- Initial system architecture
- Clean architecture implementation

### Sprint 3: Compound Components and Domain-Independent Styles 📅
**Duration**: Mar 20 - Apr 24, 2026  
**Status**: Not Started  
**Code Review**: Apr 23, 2026

**Planned Deliverables**:
- Compound components
- Domain-independent architectural styles
- SOA implementation
- Complete testing suite
- Final report

---

## 🔧 Development Workflow

### For Team Members

1. **Clone and setup**:
   ```bash
   git clone <repository-url>
   cd share-price-comparison-app
   mvn clean install
   ```

2. **Create feature branch**:
   ```bash
   git checkout develop
   git pull origin develop
   git checkout -b feature/your-feature-name
   ```

3. **Make changes and commit**:
   ```bash
   git add .
   git commit -m "feat(scope): description"
   ```

4. **Push and create PR**:
   ```bash
   git push origin feature/your-feature-name
   # Create Pull Request on GitHub
   ```

For detailed Git workflow, see [GITHUB_SETUP.md](docs/GITHUB_SETUP.md)

---

## 🧪 Testing

### Sprint 1
- Manual testing through demo application
- Code review validation

### Future Sprints
- Unit tests with JUnit 5
- Integration tests
- UI tests (if applicable)
- Continuous Integration with GitHub Actions

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙋‍♂️ Support

For questions or issues:
- Check the [documentation](docs/)
- Contact the team lead
- Create an issue on GitHub (for team members)

---

## 📌 Quick Links

- [Requirements](docs/REQUIREMENTS.md)
- [Architecture](docs/ARCHITECTURAL_DESIGN.md)
- [Code of Conduct](docs/CODE_OF_CONDUCT.md)
- [Sprint 1 Tasks](docs/SPRINT1_TASK_ALLOCATION.md)
- [GitHub Workflow](docs/GITHUB_SETUP.md)

---

**Last Updated**: February 6, 2026  
**Current Sprint**: Sprint 1  
**Next Milestone**: Code Review - February 20, 2026
