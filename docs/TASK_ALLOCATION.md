# Sprint 1 - Task Allocation
**Project**: Stock-Compare  
**Team**: Anwar, Abdala, Meshari, Omran  
**Sprint**: 1 (Due: 19.2.26)  
**Code Review**: 20.2.26

---

## Team Roles

| Member | Primary Role | Secondary Role |
|--------|-------------|----------------|
| Abdala | Requirements Lead | Documentation |
| Anwar | Architecture Lead | Implementation |
| Meshari | Implementation Lead | Testing |
| Omran | Project Management | Code Review |

---

## Task Breakdown
Anwar (40%) - Code Implementation & Diagrams Lead

All 12 Java files (interfaces and abstract classes)
All PROVIDED/REQUIRED annotations
All 3 architecture diagrams + guides
Main.java and MainUIController.java
Technical documentation
TASK_ALLOCATION.md

Abdalla (30%) - Requirements & Documentation Lead

Complete REQUIREMENTS.md
User stories and acceptance criteria
All functional and non-functional requirements
Review and quality assurance
GitHub setup

Meshari (15%) - Testing & Integration

Code compilation verification
Main.java testing
PROVIDED/REQUIRED annotation checks
Integration testing

Omran (15%) - Project Management & Documentation

CODE_OF_CONDUCT.md
MEETING_MINUTES.md
CODE_REVIEW_PREP.md
SUBMISSION_CHECKLIST.md



**GitHub Branch Strategy**:
```
main (protected)
  └── develop
       ├── feature/requirements
       ├── feature/architecture
       ├── feature/domain-models
       ├── feature/service-interfaces
       └── feature/data-layer
```

**Checklist**:
- [ ] Repository created and all members have access
- [ ] Branching strategy documented
- [ ] Project board set up with Sprint 1 tasks
- [ ] Team can successfully clone and push to repo

---

### 3. Code of Conduct (All - Collaborate)

**Assigned to**: All team members  
**Lead**: Omran  
**Deadline**: 14.2.26  
**Deliverables**:
- [ ] Review CODE_OF_CONDUCT.md template
- [ ] Customize for team needs
- [ ] Define meeting schedule
- [ ] Define communication protocols
- [ ] Define conflict resolution process
- [ ] All team members sign off

**Meeting Schedule** (to be agreed):
- Weekly team meeting: [Day/Time TBD]
- Daily standup (async): [Platform TBD]
- Code review sessions: As needed

**Checklist**:
- [ ] All sections completed
- [ ] All team members agree to terms
- [ ] Signed by all members with dates

---

### 4. Architectural Design (Abdala - Lead, Anwar - Support)

**Assigned to**: Abdala  
**Support**: Anwar  
**Deadline**: 17.2.26  
**Deliverables**:
- [ ] Complete ARCHITECTURE.md document
- [ ] Research Simple Architecture principles
- [ ] Create high-level architecture diagram
- [ ] Define all components (9 components total)
- [ ] Define interfaces for each component
- [ ] Create component interaction diagram
- [ ] Document design decisions and rationale
- [ ] Interface allocation analysis

**Components to Define**:
1. MainUIController (Presentation)
2. ChartDisplayComponent (Presentation)
3. InputFormComponent (Presentation)
4. StockDataManager (Business Logic)
5. PriceAnalyzer (Business Logic)
6. ComparisonService (Business Logic)
7. LocalRepository (Data Access)
8. APIService (Data Access)
9. CacheManager (Data Access)

**Checklist**:
- [ ] All components documented with responsibilities
- [ ] All interfaces defined
- [ ] Architecture diagram created
- [ ] Design validated by team
- [ ] Supports all requirements from REQUIREMENTS.md

---

### 5. Java Implementation - Domain Models (Meshari)

**Assigned to**: Meshari  
**Deadline**: 17.2.26  
**Deliverables**:
- [ ] Implement StockData.java class
- [ ] Implement DateRange.java class
- [ ] Add JavaDoc comments
- [ ] Write basic unit tests
- [ ] Ensure code compiles without errors

**Files to Create**:
- `src/main/java/com/stockcompare/domain/StockData.java`
- `src/main/java/com/stockcompare/domain/DateRange.java`
- `src/test/java/com/stockcompare/domain/StockDataTest.java`
- `src/test/java/com/stockcompare/domain/DateRangeTest.java`

**Checklist**:
- [ ] All fields properly encapsulated
- [ ] Business rules enforced (2-year max range)
- [ ] Comprehensive JavaDoc
- [ ] Unit tests passing
- [ ] Code follows Java naming conventions

---

### 6. Java Implementation - Service Layer (Abdala)

**Assigned to**: Abdala  
**Deadline**: 17.2.26  
**Deliverables**:
- [ ] Create service interfaces (IStockDataManager, IComparisonService)
- [ ] Implement AbstractStockDataManager
- [ ] Implement AbstractPriceAnalyzer
- [ ] Create custom exceptions (StockDataException)
- [ ] Add JavaDoc comments
- [ ] Ensure code compiles

