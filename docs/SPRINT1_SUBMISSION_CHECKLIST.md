# Sprint 1 Submission Checklist
## Share Price Comparison Application

**Sprint**: 1 - Introduction to Architectural Principles  
**Deadline**: February 19, 2026  
**Code Review**: February 20, 2026

---

## Pre-Submission Checklist

Use this checklist to ensure all Sprint 1 deliverables are complete before the code review on February 20, 2026.

---

## 📋 Part 1: Requirements Identification (25 Marks)

### ✅ Requirements Documentation

- [ ] **Functional Requirements (FR1-FR5)** documented in REQUIREMENTS.md
  - [ ] FR1: Share price data retrieval
  - [ ] FR2: Data persistence
  - [ ] FR3: Data visualization
  - [ ] FR4: User interface
  - [ ] FR5: Offline functionality

- [ ] **Non-Functional Requirements (NFR1-NFR5)** documented
  - [ ] NFR1: Performance requirements
  - [ ] NFR2: Scalability requirements
  - [ ] NFR3: Reliability requirements
  - [ ] NFR4: Maintainability requirements
  - [ ] NFR5: Usability requirements

- [ ] **Technical Requirements** documented
  - [ ] Technology stack specified
  - [ ] Data source identified
  - [ ] Architecture pattern chosen

- [ ] **Constraints and Assumptions** documented
  - [ ] Time constraints
  - [ ] Data constraints
  - [ ] Network constraints

- [ ] **Requirements Traceability Matrix** created
  - [ ] Maps requirements to components
  - [ ] Shows priority and sprint allocation

### 📊 Quality Criteria
- [ ] Requirements clearly identified and scoped (5 marks)
- [ ] Requirements investigated in detail (10 marks)
- [ ] Requirements organized and presented effectively (5 marks)
- [ ] Requirements aligned to overall goals (5 marks)

---

## 🏗 Part 2: Architectural Design (30 Marks)

### ✅ Architecture Documentation

- [ ] **Architectural Principles** documented
  - [ ] Simple Architecture principles explained
  - [ ] Layered architecture described
  - [ ] Separation of concerns demonstrated

- [ ] **Component Specification Diagram** created
  - [ ] Shows all 4 layers clearly
  - [ ] Shows all components and their interactions
  - [ ] Shows interfaces and implementations
  - [ ] Uses proper notation and labeling
  - [ ] Saved in diagrams/ folder

- [ ] **Layer Descriptions** complete
  - [ ] Presentation Layer described
  - [ ] Service Layer described
  - [ ] Repository Layer described
  - [ ] Domain Model Layer described

- [ ] **Design Patterns** documented
  - [ ] Repository Pattern explained
  - [ ] Factory Pattern explained
  - [ ] Strategy Pattern explained
  - [ ] Adapter Pattern explained
  - [ ] Rationale for each pattern provided

- [ ] **Interface Specifications** defined
  - [ ] IStockPriceService interface
  - [ ] IStockRepository interface
  - [ ] IExternalDataSource interface
  - [ ] Method signatures documented
  - [ ] JavaDoc comments included

- [ ] **Data Flow Scenarios** documented
  - [ ] First-time data fetch (online)
  - [ ] Cached data retrieval (offline)
  - [ ] Two-stock comparison
  - [ ] Step-by-step flow for each

- [ ] **Technology Stack** justified
  - [ ] Each technology choice explained
  - [ ] Rationale provided
  - [ ] Alternatives considered

### 📊 Quality Criteria
- [ ] Architectural concepts well-researched and applied (10 marks)
- [ ] Component specification diagram clear and complete (10 marks)
- [ ] Architecture supports requirements and project goals (10 marks)

---

## 💻 Part 3: Implementation (20 Marks)

### ✅ Code Implementation

**Domain Model Layer:**
- [ ] `StockPrice.java` implemented
  - [ ] All OHLCV fields included
  - [ ] Validation logic added
  - [ ] Helper methods (getDailyChange, getPercentageChange)
  - [ ] equals() and hashCode() implemented
  - [ ] toString() implemented
  - [ ] Full JavaDoc comments

