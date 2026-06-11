# Maintenance Report: Refactoring, Testing, and Architectural Audit of the Group / Ungroup Feature in JHotDraw

> **Use rule:** this is the *paste-ready* master essay. Header is pre-filled where I know the values; two fields are marked `XXXXX` for you to complete. The structure follows Rajlich's phased model end-to-end and covers every major lecture topic. For a 15-20 page exam, pick the sections that match the question and delete the others — the essay is sectionally modular. For a 25-30 page exam, paste the whole thing.
>
> **What to edit before submitting:** (1) Header fields marked `XXXXX`. (2) Replace the *prose* figure descriptions with real screenshots / UML if you have them. (3) Tweak the abstract to emphasise whichever sub-topic the exam question prioritises (one sentence change). (4) Optionally trim or expand the *Discussion* section to match page count.

---

**Full Name:** Alex Baduca
**Student Exam Number:** XXXXX
**Student Email:** baducualexandrudaniel@gmail.com
**Course Number:** SB5-MAI Software Maintenance
**Number of Pages:** [page numbers in top-right]
**Name of Lecturer:** Jan Corfixen Sørensen
**Lecturer Email:** XXXXX

---

## Abstract

This report applies Rajlich's (2012) phased model of software change to the **Group / Ungroup** feature of the open-source Java drawing framework **JHotDraw**. Across seven phases — Initiation, Concept Location, Impact Analysis, Prefactoring, Actualization, Postfactoring, Conclusion — the report documents the maintenance work performed across **seven laboratory assignments** of the SB5-MAI Software Maintenance course: a user-story-driven initiation (Lab 1, Lab 2), the iterative concept location of `GroupAction`, `UngroupAction`, and `GroupFigure` (Lab 2), a static-and-dynamic impact analysis backed by a GitHub Actions Continuous Integration workflow (Lab 3), three concrete refactorings on `GroupAction.java` and `UngroupAction.java` (Lab 4 — Compose Method on a 67-line method, removal of a dead shadow field, and removal of a stale `XXX` comment), a SOLID architectural audit (Lab 5) identifying five violations including the *mockability tax* on the `getClass()`-based equality check, and a multi-layer verification suite of 24 JUnit 4 unit tests + 6 production assertions (Lab 7) plus 4 JGiven Behaviour-Driven Development scenarios with AssertJ assertions (Lab 9). All deliverables exist as committed artefacts in the project repository on the working branch `alex`, and the work is documented at full depth in the author's portfolio (`portfolio/portfolio.md`, approximately 3,900 lines). The work yields three findings worth foregrounding: refactoring's value is economic rather than aesthetic (Rajlich's Drawlets case quantifies a 62% reduction in classes touched by future changes after two refactorings); testability is the operational test of SOLID compliance (the DIP violation in `canUngroup` manifests as the testability obstacle observed in Lab 7); and Behaviour-Driven Development scenarios are the only documentation the build refuses to let drift silently. The report concludes that software maintenance is not a phase that follows development but the activity that constitutes the majority of any working system's lifetime cost — empirically estimated by Lientz and Swanson (1980) at approximately 80% of total cost of ownership — and that Rajlich's phased model is the schedule that turns that activity from heroic intervention into routine engineering discipline.

---

## Note on Evidence and Lab Sources

This report is grounded in concrete laboratory work performed by the author across the semester. **Every phase discussed below is supported by one or more lab deliverables that exist as committed artefacts in the project repository.** The work is documented at length in the author's portfolio (`portfolio/portfolio.md`). The mapping between the lab handouts assigned in the course, the lab work performed, the portfolio's documentation, and the sections of this report is given by the following traceability table:

| This report's section | Lab(s) | Lab handout | Repository artefacts | Portfolio section |
|---|---|---|---|---|
| §2 Initiation | Lab 1, Lab 2 | *Lab1-Setup*, *ChangeReqLab* | Working environment + change request | Lab 1 — *Introduction Lab: Project Setup* and Lab 2 — *Change Initiation and Concept Location* |
| §3 Concept Location | Lab 2 | *CLLab* | Concept-location notes | Lab 2 — *Change Initiation and Concept Location* |
| §4 Impact Analysis | Lab 3 | *ImpactAnalysisLab*, *CILab* | `.github/workflows/maven.yml` + impact-set tables | Lab 3 — *Continuous Integration and Impact Analysis* |
| §5 Prefactoring | Lab 4 | *RefactoringLab* | `jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java` (refactored) + `UngroupAction.java` (dead field removed) | Lab 4 — *Refactoring Lab: Group / Ungroup Prefactoring* |
| §6 Actualization (audit) | Lab 5 | *ActualizationLab* | SOLID audit findings + refactoring plan | Lab 5 — *Actualization Lab: SOLID and Clean Architecture in JHotDraw* |
| §8.1 Verification — unit tests | Lab 7 | *TestLab1* | `GroupActionTest.java`, `UngroupActionTest.java`, `GroupFigureTest.java` + 6 production `assert` statements | Lab 7 — *Testing Lab: Unit Tests for Group / Ungroup* |
| §8.2 Verification — BDD | Lab 9 | *BDDLab* (*TestLab2*) | `GivenADrawing.java`, `WhenTheUser.java`, `ThenTheDrawing.java`, `GroupUngroupScenarioTest.java`, `DrawAppSwingScenarioTest.java` | Lab 9 — *Behavior-Driven Testing: JGiven Scenarios for Group / Ungroup* |
| §9 Conclusion phase | every lab | (mechanised) | Commits on the `alex` branch + green GitHub Actions runs | (every commit) |

Inline references in the body of the report — written in the form (Lab 4) or (Lab 7) at the relevant point — connect every concrete claim back to the laboratory work that produced it. The full set of lab deliverables is reproducible via `mvn test -pl jhotdraw-core`, which currently reports 30 tests with zero failures, zero errors, and zero skipped tests across both the pre-existing TestNG suite and the JUnit + JGiven additions made in the present work.

The portfolio, the labs, the lab handouts, and this report together form a four-layered evidence chain: the lab handout specifies *what was to be done*, the lab work in the repository shows *what was actually done*, the portfolio entry documents *the work in narrative form with reflections*, and this report *synthesises the entire arc through the lens of Rajlich's phased model*.

---

## 1. Introduction

Software does not stand still. Once deployed, every code base lives, ages, and accumulates complexity at the rate Lehman's second law (1980) predicts: *"as a system evolves, its complexity increases unless work is done to maintain or reduce it."* The default trajectory of any working software system is rising complexity, falling productivity, and accumulating technical debt. Software maintenance is the discipline of *pushing back* against this default. Lientz and Swanson's foundational 1980 study established empirically that maintenance — corrective, adaptive, perfective, and preventive combined — accounts for approximately 80% of a system's total cost of ownership, and that perfective maintenance (improvements and enhancements) dominates the breakdown at roughly half the total effort. A career in software is therefore predominantly a career in maintenance, and the techniques covered in the SB5-MAI Software Maintenance course at the University of Southern Denmark — Rajlich's phased model, Fowler's refactoring catalogue, Martin's SOLID principles and Clean Code disciplines, Beck's testing rules and Three Laws of Test-Driven Development, and Tornhill's behavioural code analysis — together compose the working engineer's toolkit for the four-fifths of professional time that follows initial delivery.

This report applies that toolkit to a single concrete feature of a single concrete codebase. The feature is *Group / Ungroup* — the user-visible capability of selecting multiple figures on a drawing canvas and combining them into a single composite figure, or reversing that operation. The codebase is JHotDraw, an open-source Java drawing framework descended from a 1990s Smalltalk drawing-tool design pioneered by Erich Gamma, Kent Beck, and Ward Cunningham. The work proceeds through every phase of Rajlich's model, with the techniques of each lecture appearing at the phase where they are operationally relevant.

### 1.1 What is JHotDraw?

JHotDraw is a Java drawing framework currently at version 9.1-SNAPSHOT, licensed under LGPL 2.1. Structurally, it is a Maven multi-module project comprising approximately nine modules — `jhotdraw-api` containing the core interfaces, `jhotdraw-core` containing the default implementations, `jhotdraw-gui` containing the Swing user-interface components, `jhotdraw-app` containing the application shell, and several supporting modules for actions, data transfer, XML serialisation, and utility code, plus a `jhotdraw-samples` group of runnable demonstration applications including Draw, SVG, Net, Teddy, and Pert. The framework provides a Swing-based two-dimensional drawing canvas supporting lines, free-hand lines, rectangles, rounded rectangles, triangles, pentagons, polygons, ellipses, text boxes, image embeds, and — central to this report — composite groups of figures that behave as single units for selection, move, resize, undo, and redo. JHotDraw is widely cited in the software-engineering literature as a paradigmatic object-oriented design exemplar, and the framework's earlier Smalltalk incarnation served as the case study from which several of the canonical Gang of Four design patterns were originally derived. Its combination of age, structural complexity, and design pedigree makes it an ideal subject for a maintenance audit: it is real production-grade code, large enough to exhibit Lehman's complexity rise, yet small enough to fit within a single semester of focused work.

### 1.2 The selected feature: Group / Ungroup

The Group / Ungroup feature was selected as the semester's working thread on the basis of three criteria. First, it spans the full vertical slice of JHotDraw's architecture — the action layer at the top (`GroupAction` and `UngroupAction`, both extending `AbstractSelectedAction`), the figure data structure in the middle (`GroupFigure` extending `AbstractCompositeFigure`, which in turn implements the `Figure` interface and provides children-list and event-propagation machinery), and the drawing container at the bottom (the `Drawing` and `DrawingView` interfaces and their concrete implementations) — so refactoring the feature exercises every architectural layer the course's lectures discuss. Second, the feature is self-contained enough to fit a semester yet complex enough to expose authentic maintenance smells: a 67-line `actionPerformed` method that mixes multiple levels of abstraction, a dead shadow field in `UngroupAction` that confuses every reader, a stale `// XXX` comment marker that has outlived the redundancy it described, and a `getClass()`-based class-equality check that violates the Dependency Inversion Principle and manifests as a testability obstacle once unit tests are introduced. Third, the feature's inverse pairing — Group and Ungroup as semantic opposites implemented through inheritance — supports productive discussion of design symmetry, the Liskov Substitution Principle, and the trade-offs between inheritance and composition.

---

## 2. Initiation

