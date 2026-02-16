# Quick Guide: Generate Diagram Images

## Method 1: Mermaid Live Editor (Easiest - 2 minutes)

1. Go to: **https://mermaid.live**

2. Delete the example code

3. Copy the entire content from:
   - `docs/diagrams/high-level-architecture.mermaid`
   
4. Paste into the editor

5. Click **Actions** → **PNG** or **SVG** 

6. Save as `high-level-architecture.png`

7. Repeat for `component-specification.mermaid`

8. Save as `component-specification.png`

**Done!** You now have 2 PNG images to include in your documentation.

---

## Method 2: GitHub (Automatic)

1. Push your code to GitHub

2. Create a file called `README.md` in `docs/diagrams/`

3. Add this content:
   ````markdown
   # Architecture Diagrams
   
   ## High-Level Architecture
   ```mermaid
   [paste content from high-level-architecture.mermaid]
   ```
   
   ## Component Specification
   ```mermaid
   [paste content from component-specification.mermaid]
   ```
   ````

4. GitHub will automatically render the diagrams!

5. Take screenshots of the rendered diagrams

---

## Method 3: VS Code (If you have it)

1. Install extension: **Markdown Preview Mermaid Support**

2. Create a markdown file: `diagrams.md`

3. Add this:
   ````markdown
   ## Diagram 1
   ```mermaid
   [paste content]
   ```
   
   ## Diagram 2  
   ```mermaid
   [paste content]
   ```
   ````

4. Right-click → **Markdown: Open Preview**

5. Take screenshot

---

## Method 4: PowerPoint/Draw.io (Manual but looks professional)

Use the templates in `TEXT_DIAGRAMS.md` and draw them manually.

**Advantage**: You can customize colors, add your team logo, make it unique!

---

## For Your Code Review

You need these 2 images:

1. ✅ **high-level-architecture.png** - Shows the 3 layers
2. ✅ **component-specification.png** - Shows all 9 components

**Where to use them:**
- Code review presentation slides
- ARCHITECTURE.md document  
- Final Sprint 1 report
- Printed handouts for reviewers

---

## Recommended Approach

**Best**: Use Method 1 (Mermaid Live Editor)
- Takes 2 minutes
- No installation needed
- High quality output
- Professional looking

**Alternative**: Method 4 (Draw manually)
- More personal
- Can customize
- Shows effort
- Good if you want to impress

---

*Generated diagrams should be saved in: docs/diagrams/images/*
