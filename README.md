# StockCompare

> **Share Price Technical Analysis Web Application**

A robust and scalable Java-based web application for comparing and analyzing stock prices over time. Built as part of the Software Architecture and Design coursework.

[![Java](https://img.shields.io/badge/Java-11%2B-orange)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

##  Project Overview

StockCompare enables users to:
-  Fetch daily stock price data for any ticker symbol
- Store data locally for offline access
-  Visualize price trends with interactive charts
-  Compare performance of two stocks side-by-side

**Course:** Software Architecture and Design  
**Academic Year:** 2025-2026  
**Team:** Anwar, Abdala, Meshari, Ismail, Omran  

---

## Architecture

StockCompare follows a **layered architecture** with clear separation of concerns:

```
┌─────────────────────────────────────┐
│     Presentation Layer (UI)         │
├─────────────────────────────────────┤
│     Controller Layer                │
├─────────────────────────────────────┤
│     Business Logic Layer (Services) │
├─────────────────────────────────────┤
│     Data Access Layer (Repository)  │
├─────────────────────────────────────┤
│     External Systems (API, DB)      │
└─────────────────────────────────────┘
```

### Key Design Patterns
- **Repository Pattern:** Abstracts data access
- **Facade Pattern:** Simplifies complex subsystems
- **Template Method:** Defines algorithm skeleton
- **Adapter Pattern:** Adapts external APIs to domain model

See [Component Specification Diagram](docs/Component_Specification_Diagram.md) for details.

---

##  Features

### Sprint 1 (Complete: 19 Feb 2026) ✅
- ✅ Requirements specification
- ✅ Component architecture design
- ✅ Abstract implementation of core interfaces
- ✅ Domain model classes

### Sprint 2 (Due: 19 Mar 2026) 🔄
- 🔄 Clean architecture implementation
- 🔄 Use case models and sequence diagrams
- 🔄 Yahoo Finance API integration
- 🔄 SQLite database persistence

### Sprint 3 (Due: 24 Apr 2026) 📅
- 📅 Compound components design
- 📅 Domain-independent architectural styles
- 📅 Service-Oriented Architecture (SOA)
- 📅 Comprehensive testing suite

---

## 🛠️ Technology Stack

| Category | Technology |
|----------|-----------|
| **Language** | Java 11+ |
| **Build Tool** | Maven / Gradle |
| **Data Source** | Yahoo Finance API |
| **Database** | SQLite (local) |
| **Testing** | JUnit 5 |
| **Logging** | java.util.logging |

### Future Dependencies
- **HTTP Client:** Apache HttpClient / Java 11 HttpClient
- **JSON Parsing:** Jackson / Gson
- **Charting:** JFreeChart / XChart
- **Database Driver:** SQLite JDBC

---

## 📁 Project Structure

```
stockcompare/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── stockcompare/
│   │   │           ├── model/          # Domain entities
│   │   │           ├── service/        # Business logic
│   │   │           ├── repository/     # Data access
│   │   │           ├── controller/     # Request handlers
│   │   │           └── view/           # UI components
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── stockcompare/
├── docs/
│   ├── requirements/
│   ├── architecture/
│   ├── diagrams/
│   └── meeting-notes/
├── .gitignore
├── README.md
└── pom.xml (or build.gradle)
```

---

##  Getting Started

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Maven 3.6+ or Gradle 7.0+
- Git

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/Abdalamohamed7906/Software-Architecture-and-Design.git
cd Software-Architecture-and-Design
```

2. **Build the project**

Using Maven:
```bash
mvn clean install
```

Using Gradle:
```bash
gradle build
```

3. **Run the application** (Sprint 2+)
```bash
# Maven
mvn exec:java

# Gradle
gradle run
```

---

## 📖 Documentation

- [Requirements Specification](docs/Sprint1_Requirements_StockCompare.md)
- [Component Specification Diagram](docs/Component_Specification_Diagram.md)
- [Team Code of Conduct](docs/Team_Code_of_Conduct.md)
- [Task Allocation (Asana)](docs/Sprint1_Task_Allocation_Asana.md)

---

## Contributing

We follow the **Feature Branch Workflow**:

1. Create a feature branch from `develop`:
   ```bash
   git checkout develop
   git pull origin develop
   git checkout -b feature/your-feature-name
   ```

2. Make your changes and commit:
   ```bash
   git add .
   git commit -m "[FEATURE] Add description of your changes"
   ```

3. Push your branch:
   ```bash
   git push origin feature/your-feature-name
   ```

4. Create a Pull Request (PR) to `develop`
   - Assign at least 2 reviewers
   - Link related Asana task
   - Wait for approval before merging

### Commit Message Format
```
[TYPE] Brief description

Detailed explanation if needed

- Bullet points for multiple changes
```

**Types:** `[FEATURE]`, `[BUGFIX]`, `[REFACTOR]`, `[DOCS]`, `[TEST]`

See [CONTRIBUTING.md](CONTRIBUTING.md) for details.

---

## 👥 Team

| Name | Role | Responsibilities |
|------|------|-----------------|
| **Anwar** | Project Lead | Requirements, Domain Models, Services, Integration |
| **Abdala** | Architecture Lead | Component Diagrams, Design Patterns, Interfaces, Submission |
| **Meshari** | Documentation Lead | Glossary, Code of Conduct, README Updates |
| **Ismail** | QA & Notes Lead | Meeting Notes, Testing, Documentation Organization |
| **Omran** | DevOps Lead | Maven Setup, Git Support, Build System |

---

## Project Timeline

| Sprint | Deadline | Focus | Status |
|--------|----------|-------|--------|
| **Sprint 1** | 19 Feb 2026 | Requirements, Architecture, Abstract Implementation | 🔄 In Progress |
| **Sprint 2** | 19 Mar 2026 | Clean Architecture, Use Cases, Implementation | 📅 Upcoming |
| **Sprint 3** | 24 Apr 2026 | Compound Components, Styles, SOA, Testing | 📅 Upcoming |

**Code Reviews:**
- Sprint 1: 20 Feb 2026
- Sprint 2: 20 Mar 2026
- Sprint 3: 24 Apr 2026

**Sprint 1 Meetings:**
-  Initial Planning: Friday, 7 Feb 2026 (Completed)
- Mid-Sprint Check-in: Thursday, 13 Feb 2026
- Progress Review: Friday, 14 Feb 2026, 6:00 PM
- Final Review: Wednesday, 19 Feb 2026 (Microsoft Teams)
- Code Review: Thursday, 20 Feb 2026

---

## 🧪 Testing

### Run Tests

Using Maven:
```bash
mvn test
```

Using Gradle:
```bash
gradle test
```

### Test Coverage
- Target: 70% code coverage minimum
- All business logic must have unit tests
- Integration tests for data layer (Sprint 3)

---

## 📊 Sprint Progress

### Sprint 1 Checklist
- [x] Requirements Document
- [x] Component Specification Diagram
- [x] Domain Model Classes
- [x] Repository Interfaces
- [x] Service Interfaces
- [x] Abstract Service Implementation
- [x] GitHub Repository Setup
- [x] Asana Board Setup
- [x] Team Code of Conduct
- [x] Code Review Preparation

---

## 🐛 Known Issues

*No known issues for Sprint 1 (abstract implementation only)*

Report issues: [GitHub Issues](https://github.com/Abdalamohamed7906/Software-Architecture-and-Design/issues)

---

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- **Course Instructor:** [Instructor Name]
- **Yahoo Finance API:** For providing stock market data
- **Open Source Libraries:** JFreeChart, Jackson, SQLite JDBC

---

## 📞 Contact

**Team Communication:**
- WhatsApp/MS Teams
- GitHub Issues: [Issue Tracker](https://github.com/Abdalamohamed7906/Software-Architecture-and-Design/issues)
- Asana Board: [https://app.asana.com/1/15441878602959/project/1213167865275390/board/1213169424196971]

**Meeting Schedule:**
- Regular check-ins: Thursdays and Fridays
- Final reviews: Microsoft Teams (before submissions)

---

## 📈 Project Status

![Sprint Progress](https://progress-bar.dev/33/?title=Overall%20Progress&width=500)

**Last Updated:** 10 February 2026  
**Current Sprint:** Sprint 1  
**Next Milestone:** Sprint 1 Code Review (20 Feb 2026)

---

<div align="center">
  <p>Built with ❤️ by the StockCompare Team</p>
  <p><strong>Anwar • Abdala • Meshari • Ismail • Omran</strong></p>
</div>
