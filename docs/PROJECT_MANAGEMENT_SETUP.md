# Project Management Tool Setup Guide
## Share Price Comparison Application

---

## Overview

This document provides step-by-step instructions for setting up and using a project management tool for the Share Price Comparison Application coursework. We recommend using one of the following tools:

1. **GitHub Projects** (Recommended - Free, integrated with GitHub)
2. **Trello** (Free, visual Kanban boards)
3. **Jira** (Free for students, professional tool)
4. **Azure DevOps** (Free for small teams)

---

## Option 1: GitHub Projects (Recommended)

### Why GitHub Projects?
- ✅ Integrated with GitHub repository
- ✅ Automatically linked to issues and pull requests
- ✅ Free for all team members
- ✅ No additional account needed
- ✅ Track commits directly on cards

### Setup Instructions

#### Step 1: Create a New Project

1. Navigate to your GitHub repository
2. Click on the **"Projects"** tab
3. Click **"New Project"**
4. Choose template: **"Board"** or **"Table"**
5. Project name: **"Share Price App - Sprint 1"**
6. Description: **"Track tasks and progress for Sprint 1"**
7. Click **"Create project"**

#### Step 2: Configure Columns

Create the following columns (Kanban style):

| Column | Purpose | Automation |
|--------|---------|------------|
| 📋 **Backlog** | Tasks not yet prioritized | Items start here |
| 🎯 **To Do** | Ready to work on | Sprint planning → move here |
| 🏗️ **In Progress** | Currently being worked on | When work starts |
| 👀 **In Review** | Awaiting code review | When PR is created |
| ✅ **Done** | Completed | When PR is merged |

To add columns:
- Click **"+ Add column"** button
- Name the column
- Set automation rules (optional)

#### Step 3: Add Sprint 1 Tasks

For each task in [SPRINT1_TASK_ALLOCATION.md](SPRINT1_TASK_ALLOCATION.md):

1. Click **"+ Add item"** in Backlog
2. Create issue with format:
   ```
   Title: [TASK-ID] Task Description
   Example: [REQ-01] Document functional requirements
   ```
3. In issue description, add:
   ```markdown
   ## Description
   [Brief description of the task]
   
   ## Acceptance Criteria
   - [ ] Criterion 1
   - [ ] Criterion 2
   
   ## Estimated Hours
   X hours
   
   ## Assigned To
   @username
   
   ## Due Date
   YYYY-MM-DD
   
   ## Sprint
   Sprint 1
   ```
4. Add labels: `sprint-1`, `requirements`, `high-priority`, etc.
5. Assign to team member
6. Set milestone: "Sprint 1"

#### Step 4: Create Labels

Navigate to **Issues** → **Labels** → **New label**

Create these labels with appropriate colors:

**Sprint Labels:**
- `sprint-1` (Blue)
- `sprint-2` (Blue)
- `sprint-3` (Blue)

**Category Labels:**
- `requirements` (Purple)
- `architecture` (Orange)
- `implementation` (Green)
- `team-mgmt` (Pink)
- `documentation` (Gray)
- `testing` (Yellow)

**Priority Labels:**
- `high-priority` (Red)
- `medium-priority` (Orange)
- `low-priority` (Green)

**Type Labels:**
- `feature` (Green)
- `bug` (Red)
- `enhancement` (Blue)

#### Step 5: Create Milestones

Navigate to **Issues** → **Milestones** → **New milestone**

Create milestones for each sprint:

**Sprint 1 Milestone:**
- Title: Sprint 1 - Architectural Principles
- Due date: February 19, 2026
- Description: Introduction to Architectural Principles

#### Step 6: Set Up Automations

In your project board:
- Click **"..."** → **"Workflows"**
- Enable these automations:
  - ✅ Auto-move to "In Progress" when issue is assigned
  - ✅ Auto-move to "In Review" when PR is created
  - ✅ Auto-move to "Done" when PR is merged/closed

#### Step 7: Daily Use

**For Team Members:**
1. Check project board daily
2. Update task status by dragging cards
3. Add comments to issues for updates
4. Link commits to issues using `Resolves #issue-number` in commit messages
5. Request PR reviews through GitHub

