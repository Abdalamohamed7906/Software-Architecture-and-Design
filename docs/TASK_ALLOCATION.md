# Sprint 1 - Task Allocation
**Project**: Stock-Compare  
**Team**: Anwar, Abdalla, Meshari, Omran  
**Sprint**: 1 (Due: 19.2.26)  
**Code Review**: 20.2.26

---

## Team Roles & Contributions

| Member | Role | Contribution % | Hours | Work Focus |
|--------|------|----------------|-------|------------|
| Anwar | **ALL Java Code Implementation** + Diagrams Lead | 40% | ~25 hours | **100% of coding** |
| Abdalla | Requirements & Documentation Lead | 30% | ~15 hours | Requirements only |
| Meshari | Testing & Integration | 15% | ~8 hours | Testing only |
| Omran | Project Management & Documentation | 15% | ~8 hours | Management only |

**IMPORTANT: Anwar wrote ALL 15 Java files. Other members did requirements, testing, and management - NOT code.**

---

## Anwar (40%) - Code Implementation & Diagrams

### Tasks Completed:
✅ **ALL Java Code Implementation (15 files)**
- **Domain Layer (2 files):**
  - StockData.java - Complete domain model
  - DateRange.java - Business rule validation (2-year max)

- **Service Layer (6 files):**
  - IStockDataManager.java - Core business interface
  - AbstractStockDataManager.java - Template implementation
  - IPriceAnalyzer.java - Analysis interface
  - AbstractPriceAnalyzer.java - Analysis implementation
  - IComparisonService.java - Comparison interface
  - StockDataException.java - Custom exception

- **Data Layer (3 files):**
  - IRepository.java - Persistence interface
  - IAPIService.java - External API interface
  - ICacheManager.java - Cache interface

- **Presentation Layer (3 files):**
  - IChartDisplay.java - Chart rendering interface
  - IInputHandler.java - Input handling interface
  - MainUIController.java - Main controller component

- **Application (1 file):**
  - Main.java - Demonstration program

**Anwar wrote ALL the Java code - all 15 files from scratch**

✅ **PROVIDED/REQUIRED Annotations**
- Added architectural documentation to all 15 files
- Clear ○ PROVIDED and ◐ REQUIRED comments throughout
- Showed component dependencies in every file

✅ **Architecture Diagrams (8 files)**
- high-level-architecture.mermaid
- component-specification.mermaid
- component-provided-required.mermaid
- PROVIDED-REQUIRED-SPECIFICATION.md
- PROVIDED-REQUIRED-DIAGRAM.txt
- DIAGRAM_GUIDE.md
- TEXT_DIAGRAMS.md
- QUICK_RENDER_GUIDE.md

✅ **Documentation**
- Updated ARCHITECTURE.md with PROVIDED/REQUIRED
- Component specifications and descriptions
- Design rationale and decisions
- ANWAR_COMPLETE_PORTFOLIO.md

---

## Abdala (30%) - Requirements & Documentation

### Tasks Completed:
✅ **Requirements Specification (Primary Work)**
- Complete REQUIREMENTS.md document (comprehensive)
- Functional requirements (FR1-FR5) - all defined in detail
- Non-functional requirements (NFR1-NFR5) - all documented
- User stories and acceptance criteria for each requirement
- In-scope and out-of-scope items clearly defined
- Risk assessment and mitigation strategies
- Requirements alignment with project goals
- Assumptions and constraints documentation

✅ **GitHub Setup**
- Created and configured GitHub repository
- Set up branch protection
- Added all team members with appropriate access
- Configured repository settings

✅ **Review & Quality Assurance**
- Reviewed all technical documentation
- Provided feedback on architecture design
- Ensured consistency across all documents
- Validated that requirements align with implementation

✅ **Documentation Support**
- Helped with ARCHITECTURE.md review
- Contributed to team coordination docs
- Ensured documentation quality standards

**No Java code implementation - focused entirely on requirements engineering**


---

## Meshari (15%) - Testing & Integration

### Tasks Completed:
✅ **Code Verification & Testing**
- Verified ALL 15 Java files compile in IntelliJ
- Tested Main.java execution multiple times
- Verified output is correct
- Checked all PROVIDED/REQUIRED annotations are present
- Ensured JavaDoc comments are complete
- Integration testing of abstract implementations

✅ **Quality Assurance**
- Code quality verification
- Ensured naming conventions are followed
- Checked for compiler warnings
- Verified code follows Java standards
- Made sure all imports are correct

✅ **Support & Collaboration**
- Assisted Anwar with testing during development
- Provided feedback on code quality
- Helped identify any issues early
- Supported integration testing

**No code implementation - focused entirely on testing and quality assurance**

### Deliverables: Testing and quality verification

---

## Omran (15%) - Project Management & Documentation

### Tasks Completed:
✅ **Project Management Documents**
- CODE_OF_CONDUCT.md
- TASK_ALLOCATION.md
- MEETING_MINUTES.md
- CODE_REVIEW_PREP.md
- SUBMISSION_CHECKLIST.md
- QUICK_START.md

✅ **Team Coordination**
- Organized team meetings
- Tracked task progress
- Coordinated deadlines
- Managed communication

✅ **GitHub Management**
- Branch structure documentation
- Project board setup
- Issue tracking



---

**CRITICAL POINT: Anwar wrote 100% of the Java code (all 15 files). The other team members contributed requirements, testing, and project management - NOT code implementation.**



---

## Sprint 1 Status: ✅ COMPLETE

### All Deliverables Met:
✅ Requirements documented (Abdalla)  
✅ Architecture designed (Anwar)  
✅ **ALL Java code implemented by Anwar (100% of coding)**  
✅ All diagrams created (Anwar)  
✅ Testing completed (Meshari)  
✅ Project management docs (Omran)  
✅ GitHub repository ready  
✅ Ready for code review (20.2.26)

---

## Key Achievements

1. **Complete Code Implementation by Anwar** - ALL 15 Java files written from scratch
2. **Component Architecture** - 9 components across 3 layers
3. **PROVIDED/REQUIRED Pattern** - Clear architectural contracts in every file
4. **Working Code** - All files compile and Main.java runs successfully
5. **Multiple Diagram Views** - High-level, detailed, and PROVIDED/REQUIRED (all by Anwar)
6. **Comprehensive Documentation** - Requirements (Abdalla), architecture (Anwar), management (Omran)
7. **Team Collaboration** - Fair task distribution based on skills and complexity

---

## Files Created by Team

| Category | Count | Author |
|----------|-------|--------|
| **Java Source Files** | **15** | **Anwar (100%)** |
| **Diagram Files** | **8** | **Anwar (100%)** |
| **Requirements Docs** | **1** | **Abdalla** |
| **Project Mgmt Docs** | **6** | **Omran** |
| **Architecture Docs** | **1** | **Anwar** |
| **Total** | **31+** | **Team** |

**Code Implementation: 100% Anwar**  
**Requirements: 100% Abdala**  
**Testing: 100% Meshari**  
**Project Management: 100% Omran**

---

**Status**: Sprint 1 Complete ✅  
**Code Review**: 20.2.26 (Ready)  
**Last Updated**: 17.2.26
