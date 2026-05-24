# SB5-MAI Software Maintenance — Individual Portfolio

**Student:** Alex Baduca (baducualexandrudaniel@gmail.com)  
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