**For Project Lead:**
1. Monitor overall progress
2. Identify blockers
3. Reassign tasks if needed
4. Run sprint retrospectives

---

## Option 2: Trello

### Setup Instructions

#### Step 1: Create Workspace

1. Go to [trello.com](https://trello.com)
2. Sign up/Login
3. Create new Workspace: **"Share Price App"**
4. Invite team members via email

#### Step 2: Create Board

1. Click **"Create new board"**
2. Board name: **"Sprint 1 - Share Price App"**
3. Visibility: **Private**
4. Background: Choose a color/image

#### Step 3: Create Lists

Create these lists (columns):
- 📋 Backlog
- 🎯 To Do (Sprint 1)
- 🏗️ In Progress
- 👀 Code Review
- ✅ Done

#### Step 4: Create Cards

For each task:
1. Click **"Add a card"** in appropriate list
2. Card title: **[TASK-ID] Task Name**
3. Click card to open details:
   - **Description**: Full task description
   - **Members**: Assign team member
   - **Labels**: Add category label
   - **Due Date**: Set deadline
   - **Checklist**: Add acceptance criteria
   - **Attachments**: Link related documents

#### Step 5: Create Labels

Click **"Show Menu"** → **"Labels"** → **"Create a new label"**

Same labels as GitHub Projects section

#### Step 6: Enable Power-Ups

Useful Trello Power-Ups:
- **GitHub**: Link GitHub repos and PRs
- **Calendar**: View deadlines
- **Card Aging**: Highlight stale cards
- **Custom Fields**: Add estimated hours

#### Step 7: Daily Use

- Drag cards between lists as status changes
- Add comments for updates
- Use @mentions to notify team members
- Check "Activity" feed for updates

---

## Option 3: Jira

### Setup Instructions

#### Step 1: Create Jira Account

1. Go to [atlassian.com/software/jira/free](https://www.atlassian.com/software/jira/free)
2. Sign up for free (student accounts available)
3. Create site: **sharepriceapp.atlassian.net**

#### Step 2: Create Project

1. Click **"Create Project"**
2. Template: **Scrum** or **Kanban**
3. Project name: **Share Price Comparison App**
4. Project key: **SPC**
5. Click **"Create"**

#### Step 3: Configure Board

Navigate to **Board** → **Board Settings**:

**Columns:**
- Backlog
- To Do
- In Progress
- Code Review
- Done

#### Step 4: Create Epics

Epics group related tasks:
- Epic 1: Requirements & Documentation
- Epic 2: Architectural Design
- Epic 3: Implementation
- Epic 4: Team Management

#### Step 5: Create Stories/Tasks

1. Click **"Create"**
2. Issue Type: **Story** or **Task**
3. Fill in:
   - Summary: Task name
   - Epic Link: Link to relevant epic
   - Assignee: Team member
   - Story Points: Estimated effort
   - Sprint: Sprint 1
   - Priority: High/Medium/Low
   - Description: Full task details
4. Click **"Create"**

#### Step 6: Start Sprint

1. Click **"Create Sprint"**
2. Sprint name: **Sprint 1**
3. Duration: 3 weeks
4. Start date: Jan 30, 2026
5. End date: Feb 19, 2026
6. Drag tasks from Backlog to Sprint
7. Click **"Start Sprint"**

#### Step 7: Daily Use

- View **Scrum Board** or **Kanban Board**
- Log time spent on tasks
- Update task status
- Add comments
- Generate reports (Burndown Chart, Velocity Chart)

---

## Comparison Table

| Feature | GitHub Projects | Trello | Jira |
|---------|----------------|--------|------|
| **Cost** | Free | Free (limited) | Free (10 users) |
| **GitHub Integration** | Native | Power-Up | Integration |
| **Learning Curve** | Easy | Easy | Moderate |
| **Features** | Basic | Medium | Advanced |
| **Best For** | Small teams | Visual thinkers | Enterprise teams |
| **Reporting** | Basic | Limited | Comprehensive |

---

## Recommended Workflow

### Sprint Planning (Week 1)

1. **Team Lead** creates all tasks as issues/cards
2. **Team** estimates effort for each task
3. **Team** assigns tasks to members
4. **Team** moves tasks to "To Do" column
5. **All** agree on sprint goals

### Daily Standup (Every Day)

1. Each member updates their task status
2. Move cards to current column
3. Add comments on blockers
4. Team lead monitors progress

### During Development

1. **When starting work**: Move card to "In Progress"
2. **When creating PR**: Move card to "In Review", link PR
3. **When PR approved**: Move card to "Done"
4. **Log time**: Update time tracking field

### Sprint Review (Week 3)

1. Review completed tasks
2. Demo completed features
3. Identify incomplete tasks
4. Generate sprint report

### Sprint Retrospective (After Review)

1. What went well?
2. What could improve?
3. Action items for next sprint
4. Update process if needed

---

## Task Card Template

```markdown
## [TASK-ID] Task Title

### Description
[Detailed description of what needs to be done]

### Acceptance Criteria
- [ ] Criterion 1
- [ ] Criterion 2
- [ ] Criterion 3

### Estimated Hours
X hours

### Assigned To
@team-member-name

### Dependencies
- Depends on: [OTHER-TASK-ID]
- Blocks: [ANOTHER-TASK-ID]

### Sprint
Sprint 1

### Category
Requirements / Architecture / Implementation / Team Management

### Priority
High / Medium / Low

### Due Date
YYYY-MM-DD

### Related Documents
- [Link to requirements](docs/REQUIREMENTS.md)
- [Link to architecture](docs/ARCHITECTURAL_DESIGN.md)

### Notes
[Any additional notes or considerations]
```

---

## Integration with GitHub

### Link Issues to Commits

In commit messages, use keywords:
```bash
git commit -m "feat: implement StockPrice model

Resolves #12
Refs #15"
```

Keywords that close issues:
- `Closes #issue`
- `Fixes #issue`
- `Resolves #issue`

### Link Issues to Pull Requests

In PR description:
```markdown
## Related Issues
Closes #12
Refs #15, #16
```

---

## Best Practices

### Task Breakdown
- ✅ Keep tasks small (4-8 hours max)
- ✅ Make tasks specific and actionable
- ✅ Define clear acceptance criteria
- ✅ Estimate effort realistically

### Status Updates
- ✅ Update status at least daily
- ✅ Add comments for progress/blockers
- ✅ Link related commits/PRs
- ✅ Log time spent

### Communication
- ✅ @mention team members for attention
- ✅ Use comments for questions/discussions
- ✅ Keep discussions on relevant tasks
- ✅ Document decisions in task comments

### Organization
- ✅ Use labels consistently
- ✅ Keep board clean (archive done tasks)
- ✅ Review board in team meetings
- ✅ Adjust workflow as needed

---

## Sprint 1 Initial Setup Checklist

- [ ] Choose project management tool
- [ ] Create account and workspace/project
- [ ] Invite all team members
- [ ] Configure board with columns
- [ ] Create labels
- [ ] Create Sprint 1 milestone
- [ ] Add all tasks from SPRINT1_TASK_ALLOCATION.md
- [ ] Assign tasks to team members
- [ ] Set due dates
- [ ] Configure automation rules
- [ ] Integrate with GitHub (if applicable)
- [ ] Conduct sprint planning meeting
- [ ] Document tool access details for team

---

## Support Resources

**GitHub Projects:**
- [Official Docs](https://docs.github.com/en/issues/planning-and-tracking-with-projects)
- [Video Tutorial](https://www.youtube.com/results?search_query=github+projects+tutorial)

**Trello:**
- [Getting Started Guide](https://trello.com/guide)
- [Video Tutorials](https://www.youtube.com/c/trello)

**Jira:**
- [Jira Software Docs](https://www.atlassian.com/software/jira/guides)
- [Scrum Tutorial](https://www.atlassian.com/agile/tutorials/how-to-do-scrum-with-jira-software)

---

**Last Updated**: February 6, 2026  
**Maintained By**: Testing Lead  
**Questions**: Contact team lead or post in team chat
