# GitHub Setup and Branching Strategy
## Share Price Comparison Application

---

## 1. Repository Setup

### 1.1 Create Repository

1. **Navigate to GitHub** (github.com)
2. **Click "New Repository"**
   - Owner: Your organization/username
   - Repository name: `share-price-comparison-app`
   - Description: "Java-based share price comparison web application demonstrating software architecture principles"
   - Visibility: Private (for coursework)
   - Initialize with:
     - ✅ README
     - ✅ .gitignore (Java template)
     - ✅ License (MIT or Apache 2.0)

3. **Clone Repository Locally**
   ```bash
   git clone https://github.com/[your-org]/share-price-comparison-app.git
   cd share-price-comparison-app
   ```

---

## 2. Branch Strategy

We follow a **Git Flow** branching model suitable for sprint-based development.

### 2.1 Permanent Branches

#### **main** (Production Branch)
- Contains production-ready code
- Only updated at end of each sprint
- Protected branch - no direct commits
- Requires pull request approval

#### **develop** (Integration Branch)
- Integration branch for features
- Contains latest development changes
- Protected branch - no direct commits
- Base for all feature branches

### 2.2 Temporary Branches

#### **Feature Branches** (`feature/*`)
- Created for each new feature or task
- Branched from: `develop`
- Merged to: `develop`
- Naming: `feature/<task-id>-<short-description>`
- Examples:
  - `feature/IMPL-01-domain-model`
  - `feature/ARCH-03-component-diagram`
  - `feature/REQ-01-functional-requirements`

#### **Bugfix Branches** (`bugfix/*`)
- Created for bug fixes
- Branched from: `develop`
- Merged to: `develop`
- Naming: `bugfix/<issue-id>-<short-description>`
- Examples:
  - `bugfix/ISS-123-date-validation`
  - `bugfix/null-pointer-repository`

#### **Hotfix Branches** (`hotfix/*`)
- For urgent production fixes
- Branched from: `main`
- Merged to: `main` and `develop`
- Naming: `hotfix/<version>-<description>`
- Example: `hotfix/1.0.1-critical-bug`

#### **Release Branches** (`release/*`)
- Preparation for sprint release
- Branched from: `develop`
- Merged to: `main` and `develop`
- Naming: `release/sprint-<number>`
- Examples:
  - `release/sprint-1`
  - `release/sprint-2`

---

## 3. Branch Setup Commands

### 3.1 Initial Setup

```bash
# Ensure you're on main
git checkout main

# Create develop branch
git checkout -b develop
git push -u origin develop

# Set develop as default branch on GitHub (Settings -> Branches)
```

### 3.2 Creating Feature Branches

```bash
# Update develop branch
git checkout develop
git pull origin develop

# Create feature branch
git checkout -b feature/IMPL-01-domain-model

# Work on your feature...
# Commit changes
git add .
git commit -m "feat(model): implement StockPrice domain entity

- Add StockPrice class with OHLCV fields
- Implement validation logic
- Add JavaDoc documentation

Resolves: IMPL-01"

# Push to remote
git push -u origin feature/IMPL-01-domain-model
```

### 3.3 Merging Features

```bash
# Update your feature branch with latest develop
git checkout feature/IMPL-01-domain-model
git pull origin develop
git rebase develop  # or merge develop

# Push updates
git push origin feature/IMPL-01-domain-model --force-with-lease

# Create Pull Request on GitHub
# After approval, squash and merge to develop
```

---

## 4. Branch Protection Rules

### 4.1 Protect `main` Branch

**GitHub Settings → Branches → Add Rule**

Branch name pattern: `main`

Enable:
- ✅ Require pull request before merging
- ✅ Require approvals: 2
- ✅ Dismiss stale pull request approvals when new commits are pushed
- ✅ Require review from Code Owners
- ✅ Require status checks to pass before merging
  - Maven build
  - Unit tests
- ✅ Require branches to be up to date before merging
- ✅ Include administrators
- ✅ Restrict who can push to matching branches
- ✅ Allow force pushes: Off
- ✅ Allow deletions: Off

