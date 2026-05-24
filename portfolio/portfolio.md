# SB5-MAI Software Maintenance — Individual Portfolio

**Student:** Alex Baduca (baducualexandrudaniel@gmail.com)  
**Course:** SB5-MAI Software Maintenance, University of Southern Denmark  
**Project:** JHotDraw — open-source Java drawing framework (fork, v9.1-SNAPSHOT)

---

## Table of Contents

1. [Lecture 1 — Introduction to Software Maintenance](#lecture-1--introduction-to-software-maintenance)
2. [Lab 1 — Introduction Lab: Project Setup](#lab-1--introduction-lab-project-setup)

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