**Files to Create**:
- `src/main/java/com/stockcompare/service/IStockDataManager.java`
- `src/main/java/com/stockcompare/service/IComparisonService.java`
- `src/main/java/com/stockcompare/service/AbstractStockDataManager.java`
- `src/main/java/com/stockcompare/service/AbstractPriceAnalyzer.java`
- `src/main/java/com/stockcompare/service/StockDataException.java`

**Checklist**:
- [ ] All interfaces defined with clear contracts
- [ ] Abstract classes provide template methods
- [ ] Exception handling defined
- [ ] JavaDoc complete
- [ ] Follows SOLID principles

---

### 7. Java Implementation - Data Layer (Anwar)

**Assigned to**: Anwar  
**Deadline**: 17.2.26  
**Deliverables**:
- [ ] Create data layer interfaces (IRepository, IAPIService, ICacheManager)
- [ ] Add JavaDoc comments
- [ ] Define exception classes (APIException)
- [ ] Ensure interfaces are complete
- [ ] Ensure code compiles

**Files to Create**:
- `src/main/java/com/stockcompare/data/IRepository.java`
- `src/main/java/com/stockcompare/data/IAPIService.java`
- `src/main/java/com/stockcompare/data/ICacheManager.java`

**Checklist**:
- [ ] All CRUD operations defined
- [ ] Clear method signatures
- [ ] Comprehensive JavaDoc
- [ ] Consistent naming conventions

---

### 8. Java Implementation - Presentation Layer (Omran)

**Assigned to**: Omran  
**Deadline**: 17.2.26  
**Deliverables**:
- [ ] Create presentation interfaces (IChartDisplay, IInputHandler)
- [ ] Add JavaDoc comments
- [ ] Define UI component contracts
- [ ] Ensure code compiles

**Files to Create**:
- `src/main/java/com/stockcompare/presentation/IChartDisplay.java`
- `src/main/java/com/stockcompare/presentation/IInputHandler.java`

**Checklist**:
- [ ] All UI interactions defined
- [ ] Clear separation from business logic
- [ ] JavaDoc complete
- [ ] Interfaces support requirements

---

### 9. Testing & Integration (Meshari - Lead, All - Participate)

**Assigned to**: Meshari  
**Support**: All  
**Deadline**: 18.2.26  
**Deliverables**:
- [ ] Ensure all code compiles in IntelliJ
- [ ] Run basic unit tests
- [ ] Create test plan for Sprint 2
- [ ] Integration testing of abstract implementations
- [ ] Fix any compilation errors

**Checklist**:
- [ ] Project builds without errors
- [ ] All tests pass
- [ ] No compiler warnings
- [ ] Code quality check passed

---

### 10. Documentation & Final Review (All)

**Assigned to**: All team members  
**Deadline**: 18.2.26  
**Deliverables**:
- [ ] Review all markdown documents
- [ ] Ensure consistency across docs
- [ ] Update README with current status
- [ ] Prepare code review presentation
- [ ] Practice demo

**Documents to Review**:
- README.md
- CODE_OF_CONDUCT.md
- docs/REQUIREMENTS.md
- docs/ARCHITECTURE.md

**Checklist**:
- [ ] All documents complete and proofread
- [ ] No broken references between documents
- [ ] GitHub repository clean and organized
- [ ] Ready for code review presentation

---

## Weekly Milestones

### Week 1 (10.2 - 14.2)
- [x] Project setup and repository creation
- [x] Code of Conduct agreed and signed
- [ ] Initial requirements draft
- [ ] Architecture research started

### Week 2 (15.2 - 18.2)
- [ ] Requirements finalized
- [ ] Architecture design completed
- [ ] All Java interfaces and abstract classes implemented
- [ ] Documentation complete
- [ ] Preparation for code review

### Code Review Day (19.2 - 20.2)
- [ ] Final testing and validation
- [ ] Submit all deliverables
- [ ] Code review meeting (20.2.26)

---

## Communication Plan

**Daily Updates**: Post progress in team chat by end of day  
**Blockers**: Report immediately in team channel  
**Questions**: Ask in team channel, don't wait  
**Code Reviews**: Use GitHub pull request reviews  
**Meetings**: Weekly sync + ad-hoc as needed

---

## Success Criteria

✅ All tasks completed by deadlines  
✅ Code compiles and runs without errors  
✅ All documentation complete and reviewed  
✅ GitHub repository properly organized  
✅ Team ready for code review presentation  
✅ Each member understands their component  

---

## Notes

- **Fair Distribution**: Each member has approximately 25% of the work
- **Collaboration**: Help each other if someone is stuck
- **Quality**: Don't rush - quality matters more than speed
- **Communication**: Keep team informed of progress daily
- **Early Review**: Submit for peer review 1-2 days early

---

**Last Updated**: [Date]  
**Status**: In Progress