- [ ] `DateRange.java` implemented
  - [ ] Start and end date fields
  - [ ] 2-year maximum validation
  - [ ] Business logic for date validation
  - [ ] Helper methods (contains, getDays)
  - [ ] Full JavaDoc comments

**Service Layer:**
- [ ] `IStockPriceService.java` interface defined
  - [ ] All method signatures declared
  - [ ] Full JavaDoc for each method
  - [ ] Clear contracts established

- [ ] `StockPriceServiceImpl.java` implemented
  - [ ] Constructor injection
  - [ ] getStockPrices() method
  - [ ] refreshData() method
  - [ ] isDataAvailableLocally() method
  - [ ] validateSymbol() method
  - [ ] Caching logic
  - [ ] Full JavaDoc comments

**Repository Layer:**
- [ ] `IStockRepository.java` interface defined
  - [ ] All CRUD operations declared
  - [ ] Full JavaDoc for each method

- [ ] `MockStockRepository.java` implemented
  - [ ] In-memory HashMap storage
  - [ ] save() methods
  - [ ] find() methods
  - [ ] exists() method
  - [ ] delete() methods
  - [ ] count() and getAllSymbols()
  - [ ] Full JavaDoc comments

- [ ] `IExternalDataSource.java` interface defined
  - [ ] fetchStockPrices() declared
  - [ ] validateSymbol() declared
  - [ ] Full JavaDoc comments

- [ ] `MockExternalDataSource.java` implemented
  - [ ] Dummy data generation
  - [ ] Realistic OHLCV values
  - [ ] Business day filtering
  - [ ] Full JavaDoc comments

**Architecture Patterns:**
- [ ] `RepositoryFactory.java` implemented
  - [ ] RepositoryType enum
  - [ ] createRepository() factory method
  - [ ] createDefaultRepository() method
  - [ ] createRepositoryFromConfig() method
  - [ ] Full JavaDoc comments

**Main Application:**
- [ ] `SharePriceApp.java` implemented
  - [ ] main() method
  - [ ] Demo 1: Fetch and display
  - [ ] Demo 2: Caching demonstration
  - [ ] Demo 3: Date validation
  - [ ] Demo 4: Stock comparison
  - [ ] Clear console output
  - [ ] Full JavaDoc comments

### ✅ Code Quality

- [ ] **No Compilation Errors**
  - [ ] All Java files compile successfully
  - [ ] No syntax errors
  - [ ] No type errors

- [ ] **Coding Standards**
  - [ ] Consistent indentation (4 spaces or tabs)
  - [ ] Clear variable/method/class naming
  - [ ] No magic numbers
  - [ ] Proper package structure
  - [ ] Follows Java naming conventions

- [ ] **Documentation**
  - [ ] Every class has class-level JavaDoc
  - [ ] Every public method has method-level JavaDoc
  - [ ] Complex logic has inline comments
  - [ ] @param, @return, @throws tags used appropriately

- [ ] **Build System**
  - [ ] pom.xml configured correctly
  - [ ] All dependencies listed
  - [ ] Maven build succeeds
  - [ ] Application runs successfully

### 📊 Quality Criteria
- [ ] Code implements components and meets sprint requirements (10 marks)
- [ ] Code is clean, organized, and commented effectively (5 marks)
- [ ] Code builds without errors and functions as expected (5 marks)

---

## 👥 Part 4: Team Management (25 Marks)

### ✅ GitHub Setup

- [ ] **Repository Created**
  - [ ] Repository name: share-price-comparison-app
  - [ ] Visibility: Private
  - [ ] README.md initialized
  - [ ] .gitignore for Java configured
  - [ ] LICENSE file added

- [ ] **Branch Structure**
  - [ ] `main` branch exists
  - [ ] `develop` branch created
  - [ ] Develop set as default branch
  - [ ] Feature branches created for tasks

- [ ] **Branch Protection Rules**
  - [ ] `main` protected (2 PR approvals required)
  - [ ] `develop` protected (1 PR approval required)
  - [ ] Status checks required
  - [ ] Direct commits disabled

