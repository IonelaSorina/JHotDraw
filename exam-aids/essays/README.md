# Essays — Full 15-20 Page Templates

Five complete essay drafts on the Group / Ungroup feature in JHotDraw. Each follows a slightly different focus so you can pick the one matching the exam question, or combine sections from several.

> **Use rule:** the exam question dictates the structure. These drafts are *templates* — adapt freely. Each one stands alone as a 15-20 page report.

---

## File map

| # | Essay | Focus | Best for an exam question about... |
|---|---|---|---|
| 01 | [Refactoring](essay-01-refactoring.md) | Lab 4 (Compose Method, Boy Scout cleanups) | refactoring, prefactoring, code smells, Fowler/Kerievsky catalogue |
| 02 | [SOLID + Clean Architecture](essay-02-solid-architecture.md) | Lab 5 (SRP/OCP audit) | OO principles, architecture, testability as architectural property |
| 03 | [Software Testing](essay-03-testing.md) | Lab 7 (24 JUnit tests + 6 assertions) | unit testing, assertions, mocks, TDD, the mockability tax |
| 04 | [BDD with JGiven](essay-04-bdd.md) | Lab 9 (4 JGiven scenarios + AssertJ) | BDD, living documentation, acceptance tests, user stories |
| 05 | [Phased Model — Integrative Essay](essay-05-phased-model.md) | All labs as one Rajlich-phased pass | the maintenance lifecycle, software change as a discipline |

**Recommendation:** read essay 05 first. It is the *integrative* one and gives the framework into which the others slot. Then read the essay matching your most likely exam topic.

---

## Evaluation of your colleague's proposed structure

> *"Proposal Structure for Maintenance Report — Abstract / Introduction / Initiation / Concept Location / Impact Analysis / Prefactoring / Actualization / Postfactoring / Verification / Conclusion / Discussion / References / Appendix."*

### What works in it

- **It maps directly onto Rajlich's phased model.** Every named section is a phase. This is the *right* spine for a software-maintenance report and shows the grader you understood the course's central framework.
- **It is concrete about deliverables.** The 2-column "domain class / responsibility" table in Concept Location, the package list in Impact Analysis, the before/after UML in Postfactoring — these are *specific* artefacts that make the report visibly substantive rather than pure prose.
- **It distinguishes Static and Dynamic Impact Analysis.** A subtle distinction (Lec 3) that many students miss. Including both shows depth.
- **It folds Clean Code (Chapter 2: names, Chapter 3: functions, Chapter 4: comments, Chapter 5: formatting) into the Prefactoring section.** Sensible — those are exactly the line-by-line moves done in prefactoring.
- **Discussion section ("what could have been better, what failed").** This is the *master's-level signal* — graders reward students who can critique their own work.

### What does not work in it / where to be careful

- **It is one big report template, not five essays.** The user (you) asked for five 15-20 page essays. Following this structure five times would produce five near-identical reports. Better: **use this structure as the spine of one essay, and use the other four essays to dig deeper into specific lectures.** That is the approach the five essay templates here take.

