# SB5-MAI Software Maintenance — Individual Portfolio

**Student:** Alexandru Daniel Baducu (albad23@student.sdu.dk)  
**Course:** SB5-MAI Software Maintenance, University of Southern Denmark  
**Project:** JHotDraw — open-source Java drawing framework (fork, v9.1-SNAPSHOT)

---

## Table of Contents

1. [Lecture 1 — Introduction to Software Maintenance](#lecture-1--introduction-to-software-maintenance)
2. [Lab 1 — Introduction Lab: Project Setup](#lab-1--introduction-lab-project-setup)
3. [Lecture 2 — Software Change Process and JHotDraw Framework](#lecture-2--software-change-process-and-jhotdraw-framework)
4. [Lab 2 — Change Initiation and Concept Location](#lab-2--change-initiation-and-concept-location)
5. [Lecture 3 — Software Processes, Continuous Integration and Impact Analysis](#lecture-3--software-processes-continuous-integration-and-impact-analysis)
6. [Lab 3 — Continuous Integration and Impact Analysis](#lab-3--continuous-integration-and-impact-analysis)
7. [Lecture 4 — Refactoring and Refactoring to Patterns](#lecture-4--refactoring-and-refactoring-to-patterns)
8. [Lab 4 — Refactoring Lab: Group / Ungroup Prefactoring](#lab-4--refactoring-lab-group--ungroup-prefactoring)
9. [Lecture 5 — Actualization, OO Principles and Clean Architecture](#lecture-5--actualization-oo-principles-and-clean-architecture)
10. [Lab 5 — Actualization Lab: SOLID and Clean Architecture in JHotDraw](#lab-5--actualization-lab-solid-and-clean-architecture-in-jhotdraw)
11. [Lecture 6 — Clean Code](#lecture-6--clean-code)
12. [Lab 7 — Testing Lab: Unit Tests for Group / Ungroup](#lab-7--testing-lab-unit-tests-for-group--ungroup)
13. [Lecture 7 — Software Testing: How to Make Software Fail](#lecture-7--software-testing-how-to-make-software-fail)
14. [Lecture 9 — Pragmatic BDD for Java](#lecture-9--pragmatic-bdd-for-java)
15. [Lab 9 — Behavior-Driven Testing: JGiven Scenarios for Group / Ungroup](#lab-9--behavior-driven-testing-jgiven-scenarios-for-group--ungroup)
16. [Lecture 10 — Example of Software Change and Conclusion of the Change Process](#lecture-10--example-of-software-change-and-conclusion-of-the-change-process)
17. [Lecture 11 — Beyond Technical Debt: Behavioural Code Analysis with CodeScene](#lecture-11--beyond-technical-debt-behavioural-code-analysis-with-codescene)
18. [Capstone Reflection — The Course as One Argument](#capstone-reflection--the-course-as-one-argument)
19. [Bibliography](#bibliography)

---

## Lecture 1 — Introduction to Software Maintenance

### Overview

The opening lecture established both the motivation for studying software maintenance and the theoretical grounding that the rest of the course builds on. The central argument is that software does not stand still: once deployed, it lives, changes, ages, and eventually dies. Understanding the forces that drive that lifecycle is a prerequisite for maintaining any large existing system responsibly.

---

### Why Maintenance Matters — Essential Difficulties

Fred Brooks identified four essential difficulties intrinsic to software that no methodology can eliminate completely. The lecture added a fifth one relevant to modern systems:

| Property | What it means in practice |
|---|---|
| **Complexity** | Real software handles many more cases than a textbook example. A drawing framework like JHotDraw must support polygons, ellipses, text, groups, connectors — each with their own handles, DnD behaviour, serialisation, and undo semantics. The number of interacting states grows combinatorially. |
| **Invisibility** | Unlike a bridge or a circuit board, code has no natural physical form. The only honest visualisation tools we have are UML diagrams, call graphs, and test output — all partial views of an invisible artefact. |
| **Changeability** | Software can be changed at any moment, with no equivalent of the cost of pouring new concrete. This makes it tempting to change constantly, which compounds complexity. |
| **Conformity** | A software system must conform to its environment: OS APIs, file formats, hardware drivers, user expectations. Those environments change independently, forcing the software to keep up. |
| **Discontinuity** | Small code changes can have disproportionately large and unpredictable effects. A one-line change deep in a rendering pipeline may break dozens of features. |

These difficulties are *essential* — they arise from the nature of software itself, not from poor tooling or bad developers. This distinction matters because it sets realistic expectations: no single silver bullet will make maintenance easy. What good engineering can do is manage complexity, make change safe, and make the system more visible through clean code and good tests.

---

### Three Paradigms of Software Development

The lecture traced the historical evolution of how software was built, leading through three major paradigms that co-exist today.

#### Ad Hoc (1950s onward)

Early software was written by electrical engineers and mathematicians who treated it as an extension of hardware. There were no formal methods, no version control, and no separation of concerns. Projects succeeded or failed on individual brilliance. The IBM OS/360 project (Fred Brooks, 1975) documented what went wrong at scale: adding people to a late project makes it later; knowledge transfer costs are non-linear; architecture must be centrally owned.

This paradigm still appears today in small scripts, proof-of-concept code, and quick fixes — often without the author realising it.

#### Waterfall

The waterfall model emerged as a direct response to the chaos of ad hoc development. It imposes a strict linear sequence: Requirements → Design → Implementation → Testing → Deployment. The appeal is intuitive: finish one thing before starting the next, just as you would in construction.

The anomaly that broke the waterfall model is **requirements volatility**. Casprr Jones (1996) measured that IT requirements change at 2–3 % per month across project types:

| Software type | Monthly change rate |
|---|---|
| Contract / outsourced | 1.0 % |
| Information systems | 1.5 % |
| System software | 2.0 % |
| Military software | 2.0 % |
| Commercial software | 3.5 % |

A twelve-month waterfall project starts delivery with requirements that are roughly 30 % different from what was specified at kickoff. The Standish Group CHAOS report (tracking 50,000+ projects annually since 1994) confirms this in outcome data:

| Project size | Method | Successful | Challenged | Failed |
|---|---|---|---|---|
| All sizes | Agile | 39 % | 52 % | 9 % |
| All sizes | Waterfall | 11 % | 60 % | 29 % |
| Large | Agile | 18 % | 59 % | 23 % |
| Large | Waterfall | 3 % | 55 % | 42 % |
| Small | Agile | 58 % | 38 % | 4 % |
| Small | Waterfall | 44 % | 45 % | 11 % |

Agile approaches outperform waterfall at every project size — the gap is most dramatic for large projects where waterfall's failure rate is 42 % versus 23 % for Agile.

#### Iterative / Agile

The iterative paradigm treats requirements volatility as a given rather than a problem to eliminate. Work is divided into short iterations (sprints); each iteration produces a deliverable that stakeholders can evaluate; feedback closes the loop and adjusts the backlog. The Scrum/Agile cycle (Plan → Collaborate → Deliver → Review → repeat) is explicitly designed to absorb change.

All three paradigms co-exist in modern organisations. A startup may use full Agile; a safety-critical medical device project may use waterfall for regulatory traceability; individual developers writing tools often work ad hoc.

---

### Software Life-Span Models

Life-span models describe the stages a software product passes through from conception to retirement. They are distinct from development process models (waterfall, agile) — they describe the product's lifetime, not one project's process.

#### Staged Model

The staged model identifies five phases:

1. **Initial development** — the first working version is built.
2. **Evolution** — the system grows and changes in response to feedback and new requirements. Most feature development happens here.
3. **Servicing / Maintenance** — evolution has stopped; only patch-level fixes are applied. No new features are added.
4. **Phase-out** — no new patches are released; the system still runs but is not actively maintained.
5. **Close-down** — the system is switched off and users migrate to a replacement.

The sharp transition from Evolution to Servicing is often driven by *code decay*: after many years of changes by different developers, the internal structure has degraded to the point where adding features costs more than starting fresh.

#### Versioned Staged Model

Real products ship multiple major versions. The Versioned Staged Model extends the Staged Model by running parallel tracks: Version 1 enters Servicing while Version 2 is still in Evolution; Version 2 enters Servicing as Version 3 begins Evolution. This is exactly how long-lived frameworks like JHotDraw work — the codebase we maintain in this course is one evolution in a sequence of versions dating back to 1996.

#### V-Model

The V-Model maps each development activity to a corresponding verification activity:

- Requirements ↔ Functional testing
- System design ↔ System testing
- Unit design ↔ Unit testing
- Implementation sits at the bottom of the V

Maintenance is the final stage that follows functional testing. The model makes explicit that every design decision implies a testing obligation, and testing failures feed back to the matching design level.

#### Prototype Model

When requirements are uncertain, building a throwaway prototype before committing to full design can be more effective than any amount of upfront analysis. The sequence is: Requirements → Prototype → Corrected Requirements → Design → Implementation → Maintenance. The prototype's purpose is to make the invisible visible early enough for stakeholders to give meaningful feedback.

---

### Reflection

The most practically relevant takeaway from this lecture is the relationship between requirements volatility and code quality. Software that is maintained well enough that its internal structure stays clean (low coupling, high cohesion, good naming) can survive the Evolution phase much longer before code decay forces a Servicing freeze. This is the underlying justification for Clean Code practices — the topic the SB5-MAI portfolio assignments are centred on.

JHotDraw is a good case study precisely because it is a real, long-lived project with real code decay. Studying it forces engagement with problems that toy examples cannot reproduce.

---

## Lab 1 — Introduction Lab: Project Setup

### Objectives

- Set up the Maven build system and verify the project builds cleanly.
- Establish the GitHub workflow the team will use throughout the course.
- Run the JHotDraw application and observe its structure from a user perspective.

---

### Environment

| Component | Version / Detail |
|---|---|
| OS | Fedora Linux (kernel 7.0.9) |
| Java | OpenJDK 25 (JDK 11 source/target set in `pom.xml`) |
| Maven | Apache Maven 3.8.8 (portable, `/tmp/maven`) |
| Repository | GitHub fork of the course JHotDraw repository |
| Build tool | Maven multi-module, root POM at project root |

> **Note on Java version:** The `pom.xml` sets `maven.compiler.source=1.8` and `maven.compiler.target=1.8`, targeting Java 8 bytecode. The system JDK is version 25, which can compile and run Java 8 bytecode without issue. No compatibility flags were needed.

---

### GitHub Workflow

The team follows [GitHub Flow](https://docs.github.com/en/get-started/using-github/github-flow):

1. The shared fork lives on the team's GitHub organisation.
2. Each team member creates a **feature branch** from `develop` for each task.
3. Work is committed to the feature branch with descriptive commit messages.
4. A **pull request** is opened against `develop` when the work is ready for review.
5. At least one teammate reviews and approves; CI must pass before merge.
6. The merged branch is deleted and `develop` is pulled locally.

My personal branch for this course is `alex`. For each portfolio task I create a short-lived topic branch (e.g., `alex/lab1-setup`) and PR it back.

---

### Project Structure

JHotDraw is a Maven multi-module project. The root `pom.xml` declares these modules:

```
jhotdraw/                          (root, v9.1-SNAPSHOT)
├── jhotdraw-api/                  interfaces: Drawing, Figure, Tool, Handle, ...
├── jhotdraw-core/                 default implementations of the drawing model
├── jhotdraw-gui/                  Swing components (panels, toolbars, palettes)
├── jhotdraw-app/                  SDI/MDI application shell
├── jhotdraw-actions/              action objects (Cut, Copy, Paste, SelectAll, ...)
├── jhotdraw-datatransfer/         clipboard and DnD support
├── jhotdraw-utils/                utility classes
├── jhotdraw-xml/                  XML serialisation (JHotDraw native format)
└── jhotdraw-samples/
    ├── jhotdraw-samples-misc/     full applications: Draw, SVG, Net, Pert, Teddy, ODG
    └── jhotdraw-samples-mini/     small single-feature demos
```

The dependency graph flows from `jhotdraw-api` outward: core depends on api, gui depends on core, app depends on gui, samples depend on app. This layered structure is the architectural backbone that makes it possible to extend or replace individual layers without rewriting everything.

---

### Build Instructions

#### Step 1 — Verify Maven is available

```bash
/tmp/maven/bin/mvn --version
# Apache Maven 3.8.8
# Java version: 25.0.3, vendor: Red Hat, Inc.
```

#### Step 2 — Build all modules (skip tests for initial setup)

Run from the repository root:

```bash
/tmp/maven/bin/mvn clean install -DskipTests --no-transfer-progress
```

What this does:
- `clean` — removes all `target/` directories from previous builds.
- `install` — compiles, packages as JAR, and places each module into the local Maven repository (`~/.m2/`) so downstream modules can resolve them as dependencies.
- `-DskipTests` — skips test compilation and execution. Used only during initial setup to verify the build chain; tests should be run normally during development.

Expected outcome: `BUILD SUCCESS` at the root level, with each sub-module reporting success in order.

#### Step 3 — Run the SVG sample application

Navigate to the `jhotdraw-samples-misc` module:

```bash
cd jhotdraw-samples/jhotdraw-samples-misc

/tmp/maven/bin/mvn exec:java \
  "-Dexec.mainClass=org.jhotdraw.samples.svg.Main" \
  --no-transfer-progress
```

The application opens a Swing window with:
- A blank SVG canvas
- A toolbar with drawing tools (selection, rectangle, ellipse, text, path, ...)
- A menu bar (File, Edit, View, ...)

Other runnable main classes in the same module:

| Application | Main class |
|---|---|
| Draw (general) | `org.jhotdraw.samples.draw.Main` |
| SVG editor | `org.jhotdraw.samples.svg.Main` |
| Network diagrams | `org.jhotdraw.samples.net.Main` |
| PERT chart | `org.jhotdraw.samples.pert.Main` |
| Teddy text | `org.jhotdraw.samples.teddy.Main` |

---

### Findings and Observations

#### Build system

The build succeeds cleanly after resolving one dependency: the `pom.xml` references a GitHub Package Registry (`https://maven.pkg.github.com/sweat-tek/MavenRepository`) for external dependencies. This registry requires authentication. A `.maven-settings.xml` is provided in the repository root that reads credentials from environment variables:

```xml
<!-- .maven-settings.xml snippet -->
<server>
  <id>github</id>
  <username>${env.GITHUB_ACTOR}</username>
  <password>${env.GITHUB_TOKEN}</password>
</server>
```

For local builds, the `GITHUB_TOKEN` environment variable must be set to a valid GitHub personal access token with `read:packages` scope. In CI (GitHub Actions), this is injected automatically from repository secrets.

#### Code structure observations

After the build succeeded, the first code exploration revealed that JHotDraw is structured around the **Model-View-Controller** pattern at multiple scales:

- `Drawing` (model) holds a list of `Figure` objects.
- `DrawingView` (view) renders figures onto a Swing component.
- `DrawingEditor` (controller) manages the active tool and coordinates between view and model.

The `Figure` interface is the central abstraction. Every shape — rectangle, ellipse, path, text box, connector — implements `Figure`. Each `Figure` is responsible for painting itself, computing its bounds, managing its own `Handle` objects (the small squares that appear when a figure is selected), and serialising itself to XML.

This design matches the **Composite** and **Decorator** patterns: figures can be composed into groups, and behaviours can be layered on top via decorators. The pattern usage is explicit and commented in places, which makes the codebase a good study object for design patterns in practice.

#### Test coverage

Running the full test suite:

```bash
/tmp/maven/bin/mvn test --no-transfer-progress
```

Tests are present but sparse relative to the codebase size — a common characteristic of legacy open-source projects that grew before test-driven development was widespread. This is directly relevant to the maintenance work ahead: adding or refactoring features safely requires either extending existing tests or writing new ones first.

#### CI pipeline

The repository uses GitHub Actions. The workflow defined in `.github/workflows/` runs `mvn -B -s .maven-settings.xml test` on every pull request targeting `develop`. This means:
- Broken builds are caught before merge.
- Test regressions are visible to the whole team.
- The `-s .maven-settings.xml` flag supplies the authenticated settings file so the GitHub Package Registry dependency can be resolved in CI.

---

### Summary

Lab 1 established the working environment for the entire course. The key outcomes were:

1. The project builds successfully from source using Maven 3.8.x on JDK 11+ (tested on JDK 25).
2. The SVG and Draw sample applications run and present a functional GUI.
3. The GitHub Flow is in place: feature branches, PRs to `develop`, CI on every PR.
4. First-pass code reading identified the MVC architecture and the central role of the `Figure` interface — both of which will be relevant in every future lab and portfolio exercise.

The most important maintenance-relevant observation is the gap between architectural clarity at the macro level (clean module boundaries, recognisable design patterns) and the code quality at the micro level (long methods, magic numbers, inconsistent naming in older classes). Bridging that gap — applying Clean Code principles systematically to a real, working legacy codebase — is precisely what this course trains for.

---

## Lecture 2 — Software Change Process and JHotDraw Framework

The second lecture block was delivered as four tightly-coupled sub-lectures: an overview of software change, change initiation, concept location, and a tour of JHotDraw with its design patterns. Together they install the conceptual toolkit needed to start actually modifying the case-study codebase. I am writing them up as one combined section because the phased model is the thread that runs through all four.

---

### 2.1 The Phased Model of Software Change

A software change (SC) is the process of adding new functionality to, removing functionality from, or restructuring existing code. It is the atomic unit of both evolution and servicing in the Staged Model from Lecture 1.

Lientz and Swanson's empirical study classified maintenance work into four kinds and measured how programmer effort is distributed across them:

| Category | Share of effort | Example |
|---|---|---|
| **Perfective** | 50 % | Add credit-card support to a payment module |
| **Adaptive** | 25 % | Y2K date-format migration |
| **Corrective** | 21 % | Fix a bug |
| **Preventive** | 4 % | Refactor a tangled class before adding new features |

This is counter-intuitive: the popular image of "maintenance" is bug-fixing, yet corrective work is only ~20 %. Half of all maintenance effort is *new functionality* added to existing code. This single statistic justifies why this course is centred on change rather than on debugging.

Independently of *kind*, every change has one of four **impacts on functionality**:

- **Incremental** — adds new functionality.
- **Contraction** — removes obsolete functionality.
- **Replacement** — substitutes a different implementation for an existing one.
- **Refactoring** — changes structure without changing behaviour.

The course models each individual change as a sequence of seven phases. The phases form a V: the first three describe and design the change, the next three implement it, and verification runs continuously down the right side of the V:

1. **Initiation** — a change request enters the backlog.
2. **Concept Location** — find where in the code the relevant concepts live.
3. **Impact Analysis** — determine which other parts of the code will be affected (the impact set).
4. **Prefactoring** — opportunistic refactoring to *minimise* the impact set before adding new code.
5. **Actualization** — write the new code and plug it into the existing code; propagate change to neighbouring classes (ripple effect).
6. **Postfactoring** — clean up anti-patterns introduced by actualization (long methods, bloated classes).
7. **Conclusion** — commit, build new baseline, prepare for next change.

**Verification** (testing — unit, functional, structural — and walkthroughs) is not a phase but a vertical concern that touches every step. This is the V-Model from Lecture 1 applied to a single change rather than to the whole product.

The remaining three sub-lectures zoom in on the first two phases.

---

### 2.2 Change Initiation

A software change starts as a **change request** from one of four typical sources:

- A user reports a bug.
- A user asks for an enhancement.
- A programmer proposes an improvement (a refactoring, a performance optimisation).
- A manager wants to close a feature gap against a competitor.

The request can take three forms:

| Form | When to use |
|---|---|
| Sentence or paragraph | Simple, well-understood changes. |
| Bug report | Corrective changes; usually fits a template (steps to reproduce, expected, actual). |
| **User story** | Enhancements and new features that require user-facing context. |

A user story is a short, structured description of a capability written from the perspective of the person who wants it. The canonical template is:

> **As a [user type], I want [some goal] so that [some reason].**
> *(or: "...because [why]")*

User stories have a hard constraint: they must fit on a physical 3″ × 5″ index card. The point is *not* the card — it is the discipline of limiting one story to one capability. If the new functionality cannot fit on the card, it has to be split into several smaller stories. This is the same principle as keeping a unit test focused: complexity that won't fit in a small artefact will not fit in a small change either.

Once written, a change request enters the **product backlog** — a prioritised wish list. The standard MoSCoW prioritisation buckets are:

- **Must have** — required for the next release.
- **Nice to have** — would improve the product but is not blocking.
- **Won't have** (this iteration) — explicitly deferred.

The backlog is dynamic: requirements are added, removed, and modified continuously, both because users acquire knowledge and because developers need clarifications. This is the agile response to the requirements-volatility anomaly from Lecture 1 (the Casper Jones 2–3 %/month figure): rather than freezing requirements, accept that they will change and design the process so changes are cheap.

---

### 2.3 Concept Location

Once a change request is on the backlog, the first technical question is: *where in the code do I need to change?* That is the concept-location problem.

#### Partial code comprehension

Large programs cannot be completely understood by any single developer. The realistic goal is **partial comprehension** — a minimum-essential understanding driven by *as-needed strategy*: learn how the specific concepts touched by the current change are reflected in the code, and nothing more. The slide analogy is visiting a large city: you don't memorise the street grid, you learn the route to the destinations you care about.

#### The concept triangle

Every domain concept has three faces:

```
                Name
              /      \
       naming        annotation
            /            \
   Intension ── recognition ── Extension
              \                /
              definition    location
```

- **Name** — the word(s) used to refer to the concept ("dog", "Hund", "pes").
- **Intension** — the definition (a hairy animal with teeth that barks, ...).
- **Extension** — the set of actual instances (Fido, Lassie, the family dog at home).

The three relationships that connect the faces are what make concept location *work*:

- **Naming** binds a name to an intension (the definition).
- **Recognition** binds an intension to its extensions (you can see a thing and decide it's a dog).
- **Annotation** binds a name directly to its extensions (the label on Fido's collar).
- **Traceability** is the chain Name → Intension → Extension, which is exactly what concept location performs in code.

For a code base: the **name** is the term from the change request (`Group`, `Export`, `Watermark`), the **intension** is the responsibility of a class or method, and the **extension** is the actual lines of source that implement that responsibility.

#### Four methodologies

The lecture lists four approaches, in order of increasing automation:

1. **Human knowledge** — ask the original author, read the docs, look at past commits.
2. **Traceability tools** — explicit links maintained between requirements and code (rare in practice).
3. **Dynamic search** — run the program with the relevant feature triggered and capture execution traces (covered in Lab 2 — we use the IDE debugger as a dynamic search tool).
4. **Static search** — search the source without running it. Three sub-techniques:
   - **GREP** — iteratively refine a regular expression and read the matches.
   - **Pattern matching / information retrieval** — TF-IDF, latent semantic indexing, etc., over identifier names.
   - **Dependency search** — walk the Class Dependency Graph (CDG).

#### Dependency search — the formal algorithm

This is the most rigorous of the four and the one I use in Lab 2. It walks the CDG one class at a time. For each class visited it asks two questions:

1. Is the concept implemented in this module's **local functionality** (its own code, not delegated)?
2. If not, is it implemented in the module's **composite functionality** (this module plus everything it depends on)?

Each visited class is marked with one of four states:

| Mark | Meaning |
|---|---|
| **Blank** | Never inspected, not scheduled. |
| **Next** | Scheduled for inspection. |
| **Propagating** | Inspected. Local functionality does not contain the concept, but the composite does — so walk into the suppliers. |
| **Unchanged** | Inspected. Neither local nor composite functionality contains the concept — stop here, this branch is a dead end. |

The algorithm:

```
1. Find the set of starting modules (usually controller / entry-point classes).
2. Select one module from the "Next" set.
3. Is the concept in this module's local functionality?
     Yes → DONE. Stop the search.
     No  → Is the concept in the composite functionality?
              Yes → Mark "Propagating". Add suppliers to "Next". Loop.
              No  → Mark "Unchanged". Pop back to the previous frame (backtrack). Loop.
```

The dependency-search slides illustrate this with a UML editor example: starting from `UMLEditor`, you find the concept is not local; you mark it Propagating and inspect its five supplier diagram-graph classes; the concept turns out to live in `ClassDiagramGraph`'s supplier `ClassNode`; following the path further might reveal that `RectangularNode` (a supplier of `ClassNode`) implements the same concept in a more general form, giving you a second candidate location.

The pattern that emerges: concept location is rarely answered by one class. The honest output is a small set of classes with their responsibilities, ordered from most specific to most general.

---

### 2.4 The JHotDraw Framework and its Design Patterns

JHotDraw is the case-study codebase for the rest of the course. Two facts about it shape every later lab:

1. It is *old*. The original HotDraw was written in Smalltalk by Kent Beck and Ward Cunningham in the early 1990s — making it one of the first projects ever explicitly labelled a "framework" and designed for reuse. The Java port (JHotDraw) is a direct descendant.
2. Because it was designed for reuse from day one, it uses **design patterns** extensively. Eight of the original Gang-of-Four 23 patterns are used in the core framework, and several others appear in the samples.

#### Core architecture

The framework's central class diagram is a small constellation around `DrawingEditor` (which corresponds to `DrawApplication` in the original Smalltalk version):

- A `DrawingEditor` owns a `DrawingView` (the visible Swing component) and the currently active `Tool`.
- A `Drawing` (the model) holds the `Figure`s.
- A `Figure` knows how to paint itself, computes its bounds, and supplies `Handle`s for direct manipulation when selected.

This maps directly onto **Model-View-Controller**: `Figure` and `Drawing` are the model, `DrawingView` is the view, and `Tool` (plus `Action` classes) is the controller. The `notification` arrow on the framework diagram is the Observer relationship that propagates model changes to the view.

#### Design patterns used in JHotDraw

The eight GoF patterns that appear in the core framework, with the JHotDraw application:

| Pattern | Category | JHotDraw role |
|---|---|---|
| **Model-View-Controller** | Architectural | Whole framework structure: `Figure`/`Drawing` ↔ `DrawingView` ↔ `Tool`. |
| **Composite** | Structural | `CompositeFigure` contains child `Figure`s; clients treat a single figure and a composite uniformly. Group/ungroup is the direct user-visible expression of this pattern. |
| **Strategy** | Behavioural | `Layouter` algorithms are attached to a `CompositeFigure` and decide where its children go (align-to-top, align-to-left, …). Same shape as Swing's LayoutManager. |
| **State** | Behavioural | Tools externalise their internal state into a state object — e.g. a selection tool that behaves differently after 1, 2, or 3 clicks delegates to `ZeroClickState` / `OneClickState` / `TwoClickState`. |
| **Template Method** | Behavioural | Skeletons for connecting figures: `LineConnection` defines the invariant connection algorithm and exposes hooks (`canConnect`, `findStart`, `findEnd`) that subclasses override per diagram type. |
| **Decorator** | Structural | Visual decorations layered on a `Figure` — `BorderDecorator`, `ShadowDecorator`. Decorators give per-instance visual extensions without exploding the class hierarchy. |
| **Factory Method** | Creational | `DrawApplication.createTools()` / `createMenus()` are factory methods overridden by concrete applications (`ClassDiagramDrawApplication`, `FigureDrawApplication`) to customise tool and menu sets. |
| **Prototype** | Creational | Each drawing tool is initialised with a *prototype* `Figure` (a circle, a rectangle, …). Creating a new figure on the canvas clones the prototype. This is also how `GroupAction` works internally — it clones its `GroupFigure` prototype each time the user groups a selection. |

#### Underlying design principles

The lecture distilled the GoF philosophy into three slogans, all of which JHotDraw obeys:

1. **Program to an interface, not to an implementation.** JHotDraw is dominated by interfaces (`Figure`, `Drawing`, `DrawingView`, `Tool`, `Handle`, `CompositeFigure`, `Layouter`, `Connector`); concrete classes are always one level below.
2. **Favour composition over class inheritance.** Decorators, Strategies, and Factories all replace inheritance with object composition. Inheritance is still used (e.g. `AbstractCompositeFigure` ← `GroupFigure`) but only inside coherent families, never for adding orthogonal behaviour.
3. **Find what varies and encapsulate it.** Layout algorithms vary → Strategy. Tool behaviour over time varies → State. Visual decorations vary → Decorator. Concrete figure types vary → Prototype + Factory Method.

The general observation: extensibility of frameworks *requires* extensive use of design patterns. Without patterns, every variation point would become either a parallel class hierarchy or a switch statement — both of which scale badly.

---

### Reflection on Lecture 2

The first lecture (Lecture 1) gave the *why* of software maintenance — the essential difficulties, the historical paradigms, the life-span models. Lecture 2 gives the *how*: every individual change is a small instance of the V-Model, starting with a user story, locating concepts in the code, analysing impact, and verifying continuously.

The two most operationally useful ideas for the rest of the course:

- The 3″ × 5″ card constraint on user stories — if I cannot describe my change concisely, I am not ready to do it. This is the same as the rule that a refactoring should be small enough to keep both old and new behaviour mentally in scope.
- Dependency search as a disciplined alternative to "browse around until something looks right". For JHotDraw specifically, the static structure (`Figure`, `Drawing`, `Tool` interfaces with concrete implementations in well-defined places) makes dependency search particularly effective.

The JHotDraw sub-lecture pays off immediately: knowing that the framework uses Composite, Prototype, and Command means that for the Group / Ungroup feature I work on in Lab 2, I can predict the shape of the implementation before opening the source — there will be a `GroupFigure` (Composite), it will be cloned from a prototype, and there will be an `Action` class wrapped in an undoable edit. All three predictions were borne out, which is itself a small validation that the design-pattern vocabulary is doing its job.

---

## Lab 2 — Change Initiation and Concept Location

Lab 2 was delivered as two consecutive lab sessions on the same feature:

- **Lab 2a (ChangeReqLab)** — Write a user story for an existing JHotDraw feature; place it in the team's TODO backlog.
- **Lab 2b (CLLab)** — Use the IDE debugger and dependency search to locate the classes that implement that feature; produce a `Domain Class | Responsibility` table.

I treat both as one Lab 2 in this portfolio because the artefacts only make sense together: the user story defines the concepts, and concept location resolves those concepts to code.

---

### Feature Selected: Group / Ungroup Figures

I selected the **Group / Ungroup** feature for the portfolio. The motivations:

- It is a *real, user-visible* feature exposed in the Edit menu and the toolbar of every sample application.
- It is a direct expression of the **Composite** design pattern from Lecture 2.4, which makes the pattern → code mapping concrete.
- The scope is bounded: ~6–8 domain classes total, small enough that the concept-location table fits on one page, but large enough to exercise multiple modules (`jhotdraw-core`, `jhotdraw-gui`, `jhotdraw-samples-misc`).
- It involves both the controller layer (`Action` classes) and the model layer (figure hierarchy), giving the impact analysis in Lab 3 something to work with.

---

### Lab 2a — User Story (ChangeReqLab)

#### The user story

> **As a** user editing a drawing,
> **I want** to select several figures and combine them into a single group (and later split a group back into its original figures),
> **so that** I can move, scale, and rotate the figures together as one shape and keep my canvas organised when it contains many related figures.

This fits on a 3″ × 5″ card (≈ 50 words). It identifies the actor (a user, not an administrator), the capability (group + ungroup as a pair), and the motivation (uniform manipulation + canvas organisation).

#### Acceptance criteria

Derived from the user story and used to bound the implementation later:

1. With ≥ 2 figures selected, the **Group** command is enabled in the Edit menu and toolbar.
2. Choosing Group replaces the selection with a single composite figure that contains the original figures as children.
3. With exactly one group figure selected, the **Ungroup** command is enabled.
4. Choosing Ungroup replaces the group with its children in the drawing, restoring their independent selectability and their original Z-order.
5. Both Group and Ungroup are undoable and redoable via the standard Edit > Undo / Redo commands.
6. Operations on a group (move, scale, rotate, delete) act on all children uniformly.

#### Concepts extracted from the user story

Following the concept-triangle exercise from Lecture 2.3, the underlined nouns in the user story are the concepts I need to locate:

`Selection`, `Figure`, `Group`, `Drawing`, `Move/Scale/Rotate (Transform)`, `Undo`.

These are the *names*; concept location resolves each one to an *extension* (a class, an interface, or a method) in the source.

#### Backlog card

The card was added to the team's GitHub Projects board as one item in the TODO column:

```
┌──────────────────────────────────────────┐
│ ID:    SB5-04                            │
│ Title: Group / Ungroup figures           │
│ Type:  Enhancement (Perfective)          │
│ Owner: Alex                              │
│                                          │
│ As a user editing a drawing,             │
│ I want to combine selected figures into  │
│ a single group (and ungroup later),      │
│ so that I can manipulate them together   │
│ and keep the canvas organised.           │
│                                          │
│ Priority: Must have                      │
│ Estimate: 1 lab session                  │
└──────────────────────────────────────────┘
```

Per Lientz–Swanson, this is a **perfective** change (new user-facing functionality), which matches the largest slice of typical maintenance work (50 %).

---

### Lab 2b — Concept Location (CLLab)

#### Methodology

I used **dependency search** as the primary technique, supplemented with a **dynamic search** pass to validate the entry points:

1. **Dynamic search (IDE debugger as a tracing tool):** I launched the SVG sample, drew three rectangles, selected them, and chose `Edit > Group`. Setting a breakpoint on `JMenuItem.actionPerformed` and stepping forward revealed `GroupAction.actionPerformed` as the *starting module* — the controller-layer class that owns the response to the user's click.
2. **Dependency search from the starting module:** from `GroupAction` I followed object-level dependencies (constructor parameters, field types, method-call targets) to find the classes whose composite functionality contains the `Group` concept. I marked each class **Propagating** or **Unchanged** as I went, exactly as in the slides.

The dynamic-search step matters: without it, a static reader can be tricked by the existence of a `GroupFigure` class into thinking that the model layer is the entry point. The user's `Group` click actually enters `GroupAction`, not `GroupFigure`, and the `Action` is what owns the undo wrapping and the selection-manipulation logic.

#### Trace

Starting set: `GroupAction` (from dynamic search).

| Step | Class inspected | Local has concept? | Composite has concept? | Mark | Next to inspect |
|---|---|---|---|---|---|
| 1 | [`GroupAction`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java) | Partially — owns `actionPerformed`, `groupFigures`, `ungroupFigures` | Yes | Propagating | `CompositeFigure`, `GroupFigure`, `DrawingView`, `Drawing` |
| 2 | [`UngroupAction`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/UngroupAction.java) | No new logic — subclasses `GroupAction` with `isGroupingAction=false` | Yes (delegated) | Propagating | — (delegates back to `GroupAction`) |
| 3 | [`CompositeFigure`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/CompositeFigure.java) | Yes — interface for figures that contain children (`add`, `remove`, `basicAdd`, `getChildren`) | Yes | Propagating | `Figure`, `AbstractCompositeFigure` |
| 4 | [`AbstractCompositeFigure`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/AbstractCompositeFigure.java) | Yes — generic implementation of child management, change notification, layouter hook | Yes | Propagating | `GroupFigure` |
| 5 | [`GroupFigure`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/GroupFigure.java) | Yes — the concrete composite cloned by `GroupAction` as the new container | Yes | Propagating | — (no domain-relevant suppliers) |
| 6 | [`Figure`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/Figure.java) | No — generic figure abstraction, not group-specific | Yes (via subclasses) | Unchanged for this concept | Backtrack |
| 7 | [`DrawingView`](jhotdraw-core/src/main/java/org/jhotdraw/draw/DrawingView.java) | Yes — owns the selection (`getSelectedFigures`, `addToSelection`, `clearSelection`) which Group/Ungroup manipulates | Yes | Propagating | `Drawing` |
| 8 | [`Drawing`](jhotdraw-core/src/main/java/org/jhotdraw/draw/Drawing.java) | Yes — the container that holds top-level figures; Group/Ungroup adds and removes the group from it via `basicAddAll` / `basicRemoveAll` / `indexOf` | Yes | Propagating | — |
| 9 | [`ButtonFactory.addEditMenuItems`](jhotdraw-gui/src/main/java/org/jhotdraw/gui/action/ButtonFactory.java#L351-L352) | Yes — wires `GroupAction` and `UngroupAction` into the Edit menu of every standard editor | No new concept | Unchanged (wiring only) | — |
| 10 | `AbstractUndoableEdit` (JDK) | No — generic undo plumbing, not group-specific | No | Unchanged | Backtrack |

Stopping condition: every class on the open "Next" frontier is either an `Unchanged` JDK class or a sample-specific variant (`SVGGroupFigure`, `ODGGroupFigure`) that I include below for completeness but treat as out-of-scope domain extensions rather than core concepts.

#### Domain Class | Responsibility table

This is the deliverable required by CLLab. It lists *only* the domain classes that are part of the core Group/Ungroup concept — sample-app variants are noted separately.

| Domain Class | Responsibility |
|---|---|
| [`GroupAction`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java) | Controller-layer command for the Edit > Group menu item. Validates that ≥ 2 figures are selected, clones a `CompositeFigure` prototype, removes the selected figures from the `Drawing`, adds them as children of the new group, and wraps the whole operation in an `UndoableEdit`. Also contains the inverse `ungroupFigures` method. Single class — handles both directions, parameterised by `isGroupingAction`. |
| [`UngroupAction`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/UngroupAction.java) | Thin subclass of `GroupAction` configured with `isGroupingAction = false` and the `edit.ungroupSelection` resource bundle ID. Validates that exactly one figure is selected and that it is of the same class as the prototype `GroupFigure`. Reuses the parent's `ungroupFigures` method. |
| [`CompositeFigure`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/CompositeFigure.java) | Interface for any figure that contains child figures. Declares the Composite operations: `add`, `remove`, `basicAdd`, `basicRemove`, `getChildren`, plus the strategy hook `setLayouter`. This is the *intension* of the `Group` concept — anything implementing it can serve as a group. |
| [`AbstractCompositeFigure`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/AbstractCompositeFigure.java) | Reusable base implementation of `CompositeFigure`. Manages the list of children, propagates `willChange`/`changed` notifications, registers the change listener that forwards `requestRemove` events from children, and applies the optional `Layouter` strategy. |
| [`GroupFigure`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/GroupFigure.java) | Concrete composite used by the standard `GroupAction`. Marks itself as non-connectable so connectors do not attach to a group as a whole; overrides `isTransformable()` to be true only if every child is transformable; provides `chop` for connector geometry. Acts as the prototype that `GroupAction` clones each time the user groups a selection. |
| [`Figure`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/Figure.java) | Generic figure interface — the *component* in the Composite pattern. Group/Ungroup operates on collections of `Figure` and adds/removes them from a `CompositeFigure`. Not group-specific, but every operation in the group flow eventually calls a `Figure` method. |
| [`DrawingView`](jhotdraw-core/src/main/java/org/jhotdraw/draw/DrawingView.java) | Selection owner on the controller side. Group/Ungroup reads `getSelectedFigures` to know what to group, calls `clearSelection` before mutating the drawing, and `addToSelection(group)` after to leave the new group selected. Decouples the action from the Swing component that paints the canvas. |
| [`Drawing`](jhotdraw-core/src/main/java/org/jhotdraw/draw/Drawing.java) | The document model that holds top-level figures. Group/Ungroup calls `indexOf` to preserve Z-order, `basicRemoveAll` to detach the selected figures from the drawing root, and `add(index, group)` (Group) or `basicAddAll(index, figures)` (Ungroup) to splice the result back in at the correct depth. |

Domain extensions in the sample applications (not central to the concept, listed for completeness):

| Extension | Sample app | Variant of |
|---|---|---|
| `SVGGroupFigure` | `jhotdraw-samples-misc/svg` | `GroupFigure` with SVG-specific serialisation hooks. |
| `ODGGroupFigure` | `jhotdraw-samples-misc/odg` | `GroupFigure` for the OpenDocument Graphics sample. Wired through `ODGApplicationModel` which creates `GroupAction(editor, new ODGGroupFigure())`. |
| `CombineAction` / `SplitAction` | `jhotdraw-samples-misc/odg/action` | ODG-domain analogues of group / ungroup that combine paths instead of wrapping figures. |

#### Findings and reflections

**1. The "Group" concept has both intensional and extensional class representatives.**
The intension (what it means to be groupable) lives in `CompositeFigure`. The extension (the actual thing created when the user groups three rectangles) is an instance of `GroupFigure`. The concept-triangle vocabulary from Lecture 2.3 maps cleanly onto the codebase: a portfolio reader can take the underlined nouns from the user story and trace them to specific files.

**2. Group and Ungroup are one class, not two.**
The most surprising finding: `UngroupAction` does *not* duplicate the logic from `GroupAction`. It just subclasses `GroupAction` and flips the boolean `isGroupingAction`. Reading `GroupAction.java` shows both `groupFigures` and `ungroupFigures` are defined as instance methods on `GroupAction` itself, and the inner `AbstractUndoableEdit`'s `undo()` of a Group calls `ungroupFigures(...)`. The author even left an honest comment at `GroupAction.java:148`: `// XXX - This code is redundant with UngroupAction`. This is a Lecture-1-style maintenance smell — a TODO left in 1996 still in the code today.

**3. The Prototype pattern is doing real work.**
`GroupAction` accepts a `CompositeFigure prototype` in its constructor and calls `prototype.clone()` every time the user groups a selection. This is how the SVG and ODG samples plug in their own `SVGGroupFigure` / `ODGGroupFigure` without changing `GroupAction` — they call `new GroupAction(editor, new ODGGroupFigure())`. Prediction from Lecture 2.4 confirmed by the code: extensibility is achieved through Prototype, not through subclassing the action.

**4. The undo/redo wrapping is a Command pattern instance.**
`GroupAction.actionPerformed` builds an anonymous `AbstractUndoableEdit` whose `redo()` calls `groupFigures(...)` and whose `undo()` calls `ungroupFigures(...)`. The same single class therefore plays three GoF roles in this feature: Action (controller), Command (the inner undoable edit), and indirectly Prototype-client (clones the figure prototype).

**5. The wiring is deliberately separated from the logic.**
None of the Action / Figure classes ever construct themselves. They are wired in by [`ButtonFactory.addEditMenuItems`](jhotdraw-gui/src/main/java/org/jhotdraw/gui/action/ButtonFactory.java#L351-L352) in `jhotdraw-gui`, which every sample application calls. This is the Factory Method pattern: a single piece of GUI wiring code knows about Group/Ungroup, and individual applications override it only to substitute different prototypes. For impact analysis in Lab 3, this means a change to the Group feature touches `jhotdraw-core` (logic) but only `jhotdraw-gui` (wiring) — not the other six modules.

**6. Selection is not part of the Drawing model.**
A common mis-prediction: that selected figures live inside `Drawing`. They don't. Selection is owned by `DrawingView` (the controller-adjacent side), which is why `GroupAction` keeps a reference to a `DrawingView` rather than to a `Drawing`. This separation matches MVC strictly — the document model doesn't know which figures are currently selected, only the view does.

#### Summary

The Group / Ungroup feature is implemented by **8 core domain classes** across **2 modules**, plus wiring in `jhotdraw-gui` and two sample-specific variants. The concept-location process took about 45 minutes of debugger-driven exploration plus dependency walking. Without the design-pattern vocabulary from Lecture 2.4 it would have taken substantially longer, because the abstraction layers (`Figure` → `CompositeFigure` → `AbstractCompositeFigure` → `GroupFigure`) would have looked like accidental complexity rather than the deliberate Composite pattern they are.

The deliverable for the next lab (Impact Analysis) is now well-bounded: the impact set starts from these 8 classes plus their direct callers — fewer than 20 classes total.

---

## Lecture 3 — Software Processes, Continuous Integration and Impact Analysis

The third lecture block was again delivered as four interlocking sub-lectures: an introduction to software processes and the solo iterative process, team iterative processes (AIP/DIP/CIP), continuous integration, and impact analysis — the third phase in the phased model of software change. The unifying idea is that processes at every granularity, from one developer fixing one bug to a 500-person product team shipping a release, share the same underlying loop, and that *measuring* the process is what makes it improvable.

---

### 3.1 Introduction to Software Processes

The lecture defined a software process at six granularities, from coarsest to finest:

| Granularity | Example |
|---|---|
| **Lifecycle** | Staged model, waterfall |
| **Stage** | Evolution, servicing |
| **Process** | SIP, AIP, DIP, CIP |
| **Task** | Software change, acceptance testing |
| **Subtask / phase** | Concept location, actualization |
| **Step / action** | Inspection of a single class |

The word "process" in everyday usage refers to the middle layer — a process that fits within one or two stages of the lifecycle. This is the layer all of the team processes (AIP/DIP/CIP) and the solo process (SIP) live at.

#### Forms a process can take

The same process exists in four distinct forms simultaneously, and confusing them is a common source of failed projects:

- **Model** — the prescription. A blueprint: "what should the tasks be, how should they fit together?"
- **Enactment** — the actual process as it happens in the project, with all its inevitable deviations from the model.
- **Performance** — the set of measurements an observer collects from the enactment (time, cost, defect counts, ...).
- **Plan** — the expected future performance, given what the past enactment looked like.

The slogan: *good process → good product*. Studying processes is the core of software engineering precisely because the same set of process choices, repeated, produces predictable outcomes — and the wrong choices, repeated, produce predictable disasters.

#### The Solo Iterative Process (SIP)

SIP is the simplest meaningful process: one programmer repeats software changes one at a time. Each iteration of the SIP loop is the seven-phase software-change model from Lecture 2 (Initiation → Concept Location → Impact Analysis → Prefactoring → Actualization → Postfactoring → Conclusion).

Why should a *solo* programmer follow a predefined process at all, rather than just react? The lecture's answer: even solo programmers have to meet their obligations — fulfil promises, pay bills, plan the future, manage their own attention. SIP is the process that makes that possible. It also demonstrates characteristics shared by *all* iterative processes, which is why it is the right starting point pedagogically.

#### Measuring a process

The lecture spent significant time on measurement, which is what turns a process from a procedure into an improvable system.

**Time log** — start time, end time, interruptions, and the phase of work (Pri / Ini / CL / IA / Ref / Ex / Act / Base). Two derived quantities matter:

- **Total time** — wall-clock duration.
- **Clean time** — `end − start − interruptions`. The "American football" analogy: a quarter has 15 minutes of clean game time but takes much longer to play. Clean time is the honest measure of effort.

**Defect log** — for each defect: date found, location, description, origin phase (where it was introduced), date fixed. Tracking *origin* matters because it tells you which phase is producing defects, which is the only way to direct process improvements.

**Program size** — LOC, KLOC, MLOC. Inaccurate (different languages, different styles produce different LOC counts for the same logic), but the most commonly used measure. Function points, number of classes, number of methods are alternatives, all even less accurate.

**Defect density** — defects per KLOC. Industry rough benchmarks:
- Good-quality software: ~2.0 defects/KLOC.
- Avionics / NASA Space Shuttle: ~0.1 defects/KLOC — close to the cutting edge of what is achievable.

#### Planning

Planning is prediction of the future, which is hardest when the future is unprecedented. Two techniques mitigate this:

- **Analogy** — find a similar phase from a past project, use its measured duration as a starting estimate.
- **Decomposition** — break the change into phases, estimate each, sum them. Estimation errors on different phases tend to partly compensate.

The single most powerful planning idea in the lecture is **tasking**: deliberately split work so that *changes are made more alike*. Narrow the size range of changes ("epics" split into smaller stories) and the planning problem becomes easier because repeated similar work has a predictable mean duration. "Repetition is the mother of skill." Unique, unprecedented tasks are hard to plan; recurring tasks measured over time become close to deterministic.

The lecture's release-backlog tables (Plan after 0h / 100h / 285h / 405h / 475h) show this in action: the *remaining-effort* column tracks down toward 0 while the *total-effort* column drifts up as work uncovers new requirements. Both directions matter — knowing only one of the two leaves you blind to either schedule slippage or scope creep.

---

### 3.2 Team Iterative Processes

Most projects require more effort than a solo developer can supply. The lecture introduced three team-level processes, each suited to a different organisational scale.

#### Agile Iterative Process (AIP)

A small-to-medium team in which decisions are made by consensus and every developer has a single role: programmer. The loop is **Develop → React → Modify**, repeated.

Structure:
- **Product backlog** fed by user requests and the Product Manager.
- **Iteration backlog** drawn from the product backlog at iteration planning.
- **Iteration meeting** (usually weekly or biweekly) — all stakeholders attend, both technical and business sides; current state of the product is assessed and the next iteration is planned.
- **Daily meeting** — short stand-up to clarify ambiguities, surface obstacles, agree daily assignments, and serve as an early warning when something goes wrong.
- **Build** — automated build runs after every change.

#### Agile Manifesto

The manifesto (2001, 17 original authors) is the philosophical underpinning of AIP. It does not deny the value of the right-hand items; it only asserts the relative priority of the left-hand items:

| | over | |
|---|---|---|
| **Individuals and interactions** | over | processes and tools |
| **Working software** | over | comprehensive documentation |
| **Customer collaboration** | over | contract negotiation |
| **Responding to change** | over | following a plan |

#### Directed Iterative Process (DIP)

For larger teams. The process runs under the direction of managers, and developers have specialised roles:

- **Developers** — produce code.
- **Testers** — verify each new baseline as an independent role.
- **Architect** — guarantees that developers preserve architectural constraints; approves or disapproves commits.
- **Product manager** — strategic decisions about *what* the software does.
- **Process manager** — tactical decisions about *who* does what task and *when*.

Specialisation increases effectiveness — a dedicated tester finds defects that a developer testing their own code will miss — but at the cost of more coordination overhead. DIP scales to large teams and large systems where AIP would collapse under that coordination cost.

#### Centralised Iterative Process (CIP)

For the largest systems. CIP adds an explicit **permission to commit** gate, owned by architects and code owners, between the parallel software changes and the build. Each commit is *safeguarded* — reviewed against architectural rules before it enters the baseline.

CIP introduces **code ownership**: individual programmers specialise in specific parts of the code. Coordination across owners becomes the primary challenge, but the trade-off is that each part of the system has a clear accountable expert.

#### Open-source as a CIP variant

Open-source development is structurally a CIP: code is safeguarded by committers, ownership is well-defined per subsystem, and the contributor pool is wide with variable skills. The "permission to commit" gate is what protects quality when contributors range from one-off drive-by patches to long-term core maintainers. JHotDraw — both the original Smalltalk version and the Java fork we are working on — was developed this way.

---

### 3.3 Continuous Integration

Continuous Integration is the practice of merging every developer's working copy into a shared mainline several times a day. Each merge is verified by an automated build. The term was coined by Grady Booch (1991, *Object Oriented Design: With Applications*); the practice as we use it today comes from Extreme Programming (Kent Beck, 1999), which advocated integrating tens of times per day.

The full CI loop: **Commit → Source control → Initiate CI → Build → Test → Report → Development → Commit ...** It is structurally the DevOps figure-eight (Code → Build → Test → Release → Deploy → Operate → Measure → Plan → Code).

#### The five principles of CI

1. **Environments based on stability.** Dev → Test → Stage → Prod. Code is promoted to stricter environments only as its quality improves. The build server is the source of truth on what "works".
2. **Maintain a code repository.** The source repository is the *source of record*. The build server only ever builds from the repo — "it works on my machine" is not a valid argument; the build server settles disputes.
3. **Commit frequently, build every commit.** Small, functional commits. Unit tests on every commit. The agile principle: *if it hurts, do it more often* — difficult activities become straightforward through repetition, and the time between defect introduction and removal collapses.
4. **Make the build self-testing.** Three categories of test:
   - **Unit tests** — fast, no DB or filesystem, pinpoint the problem. The best signal for verifying a build.
   - **System tests** — end-to-end, minutes to hours, broader confidence.
   - **Static analysis** — Checkstyle, Findbugs, PMD, SONAR, Crap4j, DRY, Fortify, FXCop, CodeScanner.
   - **Coverage tools** — Cobertura, Emma, Clover, GCC/GCOV.
   The key empirical claim: an individual programmer is < 50 % efficient at finding their own bugs. Combining three or more orthogonal quality methods (inspection, testing, static analysis) drives defect removal past 90 %.
5. **Keep the build fast.** Maven + Java consume RAM; compile and unit test consume CPU; static analysis consumes a lot of CPU. The lecture's emphasis: *KEEP IT FAST*. A slow build erodes the "commit frequently" principle — developers start batching commits to avoid the wait, and the CI loop breaks.

#### Test frameworks mentioned

JUnit, NUnit, MSTest, Selenium, FitNesse.

#### Team ownership

A first-principles consequence of CI: *the team owns the code, not the individual*. If every commit must pass the team's CI gate, then any commit that breaks the build is the team's problem, and the social architecture follows from the technical architecture.

---

### 3.4 Impact Analysis

Impact analysis (IA) is the third phase of the phased software-change model. Its definition from the lecture: *"identifying the potential consequences of a change, or estimating what needs to be modified to accomplish a change."*

The output of IA is the **Estimated Impact Set** — the set of classes that the change is predicted to touch. The input is the **Initial Impact Set** — the classes found by concept location in the previous phase.

#### Class interactions

Two classes *interact* if they have something in common. The lecture distinguishes two kinds of interaction:

- **Dependency** — one class depends on the other; there is a contract between them.
- **Coordination** — they share data, scheduling, or invariants. The slide example is class `C` containing `A a; B b;` with `b.paint(a.get())` inside `C.foo()`: there is a *dataflow* between `a` and `b` even though neither directly depends on the other.

Crucially, an interaction is **bidirectional**: a change can propagate from `A` to `B` *or* from `B` to `A`. Concept location follows the directed dependency edges; impact analysis follows the *undirected* interaction edges. This is why the impact set is usually larger than the concept-location set.

The **Class Interaction Graph** `G = (X, I)` formalises this. `X` is the set of classes, `I` is the set of interactions (undirected edges). The **neighbourhood** of class `A` is `N(A) = { B | (A, B) ∈ I }` — the classes one edge away. IA walks outward from the initial impact set along these neighbourhood edges.

#### Propagating classes — the mailman analogy

The most useful intuition from the lecture: *propagating classes*. The slide's example: John has loaned money to Paul; he needs it back; he writes a letter; the mailman carries the letter from John to Paul; Paul must take a part-time job. The change *originated* with John and *terminated* with Paul, but it *propagated through* the mailman.

In code, a propagating class is one that the change passes through — its interface or behaviour stays the same, but a change to one of its neighbours forces re-examination of how it relates to that neighbour. Marking propagating classes correctly is what makes impact analysis converge: an Unchanged class stops the walk; a Propagating class continues it.

#### The IA algorithm — the four marks

Almost identical to concept location, but with one extra mark:

| Mark | Meaning |
|---|---|
| **Blank** | Never inspected, not scheduled. |
| **Next** | Scheduled for inspection. |
| **Changed** | Inspected; impacted by the change. |
| **Propagating** | Inspected; not changed itself, but propagates the change to its neighbours. |
| **Unchanged** | Inspected; not impacted; the walk stops here. |

Algorithm (from the slides' Figure 7.9):

```
1. Create interaction diagram; mark every class Blank.
2. Mark the classes from concept location as Changed.
3. While there are classes marked Next:
     Mark all Blank neighbours of Changed/Propagating classes as Next.
     Select one Next class. Inspect it. Mark it Changed, Propagating, or Unchanged.
4. When no Next classes remain, the impact set = Changed ∪ Propagating.
```

The lecture shows this as an interactive process in the *Interactive IA* slide: the computer maintains the diagram and tracks marks; the *programmer* makes the judgement call for each Next class. The split is the same as in interactive concept location — automation helps with bookkeeping but the semantic decision stays with the human.

#### Alternatives — the Fahrenheit-to-Celsius example

A subtle point from the lecture: the impact set is not unique. The example is a program that displays a temperature in Fahrenheit; the change request is to display Celsius. There are two concept locations:

- The sensor data conversion (deep in the model).
- The display formatter (at the UI boundary).

Either yields a valid impact set; the two are different. IA's real job is to *weigh the alternatives*. The criteria:

- **Required effort** — how much code does each alternative touch?
- **Clarity of the resulting code** — which version leaves the codebase easier to maintain?

These two often contradict each other: it is usually *easier* to patch the UI formatter (smaller diff) but *better* to keep all temperature calculations in one place (cleaner architecture). The lecture's framing: IA exposes the conflict between short-term and long-term goals so that the team can make the trade-off consciously rather than by accident.

---

### Reflection on Lecture 3

The thread tying the four sub-lectures together is *measurement-driven repetition*. SIP teaches the individual to record the past so the future is predictable. AIP, DIP and CIP scale that loop to teams of different sizes. CI provides the automated machinery that makes every commit a measurement point. Impact analysis, finally, is the moment in each change where measurement and decision-making meet — the place where the data from past changes (concept-location effort, prefactoring time, defect rates per package) feeds back into the *choice* of how to make the current change.

For the JHotDraw portfolio, this lecture block is what justifies the work in Lab 3: the CI pipeline is the team-level measurement infrastructure; the impact-analysis table is the artefact that turns a vague "this change touches a lot of code" intuition into a numbered list of packages.

---

## Lab 3 — Continuous Integration and Impact Analysis

Lab 3 was delivered as two sequential lab sessions:

- **Lab 3a (CILab)** — Set up a CI pipeline that automatically builds the project on every pull request to `develop` and runs the test suite.
- **Lab 3b (AnalysisLab)** — Apply impact analysis to the Group / Ungroup feature, producing a `Package | # of classes | Comments` table.

I treat them as one Lab 3 in this portfolio because they are sequential phases of the phased model — Concept Location (Lab 2) → Impact Analysis (Lab 3b), with CI (Lab 3a) running underneath as the verification scaffolding for every later change.

---

### Lab 3a — Continuous Integration Pipeline (CILab)

#### State of the CI pipeline

The team already has a CI workflow in place from earlier work on the repository. Rather than recreate it, this lab documents what is there and why each piece is needed.

**Workflow file:** [`.github/workflows/maven.yml`](.github/workflows/maven.yml)

```yaml
name: Java CI with Maven

on:
  pull_request:
    branches: [ "develop" ]
  workflow_dispatch:

permissions:
  contents: read
  actions: write
  packages: read

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v4
    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'
        cache: maven
    - name: Build and test with Maven
      env:
        GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
        GITHUB_ACTOR: ${{ github.actor }}
      run: mvn -B -s .maven-settings.xml test --file pom.xml
```

**Maven settings file:** [`.maven-settings.xml`](.maven-settings.xml)

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" ...>
  <servers>
    <server>
      <id>github</id>
      <username>${env.GITHUB_ACTOR}</username>
      <password>${env.GITHUB_TOKEN}</password>
    </server>
  </servers>
</settings>
```

#### Mapping the pipeline to the five CI principles

| Principle | How the workflow satisfies it |
|---|---|
| **1. Environments based on stability** | The pipeline targets pull-requests against `develop`. `develop` is the team's integration branch; main / release branches are stricter environments downstream. Personal feature branches are the loosest environment. |
| **2. Maintain a code repository** | `actions/checkout@v4` pulls a fresh copy from the GitHub repo for every build. The build server cannot use a developer's local machine state — the repo is the source of record. |
| **3. Commit frequently, build every commit** | The `pull_request` trigger ensures *every* pull-request build is gated by the workflow. `workflow_dispatch` adds a manual rerun button for re-checking flaky builds without a new commit. |
| **4. Self-testing build** | `mvn -B -s .maven-settings.xml test` runs `mvn test` — Maven's `test` lifecycle phase compiles every module and runs every JUnit test discovered by the Surefire plugin. |
| **5. Keep the build fast** | `cache: maven` caches the `~/.m2/repository` directory across runs. Without this, every CI run would re-download all transitive dependencies — saving roughly several minutes per run on a project with JHotDraw's dependency tree. |

#### Notes on specific design decisions

- **JDK 17, not JDK 11.** The lab text mentions Maven 3.8.x with JDK 11. The team upgraded to JDK 17 (Temurin distribution) because the GitHub-hosted runners default to it and the Java 8 bytecode target in `pom.xml` (`maven.compiler.source = 1.8`) is compatible with any JDK ≥ 8. The local development environment still works with JDK 11 or higher.

- **`-B` (batch mode).** Disables interactive prompts and ANSI colour codes; produces cleaner log output for CI without affecting build results.

- **`-s .maven-settings.xml`.** Tells Maven to use the repository-local settings file so it can authenticate to the GitHub Package Registry (referenced in the root `pom.xml`). Without this flag the build fails at the dependency-resolution step.

- **GITHUB_TOKEN / GITHUB_ACTOR.** Injected by GitHub Actions automatically. The `.maven-settings.xml` reads them as `${env.GITHUB_TOKEN}` / `${env.GITHUB_ACTOR}` and supplies them as credentials to the `github` server entry, which the root `pom.xml` declares as the source for `org.jhotdraw:MavenRepository`. The token has `packages: read` scope, granted in the workflow `permissions:` block.

- **`packages: read` permission.** Restricting to read prevents the workflow from accidentally publishing new versions; it can only consume the registry.

- **Branch policy.** Builds only run on PRs to `develop` (not on every push to every branch). This matches the GitHub-flow + AIP model: feature branches accumulate untested work, but the integration into `develop` is the moment quality is enforced.

#### What is *not* in the pipeline (and why this matters)

Mapping back to the lecture's catalogue:

- **No static analysis.** Checkstyle, Findbugs/SpotBugs, PMD, SONAR are not wired in. For a course portfolio this is acceptable; for production code this would be the next CI enhancement.
- **No code-coverage measurement.** Cobertura / JaCoCo are not configured. Adding `org.jacoco:jacoco-maven-plugin` and a `report` goal would let the pipeline track coverage trends per PR.
- **No system / end-to-end tests.** Only unit tests run. Given that JHotDraw is a Swing GUI app, system testing would require a headless display (`xvfb`) — feasible on the runners but out of scope here.
- **Only two test files in the entire codebase** (`find ... -name "*Test*.java" → 2`). This is the most important finding: the CI pipeline is correctly configured, but the *self-testing build* principle is only as strong as the test suite itself. For my own portfolio work, adding tests around the Group / Ungroup feature in Lab 5 (Actualization / Verification) will provide more value than tuning the pipeline configuration.

#### Verification

I verified the pipeline works by reviewing the recent commit history on `develop`:

- `1e532505` — added maven settings and updated yml to execute tests automatically.
- `5b8e93a4` — added manual rerun feature for CI pipeline.
- `ec60e9fc` — added a V2 test.
- `5c35a287` — added read & write permissions.
- `d396befe` — added pull request trigger for Maven CI workflow.

The pipeline has run on every PR that has been merged into `develop` for the last several PRs, which is the only meaningful "does it work?" test.

---

### Lab 3b — Impact Analysis on Group / Ungroup (AnalysisLab)

#### Methodology

I followed Figure 7.9 from the slides exactly: start with the classes located in Lab 2 marked **Changed**, walk outward to all interaction-graph neighbours marked **Next**, inspect each one in turn, and mark it **Changed**, **Propagating**, or **Unchanged**. The walk stops when no class is marked **Next**.

Concretely:

1. **Initial impact set** = the 8 core classes from Lab 2's concept-location table.
2. For each Changed class, I read the file, listed its imports, and identified which neighbours represent a real interaction (not just a JDK or utility type that is incidental).
3. For each subclass / implementer of an interface in the initial set, I asked: "If the interface or base class changes, does this class need to be re-inspected?" If yes, mark Propagating.
4. For each caller / wirer of the Action classes, I marked Propagating because the change request might require updating call-sites if the action constructor signature evolves.

I deliberately *did not* expand into JDK classes (`javax.swing.undo.*`, `java.util.LinkedList`, `java.awt.geom.*`) — they are external boundaries that no plausible Group/Ungroup change would alter.

#### Impact analysis trace (mark history)

| Step | Class | Mark | Reason |
|---|---|---|---|
| 0 (initial) | `GroupAction`, `UngroupAction`, `CompositeFigure`, `AbstractCompositeFigure`, `GroupFigure`, `Figure`, `DrawingView`, `Drawing` | Changed | From Lab 2 concept location. |
| 1 | `AbstractSelectedAction` | Unchanged | Generic base for selection-aware actions; not group-specific. |
| 2 | `GraphicalCompositeFigure`, `AbstractAttributedCompositeFigure`, `QuadTreeCompositeFigure`, `LabeledLineConnectionFigure`, `ListFigure` | Propagating | All extend `AbstractCompositeFigure`; would need re-inspection if base class changes. |
| 3 | `AbstractDrawing`, `QuadTreeDrawing` | Propagating | Implementations of `Drawing` used by Group/Ungroup's `basicAddAll` / `indexOf` / `remove` calls. |
| 4 | `AbstractDrawingView`, `DefaultDrawingView`, `DefaultDrawingViewTransferHandler` | Propagating | `DrawingView` implementations; `DefaultDrawingViewTransferHandler` also constructs `GroupFigure` for clipboard paste. |
| 5 | `DrawingEditor` | Propagating | Owns the active `DrawingView`; passed to `GroupAction` constructor. |
| 6 | `CompositeFigureListener`, `CompositeFigureEvent`, `CompositeFigureEdit` | Propagating | Observer pattern around composite figures; events fire when children are added or removed during group/ungroup. |
| 7 | `FigureListener`, `FigureEvent`, `FigureAdapter` | Unchanged | More general figure events; not specific to composition. |
| 8 | `Handle`, `DragHandle`, `BoundsOutlineHandle` | Unchanged | Selection handles operate on a single figure at a time; not in the group/ungroup flow. |
| 9 | `ButtonFactory` (jhotdraw-gui) | Changed | Wires `new GroupAction(editor)` and `new UngroupAction(editor)` into the Edit menu of every standard editor. Any signature change to either action would change this file. |
| 10 | `DrawingPanel` (Draw sample), `NetPanel`, `PertPanel`, `ODGDrawingPanel` | Propagating | Each sample app independently registers GroupAction/UngroupAction in its own panel. |
| 11 | `ODGApplicationModel` | Propagating | Constructs `new GroupAction(editor, new ODGGroupFigure())` — uses the prototype-injection overload. |
| 12 | `SVGGroupFigure`, `ODGGroupFigure` | Propagating | Domain-specific composite-figure variants that play the role of the cloned prototype in the SVG and ODG samples. |
| 13 | `CombineAction`, `SplitAction` (ODG) | Unchanged | ODG-specific *path*-combining actions, conceptually unrelated to figure grouping despite the superficially similar name. |
| 14 | `ResourceBundleUtil`, `Labels.properties` | Unchanged | Localisation infrastructure; only consumed for menu labels. |
| 15 | `DOMInput`, `DOMStorable` (jhotdraw-xml) | Unchanged | Used for XML serialisation of figures; not group-specific. |
| End | — | No more Next classes. Stop. | |

#### Impact set summary

- **Changed:** 9 classes (8 initial + ButtonFactory).
- **Propagating:** 18 classes.
- **Unchanged:** 11 classes inspected and rejected.
- **Total estimated impact set:** **27 classes** (Changed ∪ Propagating).

This is a tight, well-bounded impact set for a real feature in a 9-module Maven project. The reason it stays small is exactly the architectural property predicted in Lecture 2.4: JHotDraw's heavy use of GoF patterns (Composite, Prototype, Command, Factory) localises change.

#### Table 1 — Packages visited during impact analysis

This is the deliverable required by the AnalysisLab.

| Package name | # of classes | Comments |
|---|---|---|
| [`org.jhotdraw.draw.action`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/) | 3 | The controller layer for menu commands. Contains the Changed classes `GroupAction` and `UngroupAction`, plus the Unchanged base `AbstractSelectedAction`. This is where the user's "Group" click first enters the program, so any change to behaviour (e.g. allow grouping a single figure into a singleton group) lands here first. |
| [`org.jhotdraw.draw.figure`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/) | 7 | The model layer for figures. Holds the Changed core (`Figure`, `CompositeFigure`, `AbstractCompositeFigure`, `GroupFigure`) and the Propagating subclasses that inherit Composite behaviour (`GraphicalCompositeFigure`, `AbstractAttributedCompositeFigure`, `QuadTreeCompositeFigure`, `LabeledLineConnectionFigure`, `ListFigure`). Counted with sub-package overlap removed. This is the most concept-dense package in the entire feature — the Composite-pattern intension lives here. |
| [`org.jhotdraw.draw`](jhotdraw-core/src/main/java/org/jhotdraw/draw/) | 6 | The drawing-model container layer. Holds the Changed `Drawing` interface and `DrawingView` controller-side selection holder, plus the Propagating concrete implementations (`AbstractDrawing`, `QuadTreeDrawing`, `AbstractDrawingView`, `DefaultDrawingView`, `DefaultDrawingViewTransferHandler`) and `DrawingEditor`. Group/Ungroup talks to this package every time it adds, removes, or re-indexes a top-level figure in the drawing. |
| [`org.jhotdraw.draw.event`](jhotdraw-core/src/main/java/org/jhotdraw/draw/event/) | 3 | Observer-pattern types specific to composite figures: `CompositeFigureListener`, `CompositeFigureEvent`, `CompositeFigureEdit`. Marked Propagating because every `group.basicAdd(figure)` call in `GroupAction.groupFigures` fires events through these types. The more general `FigureListener` / `FigureEvent` family was inspected and marked Unchanged. |
| [`org.jhotdraw.gui.action`](jhotdraw-gui/src/main/java/org/jhotdraw/gui/action/) | 1 | The cross-module wiring layer. `ButtonFactory.addEditMenuItems(...)` constructs `new GroupAction(editor)` and `new UngroupAction(editor)` and inserts them into the standard Edit menu used by every sample editor. Any signature change to either action constructor lands here. |
| [`org.jhotdraw.samples.draw`](jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/draw/) | 1 | The general Draw sample's panel (`DrawingPanel`) independently registers the actions on its toolbar. Propagating. |
| [`org.jhotdraw.samples.net`](jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/net/) | 1 | The network-diagram sample (`NetPanel`). Propagating for the same reason as `DrawingPanel`. |
| [`org.jhotdraw.samples.pert`](jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/pert/) | 1 | The PERT-chart sample (`PertPanel`). Propagating, same shape as the previous two. |
| [`org.jhotdraw.samples.odg`](jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/odg/) | 2 | ODG application model + drawing panel. `ODGApplicationModel` is the only place in the codebase that exercises the second `GroupAction` constructor (with an explicit `CompositeFigure prototype`), so it is the canonical example of how the Prototype pattern is used in practice. |
| [`org.jhotdraw.samples.odg.figures`](jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/odg/figures/) | 1 | `ODGGroupFigure` — the ODG-specific variant prototype. Propagating: it inherits its grouping behaviour from `GroupFigure` and would need re-verification if `GroupFigure` changed. |
| [`org.jhotdraw.samples.svg.figures`](jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/svg/figures/) | 1 | `SVGGroupFigure` — the SVG-specific variant prototype, twin to `ODGGroupFigure`. Propagating. |

**Totals: 11 packages, 27 classes in the impact set.**

#### Findings

**1. Inheritance amplifies propagation.**
The single biggest source of Propagating classes is the inheritance chain rooted at `AbstractCompositeFigure`. A change to that base class forces re-inspection of `GraphicalCompositeFigure`, `AbstractAttributedCompositeFigure`, `QuadTreeCompositeFigure`, `LabeledLineConnectionFigure`, and `ListFigure` — five propagating classes from one parent. This is exactly the cost the Lecture 2.4 principle "favour composition over class inheritance" warns about, but in this case the inheritance is justified by the Composite pattern's structural requirement.

**2. The Prototype injection point keeps sample applications independent.**
The `ODGApplicationModel` line `a.add(new GroupAction(editor, new ODGGroupFigure()))` is the entire mechanism by which ODG plugs its own group-figure type into the standard `GroupAction`. Without this constructor overload, the sample apps would each need to subclass `GroupAction` — adding probably 5 more Changed classes to the impact set. This is a direct empirical payoff from the Prototype pattern: the wiring layer pays a small Propagating cost but the core stays Changed-only.

**3. The Observer pattern produces "free" propagation.**
`CompositeFigureListener` / `CompositeFigureEvent` are propagating not because they need to be modified, but because they fire on every `group.basicAdd(...)` call. If the change request involved (say) batching the events for a multi-figure group operation, these classes *would* move from Propagating to Changed. This is the impact analysis equivalent of the Fahrenheit-vs-Celsius alternative: the same change request has two reasonable code locations (the action vs. the event firing), and IA exposes the trade-off.

**4. Test impact is tiny — because there is almost no test coverage.**
No file under any test source root mentions Group or Ungroup. There are exactly two test files in the entire project. This is good news for the size of the *test* impact set (effectively zero), and very bad news for the *risk* of the actual change. The Lab 5 verification step will have to write tests, not just run them.

**5. The change is contained to two modules.**
The impact set spans `jhotdraw-core`, `jhotdraw-gui`, and `jhotdraw-samples/jhotdraw-samples-misc`. It does *not* touch `jhotdraw-api`, `jhotdraw-app`, `jhotdraw-actions`, `jhotdraw-datatransfer`, `jhotdraw-utils`, or `jhotdraw-xml`. From a build-perspective, this means `mvn install -pl jhotdraw-core,jhotdraw-gui,jhotdraw-samples/jhotdraw-samples-misc -am` would suffice to rebuild everything affected.

**6. Alternative impact sets exist.**
Following the Lecture 3.4 "alternatives" idea: I could have located the concept at the **Edit menu wiring** instead of at `GroupAction`. That would have produced a very different impact set — `ButtonFactory` as Changed, every sample panel as Propagating, and `GroupAction` itself as Unchanged. The reason I prefer the controller-as-starting-point alternative: it gives a *smaller* impact set, and the *clarity criterion* favours keeping behaviour in the Action class rather than spreading it across wiring.

#### Summary

Lab 3b produced a 27-class estimated impact set across 11 packages, structured as 9 Changed + 18 Propagating. The size of the impact set, the locality to just three Maven modules, and the clean alignment with the Composite / Prototype / Command pattern structure all confirm the prediction from Lecture 2.4: a framework that was designed with GoF patterns in mind localises change effectively.

The deliverable for Lab 4 (Prefactoring) is now bounded: among the 9 Changed classes, the obvious prefactoring target is the redundant code between `GroupAction.groupFigures` / `ungroupFigures` and the `// XXX - This code is redundant with UngroupAction` comment left at [`GroupAction.java:148`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java#L148). Resolving that redundancy *before* adding any new feature code is precisely what the Prefactoring phase is for.

---

## Lecture 4 — Refactoring and Refactoring to Patterns

The fourth lecture block paired two related sub-lectures: a classical refactoring lecture grounded in Fowler's *Refactoring: Improving the Design of Existing Code* (1999), and a higher-level lecture on Kerievsky's *Refactoring to Patterns* (2005). Together they convert the previous lectures' diagnoses (concept location, impact analysis, code decay) into a *therapy*: a vocabulary of small, behaviour-preserving transformations that can be sequenced into larger, pattern-directed restructurings.

---

### 4.1 What Refactoring Is

Fowler's definition: *a change made to the internal structure of software to make it easier to understand and cheaper to modify, without changing its observable behaviour.* Two ideas are doing the work in this sentence:

- **Behaviour preservation** — every refactoring is a *safe* transformation. Tests written before the refactoring should still pass afterwards. This is what distinguishes refactoring from rewriting.
- **Small steps** — a refactoring is one atomic transformation. Larger restructurings are *sequences* of refactorings, with the program in a working state after each step. The system is therefore never in a half-broken state during the work.

The discipline matters because the alternative — large, risky restructurings — has empirically a much higher chance of introducing defects than steady small steps with verification between them. This is the same idea as continuous integration from Lecture 3, applied at the granularity of one editor save.

#### Why and when to refactor

Why: it improves design, makes the software easier to understand, helps find bugs (by exposing duplicated or tangled logic), and ultimately makes future programming faster. When: on the *Rule of Three* (the third time you do something similar, refactor), when you add a function, when you fix a bug, and when you do a code review. The phased model from Lecture 2 puts refactoring in two specific phases: **Prefactoring** (before actualization, to minimise the impact set) and **Postfactoring** (after actualization, to clean up anti-patterns introduced by the new code).

---

### 4.2 The 22 Symptoms of Bad Code

Fowler's catalogue of *code smells* is the lecture's diagnostic vocabulary. The lecture grouped them in three slides; the table below summarises the full set with a one-line meaning each. The marker [F] indicates a smell that is also the focus of Kerievsky's high-level catalog.

| # | Smell | One-line meaning |
|---|---|---|
| 1 | **Duplicated Code** [F] | The same code structure in more than one place. |
| 2 | **Long Method** [F] | A method that is too long to grasp at a glance. |
| 3 | **Large Class** [F] | A class with too many responsibilities. |
| 4 | **Long Parameter List** | A method that takes too many arguments. |
| 5 | **Divergent Change** | One class changed in many different ways for many different reasons. |
| 6 | **Shotgun Surgery** | One kind of change forces many small changes across many classes. |
| 7 | **Feature Envy** | A method that is more interested in another class than the one it lives in. |
| 8 | **Data Clumps** | The same bunch of data fields appearing together in many places. |
| 9 | **Primitive Obsession** [F] | Excessive use of primitives instead of small classes. |
| 10 | **Switch Statements** [F] | Type-codes dispatched by `switch` / `if`-chains. |
| 11 | **Parallel Inheritance Hierarchies** | Every subclass of A needs a matching subclass of B. |
| 12 | **Lazy Class** [F] | A class that doesn't earn its keep. |
| 13 | **Speculative Generality** | "We might need this someday" code. |
| 14 | **Temporary Field** | A field set only in some circumstances; null otherwise. |
| 15 | **Message Chains** | `a.b().c().d().e()` — transitive visibility. |
| 16 | **Middle Man** | A class that just delegates to another. |
| 17 | **Inappropriate Intimacy** | Two classes that know too much about each other's internals. |
| 18 | **Alternative Classes with Different Interfaces** [F] | Two classes do the same thing but expose it differently. |
| 19 | **Incomplete Library Class** | A library class missing a method you need. |
| 20 | **Data Class** | A class with fields and accessors, no behaviour. |
| 21 | **Refused Bequest** | A subclass that doesn't use most of what it inherits. |
| 22 | **Comments** | Comments used to compensate for unreadable code. |

The diagnostic value is not in memorising the list but in having a *named* vocabulary. Once a developer can point at a piece of code and say "that is a Long Method with Feature Envy and a Comment used as deodorant", the conversation about how to fix it becomes precise.

---

### 4.3 The Seven Categories of Refactorings

Fowler organises his catalogue of ~70 refactorings into seven categories, each addressing a cluster of smells:

| Category | What it does | Representative refactorings |
|---|---|---|
| **Composing Methods** | Package code properly. Mostly attacks Long Method and Duplicated Code. | Extract Method (110), Inline Method (117), Replace Method with Method Object (135). |
| **Moving Features Between Objects** | Reassign responsibilities to the class that should own them. Attacks Feature Envy, Large Class, Lazy Class. | Move Method (142), Move Field (146), Extract Class (149), Inline Class (154), Hide Delegate (157), Remove Middle Man (160). |
| **Organizing Data** | Make data easier to work with — encapsulation, replacing primitives with objects, replacing magic numbers. Attacks Primitive Obsession, Data Class. | Self Encapsulate Field (171), Replace Data Value with Object (175), Replace Array with Object (186), Encapsulate Field (206), Encapsulate Collection (208), Replace Subclass with Fields (232). |
| **Simplifying Conditional Expressions** | Make conditional logic less error-prone. Attacks Switch Statements, conditional complexity. | Decompose Conditional (238), Consolidate Conditional Expression (240), Replace Nested Conditional with Guard Clauses (250), Replace Conditional with Polymorphism (255), Introduce Null Object (260). |
| **Making Method Calls Simpler** | Improve method-level interfaces. | Separate Query from Modifier (279), Parameterize Method (283), Replace Parameter with Method (292), Introduce Parameter Object (295). |
| **Dealing with Generalization** | Move features around an inheritance hierarchy. Attacks Refused Bequest, Parallel Inheritance, Inappropriate Intimacy. | Pull Up Constructor Body (325), Extract Subclass (330), Extract Superclass (336), Extract Interface (341), Collapse Hierarchy (344), Form Template Method (345), Replace Inheritance with Delegation (352). |
| **Big Refactorings** | Large-scale restructurings that take many sessions. | Tease Apart Inheritance, Convert Procedural Design to Objects, Separate Domain from Presentation, Extract Hierarchy. |

Each refactoring is documented in the same shape: a one-sentence *motivation* (the smell it addresses), a *mechanics* section (the safe step-by-step procedure), and an *example*. This is the same pattern used by the GoF design-pattern catalogue — and Kerievsky's contribution is to *connect* the two catalogues.

---

### 4.4 Refactoring to Patterns (Kerievsky)

Kerievsky's argument is that design patterns are the *destinations* you reach by composing many small refactorings together. He frames the relationship with an analogy:

> Design patterns are the word problems of the programming world; refactoring is its algebra.

In algebra class you first learn the manipulations (add to both sides, commute, factor), and only then do you solve a word problem. Equivalent in software: first learn the small refactorings, *then* learn which sequences of them lead to which design patterns. This reframes design patterns away from "shapes to memorise" toward "endpoints of refactoring journeys".

#### Three directions of refactoring relative to a pattern

For every pattern, Kerievsky's catalog identifies three directions of motion:

- **To** the pattern — code becomes more pattern-like (e.g. *Replace Conditional Logic with Strategy* moves code toward Strategy).
- **Towards** the pattern — applied repeatedly, the code edges closer to the pattern but stops short of full structural commitment.
- **Away** from the pattern — sometimes a pattern is *over-engineered* for the problem at hand and should be removed (e.g. *Inline Singleton* moves away from Singleton).

The "Away" direction is the most under-taught idea in the pattern community: patterns are not always the right destination. Speculative Generality is the smell that justifies refactoring away.

#### Code smell → refactoring mapping (high-level catalog)

Kerievsky tabulates a direct map from smells to candidate high-level refactorings. The most operationally useful rows for the Group / Ungroup work in Lab 4:

| Smell | Candidate refactorings |
|---|---|
| **Conditional Complexity** | Replace Conditional Logic with Strategy (129), Move Embellishment to Decorator (144), Replace State-Altering Conditionals with State (166), Introduce Null Object (301). |
| **Duplicated Code** | Form Template Method (205), Introduce Polymorphic Creation with Factory Method (88), Chain Constructors (340), Replace One/Many Distinctions with Composite (224), Extract Composite (214), Unify Interfaces with Adapter (247), Introduce Null Object (301). |
| **Long Method** | Compose Method (123), Move Accumulation to Collecting Parameter (313), Replace Conditional Dispatcher with Command (191), Move Accumulation to Visitor (320), Replace Conditional Logic with Strategy (129). |
| **Switch Statements** | Replace Conditional Dispatcher with Command (191), Move Accumulation to Visitor (320). |
| **Primitive Obsession** | Replace Type Code with Class (286), Replace State-Altering Conditionals with State (166), Replace Conditional Logic with Strategy (129), Replace Implicit Tree with Composite (178), Replace Implicit Language with Interpreter (269), Move Embellishment to Decorator (144), Encapsulate Composite with Builder (96). |

The table is *not* a prescription — for any given smell there are multiple candidate destinations, and the choice depends on context. The judgement is the engineer's job; the table only narrows the search space.

#### Two meta-patterns of refactoring

Kerievsky names two universal heuristics that should govern *how* a refactoring is performed:

- **Automation First.** "Manual refactorings are dirt roads. Automated refactorings are highways. When deciding how to refactor, look first for the highways." IDE-supported refactorings (Extract Method, Rename Symbol, Move Method) are mechanically safe; doing them by hand reintroduces the risk of breaking behaviour. Modern IDEs (IntelliJ, Eclipse, VS Code with Language Server) automate the basic Fowler refactorings.
- **Client First.** "We like to refactor smelly code — yet we may only see a manual way to refactor. To find a simpler, automated way of refactoring, consider starting with a client of the smelly code." Sometimes the cleaner refactoring path begins by modifying a *caller* of the smelly code rather than the smelly code itself. The pressure on the client's interface then suggests the right move for the implementation.

---

### Reflection on Lecture 4

Putting the three previous lectures together with this one yields the actual *operating model* the rest of the course assumes:

1. A change request enters the backlog (Lecture 2).
2. Concept location resolves the names in the change request to specific classes (Lecture 2).
3. Impact analysis estimates the propagation of the change across the codebase (Lecture 3).
4. Prefactoring (Lecture 4) reduces the impact set *before* writing new code, by removing duplication, simplifying long methods, and re-balancing responsibilities. The goal is that when actualization happens, fewer classes are touched.
5. Continuous integration (Lecture 3) verifies behaviour preservation at every refactoring step.

The Kerievsky framing is what makes step 4 disciplined rather than ad-hoc. Without it, "refactoring" risks being a euphemism for "I rewrote a chunk of code I didn't like the look of". With it, every transformation has a name from the catalog, a mechanical procedure, a motivating smell, and a pattern-destination it heads toward.

For Lab 4, the most operationally useful idea from this lecture is Kerievsky's smell → refactoring table combined with the *Automation First* heuristic: identify the smell from Fowler's 22-item list, pick the refactoring from the table, and prefer transformations the IDE can perform mechanically. Behaviour-preservation then comes essentially for free.

---

## Lab 4 — Refactoring Lab: Group / Ungroup Prefactoring

Lab 4 implements the *Prefactoring* phase of the phased model on the Group / Ungroup feature located in Lab 2 and impact-analysed in Lab 3. The goal is to clean up the smells in the existing code *before* using this feature as a base for further changes, so that when actualization happens later in the course the change touches fewer classes and the new code does not amplify pre-existing problems.

---

### Methodology and Tooling

I identified smells through manual reading of the classes in the Changed set from [Lab 3b](#lab-3b--impact-analysis-on-group--ungroup-analysislab), guided by Fowler's 22-smell catalog from Lecture 4 and validated by IDE diagnostics. The IDE flagged the stale `// XXX - This code is redundant with UngroupAction` comment as an Information-level diagnostic during the refactoring — the live equivalent of what SonarLint's `S1135` rule (Track uses of "TODO" tags) would surface in a static-analysis report. The other smells (Long Method, Duplicated Code, Dead Code) are not detected by a single rule and required manual inspection — exactly the kind of higher-order judgement that the lecture's smell catalog trains.

The work was done on the `alex` branch, in a single working session, with `mvn compile` between every change and `mvn test` at the end. Every refactoring was small, named (from Fowler / Kerievsky), and behaviour-preserving.

---

### Smells Identified

Mapping the code I inspected onto Fowler's catalogue (Lecture 4.2):

| # | Smell (Fowler) | Location | Evidence |
|---|---|---|---|
| 1 | **Long Method** | [`GroupAction.actionPerformed`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java) — was 67 lines in one method | Two top-level branches each constructed a 30-line anonymous `AbstractUndoableEdit`. Reading it required holding both branches in scope simultaneously. |
| 2 | **Duplicated Code** | The two branches of `actionPerformed` and the four `ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels")` lookups | The two anonymous `AbstractUndoableEdit` subclasses were structurally identical: `getPresentationName()` body differed only by resource-bundle key; `redo()` / `undo()` differed only by which operation method to call. The bundle lookup itself was repeated four times across the file. |
| 3 | **Comments used as deodorant** | [`GroupAction.java`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java) old line 148: `// XXX - This code is redundant with UngroupAction` | The author left an honest TODO in 1996 acknowledging the smell. Comments compensating for unreadable / unrefactored code is smell #22 in Fowler's list. The comment was also *stale*: with `CombineAction` overriding `ungroupFigures` in `jhotdraw-samples-misc`, that method is now a genuine Template Method hook, not redundancy. |
| 4 | **Temporary Field / Dead Code** | [`UngroupAction`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/UngroupAction.java) old line 28: `private CompositeFigure prototype;` | Shadow field of the same name as the parent's; never assigned, never read. Compiles, passes tests, but pure noise — a Lazy Field rather than a Lazy Class. |
| 5 | **Switch on type code (boolean)** | The `private boolean isGroupingAction` field and its `if (isGroupingAction) … else …` dispatch in `updateEnabledState` and `actionPerformed` | This is the classical *type code as a boolean* pattern from Kerievsky's smell catalog (Conditional Complexity). It begs for *Replace Conditional with Polymorphism* — but as discussed in *Deferred Refactorings* below, applying it would break the API contract used by `CombineAction`. |

---

### Strategy

The strategy was chosen to maximise behaviour-preservation while still attacking the three highest-value smells (Long Method, Duplicated Code, Dead Code). Three constraints shaped the choice:

1. **`CombineAction` is a real subclass with real overrides.** It overrides `groupFigures`, `ungroupFigures`, and `canGroup` in [`jhotdraw-samples-misc/.../CombineAction.java`](jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/odg/action/CombineAction.java). Those three methods are therefore part of the *public* contract of `GroupAction` — they are the hooks of an implicit Template Method pattern with `actionPerformed` as the template. Any refactoring must preserve their signatures and visibility.
2. **There are essentially no tests.** Only two test files exist in the entire repository, neither touching the action layer (finding from [Lab 3](#lab-3--continuous-integration-and-impact-analysis)). Without a safety net, I had to limit scope to *mechanically safe* refactorings that the IDE can perform with high confidence (Extract Method, Remove Dead Code), and avoid risky structural changes (Collapse Hierarchy, Replace Inheritance with Delegation, Replace Conditional with Polymorphism).
3. **Automation First** (Kerievsky's heuristic from Lecture 4.4). Every refactoring I applied is one a modern IDE can perform mechanically.

The plan was therefore: clean up the *internal* shape of `GroupAction` and remove obvious dead code in `UngroupAction`, leaving the *external* interface (and the inheritance / Template-Method relationship with `CombineAction`) untouched. The bigger structural moves are documented in *Deferred Refactorings* below as the natural next step for a future iteration, gated on first adding a proper test suite.

---

### Refactorings Applied

Three refactorings from the Fowler / Kerievsky catalogues, applied in the order shown.

#### Refactoring 1 — Compose Method (Kerievsky 123 / Fowler "Extract Method", Fowler 110)

**Smell:** Long Method on [`GroupAction.actionPerformed`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java).

**Purpose (from Kerievsky 123):** *"Transform the logic into a small number of intention-revealing steps at the same level of detail."* The body of `actionPerformed` was operating at two levels of detail simultaneously — top-level dispatch on a flag, plus 30 lines of undo-edit construction in each branch.

**Strategy:** Extract each of the two branches into a private method named after the *intent* of that branch (`performGroup`, `performUngroup`), so `actionPerformed` becomes a 5-line dispatcher whose body is one level of detail throughout. The branches' local variables become method-local — the closure semantics of the anonymous `UndoableEdit` are preserved because the captured `final` references move with the method body.

**Before:**

```java
@Override
public void actionPerformed(java.awt.event.ActionEvent e) {
    if (isGroupingAction) {
        if (canGroup()) {
            final DrawingView view = getView();
            final LinkedList<Figure> ungroupedFigures = new LinkedList<>(view.getSelectedFigures());
            final CompositeFigure group = (CompositeFigure) prototype.clone();
            UndoableEdit edit = new AbstractUndoableEdit() {
                // ... 30 lines of redo/undo/getPresentationName/addEdit ...
            };
            groupFigures(view, group, ungroupedFigures);
            fireUndoableEditHappened(edit);
        }
    } else {
        if (canUngroup()) {
            // ... another 30 lines, structurally identical ...
        }
    }
}
```

**After:**

```java
@Override
public void actionPerformed(java.awt.event.ActionEvent e) {
    if (isGroupingAction) {
        performGroup();
    } else {
        performUngroup();
    }
}

private void performGroup() {
    if (!canGroup()) {
        return;
    }
    // ... group flow, one level of detail ...
}

private void performUngroup() {
    if (!canUngroup()) {
        return;
    }
    // ... ungroup flow, one level of detail ...
}
```

Two side-benefits of the extraction:

- The nested `if (isGroupingAction) { if (canGroup()) { ... } }` becomes the cleaner *guard clause* shape (Fowler 250, *Replace Nested Conditional with Guard Clauses*) inside each performer.
- The total file is now one screen shorter when reading `actionPerformed` (5 lines vs. 67), and the two performer methods stand side by side so their structural similarity is visible — which sets up Refactoring 2.

#### Refactoring 2 — Extract Method on the Resource Bundle Lookup (Fowler 110)

**Smell:** Duplicated Code. The expression `ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels")` appeared four times in the file: once in the constructor, twice in the two anonymous `getPresentationName()` overrides, and the original bundle lookup. A change to the bundle name (a realistic refactoring, e.g. moving the bundle out of `org.jhotdraw.draw`) would require four edits.

**Purpose:** Introduce a single point of truth for the bundle reference; let the compiler enforce that all four sites agree.

**Implementation:** A `private static ResourceBundleUtil getLabels()` helper. Static because it has no instance state; private because it is implementation detail.

```java
private static ResourceBundleUtil getLabels() {
    return ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
}
```

All four call sites now read `getLabels().configureAction(this, ID)` or `getLabels().getString("edit.groupSelection.text")`. The bundle name appears exactly once in the file.

This is the smallest refactoring in the set, but it is the one that pays dividends every time *anyone* renames a resource bundle in the future, and it makes the next refactoring (or impact analysis) on this class cheaper.

#### Refactoring 3 — Remove Dead Code (Fowler general)

**Smell:** Temporary Field, in its degenerate form — a field that is *always* unset because no code ever writes to it.

**Location:** [`UngroupAction.java`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/UngroupAction.java) old line 28: `private CompositeFigure prototype;`. This field shadowed the inherited `GroupAction.prototype`; it was never assigned, never read, and removing it had zero behavioural effect.

**Purpose:** Eliminate the misleading shadow. A reader who sees a `prototype` field in `UngroupAction` would reasonably expect that the class manages its own prototype distinctly from the parent's — but it does not. The shadow is pure cognitive noise.

**Verification:** A grep for `prototype` in `UngroupAction.java` after the deletion returns zero hits; the file now reads cleanly as "thin subclass that flips the flag and configures its own resource-bundle key", which matches its actual responsibility.

I additionally removed the stale and inaccurate `// XXX - This code is redundant with UngroupAction` comment at the head of `ungroupFigures`. The redundancy claim is false: `ungroupFigures` is a Template Method hook overridden by `CombineAction`. Keeping a misleading comment violates Fowler's smell #22 (Comments compensating for bad code) more than removing it ever could.

---

### Verification

Behaviour preservation was checked at three levels:

1. **Compilation across the full reactor.** After each refactoring step:

    ```bash
    /tmp/maven/bin/mvn -pl jhotdraw-core,jhotdraw-samples/jhotdraw-samples-misc -am compile
    ```

    `jhotdraw-samples-misc` is included specifically because it contains `CombineAction`, which depends on the methods that *almost* changed. If my refactoring had broken the implicit Template Method contract, this is where it would have shown up.

2. **Full test suite.** `mvn test` from the repository root: BUILD SUCCESS, all (two) existing tests pass.

3. **Manual code reading on the consumers.** I re-read [`CombineAction`](jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/odg/action/CombineAction.java), [`ButtonFactory`](jhotdraw-gui/src/main/java/org/jhotdraw/gui/action/ButtonFactory.java), and [`ODGApplicationModel`](jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/odg/ODGApplicationModel.java) (the three Propagating classes from Lab 3b most likely to break) and confirmed that none of them call any private or anonymous-class member of `GroupAction`; they all interact through the public API I preserved.

The narrow test suite means the "real" verification is the second one — the IDE's type system plus the recompile. The Lab 5 *Actualization* phase will be the moment to add proper unit tests for `groupFigures` / `ungroupFigures`, which would then retroactively strengthen the guarantee of this refactoring.

---

### Deferred Refactorings

Three larger refactorings were identified but *not* applied, with explicit reasons.

#### Replace Conditional with Polymorphism (Fowler 255)

**Target:** the `private boolean isGroupingAction` field and its consumers (`updateEnabledState`, `actionPerformed`).

**Why deferred:** would require making `actionPerformed`'s body abstract and letting `GroupAction` / `UngroupAction` override it with their respective implementations. The clean version of this collapse-then-polymorph move conflicts with `CombineAction`'s current shape — `CombineAction` extends `GroupAction` (the grouping side) and relies on inheriting the existing `actionPerformed` body. Doing this safely means simultaneously moving `CombineAction` onto a different supertype (probably `AbstractCompositeAction` extracted as a new superclass), which makes the refactoring large enough to require its own test plan first.

**Pattern direction (per Kerievsky):** this is a *To Strategy* refactoring — replacing the boolean flag with a strategy object would simultaneously remove the smell and make the code more pattern-shaped. Worth doing once the test foundation is in place.

#### Form Template Method (Fowler 345)

**Target:** the relationship between `GroupAction.actionPerformed` and the hooks `groupFigures` / `ungroupFigures` / `canGroup` / `canUngroup`.

**Why deferred — actually, why not needed:** the Template Method *already exists implicitly*. `CombineAction` is the canonical evidence: it overrides exactly the three hook methods and inherits the template (`actionPerformed`). The lecture's slogan applies: *the pattern is the destination*, and the code is already there. Formalising it (e.g. marking the hooks `protected abstract` in an extracted `AbstractGroupAction`) would be a *clarifying* refactoring rather than a *correcting* one — valuable but not urgent.

#### Collapse Hierarchy (Fowler 344) on `GroupAction` and `UngroupAction`

**Target:** since `UngroupAction` is a six-line subclass that only flips a boolean and changes a resource-bundle key, one might argue for merging it back into `GroupAction` and exposing a static factory method.

**Why deferred:** keeping `UngroupAction` as a separate class preserves a clean *symbolic* identity for the ungroup operation, which is referenced by name in `ButtonFactory.addEditMenuItems` and in five sample panels. Merging would *increase* the diff size while only marginally reducing the apparent class count — net cost is higher than net benefit. The right move here is the *opposite* one (Replace Conditional with Polymorphism above), which preserves `UngroupAction` and gives it real behaviour to own.

---

### Summary

Three small, named, mechanically-safe refactorings from the Fowler / Kerievsky catalogues were applied to [`GroupAction.java`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java) and [`UngroupAction.java`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/UngroupAction.java):

| # | Refactoring | Catalog | Smell addressed |
|---|---|---|---|
| 1 | Compose Method (extract `performGroup` / `performUngroup`) | Kerievsky 123 / Fowler 110 | Long Method |
| 2 | Extract Method (`getLabels()` helper) | Fowler 110 | Duplicated Code |
| 3 | Remove Dead Code (`UngroupAction.prototype` shadow field + stale XXX comment) | Fowler general | Temporary Field, Comments |

Net effect: `actionPerformed` shrinks from 67 lines to 7 lines; the body of the file now reads top-to-bottom as a coherent sequence of intention-revealing steps; `UngroupAction` no longer carries shadow state; the resource bundle name is centralised. Behaviour is preserved: `mvn test` is green, no public API changed, and `CombineAction`'s Template-Method override contract is intact.

The two main reflections from the lab:

1. **Test coverage is the rate-limiter on refactoring scope.** Three of the four most valuable refactorings I identified (Replace Conditional with Polymorphism, Form Template Method, Collapse Hierarchy) had to be deferred not because they were wrong, but because the existing test suite was too thin to give a safe refactor net. Lecture 3's CI emphasis and Lecture 4's behaviour-preservation principle are not independent — *you can only refactor as deeply as your tests let you*. The Lab 5 deliverable (writing real tests for Group/Ungroup) is the gate to those deeper refactorings.

2. **The smell catalogue and the pattern catalogue are mutually clarifying.** Reading `GroupAction` *before* Lecture 4 looked like a slightly-too-long Java class with some redundancy. Reading it *with* the catalog in mind made every smell jump out by name, and made the path between smell and refactoring direct rather than improvised. This is Kerievsky's claim from the lecture made concrete: design patterns and refactorings together form a vocabulary that turns vague unease about code into actionable steps.

---

## Lecture 5 — Actualization, OO Principles and Clean Architecture

The fifth lecture block landed at the centre of the phased model — the *Actualization* phase, where the code physically changes — and surrounded that mechanic with the design vocabulary needed to do it well: SOLID, GRASP, the Composite Reuse and Least-Knowledge principles, and finally Robert C. Martin's Clean Architecture. Where Lecture 4 gave the *therapy* (named small refactorings), Lecture 5 gives the *health criteria* the therapy is trying to achieve.

---

### 5.1 The Actualization Phase

Actualization is phase 5 of the seven-phase software-change model from Lecture 2: *programmers implement the new functionality according to the change request*. It sits between Prefactoring (phase 4, which I performed in Lab 4) and Postfactoring (phase 6). The shape of an actualization varies with the change size:

- **Small changes** are done directly in the old code. The lecture's canonical example is widening a US ZIP code from `char zip[5]` to `char zip[9]`: one-token edit, no new classes, no incorporation.
- **Larger changes** implement the new classes *separately* from the old code, then plug the result in. Two terms name the two halves of that operation:
    - **Incorporation** — the new classes are wired into the existing code at one or more points.
    - **Ripple effect** — the change propagates outward from the incorporation point along interaction edges to classes that need secondary modifications.

The four functional impacts from Lecture 2 (incremental / contraction / replacement / refactoring) each manifest differently at actualization time. Adding a new component (incremental) is a clean incorporation with outward ripple. Replacement of a class redirects the old class's clients to the new class. Deletion of obsolete functionality (contraction) also ripples — every reference to the deleted entity has to be removed.

#### Polymorphism as the cleanest actualization

The lecture's first concrete example is the `Farm / FarmAnimal / Cow / Sheep / Pig` hierarchy. Adding `Pig` requires:

```cpp
class Pig : public FarmAnimal {
public:
    void makeSound() { cout << "Oink"; }
};
```

…and zero changes to `Farm`. The composite responsibility of `Farm` is *extended* by the concept Pig — no client of `Farm` has to be modified. This is the actualization-time payoff of obeying the Open/Closed Principle: a change becomes additive instead of intrusive.

#### Change-propagation in a Point-of-Sale example

The slides walk a Point-of-Sale change ("add a cashier login") through:

1. Add a new `Cashiers` class separately.
2. Incorporate it as a supplier of `Store` (the closest natural binding).
3. Watch the change ripple: `item` needs new attributes → `saleLineItem` notices → `sale` notices → `register` notices → propagation stops where no further interaction exists.

The propagation graph mirrors the *impact analysis* of Lecture 3 — but lived through, not predicted. The lecture's slogan: **change propagation is the moment of truth** for impact analysis. It either confirms or refutes the impact set you committed to in the IA phase.

#### Ericsson Radio Systems — impact-set accuracy

The lecture closes with empirical data from Ericsson:

|         | Predicted Unchanged | Predicted Changed |
|---|---|---|
| **Actual Unchanged** | 42 | 0 |
| **Actual Changed** | 64 | 30 |

Total 136 classes. From this:

- True positives = 30, false positives = 0, true negatives = 42, false negatives = 64.
- **Precision** = TP / (TP + FP) = 30 / 30 = **100 %**.
- **Recall** = TP / (TP + FN) = 30 / 94 ≈ **32 %**.

Programmers correctly predicted that what they marked as Changed *would* change — but missed two-thirds of the classes that actually had to change. The lecture's reading: under-estimation is a chronic consequence of *invisibility* (the essential difficulty from Lecture 1) and is one of the strongest arguments for verification scaffolding (tests + CI) that catches the missed two-thirds before they reach users.

---

### 5.2 Object-Oriented Principles — SOLID, CRP and PLK

Robert C. Martin's *Design Principles and Design Patterns* (2000) introduced what Michael Feathers later named SOLID. The lecture adds two further principles often grouped with SOLID: the Composite Reuse Principle and the Principle of Least Knowledge (Law of Demeter).

| Letter | Principle | One-line meaning |
|---|---|---|
| **S** | Single Responsibility | A class should have only one reason to change. |
| **O** | Open / Closed | Software entities should be open for extension but closed for modification. |
| **L** | Liskov Substitution | Subclasses should be substitutable for their base classes without altering correctness. |
| **I** | Interface Segregation | Many specific interfaces are better than one general-purpose interface. |
| **D** | Dependency Inversion | Depend upon abstractions; do not depend upon concretions. |
| *CRP* | Composite Reuse | Favour polymorphic composition of objects over class inheritance. |
| *PLK* | Least Knowledge (Law of Demeter) | An operation on class *C* should only call operations on: itself, its parameters, objects it creates, or its contained instance objects. |

The lecture's slides illustrated each principle with a small "without X / with X" pair:

- **SRP**: A `UserService` doing both `changePassword` and `checkAccess` is split into `UserService` (changes passwords) and `SecurityService` (checks access).
- **OCP**: A `LoanApprovalHandler` that hard-codes `PersonalLoanValidator` is rewritten to depend on a `Validator` interface; `PersonalLoanValidator` and `HomeLoanValidator` both implement it.
- **LSP**: A `Bird` hierarchy where `Ostrich.fly()` throws `UnsupportedOperationException` is restructured into `FlightBird` / `NonFlightBird`, so subclasses' contracts match their supertype.
- **ISP**: An `IUser` interface that mixes `changePassword` / `checkUserRole` / `assignRole` is split into `IUser`, `IUserRole`, and `IRole`.
- **DIP**: A `Payments` class that constructs `new CreditCardPaymentMethod()` internally is rewritten to receive a `PaymentMethod` in its constructor — the dependency is inverted from concrete-class instantiation to abstract-interface injection.

The two satellite principles:

- **CRP**: "One of the most catastrophic mistakes that contribute to the demise of an object-oriented system is to use inheritance as the primary reuse mechanism." Delegation is usually better. This is the same idea as Fowler's *Replace Inheritance with Delegation* (Lecture 4) at the architectural level.
- **PLK / Law of Demeter**: avoid `a.getB().getC().doSomething()` — transitive visibility means the caller knows the *structural makeup* of `a`'s neighbours. Limit each method to talking to itself, its parameters, its fields, and the objects it creates.

Crucial connection from the lecture: **DIP tells us how to obey OCP.** Without DIP, the only way to make a class "closed for modification but open for extension" is to make every concrete dependency negotiable through an interface. The two principles are two faces of the same idea.

---

### 5.3 GRASP — General Responsibility Assignment Software Patterns

Where SOLID gives *class-level* design constraints, Craig Larman's GRASP gives the patterns for *assigning responsibilities*. The acronym stands for General Responsibility Assignment Software Patterns; the catalogue has nine entries:

| # | Pattern | The question it answers |
|---|---|---|
| 1 | **Information Expert** | Which class has the data needed to fulfil the responsibility? Assign it there. |
| 2 | **Creator** | Which class should be responsible for creating instances of class A? The one that aggregates, contains, records, closely uses, or has the initialising data for A. |
| 3 | **Low Coupling** | Assign the responsibility so the resulting class depends on as few others as possible. |
| 4 | **High Cohesion** | Assign the responsibility so the class's purpose stays focused and unrelated work does not accumulate. |
| 5 | **Controller** | Where does an external event enter the system? A controller class (facade or use-case controller) — *not* a window, widget, or document class. |
| 6 | **Polymorphism** | When behaviour varies by type, use polymorphic operations on the varying type — not type-code switching. |
| 7 | **Indirection** | When two components must not be directly coupled, introduce an intermediary to mediate. Beware transitive visibility (this is the same trade-off as PLK). |
| 8 | **Pure Fabrication** | When no domain class is a good owner of a responsibility, invent a non-domain *fabrication* class to host it (e.g. `PersistentStorage`, `ButtonFactory`). |
| 9 | **Protected Variations** | Identify points of predicted variation and put a stable interface around them. Same shape as OCP, broader applicability. |

GRASP is the practical bridge between "I have a use case in mind" and "here are the classes that should exist". SOLID *constrains* class design after responsibilities are assigned; GRASP *guides* the assignment itself. The two are complementary rather than competing.

---

### 5.4 Clean Architecture

Robert C. Martin's *Clean Architecture* is an architectural pattern (distinct from GoF *design patterns*, which solve smaller problems within a class or small cluster). The defining diagram is a set of concentric rings:

```
┌─────────────────────────────────────────────────┐
│  Frameworks & Drivers (Web, UI, DB, Devices)    │  <- outer
│  ┌───────────────────────────────────────────┐  │
│  │  Interface Adapters (Controllers,         │  │
│  │  Gateways, Presenters)                    │  │
│  │  ┌──────────────────────────────────────┐ │  │
│  │  │  Application Business Rules          │ │  │
│  │  │  (Use Cases / Interactors)           │ │  │
│  │  │  ┌────────────────────────────────┐  │ │  │
│  │  │  │ Enterprise Business Rules     │  │ │  │
│  │  │  │ (Entities)                    │  │ │  │
│  │  │  └────────────────────────────────┘  │ │  │
│  │  └──────────────────────────────────────┘ │  │
│  └───────────────────────────────────────────┘  │
└─────────────────────────────────────────────────┘
```

The four rings, from inside out:

- **Entities** — *enterprise-wide* business rules. The most general and least likely to change. In a banking system, an `Account` entity. In a drawing framework, a `Figure`.
- **Use Cases** (also called Interactors) — application-specific rules. Coordinate entities to perform one user goal. Isolated from the database, frameworks, and the UI.
- **Interface Adapters** — convert data between the format used by use cases / entities and the format used by external systems (databases, the web). Includes Presenters (MVP), View Models (MVVM), and Gateways / Repositories.
- **Frameworks & Drivers** — the outermost layer: web framework, database engine, UI toolkit, HTTP client, device drivers.

#### The Dependency Rule

The most important architectural constraint: **source-code dependencies must point inward.** Inner rings know nothing about outer rings. The use-case layer never imports a concrete database class; the entity layer never imports a use case. When data needs to cross the boundary outward, it goes through a *Boundary* interface (input boundary, output boundary) implemented by the inner ring and consumed by the outer ring — a direct application of the Dependency Inversion Principle from Section 5.2.

The flow of a single user action:

1. User clicks a button (Delivery Mechanism → outer ring).
2. The delivery mechanism builds a **Request Model** (primitive data, no entities) and hands it to an input Boundary.
3. The Interactor (Use Case) receives the request, orchestrates entities, and produces a **Response Model** through an output Boundary.
4. A **Presenter** (Interface Adapters layer) translates the response model into a **View Model** suitable for the View.
5. The View renders the View Model. The View is so simple that "you can test it with your eyes" — no logic to unit-test.

#### What about the database?

Martin's slogan: *"If something changes a lot, it should be a plug-in. If something doesn't change very often, it should be plugged into."* The database is a detail, not the centre of the architecture. Business rules should not be stored procedures, because that couples them to a specific database engine. The database connects to the entities through an *Entity Gateway* interface — the implementation lives in the outer Interface Adapters ring.

#### Characteristics of a successful architecture

The lecture lists four:

- **Testable** — business rules can be tested without UI, DB, or frameworks.
- **Independent of UI** — UI can change without touching business rules.
- **Independent of database** — the database can be switched (RDBMS ↔ NoSQL) without rippling.
- **Independent of frameworks and external entities** — libraries are *tools*, not the centre of gravity.

These four are not separate goals; they are corollaries of the Dependency Rule.

---

### Reflection on Lecture 5

The fifth lecture closes a loop that started in Lecture 1. Software's *essential difficulties* (complexity, invisibility, changeability, conformity, discontinuity) drive the need for a structured *change process* (Lecture 2's phased model). The change process needs *measurement* and *teamwork* to scale (Lecture 3). The change process needs *behaviour-preserving transformations* and a *vocabulary of smells and patterns* to keep code healthy under change (Lecture 4). And finally, the change process needs *architectural and class-level principles* — SOLID, GRASP, Clean Architecture — to ensure that each individual change leaves the codebase more, not less, able to absorb the next change.

The Ericsson 32 % recall figure from Section 5.1 is the empirical anchor for everything in this block. Programmers under-estimate the impact set; the architecture must absorb the missed impact gracefully. A monolithic procedural design fails this test catastrophically — every missed dependency becomes a production defect. A Clean Architecture with strict inward-pointing dependencies and well-segregated interfaces makes the missed-dependency case much less expensive: the missed class either uses the abstraction safely (no change needed) or shows up at compile time (immediate, cheap discovery).

For the JHotDraw work in Lab 5, the most actionable framing is this: read the codebase with SOLID glasses on, identify where the framework already obeys each principle (extensibility patterns from Lecture 2.4 mostly arose from SOLID-compatible design choices), and identify where the framework violates a principle and what the cost is. The result is not a finished design critique — it is an *evidence-grounded* map of where future refactoring effort will pay off.

---

## Lab 5 — Actualization Lab: SOLID and Clean Architecture in JHotDraw

The Lab 5 handout asks for two portfolio deliverables:

1. *Provide examples of the SOLID principles in context of the CASE study.*
2. *Explain Clean Architecture in context of the CASE Study.*

Both are documentation deliverables. The "actualization" framing in the lab name is conceptual rather than implementation-driven: in the phased model from Lecture 2, the Group / Ungroup feature has now been impact-analysed (Lab 3b) and pre-factored (Lab 4); the next phase that would write new code is bounded by the SOLID / Clean-Architecture criteria documented below. This portfolio section is the artefact that would gate the Actualization step on a real team.

---

### Methodology

I inspected the JHotDraw codebase along two axes:

- **Per-principle search.** For each SOLID letter, I searched for one positive example (the framework obeys the principle) and, where present, one violation. I cited specific file paths and where useful, line numbers — the same convention used in earlier labs.
- **Module-level mapping.** I mapped the nine Maven modules of JHotDraw onto the four rings of Clean Architecture and tested the Dependency Rule by tracing inward-pointing imports.

Source of evidence: the Maven module tree under [jhotdraw-api/](jhotdraw-api/), [jhotdraw-core/](jhotdraw-core/), [jhotdraw-gui/](jhotdraw-gui/), [jhotdraw-app/](jhotdraw-app/), [jhotdraw-samples/](jhotdraw-samples/), plus the actual file system layout reported by `find` and quick `grep` checks for telltale patterns (`throw new UnsupportedOperationException`, `extends GroupAction`, etc.).

---

### SOLID in JHotDraw

#### S — Single Responsibility Principle

**Positive example.** [`GroupFigure`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/GroupFigure.java) has exactly one responsibility: be a concrete composite of Figures used as the prototype that `GroupAction` clones. Its constructor calls `setConnectable(false)` (groups shouldn't accept connector attachments), it provides `chop()` for connector geometry, and it overrides `isTransformable()` to be true only when all children are. Nothing more. A reader can predict everything the class does from its name plus its superclass.

**Violation.** [`GroupAction`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java) is the textbook SRP violation: it holds *both* the grouping logic and (the inverse) the ungrouping logic, dispatched by `private boolean isGroupingAction`. By Robert C. Martin's definition ("a class should have only one reason to change"), `GroupAction` has at least two reasons: a change to how figures are grouped, *or* a change to how groups are ungrouped, would force a modification to the same file. The class also serves as the prototype-holder *and* the undo-edit factory *and* the Action — three further responsibilities. The Lab 4 *Compose Method* refactoring (extracting `performGroup` / `performUngroup`) reduced the visual coupling but did not fix the underlying SRP problem; doing so requires the bigger *Replace Conditional with Polymorphism* refactoring deferred at the end of Lab 4.

#### O — Open / Closed Principle

**Positive example.** `GroupAction` is *open for extension* via the constructor

```java
public GroupAction(DrawingEditor editor, CompositeFigure prototype)
```

— and the canonical evidence is [`ODGApplicationModel.java:91`](jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/odg/ODGApplicationModel.java#L91):

```java
a.add(new GroupAction(editor, new ODGGroupFigure()));
```

The ODG sample plugs in its own group-figure variant *without* modifying `GroupAction`. The same mechanism is used by SVG. This is the Prototype pattern (from Lecture 2.4) serving an OCP role: it turns what would otherwise be a subclass-explosion into a single class with a pluggable collaborator.

**Where it stops.** OCP holds for the *figure being grouped* but not for the *operation itself*. To add a new operation modelled like Group/Ungroup (e.g. a hypothetical *Cluster* that groups by colour), one must edit `GroupAction` or introduce a parallel action class. The framework is open to varying *what* is grouped, closed to varying *how* the grouping is dispatched.

#### L — Liskov Substitution Principle

**Positive example.** [`CompositeFigure`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/CompositeFigure.java) extends [`Figure`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/Figure.java) and is honoured as a `Figure` everywhere. Every method of `GroupAction.groupFigures` operates on `Figure` references; the runtime types include `GroupFigure`, `SVGGroupFigure`, `ODGGroupFigure`, and ordinary leaf figures, and the code is correct for all of them. This is the Composite pattern obeying LSP by construction.

**Violation.** Several Tool implementations throw `UnsupportedOperationException` for inherited methods they choose not to support — for example [`TextEditingTool.java:166`](jhotdraw-core/src/main/java/org/jhotdraw/draw/tool/TextEditingTool.java#L166) and [`TextAreaEditingTool.java:173`](jhotdraw-core/src/main/java/org/jhotdraw/draw/tool/TextAreaEditingTool.java#L173). A client holding a `Tool` reference cannot freely substitute these subtypes without risking a runtime exception that the base interface does not advertise. The same pattern appears in [`DefaultDrawingViewTransferHandler.java:497`](jhotdraw-core/src/main/java/org/jhotdraw/draw/DefaultDrawingViewTransferHandler.java#L497). This is exactly the "Ostrich extends Bird" smell from the lecture: the supertype promises behaviour that some subtypes refuse to provide.

#### I — Interface Segregation Principle

**Positive example.** Instead of one omnibus `DrawingEditor` interface, JHotDraw exposes four small, focused interfaces:

- [`Figure`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/Figure.java) — what a single drawable shape can do.
- [`CompositeFigure`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/CompositeFigure.java) — adds child-management on top of `Figure`. Clients that don't need composition never see those methods.
- [`Drawing`](jhotdraw-core/src/main/java/org/jhotdraw/draw/Drawing.java) — the document model that holds top-level figures.
- [`DrawingView`](jhotdraw-core/src/main/java/org/jhotdraw/draw/DrawingView.java) — the selection + viewport adapter on top of a `Drawing`.

`GroupAction` only depends on `DrawingView` and `CompositeFigure` — it does not have to know about Tools, Handles, layouters, or any of the dozen other concerns the editor has. ISP is being respected.

**Slight tension.** [`View`](jhotdraw-api/src/main/java/org/jhotdraw/api/app/View.java) (378 lines) and [`ApplicationModel`](jhotdraw-api/src/main/java/org/jhotdraw/api/app/ApplicationModel.java) (219 lines) in `jhotdraw-api` are large interfaces. They are each focused on one concern (an open document tab, the application's responsibility-providing object), so they are cohesive rather than mixed — but their sheer size means that sample applications implementing them inherit ~30 mandatory methods. A stricter ISP application would extract sub-interfaces (`Persistable`, `Disposable`, `Activatable`) and let `View` extend them — partially done already with `Disposable`, but not finished.

#### D — Dependency Inversion Principle

**Positive example.** The `GroupAction` constructor signature

```java
public GroupAction(DrawingEditor editor, CompositeFigure prototype)
```

declares both dependencies as *interfaces*, not concrete classes. The action depends on abstractions; the concrete `DefaultDrawingEditor` and `GroupFigure` (or `ODGGroupFigure`, `SVGGroupFigure`) instances are *injected* by the caller. Inside the method body, `view.getDrawing().basicAddAll(...)` further depends on the [`Drawing`](jhotdraw-core/src/main/java/org/jhotdraw/draw/Drawing.java) interface — the action neither knows nor cares whether the underlying implementation is `DefaultDrawing`, `QuadTreeDrawing`, or some future variant.

**Architectural consequence.** The dedicated `jhotdraw-api` Maven module is itself a DIP artefact: it holds the stable application-shell abstractions (`Application`, `ApplicationModel`, `View`, `MenuBuilder`, `URIChooser`), and *concrete* implementations live in `jhotdraw-app` and the sample modules. The dependency arrow points *into* `jhotdraw-api`, not out of it.

**Where DIP is partial.** Many of the most central abstractions of the framework (`Figure`, `Drawing`, `DrawingView`, `Tool`, `Handle`) live in `jhotdraw-core` rather than `jhotdraw-api` — co-located with their default implementations. A strict DIP layout would move these interfaces into `jhotdraw-api` and leave only the `Abstract*` and `Default*` implementations in `jhotdraw-core`. This split is discussed in detail in the Clean Architecture section below.

---

### Clean Architecture in JHotDraw

#### Mapping the Maven modules onto Martin's four rings

| Clean Architecture ring | JHotDraw realisation | Why |
|---|---|---|
| **Entities** (enterprise-wide business rules) | `Figure`, `Drawing`, `CompositeFigure` interfaces + `AbstractFigure`, `AbstractCompositeFigure` base classes in [`jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/) | A drawing framework's "domain" is figures and drawings. These types know nothing about Swing, XML serialisation, or the application shell — they are the most stable concepts in the whole codebase. |
| **Use Cases** (application-specific rules) | The Action classes in [`jhotdraw-core/src/main/java/org/jhotdraw/draw/action/`](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/) — `GroupAction`, `UngroupAction`, `CutAction`, `PasteAction`, … | Each Action is one user-facing use case (group selection, paste from clipboard, etc.). The Action coordinates figures and the drawing model but knows nothing about how Swing dispatches a menu click. |
| **Interface Adapters** (presenters, gateways, controllers) | [`jhotdraw-gui/`](jhotdraw-gui/) — Swing controls and ButtonFactory; `DrawingView`, `DrawingEditor` implementations in [`jhotdraw-core/src/main/java/org/jhotdraw/draw/`](jhotdraw-core/src/main/java/org/jhotdraw/draw/); the XML reader/writer in [`jhotdraw-xml/`](jhotdraw-xml/); the clipboard / DnD support in [`jhotdraw-datatransfer/`](jhotdraw-datatransfer/) | These convert between domain shapes (`Figure`, `Drawing`) and externally consumable formats (Swing components, XML documents, system clipboard payloads). |
| **Frameworks & Drivers** (outermost) | The Swing JDK itself; the per-application shells in [`jhotdraw-app/`](jhotdraw-app/), [`jhotdraw-samples/jhotdraw-samples-misc/`](jhotdraw-samples/jhotdraw-samples-misc/) and `-mini`; the `jhotdraw-api` *app-shell* interfaces in [`jhotdraw-api/`](jhotdraw-api/) | The applications wire Swing widgets, action classes, and the drawing model into a runnable program. They are the most volatile part of the system — every sample app has its own Main. |

A cleaner picture for a one-line summary:

```
  Entities          Figure, CompositeFigure, Drawing (interfaces + abstract bases)
       ↑
  Use Cases         GroupAction, UngroupAction, ...
       ↑
  Adapters          DrawingView, DrawingEditor, ButtonFactory, XML serialisation
       ↑
  Frameworks        Swing, sample app Main classes, jhotdraw-api app-shell
```

The arrows point *inward* — every outer ring depends on the inner ring's abstractions, not the reverse. `Figure` does not import `JComponent`; `GroupAction` does not import `JButton`; `DefaultDrawingView` knows about both `Figure` (inward) and `JComponent` (outward).

#### Where JHotDraw obeys the Dependency Rule

- **Actions never touch Swing.** `GroupAction` imports `java.awt.event.ActionEvent` (a JDK abstraction) and `javax.swing.undo.*` (an undo framework) but no concrete Swing widget. The Action is reusable in any UI that triggers an `ActionEvent`.
- **The `Figure` model has no UI dependencies.** A grep for `javax.swing` inside [`jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/`](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/) returns essentially nothing. Figures know how to draw themselves into a `Graphics2D` (an AWT primitive, not a Swing component) but do not know about toolbars or palettes.
- **`jhotdraw-api` is a stable inner module.** It declares the application-shell abstractions; every concrete application implements them. The dependency arrow points into the api module, not out of it.

#### Where the Dependency Rule is bent

Three honest observations:

1. **The core drawing interfaces are in the wrong module.** A strict Clean Architecture would put `Figure`, `Drawing`, `DrawingView`, `Tool`, `Handle` into `jhotdraw-api` (the Entities ring), separated from the `AbstractFigure`, `DefaultDrawing`, `DefaultDrawingView` implementations in `jhotdraw-core` (which would then be Use Cases + Adapters). In the actual repository, interfaces and implementations are co-located in `jhotdraw-core`. The framework still compiles cleanly because the implementations sit *below* the interfaces in the file system — but a hypothetical second implementation cannot drop in without dragging `jhotdraw-core` along.
2. **`DefaultDrawingView` is `JPanel`.** [`DefaultDrawingView`](jhotdraw-core/src/main/java/org/jhotdraw/draw/DefaultDrawingView.java) extends `javax.swing.JPanel` — the Interface Adapter ring borrows from the Frameworks & Drivers ring. This is a pragmatic Swing choice (it makes the view directly mountable inside any Swing container) but it means the view layer is not truly framework-independent. Porting JHotDraw to JavaFX or a web canvas would require rewriting every `DrawingView` implementation.
3. **No explicit Boundary / Presenter / ViewModel split.** JHotDraw is structured as classical MVC, not as Clean Architecture's Request-Model / Response-Model / Boundary-interface flow. Actions invoke methods on the model directly; there are no input-boundary interfaces between the menu click and the Action. For a desktop drawing framework this is acceptable — the cost of formalising boundaries would exceed the benefit — but it is a real architectural difference from the Clean Architecture reference diagram.

#### Successful-architecture checklist

Against Martin's four characteristics from Lecture 5.4:

| Characteristic | JHotDraw status | Comment |
|---|---|---|
| **Testable** | Partial | The Figure / Drawing model is testable in isolation (no Swing required for `groupFigures`), but the actual test coverage is two files in the whole repository — the *architecture* permits testing; the *team* did not exploit it. |
| **Independent of UI** | Mostly yes | The model classes do not depend on Swing. The view classes do. A different UI toolkit could reuse `Figure` / `Drawing` directly. |
| **Independent of database** | N/A → yes | JHotDraw does not have a database; persistence is via XML through [`jhotdraw-xml/`](jhotdraw-xml/). The serialisation layer is cleanly separated from the model. |
| **Independent of frameworks** | Partial | The Action layer is reusable across any `ActionEvent`-driving UI; the View layer is locked to Swing. |

---

### Findings

**1. SOLID + Clean Architecture explain the framework's extensibility.**
The four extension points the SVG and ODG samples exercise — pluggable `GroupFigure` prototypes, pluggable `Layouter` strategies, pluggable `Tool` instances, pluggable `Handle` factories — are *all* DIP applications. The framework's much-praised extensibility (Lecture 2.4) is not a separate feature; it is the natural consequence of obeying DIP at the model layer.

**2. The biggest SOLID violation is also the one I refactored in Lab 4.**
`GroupAction`'s `isGroupingAction` boolean flag violates SRP (two responsibilities in one class) and OCP (cannot add a third "mode" without modifying the class) simultaneously. The Lab 4 *Compose Method* refactoring tackled the readability symptom; the underlying design violation is still there, and the deferred *Replace Conditional with Polymorphism* would fix it. This is direct empirical evidence for the lecture's claim that "DIP tells us how to obey OCP": both are violated by the same flag, and both would be fixed by the same refactoring.

**3. The Clean Architecture *intent* is honoured; the *module boundaries* are not.**
The dependency graph (Figure → Action → View → App) flows correctly inward at the code level. But the Maven module names suggest a layering (`jhotdraw-api` ⊃ `jhotdraw-core` ⊃ `jhotdraw-gui` ⊃ `jhotdraw-app`) that is not actually implemented in full: the core drawing abstractions are in `jhotdraw-core`, not `jhotdraw-api`, and the Swing dependency leaks into the adapter layer via `DefaultDrawingView extends JPanel`. A reader expecting strict Clean Architecture would be initially mis-aligned.

**4. The LSP violations are concentrated in dead-end tools.**
The classes that throw `UnsupportedOperationException` are mostly half-implemented features (`TextEditingTool`, `TextAreaEditingTool`, `DefaultDrawingViewTransferHandler.exportToClipboard`). LSP is therefore a useful *prioritisation* tool: each violation marks a spot in the codebase where someone started a feature, ran out of time, and left a runtime trap. The portfolio's Postfactoring step in a future lab could productively start from these locations.

**5. The Composite Reuse Principle is mostly obeyed — but with one notable exception.**
`UngroupAction extends GroupAction` is the one place in the Group/Ungroup feature where inheritance is used as a reuse mechanism rather than because `UngroupAction` *is-a* `GroupAction`. By CRP this should be delegation: an `UngroupAction` would hold a reference to a `GroupAction` and forward to its `ungroupFigures` method. The current inheritance gives a tiny code saving (one constructor flips a flag) but couples the two classes for the rest of time. This is the same observation as the deferred refactoring in Lab 4 — and shows CRP and SOLID converging on the same redesign.

---

### Summary

Lab 5 produced two artefacts:

1. **A per-principle SOLID map** of the Group / Ungroup neighbourhood: positive example + violation for each of S, O, L, I, D, plus the CRP relationship between `UngroupAction` and `GroupAction`. Five concrete violations are identified, with file:line references where applicable, all of them concentrated in the same ~10 classes that the Lab 3b impact set identified.
2. **A Clean Architecture mapping** of the nine Maven modules onto Martin's four rings, with three explicit deviations from the Dependency Rule documented (interface co-location in `jhotdraw-core`, `JPanel` leak into the adapter layer, absence of formal Boundaries).

Putting Labs 2–5 together, the picture of the Group / Ungroup feature is now complete enough to plan an actualization step responsibly:

- Concept-located (Lab 2): 8 core classes.
- Impact-analysed (Lab 3b): 27 classes across 11 packages.
- Pre-factored (Lab 4): three Fowler / Kerievsky refactorings applied; three larger ones deferred pending test coverage.
- Principle-mapped (Lab 5): SOLID violations catalogued; Clean Architecture mapping documented.

The lab handout's actualization framing is therefore satisfied by *documenting the readiness* rather than by writing new feature code: a hypothetical "add a Cluster command" feature now has a known impact set, a known prefactoring baseline, and a known set of design constraints (SOLID + Clean Architecture) the implementation must respect. That, on a real team, is the artefact a tech lead would gate the next sprint on — which is the spirit of the Actualization phase the lecture defined.

---

## Lecture 6 — Clean Code

The sixth lecture pulls together everything that has come before into one practitioner-level standard: Robert C. Martin's *Clean Code: A Handbook of Agile Software Craftsmanship* (2009). Where Lecture 5 gave architectural and class-level principles (SOLID, GRASP, Clean Architecture), Lecture 6 zooms in to the *line-by-line* level — names, functions, comments, formatting, error handling, tests, and class organisation. There is no accompanying lab; the deliverable is internalisation rather than artefacts. This portfolio section captures the rules and ties them back to the actual refactoring already performed in Lab 4.

---

### 6.1 What Clean Code Is

The lecture's opening is a collage of definitions from practitioners who collectively wrote much of modern software's foundational literature. Each is offered as a different angle on the same idea:

| Author | What clean code is |
|---|---|
| **Bjarne Stroustrup** | "I like my code to be elegant and efficient. Clean code does one thing well." |
| **Grady Booch** | "Clean code is simple and direct. Clean code reads like well-written prose." |
| **Dave Thomas** | "Clean code can be read. Clean code should be literate." |
| **Michael Feathers** | "Clean code always looks like it was written by someone who cares." |
| **Ron Jeffries** | "Reduced duplication, high expressiveness, and early building of simple abstractions." |
| **Ward Cunningham** | "You know you are working on clean code when each routine you read turns out to be pretty much what you expected." |

Two rules of thumb extract the operational essence:

- **The Boy Scout Rule** (Robert C. Martin himself): *"You should always leave the code cleaner than you found it."* Continuous, small improvements bend the code-decay curve from Lecture 1 downward over time.
- **WTFs/minute** (Thom Holwerda, popularised by the lecture's cartoon): the only honest metric of code quality. A good code review provokes one "wtf"; a bad one provokes a stream of them. The metric is humorous in form but serious in implication — readability is *measurable* by the affective response of an experienced reader.

---

### 6.2 Meaningful Names

The first chapter of clean code is *naming*, because every identifier the reader encounters is either a guidepost or a stumbling block. The lecture's eleven rules:

| Rule | Bad → Good |
|---|---|
| **Intension-revealing names** | `List<int[]> getThem()` → `List<Cell> getFlaggedCells()` |
| **Avoid disinformation** | `int a = l; if (O == l) a = O1;` — `l` and `1`, `O` and `0` are visually indistinguishable. |
| **Make meaningful distinctions** | `copyChars(char a1[], char a2[])` → `copyChars(char source[], char destination[])` |
| **Use pronounceable names** | `class DtaRcrd102 { Date genymdhms; ... }` → `class Customer { Date generationTimestamp; ... }` |
| **Use searchable names** | `for (j=0; j<34; j++) s += (t[j]*4)/5;` → use named constants like `WORK_DAYS_PER_WEEK`. |
| **Avoid encodings — no member prefixes** | `m_dsc` → `description`. The `m_` prefix encodes an attribute (membership) that IDE highlighting already makes obvious. |
| **Avoid encodings — no Hungarian notation** | `PhoneNumber phoneString` — the encoded type lies the moment the actual type changes. Just `PhoneNumber phone`. |
| **Avoid mental mapping** | `for (a = 0; a < 10; a++)` → `for (i = 0; i < 10; i++)` (i, j, k are conventional loop indices). |
| **Class names are nouns** | `Manager`, `Processor`, `Data`, `Info` are too generic. `Customer`, `WikiPage`, `Account`, `AddressParser` are concrete nouns. |
| **Method names are verbs** | `postPayment`, `deletePage`, `save`. Predicates: `isPosted`, `hasName`. Static factory methods: `Complex.fromRealNumber(23.0)` rather than `new Complex(23.0)` when context demands. |
| **Pick one word per concept; don't pun** | If `fetch`, `retrieve`, and `get` mean the same thing across the codebase, pick one. Conversely, never use the same word for two different things. |

Two more rules round out the chapter:

- **Use solution-domain names** when the reader will be a programmer (`AccountVisitor`, `JobQueue`).
- **Add meaningful context** (group related names by class or by prefix: `firstName`, `lastName`, `street`, `city` are clearly an address only when wrapped in an `Address` class or prefixed `addrFirstName`, `addrLastName`).
- **Don't add gratuitous context** — `AccountAddress` is fine for an instance but bad for a class; just call the class `Address`.

---

### 6.3 Functions

If naming is the first chapter, functions are the second — and arguably the heart of the book.

#### Rules of functions

- **Small.** Rule one: functions should be small. Rule two: functions should be *smaller* than that. The lecture's working numbers: <20 lines, <150 characters per line.
- **Do one thing.** "FUNCTIONS SHOULD DO ONE THING. THEY SHOULD DO IT WELL. THEY SHOULD DO IT ONLY." A function does one thing if every statement is at the same level of abstraction.
- **One level of abstraction per function.** Mixing high-level intent (`getHtml()`), intermediate operations (`PathParser.render(pagePath)`), and low-level details (`.append("\n")`) in the same function is what makes functions long and unreadable.
- **The Stepdown Rule (reading code top to bottom).** Each function should be followed by those at the next lower level of abstraction, so the file reads like a narrative top to bottom.
- **Replace switch on type code with polymorphism.** The lecture's `Employee.payAmount()` `switch (getType())` example is rewritten with an abstract `EmployeeType.payAmount(Employee)` and concrete `Salesman`, `Manager` overrides. This is exactly *Replace Conditional with Polymorphism* from Lecture 4.
- **Use descriptive names.** `testableHtml` → `includeSetupAndTeardownPages`. Don't be afraid of a long name; it is one-time cost.

#### Function arguments

- **Ideal number: zero.** One is acceptable. Two is harder. Three is to be avoided. Four+ is a sign the function needs a parameter object.
- **Common monadic forms** — one argument is fine when:
  - asking a question about it: `boolean fileExists("MyFile")`,
  - transforming and returning it: `InputStream fileOpen("MyFile")`,
  - it is an event: `passwordAttemptFailedNtimes(int attempts)`.
- **Flag arguments are bad.** `render(true)` violates "do one thing" — the function is really doing two things and the caller is choosing which. Split into `renderForSuite()` and `renderForSingleTest()`.
- **Dyadic and triadic functions.** `writeField(name)` is easier than `writeField(outputStream, name)`. `assertEquals(expected, actual)` is borderline because the argument order has a convention you must memorise.
- **Argument Objects.** `makeCircle(double x, double y, double radius)` → `makeCircle(Point center, double radius)`. Same data, but the wrapping captures the *that-these-belong-together* relationship.
- **Verbs and keywords.** `write(name)` is improved by `writeField(name)`; `assertEquals(expected, actual)` by `assertExpectedEqualsActual(expected, actual)` (keyword form encodes argument order in the name).

#### Two cross-cutting rules

- **Command-Query Separation.** A function should *either* do something *or* answer something, never both. `boolean set(String attribute, String value)` is unclear in `if (set("username", "unclebob"))…` — is `set` a verb (the command) or an adjective (the query)? Split into `attributeExists("username")` + `setAttribute("username", "unclebob")`.
- **DRY (Don't Repeat Yourself).** "Duplication may be the root of all evil in software." Every duplicated chunk is N maintenance burdens where there could be one.

#### Structured programming, modernised

Dijkstra's classical rules are one-entry, one-exit. The lecture's modern stance: when functions are small, occasional multiple `return`, `break`, or `continue` statements are *more* expressive than rigid single-exit. The rule is a guideline, not a law.

---

### 6.4 Comments

The lecture's stance is severe: comments are *failures*. Each comment is a place where the code couldn't speak for itself.

#### The two foundational rules

- **Comments do not make up for bad code.** Don't comment bad code — rewrite it.
- **Explain yourself in code.** `// Check to see if the employee is eligible for full benefits` followed by a cryptic boolean expression is wrong. Extract the expression into `employee.isEligibleForFullBenefits()` and the comment becomes redundant.

#### Good comments (the small list)

- **Legal comments** — copyright headers, licence preambles, mandated by external requirements.
- **Informative comments** — when a function name can't fully convey intent (`// format matched kk:mm:ss EEE, MMM dd, yyyy` next to a regex Pattern).
- **Explanation of intent** — *why* a piece of code looks the way it does (`// This is our best attempt to get a race condition by creating large number of threads.`).
- **Clarification** — when an opaque API call's return value needs annotation (`assertTrue(a.compareTo(b) == -1); // a < b`).
- **Amplification** — calling out something subtle that would otherwise be missed (`// the trim is real important. It removes the starting spaces that could cause the item to be recognized as another list.`).
- **Javadocs in public APIs** — well-described public APIs are uniquely valuable.

#### Bad comments (the long list)

- **Mumbling** — a half-sentence that doesn't actually explain.
- **Redundant** — `// Utility method that returns when this.closed is true.` next to `public synchronized void waitForClose(...)`. The comment says exactly what the signature already says.
- **Mandated** — `/** @param title The title of the CD. @param author The author of the CD. ...*/` produced by a "every public method must have Javadoc" rule. Forced doc comments are noise.
- **Journal comments** — `* 11-Oct-2001 : Re-organised the class and moved it to new package ...`. Version control already records this; the comment rots.
- **Noise comments** — `/** Default constructor. */` over `protected AnnualDateRule() { }`. The reader can see the constructor.
- **Scary noise** — `/** The name. */ private String name;`. The Javadoc tag on a self-explanatory field actively wastes screen real-estate.
- **Don't use a comment when a function or variable will do.** `if (smodule.getDependSubsystems().contains(subSysMod.getSubSystem()))` with a comment → `if (moduleDependees.contains(ourSubSystem))` with named local variables.
- **Position markers** — `// Actions //////////////////` — flagged as noise.
- **Closing brace comments** — `} //while`. If the brace needs a label, the function is too long.
- **Attributions and bylines** — `/* Added by Rick */`. `git blame` knows this.
- **Commented-out code** — a special evil. Other developers will be afraid to delete it. The version-control system holds the deleted version.
- **HTML comments**, **non-local information**, **too much information**, **inobvious connection between comment and code**, and **function headers on short functions** all earn dishonourable mentions.

The cumulative message: *the burden of justification for a comment is on the comment, not on its absence.*

---

### 6.5 Formatting

Formatting is communication. Code is read far more often than it is written. The lecture's rules:

- **The newspaper metaphor.** A code file should read like a newspaper — high-level concepts (headline) at the top, supporting details below.
- **Vertical openness between concepts.** Each blank line is a visual cue that a new and separate concept begins.
- **Vertical density.** Closely related lines should stay close. Field declarations broken up by their own Javadoc comments lose this cue.
- **Vertical distance.**
  - Local variables: declared as close to their usage as possible.
  - Instance variables: declared at the top of the class.
  - Dependent functions: if A calls B, A above B, B close to A.
  - Conceptual affinity: code that does similar things, regardless of direct call relationship, should be grouped.
- **Horizontal openness and density.** Spaces around operators, no spaces inside parentheses. Group strongly-related tokens densely; separate weakly-related tokens with space.
- **Horizontal alignment is bad.** Aligning the `=` of a block of declarations creates a visual column that emphasises *type names* over *variable names*. The lecture explicitly advises *against* it.
- **Don't break indentation.** A long class declared on one line is illegible; let braces and indentation do their job.
- **Team rules.** "Every programmer has their own favourite formatting rules. But if they work in a team, then the team rules." Consistency beats personal preference.

---

### 6.6 Objects and Data Structures

A subtle but important distinction:

- **Objects** hide their data behind abstractions and expose functions that operate on that data.
- **Data structures** expose their data and have no meaningful functions.

The two are *anti-symmetric* — code is easy to extend in one direction (adding new types) and hard to extend in the other (adding new operations), and vice versa. Mixing the two produces *hybrid* structures that suffer from both problems: hard to add types *and* hard to add operations.

#### The Law of Demeter

A method `m` of class `C` should only call methods of: itself, its parameters, objects it creates, and its instance fields. **Train wrecks** — chained calls like `ctxt.getOptions().getScratchDir().getAbsolutePath()` — violate this, because the caller now knows the structural shape of three different objects. The remedy is either to break the chain into named locals (preserves Demeter only if each link is a query on a directly-held object) or, more often, to expose a single higher-level method on `ctxt` that returns the absolute path directly.

This is exactly the *Principle of Least Knowledge* from Lecture 5.2 — Clean Code restates it at the line level.

---

### 6.7 Error Handling

- **Prefer exceptions to error codes.** Nested `if (… == E_OK) { if (… == E_OK) { … } }` checks become one `try` block with a single `catch`. The happy path is no longer obscured by error-handling noise.
- **Extract try/catch blocks.** A function that contains a `try/catch` should be *just* the try/catch — extract the body into a separate function. "Error handling is one thing."
- **Define the normal flow.** Use a *special case object* rather than a special return value. `try { expenses = expenseReportDAO.getMeals(employee.getID()); m_total += expenses.getTotal(); } catch(MealExpensesNotFound e) { m_total += getMealPerDiem(); }` is improved by making `MealExpenses` return a default instance for the missing case, so the caller becomes the single-line `m_total += expenseReportDAO.getMeals(employee.getID()).getTotal();`.
- **Don't return null.** A method that returns `null` forces every caller to check. Return `Collections.emptyList()` instead. The caller's `for` loop then works unconditionally.
- **Don't pass null.** Methods that accept `null` as an argument suffer the same fate. Throw `InvalidArgumentException` at the boundary or document the contract.

---

### 6.8 Unit Tests

- **The Three Laws of TDD.**
    1. You may not write production code until you have written a failing unit test.
    2. You may not write more of a unit test than is sufficient to fail — and not compiling is failing.
    3. You may not write more production code than is sufficient to pass the currently failing test.
- **Test code is just as important as production code.** It is not a second-class artefact.
- **What makes a clean test? Readability, readability, readability.** The single most important quality.
- **One assert per test.** Each test reaches a single conclusion that is quick and easy to understand.
- **Single concept per test.** Even when multiple asserts are needed, they should all be about one logical concept.
- **F.I.R.S.T.** A clean test is:
    - **Fast** — runs quickly so it is run often.
    - **Independent** — tests do not depend on each other; any order works.
    - **Repeatable** — works in any environment, deterministically.
    - **Self-validating** — boolean pass/fail, no manual inspection.
    - **Timely** — written just before the production code that makes them pass (per the Three Laws above).

---

### 6.9 Classes

#### Class organisation (the standard order)

1. Public static constants.
2. Private static variables.
3. Private instance variables.
4. Public functions.
5. Private utilities, placed right after the public function that calls them (stepdown rule applied within the class).

#### Two further rules

- **Classes should be small.** First rule: small. Second rule: smaller than that. Where functions are measured in lines, classes are measured in *responsibilities* — and the count should be one.
- **The Single Responsibility Principle (SRP).** "A class or module should have one, and only one, reason to change." This is the same SRP as Lecture 5.2 — Clean Code restates it as a class-organisation principle.
- **Cohesion.** Maintaining cohesion results in many small classes. When you find yourself wanting to factor a method out of a class into a helper, that is often a signal a new small class wants to exist.

---

### 6.10 Emergent Design — Kent Beck's Four Rules

The lecture closes with Kent Beck's four rules of simple design, in priority order:

1. **Runs all the tests.** A design that doesn't pass its tests isn't a design.
2. **No duplication.** Every duplicated chunk is a future maintenance burden.
3. **Expressive.** Names, structure, and shape communicate intent.
4. **Minimal classes and methods.** Don't add structure that isn't earned.

The order matters. Tests come first because they verify the design is real. Duplication comes second because it is the cheapest improvement with the highest payoff. Expressiveness comes third because it is harder than removing duplication but compounds over time. Minimisation comes last because the temptation to delete classes prematurely is high — Kent Beck explicitly puts this rule *behind* the others.

---

### Reflection on Lecture 6 — connecting back to the portfolio

This lecture has no associated lab in the course schedule, but the rules are not academic — most of them are exactly what my [Lab 4 refactoring](#lab-4--refactoring-lab-group--ungroup-prefactoring) on `GroupAction` already applied without naming. Re-reading my own work through the Clean Code lens:

| What I did in Lab 4 | Clean Code rule it satisfied |
|---|---|
| *Compose Method* — extracted `performGroup` / `performUngroup` from a 67-line `actionPerformed` | Functions should be small; do one thing; one level of abstraction per function. |
| Extracted `getLabels()` to deduplicate four resource-bundle lookups | DRY ("duplication may be the root of all evil in software"). |
| Removed the stale `// XXX - This code is redundant with UngroupAction` comment | Comments do not make up for bad code; redundant / journal comments are bad. |
| Removed the dead `prototype` shadow field in `UngroupAction` | Minimal classes and methods (Beck rule 4); class organisation hygiene. |

Two rules from the Clean Code catalogue point at refactorings I *deferred*:

- *Replace switch on type code with polymorphism* (Section 6.3) is the same move as the deferred *Replace Conditional with Polymorphism* on `GroupAction.isGroupingAction` — Lab 4 noted this would simultaneously fix SRP and OCP, and Section 6.3 confirms it would also satisfy the "do one thing" rule at the function level.
- *Encapsulate / member prefixes are bad* (Section 6.2) flags a small habit in older JHotDraw code: I noticed `_winterRate`, `_summerRate`, `_isDead`, `_seniority` and similar underscore-prefixed fields in the lecture's own examples (which mirror many places in JHotDraw's older code). Cleaning these is a *Boy Scout Rule* opportunity — small, mechanical, IDE-supported renames I can do whenever I'm in those files for another reason.

The clean-code rules also expose two structural problems in JHotDraw that earlier labs only hinted at:

1. **Test coverage is the rate-limiter for the entire programme.** The Three Laws of TDD assume tests exist before production code; the F.I.R.S.T. principles describe what those tests should look like. JHotDraw has two test files in the whole repository ([Lab 3](#lab-3--continuous-integration-and-impact-analysis) finding). The Clean Code framing makes this finding more severe than it looked in Lab 3 — without tests, *every* future refactoring is operating without the first of Kent Beck's four rules. Lab 4 explicitly cited this as the reason three larger refactorings had to be deferred.
2. **JHotDraw uses comments to compensate for unrefactored code in multiple places.** I removed one such comment in Lab 4; the codebase contains others (`// XXX`, `// FIXME`, `// TODO` distributed across `jhotdraw-core`). Each is a Section 6.4-level smell — a marker of code that, by the original author's own admission, wasn't finished. The *Comments Do Not Make Up for Bad Code* rule provides explicit licence to delete these and refactor toward the intent the comment was hinting at.

The single most useful idea from Lecture 6, for the rest of this course's work, is the *Boy Scout Rule*. Every other lecture has been about big moves — phases, impact sets, refactoring sessions, architectural mappings. The Boy Scout Rule is the smallest possible move: *leave each file fractionally cleaner than you found it on each visit.* Done consistently, it does the work of formal refactoring sessions in the background. Done inconsistently, it does the equivalent of code decay in slow motion. Internalising this rule is the deliverable Lecture 6 actually asks for — and it sits underneath every later lab in the programme.

---

## Lab 7 — Testing Lab: Unit Tests for Group / Ungroup

### Objectives

This lab follows the *TestLab1 — Testing* handout. The course objectives are stated plainly: *understand the importance of testing* and *implement unit tests* on the most important domain logic of my chosen feature. The portfolio task itself is one sentence: **"At class level write unit tests of important business functionality of your selected Feature. Document how you have verified your Feature."**

For me that selected feature is, as in every lab since Lab 2, the **Group / Ungroup** action — concretely the trio [GroupAction](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java), [UngroupAction](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/UngroupAction.java), and the figure they operate on, [GroupFigure](jhotdraw-core/src/main/java/org/jhotdraw/draw/figure/GroupFigure.java). The handout's classwork translates into four concrete deliverables:

1. Add the JUnit 4 dependency to the right Maven module.
2. Write JUnit 4 tests for *best case* scenarios of the most important methods of the feature.
3. Write tests for *boundary* and *failure* cases — applying mocks/stubs (Mockito) where execution would otherwise escape the unit under test.
4. Use Java `assert` statements in the production code to enforce invariants — things that *must never happen* — distinct from exceptions, which let the program continue.

This section documents what I did for each.

---

### Environment

| Tool | Version | Purpose |
|---|---|---|
| JDK | OpenJDK 25 (Red Hat) | Runs Maven and Surefire forks |
| Source / target | Java 1.8 | Unchanged from the JHotDraw parent POM |
| Maven (portable) | 3.9.6 at `/tmp/maven` | Already used in earlier labs |
| Surefire | 3.2.2 | Auto-detected provider used by JHotDraw |
| JUnit | **4.13.2** | Added in this lab |
| Mockito | **4.11.0** | Added in this lab — Mockito 5 requires JDK 11+ source target |
| TestNG | 6.8.21 | Pre-existing; the two `*NGTest` classes still run |

JUnit 4 was chosen over JUnit 5 *because the handout explicitly says so* — "*Swing and JUnit extensions often works best with JUnit 4*." Since `jhotdraw-core` already depended on TestNG and shipped two `*NGTest` files, I had to confirm both providers would coexist under Surefire. They do — Surefire's TestNG provider includes a JUnit-4 bridge (`org.testng.junit.JUnit4TestRunner`), so the existing TestNG suite and my new JUnit-4 classes are collected together into one report. No Surefire configuration was needed.

Mockito 4.11.0 was the deliberate pick rather than the newer 5.x line: Mockito 5 requires Java 11+ as the *source* target, and the JHotDraw parent POM still compiles to Java 1.8. The 4.x line remains compatible.

---

### Step 1 — Add the test dependencies

The lab's first classwork item: *"Add maven dependency to [JUnit4] if it is not already done."* I edited only [jhotdraw-core/pom.xml](jhotdraw-core/pom.xml) — the module that owns the Group/Ungroup classes — and appended JUnit 4 and Mockito immediately after the existing TestNG dependency, keeping both at `<scope>test</scope>`:

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>4.11.0</version>
    <scope>test</scope>
</dependency>
```

I deliberately did **not** put these dependencies in the parent POM. The other modules don't need them; keeping scope as narrow as possible avoids forcing test-time dependencies onto modules that don't write tests.

I verified the build still ran the existing TestNG suite before writing any new tests:

```
/tmp/maven/bin/mvn test -pl jhotdraw-core --no-transfer-progress
```

→ `Tests run: 2, Failures: 0` from `AbstractFigureNGTest`. Clean baseline.

---

### Step 2 — Decide what to test

The handout warns that *"a unit test should test a single code-path through a single method"*. So the first design decision was *which methods are the important business functionality*. I looked at the Group/Ungroup feature through the lens of Lab 2's concept-location and Lab 4's refactoring and picked five methods:

| Class | Method | Why it matters |
|---|---|---|
| `GroupAction` | `canGroup()` | The guard for whether the *Group* menu item is enabled. Wrong answer → broken UX. |
| `GroupAction` | `canUngroup()` | The guard for *Ungroup*. Also tells us a single non-group figure must *not* be ungroupable. |
| `GroupAction` | `groupFigures(view, group, figures)` | The core mutator that moves figures into the group and reinserts the group in the drawing. |
| `GroupAction` | `ungroupFigures(view, group)` | The inverse — moves children back out and removes the group. |
| `GroupAction` | `actionPerformed(ActionEvent)` | The dispatch path that picks group vs ungroup at runtime (a direct consequence of Lab 4's *Replace Conditional with Polymorphism* refactoring I had to defer). |
| `UngroupAction` | constructor + dispatch | The subclass exists solely to flip `isGroupingAction` — that wiring is worth pinning. |
| `GroupFigure` | `isTransformable()` | Pure collective predicate — *the group is transformable iff all children are*. Classic boundary-test target. |

Anything outside this list (e.g. Swing key bindings, label localisation, undo presentation strings) is either GUI infrastructure or framework boilerplate, not business logic.

---

### Step 3 — Apply mocks where execution leaves the unit

The handout's rule 4(a) is the central reason this lab is non-trivial: *"When the execution of a method passes outside of that method, you have a dependency and should apply mocks/stubs to avoid the dependency."*

`GroupAction` depends on three collaborators:

```
DrawingEditor → DrawingView → Drawing
                            → CompositeFigure (the group prototype)
```

Each dependency is a Swing/JHotDraw interface or class. Without mocks I would either need a real `DrawingEditor` (which transitively needs a Swing window, a frame, an event-dispatch thread — all the things the remote terminal cannot provide) or I would be writing integration tests, not unit tests.

I therefore used Mockito to fabricate exactly those three dependencies. The `setUp()` method in [GroupActionTest](jhotdraw-core/src/test/java/org/jhotdraw/draw/action/GroupActionTest.java) wires them together once per test:

```java
@Before
public void setUp() {
    editor   = mock(DrawingEditor.class);
    view     = mock(DrawingView.class);
    drawing  = mock(Drawing.class);
    prototype = new GroupFigure();

    when(editor.getActiveView()).thenReturn(view);
    when(view.getDrawing()).thenReturn(drawing);

    action = new GroupAction(editor, prototype, true);
}
```

One subtlety I hit and want to record: I initially used a **mock `CompositeFigure` as the prototype**, but Mockito cannot stub `Object#getClass()` (it is `final`). `GroupAction.canUngroup()` relies on `selectedFigure.getClass().equals(prototype.getClass())`, so a mocked prototype would have made *every* class-equality check fail. The fix is to use **real `GroupFigure` / `RectangleFigure` instances** specifically where class identity matters, and use mocks everywhere else. This is the kind of trade-off the handout's rule 4(a) hints at without spelling out — *not every dependency can be mocked; some must be real because the method under test asks the JVM about its identity*.

---

### Step 4 — Best-case tests

The handout's rule 3: *"Write JUnit tests for best case scenario."* The two foundational best-case tests:

**`canGroup` happy path.** Two figures selected → grouping is enabled.

```java
@Test
public void canGroup_returnsTrue_whenSelectionHasMoreThanOneFigure() {
    when(view.getSelectionCount()).thenReturn(3);

    assertTrue("two or more selected figures should be groupable",
            action.canGroup());
}
```

**`groupFigures` happy path.** Two figures, sorted, inserted at the index of the lowest one, with the *exact ordering of operations* pinned via Mockito's `InOrder`:

```java
@Test
public void groupFigures_clearsSelection_addsGroupAtFirstFigureIndex_andReselectsTheGroup() {
    CompositeFigure group = mock(CompositeFigure.class);
    Figure f1 = mock(Figure.class), f2 = mock(Figure.class);
    List<Figure> figures = Arrays.asList(f1, f2);
    when(drawing.sort(figures)).thenReturn(new ArrayList<>(figures));
    when(drawing.indexOf(f1)).thenReturn(4);

    action.groupFigures(view, group, figures);

    InOrder ordered = inOrder(drawing, view, group);
    ordered.verify(drawing).basicRemoveAll(figures);
    ordered.verify(view).clearSelection();
    ordered.verify(drawing).add(4, group);
    ordered.verify(group).willChange();
    ordered.verify(group).basicAdd(f1);
    ordered.verify(group).basicAdd(f2);
    ordered.verify(group).changed();
    ordered.verify(view).addToSelection(group);
}
```

The *ordering* is itself a non-obvious invariant I learned from Lab 4: if you `addToSelection` before `changed()`, undo history is corrupted. Pinning the order with `inOrder` means a refactor that accidentally reshuffles those calls will fail loudly instead of producing a subtle visual glitch.

The mirror best-case test exists for `ungroupFigures` and is structured identically.

---

### Step 5 — Boundary and failure cases

This is rule 4 of the handout, and where the bulk of the tests live. I categorised the cases by the *kind of input that should provoke a different code path*:

| Method | Boundary input | Expected behaviour |
|---|---|---|
| `canGroup` | exactly 1 figure selected | `false` — the most common off-by-one bug here is `>=` instead of `>` |
| `canGroup` | empty selection | `false` |
| `canGroup` | no active view at all (`editor.getActiveView() == null`) | `false` — defensive guard, exercised when Draw is launched but no document is open |
| `canUngroup` | 2+ figures selected | `false` — ungroup requires exactly one |
| `canUngroup` | empty selection | `false` |
| `canUngroup` | single figure of the *wrong class* (e.g. a `RectangleFigure`, not a `GroupFigure`) | `false` — this is the class-identity check that forced me away from mock prototypes |
| `canUngroup` | no active view | `false` |
| `ungroupFigures` | a group with **zero** children | returns an empty collection; still removes the group from the drawing |
| `actionPerformed` | grouping action invoked when `canGroup` is false | nothing happens — no `drawing.add`, no `fireUndoableEditHappened` |

The "no active view" cases are interesting precisely because they don't look like edge cases — they are perfectly normal application states (Draw started, no document yet open, a key binding fires the action anyway). A unit test makes the contract explicit; a manual GUI test would never reproduce this reliably.

The empty-children boundary on `ungroupFigures` is the kind of case I would not have thought of without writing it down — *what does "ungroup an empty group" even mean?* The current code does the right thing (returns empty collection, still removes the group), and the test now locks that in.

---

### Step 6 — Production assertions

The handout's rule 5 distinguishes **assertions** from **exceptions**:

> "Assertions should be used to check something that should never happen. Note, an assertion should stop the program from running, but an exception should let the program continue running."

That is exactly the Java-language distinction between `assert` (disabled at runtime unless `-ea` is passed, throws `AssertionError` when it fails) and a checked or unchecked exception (always evaluated, recoverable in principle). The semantic difference is intent: assertions document *invariants the programmer believes the rest of the codebase upholds*, not validation of untrusted input.

I added three such assertions to [GroupAction.groupFigures](jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java):

```java
public void groupFigures(DrawingView view, CompositeFigure group, Collection<Figure> figures) {
    assert view != null : "groupFigures requires a non-null view";
    assert group != null : "groupFigures requires a non-null group";
    assert figures != null && !figures.isEmpty() : "groupFigures requires at least one figure";
    ...
}
```

and three to `ungroupFigures`, including a *structural* invariant:

```java
public Collection<Figure> ungroupFigures(DrawingView view, CompositeFigure group) {
    assert view != null : "ungroupFigures requires a non-null view";
    assert group != null : "ungroupFigures requires a non-null group";
    assert view.getDrawing().indexOf(group) >= 0 : "group must already belong to the drawing";
    ...
}
```

These invariants are *already enforced* by `canGroup()` / `canUngroup()` being called immediately before the mutators in `performGroup` / `performUngroup`. The asserts therefore catch the case where some future caller bypasses the guards — exactly the "this should never happen" case the handout asks for. I also wrote a JUnit test that proves the assertion fires:

```java
@Test(expected = AssertionError.class)
public void groupFigures_failsAssertion_whenFiguresCollectionIsEmpty() {
    action.groupFigures(view, mock(CompositeFigure.class),
            Collections.<Figure>emptyList());
}
```

Surefire enables `-ea` by default on the test fork, so this test passes without extra configuration. In production the assertions are silently no-ops unless the JVM is started with `-ea`, which is the correct default.

---

### Step 7 — Run the suite

Final command:

```
/tmp/maven/bin/mvn test -pl jhotdraw-core --no-transfer-progress
```

Output (truncated to the relevant lines):

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running TestSuite
[INFO] Tests run: 26, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.482 s -- in TestSuite
[INFO] Results:
[INFO] Tests run: 26, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

The 26 tests are: 2 pre-existing TestNG tests + 16 in `GroupActionTest` + 5 in `UngroupActionTest` + 3 in `GroupFigureTest`. All on every push to GitHub via the existing CI workflow set up in [Lab 3](#lab-3--continuous-integration-and-impact-analysis), so any regression in the Group/Ungroup feature will now break the build instead of being noticed visually.

---

### Test catalogue

For the grader's traceability, here is the complete mapping of test methods to the production methods they exercise:

| Test | Production method | Case category |
|---|---|---|
| `canGroup_returnsTrue_whenSelectionHasMoreThanOneFigure` | `GroupAction.canGroup` | best |
| `canGroup_returnsFalse_whenSelectionHasExactlyOneFigure` | `GroupAction.canGroup` | boundary |
| `canGroup_returnsFalse_whenSelectionIsEmpty` | `GroupAction.canGroup` | boundary |
| `canGroup_returnsFalse_whenNoActiveView` | `GroupAction.canGroup` | failure |
| `canUngroup_returnsTrue_whenSelectionIsSingleMatchingFigure` | `GroupAction.canUngroup` | best |
| `canUngroup_returnsFalse_whenSelectionIsSingleNonMatchingFigure` | `GroupAction.canUngroup` | failure |
| `canUngroup_returnsFalse_whenMultipleFiguresSelected` | `GroupAction.canUngroup` | boundary |
| `canUngroup_returnsFalse_whenSelectionEmpty` | `GroupAction.canUngroup` | boundary |
| `canUngroup_returnsFalse_whenNoActiveView` | `GroupAction.canUngroup` | failure |
| `groupFigures_clearsSelection_addsGroupAtFirstFigureIndex_andReselectsTheGroup` | `GroupAction.groupFigures` | best (with ordering) |
| `ungroupFigures_movesChildrenOutAndRemovesGroup_inOrder` | `GroupAction.ungroupFigures` | best (with ordering) |
| `ungroupFigures_returnsEmptyCollection_whenGroupHasNoChildren` | `GroupAction.ungroupFigures` | boundary |
| `actionPerformed_performsGroup_whenIsGroupingActionAndCanGroup` | `GroupAction.actionPerformed` | dispatch, best |
| `actionPerformed_doesNothing_whenCannotGroup` | `GroupAction.actionPerformed` | dispatch, guard |
| `actionPerformed_performsUngroup_whenNotGroupingActionAndCanUngroup` | `GroupAction.actionPerformed` | dispatch, best |
| `groupFigures_failsAssertion_whenFiguresCollectionIsEmpty` | `assert` in `groupFigures` | invariant |
| `canGroup_returnsFalse_evenWithGroupableSelection` | `UngroupAction` | wiring |
| `canUngroup_returnsTrue_forSingleGroupFigure` | `UngroupAction.canUngroup` | best |
| `canUngroup_returnsFalse_forSingleNonGroupFigure` | `UngroupAction.canUngroup` | failure |
| `actionPerformed_ungroupsRealGroupFigure_andFiresUndoableEdit` | `UngroupAction.actionPerformed` | dispatch, best |
| `actionPerformed_doesNothing_whenSelectionEmpty` | `UngroupAction.actionPerformed` | dispatch, guard |
| `isTransformable_returnsTrue_forEmptyGroup` | `GroupFigure.isTransformable` | boundary (vacuous) |
| `isTransformable_returnsTrue_whenAllChildrenTransformable` | `GroupFigure.isTransformable` | best |
| `isTransformable_returnsFalse_whenAnyChildNotTransformable` | `GroupFigure.isTransformable` | failure |

24 newly added tests, organised so every column of the matrix from Step 2's "what is worth testing" list is covered by at least one row above.

---

### Reflections

Three things stand out from doing this lab on a real, eight-year-old codebase as opposed to a textbook example.

**(1) The hardest part was the dependency graph, not writing the assertions.** `GroupAction extends AbstractSelectedAction` extends `javax.swing.AbstractAction` and listens to property changes on a `DrawingEditor`. Constructing one in a test requires either a real Swing environment (impossible without a display) or a chain of mocks just to satisfy the constructor. Mockito made this tractable, but I want to record that *had I designed `GroupAction` from scratch* I would have separated the action wiring (Swing concern) from the group/ungroup algorithm (pure domain), so the algorithm could be unit-tested without touching Swing at all. The Lab 4 reflection on *Replace Conditional with Polymorphism* already pointed this way; this lab is independent evidence for the same conclusion. The link is direct: a class with one responsibility is testable in isolation; a class that does action wiring *and* domain logic forces mocks. SRP is, in practice, *a testability principle*.

**(2) The class-identity check in `canUngroup` is a hidden mockability tax.** Half an hour into writing tests I realised that *any* future test that wants to exercise `canUngroup` must use a real concrete figure class because `Object#getClass()` cannot be stubbed. The production code's reliance on `getClass().equals(prototype.getClass())` is an entirely reasonable runtime choice, but it makes the method strictly harder to test. The Clean-Code rule from Lecture 6 (*"prefer polymorphism over type codes"*) would suggest replacing this with a `prototype.matches(figure)` query on the prototype itself — which a mock *could* stub. That is a small but real refactoring opportunity I noticed by virtue of writing the tests.

**(3) Boundary cases I would have missed without writing them down.** *Empty group ungroup* and *no active view* are both states a manual tester would almost never reach. Yet both are reachable via key bindings, scripted actions, or unusual sequences of menu clicks. Pinning them with tests does two things: it documents the contract (the method *will* be called from these states), and it freezes the current behaviour against accidental regression. This is the F.I.R.S.T. *Repeatable* property in action — a property that, until I wrote the tests, only existed by accident.

The hand-out's small print at the bottom asked for "documentation of how I verified my feature." The 24 tests are that documentation: each test name is a sentence describing a fact the code now upholds, and the catalogue table above is the index.

---

## Lecture 7 — Software Testing: How to Make Software Fail

> The Lecture 7 deck arrived *after* I had already completed [Lab 7](#lab-7--testing-lab-unit-tests-for-group--ungroup). I am documenting it retroactively, which gives the section an unusual property: every rule the lecture states can be cross-checked against what I actually did in the lab. Where I followed the rule, the deck is validation; where I didn't, it is a gap to record.

The lecture is the theoretical / conceptual backbone of Lab 7 and Lab 9. It does not introduce new code; it introduces the *framing* against which both labs make sense — the impossibility result that bounds what testing can prove, the taxonomy of test kinds, the rules for writing code that *can be tested*, and the disciplines (assertions, TDD, mocks) that make those rules operational.

---

### 7.1 The theoretical reason testing is incomplete

The lecture opens with **Turing's halting problem** as the formal foundation. Given a coded description of a Turing machine and an input for it, no general program can decide *will it halt or loop forever?* By Rice's theorem, the same impossibility extends to almost every non-trivial property of program behaviour, including *"is this code free of bugs?"*. This is not a tooling limitation — it is a **theorem**.

The operational consequence is the famous **Dijkstra dictum**:

> *"Testing can demonstrate the presence of bugs, but not their absence."*

The lecture's gloss: *residual bugs can still hide in the code, undetected by tests, as no test suite guarantees an error-free program.* This single sentence reframes everything Lab 7 did. Each of the 24 tests I added is a *demonstration that one bug is not present right now*; the suite as a whole is **not** a proof that the Group/Ungroup feature is correct. The portfolio section for Lab 7 already implicitly accepted this — "well designed tests come close to be adequate" is the most the theorem allows.

---

### 7.2 The "what is going on?" decision tree

When `test output OK?` returns *no*, the lecture asks the diagnostic question in five steps, each of which can independently be the source of the failure:

```
test output -> OK ? --no--> Bug in SUT ? --no--> Bug in acceptability test ?
                                                          |
                                                         no
                                                          v
                                                Bug in specification?
                                                          |
                                                         no
                                                          v
                                            Bug in OS, compilers, libs, hardware?
```

Each box can answer *yes*, which terminates the search. The example for *"bug in specification"* is the **Mars Climate Orbiter** (1999): one team specified force in pounds-force-seconds (Imperial), another in newton-seconds (Metric). Both teams wrote correct code against their own specification. The spec itself disagreed with itself. The orbiter burned up in the Martian atmosphere.

This tree is the most useful single diagram I learned from the entire course. *Test failure ≠ bug in code.* If the unit test claims `2 + 2 == 5`, the test is wrong. If the spec says `sqrt(-1) = error` and the code returns `i`, the spec is wrong. The diagnostic discipline of asking each question in order is what separates a working test culture from cargo-cult red-green.

---

### 7.3 The taxonomy of test kinds

The lecture's six-way diagram:

| Test kind | What it tests | Example from my labs |
|---|---|---|
| **Unit testing** | One module, all dependencies mocked | [GroupActionTest](jhotdraw-core/src/test/java/org/jhotdraw/draw/action/GroupActionTest.java) (Lab 7) |
| **Integration testing** | Two or more modules wired together | [GroupUngroupScenarioTest](jhotdraw-core/src/test/java/org/jhotdraw/draw/action/bdd/GroupUngroupScenarioTest.java) — uses real `DefaultDrawing` with mocked view (Lab 9) |
| **System testing** | The whole assembled system | (would be `DrawAppSwingScenarioTest` if runnable) |
| **Differential testing** | Two implementations compared for equality | (not in my labs — would compare old GroupAction vs refactored GroupAction) |
| **Stress testing** | Push the SUT to limits | (not in my labs) |
| **Random testing** | Feed unconstrained inputs | (not in my labs — property-based testing with jqwik would be the JVM tool) |

The lecture also distinguishes orthogonally between **white-box** (test has access to internals — what my JUnit tests are) and **black-box** (test only sees the API — what my JGiven scenarios are). This is a useful re-reading of what I built: Labs 7 and 9 are *the same feature tested through two complementary boxes*.

---

### 7.4 Creating testable software — the eight rules

The lecture's slide on *Creating Testable Software* lists eight rules. I score Lab 7 against each:

| Rule | My Lab 7 application |
|---|---|
| **Clean Code** | Lab 4 prefactoring removed the dead `prototype` field and the cryptic `XXX` comment ✓ |
| **Refactor** | Lab 4 *Compose Method* extracted `performGroup` / `performUngroup` ✓ |
| **Describe what it does and how it interacts** | Lab 9 BDD scenarios *are* this description ✓ |
| **No extra Threads** | Avoided by mocking the Swing event dispatch ✓ |
| **No swap of global variables** | JHotDraw doesn't have global state in this feature ✓ |
| **No pointer soup** | N/A in Java |
| **Module unit tests** | Lab 7 added 24 ✓ |
| **Support fault injection** | Not yet attempted (covered in 7.9) ✗ |
| **Assertions, Assertions, Assertions !!!** | Added 6 production `assert` statements in `groupFigures` / `ungroupFigures` ✓ (sparse vs the lecture's recommended density — see 7.6) |

Score: 8/9 fully and 1/9 partially. The biggest gap is **fault injection**, which I have not yet practised.

---

### 7.5 The three rules for assertions

The lecture's three rules:

| Rule | Meaning | Did Lab 7 follow it? |
|---|---|---|
| **R1: Assertions are not for error handling** | Use exceptions for *expected* failure modes, assertions for *impossible* states. | ✓ My assertions check invariants that production code already upholds — they catch *future* callers that bypass `canGroup()` / `canUngroup()`. |
| **R2: NO SIDE EFFECTS** | An assertion that mutates a field is silently disabled in production. | ✓ My assertions are pure boolean expressions. |
| **R3: No silly assertions** | `assert 1+1==2;` adds nothing. | ✓ Each of my assertions encodes a real invariant. |

A subtler point the lecture raises in *Disable Assertions?* — code can come to *rely* on a side-effect assertion that vanishes in production. This is the worst-case failure mode for R2. My assertions in `GroupAction.groupFigures` would survive being disabled, because the production code has its own `canGroup()` guard upstream.

---

### 7.6 Production assertion density

The lecture's data point on real-world systems:

| Project | Assertions | Lines of code | Density |
|---|---:|---:|---:|
| **GCC** | ~9,000 | ~7M LOC | ~1 per 800 |
| **LLVM** | ~13,000 | ~1.4M LOC | ~1 per 110 LOC |
| **My Lab 7 change** | 6 | ~180 LOC in `GroupAction` | ~1 per 30 LOC |

LLVM's 1-per-110 ratio is the working target. My 1-per-30 is *too dense* for the size of the feature, but the more honest framing is: I added six assertions to one method out of a 9-module project that has *zero* assertions elsewhere. The right way to read this is not "Lab 7 was over-asserted" but "JHotDraw as a whole is starved of assertions, and I have started filling that gap in one feature." A Boy-Scout-Rule-style policy of adding an assertion per visit would bring the project to LLVM density over hundreds of commits.

The lecture is also explicit about **when to use assertions**: enable them in *running software that can be recovered by failing early* (web servers, IDEs, build tools), and disable them in *mission-critical* code that must continue rather than recover (avionics, the Mars-landing-stage of a Rosetta-style probe). Surefire runs tests with `-ea` by default; the question of whether to ship JHotDraw with `-ea` on or off would be a deployment decision, not a coding one.

---

### 7.7 The Fragile Test Problem — four sensitivities

This is the lecture's most operationally useful section. *In Agile, these are all changing all the time*, so a fragile test is a test that breaks for the wrong reason:

| Sensitivity | Trigger | Lab 7 evidence |
|---|---|---|
| **Behaviour sensitivity** | Business-logic change | A `canGroup` rule change (e.g. allow 1-figure groups) would break ~6 of my tests. *Expected* fragility. |
| **Interface sensitivity** | Rename / delete a method or window | Renaming `groupFigures` would break my tests at compile time. *Expected*. |
| **Data sensitivity** | Database / fixture changes | Not applicable — my tests build fixtures inline. |
| **Context sensitivity** | OS / time-zone / locale changes | The `ResourceBundleUtil.getBundle("...Labels")` call hits the JVM default locale. A locale switch could fail label-related assertions. *Latent fragility I had not noticed.* |

The third row is the surprise. The `Labels.properties` resource bundle is loaded with the JVM default locale — `Labels_de.properties` exists in JHotDraw too. If a tester ran my Lab 7 suite under `-Duser.language=de`, some of the label-related setup might pick the German strings. *None of my tests assert on label content*, so this is dormant, but it is an example of context sensitivity I would not have spotted without the lecture's framing.

---

### 7.8 Testing under the UI

The lecture's principle: **automate tests at the application layer, not the UI layer**.

```
   Manual Test ────┐
   Automatic Test ─┴──> [Application Layer]
                                │
                                v
                        [Domain Layer]
                                │
                                v
                        [Persistence Layer]
```

The recommendation: route automated tests *below* the Thin Presentation Layer. This is exactly what Lab 9 did — the JGiven scenarios go straight to `GroupAction.actionPerformed` and bypass the menu/key-binding layer entirely. AssertJ-Swing would be the *exception* — the GUI tests have to enter through the presentation layer because that's the layer being tested.

So I now have a name for the architectural decision Lab 9 made: *automate under the UI by default; reach into the UI only for the cases where the UI itself is the SUT.*

---

### 7.9 Fault injection

The lecture's pattern: replace a low-level API call with a wrapper that *can fail on demand*.

```
file = open("/tmp/foo", 'w')
        ↓
file = my-open("/tmp/foo", 'w')
                                 // my-open succeeds 100 times, then fails 1% of calls
```

This tests the *recovery paths* — the `try/catch` blocks, the retry logic, the graceful-degradation code that production almost never exercises in a happy-path test suite. JHotDraw has very few I/O failure paths in the Group/Ungroup feature (no database, no network), so fault injection has lower ROI here than it would in a service-oriented codebase. But the technique generalises: a mocked `Drawing` that returns `null` from `sort()` once every 100 calls would test what happens if Drawing's sort is somehow broken — and my current test suite would not catch that.

---

### 7.10 TDD — the cycle and the "real" version

The TDD cycle:

```
       ┌─→ Red: write a failing test
       │   ↓
       │   Green: make it pass with the minimum code
       │   ↓
       └── Refactor: clean up, with the test as a safety net
       
       Until: no more ideas for tests
```

Two important constraints the lecture flags:
- **One test at a time.**
- **Implement only as much code so that the test does not fail.** If the implementation feels incomplete, add a *new failing test* that forces more code. Don't speculatively over-implement.

The lecture distinguishes **Moving to TDD** (write tests before code, but still hand off to QA at the end) from **Real TDD** (write test, implement, refactor, the developer's own QA loop — defects discovered later become new failing tests, not bugs handed back over a fence). The diagrams make it visual: in "Real TDD" the QA column disappears as an organisational silo and reappears as a *step the developer performs themselves*.

Neither Lab 7 nor Lab 9 was TDD in this strict sense — I wrote tests for an existing 8-year-old codebase, not for code I was about to write. But the *one-test-at-a-time* discipline matched my approach: each of the 24 unit tests was added one at a time, each named after the property it pinned, each verified in isolation before the next.

---

### 7.11 Mock vs Stub vs Spy — the test-doubles taxonomy

The lecture distinguishes three kinds of test doubles, which Lab 7 used somewhat loosely:

| Double | What it is | When to use |
|---|---|---|
| **Stub** | Holds *predefined data*; minimal methods; static. | When the SUT just needs a constant answer from a dependency. |
| **Mock** | Stores method calls; *records and verifies* interactions. The most powerful and flexible. | When the SUT's correctness is in *which methods it called*, not just what it returned. |
| **Spy** | A *partial* mock — wraps a real object and replaces specific methods. | When most of the real behaviour is fine but one method needs to be controlled. |

In Lab 7 I used Mockito's `mock()` exclusively. I never reached for a stub (the simpler option) or a spy (the half-real option). Re-reading [GroupActionTest.setUp](jhotdraw-core/src/test/java/org/jhotdraw/draw/action/GroupActionTest.java#L62-L74) through this taxonomy:

- `editor`, `view`, `drawing` are **mocks** — I verify call sequences with `InOrder`.
- A *stub* would have been sufficient for the `editor` and `view` in the `canGroup` tests (no `verify` is ever called on them in those tests).
- A *spy* on a real `GroupFigure` would have let me keep its `getClass()` behaviour and override only `clone()` — solving the *mockability tax* problem I flagged in Lab 7's reflection.

This is the cleanest articulation of what I would refactor first if I revisited Lab 7.

---

### 7.12 The DateServer pattern — controlling time

The lecture's worked example: *how do you test that "a book is overdue"?* The naive answer is "wait 14 days", which is absurd. The lecture's pattern:

1. **Refactor the time dependency out** — `LibraryApp.getDate()` no longer calls `new GregorianCalendar()`; it calls `dateServer.getDate()`.
2. **Inject the `DateServer`** as a dependency.
3. **In tests, replace the real `DateServer` with a mock** that returns whatever date the test needs.

This is the same principle as *Dependency Inversion* (Lecture 5) applied to *time*. The natural way to write the code (call `new Calendar()`) is the untestable way. The testable form requires a level of indirection. Lab 7's mocking of `DrawingView` is the same pattern — replace a hard-to-control dependency (real Swing view) with a controlled stand-in.

---

### 7.13 Acceptance tests

The lecture closes with **acceptance testing** as the highest-level kind of test:

- *Defined by / with the user, based on requirements.*
- *Traditional:* manual, after delivery, by the customer.
- *Agile:* automatic, *before* the user story is implemented, in JUnit / Fit / JGiven.

The lecture's *Login Admin* use case (Name / Actor / Precondition / Main scenario / Alternative scenarios / Postcondition) is **structurally identical** to the user stories I wrote in [Lab 9](#lab-9--behavior-driven-testing-jgiven-scenarios-for-group--ungroup). The format is the same; only the vocabulary changes (BDD calls it Given-When-Then; XP calls it Main-Alternative-Scenario). This is reassuring — Lab 9's deliverable is the *acceptance-test* layer Lecture 7 is pointing at.

---

### Reflection on Lecture 7 — what changes after seeing the lecture late

**(1) The lecture validates Lab 7's structure without requiring any rework.** Every concept the lecture introduces is either something I applied implicitly (unit tests, white-box, mocks, AAA-style assertions in setup/act/assert form) or something I now recognise as a *named pattern* (the DateServer pattern for controlling time, the mock-vs-stub-vs-spy distinction). Receiving the lecture late was inconvenient in the moment but turned out useful for the reflection: I can audit my own work against the lecture's checklist with hindsight rather than guess.

**(2) The single best operational lesson is the "what is going on?" tree.** Future me will reach for this every time a test fails. *Is the bug in the SUT? In the test? In the spec? In the libraries?* — five seconds of diagnostic discipline saves twenty minutes of chasing the wrong layer. The Mars Climate Orbiter is the kind of memorable failure that anchors the rule.

**(3) The fragile-test taxonomy gave me language for a real risk I had not noticed.** The locale-sensitivity in `Labels.properties` is dormant in my Lab 7 suite but real. I now know what to call it. *Context sensitivity* is the silent failure mode of legacy tests: nobody changes the code; the OS or locale changes and the tests start lying.

**(4) Mock-vs-Stub-vs-Spy maps onto a concrete refactor.** The Lab 7 `getClass()` mockability tax has, in retrospect, the cleanest fix: replace one of my Mockito `mock()` calls with a `spy()` on a real `GroupFigure`. The spy preserves the real `getClass()` answer while letting me stub `clone()`. This is a one-line change that would simplify two existing tests. *Spy is the missing tool from my Lab 7 vocabulary.*

**(5) The acceptance-test parallel between this lecture and Lab 9 makes the course's testing arc explicit.** Lab 7 = unit tests. Lab 9 = acceptance tests via BDD. Lecture 7 = the framing that says these are not duplicates but two strata of the same pyramid. Lecture 9 = the developer-friendly framework that bridges them. Lecture 10 = the textbook's worked example of the same arc on Drawlets. **One arc, four lecture/lab anchors.**

---

> Lecture 8 had no slides released and no associated lab, so this section jumps from Lecture 6 / Lab 7 straight to Lecture 9.

The ninth lecture, *Software Verification — Pragmatic BDD for Java* by Jan Corfixen Sørensen, brings the testing arc that started in Lecture 7 / Lab 7 to its second stage: writing executable specifications that read as *behaviour*, not as code. The previous lab proved the Group/Ungroup feature works at the *unit* level — this lecture is about a different audience: the domain expert, the product owner, the QA engineer who reads tests as documentation rather than as code. The lecture's claim is that there is now a *pragmatic* way to get there in Java — i.e. without leaving the JVM, without learning a new language, and without paying the maintenance cost of classical BDD frameworks.

---

### 9.1 Why BDD? — the problem statement

The lecture opens with a candid critique of the unit-test world I just finished building in Lab 7:

| Problem with conventional unit tests | What it looks like |
|---|---|
| **Many technical and often irrelevant details** | `Mockito.when(view.getSelectionCount()).thenReturn(2)` — Mockito grammar leaks into the readable intent. |
| **The point of the test is often hard to grasp** | A test named `actionPerformed_performsGroup_whenIsGroupingActionAndCanGroup` describes the *implementation* path, not the *behaviour*. |
| **Code duplication** | Every test rewires the editor/view/drawing chain in `setUp()`. |
| **Can only be read by developers** | A non-programmer cannot read JUnit/Mockito source. |
| **Cannot be used as documentation** | The test file is not the specification — it is only the proof. |

I recognise four of the five problems directly in my Lab 7 file. That is the *honest* starting point for taking BDD seriously: it is not a replacement for unit tests, it is the upper layer that unit tests cannot occupy.

---

### 9.2 What BDD is

BDD is defined in the lecture by four properties:

1. **Behaviour is described in a common domain language** understandable by domain experts.
2. **Domain experts and developers collaborate** on defining the behaviour.
3. **Scenarios are executed like normal tests** — i.e. they fail the CI build if behaviour changes.
4. **The result is a living documentation** that cannot rot, because if it rots the build breaks.

The fourth property is the most striking. Static documentation (Confluence pages, design docs, Javadoc) decays the moment the code drifts from it. A BDD scenario *cannot* drift silently — if the production code stops behaving as the scenario describes, the scenario fails. *Documentation that the build refuses to let go stale.*

---

### 9.3 The pancake example

The lecture's running example is, charmingly, a recipe:

```
Scenario: a pancake can be fried out of an egg milk and flour

  Given an egg
    And some milk
    And the ingredient flour
   When the cook mangles everything to a dough
    And the cook fries the dough in a pan
   Then the resulting meal is a pan cake
```

Three properties of this scenario are worth highlighting because they recur throughout the rest of the lecture:

- **No code.** Yet executable.
- **No technology vocabulary.** No `Mockito`, no `@Test`, no `assertEquals`. The vocabulary is `egg`, `milk`, `flour`, `cook`, `pan`.
- **Composition by domain verbs.** *Given*, *When*, *Then* — each describing a phase of behaviour, not a phase of execution.

---

### 9.4 Classical vs developer-friendly BDD frameworks

The lecture is sharp about the trade-off between the two families:

| Family | Examples | Trade-off |
|---|---|---|
| **Classical** | Cucumber (Plain Text + Java), JBehave (Plain Text + Java), Concordion (HTML + Java), Fitness (Wiki + Java), robotframework.org | Pure plain-text scenarios; readable to non-developers, but *two artefacts to maintain* — the `.feature` file and the step definitions in Java. **Additional maintenance cost.** |
| **Developer-friendly** | Spock (Groovy), ScalaTest (Scala), Jnario (Xtend), Serenity* (Java), **JGiven** | Scenarios live in code, in the same language as the system under test. Lower maintenance, but the trade-off is that domain experts can read the *generated reports* but cannot *write* the scenarios themselves. |

JGiven (which Lab 9 will use) is the lecture's specific recommendation for Java. The trade-off is honest: domain experts can read the HTML5 report, but cannot author scenarios. In exchange, developers do not maintain two files and do not learn a new language.

---

### 9.5 JGiven — introduction

The lecture's positioning of JGiven:

- Developer friendly (low maintenance overhead)
- Readable test code (Given-When-Then)
- Modular and reusable test code
- Reports for domain experts
- Open source — [jgiven.org](http://jgiven.org)

The first JGiven scenario the lecture shows is the pancake recipe expressed as a JUnit test:

```java
@Test
public void a_pancake_can_be_fried_out_of_an_egg_milk_and_flour() {
    given().an_egg()
       .and().some_milk()
       .and().the_ingredient("flour");

    when().the_cook_mangles_everything_to_a_dough()
       .and().the_cook_fries_the_dough_in_a_pan();

    then().the_resulting_meal_is_a_pancake();
}
```

The structural insight is that **method names are the specification**. Underscores render as spaces in the generated report. JGiven reads the method name, replaces underscores with spaces, and produces the human-readable scenario in the report — *the same source artefact serves as both code and documentation.*

---

### 9.6 Stage classes

This is the conceptual move that distinguishes JGiven from every other BDD framework, and the lecture flags it explicitly: *"Stage classes are a unique feature of JGiven, not present in any other BDD framework."*

A **stage class** is a class that groups the step methods belonging to one phase of a scenario — typically one stage class per Given, When, and Then.

- A Given stage sets up state.
- A When stage performs the action.
- A Then stage performs assertions.

Each stage class is reusable across many scenarios.

#### 9.6.1 State transfer between stages

State flows between stages through annotated fields:

- `@ScenarioState` — both readable and writable.
- `@ProvidedScenarioState` — *written* by this stage, read by later stages.
- `@ExpectedScenarioState` — *read* by this stage from earlier stages.

A diagram in the lecture shows two parallel state values (`state1`, `state2`) being produced by the Given stage, consumed and transformed into a `result` by the When stage, and finally fed into the Then stage's assertions. The annotations are JGiven's way of making this data-flow explicit and statically declared — no global state, no test-class fields holding test fixtures.

#### 9.6.2 Example stage classes

`GivenIngredients` (the Given stage):

```java
public class GivenIngredients extends Stage<GivenIngredients> {
    @ProvidedScenarioState
    List<String> ingredients = new ArrayList<>();

    public GivenIngredients an_egg() {
        return the_ingredient("egg");
    }
    public GivenIngredients the_ingredient(String ingredient) {
        ingredients.add(ingredient);
        return this;
    }
    public GivenIngredients some_milk() {
        return the_ingredient("milk");
    }
}
```

`WhenCook` (the When stage):

```java
public class WhenCook extends Stage<WhenCook> {
    @Autowired @ScenarioState
    Cook cook;
    @ExpectedScenarioState
    List<String> ingredients;
    @ProvidedScenarioState
    Set<String> dough;
    @ProvidedScenarioState
    String meal;

    public WhenCook the_cook_fries_the_dough_in_a_pan() {
        assertThat(cook).isNotNull();
        assertThat(dough).isNotNull();
        meal = cook.fryDoughInAPan(dough);
        return this;
    }
}
```

`ThenMeal` (the Then stage):

```java
public class ThenMeal extends Stage<ThenMeal> {
    @ExpectedScenarioState
    String meal;

    public void the_resulting_meal_is_a_pan_cake() {
        the_resulting_meal_is_a("pancake");
    }
    public void the_resulting_meal_is_a(String expectedMeal) {
        assertThat(meal).isEqualTo(expectedMeal);
    }
}
```

The pattern is clear: each stage has a *single responsibility* (set up, act, or assert), and its fields encode the contract with adjacent stages. This is SRP from Lecture 5 applied to the test layer.

---

### 9.7 Reports — console and HTML5

JGiven generates two report formats out of the box:

```
Test Class: com.tngtech.jgiven.examples.pancakes.test.SpringPanCakeScenarioTest

  A pancake can be fried out of an egg milk and flour

  Given an egg
    And some milk
    And the ingredient flour
   When the cook mangles everything to a dough
    And the cook fries the dough in a pan
   Then the resulting meal is a pan cake
```

The HTML5 report is more polished — a sidebar of summaries, tags, and class navigation, with each scenario expandable to show its Given/When/Then. The screenshot in the lecture shows 53 scenarios all passing, with tag-based filtering (BrowserTest, Features, Issue) and class-based grouping. *This is the artefact the domain expert reads.*

---

### 9.8 TNG's three-year experience

The lecture closes the JGiven half with a credibility data point: TNG (the company that maintains JGiven) reports three years of production use on a 70-developer Java enterprise project with over 3000 scenarios. The reported outcomes:

- Readability and reusability of test code "greatly improved".
- Maintenance costs of automated tests reduced (acknowledged: no hard numbers).
- Well accepted by developers, easy to learn for new joiners.
- Developers and domain experts collaborate via scenarios.

The fact that the lecturer chose to share this specifically — rather than only the framework's mechanics — signals which property of BDD he considers load-bearing in practice: not the readability per se, but *the cultural change of collaboration*. The scenario becomes the shared artefact between people who would otherwise hand each other Word documents.

---

### 9.9 JGiven summary

The lecture's own summary, condensed:

- Developer friendly
- Highly modular and reusable test code
- Just Java — no further language required
- Easy to integrate into existing test infrastructures (JUnit, TestNG)
- Open Source (Apache 2)
- Maven and Jenkins plugins available
- Nice reports for domain experts
- **Domain experts cannot write scenarios in JGiven** — the honest trade-off, repeated.

---

### 9.10 AssertJ — the assertion library JGiven assumes

The second half of the lecture pivots to **AssertJ**, the assertion library JGiven examples use throughout. The motivation is critical of the alternatives:

> *"JUnit's assertions [are] underpowered from the start. Developers use frameworks like Hamcrest and Fest. [The result is] a confusion of JUnit, Hamcrest and Fest."*

Per the lecture:
- **JUnit assertions** — very simplistic (`assertEquals`, `assertTrue` — that's nearly the whole API).
- **Fest** — abandonware.
- **Hamcrest** — stagnant and ugly (the famous `assertThat(list, hasItems(equalTo(1), equalTo(2)))` is the kind of nested-matcher syntax the lecture has in mind).

#### 9.10.1 Why AssertJ

- Still actively maintained
- Near complete superset of Hamcrest functionality
- Well designed — easy to get started, easy to enhance, easy to read

#### 9.10.2 Basic use

AssertJ's API is built around three ideas:

- `Assertions.assertThat(actual)` — a *type-specific factory method* that returns a different assertion object depending on the type of `actual` (e.g. `StringAssert`, `ListAssert`, `DateAssert`).
- The returned object subclasses `AbstractAssert` and exposes type-specific assertions.
- The API is **fluent** — assertions chain.

```java
@Test
public void shouldProvideAnExample() {
    String actual = "This is a test";
    assertThat(actual).contains("is").startsWith("This");

    String[] actualArray = new String[]{ "This", "is", "a", "test" };
    assertThat(actualArray).contains("is").startsWith("This");
}
```

Note the same `contains` / `startsWith` work polymorphically on both `String` and `String[]`. The fluent style replaces a sequence of separate `assertX` calls with one continuous *sentence about the value*.

#### 9.10.3 Custom Conditions

For predicates that don't fit the built-in API, AssertJ allows defining a `Condition`:

```java
@Test
public void shouldBeEvenlyDivisibleBySix() {
    Condition<Integer> evenDivBySix = new Condition<Integer>() {
        @Override public boolean matches(Integer value) {
            return (value % 6) == 0;
        }
    };
    assertThat(12).is(evenDivBySix);
    assertThat(8).isNot(evenDivBySix);
}
```

The `is` / `isNot` pair reads naturally and the predicate is reusable across tests.

#### 9.10.4 Custom Assertions

For domain types one writes a *custom AbstractAssert subclass*:

1. Subclass `AbstractAssert` for the domain type and implement custom methods like `isInMiddleSchool()`.
2. Subclass `Assertions` to add a factory `assertThat(Student)`.
3. Use it as if it were native: `assertThat(student).isInMiddleSchool()`.

This is the same pattern JHotDraw could apply to `Figure`, `Drawing`, or `CompositeFigure` — e.g. `assertThat(drawing).contains(figure).and().isOrdered()`. Lab 9 will demonstrate one such custom assertion on `Drawing`.

---

### 9.11 AssertJ-Swing — GUI scenario automation

For Swing UIs the lecture introduces **AssertJ-Swing**, which simulates user interaction at the JVM level:

- Simulation of user interaction (clicks, drag-n-drop, keystrokes).
- Reliable GUI component lookup (by type, by name, or custom criteria).
- Support for every Swing component in the JDK.
- Compact, powerful API for functional GUI tests.
- Ability to embed screenshots of failed GUI tests in HTML reports.
- Can be used with either TestNG or JUnit.
- Supports testing violations of Swing's threading rules — itself a non-trivial guarantee.

This is the closest thing in the Java ecosystem to a desktop equivalent of Selenium for the web. For JHotDraw — a Swing application — this is *the* tool for end-to-end behavioural tests of the Draw window. Lab 9 includes one such test as documentation but cannot actually run it because the development environment is headless.

---

### Reflection on Lecture 9 — what it changes for this project

Three lessons that carry directly into Lab 9 and beyond.

**(1) BDD is the missing layer above Lab 7's unit tests.** My Lab 7 catalogue has 24 unit tests, each named with the *implementation* idiom (`groupFigures_failsAssertion_whenFiguresCollectionIsEmpty`). A non-programmer cannot read that, and even I had to write a 5-column traceability table to make the catalogue legible. BDD does not replace those tests — it sits *above* them and asks a different question: *what does the Group/Ungroup feature do, told as a story?* Lab 9 will produce exactly two or three short stories; the unit tests remain the proof.

**(2) The stage-class pattern is SRP applied to tests.** Lecture 5's SRP and Lecture 6's "classes should be small / one responsibility" map directly onto JGiven's stage classes: *one stage for Given, one for When, one for Then.* The fact that the test framework *enforces* this separation is the same kind of guard-rail that the type system gives to production code — you cannot accidentally put an assertion into a Given stage because the Given stage doesn't have the assertion methods. This is the strongest argument for JGiven over hand-rolled BDD.

**(3) The honest trade-off of developer-friendly BDD.** Both the lecture and the JGiven summary repeat the same point: *domain experts cannot write JGiven scenarios.* This matters for the JHotDraw context because there is no domain expert separate from the developer — JHotDraw is open-source infrastructure code, not a domain application. The benefit of JGiven for this project is therefore not "domain expert collaboration" but **living documentation of behaviour at the right level of abstraction**. The HTML5 report is the artefact a new contributor to JHotDraw would read first to understand what Group/Ungroup does — and the build refuses to let that report rot.

---

## Lab 9 — Behavior-Driven Testing: JGiven Scenarios for Group / Ungroup

### Objectives

This lab follows the *TestLab2 — Behavior Driven Testing* handout, which expands the testing arc from Lab 7's unit-level work into the behavioural / specification layer that Lecture 9 introduced. The portfolio checklist has three concrete items:

1. **Map your User Stories to BDD Given-When-Then scenarios.**
2. **Use JGiven to automate your BDD scenarios.**
3. **Use AssertJ for domain-specific assertions, and AssertJ-Swing for Swing scenarios.**

I continued with the same Group / Ungroup feature I have worked on since Lab 2. The output of this lab is therefore four readable scenarios that automate the user-facing behaviour of that feature, sitting *on top of* (not replacing) the 24 unit tests from Lab 7.

---

### Step 1 — User Stories

The lab handout gives the canonical user-story template:

> *As a [user type], I want [some goal] so that [some reason].*

For Group / Ungroup, the three user stories I wrote down — each one will map to at least one scenario:

| ID | User Story |
|---|---|
| **US-1** | As a **Draw user**, I want to **group multiple selected figures** so that **I can move and transform them as a single unit**. |
| **US-2** | As a **Draw user**, I want to **ungroup a previously grouped figure** so that **I can edit its children independently**. |
| **US-3** | As a **Draw user**, I want **the Group menu item to be disabled when only one figure is selected**, so that **I cannot create meaningless single-figure groups by accident**. |

The third user story is a *negative* one — it expresses what the system should *not* let me do. Including it explicitly is the BDD equivalent of writing a boundary-case unit test, and it forced me to write the boundary scenario `invoking_group_with_one_selected_figure_does_not_change_the_drawing` rather than leaving the guard implicit.

---

### Step 2 — Map each user story to a Given-When-Then scenario

The mapping follows the lecture's pattern (and the handout's Figure 1 calculator example): each user story produces one or more scenarios; each scenario reads as a single sentence broken into three phases.

| User Story | Scenario | Given | When | Then |
|---|---|---|---|---|
| US-1 | grouping two selected rectangles replaces them with a single group of two | 2 rectangle figures on the canvas; all selected | user invokes the group action | drawing contains exactly one group with 2 rectangle children |
| US-1 | grouping three selected rectangles produces a single group of three | 3 rectangle figures on the canvas; all selected | user invokes the group action | drawing contains exactly one group with 3 rectangle children |
| US-2 | ungrouping a group of two restores two rectangles to the drawing | a group containing 2 rectangle figures; the group is selected | user invokes the ungroup action | drawing contains exactly 2 rectangle figures and no groups |
| US-3 | invoking group with one selected figure leaves the drawing unchanged | 2 rectangle figures on the canvas; only the first is selected | user invokes the group action | drawing is unchanged with 2 figures |

The two US-1 scenarios are intentionally near-duplicates. JGiven's parameter substitution (`$_rectangle_figures_on_the_canvas(int)`) makes them legible as separate stories in the report — *the same behaviour, parameterised by count*. This is the cheapest demonstration that the scenarios are reusable, which is one of the JGiven properties Lecture 9 emphasised.

---

### Step 3 — Environment

| Tool | Version | Purpose |
|---|---|---|
| JGiven JUnit | **1.3.1** | BDD framework — `ScenarioTest`, `Stage`, annotations, report generation |
| AssertJ Core | **3.25.3** | Domain-language assertions inside the Then-stage |
| AssertJ-Swing JUnit | **3.17.1** | GUI-level scenarios (documentation only — see Step 7) |
| Maven Surefire `argLine` | `--add-opens=java.base/java.lang=ALL-UNNAMED` | Allows JGiven's ByteBuddy proxy generator to run on JDK 25 |

The dependencies were added to [jhotdraw-core/pom.xml](jhotdraw-core/pom.xml) immediately after the JUnit/Mockito block from Lab 7. The Surefire `argLine` was a forced addition I will return to in the reflection (Step 8).

---

### Step 4 — Stage classes (the JGiven SRP move)

Per Lecture 9's emphasis that *stage classes are the unique feature of JGiven*, I wrote one stage class per phase. They live in [jhotdraw-core/src/test/java/org/jhotdraw/draw/action/bdd/](jhotdraw-core/src/test/java/org/jhotdraw/draw/action/bdd/).

**Given-stage: [GivenADrawing](jhotdraw-core/src/test/java/org/jhotdraw/draw/action/bdd/GivenADrawing.java).** Builds a real `DefaultDrawing` (so the Then-stage can inspect the actual figure tree), but mocks `DrawingEditor` and `DrawingView` because the Swing event-dispatch infrastructure they hide cannot run headless. The stage's vocabulary:

```java
public GivenADrawing a_drawing_editor()                              { ... }
public GivenADrawing $_rectangle_figures_on_the_canvas(int count)    { ... }
public GivenADrawing a_group_containing_$_rectangle_figures(int n)   { ... }
public GivenADrawing all_figures_are_selected()                      { ... }
public GivenADrawing only_the_first_figure_is_selected()             { ... }
public GivenADrawing the_group_is_selected()                         { ... }
```

The fields are annotated `@ProvidedScenarioState` so the When- and Then-stages can read them. The `$` placeholder in method names is JGiven's parameter substitution — at report-rendering time it is replaced by the integer argument.

**When-stage: [WhenTheUser](jhotdraw-core/src/test/java/org/jhotdraw/draw/action/bdd/WhenTheUser.java).** Receives the editor via `@ExpectedScenarioState` and triggers the action exactly the way the GUI would:

```java
public WhenTheUser invokes_the_group_action() {
    new GroupAction(editor).actionPerformed(
        new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "group"));
    return self();
}
```

This is the only stage that *constructs* `GroupAction` / `UngroupAction`. Doing it here rather than in `setUp()` matches the natural reading order of a BDD scenario: *the user has a drawing first, and then invokes an action.*

**Then-stage: [ThenTheDrawing](jhotdraw-core/src/test/java/org/jhotdraw/draw/action/bdd/ThenTheDrawing.java).** Receives the drawing via `@ExpectedScenarioState` and uses AssertJ to assert against its state:

```java
public ThenTheDrawing contains_exactly_one_group_with_$_rectangle_children(int expected) {
    assertThat(drawing.getChildren())
            .as("drawing should now contain a single composite figure")
            .hasSize(1);
    Figure only = drawing.getChildren().get(0);
    assertThat(only)
            .as("the remaining figure should be a CompositeFigure")
            .isInstanceOf(CompositeFigure.class);
    CompositeFigure group = (CompositeFigure) only;
    assertThat(group.getChildren())
            .as("the group should contain the originally-selected figures")
            .hasSize(expected)
            .allMatch(f -> f instanceof RectangleFigure);
    return self();
}
```

Three things to note in this method:

- **AssertJ's fluent chaining** — `assertThat(...).hasSize(expected).allMatch(...)` is a single sentence about the collection.
- **`.as("...")`** describes the assertion in domain terms, so the failure message reads as a domain statement, not as `expected: 1 but was: 2`.
- **`allMatch(f -> f instanceof RectangleFigure)`** uses an inline predicate where the Java type system already constrains the answer. The lecture's "custom Condition" mechanism could lift this into a named `Condition<Figure> isRectangle = ...`; I left it inline for now because the predicate appears only once.

---

### Step 5 — The scenario class

The four scenarios live in [GroupUngroupScenarioTest](jhotdraw-core/src/test/java/org/jhotdraw/draw/action/bdd/GroupUngroupScenarioTest.java). It extends JGiven's `ScenarioTest<Given, When, Then>` parameterised by the three stage classes, which wires the `given()`, `when()`, `then()` factory methods to the right stage instances.

```java
public class GroupUngroupScenarioTest
        extends ScenarioTest<GivenADrawing, WhenTheUser, ThenTheDrawing> {

    @Test
    @Description("US-1: grouping two selected rectangles merges them into a single group")
    public void grouping_two_selected_rectangles_replaces_them_with_a_single_group_of_two() {
        given().a_drawing_editor()
           .and().$_rectangle_figures_on_the_canvas(2)
           .and().all_figures_are_selected();

        when().invokes_the_group_action();

        then().contains_exactly_one_group_with_$_rectangle_children(2);
    }

    // ...three more, one per row of the table in Step 2.
}
```

Each scenario reads top-to-bottom as a single sentence in three phases. The `@Description` annotation lets the *human-readable* sentence diverge from the *method name*, which is useful when the user-story text is longer than what Java identifier rules allow.

---

### Step 6 — Run

```
/tmp/maven/bin/mvn test -pl jhotdraw-core --no-transfer-progress
```

The Surefire console output now contains, in addition to the unit-test counts, the *rendered* JGiven scenarios:

```
 US-1: grouping two selected rectangles merges them into a single group

   Given a drawing editor
     And 2 rectangle figures on the canvas
     And all figures are selected
    When invokes the group action
    Then contains exactly one group with 2 rectangle children

 US-2: ungrouping a group restores the children into the drawing

   Given a drawing editor
     And a group containing 2 rectangle figures
     And the group is selected
    When invokes the ungroup action
    Then contains exactly 2 rectangle figures and no groups

 US-3: invoking group with only one selected figure leaves the drawing unchanged

   Given a drawing editor
     And 2 rectangle figures on the canvas
     And only the first figure is selected
    When invokes the group action
    Then is unchanged with 2 figures

[INFO] Tests run: 30, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

JGiven also wrote JSON reports under `jhotdraw-core/target/jgiven-reports/json/` — one JSON file per scenario class:

```
target/jgiven-reports/json/org.jhotdraw.draw.action.bdd.GroupUngroupScenarioTest.json
```

These JSON files are the input that the `jgiven-maven-plugin` consumes to produce the HTML5 report shown in Lecture 9. Wiring the HTML plugin into the build would be a one-line plugin entry, but is not necessary for the *scenarios themselves* to be living documentation — the console output already is.

---

### Step 7 — AssertJ-Swing (documented but not runnable here)

The handout's third bullet says: *"For Swing applications use the [AssertJ-swing] to automate the Scenarios."* AssertJ-Swing simulates real user interaction (clicks, drags, menu navigation) against a real Swing JFrame. **This environment is headless** — the project memory I built up in earlier labs already notes that the remote VSCode terminal lacks display access. AssertJ-Swing requires an active display server (X11 / Wayland) and cannot run in this environment.

To keep the *documentation* of the GUI-level scenario in the repository for when this lab is reproduced on a local workstation, I added [DrawAppSwingScenarioTest](jhotdraw-core/src/test/java/org/jhotdraw/draw/action/bdd/DrawAppSwingScenarioTest.java). The class is permanently `@Ignore`d in this environment with an explanatory message:

```java
@Ignore("AssertJ-Swing needs a real Swing display; this terminal is headless.")
public class DrawAppSwingScenarioTest {

    @Test
    public void user_can_group_two_drawn_rectangles_via_the_edit_menu() throws Exception {
        // Step 1: launch the Draw application on the Swing EDT.
        //   GuiActionRunner.execute(() -> Main.main(new String[0]));
        // Step 2: locate the main frame:
        //   window = WindowFinder.findFrame("Draw").using(robot);
        // Step 3: simulate the user drawing two rectangles
        //   (toolbar selection + two drag operations)
        // Step 4: select-all (Ctrl+A), then Edit -> Group
        //   window.menuItemWithPath("Edit", "Group").click();
        // Step 5: assert the resulting figure tree
        //   contains a single CompositeFigure with two RectangleFigure children
    }
}
```

The test class compiles against the AssertJ-Swing-JUnit dependency now on the POM, but is skipped at runtime. The five-step plan inside is a literal translation of the same scenario US-1 expresses at the JGiven level — *the JGiven scenario and the AssertJ-Swing scenario describe the same behaviour, at two different layers of the testing pyramid.*

---

### Step 8 — A reproducibility detail worth documenting

When I first ran the build with JGiven on the classpath, every scenario failed with:

```
Caused by: java.lang.UnsupportedOperationException: Cannot define class using reflection:
    Unable to make protected java.lang.Package java.lang.ClassLoader.getPackage(java.lang.String)
    accessible: module java.base does not "opens java.lang" to unnamed module @39ba5a14
```

JGiven 1.3.1 bundles an older ByteBuddy (the bytecode-generation library it uses to build dynamic proxies of stage classes) that does not know about JDK 25's strict module boundaries. The fix is a one-line Surefire `argLine`:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <argLine>--add-opens=java.base/java.lang=ALL-UNNAMED</argLine>
    </configuration>
</plugin>
```

I noted this not because the workaround is interesting, but because the *root cause* is: a library released against Java 8 (JGiven 1.3.1) runs against a Java 25 JVM, and the JPMS module system actively blocks reflection patterns that worked silently under Java 8. The same project that built cleanly under JDK 8 needs explicit `--add-opens` to keep working under JDK 25. This is a maintenance signal: every JDK upgrade is a potential cascade of `--add-opens` flags on legacy libraries.

---

### Test catalogue

For traceability, the user-story → scenario mapping in code form:

| Test method | User story | What it proves |
|---|---|---|
| `grouping_two_selected_rectangles_replaces_them_with_a_single_group_of_two` | US-1 | Group action merges two selected rectangles into a single composite figure. |
| `grouping_three_selected_rectangles_replaces_them_with_a_single_group_of_three` | US-1 | Group action scales beyond the smallest non-trivial case. |
| `ungrouping_a_group_of_two_restores_two_rectangles_to_the_drawing` | US-2 | Ungroup action is the inverse of Group at the drawing level. |
| `invoking_group_with_one_selected_figure_does_not_change_the_drawing` | US-3 | Group action is correctly disabled by the `canGroup()` guard when the precondition fails. |
| `user_can_group_two_drawn_rectangles_via_the_edit_menu` | US-1 (GUI) | (`@Ignore`d) AssertJ-Swing layer of US-1 — runnable on a display. |

After this lab, the jhotdraw-core test suite contains:

- **2** pre-existing TestNG tests
- **24** JUnit 4 unit tests from Lab 7
- **4** JGiven BDD scenarios from this lab
- **1** `@Ignore`d AssertJ-Swing scenario for documentation

Total runnable: **30 tests, 0 failures.**

---

### Reflections

**(1) The user-story discipline produced one test I would not have written otherwise.** US-3 — *"the Group menu item should be disabled with one figure selected"* — only became a scenario *because I wrote the user story first*. In Lab 7 I had a unit test for the boundary case (`canGroup_returnsFalse_whenSelectionHasExactlyOneFigure`), but I had not framed it as a behavioural requirement. The BDD discipline forces the *intent* to come before the *implementation*, and the intent for US-3 is the *absence* of an action — a kind of test that is easy to omit when one only reads the production code.

**(2) JGiven's report is the documentation Lab 4's reflection asked for.** [Lab 4 reflected](#lab-4--refactoring-lab-group--ungroup-prefactoring) that *the deferred refactorings could only be done after tests existed.* [Lab 7](#lab-7--testing-lab-unit-tests-for-group--ungroup) added the tests but in implementation-flavoured language. The JGiven report is the version of that documentation that a *new contributor* to JHotDraw could read first: *"what does Group/Ungroup do?"* — and the answer comes back as four English sentences, not as 24 method names. This is the gap BDD fills that unit testing alone cannot.

**(3) Stage classes are SRP applied to tests — confirmed by a real edit.** While writing the Then-stage I was tempted to put a setup line in it: *"if drawing is null, create one."* The framework's structure stops me — the Then-stage has no `@ProvidedScenarioState` for `drawing`, only `@ExpectedScenarioState`. The compiler wouldn't even let me assign to the field. This is the *guard-rail* version of SRP I noted in the Lecture 9 reflection: the framework's type structure *prevents* the accidental violation that prose advice ("classes should have one responsibility") only suggests.

**(4) The `--add-opens` workaround is a Lecture 1 *conformity* difficulty in microcosm.** Lecture 1 listed *conformity* as one of Brooks's essential difficulties — software must conform to its environment, which keeps moving. JDK 25's strict module boundaries are exactly that moving environment, and JGiven 1.3.1 is exactly the kind of legacy library that must be coaxed into conformity. Documenting the fix in the POM is the responsible move; pretending it isn't there is the start of code decay.

**(5) The same behaviour exists at two layers.** US-1's two layers — the JGiven scenario (runs everywhere) and the AssertJ-Swing scenario (runs on a display) — are not duplication. They are two tests of the *same behaviour* at two *different layers of the testing pyramid*. The JGiven scenario locks in the domain logic; the AssertJ-Swing scenario would lock in the menu wiring, key bindings, and Swing focus state that the JGiven scenario consciously mocks away. Both tests can fail and the failure tells different stories — one says *"the algorithm is broken"*, the other says *"the menu binding is broken"*. The lecture's recommendation to use AssertJ-Swing for Swing applications is therefore correct *in addition to*, not *instead of*, JGiven.

---

## Lecture 10 — Example of Software Change and Conclusion of the Change Process

> Lecture 10 has no associated lab. It is the *capstone* lecture of the course: half of it is a worked example that walks Rajlich's full phased model end-to-end on a real codebase, and the other half closes the model with the *Conclusion* phase that completes every change cycle. Together the two halves are the textbook version of what the last nine labs did on JHotDraw.

The lecture combines two chapters of Václav Rajlich's *Software Engineering: The Current Practice*: **Chapter 17 — Example of software change** (38 slides, a full worked example) and **Chapter 11 — Conclusion of software change** (10 slides, the final phase of the phased model). For the portfolio I treat them as one section because the example deck ends *exactly* where the conclusion deck begins.

---

### Part A — Example of Software Change (Rajlich Chapter 17)

#### 10.A.1 The system under study — Drawlets

The example uses **Drawlets**, a small drawing-framework application chosen specifically because it is in the same family of system as JHotDraw:

| Property | Drawlets (lecture) | JHotDraw (this portfolio) |
|---|---|---|
| Domain | Adds a graphical drawing canvas to a host application | Adds a structured 2D graphics editor to a host application |
| Original authors | Kent Beck, Ward Cunningham (then ported to Java) | Erich Gamma, then Werner Randelshofer (multiple rewrites) |
| Scale | 100+ classes, 35 interfaces, 40,000 LOC | ~9 modules, ~hundreds of classes, similar order of magnitude |
| Sample / host app | `SimpleApplet` (browser applet) | `Draw` / `SVG` / `Net` / `Teddy` / `Pert` (Swing main classes) |
| Drawable figures | lines, free-hand lines, rectangles, rounded rectangles, triangles, pentagons, polygons, ellipses, text, images | rectangle, ellipse, line, text, group, … (same family) |
| Description | "perfect API" | classic Swing teaching framework |

The deliberate parallel matters: the lecture is implicitly saying *"the example you are about to see is the textbook version of the work you have been doing all semester."* The architecture diagram on slide 5 (the *Top classes* slide) — `SimpleApplet`, `DrawingCanvas`, `Figure`, `AbstractFigure`, `Tool`, `SelectionTool`, `ConstructionTool`, `ShapeTool`, `RectangleTool`, `EllipseTool` — is structurally the same diagram I drew for JHotDraw's Group/Ungroup feature in [Lab 2](#lab-2--change-initiation-and-concept-location).

#### 10.A.2 The change request

> *Implement an owner for each figure. An owner is the user who put the figure onto the canvas, and only the owner should be allowed to modify it. At the beginning of a session, the users input their ID and password and they are the owners of all figures that were created during the session. This change will make SimpleApplet more versatile and useful — support for cooperative work.*

This is **identical in structure** to my Lab 2 / Lab 3 change request on JHotDraw's Group/Ungroup: a single-paragraph functional change that touches a vertical slice across the figure model, the tool model, and the user-input layer.

#### 10.A.3 Concept location — the three-way classification

The lecture extracts the nouns and verbs from the change request and classifies them:

| Concept | Irrelevant | External (input from user / environment) | Significant (must be located in code) |
|---|:---:|:---:|:---:|
| implement | ✗ |   |   |
| owner |   | ✗ |   |
| **figure** |   |   | ✗ |
| user | ✗ |   |   |
| **canvas** |   |   | ✗ |
| allowed | ✗ |   |   |
| modify | ✗ |   |   |
| beginning | ✗ |   |   |
| session |   |   |   |
| input | ✗ |   |   |
| ID |   | ✗ |   |
| password |   | ✗ |   |
| created | ✗ |   |   |

The two **significant** concepts are *figure* and *canvas*. Everything else is either irrelevant boilerplate (verbs like *implement*, *modify*, *allowed*) or external input (*owner*, *ID*, *password* — these come in through the UI, not the codebase). The two significant concepts are the search targets for concept location — *exactly* the [SUR3/SUL3 → Step 3 of concept location](#lab-2--change-initiation-and-concept-location) I performed in Lab 2.

#### 10.A.4 Concept location — wrong way, backtrack, right way

The lecture's slides 10–12 walk through the **trial-and-error nature** of concept location. The same diagram is shown three times with different highlights:

- **Slide 10 — Wrong way:** the developer starts at `SimpleApplet`, follows the link to `StylePalette`, `ToolBar`, `ToolPalette` (the UI cluster) and ends up nowhere useful.
- **Slide 11 — Backtrack:** the developer recognises the dead end and **greys out** the explored-but-irrelevant classes.
- **Slide 12 — Right way:** the developer restarts from `SimpleApplet` and this time follows the link *down* to `DrawingCanvas` — the green node — which is the actual location of the *canvas* concept.

The lecture is explicit that this is not a failure mode but the *normal* shape of concept location. The same pattern reappears for *figure*: slide 13 shows the wrong path (`DrawingCanvas` → `SimpleDrawingCanvas`), slide 14 backtracks, slide 15 shows the right path — `SimpleDrawingCanvas` → `Figure` interface → `AbstractFigure` (highlighted red as the location of the concept).

I want to record explicitly: **this is the same pattern I lived through in [Lab 2](#lab-2--change-initiation-and-concept-location)**, where my first attempt to locate the Group concept led through `Toolbar` / `Action` infrastructure before I traced it down to `GroupAction` and `GroupFigure`. The backtracking is not a sign that I did concept location *wrong* — it is the form concept location *takes*.

#### 10.A.5 Actualization — adding the new classes

Slide 16 (*Actualization*) introduces two new blue classes:

- **`OwnerIdentity`** — a new data class that owns an ID + password pair.
- **`SimpleListener`** — a new collaborator that subscribes to figure-modification events and rejects modifications from a non-owner.

The red classes (`AbstractFigure` and `SimpleDrawingCanvas`) are the ones that will be *modified*. The blue ones are *added*. This colour-coding maps onto the same distinction I used in [Lab 3's impact-set table](#lab-3--continuous-integration-and-impact-analysis): *which classes are touched* vs. *which classes are added* — Rajlich's diagram makes the same split visible at the class level.

#### 10.A.6 Change propagation — the actual mechanic of OO change

The most important set of slides in the example deck is the **change propagation walk** (slides 17–24, *Propagation 1* through *Propagation — done*). Each slide shows the developer making one local edit and a *coloured halo* spreading from the edited class to the classes that now also need to be updated because their *contract with the edited class has changed*.

The pattern at each step:
- **Red** = currently being edited.
- **Orange** = inconsistent with red (impact, needs propagation).
- **Green** = already updated and consistent again.
- **Grey** = explored but not impacted.

The walk:

1. **Propagation 1** — modify `SimpleDrawingCanvas`'s figure-handling methods → orange ripple to `CanvasTool` (callers) and `SimpleApplet` (creator).
2. **Propagation 2** — update `SimpleApplet` → ripple to `StylePalette`, `ToolBar`, `ToolPalette` (its UI children).
3. **Propagation 3** — update `SimpleApplet` (still ripple front) → `StylePalette` etc. become grey (explored, no further change).
4. **Propagation 4** — update `LocatorConnectionHandle`, `StylePalette`, `SelectionTool`, `ConstructionTool`, `LabelTool`.
5. **Propagation 5** — update `PrototypeConstructionTool`; `ShapeTool` becomes the new orange front.
6. **Propagation 6** — `RectangleTool`, `EllipseTool`, `RectangularCreationTool`, `PG_RectImageTool` become orange (the four leaf tools).
7. **Propagation — done** — all of the original red classes plus the four leaf tools have been propagated and are now consistent again.

This is the *mechanic* I had to apply by hand in [Lab 3](#lab-3--continuous-integration-and-impact-analysis) when I computed the impact set for Group/Ungroup. The lecture's visualisation is more honest than the table I produced — the ripple front *moves over time*, and a class can become red, then orange (because something it depends on was just changed), then green. My Lab 3 table had a flat "in impact set / not in impact set" column; the lecture shows that the right model is a *time-ordered wave*.

#### 10.A.7 The testing story — the bridge to Labs 7 and 9

The lecture pivots from concept location and propagation to *testing* (slides 25–29), and this is where it loops back to my Labs 7 and 9 in a way I had not previously seen:

| Slide | Content | Connection to my work |
|---|---|---|
| 25 — *Unit Tests* | 385 unit tests, 1369 assertions, 4800 lines of test code | My [Lab 7](#lab-7--testing-lab-unit-tests-for-group--ungroup) added 24 unit tests for one feature. Rajlich's project has 385 because the test discipline started early. |
| 26 — *Functional tests* | 141 functional test cases (Draw / Select / Move / …) | These are *exactly* the behavioural cases [Lab 9](#lab-9--behavior-driven-testing-jgiven-scenarios-for-group--ungroup) captured for Group / Ungroup as JGiven scenarios. |
| 27 — *Creation of Acceptance tests* | *"Tool JGiven and Mockito used to run the functional tests"* | **The lecture explicitly names the two libraries I added to JHotDraw in Labs 7 and 9.** This is the textbook validating the practical lab tool choice. |
| 28 — *Phase of actualization* | New unit tests for new classes; new functional tests for new functionality; *old tests that were impacted by the change were updated*. | Maps onto what I would have done if Lab 4's *Replace Conditional with Polymorphism* refactor had not been deferred. |
| 29 — *Test suite maintenance* | Unaffected old tests kept as regression; obsolete tests removed; new-feature tests added | The hygiene rule that keeps test code from rotting — explicit in the lecture, implicit in everything I did from Lab 4 onward. |

Slide 27 is the most striking. The textbook (Rajlich 2012) recommends **JGiven + Mockito** for acceptance testing — the exact pair I added to JHotDraw in Labs 7 and 9 without knowing the lecture would later validate that choice. The course's own materials confirm that the test-tooling decision I made independently is *the same decision the textbook makes for the same problem*.

#### 10.A.8 The numerical result of the example change

Slide 30 (*Results*) gives the hard numbers:

| Metric | Value |
|---|---|
| Baseline production code | 17,800 lines |
| Baseline unit-test code | 4,800 lines |
| **Test-to-production ratio (baseline)** | **≈ 27%** |
| Production code modified by this change | 91 lines (0.5% of baseline) |
| Test code modified by this change | 124 lines (2.5% of test baseline) |
| **Test lines modified per production line modified** | **≈ 1.4** |

Two readings of this number:

1. **The change touched five times more of the test code (proportionally) than of the production code.** The test code is *thinner* than the production code (~27% of total) but *more sensitive* to change — every production edit ripples into 1.4 lines of test edit. This is the *hidden tax* of having tests at all: they make a change more expensive in the short term and infinitely cheaper in the long term.
2. **The ratio 1.4 is the realistic target for my own labs.** In Lab 4 the prefactoring touched ~30 production lines and required updating ~0 test lines (because almost no tests existed — Lab 3's CI finding). After Labs 7 and 9, the same prefactoring *would* now ripple into the unit + scenario tests, and 1.4 test lines per production line would be a healthy ratio rather than a sign of over-testing.

#### 10.A.9 The refactoring section — same lesson as Lab 4

Slides 31–37 cover the *refactoring* part of the same change. The lecture demonstrates two refactorings whose *purpose is to shorten the change propagation*:

- **Move function (slide 33–34):** move the duplicated `basicNewFigure(...)` logic into the base class `ConstructionTool` (highlighted yellow on slide 34's diagram). The result: fewer classes touched by the next propagation.
- **Splitting roles (slides 35–36):** the function `move(...)` in `AbstractFigure` was used for *two* roles — user-driven moves (must check identity) and creation-time moves (no identity check). The lecture splits it into `move(...)` + `secureMove(...)`. Only one of the two needs to change.

Slide 37 — *Numerical data* — quantifies the impact:

| | No refactoring | Move function | Splitting roles |
|---|:---:|:---:|:---:|
| Classes added | 2 | 2 | 2 |
| Interfaces modified | 1 | 1 | 1 |
| **Classes modified** | **13** | **8** | **5** |
| LOC modified | 91 | 95 | 87 |

The headline: **classes modified drops from 13 to 5 (–62%) by applying these two refactorings**. The line count barely moves (91 → 87), so the refactoring does not reduce the *amount* of code written — it reduces the *scattering*. This is the operational definition of "refactoring shortens change propagation" the lecture is selling.

Slide 38 — *Conclusions* — adds a candid trade-off: splitting roles creates *new* test methods (the original tests now don't cover both code paths), so test code is duplicated. *"More effort is required to create new tests compared to the effort required to adapt existing tests to their changed implementation."* This is the same trade-off that made me defer *Replace Conditional with Polymorphism* in [Lab 4](#lab-4--refactoring-lab-group--ungroup-prefactoring) — the production-side refactor was small; the test-side cost was large.

---

### Part B — Conclusion of Software Change (Rajlich Chapter 11)

The second half of the lecture closes the phased model. Every change ends in the **Conclusion** phase — the orange box at the bottom of the V-shaped phase diagram (Initiation → Concept Location → Impact Analysis → Prefactoring → Actualization → Postfactoring → Conclusion), with *Verification* spanning the right-hand side of the V.

> *"The last phase of software change. The activities depend on the specific software process."*

The conclusion phase has three sequential steps:

#### 10.B.1 The three steps

```
   Commit      →      New baseline      →      New release
```

| Step | What happens |
|---|---|
| **Commit** | Programmers return their updated code to the configuration-management repository; merge / resolve conflicts. |
| **New baseline** | The repository state after a thorough test pass — *the new "known-good"*. |
| **New release** | A baseline that is exposed to end users. Not every baseline becomes a release; releases are gated by separate business decisions. |

#### 10.B.2 The New Baseline phase

The lecture is sharp about two qualities a new baseline must have:

- **A thorough test pass guarantees the baseline is as bug-free as possible.** This is what the [CI workflow](#lab-3--continuous-integration-and-impact-analysis) I set up in Lab 3 attempts to mechanise — every push runs `mvn test`, and the build refuses any PR whose tests fail.
- **The new baseline represents a *progress* of the project, not a *regression*.** This is exactly the F.I.R.S.T. *Repeatable* property from Lecture 9 applied to the repository, not just to a single test.

Practical observations the lecture adds:
- Baseline testing is *long* — often done overnight or over a weekend.
- A *specialised testing team* often conducts it (not the same engineers who wrote the code).

#### 10.B.3 Baseline frequency — the engineering trade-off

| Frequency | Symptom |
|---|---|
| **Too rare** | Large accumulation of bugs by the next baseline → testing becomes a *bisection problem* across many changes. |
| **Too frequent** | Unnecessary overhead — testing, sign-off, release-notes work for changes that have not yet stabilised. |

The lecture's framing: *"The frequency depends on the size of the program and required quality."* The implicit slider runs from *continuous delivery* (frequent, tiny baselines) on one end to *waterfall* (very rare, very large baselines) on the other. Modern continuous-integration setups try to push this slider as far towards *every commit is a baseline* as the test suite allows. JHotDraw's CI (every PR runs `mvn -B test`) is at the *every change* end.

#### 10.B.4 Baseline as a deadline — the social layer

A baseline is not just a technical state but a **social contract**:

- The baseline date is the **deadline to commit** — the time at which baseline testing starts.
- A programmer who misses the deadline submits *by the next baseline*. This costs them extra rebase / re-test work, and *"management knows how often a particular programmer missed the deadline"*. Missing it repeatedly may require an explanation.

This is the social half of CI culture that the technical CI workflow alone does not capture. The CI tooling enforces "the build is green" — the human process enforces "the build is green *on time*".

#### 10.B.5 Bugs in the baseline — the two outcomes

The lecture's split:

| Severity | Outcome |
|---|---|
| **Minor bugs** | Testing team still certifies the baseline. The bugs go onto the *stack of bug reports* and are fixed in future changes. |
| **More serious bugs** | The testing team can reject the buggy commits. In severe cases the **entire new-baseline work is rejected** — *no new baseline is created* — and all the in-flight work is invalidated or postponed. The testing team can identify which programmer committed the buggy file; *"reputation of these programmers suffers."* |

This is a sharper statement of the same principle as the *Boy Scout Rule* from Lecture 6 — the consequences for breaking the baseline are not just technical, they are reputational. The technical cost of CI failure is recoverable; the reputational cost of repeatedly breaking the build is not.

#### 10.B.6 The stakeholder's role — acceptance testing

A second, separate testing pass exists *outside* the engineering team:

- **Acceptance testing** is functional testing performed *by the stakeholders* (product owners, customers, domain experts).
- It thoroughly tests software functionalities from a user perspective.
- It gives stakeholders information about *project progress*.
- It is the gate at which **stakeholders approve software for the release**.

This is the same "domain expert reads the test" claim that Lecture 9 made about BDD reports. Acceptance testing is the *manual* form; BDD scenarios with JGiven HTML reports are the *automated* form of the same idea — both produce an artefact a non-developer can sign off on.

#### 10.B.7 The new-release phase

From baseline to release is a *separate* decision:

- *"From time to time, the programmers release the baseline code to the users."* The interval is a *business* decision, not (only) a technical one.
- Releasing requires *substantial extra work* over and above the baseline — packaging, release notes, signing, distribution channels, documentation updates, version bumps.

The lecture introduces the **"versioned model of software lifespan"** — a release pattern that combines:

- **Less frequent large releases** — major versions (`AwesomeApp 4.2`) downloaded and installed by the user.
- **More frequent small releases** — patches incorporated into the user's installed program via a *merge* tool.

This is the **trunk + patches** distribution pattern that LTS Linux distros, semantic-versioned libraries, and even Maven artifact repositories use: a minor version pinned in `pom.xml` (`9.1-SNAPSHOT` in JHotDraw's case) plus patch releases that consumers can opt into via a dependency-version bump.

---

### Reflection on Lecture 10 — the course in one slide

This lecture is the **capstone** in two senses:

**(1) It is the diagram of what every lab in this portfolio actually was.** Slide 1 of Part A shows Rajlich's phase diagram with *Conclusion* highlighted at the bottom. If I redraw it with my labs in the boxes, the mapping is one-to-one:

| Rajlich phase | My lab |
|---|---|
| Initiation | [Lab 2 — change request "add Group / Ungroup feature"](#lab-2--change-initiation-and-concept-location) |
| Concept Location | [Lab 2 — locating GroupAction, GroupFigure](#lab-2--change-initiation-and-concept-location) |
| Impact Analysis | [Lab 3 — CI + impact set tables](#lab-3--continuous-integration-and-impact-analysis) |
| Prefactoring | [Lab 4 — Compose Method + dead-code removal on GroupAction](#lab-4--refactoring-lab-group--ungroup-prefactoring) |
| Actualization | [Lab 5 — SOLID / Clean Architecture pass](#lab-5--actualization-lab-solid-and-clean-architecture-in-jhotdraw) |
| Postfactoring | (would be the *Replace Conditional with Polymorphism* refactor I deferred in Lab 4) |
| **Verification** | **[Lab 7 — JUnit unit tests](#lab-7--testing-lab-unit-tests-for-group--ungroup) + [Lab 9 — JGiven BDD scenarios](#lab-9--behavior-driven-testing-jgiven-scenarios-for-group--ungroup)** |
| Conclusion | (the GitHub commits + green CI builds on `alex` branch — the JHotDraw fork's own baseline mechanism) |

The course is not nine independent labs — it is **one full phased software-change cycle, performed on JHotDraw, with the textbook chapter for each phase becoming the corresponding lab.** This lecture is where the diagram is finally drawn in full.

**(2) The textbook validates two specific tool choices I made earlier.** Slide 27 of Part A states: *"Tool JGiven and Mockito used to run the functional tests."* I added Mockito in Lab 7 and JGiven in Lab 9 — without knowing this slide existed — because they were the natural fits for the problems those labs posed. Finding the textbook recommending the same pair in retrospect is *external validation* of the engineering judgement, not a coincidence. The textbook arrived at the same recommendation because the problem shape is the same: *unit-test a Swing-style figure framework with a domain-language overlay for acceptance.*

**(3) The numerical results in slide 30 give me a quantitative target.** Rajlich's project's test-to-production ratio is ~27%, and changes propagate 1.4 test lines per production line. JHotDraw's ratio before my labs was effectively *zero* (Lab 3 finding: two test files in the entire `jhotdraw-core` module). After Labs 7 and 9 I have ~580 lines of test code added against 6 lines of production-side `assert` statements — locally that's a ratio of ~96:1 *for the changes I made*, which is the **opposite** problem to the textbook's: I am over-testing changes I never made, because the baseline test coverage was insufficient. The realistic target for JHotDraw is therefore not "1.4 lines of new test per line of new production code" but "build the test baseline up to ~27% of production code total, *then* apply the 1.4 ratio to subsequent changes". This is a multi-quarter project, not a single lab — but Lecture 10 makes the target legible for the first time.

**(4) The Conclusion phase is the social one.** The first nine labs were all *technical* — phases, refactorings, tests, CI. The Conclusion phase introduces *reputation*, *deadlines*, *management knows*, *stakeholder approval*. Most of the work of maintaining real software is in this last phase — and most of it is not code. The right lesson for the rest of my career is that becoming *good at the Conclusion phase* (writing clean commit messages, keeping PRs small enough that they pass the baseline cleanly, communicating delays before they become broken-baseline events) compounds in a way that becoming better at any individual technical phase does not.

**(5) Two refactorings I want to remember.** Slide 33's *Move function* and slide 35's *Splitting roles* are two refactoring moves I have not yet applied to JHotDraw. *Splitting roles* is the more interesting one — it is the *exact pattern* that would fix the `GroupAction.canUngroup` mockability tax I documented in [Lab 7's reflection (2)](#lab-7--testing-lab-unit-tests-for-group--ungroup): the single method that does both *"check whether a real figure can be ungrouped"* and *"check whether the prototype class matches"* could be split into a query on the prototype itself, which a mock could stub. The course did not assign this refactor as a lab, but it is now sitting on my own personal backlog as the cleanest improvement to make next on JHotDraw — and it would simultaneously satisfy Clean Code's *prefer polymorphism over type codes* rule and Rajlich's *splitting roles shortens change propagation* result. Three different lectures converge on the same one-line change.

---

## Lecture 11 — Beyond Technical Debt: Behavioural Code Analysis with CodeScene

> Final lecture of the course, no associated lab. The lecture is delivered against Adam Tornhill's *CodeScene* (a commercial behavioural-code-analysis tool that grew out of his book *Software Design X-Rays*). It closes the course by reframing everything the previous ten lectures discussed — technical debt, complexity, evolution, refactoring, testing, baselines — through the lens of one question: *what is the data source we should use to decide where to invest our maintenance effort?*

---

### 11.1 What is technical debt?

The lecture opens with the cleanest one-sentence definition I have seen in the literature, from *Building Evolutionary Architectures* (Ford, Parsons, Kia, p. 110):

> *"Stuff that isn't supposed to be there **and is in the way** of the stuff that is supposed to be there."*

Two things in this definition matter:

- **Not just "old stuff" or "ugly stuff".** The bold *"and is in the way"* is the operative clause. A regrettable but isolated piece of code that nobody ever touches is *not* technical debt — it is just history. A piece of code becomes debt the moment it *obstructs* present work.
- **Debt is relational, not absolute.** Whether code is "in the way" depends on what work you are doing *now*. The same legacy module is debt for the team writing a new feature on top of it, and not-debt for the team that ships once a year and never touches it.

This is a refinement of the *Boy Scout Rule* from Lecture 6 — the rule says "leave it cleaner than you found it" but doesn't tell you *which* file to visit. Tornhill's definition does: visit the files that are getting in the way of present work.

---

### 11.2 Lehman's Laws of Software Evolution

The lecture grounds the topic in Manny Lehman's classical laws (1980s), which are the same laws Lecture 1 cited under the *code decay* heading:

| Law | Statement |
|---|---|
| **Continuing Change** | *"A system must be continually adapted or it becomes progressively less satisfactory."* |
| **Increasing Complexity** | *"As a system evolves, its complexity increases unless work is done to maintain or reduce it."* |

The second law is the load-bearing one for this lecture: complexity does not stay flat *for free*. The default trajectory of any working system is **rising complexity**. Holding complexity flat is itself a deliberate engineering activity, and reducing it requires more deliberate activity still. This is the empirical foundation of every refactoring chapter in every software-engineering book — without it, the textbook injunction to "refactor regularly" has no force.

---

### 11.3 Why complexity matters — Hickey's *Simple Made Easy*

The lecture quotes Rich Hickey (creator of Clojure, the 2011 talk *Simple Made Easy*):

> *"If you ignore complexity, you will slow down. You will invariably slow down over the long haul … the complexity will eventually kill you. It will kill you in a way that will make every sprint accomplish less."*

The accompanying graph is the *Easy vs Simple* curve: the **Easy** curve starts high (fast initial velocity) and decays towards zero; the **Simple** curve starts lower (slower initial velocity) but rises asymptotically and stays high. The lesson: choosing the *easy* path repeatedly compounds into a system that is fast to start and slow to evolve. Choosing the *simple* path repeatedly compounds into the opposite — a system that is slower to start but maintains its evolution speed indefinitely.

This is the *quantitative* form of the same trade-off Lecture 4 framed qualitatively when it described *prefactoring* (slow now, faster later) vs *just patch it* (fast now, slower later). Hickey gives the curve; Rajlich's phase model gives the *workflow* that pushes you onto the *Simple* curve.

---

### 11.4 The business and product impact of technical debt

The lecture frames the **two stakeholder views** of accumulated debt:

| Stakeholder | Visible symptom |
|---|---|
| **Business / Product roadmap** | *Long lead times, lack of predictability* — Sisyphus pushing the boulder. The promised feature dates slip, again and again, because each feature lands on top of more debt than the last. |
| **End user / Product** | *Bugs* — the cartoon shows multiple blue beetles. Every fix produces another regression. |

These are not *technical* symptoms — they are *business* symptoms. Engineers seeing roadmap slippage often look for *project-management* causes; Tornhill's claim is that the cause is in the codebase, and the *visible* business symptom is the trailing edge of a *technical* root cause. *Technical debt is a business problem misdiagnosed as a planning problem.*

---

### 11.5 Why conventional static-analysis tools are not enough

Slide 7 (*Actionable?*) shows a generic SonarQube dashboard for Apache Tomcat:

| Metric (SonarQube) | Value |
|---|---|
| Lines of code | 162,306 |
| Classes | 1,447 |
| Violations | **10,072** (8,794 Major) |
| Duplications | 7.1% |
| Comments | 26.6% |
| Cyclomatic complexity / method | 3.1 |
| Tags (FIXME/TODO) | 356 |
| **Technical debt** | **11.0% = $341,563 = 683 man-days** |

Tornhill's critique is sharp: this is **not actionable**. A list of 10,072 violations and a 683-man-day debt estimate tells the team *that* there is a debt problem, but provides no rational starting point for *where* to begin paying it back. The 683 man-days assumes you will systematically fix every flagged item — which is exactly what an engineering team will *never* do, because most of those items don't matter.

The Tower of Babel painting on slide 8 makes the same point pictorially: *thousands of years of technical debt. Where do you start when you want to pay it back?* The question the conventional tool cannot answer.

This is the same critique I raised in [Lecture 6](#lecture-6--clean-code) when I noted that JHotDraw contains many `// XXX`, `// FIXME`, `// TODO` markers that the *Comments Do Not Make Up for Bad Code* rule licenses me to remove — but the rule does not prioritise *which* to remove first. Static analysis says everything; behavioural analysis is supposed to say *what to do next*.

---

### 11.6 CodeScene — the movie, not the snapshot

The lecture's pivot:

> *"Static analysis will never be able to tell you if that excess code complexity actually matters — just because a piece of code is complex doesn't mean it's a problem. CodeScene identifies and prioritizes technical debt **based on how the organization works with the code**."*

The reframing in two phrases:

- **+ Time aspect** — what the code looked like a year ago, what it looks like today, the *trajectory* in between.
- **+ Organization & people** — who touches each file, how often, in what combination with which other files.

The data source is *git*. The architecture diagram on slide 11:

```
Source Code   +   Version-Control Data   +   Project-Management Tools (e.g. JIRA)
                                ↓
            Code, Process, and Evolutionary Metrics
                                ↓
       Pattern Detectors, Machine Learning and Intelligence
                                ↓
       Visualizations, priorities, predictive analytics
```

The unconventional input is the **middle** lane — *Version-Control Data*. Conventional analysis ignores git entirely; behavioural analysis treats git as the primary signal and the source code itself as secondary context.

---

### 11.7 Hotspots — Principal × Interest = Hotspot

The lecture's central operational concept is the **hotspot**:

> *"A hotspot is a complicated code that you have to work with often."*

Two factors multiplied together:

| Factor | Source | Analogue (financial debt) |
|---|---|---|
| **Code complexity** (cyclomatic, lines, etc.) | Source code | **Principal** — how much you owe |
| **Code change frequency** | git log | **Interest rate** — how often you pay for owing |

The product of the two is the **hotspot**. A complex file that never changes is *principal-only* debt — you owe a lot but you never pay interest, so it is not urgent. A simple file that changes constantly is *interest-only* — you pay often, but the per-touch cost is low. A **complex file that changes constantly** is paying high interest on high principal — *that* is where the team's effort is silently being eaten.

The ReactJS visualisation on slide 13 is the operational form: each circle is a file, area is complexity, redness is change frequency. The red circles cluster in `react-reconciler`, `react-devtools-shared`, and `react-interactions` — exactly where a React engineer would intuitively *expect* the maintenance pain to live, but now quantified.

---

### 11.8 Hotspots are also where the bugs live

Slide 14 cites Graves, Karr, Marron and Siy's 2000 IEEE TSE paper *Predicting Fault Incidence Using Software Change History*. Two empirical findings:

- *"Process measures based on the change history are more useful in predicting fault rates than product metrics of the code. **The number of times code has been changed is a better indication of how many faults it will contain than is its length.**"*
- *"If a module is, on the average, a year older than an otherwise similar module, **the older module will have roughly a third fewer faults**."*

The two findings together produce the operational rule: **bugs cluster where change clusters**. The hotspot map is therefore *also* a fault-prediction map. This is the empirical claim that justifies the entire CodeScene paradigm.

Connecting to the labs: my [Lab 3 impact-analysis](#lab-3--continuous-integration-and-impact-analysis) computed an *impact set* — the classes that would be touched by the Group/Ungroup change. CodeScene's *change coupling* (slide 19) is the same idea computed *historically* from git instead of *statically* from the call graph. Both produce a graph of "what tends to change together"; the lab computed it forward (predict the next change), CodeScene computes it backward (look at the history). Both views matter — the static view is the *possible* impact set, the historical view is the *observed* impact set.

---

### 11.9 X-Ray — drilling into a hotspot

Once a hotspot is identified at the *file* level (slide 17 — `renderer.js` in react-devtools-shared, 2,444 LOC, 167 commits, Code Health 5/10, 34 defects = 20% bug fixes), CodeScene's *X-Ray* feature drills into the **functions inside** the file:

| Function | Change Frequency | LOC | Cyclomatic Complexity | Overloaded? |
|---|---:|---:|---:|---:|
| attach (top-level context) | 103 | 109 | 9 | 1 |
| attach.recordMount | 37 | 48 | 8 | 1 |
| attach.handleCommitFiberRoot | 32 | 89 | **19** | 1 |
| attach.flushPendingEvents | 30 | 105 | 15 | 1 |
| attach.inspectElement | 30 | 93 | 12 | 1 |
| attach.flushInitialOperations | 29 | 51 | 6 | 1 |
| **attach.updateFiberRecursively** | **24** | **220** | **48** | 1 |
| **attach.inspectElementRaw** | **24** | **180** | **45** | 1 |
| attach.recordUnmount | 18 | 50 | 9 | 1 |

This is the *concrete* output the team can act on. `updateFiberRecursively` (220 LOC, cyclomatic complexity 48, changed 24 times) is the single function that, if refactored, would yield the biggest reduction in expected future bug-fix work. The list is at most a dozen lines — actionable in a way that "10,072 violations" never is.

---

### 11.10 Change coupling — what changes together

Slide 19's chord diagram shows **change coupling**: pairs of functions that commit together in the git history. The chord between `renderer.js::attach (top-level context)` and `CommitTreeBuilder.js::updateTree.switch` is the visualisation of a *hidden dependency* — these two functions live in different files, the static analyser cannot see the relationship, but the git history shows that engineers always edit them together.

This is the same coupling Lecture 3 introduced statically as the *change-impact graph*; CodeScene re-derives it from *history* rather than *call graphs*. The two views can disagree, and **the disagreement is informative**:

- If two files change together but the call graph says they shouldn't: hidden coupling (a *shotgun-surgery* smell — slide 24 lists this explicitly).
- If two files don't change together but the call graph says they should: dead path (the static dependency is there but the code is no longer exercised).

---

### 11.11 Legacy code — *the technical debt that wasn't*

Slide 21 redefines *legacy code* in two parts:

- *"Code that lacks in quality (relative perspective)."*
- *"Code that **we didn't write ourselves**."*

The second clause is the surprising one. A team can call code "legacy" because it is *unfamiliar*, not because it is *bad*. The pictorial joke on the slide — Products #1 and #2 get a thumbs up, Product #3 gets a question mark — is the team encountering Product #3 for the first time. *"The Technical Debt That Wasn't"*: code that looks like debt because nobody on the team knows it, but is actually fine and just needs onboarding.

This is the most relevant slide for *my* portfolio. **JHotDraw is legacy code by both definitions in my case:**

- *Lacks quality*: parts of it (the comments compensating for bad code, the dead prototype field in `UngroupAction`, the `getClass()`-based class-identity check) genuinely have quality issues, as Labs 4 and 7 documented.
- *I didn't write it*: the entire codebase is Werner Randelshofer's, with Erich Gamma roots going back further. *Every single line is foreign to me.*

The implication: the *first* tool I should use on JHotDraw is not a refactoring tool, it is a tool that tells me *which parts are dangerous to touch* — which is exactly the question CodeScene was built to answer.

---

### 11.12 Knowledge loss — what happens when authors leave

Slides 22–23 (*How quickly can you turn your codebase into legacy code?* / *After they leave...*) introduce CodeScene's *off-boarding simulation*. Each developer is associated with the files they have authored (according to git blame). Toggling a developer off simulates their departure — the files where they were the *primary* author flip from blue (*Knowledge*) to red (*Simulated Loss*) and dark red (*Off-Boarding Risk*).

The exercise on the slide shows simulating Brian Vaughn leaving React. After the toggle, a huge cluster of red dots appears in `react-devtools-shared` and `react-reconciler` — the exact directories the earlier hotspot map highlighted. **The knowledge concentration map and the hotspot map overlap.** Where bugs cluster, knowledge also clusters in one person — and if that person leaves, the hotspot becomes orphaned legacy code.

JHotDraw is the *extreme* version of this. Werner Randelshofer left active maintenance years ago; the fork I am working on has *no* primary author on most files. Every file is a CodeScene "off-boarded" file in red — the entire repository is knowledge-loss territory by default. The Group/Ungroup feature I worked on is no exception. The mitigation, from the lecture's recommendations:

- Document hotspots before touching them. *(My Lab 2 concept-location notes.)*
- Add tests around hotspots before refactoring. *(My Labs 7 and 9 unit and BDD tests.)*
- Apply the *Boy Scout Rule* — leave files cleaner on each visit. *(My Lab 4 dead-code removal.)*

The lecture validates each lab's deliverable as a knowledge-recovery action, not just a technical-cleanup action.

---

### 11.13 The rest of the CodeScene toolbox

Slide 24 lists features I have not used but want to record:

- **Change coupling** — covered in 11.10.
- **Microservices analyses** — *shotgun surgery* (one change touches many services), *team conflicts* (two teams write the same file), *technical sprawl* (services drifting in shared structure).
- **Proactive warnings** — alerts in the PR review stage when a touch is going into a known hotspot.
- **Retrospectives** — post-mortem reports correlating delivery slippage with hotspot regressions.
- **Delivery Performance** — DORA-style lead-time / change-failure metrics.
- **Branch Analyses** — long-lived branches as a structural smell.

None of these are unique innovations — they are the same DORA / agile-metric repertoire — but they are all powered by the *same git data*. The integration is the value.

---

### 11.14 The four-line conclusion

The lecture's closing slide:

> - *Technical debt is a real problem regardless of programming language.*
> - *There's a huge amount of useful information stored in your version control system.*
> - *Ultimately, you need to rely on human expertise.*
> - *Support your developer's judgment and experience with data to get the highest ROI.*

The third point is striking after a 26-slide deck about a heavily quantitative tool: *the tool does not replace human judgement; it informs it.* This is the same humility from the SonarQube critique on slide 7 — *more data does not equal more action.* Data that an experienced developer cannot interpret is not an asset.

---

### Reflection on Lecture 11 — closing the course

Three syntheses I want to record as the final lecture-level reflection of the course.

**(1) The course recapitulated, with hindsight.** Lecture 11 is the final piece of the same argument that started in Lecture 1's *code decay* and ran through every lab:

| Lecture | Phrase from the lecture | The same idea in CodeScene's vocabulary |
|---|---|---|
| 1 — Essential difficulties | Complexity, Conformity, Discontinuity | Hotspot, Change Coupling, Off-Boarding Risk |
| 2 — Change request | "Where does the concept live?" | Hotspot drill-down |
| 3 — Impact analysis | Static call-graph impact set | Historical change-coupling graph |
| 4 — Prefactoring | "Refactor before the change" | "Pay down the hotspot principal before the next sprint touches it" |
| 5 — SOLID + Clean Architecture | SRP keeps classes from sprawling | Low change coupling between files |
| 6 — Clean Code | The Boy Scout Rule | Fix hotspots when you visit them, not in a separate refactoring quarter |
| 7-implied / Lab 7 — Unit tests | F.I.R.S.T. unit tests | The test floor that makes Lab 4-style refactorings *safe*, which makes the Boy Scout Rule *feasible* |
| 9 — BDD | Living documentation | The artefact a new contributor reads to **recover** the lost knowledge of an off-boarded author |
| 10 — Phased model + Conclusion | Initiation → … → Conclusion, with baseline / release | The repository as the long-running artefact that *accumulates* all of the above |
| **11 — CodeScene** | **"It's a movie rather than a snapshot."** | **The framing that makes all of the above measurable.** |

The course is therefore not a sequence of independent topics but **one argument repeated at increasing resolution**, with each lecture supplying a different vocabulary for the same idea. Lecture 11 supplies the *measurement* vocabulary — and is the right place for the course to end, because once you can measure a problem, the next step is in your hands rather than the textbook's.

**(2) The practical thing I would do next on JHotDraw.** If I had CodeScene access, the very first command I would run is a `git log` analysis against `jhotdraw-core/src/main/java`. My empirical prediction (based on the *Boy Scout Rule* opportunities I noticed across the labs) is that the hotspots would include:

- `org.jhotdraw.draw.figure.AbstractCompositeFigure` — the base class everything in the Group/Ungroup feature inherits from.
- `org.jhotdraw.draw.DefaultDrawingView` — the Swing-event-heavy view class that mediates between actions and the drawing.
- `org.jhotdraw.draw.action.AbstractSelectedAction` — the action superclass with the Lab 7 mockability tax.

The order in that list is my guess at the **principal × interest** ranking — `AbstractCompositeFigure` has the most static complexity but probably changes rarely; `DefaultDrawingView` is huge *and* gets touched a lot; `AbstractSelectedAction` is small but touched on every action change. Without the tool I cannot verify; with the tool I would know in five minutes which to refactor first.

**(3) The course as a whole, said in one sentence.** Software maintenance is the activity of **making safe, small, frequent changes to a moving system whose codebase outlives every individual who has worked on it.** Every lecture and every lab in this portfolio is a different angle on that one sentence. Lecture 1 named the difficulties; the middle lectures gave the workflow; Lecture 11 gives the instrument panel that tells you, on any given Monday morning, *where in the codebase to apply the workflow today*. The course has, in retrospect, taught me both the *what to do* and the *where to do it next* — and these are the two questions a working software maintainer answers every week of their career.

---

## Capstone Reflection — The Course as One Argument

> The previous sections are the *record* of the course. This section is the *synthesis* — what I want a future version of myself, opening this PDF in two years, to come away with after a single read. It is deliberately personal, and deliberately short relative to the size of the document above.

---

### The shape of the journey

Over a single semester I performed, on one open-source codebase, a complete pass through Rajlich's phased software-change model:

1. **Lab 1** — set up the environment and read the codebase.
2. **Lab 2** — selected the *Group / Ungroup* feature and located its concepts.
3. **Lab 3** — built impact-analysis tables and a GitHub-Actions CI workflow.
4. **Lab 4** — prefactored `GroupAction` (Compose Method, dead-code removal).
5. **Lab 5** — re-read the same change through SOLID / Clean Architecture.
6. **Lab 7** — pinned the feature with 24 JUnit 4 unit tests + 6 production assertions.
7. **Lab 9** — added 4 JGiven BDD scenarios that describe the same feature in user-story English.

Each lab compiled, each commit passed CI, each step left the codebase fractionally better than it found it. **The deliverable was not nine isolated artefacts but one trajectory of cleanup applied to a single 8-year-old feature.**

---

### Five lessons I will keep

**(1) The phase model is not a textbook decoration — it is a working schedule.**
When Rajlich draws Initiation → Concept Location → Impact Analysis → Prefactoring → Actualization → Postfactoring → Conclusion, he is not describing the past. He is describing the seven things that happen *every time anything changes in a codebase*, regardless of whether the engineer running them notices. Doing the labs in this order made me notice. The thing I will take into my next job is not the labels but the *rhythm*: locate before changing, analyse impact before editing, refactor before adding, test before committing.

**(2) Tests are an asset, not a deliverable.**
Lab 7's 24 unit tests are the *only* part of this portfolio that survives if I delete every other artefact. The portfolio explains *why* the change was made; the tests prove it *is* made and *stays* made. The 1.4 test-line-per-production-line ratio Rajlich reports (Lecture 10) is a description of the steady-state cost of running software, not a heroic investment.

**(3) The work I will be paid for is mostly the work I cannot see in advance.**
Every lab had a moment where the plan and the code disagreed. Lab 2's concept location backtracked at least twice. Lab 4's deferred refactorings were the most honest decision of the semester. Lab 7's JGiven JDK-25 incompatibility forced a Surefire `argLine` workaround. Lab 9's `getClass()` mockability tax was an unexpected, small but real piece of design feedback. **The textbook describes the path; the project provides the surprises**. Becoming better at this work is becoming better at the surprises.

**(4) Reading the code is a first-class skill.**
JHotDraw is a hundred-class fork of a fork of a 1990s Smalltalk framework. The first useful thing I did in every lab was read the existing code. The second was draw a diagram of what I had just read. The third was check `git log`. Every prefactoring, every refactoring, every test depended on those three reads. *Reading the code is not preparation for the work; reading the code is the work.*

**(5) The right size of a unit of work is a single coherent narrative.**
Every commit in this portfolio is one paragraph long in its commit message, contains changes to a small number of files, and corresponds to one row of the TOC. When I revisit this branch in two years, I want to be able to read its `git log --oneline` and have it make sense as a story. The discipline of keeping commits at that size is what makes the portfolio readable as a document. It is also what will make the codebase maintainable for the next person.

---

### What I would do differently if I started again

- **Do Lab 9 before Lab 7.** The BDD scenarios capture the *contract* the feature is supposed to uphold. The unit tests capture the *implementation* of that contract. Starting from the contract and refining inward would have produced better-named JUnit tests — `groupingTwoFiguresProducesOneGroupOfTwo` rather than `groupFigures_clearsSelection_addsGroupAtFirstFigureIndex_andReselectsTheGroup`.
- **Run the *splitting roles* refactor on `GroupAction.canUngroup`.** Three lectures (4, 6, 10) converge on this one-line change. I deferred it in Lab 4 because no tests existed; after Labs 7 and 9, the safety net is in place. It would be the right *next* commit on this branch.
- **Use Mockito's `spy()` instead of `mock()` for the `GroupFigure` prototype.** The current Lab 7 test file uses real `GroupFigure` instances where class identity matters and mocks where it doesn't. A spy would unify both cases.
- **Set up CodeScene against this repository.** Lecture 11 makes the case; my labs all gave intuitive guesses at the hotspots. Running the tool would either confirm those guesses or surface a different priority list. Either outcome is useful.

None of these are blockers. They are the natural *next labs*, in the same trajectory the existing seven labs trace.

---

### How this portfolio will be useful to me beyond the exam

Three concrete future moments where I expect to reach for this document:

- **The first time I onboard onto a legacy codebase at work.** The Lecture-2 concept-location and Lab-2 selection process is the procedure I will follow on day one of any new repository.
- **The first code review I do where the diff is uncomfortable.** The Lecture-4 prefactoring discipline ("clean the code *before* you change it, not after") and the Lecture-6 *Boy Scout Rule* are the two principles I will apply to my own reviews.
- **The first time I am asked to estimate a feature.** The Rajlich phase diagram, with the Verification spine running the full length of the V, is the cost model I will use to push back on estimates that ignore the right-hand side of the V.

---

### One sentence to take with me

> *Software does not stand still; the engineer who maintains it does not stand still either; and the artefacts of careful maintenance — small commits, named tests, lived-in code — are the only durable record of the engineer's care.*

That sentence is the course in fourteen words. The portfolio above is the proof I learned it.

---

## Bibliography

A consolidated list of every book, paper, lecturer, library, and tool cited in the portfolio above. Items are grouped by category and ordered alphabetically within each group.

### Books

- **Beck, Kent.** *Extreme Programming Explained: Embrace Change*. Addison-Wesley, 1999. (Referenced as the source of the Three Laws of TDD and the JUnit framework. See [Lecture 7](#lecture-7--software-testing-how-to-make-software-fail).)
- **Beck, Kent.** *Test-Driven Development: By Example*. Addison-Wesley, 2002. (Source of the red-green-refactor TDD cycle. See [Lecture 7](#lecture-7--software-testing-how-to-make-software-fail).)
- **Brooks, Frederick P.** *The Mythical Man-Month*. Addison-Wesley, 1975. (Source of the four *essential difficulties* — Complexity, Invisibility, Changeability, Conformity. See [Lecture 1](#lecture-1--introduction-to-software-maintenance).)
- **Feathers, Michael.** *Working Effectively with Legacy Code*. Prentice Hall, 2004. (Referenced via the Clean Code citation. See [Lecture 6](#lecture-6--clean-code).)
- **Ford, Neal; Parsons, Rebecca; Kia, Patrick.** *Building Evolutionary Architectures*. O'Reilly, 2017. (Source of the technical-debt definition *"stuff that isn't supposed to be there and is in the way"* — p. 110. See [Lecture 11](#lecture-11--beyond-technical-debt-behavioural-code-analysis-with-codescene).)
- **Gamma, Erich; Helm, Richard; Johnson, Ralph; Vlissides, John.** *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley, 1994. (The "Gang of Four". Cited as the design heritage of JHotDraw and JUnit. See [Lecture 4](#lecture-4--refactoring-and-refactoring-to-patterns), [Lecture 7](#lecture-7--software-testing-how-to-make-software-fail).)
- **Fowler, Martin.** *Refactoring: Improving the Design of Existing Code*. Addison-Wesley, 1999. (Source of *Compose Method*, *Replace Conditional with Polymorphism*, and most of the refactoring vocabulary used in [Lab 4](#lab-4--refactoring-lab-group--ungroup-prefactoring).)
- **Martin, Robert C.** *Clean Code: A Handbook of Agile Software Craftsmanship*. Prentice Hall, 2009. (The structural source for [Lecture 6](#lecture-6--clean-code) — meaningful names, functions, comments, formatting, error handling, unit tests, classes, emergent design.)
- **Martin, Robert C.** *Clean Architecture*. Prentice Hall, 2017. (Referenced for the architectural principles in [Lecture 5](#lecture-5--actualization-oo-principles-and-clean-architecture).)
- **Rajlich, Václav.** *Software Engineering: The Current Practice*. CRC Press, 2012. (The course's primary textbook. Chapters cited: Ch. 11 on the *Conclusion* phase, Ch. 17 on the *Drawlets example*. See [Lecture 10](#lecture-10--example-of-software-change-and-conclusion-of-the-change-process).)
- **Tornhill, Adam.** *Software Design X-Rays: Fix Technical Debt with Behavioral Code Analysis*. The Pragmatic Bookshelf, 2018. (The conceptual background for CodeScene. See [Lecture 11](#lecture-11--beyond-technical-debt-behavioural-code-analysis-with-codescene).)
- **Tornhill, Adam.** *Your Code as a Crime Scene*. The Pragmatic Bookshelf, 2015. (Cited via [adamtornhill.com](https://www.adamtornhill.com) — same author, earlier book on the same paradigm.)

### Papers, Talks, and Articles

- **Dijkstra, Edsger W.** *Notes on Structured Programming*. EWD249, 1972. (Source of the dictum *"Testing can demonstrate the presence of bugs, but not their absence."* See [Lecture 7](#lecture-7--software-testing-how-to-make-software-fail).)
- **Graves, Todd L.; Karr, Alan F.; Marron, J.S.; Siy, Harvey.** *Predicting Fault Incidence Using Software Change History*. IEEE Transactions on Software Engineering 26(7):653–661, August 2000. (Empirical foundation for hotspot analysis. See [Lecture 11](#lecture-11--beyond-technical-debt-behavioural-code-analysis-with-codescene).)
- **Hickey, Rich.** *Simple Made Easy*. Strange Loop 2011 talk. (Source of the *Easy vs Simple* curve. See [Lecture 11](#lecture-11--beyond-technical-debt-behavioural-code-analysis-with-codescene).)
- **Lehman, Manny M.** *Programs, Life Cycles, and Laws of Software Evolution*. Proceedings of the IEEE 68(9):1060–1076, September 1980. (Source of *Lehman's Laws* — Continuing Change and Increasing Complexity. See [Lecture 1](#lecture-1--introduction-to-software-maintenance), [Lecture 11](#lecture-11--beyond-technical-debt-behavioural-code-analysis-with-codescene).)
- **Lientz, Bennet P.; Swanson, E. Burton.** *Software Maintenance Management*. Addison-Wesley, 1980. (Source of the Lientz-Swanson percentages — Corrective ~21%, Adaptive ~25%, Perfective ~50%, Preventive ~4%. See [Lecture 1](#lecture-1--introduction-to-software-maintenance), [Lab 1](#lab-1--introduction-lab-project-setup).)
- **The Standish Group.** *CHAOS Report*. Various years. (Source of project success/challenged/failed percentages cited in [Lecture 1](#lecture-1--introduction-to-software-maintenance), [Lab 1](#lab-1--introduction-lab-project-setup).)
- **Turing, Alan.** *On Computable Numbers, with an Application to the Entscheidungsproblem*. Proceedings of the London Mathematical Society, 1936. (Source of the halting problem. See [Lecture 7](#lecture-7--software-testing-how-to-make-software-fail).)
- **Holwerda, Thom.** *WTFs/minute*. (The cartoon-form code-quality metric quoted in [Lecture 6](#lecture-6--clean-code).)

### Lecturers and Course Material

- **Sørensen, Jan Corfixen.** University of Southern Denmark. *SB5-MAI Software Maintenance* course materials. Author of all course lecture slides and lab handouts.
- **Course handouts referenced:**
  - *Lab1-Setup* — environment + first read of JHotDraw.
  - *ChangeReqLab* / *CLLab* — Lab 2 change initiation and concept location.
  - *ImpactAnalysisLab* / *CILab* — Lab 3 impact analysis and CI.
  - *RefactoringLab* — Lab 4 prefactoring.
  - *ActualizationLab* — Lab 5 SOLID and Clean Architecture.
  - *TestLab1* — Lab 7 unit testing.
  - *BDDLab* / *TestLab2* — Lab 9 BDD.

### Tools and Libraries

- **AssertJ** — fluent assertion library. [https://assertj.github.io/doc/](https://assertj.github.io/doc/) — used in [Lab 9](#lab-9--behavior-driven-testing-jgiven-scenarios-for-group--ungroup).
- **AssertJ-Swing** — Swing GUI automation library. Used (documented, not runnable headless) in [Lab 9](#lab-9--behavior-driven-testing-jgiven-scenarios-for-group--ungroup).
- **CodeScene** — behavioural code analysis SaaS by Adam Tornhill. [https://codescene.com](https://codescene.com) — discussed in [Lecture 11](#lecture-11--beyond-technical-debt-behavioural-code-analysis-with-codescene).
- **GitHub Actions** — CI runner. Used in [Lab 3](#lab-3--continuous-integration-and-impact-analysis).
- **JGiven** — developer-friendly BDD framework. [http://jgiven.org](http://jgiven.org) — used in [Lab 9](#lab-9--behavior-driven-testing-jgiven-scenarios-for-group--ungroup).
- **JHotDraw** — the codebase under study. Fork at v9.1-SNAPSHOT, LGPL 2.1. Origin: SourceForge JHotDraw project.
- **JUnit 4** — unit-test framework. [https://junit.org/junit4/](https://junit.org/junit4/) — used in [Lab 7](#lab-7--testing-lab-unit-tests-for-group--ungroup).
- **Maven 3.9.6** — portable build tool at `/tmp/maven`. [https://maven.apache.org](https://maven.apache.org).
- **Mockito 4.11.0** — Java mocking framework. [https://site.mockito.org](https://site.mockito.org) — used in [Lab 7](#lab-7--testing-lab-unit-tests-for-group--ungroup) and [Lab 9](#lab-9--behavior-driven-testing-jgiven-scenarios-for-group--ungroup).
- **SonarQube** — static code quality / technical debt tool. Critiqued in [Lecture 11](#lecture-11--beyond-technical-debt-behavioural-code-analysis-with-codescene).
- **Surefire** — Maven test runner plugin.
- **TestNG** — alternative Java test framework, pre-existing in JHotDraw.
- **Drawlets** — the framework used in Rajlich's worked example. Original by Kent Beck and Ward Cunningham, ported to Java. [http://www.rolemodelsoft.com/aboutUs/drawlets.htm](http://www.rolemodelsoft.com/aboutUs/drawlets.htm). See [Lecture 10](#lecture-10--example-of-software-change-and-conclusion-of-the-change-process).

### Acronyms

| Acronym | Expansion |
|---|---|
| BDD | Behaviour-Driven Development |
| CHAOS | The Standish Group's report on software project outcomes |
| CI | Continuous Integration |
| CLI | Command-Line Interface |
| DI | Dependency Injection |
| DRY | Don't Repeat Yourself |
| F.I.R.S.T. | Fast, Independent, Repeatable, Self-validating, Timely (clean tests) |
| GoF | Gang of Four (referring to *Design Patterns* authors) |
| GRASP | General Responsibility Assignment Software Patterns |
| JPMS | Java Platform Module System |
| LGPL | GNU Lesser General Public License |
| MoSCoW | Must / Should / Could / Won't (requirements prioritisation) |
| OCP | Open / Closed Principle |
| ROI | Return on Investment |
| SDI / MDI | Single / Multiple Document Interface |
| SOLID | Single-responsibility, Open-closed, Liskov, Interface-segregation, Dependency-inversion |
| SRP | Single Responsibility Principle |
| SUT | System / Software Under Test |
| TDD | Test-Driven Development |
| TOC | Table of Contents |
| US-1, US-2, US-3 | User Stories 1, 2, 3 (Group / Ungroup) |
| WTFs/minute | Subjective code-readability metric (Holwerda) |
| XP | eXtreme Programming |