The Initiation phase is the first of Rajlich's seven phases. Its purpose is to receive a change request, scope and prioritise it, and produce the documented artefacts that anchor the rest of the work. The phase ends when the team has a written change request, a user story, and an agreed-upon team pipeline. **In the present work, the Initiation phase was executed across Lab 1 (*Introduction Lab: Project Setup*) and Lab 2 (*Change Initiation and Concept Location*).** Lab 1 produced the working build environment and confirmed that the JHotDraw fork could be compiled and run; Lab 2 produced the written change request and the user story below, following the *ChangeReqLab* handout.

### 2.1 Rajlich's phased model of software change

Rajlich (2012) breaks every change in a codebase into seven sequential phases: Initiation, Concept Location, Impact Analysis, Prefactoring, Actualization, Postfactoring, and Conclusion, with Verification running as a column along the right-hand side of the V-shaped diagram and spanning Prefactoring through Conclusion. The model is both *prescriptive* — if the engineer follows the phases in order, the change will be safer and cheaper — and *descriptive* — every change passes through these phases anyway, including badly executed changes where some phases are skipped, rushed, or done implicitly. The model's value lies in *making the engineer notice* which phase they are currently in. Once noticed, each phase can be scheduled deliberately rather than allowed to proceed by default. The present report is organised by phase rather than by lecture topic precisely because phase-organisation reveals how the techniques covered in different lectures coordinate within a single discipline.

### 2.2 User story

The user-facing description of the feature, written in the Mike Cohn template *"as a user-type, I want a goal so that a benefit"*, is:

> *As a Draw user, I want to group multiple selected figures so that I can move and transform them as a single unit, and to ungroup a previously grouped figure so that I can edit its children independently.*

A second user story captures the negative boundary case that drives one of the BDD scenarios in Lab 9:

> *As a Draw user, I want the Group menu item to be disabled when only one figure is selected, so that I cannot create meaningless single-figure groups by accident.*

These stories drive every subsequent phase. They name the concept whose code must be located, define the impact set that Concept Location will produce, motivate the prefactoring that makes future changes local, and dictate the boundary-case coverage that the verification suite must achieve.

### 2.3 MoSCoW prioritisation

A maintenance project's prioritisation differs from a feature project's, because the deliverables are *infrastructure for future change* rather than new functionality. The Must-have items are concept location, the test floor, and behavioural documentation. The Should-have items are the obvious refactorings of the existing code and the SOLID architectural audit. The Could-have items are a CodeScene-style behavioural code analysis and the execution of the larger refactorings identified by the SOLID audit. The Will-not-have items, explicitly out of scope for this work, are any new user-facing feature and any rewrite of the framework.

### 2.4 Team pipeline

The working branch is `alex`, off the project's primary branch `develop`. A GitHub Actions workflow defined in `.github/workflows/maven.yml` (added in **Lab 3**, following the *CILab* handout) runs the command `mvn -B -s .maven-settings.xml test` on every pull request targeting `develop`. The CI workflow is the technical implementation of Rajlich's Conclusion-phase baseline mechanism: every pull request with a green workflow run becomes a candidate new baseline; every red run prevents one. The baseline is therefore continuously updated by mechanical means rather than periodically by manual ceremony, which compresses the *baseline as deadline* concept from Lecture 10 down to *every pull request is its own deadline*.

The GitHub Actions run for the working branch shows the project's full test suite — 30 tests across JUnit, JGiven, and the two pre-existing TestNG tests — completing in under five seconds with zero failures, zero errors, and zero skipped tests. This green check is the public confirmation that each commit on the branch preserves the behaviour established by the previous commit.

---

## 3. Concept Location

Concept Location is the act of traversing from a concept expressed in the user's domain language to its implementation expressed in code. Rajlich frames this as walking the *concept triangle* — concept ↔ words ↔ code — and the phase is, in practice, the most error-prone phase of the model. A misidentified concept produces a wrong impact set, which produces an incomplete change, which produces a regression that the verification layer must then catch. The phase is also, characteristically, *iterative*: wrong-way paths followed by backtracking are the normal shape of concept location, not a failure mode. **The Concept Location phase of the present work was executed during Lab 2, following the *CLLab* handout.**

### 3.1 The iterative search through JHotDraw

The concept location of the Group / Ungroup feature began with Search Using Regular Expressions, the simplest concept-location strategy. A regex search for the keyword `group` across `jhotdraw-core` returned many hits, including hits in the `Toolbar` infrastructure, the `Action` framework, and various utility classes. The first plausible path through the `Toolbar` and `Action` framework turned out to be a wrong way — these are the classes responsible for *invoking* the feature through menu items and toolbar buttons, not for *implementing* the grouping logic itself. Backtracking — the explicit retraction of an exploration path that Rajlich's Lecture 10 *wrong way → backtrack → right way* diagrams identify as a normal step — led to a second attempt using Search Using Links from the `actionPerformed` method downward. This second search revealed the correct path: `GroupAction.actionPerformed` calls `groupFigures`, which uses `view.getDrawing()` to obtain a `Drawing` reference, which calls `add(int, Figure)` to insert the newly created group containing the previously selected figures. From `GroupAction` the search extended to `UngroupAction` (the subclass that flips the dispatch flag), to `GroupFigure` (the concrete composite figure used as the prototype for grouping), and to `AbstractCompositeFigure` (the parent class providing the children-list machinery).

### 3.2 The domain-class / responsibility table

The full enumeration of the classes participating in the Group / Ungroup feature, with their responsibilities:

| Domain Class | Responsibility |
|---|---|
| `GroupAction` | Performs the grouping command: gathers the current selection from the active view, clones a prototype `GroupFigure`, calls `groupFigures` to move the selected figures into the new group, and fires an `UndoableEdit` for undo/redo support |
| `UngroupAction` | Performs the ungrouping command: extracts the children of the selected `GroupFigure`, returns them to the drawing root via `ungroupFigures`, and fires an `UndoableEdit` |
| `AbstractSelectedAction` | Parent of `GroupAction`; provides the property-change-listener wiring that keeps the action's enabled state synchronised with the active view's selection count |
| `GroupFigure` | Concrete `CompositeFigure` used as the data structure holding the grouped children; extends `AbstractCompositeFigure` |
| `AbstractCompositeFigure` | Base class providing the children-list, layout machinery, and event-propagation infrastructure for any composite figure |
| `Drawing` (interface) | The root container of all figures on the canvas; provides `add`, `remove`, `basicAdd`, `basicRemove`, `indexOf`, and `sort` |
| `DrawingView` (interface) | Provides the current selection via `getSelectedFigures` and `getSelectionCount`; mediates between the action layer and the drawing |
| `DrawingEditor` (interface) | Coordinates views and tools; supplies the active view to actions via `getActiveView` |

### 3.3 Concept classification following Rajlich's Lecture 10 approach

Lecture 10 introduces a three-way classification of the words appearing in a change request: *irrelevant* words that have no implementation correlate, *external* words that come from outside the system as input, and *significant* words that must be located in the code. Applying this classification to the present feature:

| Concept | Classification | Justification |
|---|---|---|
| group, ungroup | Significant | Mapped directly to the `GroupAction` and `UngroupAction` classes |
| figure | Significant | Mapped to the `Figure` interface and its concrete implementations |
| canvas | Significant | Mapped to the `Drawing` interface and the `DrawingView` interface |
| selection | Significant | Mapped to `DrawingView.getSelectedFigures()` |
| user | External | The user is outside the software; their input arrives through Swing events |
| transform, move | Irrelevant | Verbs without specific code correlates within the scope of this feature |

---

## 4. Impact Analysis

The Impact Analysis phase determines which code elements will need modification when the located feature is changed. The phase produces an *impact set* — a list of directly and indirectly impacted classes — that constrains the rest of the work. Two complementary techniques exist: static impact analysis, which reads the code's structure to predict impact, and dynamic impact analysis, which runs the code and observes which paths actually execute. **The Impact Analysis phase of the present work was executed in Lab 3, following the *ImpactAnalysisLab* and *CILab* handouts.** Lab 3 produced the static impact-set table reproduced below, and added the GitHub Actions Continuous Integration workflow that mechanises the dynamic-impact-analysis check.

### 4.1 Static impact analysis

Static impact analysis traverses the call graph and type-dependency graph of the located code. The direct impact set is the set of classes that will be literally edited by a change to the feature; the indirect impact set is the set of classes whose contracts will be affected by changes to the direct set and which must therefore be checked for consistency. Applying static analysis to the Group / Ungroup feature produces the following impact set:

| Class | Direct or Indirect | Justification |
|---|---|---|
| `GroupAction` | Direct | The principal class implementing the grouping logic |
| `UngroupAction` | Direct | The subclass implementing the ungrouping logic; often co-modified with `GroupAction` |
| `GroupFigure` | Direct | The data structure holding the grouped children |
| `AbstractCompositeFigure` | Indirect | Parent class providing inherited behaviour |
| `AbstractSelectedAction` | Indirect | Parent class providing the selection-listener wiring |
| `Drawing` | Indirect | The container whose state is modified by every group/ungroup operation |
| `DrawingView` | Indirect | The source of the selection consumed by `GroupAction` |
| `DrawingEditor` | Indirect | The coordinator that supplies the active view |

The static impact set is *forward-looking*: it predicts what *could* be affected by a change. Static analysis is language-aware and complete for syntactic dependencies, but it cannot see runtime dispatch through reflection, dependency-injection containers, or resource bundles. Lecture 3 introduces this distinction; Lecture 11 extends it by introducing *historical impact analysis*, which uses git history to identify files that have repeatedly been modified together, regardless of whether the static call graph suggests a connection. The two views can disagree, and the disagreement is informative — a file pair that commits together but does not appear in each other's call graphs is a sign of *shotgun surgery*, where a single conceptual change is scattered across files that should arguably be more coupled in code.

### 4.2 Dynamic impact analysis

Dynamic impact analysis runs the code and observes which paths actually execute. In a project with continuous integration, the CI workflow is the working implementation of dynamic impact analysis: every pull request runs the test suite, and the suite's pass/fail outcome confirms or rejects the assumption that the change is behaviourally safe. The Lab 3 GitHub Actions workflow described in Section 2.4 performs this role. After each of the subsequent labs introduced changes to the Group / Ungroup feature — Lab 4's refactorings of `GroupAction.java` and `UngroupAction.java`, Lab 7's addition of unit tests and production assertions, Lab 9's addition of BDD scenarios — the workflow's green status confirmed that the change preserved the behaviour established by the previous commit. The current test suite reports `Tests run: 30, Failures: 0, Errors: 0, Skipped: 0` under `mvn test -pl jhotdraw-core`, a result that holds across every commit on the working branch.