### 4.2 Protect `develop` Branch

Branch name pattern: `develop`

Enable:
- ✅ Require pull request before merging
- ✅ Require approvals: 1
- ✅ Require status checks to pass before merging
- ✅ Require branches to be up to date before merging
- ✅ Allow force pushes: Off

---

## 5. Commit Message Conventions

We follow **Conventional Commits** specification:

### 5.1 Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### 5.2 Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code formatting (no logic change)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Build process, dependencies, etc.

### 5.3 Examples

```bash
# Feature
git commit -m "feat(service): implement stock price caching logic

- Add cache validation in service layer
- Integrate with repository layer
- Add logging for cache hits/misses

Resolves: IMPL-03"

# Bug fix
git commit -m "fix(model): correct date range validation

DateRange was allowing future dates, now properly validates
that end date is not after today.

Fixes: #42"

# Documentation
git commit -m "docs(readme): add build instructions

Added step-by-step Maven build instructions with prerequisites"

# Refactoring
git commit -m "refactor(repository): extract query logic to helper methods

Improved readability and testability by extracting complex
queries into separate helper methods."
```

---

## 6. Pull Request Process

### 6.1 Creating a Pull Request

1. **Push your feature branch** to remote
2. **Navigate to GitHub repository**
3. **Click "Pull Requests" → "New Pull Request"**
4. **Select branches:**
   - Base: `develop`
   - Compare: `feature/your-branch`
5. **Fill in PR template:**

```markdown
## Description
Brief description of changes

## Related Task
- Task ID: IMPL-01
- Task Description: Implement domain model

## Type of Change
- [x] Feature
- [ ] Bug fix
- [ ] Documentation
- [ ] Refactoring

## Changes Made
- Implemented StockPrice class
- Added DateRange value object
- Added unit tests
- Added JavaDoc comments

## Testing
- [x] Code compiles without errors
- [x] Unit tests pass
- [x] Manual testing performed

## Screenshots (if applicable)
N/A

## Checklist
- [x] Code follows team coding standards
- [x] JavaDoc comments added
- [x] Unit tests added
- [x] No merge conflicts
- [x] Branch is up to date with develop

## Reviewers
@member1 @member2
```

6. **Request reviews** from at least 2 team members
7. **Address review comments**
8. **Merge after approval**

### 6.2 Reviewing Pull Requests

**Responsibilities:**
- Review within 48 hours
- Test the code locally if possible
- Provide constructive feedback
- Approve if code meets standards

**Review Checklist:**
- ✅ Code compiles and runs
- ✅ Follows coding standards
- ✅ Adequate test coverage
- ✅ JavaDoc comments present
- ✅ No obvious bugs
- ✅ Meets requirements
- ✅ Clear and maintainable

---

## 7. Project Structure in Git

```
share-price-comparison-app/
├── .github/
│   ├── workflows/           # GitHub Actions CI/CD
│   │   └── maven.yml
│   ├── PULL_REQUEST_TEMPLATE.md
│   └── ISSUE_TEMPLATE/
├── docs/
│   ├── REQUIREMENTS.md
│   ├── ARCHITECTURAL_DESIGN.md
│   ├── CODE_OF_CONDUCT.md
│   ├── SPRINT1_TASK_ALLOCATION.md
│   └── GITHUB_SETUP.md
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/shareapp/
│   │   │       ├── model/
│   │   │       ├── service/
│   │   │       ├── repository/
│   │   │       ├── ui/
│   │   │       └── architecture/
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/shareapp/
├── diagrams/
│   ├── component-specification.png
│   ├── architecture-layers.png
│   └── data-flow.png
├── .gitignore
├── pom.xml                  # Maven build file
├── README.md
└── LICENSE
```

---

## 8. .gitignore Configuration

```gitignore
# Compiled class files
*.class
target/

# Log files
*.log

# Package Files
*.jar
*.war
*.nar
*.ear
*.zip
*.tar.gz
*.rar

# IDE files
.idea/
*.iml
.vscode/
.classpath
.project
.settings/

# OS files
.DS_Store
Thumbs.db

# Temporary files
*.tmp
*.bak
*.swp
*~

# Build files
build/
dist/
out/

# Database files
*.db
*.sqlite
*.db-journal

# Configuration files (may contain sensitive data)
application-local.properties
```