- **Actualization vs Lab 5.** The colleague's outline treats Actualization as "introduce SOLID and apply it" — but Actualization in Rajlich's model is the *actual code change* being made, not an architectural audit. Your Lab 5 was an audit (no real change was made because you didn't add a new feature). Two options:
  - **Option A:** present Lab 5 as a *prefactoring* exercise — the SOLID audit identified violations whose fixes would be *prefactoring* before some hypothetical future change. (More honest.)
  - **Option B:** keep the colleague's labelling but frame the SOLID audit as the *plan* for an Actualization that the report does not execute. (More aligned with the colleague.)
  - **My recommendation:** Option A. The grader knows the difference and the more honest framing scores higher.

- **Postfactoring is for cleaning up duplication introduced by the change.** The colleague proposes using it for "before/after SOLID UML comparison" — that's a *postmortem*, not Postfactoring. If you do not make a change (Actualization), you cannot have Postfactoring. Either skip this section or use it for *what postfactoring would look like* if the change were made.

- **"Verification" should contain more than BDD.** The colleague mentions BDD-test and JGiven only. Your Lab 7 added 24 *unit* tests + 6 production *assertions* — those belong in Verification too. Include the test pyramid (unit / integration / acceptance) and show all three layers.

- **No "Capstone Reflection" section.** The colleague's *Discussion* section is close, but it's specifically about *what could have been better*. A grader at master's level also wants to see what you *learned* across the whole arc. The portfolio's [Capstone Reflection](../../portfolio/portfolio.md#capstone-reflection--the-course-as-one-argument) section is a model for this.

- **No section on the *Conclusion* phase of Rajlich's model.** The colleague's "Conclusion" is the report's conclusion, not Rajlich's Conclusion phase (Commit / Baseline / Release). Lecture 10 dedicates a whole chapter to this. Worth including a paragraph on what the Conclusion phase *would* look like for your feature (GitHub Actions CI = baseline mechanism; the green build = the certified known-good; the merged PR to `develop` = the new release).

### Verdict

The colleague's structure is **good, but undersells the breadth** of the course. A 15-20 page report following it will hit ~7/10 marks. To push to ~9/10, augment it with:

1. The **test pyramid** in Verification (not just BDD).
2. The **Conclusion phase** of Rajlich's model (CI + baseline + release).
3. The **honest framing** that no full Actualization was performed — and a *Discussion* paragraph on why that is itself an interesting maintenance finding.
4. A **Capstone Reflection** paragraph — what the entire arc taught you, beyond any single phase.

The five essays in this folder do all of the above.

---

## How the five essays differ

Each essay has the *same skeleton* (so you can compare side-by-side) but emphasises a different concept:

```
Title page / Header
Abstract                                (~150 words)
Introduction                            (~500 words)
Initiation                              (~400 words)
Concept Location                        (~400 words)
Impact Analysis                         (~500 words)
[CORE CONTENT — DIFFERS BY ESSAY]       (~2,500 words)
Verification (the test layer)            (~400 words)
Conclusion (report)                     (~300 words)
Discussion                              (~400 words)
References & Sources
Appendix / Extra Questions              (~200 words)
                                         TOTAL: ~5,500 words = ~17-19 pages
```

The "Core Content" section is what varies:

- Essay 01 — Prefactoring (Refactoring catalogue applied).
- Essay 02 — Actualization (SOLID + Clean Architecture).
- Essay 03 — Verification: Unit Testing (Lab 7).
- Essay 04 — Verification: Acceptance Testing (Lab 9 BDD).
- Essay 05 — All phases, integrated (the capstone view).

---

## How to use these in the exam

**During the exam:**
1. Read the question. Identify which essay matches best.
2. Open that essay. Read its core content section.
3. Adapt — change the wording so it answers the *specific* question (don't paste verbatim).
4. Use the structure as your outline.
5. Insert your own screenshots / diagrams where the templates have `[INSERT DIAGRAM]` placeholders.

**Before the exam:**
- Read essay 05 first (the integrative one).
- Read the essay matching your most likely topic.
- Spot-check the others to know what they contain.
- Practice the **Abstract** for each one — that's the part graders read first and remember most.

---

## On photos and diagrams

The colleague is right that **diagrams help**. Each essay has explicit placeholders for:

- **[INSERT SCREENSHOT]** — a code editor screenshot or terminal output.
- **[INSERT UML]** — a class diagram (use [draw.io](https://draw.io), PlantUML, or any tool you prefer).
- **[INSERT TABLE]** — already provided in markdown, but you may render as a real Word/LaTeX table.

Before the exam, prepare:
- One or two **UML class diagrams** of your Group / Ungroup feature (before and after Lab 4's refactoring).
- A **screenshot of the JGiven HTML report** (run `mvn test`, open the JSON / HTML output).
- A **screenshot of GitHub Actions** showing a green CI run.
- A **before / after side-by-side** of one refactored method.

Having these ready means you only need to *insert* during the exam — not *create*.

---

## File sizes

Each essay is ~5,500 words. Markdown size ~30 KB. Printable as ~17-19 pages at standard A4 / Letter, 1.15-line spacing, 11pt.

---

Good luck.
