# Code Review Preparation - Sprint 1
**Date**: 20.2.26  
**Duration**: 15 minutes maximum  
**Team**: Anwar, Abdala, Meshari, Omran

---

## Pre-Review Checklist

### 24 Hours Before Review
- [ ] All code pushed to GitHub
- [ ] All documentation finalized
- [ ] Project compiles without errors
- [ ] IntelliJ project setup tested
- [ ] Presentation rehearsed

### Morning of Review
- [ ] Laptop fully charged
- [ ] GitHub logged in and ready
- [ ] IntelliJ open with project loaded
- [ ] Project management tool open
- [ ] Documents ready for reference
- [ ] Team assembled 5 minutes early

---

## Review Structure (15 minutes total)

### 1. Introduction (1 minute)
**Presenter**: [Designated team member]

"Good morning/afternoon. We are Team [name], consisting of Anwar, Abdala, Meshari, and Omran. We're presenting our Sprint 1 deliverables for the Stock-Compare application."

### 2. Requirements Overview (2 minutes)
**Presenter**: Anwar

**Key Points to Cover**:
- Project scope (in scope vs out of scope)
- 5 main functional requirements
- 5 non-functional requirements
- User stories

**Demo**:
- Open docs/REQUIREMENTS.md
- Highlight key sections
- Show requirements alignment table

### 3. Architectural Design (4 minutes)
**Presenter**: Abdala

**Key Points to Cover**:
- Simple Architecture principles applied
- Three-layer architecture (Presentation, Business Logic, Data Access)
- 9 components identified
- Interface allocation strategy

**Demo**:
- Open docs/ARCHITECTURE.md
- Show architecture diagram
- Explain component responsibilities
- Discuss design decisions

### 4. Implementation Walkthrough (5 minutes)
**Presenters**: Meshari & Omran

**Show in order**:

a) **Domain Models** (1 min)
   - StockData.java
   - DateRange.java
   - Show business rules (2-year max)

b) **Service Layer** (1.5 min)
   - IStockDataManager interface
   - AbstractStockDataManager implementation
   - Show template method pattern

c) **Data Layer** (1.5 min)
   - IRepository interface
   - IAPIService interface
   - ICacheManager interface

d) **Presentation Layer** (1 min)
   - IChartDisplay interface
   - IInputHandler interface

### 5. Team Management (2 minutes)
**Presenter**: Omran

**Key Points to Cover**:
- GitHub repository structure
- Branch strategy (main, develop, feature branches)
- Project management tool setup
- Code of Conduct highlights
- Task allocation fairness

**Demo**:
- Show GitHub repository
- Show project board with tasks
- Show branch structure
- Show commit history

### 6. Q&A (1 minute)
**All team members participate**

Be ready to answer:
- Why did you choose this architecture?
- How do components interact?
- What design patterns did you use?
- How did you divide the work?

---

## What to Have Open

### Screen 1 (Main Display)
- IntelliJ with project loaded
- Code ready to show
- Project compiles successfully

### Screen 2 (Reference)
- GitHub repository
- Project management tool
- Documentation (docs folder)

---

## Common Questions & Answers

### Q: Why did you choose three-layer architecture?
**A**: It provides clear separation of concerns. UI logic is separate from business logic, which is separate from data access. This makes the system easier to test, maintain, and extend.

### Q: How do your components communicate?
**A**: Through well-defined interfaces. For example, StockDataManager uses IRepository and IAPIService interfaces. This allows us to swap implementations without affecting other components.

### Q: What design patterns did you use?
**A**: Template Method Pattern in AbstractStockDataManager, Repository Pattern for data access, and Interface Segregation Principle throughout.

### Q: How did you ensure fair work distribution?
**A**: We divided tasks based on the four main deliverable areas: requirements (Anwar), architecture (Abdala), implementation models (Meshari), and project management (Omran). Each person has roughly 25% of the work.

### Q: Does your code compile and run?
**A**: Yes, all code compiles without errors in IntelliJ. We have abstract implementations that demonstrate the architecture, though concrete implementations will come in Sprint 2.

### Q: What happens if the API is unavailable?
**A**: Our architecture supports this through the Repository pattern. The app will check local storage first and can work offline using cached data.

### Q: Why SQLite or JSON for storage?
**A**: Both are lightweight, embedded options that don't require a separate database server. SQLite offers better query capabilities, while JSON is simpler. We're keeping both options open for Sprint 2.

---

## Presentation Tips

### DO:
✅ Speak clearly and confidently  
✅ Make eye contact with reviewers  
✅ Navigate smoothly between screens  
✅ Stay within time limits  
✅ Share speaking time evenly  
✅ Support each other if someone forgets something  

### DON'T:
❌ Apologize excessively  
❌ Read directly from documents  
❌ Get defensive about questions  
❌ Go over time  
❌ Let one person dominate  
❌ Panic if something doesn't work  

---

## Technical Demonstration Plan

### Code to Show (in order):

1. **StockData.java**
   - Point out: Constructor, getters/setters, toString()
   - Highlight: Clean code, proper encapsulation

2. **DateRange.java**
   - Point out: Validation logic, 2-year business rule
   - Highlight: Domain rules enforced at model level

3. **IStockDataManager.java**
   - Point out: Clear interface contract
   - Highlight: JavaDoc documentation

4. **AbstractStockDataManager.java**
   - Point out: Template method pattern
   - Highlight: Dependency injection in constructor

5. **Architecture diagram**
   - Point out: Three layers, nine components
   - Highlight: Clear interfaces between layers

---

## Backup Plan

If technical issues occur:
1. Have screenshots of key code sections
2. Have PDF printout of architecture diagram
3. Can demo from another team member's laptop
4. Pre-record a backup video (optional)

---

## Post-Review

### Immediately After:
- [ ] Note feedback received
- [ ] Thank reviewers
- [ ] Discuss as team what went well/what to improve

### Within 24 Hours:
- [ ] Document feedback in GitHub issue
- [ ] Plan Sprint 2 based on feedback
- [ ] Update documentation if needed

---

## Rehearsal Schedule

**Practice Session 1**: [Date/Time]  
**Practice Session 2**: [Date/Time]  
**Final Run-through**: Morning of review (30 mins before)

During rehearsals:
- Time each section
- Practice transitions
- Give each other feedback
- Fix any technical issues

---

## Success Criteria

✅ Completed within 15 minutes  
✅ All deliverables demonstrated  
✅ Questions answered confidently  
✅ Code compiles and displays properly  
✅ Team coordination is smooth  
✅ Professionalism maintained  

---

## Emergency Contacts

**If running late**: [Contact method]  
**Technical issues**: [Backup person/method]  
**Team member absence**: [Contingency plan]

---

*Last Updated: [Date]*  
*Presenter Assignments Confirmed: [ ] Yes [ ] No*