- [ ] **Team Access**
  - [ ] All team members added as collaborators
  - [ ] Appropriate permissions granted
  - [ ] Everyone can push to feature branches

- [ ] **Commit History**
  - [ ] Multiple commits from each team member
  - [ ] Clear, descriptive commit messages
  - [ ] Follows conventional commits format
  - [ ] Commits linked to issues (where applicable)

### ✅ Project Management Tool

- [ ] **Tool Selected**
  - [ ] GitHub Projects / Trello / Jira set up
  - [ ] All team members have access
  - [ ] Login details documented

- [ ] **Board Configured**
  - [ ] Columns created (Backlog, To Do, In Progress, Review, Done)
  - [ ] Automation rules set up (if applicable)

- [ ] **Tasks Added**
  - [ ] All Sprint 1 tasks from SPRINT1_TASK_ALLOCATION.md added
  - [ ] Each task has description
  - [ ] Each task has assigned member
  - [ ] Each task has estimated hours
  - [ ] Each task has due date
  - [ ] Labels applied to tasks

- [ ] **Sprint Tracking**
  - [ ] Sprint 1 milestone created
  - [ ] Tasks linked to sprint
  - [ ] Progress visible on board

### ✅ Code of Conduct

- [ ] **Document Created**
  - [ ] CODE_OF_CONDUCT.md exists
  - [ ] All sections complete:
    - [ ] Core values defined
    - [ ] Communication standards
    - [ ] Workflow standards
    - [ ] Conflict resolution process
    - [ ] Work distribution principles
    - [ ] Meeting schedule
    - [ ] Academic integrity section

- [ ] **Team Agreement**
  - [ ] All team members have read it
  - [ ] All team members have signed it
  - [ ] Signatures documented in file or separate sheet

### ✅ Task Allocation

- [ ] **SPRINT1_TASK_ALLOCATION.md** created
  - [ ] All team members listed
  - [ ] All tasks categorized
  - [ ] Tasks assigned to specific members
  - [ ] Estimated hours for each task
  - [ ] Fair distribution (each member ~18 hours)
  - [ ] Timeline with weekly breakdown

### ✅ Documentation

- [ ] **GITHUB_SETUP.md** created
  - [ ] Branching strategy documented
  - [ ] Commit message conventions
  - [ ] Pull request process
  - [ ] Daily workflow guide

- [ ] **PROJECT_MANAGEMENT_SETUP.md** created
  - [ ] Tool setup instructions
  - [ ] Usage guidelines
  - [ ] Task card template

- [ ] **README.md** comprehensive
  - [ ] Project overview
  - [ ] Features list (current and planned)
  - [ ] Architecture diagram
  - [ ] Build instructions
  - [ ] Running instructions
  - [ ] Project structure
  - [ ] Team information
  - [ ] Sprint progress

### 📊 Quality Criteria
- [ ] GitHub project setup complete with branches (5 marks)
- [ ] Project Management Tool is set up and requirements are added (5 marks)
- [ ] Code of conduct defined and agreed upon (5 marks)
- [ ] Tasks allocated fairly across team members (5 marks)
- [ ] Team worked together on code and commits (5 marks)

---

## 📝 Final Preparation

### ✅ Code Review Preparation

- [ ] **Test the Application**
  - [ ] Compile the code successfully
  - [ ] Run SharePriceApp.main()
  - [ ] Verify all 4 demos execute correctly
  - [ ] Check console output is clear and informative

- [ ] **Review Documentation**
  - [ ] Read through all .md files
  - [ ] Check for typos and formatting issues
  - [ ] Verify all links work
  - [ ] Ensure diagrams are visible

- [ ] **Prepare Presentation** (for Feb 20 Code Review)
  - [ ] 5-minute overview slides
  - [ ] Architecture diagram ready to show
  - [ ] Code examples prepared
  - [ ] Demo application ready to run
  - [ ] All team members know their part

- [ ] **Team Readiness**
  - [ ] All team members available for code review
  - [ ] Laptop ready with code
  - [ ] GitHub logged in
  - [ ] Project management tool accessible
  - [ ] Can build and run application

### ✅ File Organization

Verify all files are in correct locations:

```
share-price-comparison-app/
├── src/main/java/com/shareapp/
│   ├── model/
│   │   ├── StockPrice.java ✓
│   │   └── DateRange.java ✓
│   ├── service/
│   │   ├── IStockPriceService.java ✓
│   │   └── StockPriceServiceImpl.java ✓
│   ├── repository/
│   │   ├── IStockRepository.java ✓
│   │   ├── IExternalDataSource.java ✓
│   │   ├── MockStockRepository.java ✓
│   │   └── MockExternalDataSource.java ✓
│   ├── architecture/
│   │   └── RepositoryFactory.java ✓
│   └── SharePriceApp.java ✓
├── docs/
│   ├── REQUIREMENTS.md ✓
│   ├── ARCHITECTURAL_DESIGN.md ✓
│   ├── CODE_OF_CONDUCT.md ✓
│   ├── SPRINT1_TASK_ALLOCATION.md ✓
│   ├── GITHUB_SETUP.md ✓
│   └── PROJECT_MANAGEMENT_SETUP.md ✓
├── diagrams/
│   └── component-specification-diagram.mmd ✓
├── pom.xml ✓
├── README.md ✓
├── .gitignore ✓
└── LICENSE ✓
```

---

## 📊 Self-Assessment

Before submission, honestly assess each criterion:

| Category | Max Marks | Self-Assessment | Comments |
|----------|-----------|-----------------|----------|
| Requirements - Scoped | 5 | /5 | |
| Requirements - Detail | 10 | /10 | |
| Requirements - Organized | 5 | /5 | |
| Requirements - Aligned | 5 | /5 | |
| **Requirements Total** | **25** | **/25** | |
| | | | |
| Architecture - Research | 10 | /10 | |
| Architecture - Diagram | 10 | /10 | |
| Architecture - Support | 10 | /10 | |
| **Architecture Total** | **30** | **/30** | |
| | | | |
| Implementation - Components | 10 | /10 | |
| Implementation - Quality | 5 | /5 | |
| Implementation - Builds | 5 | /5 | |
| **Implementation Total** | **20** | **/20** | |
| | | | |
| Team - GitHub Setup | 5 | /5 | |
| Team - PM Tool | 5 | /5 | |
| Team - Code of Conduct | 5 | /5 | |
| Team - Task Allocation | 5 | /5 | |
| Team - Collaboration | 5 | /5 | |
| **Team Total** | **25** | **/25** | |
| | | | |
| **GRAND TOTAL** | **100** | **/100** | |

---

## 🚀 Submission Process

### Day Before Code Review (Feb 19)

1. [ ] Complete all items in this checklist
2. [ ] Test application one final time
3. [ ] Merge all feature branches to `develop`
4. [ ] Create `release/sprint-1` branch from `develop`
5. [ ] Final testing on release branch
6. [ ] Merge release to `main` with tag `v1.0.0-sprint1`
7. [ ] Push all changes to GitHub
8. [ ] Verify everything is pushed correctly
9. [ ] Send confirmation email to team

### Day of Code Review (Feb 20)

1. [ ] Arrive 10 minutes early
2. [ ] Set up laptop and connect to display
3. [ ] Open GitHub repository
4. [ ] Open project management tool
5. [ ] Have application ready to run
6. [ ] Have architecture diagram visible
7. [ ] Team members ready to present their parts
8. [ ] Be prepared to answer questions

---

## ✅ Sign-Off

**Team Lead Verification:**  
All items checked: ______ (Initials) Date: ______

**Technical Review:**  
Code compiles and runs: ______ (Initials) Date: ______

**Documentation Review:**  
All documents complete: ______ (Initials) Date: ______

**Team Consensus:**  
Ready for submission: 
- [ ] Member 1: ______ (Initials)
- [ ] Member 2: ______ (Initials)
- [ ] Member 3: ______ (Initials)
- [ ] Member 4: ______ (Initials)
- [ ] Member 5: ______ (Initials)

---

**Good luck with your Sprint 1 code review! 🎉**

---

**Last Updated**: February 6, 2026  
**Sprint Deadline**: February 19, 2026  
**Code Review**: February 20, 2026