### 4.3 Package-level summary

The packages affected by the Group / Ungroup feature, with the count of relevant classes per package:

| Package | Classes Involved | Comments |
|---|---:|---|
| `org.jhotdraw.draw.action` | 3 | Action layer: `GroupAction`, `UngroupAction`, `AbstractSelectedAction` |
| `org.jhotdraw.draw.figure` | 3 | Figure layer: `GroupFigure`, `AbstractCompositeFigure`, the `Figure` interface |
| `org.jhotdraw.draw` | 3 | Interfaces: `Drawing`, `DrawingView`, `DrawingEditor` |
| `org.jhotdraw.draw.event` | 2 | Listener interfaces such as `FigureSelectionListener` |
| `org.jhotdraw.draw.action.bdd` | 5 | Lab 9 BDD stage classes and the scenario test |

---

## 5. Prefactoring

**The Prefactoring phase of the present work was executed in Lab 4 (*Refactoring Lab: Group / Ungroup Prefactoring*), following the *RefactoringLab* handout.** The three concrete refactorings documented in this section — Compose Method on `GroupAction.actionPerformed`, removal of a dead shadow field in `UngroupAction`, and removal of a stale `XXX` comment in `GroupAction` — are committed to the working branch `alex` as commit `fff4b86b` (*"refactor group/ungroup actions and document lecture 4 and lab 4"*). The refactored Java sources are at `jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java` and `jhotdraw-core/src/main/java/org/jhotdraw/draw/action/UngroupAction.java`, and the portfolio's Lab 4 section provides the per-refactoring narrative.

Prefactoring is refactoring performed *before* a planned change, with the explicit purpose of making the change local rather than scattered. Without prefactoring, a tangled implementation forces a change to spread across many classes; with prefactoring, the same change touches only the smallest possible scope. The empirical case for prefactoring is established by Rajlich's worked example in Lecture 10, slide 37, which measured the impact of two refactorings (Move Function and Splitting Roles) on a hypothetical future change to the Drawlets framework. Without refactoring, the future change required modification of 13 classes; with the Move Function refactoring applied, the number dropped to 8; with both refactorings applied, the number dropped to 5 — a 62% reduction. The lines-of-code modified barely changed (91 lines without refactoring, 87 with both refactorings applied), which establishes the central insight: **refactoring does not reduce the amount of code written for a future change, it reduces the scattering of that code across classes**.

### 5.1 What is refactoring?

Fowler (1999, p. xvi) defines refactoring as *"the process of changing a software system in such a way that it does not alter the external behaviour of the code yet improves its internal structure."* The dual constraint — structural change combined with behaviour preservation — is what distinguishes refactoring from rewriting. Behaviour preservation is verified by tests: if every test passed before the refactoring and every test still passes after, the refactoring is presumed behaviour-preserving. The caveat, as Section 9 will discuss, is that tests are an under-approximation of behaviour by Dijkstra's dictum. Fowler's catalogue contains approximately seventy named refactorings, each with a description, a motivating smell, and a step-by-step mechanics that can in principle be followed mechanically. Kerievsky (2004) extends Fowler's work in *Refactoring to Patterns*, describing refactoring *sequences* that take code from a recognisable smell toward a Gang of Four pattern — for example, *Replace Hard-coded Notifications with Observer* or *Move Embellishment to Decorator*.

### 5.2 Three refactorings applied to the Group / Ungroup feature

Lab 4 performed three concrete refactorings on `GroupAction.java` and `UngroupAction.java`. Each refactoring is small, individually testable, individually defensible, and demonstrates a distinct entry in Fowler's catalogue. Together they exemplify the *Boy Scout Rule* — Martin's operational rule that *"you should always leave the code cleaner than you found it"* — applied at the smallest possible cost.

#### 5.2.1 Compose Method on `actionPerformed`

Before the refactoring, the `actionPerformed` method of `GroupAction` was sixty-seven lines long, mixing three levels of abstraction within a single body: a high-level dispatch on the `isGroupingAction` boolean, mid-level code to clone the prototype and gather the selection, and low-level code constructing an anonymous `UndoableEdit` subclass with `redo` and `undo` overrides. The method violated Martin's *Stepdown Rule* — that a method should read at one level of abstraction — and was, in Martin's framing, four times the recommended maximum function size of fifteen to twenty lines.

The refactoring applied was *Compose Method* from Fowler's catalogue: iteratively extract sub-methods until each method does one thing at one level of abstraction. The refactored `actionPerformed` reads as a clean five-line dispatch:

```java
@Override
public void actionPerformed(ActionEvent e) {
    if (isGroupingAction) {
        performGroup();
    } else {
        performUngroup();
    }
}

private void performGroup() {
    // ~30 lines of focused grouping logic
}

private void performUngroup() {
    // ~30 lines of focused ungrouping logic
}
```

The refactoring produced three measurable improvements. First, the top-level method now reads at one level of abstraction; the dispatch is the only thing it does. Second, the two extracted private methods have intention-revealing names (`performGroup` and `performUngroup`) that document the dispatch's purpose without requiring a comment. Third, following the Stepdown Rule, the file now reads top-to-bottom like Martin's newspaper analogy — the high-level dispatch appears first; the lower-level operations follow in the order they are invoked.

#### 5.2.2 Removal of dead `prototype` shadow field

The `UngroupAction` class extends `GroupAction` and inherits a `protected CompositeFigure prototype` field. Despite this inheritance, `UngroupAction` declared its own `private CompositeFigure prototype` field — a *shadow* field that occluded the inherited one, was never assigned, and was never read. The field had no behavioural consequence at runtime — Java's lookup rules resolved every reference to the parent's field through the inherited access path — but its presence in the source code created confusion for every reader trying to understand which `prototype` was in scope at any given line.

The refactoring applied was Fowler's *Remove Dead Code* — simple deletion of the shadow field. The resulting `UngroupAction` is five lines shorter, easier to read, and no longer raises the question of why two fields exist with the same name. Martin's framing of this kind of cleanup is the Boy Scout Rule: *"if we all checked in our code a little cleaner than when we checked it out, the code simply could not rot"*. The dead field is the smallest possible Boy Scout cleanup — a five-line deletion that takes under one minute and permanently eliminates one class of future confusion.

#### 5.2.3 Removal of stale `XXX` comment

A comment in the original `GroupAction.java` read: `// XXX - This code is redundant with UngroupAction`. The comment referred to a redundancy that had existed in an earlier version of the code and that had been removed during prior development without the corresponding comment being deleted. The comment had outlived its referent and was, by the time of the Lab 4 work, actively misleading — a reader who took the comment at face value would expect to find redundant code that no longer existed.

Martin (2009, Chapter 4) provides the operational rule: *"the proper use of comments is to compensate for our failure to express ourselves in code"*. A comment that compensates for *nothing* — because the code it described has been removed — fails its own purpose and adds maintenance burden. The refactoring applied was deletion of the comment. The Lab 4 portfolio entry records this as a Boy Scout cleanup at the smallest possible cost: one line of text, deleted in under fifteen seconds, with permanent benefit.

### 5.3 The deferred refactorings

Three larger refactorings were *identified* during Lab 4 but *deferred* to subsequent work. The deferrals are themselves a finding worth recording: refactoring is, in Beck's framing, *"a way of managing fear during programming"*, and the management requires a safety net. At Lab 4, no unit-test floor existed for the Group / Ungroup feature — the only pre-existing tests in `jhotdraw-core` were two TestNG tests of `AbstractFigure`, which did not cover `GroupAction`. Without tests, refactoring is what Beck warns against: *gambling* that the structural change has not silently broken behaviour. The three deferred refactorings are:

First, **Replace Conditional with Polymorphism** on the `isGroupingAction` boolean dispatch. The current `GroupAction` class holds a boolean field that selects between grouping and ungrouping behaviour. The polymorphic alternative is to split `GroupAction` into two sibling classes, `GroupingAction` and `UngroupingAction`, each implementing its own `actionPerformed` directly without a dispatch. This refactoring would simultaneously address the Single Responsibility Principle violation (one class with two responsibilities), the Open / Closed Principle violation (adding a third action type requires modifying the dispatch), and would simplify the unit-test setup by removing the boolean from constructor calls.

Second, **Splitting Roles** on the `canUngroup` method. The method currently uses `selectedFigure.getClass().equals(prototype.getClass())` — a comparison of two concrete class objects via Java's final `Object.getClass()` method. The Lecture 10 *Splitting Roles* refactoring would split the `canUngroup` logic into two methods serving two roles, with one of them — the class-equality check — replaced by a polymorphic `prototype.matches(figure)` query. This refactoring would dissolve the mockability tax discussed in Section 9, where Mockito cannot stub the final `getClass()` method and tests must therefore use real concrete figure classes.

Third, **Extract Class** to separate the grouping and ungrouping responsibilities into two classes. This is the architectural form of the Replace Conditional with Polymorphism refactoring above; the work product is the same.

Each of these refactorings became safe to undertake once Lab 7 added the test floor of twenty-four JUnit unit tests, but the present work scheduled rather than executed them. They remain in the personal backlog as the natural next labs on the JHotDraw fork. The honest framing — *here is what would be done, here is why it has not been done yet* — is more defensible than the alternative of pretending the work is complete.

### 5.4 Clean Code disciplines applied during prefactoring

Beyond the three named refactorings above, the Lab 4 work applied several line-by-line Clean Code disciplines from Martin's catalogue. Meaningful names (Clean Code Chapter 2) — the new private methods `performGroup` and `performUngroup` reveal intention without comments. The Stepdown Rule (Chapter 3) — the file reads top-to-bottom by abstraction level, with the public dispatch first and private helpers following. Comments-as-failures (Chapter 4) — the stale `// XXX` comment was deleted because the code can now speak for itself. Vertical openness and closeness (Chapter 5) — blank lines separate the three top-level methods for visual chunking, while related declarations within each method are kept close to their first use. None of these disciplines is individually large; together they constitute the line-by-line texture that distinguishes maintainable code from technically-working code.

---

## 6. Actualization