---

## 9. GitHub Project Board Setup

### 9.1 Create Project Board

1. Navigate to **Projects** tab
2. Click **New Project**
3. Choose **Board** template
4. Name: "Share Price App - Sprint 1"

### 9.2 Column Structure

| Column | Purpose | Cards Move Here When... |
|--------|---------|-------------------------|
| 📋 Backlog | Tasks not yet started | Created but not assigned |
| 🎯 To Do | Ready to start | Assigned to sprint |
| 🏗️ In Progress | Currently being worked on | Work has begun |
| 👀 In Review | Awaiting code review | PR created |
| ✅ Done | Completed | PR merged or task completed |

### 9.3 Labels

Create labels for organization:
- `sprint-1`, `sprint-2`, `sprint-3`
- `requirements`, `architecture`, `implementation`, `team-mgmt`
- `high-priority`, `medium-priority`, `low-priority`
- `bug`, `feature`, `documentation`, `testing`

---

## 10. Collaboration Workflow

### 10.1 Daily Workflow

```bash
# Morning routine
git checkout develop
git pull origin develop
git checkout <your-feature-branch>
git rebase develop  # Keep your branch up to date

# Work on your feature
# ... make changes ...

# Commit frequently
git add <changed-files>
git commit -m "feat(scope): description"

# End of day - push your work
git push origin <your-feature-branch>
```

### 10.2 Resolving Merge Conflicts

```bash
# When conflicts occur during rebase
git status  # See conflicted files

# Open conflicted files and resolve
# Look for <<<<<<, =======, >>>>>> markers

# After resolving
git add <resolved-files>
git rebase --continue

# Or abort if needed
git rebase --abort
```

---

## 11. Team Coordination

### 11.1 Before Starting Work
1. Check project board for available tasks
2. Assign yourself to a task
3. Move task to "In Progress"
4. Create feature branch
5. Update team in standup

### 11.2 While Working
- Commit frequently with clear messages
- Push at least once per day
- Update task status
- Communicate blockers immediately

### 11.3 When Complete
- Create pull request
- Move task to "In Review"
- Request reviews
- Respond to feedback
- Merge when approved
- Move task to "Done"

---

## 12. Sprint Release Process

### Sprint 1 Release (Example)

```bash
# Create release branch from develop
git checkout develop
git pull origin develop
git checkout -b release/sprint-1

# Final testing and bug fixes on release branch
# ... make any last-minute fixes ...

# Merge to main
git checkout main
git merge --no-ff release/sprint-1
git tag -a v1.0.0 -m "Sprint 1 Release"
git push origin main --tags

# Merge back to develop
git checkout develop
git merge --no-ff release/sprint-1
git push origin develop

# Delete release branch
git branch -d release/sprint-1
git push origin --delete release/sprint-1
```

---

## 13. Troubleshooting

### Common Issues

**Issue**: "Branch is behind origin/develop"  
**Solution**:
```bash
git checkout develop
git pull origin develop
git checkout your-feature-branch
git rebase develop
```

**Issue**: "Cannot push - rejected"  
**Solution**:
```bash
git pull --rebase origin your-feature-branch
git push origin your-feature-branch
```

**Issue**: "Merge conflict"  
**Solution**: See Section 10.2

---

## 14. Best Practices

✅ **DO:**
- Pull latest develop before creating feature branches
- Commit frequently with descriptive messages
- Keep feature branches small and focused
- Rebase feature branches with develop regularly
- Request code reviews promptly
- Respond to review feedback quickly
- Delete merged feature branches

❌ **DON'T:**
- Commit directly to main or develop
- Create huge pull requests (>500 lines)
- Leave pull requests unreviewed for days
- Force push to shared branches
- Commit large binary files
- Include sensitive data (passwords, API keys)

---

**Last Updated**: February 6, 2026  
**Maintained By**: Technical Lead  
**Questions**: Contact team lead or post in team chat
