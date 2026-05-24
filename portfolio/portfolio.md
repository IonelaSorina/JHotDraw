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
