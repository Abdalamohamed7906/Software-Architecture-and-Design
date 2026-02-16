# Quick Start Guide - Stock-Compare

This guide will help you get started with the Stock-Compare project.

---

## Prerequisites

Before you begin, ensure you have:
- ✅ Java JDK 11 or higher installed
- ✅ IntelliJ IDEA (Community or Ultimate edition)
- ✅ Git installed on your machine
- ✅ GitHub account with access to the repository

---

## Initial Setup

### 1. Clone the Repository

```bash
# Navigate to your workspace directory
cd ~/workspace

# Clone the repository
git clone https://github.com/[your-org]/stock-compare.git

# Navigate into the project
cd stock-compare
```

### 2. Open in IntelliJ IDEA

1. Launch IntelliJ IDEA
2. Click **Open**
3. Navigate to the `stock-compare` folder
4. Click **OK**
5. Wait for IntelliJ to index the project

### 3. Configure Project SDK

1. Go to **File → Project Structure** (Ctrl+Alt+Shift+S on Windows/Linux)
2. Under **Project**, select **Project SDK**
3. Choose Java 11 or higher
4. Click **Apply** and **OK**

---

## Working with Git

### Branch Strategy

```
main (protected - no direct commits)
  └── develop (integration branch)
       ├── feature/your-feature-name
       └── feature/another-feature
```

### Creating a Feature Branch

```bash
# Make sure you're on develop branch
git checkout develop

# Pull latest changes
git pull origin develop

# Create your feature branch
git checkout -b feature/your-feature-name

# Example:
# git checkout -b feature/stock-data-model
```

### Making Changes

```bash
# Check status of your changes
git status

# Add files to staging
git add .

# Or add specific files
git add src/main/java/com/stockcompare/domain/StockData.java

# Commit with meaningful message
git commit -m "feat: add StockData domain model with validation"

# Push to GitHub
git push origin feature/your-feature-name
```

### Commit Message Format

Use conventional commit messages:
- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation changes
- `refactor:` - Code refactoring
- `test:` - Adding tests
- `chore:` - Maintenance tasks

Examples:
```
feat: add date range validation
fix: correct stock symbol validation regex
docs: update architecture diagram
refactor: simplify price calculation logic
test: add unit tests for DateRange
```

### Creating a Pull Request

1. Push your feature branch to GitHub
2. Go to the repository on GitHub
3. Click **Pull Request**
4. Select **base: develop** ← **compare: feature/your-branch**
5. Fill in description:
   - What changes were made
   - Why they were made
   - Any testing done
6. Request review from team member
7. Wait for approval before merging

---

## Project Structure

```
stock-compare/
├── docs/                          # Documentation
│   ├── REQUIREMENTS.md            # Requirements specification
│   ├── ARCHITECTURE.md            # Architecture design
│   ├── TASK_ALLOCATION.md         # Task assignments
│   ├── MEETING_MINUTES.md         # Meeting logs
│   └── CODE_REVIEW_PREP.md        # Review preparation
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── stockcompare/
│   │               ├── domain/         # Domain models (StockData, DateRange)
│   │               ├── service/        # Business logic interfaces & implementations
│   │               ├── data/           # Data access interfaces
│   │               └── presentation/   # UI interfaces
│   │
│   └── test/                      # Unit tests (to be added)
│       └── java/
│
├── .gitignore                     # Git ignore rules
├── CODE_OF_CONDUCT.md             # Team conduct guidelines
└── README.md                      # Project overview
```

---

## Building the Project

### In IntelliJ

1. Click **Build → Build Project** (Ctrl+F9)
2. Check the **Build** output panel for any errors
3. Fix any compilation errors

### Command Line (if Maven/Gradle configured)

```bash
# Maven
mvn clean compile

# Gradle
gradle build
```

---

## Running Tests (Sprint 2+)

```bash
# In IntelliJ: Right-click on test file → Run
# Or use keyboard shortcut: Ctrl+Shift+F10

# Maven
mvn test

# Gradle
gradle test
```

---

## Common Tasks

### Adding a New Java Class

1. Right-click on the package (e.g., `com.stockcompare.domain`)
2. Select **New → Java Class**
3. Enter class name (e.g., `ComparisonResult`)
4. Add package declaration and imports
5. Write your code
6. Add JavaDoc comments

### Checking Code Quality

Before committing:
- ✅ Code compiles without errors
- ✅ No unused imports
- ✅ Proper indentation (4 spaces)
- ✅ JavaDoc comments for public methods
- ✅ Meaningful variable names
- ✅ Follows team coding standards

### Syncing with Team Changes

```bash
# Switch to develop branch
git checkout develop

# Pull latest changes
git pull origin develop

# Switch back to your feature branch
git checkout feature/your-feature

# Merge develop into your branch
git merge develop

# Resolve any conflicts if they occur
# Then commit the merge
```

---

## Troubleshooting

### "Cannot resolve symbol" errors

**Solution**: 
1. File → Invalidate Caches / Restart
2. Click **Invalidate and Restart**

### Git push rejected

**Solution**:
```bash
# Pull latest changes first
git pull origin your-branch-name

# Resolve conflicts if any
# Then push again
git push origin your-branch-name
```

### Merge conflicts

**Solution**:
1. Open conflicted file in IntelliJ
2. IntelliJ will show conflict markers
3. Choose which version to keep or manually merge
4. Mark as resolved
5. Commit the resolution

### Build fails

**Solution**:
1. Check **Build** panel for specific error
2. Verify all imports are correct
3. Ensure Java version is correct
4. Try **Build → Rebuild Project**
5. Ask team for help if stuck

---

## Team Communication

### Daily Updates
Post in team chat:
- What you worked on today
- What you'll work on tomorrow
- Any blockers

### Asking for Help
1. Check documentation first
2. Ask in team chat
3. Tag relevant person
4. Provide context and error messages

### Code Reviews
- Be constructive and respectful
- Provide specific feedback
- Appreciate good code
- Learn from others' code

---

## Best Practices

### DO:
✅ Commit often with clear messages  
✅ Pull before pushing  
✅ Review your own code before committing  
✅ Write JavaDoc for public methods  
✅ Keep methods short and focused  
✅ Test your changes  
✅ Ask questions when unclear  

### DON'T:
❌ Commit directly to main or develop  
❌ Push code that doesn't compile  
❌ Commit secrets or passwords  
❌ Make huge commits (break into smaller ones)  
❌ Ignore compiler warnings  
❌ Copy-paste code without understanding it  

---

## Getting Help

- **Git issues**: Ask Omran or check [Git documentation](https://git-scm.com/doc)
- **Architecture questions**: Ask Abdala or review ARCHITECTURE.md
- **Requirements clarification**: Ask Anwar or review REQUIREMENTS.md
- **Java help**: Ask Meshari or check [Java docs](https://docs.oracle.com/en/java/)
- **General**: Post in team chat

---

## Next Steps

After setup:
1. ✅ Review CODE_OF_CONDUCT.md
2. ✅ Review your assigned tasks in TASK_ALLOCATION.md
3. ✅ Read relevant documentation (REQUIREMENTS.md, ARCHITECTURE.md)
4. ✅ Create your feature branch
5. ✅ Start working on your assigned component
6. ✅ Commit regularly and push to GitHub

---

## Useful Links

- GitHub Repository: [URL]
- Project Board: [URL]
- Team Chat: [Platform/URL]
- Module Canvas: [URL]
- Java Documentation: https://docs.oracle.com/en/java/

---

*Last Updated: [Date]*  
*Maintained by: [Team member name]*