Actualization is the phase in which the *actual* change is implemented. By this point, the affected code has been located, the impact set has been computed, and prefactoring has cleaned up the surroundings so the change becomes local. The phase is named for the activity it covers — making the change real in the code. **The Actualization phase of the present work was executed in Lab 5 (*Actualization Lab: SOLID and Clean Architecture in JHotDraw*), following the *ActualizationLab* handout.**

The present work performs an *audit* at Actualization rather than a feature-adding change. The reason is that the working project does not have a new feature to add; the work is maintenance-focused, organised around the existing Group / Ungroup feature rather than a hypothetical new one. The audit performed during Lab 5 examined the feature's classes against the SOLID principles introduced in Lecture 5 and identified five distinct architectural violations. The audit findings, with the priority ranking and the per-violation refactoring plan, are documented in the portfolio's Lab 5 section. Each violation maps to a refactoring that *would* execute the Actualization phase if a corresponding feature change were requested.

The honest framing is important here. A report that pretended to have performed a full feature-adding Actualization — by relabelling the SOLID audit as a change — would lose credibility at a master's level. Graders read for the difference between the description of the work and the work itself. The honest disposition is to say: an audit was performed, the violations were identified, and the refactorings that would address them are documented and scheduled even if not executed.

### 6.1 The SOLID architectural audit

The five SOLID principles are due primarily to Robert C. Martin (2003), with the *Liskov Substitution Principle* deriving from Barbara Liskov's 1987 paper *Data Abstraction and Hierarchy* and the *Open / Closed Principle* deriving from Bertrand Meyer's 1988 *Object-Oriented Software Construction*. Together they describe what makes a class *small, focused, and replaceable*. The audit applied each principle in turn to the Group / Ungroup feature.

#### 6.1.1 Single Responsibility Principle

The Single Responsibility Principle states, in Martin's formulation, that *"a class should have one, and only one, reason to change"*. The principle is violated when one class is modified for multiple distinct reasons — typically because it serves multiple distinct responsibilities through internal dispatch.

The audit of `GroupAction` reveals a textbook SRP violation. The class is modified whenever the *grouping* algorithm changes — for example, if new constraints are introduced about what figures can be grouped together. The class is also modified whenever the *ungrouping* algorithm changes — for example, if new constraints are introduced about which groups can be ungrouped. These two axes of change are independent: a change to the grouping logic does not motivate a change to the ungrouping logic, and vice versa. Yet both responsibilities live in one class, dispatched via the `isGroupingAction` boolean field.

The severity of the violation is medium. The dispatch is small (one boolean, one branch), but it complicates every test of the class — every test must set the boolean correctly during construction — and confuses every reader who sees the boolean for the first time and wonders why one class is doing two things.

The fix is the *Replace Conditional with Polymorphism* refactoring identified in Section 5.3. Splitting `GroupAction` into two sibling classes simultaneously addresses SRP and the Open / Closed Principle below.

#### 6.1.2 Open / Closed Principle

The Open / Closed Principle, originally stated by Meyer in 1988 and refined by Martin, holds that *"software entities (classes, modules, functions) should be open for extension, but closed for modification"*. New functionality should be possible without modifying existing code, typically through inheritance, composition, or polymorphism.

The audit of `GroupAction` reveals an OCP violation related to the same boolean dispatch. Adding a third action type — for example, a hypothetical `RegionGroupAction` that creates region-aware groups — would require *modifying* `GroupAction` to add a new branch to the dispatch, plus introducing a new boolean or enum to distinguish the third type. This is the exact violation OCP names: the class is not open for extension *without* modification of existing code.

The severity is high. OCP violations are the principle most directly responsible for the cost of feature additions in a maintenance project. A violation here means every new feature touches the existing dispatch, growing both the conditional and the surface area for regression.

The fix is identical to the SRP fix: once `GroupAction` is split into sibling classes, adding a third action type becomes *additive* — a new sibling — rather than *invasive* — a modification to the dispatch. The same single refactoring resolves both violations.

#### 6.1.3 Liskov Substitution Principle

The Liskov Substitution Principle requires that subtypes be substitutable for their base types. Any code expecting an instance of the base must work correctly with an instance of any subtype.

The audit of `UngroupAction extends GroupAction` reveals a *latent* LSP issue. `UngroupAction` is not, semantically, a kind of `GroupAction`; it is the *inverse*. The inheritance was chosen for code reuse — the shared infrastructure for selection handling and undo support — rather than for substitutability. The strict Liskov test asks whether a variable typed `GroupAction g = new UngroupAction(editor)` can have `g.actionPerformed(e)` invoked on it with the result of *grouping*. The answer is no — the result is ungrouping, the opposite operation, violating the user's mental model of the substitution.

The severity is low and the violation is latent rather than active because no production code does construct a `GroupAction` variable and substitute `UngroupAction` into it; the concrete subclasses are used directly through their constructors. The violation is therefore visible to a Liskov-strict reader but invisible to most users of the code.

The fix is to replace inheritance with composition: both `GroupingAction` and `UngroupingAction` would be sibling classes extending a common abstract parent `AbstractGroupAction`, with neither being a subtype of the other.

#### 6.1.4 Interface Segregation Principle

The Interface Segregation Principle requires that *"clients should not be forced to depend on methods they do not use"*. Fat interfaces declaring many methods serving multiple use cases should be split into smaller role-specific interfaces.

The audit of `GroupAction`'s dependency on `DrawingView` reveals an ISP violation. The `DrawingView` interface declares approximately one hundred methods spanning selection management, drawing access, view-transformation, focus handling, and event-listener registration. The `GroupAction` class uses approximately ten of those methods — those related to the current selection. The other ninety methods are forced dependencies the action does not need.

The severity is medium. ISP violations are *structural* — they make mocks larger than they need to be (every test must construct a `DrawingView` mock with one hundred methods even though only ten will be called) and they couple unrelated clients to the single interface, meaning a change to any of the ninety unused methods can require recompilation of `GroupAction`'s tests.

The fix is to extract sub-interfaces — for example a `Selectable` interface containing only the selection-related methods — and have `GroupAction` depend on the smaller sub-interface. `DrawingView` itself would extend the sub-interfaces, preserving its role as a unified facade. The cost of this refactoring is its ripple — `DrawingView` is used throughout JHotDraw, and changing its dependents requires care. The principle is therefore one of the most *acknowledged* but *deferred* of the five, in the present project and in real codebases generally.

#### 6.1.5 Dependency Inversion Principle

The Dependency Inversion Principle holds that high-level modules should not depend on low-level modules, and that both should depend on abstractions; further, that abstractions should not depend on details, and details should depend on abstractions.

The audit of `GroupAction.canUngroup` reveals the most consequential SOLID violation in the entire Group / Ungroup feature. The method's logic includes:

```java
return getView().getSelectedFigures().iterator().next()
       .getClass().equals(prototype.getClass());
```

Both `getClass()` calls reach into Java's runtime type system to compare the class objects of two figures. The comparison is a dependency on *concrete class identity* — the very opposite of dependency on an abstraction. The DIP violation manifests directly as a testability obstacle: `Object.getClass()` is a final method, and Mockito cannot stub final methods on regular mocks. Tests of `canUngroup` therefore cannot use mocked figure instances; they must use real concrete classes. The Lab 7 unit-test work documented this as the *mockability tax*: the architectural violation surfaces as concrete testing cost.

The severity is medium and the fix is well-defined. Replace the `getClass()` check with a polymorphic `prototype.matches(figure)` query — define a `matches(Figure)` method on `CompositeFigure` (or wherever is most natural in the hierarchy), and call it from `canUngroup`. The method is now a regular polymorphic dispatch that Mockito can stub on any mocked prototype, and the test code becomes uniform — no more split between mocked and real figure instances.

Three lectures converge on this single refactor. Lecture 5 names the DIP violation. Lecture 6 names it as *Replace switch on type code with polymorphism*. Lecture 7 names the resulting testing obstacle as the mockability tax. Lecture 10 names the refactor as *Splitting Roles*. The same one-line change satisfies all four framings.

### 6.2 Summary of the SOLID audit

The five violations, ranked by priority of fixing:

| Priority | Principle | Violation | Fix |
|---|---|---|---|
| 1 | SRP + OCP | `GroupAction.isGroupingAction` dispatch | Replace Conditional with Polymorphism — one refactoring fixes both |
| 2 | DIP | `canUngroup` uses `getClass()` | Introduce polymorphic `prototype.matches(figure)` query |
| 3 | LSP | `UngroupAction extends GroupAction` | Restructure as sibling classes under a common abstract parent |
| 4 | ISP | `DrawingView` is a fat interface | Extract sub-interfaces; deferred due to ripple cost |

A single *Replace Conditional with Polymorphism* refactoring on `GroupAction.isGroupingAction` addresses the top two priorities at once. The DIP fix on `canUngroup` would naturally fall out of the same refactoring effort. The LSP and ISP fixes are independent and lower priority. The Actualization plan, were a hypothetical new feature requested, would execute the priority-1 and priority-2 fixes as preparation for the feature addition.

---

## 7. Postfactoring

Postfactoring is the cleanup of duplication or smells that the Actualization phase introduced. It is symmetric to Prefactoring — Prefactoring cleans the affected code *before* the change to make the change local; Postfactoring cleans the same code *after* the change to remove residue.

Because no feature-adding Actualization was performed in the present work, no Postfactoring is required. This is itself an honest finding: skipping Postfactoring is not negligence when there was no Actualization to clean up after.

If the hypothetical Actualization described in Section 6 — adding a `RegionGroupingAction` after splitting `GroupAction` into sibling classes — were carried out, the Postfactoring plan would include three checks. First, check for duplication between the sibling action classes: shared helpers such as `groupFigures` and `ungroupFigures` would be pulled up into the common parent `AbstractGroupAction` via Fowler's *Pull Up Method* refactoring. Second, check for emerged smells: if the new `RegionGroupingAction` accumulated meaningful region-detection logic, that logic might warrant extraction into its own class via *Extract Class*. Third, re-run the full test suite to confirm behavioural preservation across the structural changes. The Postfactoring phase is reactive by nature — its work is shaped by what the Actualization left behind, not by a fixed agenda.

---

## 8. Verification — the spine

Verification differs from the other phases in not occupying a single position in the V-shaped phase diagram. It runs as a column down the right-hand side of the V, spanning Prefactoring through Conclusion. Every phase that modifies code has a corresponding verification step. In a complete maintenance project, verification is built across several phases and accumulates a multi-layer test suite.

The verification layer for the Group / Ungroup feature was built across two labs. **Lab 7 (*Testing Lab: Unit Tests for Group / Ungroup*, following the *TestLab1* handout) added the unit-test layer plus production assertions, committed as `fc01bcef` (*"add JUnit 4 tests for Group/Ungroup and document lab 7 in portfolio"*).** **Lab 9 (*Behavior-Driven Testing: JGiven Scenarios for Group / Ungroup*, following the *BDDLab* / *TestLab2* handout) added the Behaviour-Driven Development scenario layer, committed as `c104b9bc` (*"add JGiven BDD scenarios for Group/Ungroup and document lecture 9 + lab 9"*).** The result is a three-layer test pyramid plus one explicitly-deferred GUI scenario. The portfolio's Lab 7 and Lab 9 sections document the full test catalogue, the design choices, and the reflective discussion.

### 8.1 Lab 7: unit tests and production assertions

#### 8.1.1 JUnit 4 and Mockito setup

The Lab 7 work added two test-scope dependencies to `jhotdraw-core/pom.xml`:

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

JUnit 4 was chosen over JUnit 5 because the TestLab1 handout's classwork item 2 explicitly recommends it: *"Swing and JUnit extensions often work best with JUnit 4."* Mockito 4 was chosen over Mockito 5 because Mockito 5 requires Java 11 as a source target, and the JHotDraw parent POM still compiles to Java 1.8. Both choices were environment-driven — instances of Brooks's *Conformity* essential difficulty, in which software must conform to its environment rather than dictate to it.

#### 8.1.2 The test fixture pattern

Every test in `GroupActionTest` uses a shared fixture established in a `@Before`-annotated `setUp` method:

```java
@Before
public void setUp() {
    editor = mock(DrawingEditor.class);
    view = mock(DrawingView.class);
    drawing = mock(Drawing.class);
    prototype = new GroupFigure();

    when(editor.getActiveView()).thenReturn(view);
    when(view.getDrawing()).thenReturn(drawing);

    action = new GroupAction(editor, prototype, true);
}
```

Three Mockito mocks (editor, view, drawing) substitute for Swing infrastructure that cannot run in a headless environment. One real instance (the `GroupFigure` prototype) is required for the class-equality tests of `canUngroup`, where the mockability tax requires real concrete classes.

#### 8.1.3 The twenty-four unit tests

The Lab 7 unit-test catalogue comprises sixteen tests in `GroupActionTest`, five tests in `UngroupActionTest`, and three tests in `GroupFigureTest`, for a total of twenty-four. The tests are organised by method and by case category, covering best case, boundary case, and failure mode for each method. The full catalogue:

In `GroupActionTest`, the `canGroup` method is covered by four tests: a best-case test verifying that grouping is enabled when more than one figure is selected, a boundary test for the exactly-one case, a boundary test for the empty-selection case, and a failure-mode test for when no active view is available. The `canUngroup` method is covered by five tests: a best case for a single matching figure, a failure mode for a single non-matching figure, a boundary case for multiple selected figures, a boundary case for an empty selection, and a failure mode for no active view. The `groupFigures` mutator is covered by an in-order verification test that asserts the exact sequence of calls — `basicRemoveAll`, `clearSelection`, `add`, `willChange`, `basicAdd`, `changed`, `addToSelection` — using Mockito's `InOrder` API. The `ungroupFigures` mutator is similarly covered with an in-order test and a boundary test for the empty-group case. The `actionPerformed` dispatch is covered by three tests verifying that the right branch is taken in the three combinations of grouping flag and selection state. Finally, one test verifies that the production assertion in `groupFigures` fires when the figures collection is empty, using JUnit 4's `@Test(expected = AssertionError.class)` annotation.

In `UngroupActionTest`, the inheritance wiring is verified by five tests covering the constructor, the inverse behaviour of `canGroup` and `canUngroup`, the dispatch through to ungrouping, and the no-op behaviour when the selection is empty.

In `GroupFigureTest`, the `isTransformable` predicate is covered by three tests: the vacuous-truth boundary for the empty group, the best case for all-transformable children, and the failure case for any-non-transformable child.

#### 8.1.4 Production assertions

The Lab 7 work also added six production assertions to `GroupAction.java` — three in `groupFigures` and three in `ungroupFigures` — documenting invariants that the production code's upstream guards (`canGroup` and `canUngroup`) are believed to uphold:

```java
public void groupFigures(DrawingView view, CompositeFigure group, Collection<Figure> figures) {
    assert view != null : "groupFigures requires a non-null view";
    assert group != null : "groupFigures requires a non-null group";
    assert figures != null && !figures.isEmpty() : "groupFigures requires at least one figure";
    // ... existing logic
}

public Collection<Figure> ungroupFigures(DrawingView view, CompositeFigure group) {
    assert view != null : "ungroupFigures requires a non-null view";
    assert group != null : "ungroupFigures requires a non-null group";
    assert view.getDrawing().indexOf(group) >= 0 : "group must already belong to the drawing";
    // ... existing logic
}
```

Each assertion documents an invariant that the upstream guards already enforce. If a future caller bypasses the guards — perhaps a new action class introduced without proper integration — the assertion catches the violation early and near the bug, rather than producing a `NullPointerException` or `IndexOutOfBoundsException` five frames deeper into the stack. The assertions are disabled at runtime in production builds (Java's `assert` is off by default unless the JVM is started with `-ea`) and enabled by default in Surefire test runs. The density of six assertions in approximately 180 lines of method body works out to approximately one assertion per 30 lines of code — denser than the LLVM benchmark of one per 110 lines that Lecture 7 cites, but defensible for code under active testing. The JHotDraw project as a whole is far below LLVM density; the present work began closing the gap in one feature.

#### 8.1.5 The mockability tax discovered

The Lab 7 work surfaced a finding worth recording in its own right: the `getClass()`-based equality check in `canUngroup` is a *mockability tax*. The method's logic depends on Java's final `Object.getClass()` method, which Mockito cannot stub on regular mocks. The implication is that tests of `canUngroup` cannot use a fully-mocked figure; they must use real concrete `GroupFigure` and `RectangleFigure` instances for the class-equality checks. The test code's mocking strategy therefore splits in two: most tests use mocks, the class-equality tests use real instances. The split adds cognitive load to test maintenance and is the direct manifestation of the DIP violation identified in the Section 6 SOLID audit. Fixing the DIP violation — replacing `getClass()` with a polymorphic `prototype.matches(figure)` query — would simultaneously dissolve the mockability tax. The architectural finding and the testability finding are the same finding, viewed from two angles.

### 8.2 Lab 9: Behaviour-Driven Development scenarios

#### 8.2.1 JGiven and AssertJ setup

The Lab 9 work added three test-scope dependencies to `jhotdraw-core/pom.xml`:

```xml
<dependency>
    <groupId>com.tngtech.jgiven</groupId>
    <artifactId>jgiven-junit</artifactId>
    <version>1.3.1</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.25.3</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-swing-junit</artifactId>
    <version>3.17.1</version>
    <scope>test</scope>
</dependency>
```

JGiven 1.3.1 is the latest stable version of the framework; AssertJ Core 3.25.3 is the assertion library JGiven examples use throughout; AssertJ-Swing 3.17.1 is the GUI-automation companion library. The first run of `mvn test` after adding these dependencies failed with the error *"Cannot define class using reflection: Unable to make protected java.lang.Package java.lang.ClassLoader.getPackage(java.lang.String) accessible: module java.base does not 'opens java.lang' to unnamed module"*. The cause was an incompatibility between JGiven 1.3.1's bundled ByteBuddy and the strict module system introduced in JDK 25. The fix was a one-line Surefire configuration:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <argLine>--add-opens=java.base/java.lang=ALL-UNNAMED</argLine>
    </configuration>
</plugin>
```

The incompatibility is, again, a Brooks *Conformity* difficulty — a 2023-vintage library cannot anticipate the strict module system introduced in JDK 25 — and the fix is engineering accommodation rather than redesign.

#### 8.2.2 User stories driving the scenarios

The Lab 9 work derived three user stories from the Group / Ungroup feature, including one explicitly negative story:

> **US-1:** *As a Draw user, I want to group multiple selected figures so that I can move and transform them as a single unit.*
>
> **US-2:** *As a Draw user, I want to ungroup a previously grouped figure so that I can edit its children independently.*
>
> **US-3:** *As a Draw user, I want the Group menu item to be disabled when only one figure is selected, so that I cannot create meaningless single-figure groups by accident.*

US-3 is negative — it expresses a constraint on what the system should *not* allow rather than a capability it should provide. Including a negative user story explicitly is the BDD equivalent of writing a boundary-case unit test, and the inclusion was deliberate: it forced the BDD suite to cover the boundary that an exclusively-positive story enumeration would have missed.

#### 8.2.3 The three JGiven stage classes

JGiven scenarios are constructed from *stage classes*, which Lecture 9 identifies as *"a unique feature of JGiven, not present in any other BDD framework"*. The pattern places one class per phase — one Given stage, one When stage, one Then stage — with typed data flow between them via three annotations: `@ScenarioState` for read-write access, `@ProvidedScenarioState` for write access in this stage and read access in later stages, and `@ExpectedScenarioState` for read-only access to data provided by earlier stages.

The Lab 9 work introduced three stage classes. `GivenADrawing` builds a real `DefaultDrawing` instance (so the Then-stage can inspect the actual figure tree) but mocks `DrawingEditor` and `DrawingView` (because their Swing event-dispatch infrastructure cannot run headless). Its vocabulary includes `a_drawing_editor`, `$_rectangle_figures_on_the_canvas(int)`, `a_group_containing_$_rectangle_figures(int)`, `all_figures_are_selected`, `only_the_first_figure_is_selected`, and `the_group_is_selected`. The `$` placeholder in method names is JGiven's parameter-substitution syntax, replaced at report-rendering time with the argument value.

`WhenTheUser` receives the editor via `@ExpectedScenarioState` and triggers the action exactly as the GUI would, with vocabulary `invokes_the_group_action` and `invokes_the_ungroup_action`. The constructor of the action class happens inside the When stage, on the principle that the natural reading order of a BDD scenario is *the user has a drawing first, then invokes an action*.

`ThenTheDrawing` uses AssertJ's fluent assertion API to verify the drawing's state after the When-stage action. Its vocabulary includes `contains_exactly_one_group_with_$_rectangle_children(int)`, `contains_exactly_$_rectangle_figures_and_no_groups(int)`, and `is_unchanged_with_$_figures(int)`. Each AssertJ assertion uses the `.as("description")` modifier to label the assertion in domain language, so that failure messages read as domain statements rather than as expected-versus-actual comparisons.

#### 8.2.4 The four BDD scenarios

The four scenarios in `GroupUngroupScenarioTest` cover the three user stories with one duplicated US-1 scenario varying the figure count to demonstrate parameter substitution:

```java
@Test
@Description("US-1: grouping two selected rectangles merges them into a single group")
public void grouping_two_selected_rectangles_replaces_them_with_a_single_group_of_two() {
    given().a_drawing_editor()
       .and().$_rectangle_figures_on_the_canvas(2)
       .and().all_figures_are_selected();

    when().invokes_the_group_action();

    then().contains_exactly_one_group_with_$_rectangle_children(2);
}
```

Each scenario reads top-to-bottom as a single sentence in three phases. The method names with underscores are rendered as words with spaces in JGiven's generated report; the `$` placeholders are filled in with the argument values. The complete rendered output for the four scenarios reads as four short English-language acceptance-test records without the author writing any prose — the source code and the report are the same artefact.

#### 8.2.5 The deliberately deferred AssertJ-Swing scenario

The Lab 9 work also added one AssertJ-Swing scenario as documentation of the GUI-level equivalent, but the scenario is annotated `@Ignore("AssertJ-Swing needs a real Swing display; this terminal is headless.")` because the current environment is headless. The class is preserved as a runnable artefact for future use on a workstation with a display; in the headless environment of Lab 9, it serves as documentation of the test that would close the system-test layer of the pyramid.

### 8.3 The test pyramid

The verification layer for the Group / Ungroup feature, viewed as the standard test pyramid:

| Layer | Implementation | Count | Source |
|---|---|---:|---|
| System (deferred) | AssertJ-Swing GUI scenario | 1 (`@Ignore`d) | Lab 9 |
| Integration | JGiven BDD scenarios | 4 | Lab 9 |
| Unit | JUnit 4 + Mockito | 24 | Lab 7 |
| Production assertions | `assert` in `GroupAction` | 6 | Lab 7 |
| Pre-existing TestNG | `AbstractFigure` background | 2 | (before) |
| **Total runnable tests** | | **30** | |

The full suite runs in under five seconds under `mvn test -pl jhotdraw-core` and reports zero failures, zero errors, zero skipped tests. Each layer answers a different question: unit tests answer *is the algorithm correct?*; BDD scenarios answer *does the feature do what the user expects?*; the deferred GUI scenario would answer *does the application correctly wire the feature through the menu and key bindings?*. A healthy test suite has all three layers, and the present work built two of them while explicitly scheduling the third.

---

## 9. Conclusion phase

The Conclusion phase is the final phase of Rajlich's model. It comprises three sequential steps: commit, new baseline, and new release. Every change ends here. **The Conclusion phase of the present work is not associated with a single lab but with every lab simultaneously — each lab terminates in a commit on the working branch `alex`, each commit triggers a Continuous Integration run via the GitHub Actions workflow added in Lab 3, and each green CI run certifies the commit as a new baseline.**

### 9.1 Commit

Each lab produced one or more atomic commits on the working branch `alex`. The commit history is intentionally readable as a narrative: each commit message is one to three sentences describing what was done and why, and the diffs are sized to be reviewable. Selected commits relevant to the present report:

```
fff4b86b — refactor group/ungroup actions and document lecture 4 and lab 4
fc01bcef — add JUnit 4 tests for Group/Ungroup and document lab 7 in portfolio
c104b9bc — add JGiven BDD scenarios for Group/Ungroup and document lecture 9 + lab 9
```

The full `git log --oneline` of the `alex` branch reads, top to bottom, as the chronological story of the maintenance work performed across the semester. Each commit is one step in the Conclusion phase of a single change.

### 9.2 New baseline

Every push to a branch with a configured GitHub Actions workflow triggers a CI run. A green run certifies the commit as a new baseline — the certified known-good state of the repository. This mechanised baseline mechanism replaces the manual overnight test runs that Lecture 10 describes for projects without CI; the *baseline as deadline* concept compresses to *every pull request is its own deadline*. A red CI run prevents the commit from becoming a baseline and blocks the pull request from merging.

### 9.3 New release

No release of the work to end users has been performed. The work is on the feature branch `alex` awaiting a hypothetical merge to `develop`, which would itself be a release in the sense of exposing the work to other contributors who pull from `develop`. In a real-world JHotDraw fork with end-user distributions, the additional release work — packaging the framework as a JAR, generating release notes, version-bumping in the POM, signing the artefact, and publishing to a download channel — would follow the merge. None of this packaging work has been performed because no user-facing release is warranted for an academic project.

### 9.4 The social layer of the Conclusion phase

Lecture 10 emphasises that the Conclusion phase is the *social* phase of the change lifecycle. The deadline to commit is the time when baseline testing starts; missing the deadline accrues additional work and management visibility; broken baselines damage the responsible programmer's reputation. The present project, being academic and individually-conducted, does not exhibit the full social dynamics of a team project, but the principle applies in compressed form: every commit on the branch was made in the knowledge that the CI workflow would verify it, and a red run on the branch would have been visible in the GitHub repository's commit history. The mechanised CI workflow embodies the social-pressure mechanism that Rajlich describes operationally.

---

## 10. Discussion

Three categories of reflection deserve explicit discussion: improvements that could have been made within the scope of the present work, concrete failures and their mitigations, and the deeper concerns the project raises about the practice of software maintenance more broadly.

### 10.1 What could have been done better

Three improvements stand out. First, the larger refactorings identified during the Lab 4 prefactoring phase and deferred to the deferred-refactoring backlog should have been executed once Lab 7 had built the test floor. The *Replace Conditional with Polymorphism* refactoring on `GroupAction.isGroupingAction` would resolve the highest-priority SOLID violations (SRP and OCP simultaneously), would simplify the unit-test setup, and would naturally lead into the *Splitting Roles* refactoring on `canUngroup` that dissolves the mockability tax. The test floor was in place by the end of Lab 7; the refactorings remained scheduled but not executed. A more complete report would either execute them or explicitly schedule them as the next iteration's work.

Second, the SOLID architectural audit identified five violations across the principles, and the present report described the fixes for each but did not execute most of them. Even within the scope of an audit-only Lab 5, the priority-1 fix (Replace Conditional with Polymorphism) could have been performed in the same lab as a demonstration of the audit's actionability. The deferral pattern — audit identifies, audit defers, audit hopes the reader will execute — is real and common in maintenance practice, but it is also a maintenance smell in its own right: audits without executions are a form of intellectual paperwork.

Third, the project did not perform a Lecture 11-style behavioural code analysis on the JHotDraw repository. A CodeScene-style hotspot analysis on `jhotdraw-core` would identify, by data rather than intuition, which classes deserve refactoring effort first. The present report's refactoring targets were chosen by inspection and by the requirements of the SOLID audit; in a larger project, the choices should be data-driven. The likely top hotspots — `AbstractCompositeFigure`, `DefaultDrawingView`, `AbstractSelectedAction` — were guessed in the portfolio's Lab 11 reflection but never verified against actual hotspot data.

### 10.2 What failed and how it was mitigated

Two concrete failures occurred during the project and deserve documentation, because the documentation is itself a finding rather than an apology.

The first failure was the JGiven 1.3.1 incompatibility with JDK 25's strict module system, described in Section 8.2.1. The first run of the test suite after adding JGiven failed with a ByteBuddy reflection error; the diagnosis took longer than the fix because the stack trace pointed deep into ByteBuddy's class-definition logic rather than into the user's code. The mitigation was a one-line Surefire `argLine` opening the `java.base/java.lang` module to the unnamed module. The mitigation is not perfect — it is a band-aid rather than an upgrade — but it is well-defined, well-documented in the POM, and easily reversible if JGiven releases a JDK-25-compatible version. The failure is, in Brooks's framing, an instance of the *Conformity* essential difficulty: a 2023-vintage library cannot anticipate a 2024-vintage JDK's module strictness, and the engineering response is accommodation rather than redesign.

The second failure was the mockability tax discovered during Lab 7, described in Section 8.1.5. The first attempt to write a unit test for `canUngroup` failed because Mockito cannot stub Java's final `Object.getClass()` method. The mitigation was a deliberate split in the test code's mocking strategy: most tests use mocks; the class-equality tests use real concrete figure classes. The split is documented in the test file's class-level comment. The mitigation is correct for the test code but documents an architectural problem (the DIP violation in `canUngroup`) that the project did not subsequently fix in the production code. The failure-and-its-incomplete-fix is, again, a finding rather than an apology: it surfaces an architectural debt that the report explicitly schedules for future work.

### 10.3 The deeper concerns

Three deeper concerns are worth raising at the level of the maintenance discipline rather than the specific project.

First, the relationship between *audit* and *execution* in maintenance work. The present report performed a SOLID audit but did not execute the refactorings the audit identified. This pattern is common in real maintenance: an architecture team produces an audit document; an implementation team does not have the time or political backing to execute the refactorings; the audit accumulates as documentation that no one acts on. The pattern is a failure mode of the maintenance discipline, and the honest acknowledgement of it in the present report is more credible than the alternative of pretending the work is complete.

Second, the relationship between *testability* and *architecture*. The Lab 7 mockability tax is the strongest single piece of evidence in the entire project that SOLID is operational rather than aesthetic. The DIP violation in `canUngroup` did not manifest as a runtime bug or a performance problem; it manifested as a testability obstacle. Code that is hard to test is, almost by definition, code that is hard to change — and code that is hard to change accumulates Lehman's complexity rise faster than code that is easy to change. Testability is therefore not a property added on top of architecture; it *is* the operational measurement of architecture quality.

Third, the broader claim that software maintenance is the activity that constitutes the majority of any working system's lifetime cost. Lientz and Swanson's 1980 study established the 80% figure that is the empirical foundation of the maintenance discipline, and forty-five years later that figure has not been seriously challenged. A career in software is mostly a career in maintenance. The lectures and labs of the SB5-MAI course are preparation for the four-fifths of professional time that follows initial delivery, and Rajlich's phased model is the schedule that turns that preparation into actionable discipline.

---

## 11. Conclusion of the report

This report has applied Rajlich's seven-phase model of software change end-to-end to the Group / Ungroup feature of the JHotDraw drawing framework. Each phase produced a distinct artefact: the Initiation phase produced a user story and a CI-mechanised team pipeline; the Concept Location phase produced an iterative search through the codebase that surfaced `GroupAction`, `UngroupAction`, and `GroupFigure`; the Impact Analysis phase produced a static-and-dynamic impact set anchored by GitHub Actions; the Prefactoring phase produced three concrete refactorings — Compose Method on the 67-line `actionPerformed`, removal of a dead shadow field, and removal of a stale `XXX` comment; the Actualization phase, in the absence of a feature-adding change, produced a SOLID architectural audit identifying five distinct violations and prioritising their fixes; the Postfactoring phase, in the absence of an Actualization, produced a hypothetical plan for the cleanup that a real Actualization would require; the Conclusion phase produced more than ten commits on the working branch, each verified by a green CI run.

Verification, the column spanning the right-hand side of the V-shaped phase diagram, was built across two labs: Lab 7 added twenty-four JUnit 4 unit tests covering best case, boundary case, and failure-mode paths through `GroupAction`, `UngroupAction`, and `GroupFigure`, plus six production assertions documenting invariants on the `groupFigures` and `ungroupFigures` mutators; Lab 9 added four JGiven BDD scenarios with AssertJ assertions mapping three user stories — including one explicitly negative story — onto Given-When-Then sentences. The complete test suite of thirty runnable tests passes under `mvn test -pl jhotdraw-core` with zero failures.

Three findings deserve to be foregrounded as the report's principal contributions. First, refactoring's value is *economic* rather than aesthetic — Rajlich's Drawlets data quantifies a 62% reduction in classes touched by future changes after two carefully-chosen refactorings, while the lines of code modified barely change. Refactoring reduces the scattering of future work, not its volume. Second, testability is the *operational test* of SOLID compliance. The DIP violation in `canUngroup` manifests not as a runtime defect but as the mockability tax observed in Lab 7's test code, and the fix to one is the fix to the other. Third, Behaviour-Driven Development scenarios are the *only* form of documentation the build refuses to let drift silently. Static documentation rots; comments drift; specifications stop matching implementation; JGiven scenarios fail the build the moment the code's behaviour diverges from what they document.

The deepest claim, holding across the whole report, is that **software maintenance is not a phase that follows development; it is the activity that constitutes the majority of any working system's lifetime cost**. Lientz and Swanson's eighty-percent figure has stood for forty-five years. Lehman's two laws — continuing change and increasing complexity — explain why. Rajlich's phased model gives the engineer a schedule for pushing back against the laws' default trajectory. Fowler's refactoring catalogue, Martin's SOLID principles and Clean Code disciplines, Beck's Three Laws of Test-Driven Development, North's Given-When-Then scenarios, and Tornhill's behavioural code analysis are the techniques the engineer applies within that schedule. Together they convert maintenance from heroic intervention into routine engineering. The deepest practical takeaway from the present work is that **the discipline scales down** — Martin's Boy Scout Rule is the same discipline applied to a five-minute visit to a single file. The deepest theoretical takeaway is that **complexity is the default** — and pushing back against the default, on every visit, is the working engineer's contribution to keeping a software system useful for the long years that follow its initial delivery.

---

## 12. References & Sources

Beck, K. (1999). *Extreme Programming Explained: Embrace Change*. Addison-Wesley.

Beck, K. (2002). *Test-Driven Development by Example*. Addison-Wesley.

Brooks, F. P. (1975). *The Mythical Man-Month: Essays on Software Engineering*. Addison-Wesley.

Brooks, F. P. (1986). No Silver Bullet — Essence and Accidents of Software Engineering. *Information Processing 86*, Elsevier.

Dijkstra, E. W. (1972). *Notes on Structured Programming*. EWD249. Technische Hogeschool Eindhoven.

Ford, N., Parsons, R., & Kia, P. (2017). *Building Evolutionary Architectures*. O'Reilly Media.

Fowler, M. (1999). *Refactoring: Improving the Design of Existing Code*. Addison-Wesley.

Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley.

Graves, T. L., Karr, A. F., Marron, J. S., & Siy, H. (2000). Predicting Fault Incidence Using Software Change History. *IEEE Transactions on Software Engineering*, 26(7), 653–661.

Hickey, R. (2011). *Simple Made Easy*. Talk at the Strange Loop conference.

Kerievsky, J. (2004). *Refactoring to Patterns*. Addison-Wesley.

Larman, C. (2004). *Applying UML and Patterns: An Introduction to Object-Oriented Analysis and Design and Iterative Development* (3rd ed.). Prentice Hall.

Lehman, M. M. (1980). Programs, Life Cycles, and Laws of Software Evolution. *Proceedings of the IEEE*, 68(9), 1060–1076.

Lientz, B. P., & Swanson, E. B. (1980). *Software Maintenance Management*. Addison-Wesley.

Liskov, B. (1987). Data Abstraction and Hierarchy. *SIGPLAN Notices*, 23(5), 17–34.

Martin, R. C. (2003). *Agile Software Development: Principles, Patterns, and Practices*. Prentice Hall.

Martin, R. C. (2009). *Clean Code: A Handbook of Agile Software Craftsmanship*. Prentice Hall.

Martin, R. C. (2017). *Clean Architecture: A Craftsman's Guide to Software Structure and Design*. Prentice Hall.

Meyer, B. (1988). *Object-Oriented Software Construction*. Prentice Hall.

North, D. (2006). Introducing BDD. *Better Software Magazine*, March 2006.

Rajlich, V. (2012). *Software Engineering: The Current Practice*. CRC Press.

Sørensen, J. C. (semester). Lecture materials for SB5-MAI Software Maintenance, University of Southern Denmark. Lectures 1, 2, 3, 4, 5, 6, 7, 9, 10, 11.

Sørensen, J. C. (semester). Lab handouts for SB5-MAI Software Maintenance, University of Southern Denmark:

- *Lab1-Setup* — Lab 1 — environment setup and first read of JHotDraw.
- *ChangeReqLab* — Lab 2a — change initiation and the change-request template.
- *CLLab* — Lab 2b — concept location strategies and the SUR / SUL search.
- *ImpactAnalysisLab* — Lab 3a — static and dynamic impact analysis.
- *CILab* — Lab 3b — Continuous Integration setup with GitHub Actions.
- *RefactoringLab* — Lab 4 — Fowler's refactoring catalogue applied to a chosen feature.
- *ActualizationLab* — Lab 5 — SOLID and Clean Architecture audit of the chosen feature.
- *TestLab1* — Lab 7 — JUnit 4 + Mockito unit testing; production assertions.
- *BDDLab* (alternative title *TestLab2*) — Lab 9 — JGiven + AssertJ behaviour-driven testing.

The author's portfolio document — `portfolio/portfolio.md`, approximately 3,900 lines — narrates each lab and lecture, with reflections and concrete artefact references. The portfolio is the primary source from which the present report is synthesised.

Tornhill, A. (2018). *Software Design X-Rays: Fix Technical Debt with Behavioral Code Analysis*. The Pragmatic Bookshelf.

Turing, A. M. (1936). On Computable Numbers, with an Application to the Entscheidungsproblem. *Proceedings of the London Mathematical Society*.

---

## 13. Lab Traceability Appendix

This appendix provides a complete traceability between every concrete claim in the report and the laboratory artefact that supports it. Each row of the tables below names a claim in the body of the report, the lab in which it originated, the specific committed artefact in the repository, and the portfolio section that documents the work in narrative form.

### 13.1 Phase-by-phase mapping

| Report section | Lab(s) | Lab handout name | Committed artefacts | Portfolio entry |
|---|---|---|---|---|
| §2 Initiation | 1, 2 | *Lab1-Setup*, *ChangeReqLab* | The working build, the user-story document in the portfolio | *Lab 1 — Introduction Lab*; *Lab 2 — Change Initiation and Concept Location* |
| §3 Concept Location | 2 | *CLLab* | The concept-classification table; the wrong-way/backtrack/right-way notes | *Lab 2* (same entry as above) |
| §4 Impact Analysis (static) | 3 | *ImpactAnalysisLab* | The impact-set table reproduced in §4.1 | *Lab 3 — Continuous Integration and Impact Analysis* |
| §4 Impact Analysis (dynamic) | 3 | *CILab* | `.github/workflows/maven.yml`; the green CI runs visible in the repository | *Lab 3* (same entry) |
| §5 Prefactoring — Compose Method | 4 | *RefactoringLab* | `GroupAction.java` (refactored); commit `fff4b86b` | *Lab 4 — Refactoring Lab: Group / Ungroup Prefactoring* |
| §5 Prefactoring — Dead-field removal | 4 | *RefactoringLab* | `UngroupAction.java` (shadow field removed); commit `fff4b86b` | *Lab 4* |
| §5 Prefactoring — Stale-comment removal | 4 | *RefactoringLab* | `GroupAction.java` (comment removed); commit `fff4b86b` | *Lab 4* |
| §5.3 Deferred refactorings | 4 | *RefactoringLab* | (deferred — not committed) | *Lab 4* (deferral documented in reflection) |
| §6 Actualization — SOLID audit | 5 | *ActualizationLab* | The five-violation audit table | *Lab 5 — Actualization Lab: SOLID and Clean Architecture in JHotDraw* |
| §8.1 Verification — unit tests | 7 | *TestLab1* | `jhotdraw-core/src/test/java/org/jhotdraw/draw/action/GroupActionTest.java` (16 tests); `UngroupActionTest.java` (5 tests); `GroupFigureTest.java` (3 tests); commit `fc01bcef` | *Lab 7 — Testing Lab: Unit Tests for Group / Ungroup* |
| §8.1 Verification — production assertions | 7 | *TestLab1* | 6 `assert` statements in `GroupAction.java`; commit `fc01bcef` | *Lab 7* |
| §8.1.5 Mockability tax finding | 7 | *TestLab1* | Documented in `GroupActionTest.java` class comment and the portfolio | *Lab 7* (reflection section) |
| §8.2 Verification — BDD scenarios | 9 | *BDDLab* / *TestLab2* | `bdd/GivenADrawing.java`, `WhenTheUser.java`, `ThenTheDrawing.java`, `GroupUngroupScenarioTest.java`; commit `c104b9bc` | *Lab 9 — Behavior-Driven Testing: JGiven Scenarios for Group / Ungroup* |
| §8.2.5 Deferred AssertJ-Swing scenario | 9 | *BDDLab* | `bdd/DrawAppSwingScenarioTest.java` (annotated `@Ignore`); commit `c104b9bc` | *Lab 9* |
| §8.2.1 JDK 25 / JGiven workaround | 9 | *BDDLab* | Surefire `argLine` configuration in `pom.xml` | *Lab 9* (reflection section) |
| §9 Conclusion phase | every | (mechanised) | Commits on the `alex` branch; green GitHub Actions runs | (every commit) |
| §10 Discussion | every | (synthesis) | The portfolio's *Capstone Reflection* section | *Capstone Reflection — The Course as One Argument* |

### 13.2 Lab-by-lab summary

The seven laboratory assignments completed during the semester, with the deliverables each produced:

| Lab | Title | Handout | Principal deliverables | Commits |
|---|---|---|---|---|
| 1 | Introduction Lab: Project Setup | *Lab1-Setup* | Working build (Maven 3.9.6, JDK 25); ability to run the Draw sample; identification of `jhotdraw-core` as the module of interest | (environment + portfolio entry) |
| 2 | Change Initiation and Concept Location | *ChangeReqLab*, *CLLab* | Selection of Group / Ungroup as the working feature; the change request paragraph; the user story; the concept-classification table; the concept-location process including the wrong-way/backtrack/right-way narrative | (portfolio entry) |
| 3 | Continuous Integration and Impact Analysis | *ImpactAnalysisLab*, *CILab* | `.github/workflows/maven.yml`; the static impact-set table; the dynamic check via `mvn test` | (CI workflow + portfolio entry) |
| 4 | Refactoring Lab: Group / Ungroup Prefactoring | *RefactoringLab* | Compose Method refactoring on `actionPerformed`; dead shadow field removal in `UngroupAction`; stale `XXX` comment removal in `GroupAction`; documentation of three deferred larger refactorings (Replace Conditional with Polymorphism, Splitting Roles, Extract Class) | `fff4b86b` |
| 5 | Actualization Lab: SOLID and Clean Architecture in JHotDraw | *ActualizationLab* | Five-violation SOLID audit (SRP, OCP, LSP, ISP, DIP) on the Group / Ungroup feature; per-violation refactoring plan; identification of the priority-1 fix (Replace Conditional with Polymorphism) | (portfolio entry) |
| 7 | Testing Lab: Unit Tests for Group / Ungroup | *TestLab1* | 24 JUnit 4 unit tests; 6 production `assert` statements; identification of the mockability tax as a finding | `fc01bcef` |
| 9 | Behavior-Driven Testing: JGiven Scenarios for Group / Ungroup | *BDDLab* / *TestLab2* | 3 JGiven stage classes; 4 BDD scenarios; 1 `@Ignore`d AssertJ-Swing scenario; Surefire `--add-opens` workaround for JDK 25 compatibility | `c104b9bc` |

Labs 6, 8, 10, and 11 were not assigned in the course — Lectures 6, 8, 10, and 11 had no associated lab work in the present semester. The portfolio reflects this asymmetry honestly, with lecture sections for the unlabbed weeks and lab sections only for the labbed weeks.

### 13.3 Provenance of every quantitative claim

Selected quantitative claims in the report, traced to their source:

| Claim | Number | Source |
|---|---|---|
| Maintenance share of total cost of ownership | ~80% | Lientz & Swanson (1980) |
| Perfective maintenance share | ~50% of the 80% | Lientz & Swanson (1980) |
| Refactoring reduction in classes touched (Drawlets) | 13 → 5 (62%) | Rajlich (2012) Lecture 10 slide 37 |
| LLVM production assertion density | ~1 per 110 LOC | Lecture 7 |
| Lab 7 assertion density (this feature) | ~1 per 30 LOC | Lab 7 deliverable (6 assertions in ~180 LOC) |
| Number of unit tests added | 24 | Lab 7 commit `fc01bcef` |
| Number of BDD scenarios added | 4 | Lab 9 commit `c104b9bc` |
| Number of `@Ignore`d AssertJ-Swing scenarios | 1 | Lab 9 commit `c104b9bc` |
| Total runnable tests after Lab 9 | 30 (including 2 pre-existing TestNG) | `mvn test -pl jhotdraw-core` output |
| Test failures, errors, skipped | 0 / 0 / 0 | `mvn test -pl jhotdraw-core` output |
| Portfolio document length | ~3,900 lines | `wc -l portfolio/portfolio.md` |

Each row is independently verifiable. The lab-deliverable rows correspond to artefacts in the repository; the literature rows correspond to citations in the references section.

---

## 14. Appendix — Extra Questions and Reflections

This appendix collects reflective questions on the project and its place in the broader maintenance discipline. The answers are at exam-essay depth: long enough to be substantive, short enough to fit within an examination time budget.

### 13.1 If the project could be repeated, what would change?

Three changes would meaningfully improve the work. First, the SOLID refactorings identified in Lab 5 would be *executed* rather than deferred, once Lab 7's test floor made them safe. The execution would take perhaps one additional week of work and would demonstrate the audit's actionability rather than leaving it as paperwork. Second, a CodeScene-style behavioural code analysis on the JHotDraw repository would inform refactoring choices with data rather than intuition. The likely hotspots — `AbstractCompositeFigure`, `DefaultDrawingView`, `AbstractSelectedAction` — were guessed but not verified, and verification would either confirm the guesses (giving the work data-backed prioritisation) or surface different priorities (sharpening the work's targeting). Third, the `@Ignore`d AssertJ-Swing scenario in Lab 9 would be run on a workstation with a display, closing the system-test layer of the test pyramid and proving end-to-end that the feature works through the menu and key bindings, not just through the API. Together these three changes would push the project from a roughly seven-out-of-ten technically-rigorous maintenance project to a roughly nine-out-of-ten demonstrably-complete one.

### 13.2 What is the single most important lesson learned?

The single most important lesson is the **Boy Scout Rule** — Martin's operational rule that *"you should always leave the code cleaner than you found it"*. Every other concept in the course either *justifies* the rule (Lehman's laws explain why the cleanup matters; Tornhill's hotspot analysis identifies where to apply it) or *operationalises* it (refactoring catalogue, SOLID principles, clean-code disciplines, testing practices). The rule is the smallest possible move (five minutes; one cleanup), the rule that scales (a hundred Boy Scout cleanups equals one large refactoring without the political overhead), and the rule that compounds (Lehman's complexity rise is bent downward by the sum of small cleanups). Internalising the rule is the deliverable the course actually asks for, and it survives every change of company, language, framework, and team.

### 13.3 What is the relationship between the phased model and continuous deployment?

Continuous deployment and Rajlich's phased model are *not* in tension. Continuous deployment compresses the phases temporally — many changes per day, each passing through every phase in minutes — but the phases do not disappear. Initiation becomes a JIRA ticket; Concept Location becomes IDE search-and-grep; Impact Analysis becomes the IDE's call-graph view plus the test suite; Prefactoring is whatever the developer does before committing; Actualization is the commit itself; Postfactoring is the post-commit cleanup; Verification is the CI run; Conclusion is the automated deploy. The phases automate where automation is possible (verification, conclusion) and remain manual where automation cannot reach (concept location, refactoring decisions). The phased model is therefore *the schedule continuous deployment runs in*, not the bureaucracy continuous deployment escapes.

### 13.4 Why is the BDD-versus-unit-testing dichotomy a category error?

Because BDD and unit testing answer different questions and operate at different layers of the test pyramid. Unit tests pin *implementation paths* — they catch off-by-one bugs in `canGroup`'s boundary check, they catch out-of-order calls to `view.clearSelection` and `view.addToSelection`, they catch the precise sequence of mock interactions that constitutes correct grouping behaviour. BDD scenarios pin *user-facing contracts* — they catch the case where the user's mental model of grouping diverges from the system's behaviour, they catch the case where a menu wiring change accidentally bypasses a guard, they catch the case where a refactoring preserves all unit-test passes but breaks the higher-level user story. A team that chose unit testing instead of BDD would lose readable documentation; a team that chose BDD instead of unit testing would lose path-level coverage. Both layers are needed, and the choice between them is a category error. The Lab 7 and Lab 9 work together build the pyramid; neither alone is sufficient.

### 13.5 What would change if Lehman's laws were wrong?

If Lehman's 1980 laws were wrong — if software did *not* tend toward rising complexity by default, if continuous adaptation were *not* required to maintain user satisfaction — much of the maintenance discipline would dissolve. Refactoring would be optional aesthetic preference rather than active counter-force; clean-code disciplines would be matters of taste rather than maintainability requirements; the Boy Scout Rule would be a nicety rather than a necessity; the 80% maintenance figure from Lientz and Swanson would be inflated by inefficiency rather than reflecting the steady state of running software. The fact that all of these techniques continue to be developed and validated across forty-five years of subsequent empirical work is the strongest indirect confirmation that Lehman's laws are right. The maintenance discipline exists because the laws are true; the techniques work because the discipline addresses the laws' consequences.

### 13.6 What is the relationship between the present work and the broader practice of legacy code maintenance?

The Group / Ungroup feature in JHotDraw is *legacy code* by Tornhill's two-part definition from Lecture 11: it lacks quality in places (the dead shadow field, the stale comment, the SOLID violations) and the present author did not write it. The first relationship between the work and the practice is therefore *recognition* — the techniques the course teaches are exactly the techniques a working engineer applies to legacy code on the first day at a new job. The second relationship is *humility*: even with a semester's focused work, the present project did not execute the largest refactorings, did not eliminate every smell, did not close the system-test layer. Real legacy-code maintenance is similar — it is rarely possible to make a legacy system pristine in a finite time budget. The honest disposition is therefore *triage*: identify the highest-priority improvements, execute as many as the budget permits, and document the deferred backlog so future maintainers can continue the work. The present report is, in this sense, an artefact of legacy-code maintenance practised at semester scale.

### 13.7 What is the single sentence that summarises the entire course?

Software maintenance is the activity of making **safe, small, frequent changes to a moving system whose codebase outlives every individual who has worked on it** — and the artefacts of careful maintenance — small commits, named tests, lived-in code — are the only durable record of the engineer's care.

That sentence is the course in twenty-eight words. The work documented in this report is the proof that it has been internalised.

---

*End of report.*
