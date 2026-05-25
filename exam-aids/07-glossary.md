# 07 — Glossary

Every term used in the course, defined in one or two sentences. Sorted alphabetically.

> **Use rule:** when a term appears in the exam question and your brain blanks, search here. Paste the definition into your essay verbatim or rephrase as needed.

---

## A

**Acceptance test.** A test that verifies the system meets a *user-visible* requirement; defined with the user, often automated in Agile (Lec 7, Lec 9, Lec 10).

**Acceptance testing.** The phase in which stakeholders verify functional requirements; gates the *new release* in Rajlich's Conclusion phase (Lec 10).

**Actualization.** Phase 5 of Rajlich's model: the actual code modification that implements the change request (Lec 2).

**Adaptive maintenance.** Modifications to keep software working in a changing environment (new OS version, new library). One of Lientz-Swanson's four categories.

**Anti-pattern.** A common solution that looks reasonable but creates more problems than it solves (e.g., God Class, Shotgun Surgery).

**API (Application Programming Interface).** The public contract of a module — the set of methods other code may call.

**Assertion.** An executable check for a property that must be true (an *invariant*). In Java: `assert condition : "message";` (Lec 7).

**AssertJ.** A fluent Java assertion library; `assertThat(value).is(...).and(...)` chains (Lec 9).

**AssertJ-Swing.** A GUI test automation library for Swing applications; simulates clicks, drags, menu navigation (Lec 9).

---

## B

**Backtracking.** Step in concept location where the developer recognises a wrong path and retreats. Rajlich (Lec 10) emphasises this is normal, not a failure mode.

**Baseline.** The current known-good state of the repository, certified by passing the test suite (Lec 10).

**Baseline testing.** Thorough testing performed before declaring a new baseline; "often done overnight or over the weekend" (Lec 10).

**Behaviour-Driven Development (BDD).** Testing methodology where scenarios are written in domain language (Given-When-Then), executable as tests, and serve as living documentation (Lec 9).

**Black-box testing.** Testing that only sees the API, not the internals. Typically integration / system tests.

**Boy Scout Rule.** *"Always leave the code cleaner than you found it"* — Martin's operational rule for continuous, small-cost cleanups (Lec 6).

**ByteBuddy.** A runtime code-generation library used by Mockito and JGiven for creating dynamic proxies. The cause of Lab 9's JDK 25 incompatibility.

---

## C

**Change coupling.** Files that historically commit together in git. Tornhill's behavioural counterpart to the static call-graph impact set (Lec 11).

**Change propagation.** The *temporal* shape of an impact set — a wave of inconsistencies spreading outward from each edit until the system is consistent again (Lec 10).

**Change request.** A textual description of what should change; the input to Rajlich's *Initiation* phase.

**Clean Architecture.** Martin's (2017) concentric-layers architectural style with the *Dependency Rule*: source code dependencies point inward.

**Clean code.** Code that is readable, focused, expressive, minimal, and meets the reader's expectations (Lec 6, multi-author definition).

**Code coverage.** Percentage of code executed by tests. *Not* a guarantee of correctness — Dijkstra's bug-existence rule still applies.

**Code smell.** A surface symptom (long method, duplicate code, large class) that suggests a deeper structural problem.

**CodeScene.** Adam Tornhill's behavioural code analysis tool — uses git history + source code to find hotspots, change coupling, and off-boarding risk (Lec 11).

**Command-Query Separation (CQS).** A function should *either* do something *or* answer something, not both (Lec 6).

**Commit.** First step of Rajlich's Conclusion phase: returning updated code to the repository, resolving conflicts (Lec 10).

**Compose Method (refactoring).** Extract methods until each method does one thing at one level of abstraction (Fowler).

**Concept.** An idea in the user's domain language that the code must implement (e.g., "ownership", "group of figures").

**Concept location.** Phase 2 of Rajlich's model: finding where in the code a concept is implemented. Often iterative with backtracking (Lec 2).

**Conformity.** Brooks's essential difficulty: software must conform to environments (OS, hardware, expectations) that change independently.

**Continuous Integration (CI).** Mechanised verification — every commit triggers the full test suite. The technical implementation of Rajlich's baseline mechanism (Lec 3, Lec 10).

**Corrective maintenance.** Bug fixes. Lientz-Swanson's smallest category (~21%).

**Coupling.** The degree to which two modules depend on each other. *Low coupling* is good. Compare with *cohesion*.

**Cohesion.** The degree to which a module's elements belong together. *High cohesion* is good.

**Cyclomatic complexity.** A metric counting linearly independent paths through a function. Used in hotspot analysis.

---

## D

**Decorator (pattern).** Wraps an object to add behaviour transparently. One of the GoF patterns.

**Dependency Inversion Principle (DIP).** Depend on abstractions, not concretions. The "D" in SOLID (Lec 5).

**Dependency injection.** Passing a dependency in (via constructor / setter / argument) instead of creating it internally. The mechanism that makes DIP testable.

**Dependency Rule (Clean Architecture).** Source code dependencies must point inward. Outer layers know about inner; inner know nothing about outer (Lec 5).

**Differential testing.** Compare two implementations on the same input; flag discrepancies (Lec 7).

**Dijkstra's dictum.** *"Testing can demonstrate the presence of bugs, but not their absence"* (1972).

**Discontinuity.** Lec 1's addition to Brooks's essential difficulties: small changes can have disproportionate effects.

**DRY (Don't Repeat Yourself).** Each piece of knowledge should have a single, unambiguous representation in the code (Hunt & Thomas; reinforced by Martin and Beck).

---

## E

**Easy vs Simple (Hickey).** "Easy" code has fast initial velocity but decays; "Simple" code starts slower but sustains velocity (Lec 11).

**Encapsulation.** Hiding implementation details behind an interface. One of OOP's four pillars.

**Essential difficulties (Brooks).** Properties intrinsic to software — Complexity, Invisibility, Changeability, Conformity. No methodology eliminates them.

**Evolutionary architecture.** Architecture that supports continuous change. Source of the Ford/Parsons/Kia technical debt definition.

**Extract Method (refactoring).** Take a code fragment and move it into a new method with a meaningful name (Fowler).

**Extreme Programming (XP).** Beck's agile methodology; source of the Three Laws of TDD, pair programming, the four-rules-of-simple-design.

---

## F

**Fault injection.** Replace a low-level call with a wrapper that can fail on demand; used to test recovery paths (Lec 7).

**Feature.** A user-visible capability of the system. The author's selected *feature* is Group/Ungroup.

**F.I.R.S.T.** Fast, Independent, Repeatable, Self-validating, Timely. The five properties of a clean test (Lec 6, Martin).

**Flag argument.** A boolean argument that selects between two different behaviours; violates "do one thing" (Lec 6).

**Fragile Test Problem.** Tests can break for wrong reasons. Lec 7's four-sensitivity taxonomy: Behaviour, Interface, Data, Context.

---

## G

**Gang of Four (GoF).** Gamma, Helm, Johnson, Vlissides. Authors of *Design Patterns* (1994).

**Given-When-Then.** The universal BDD scenario shape: initial state, action, expected outcome (Lec 9).

**GRASP.** General Responsibility Assignment Software Patterns (Larman). Includes Information Expert, Creator, Controller, etc.

---

## H

**Halting problem.** Turing's 1936 result that no general program decides whether another program halts. The theoretical foundation of testing incompleteness (Lec 7).

**Hotspot.** A complex code element that is also frequently changed; the product of code complexity (principal) and change frequency (interest). High-priority refactor target (Lec 11).

---

## I

**Impact analysis.** Phase 3 of Rajlich's model: determining what code will require modification for a change to be complete (Lec 3).

**Impact set.** The set of code elements that will need modification. Direct (literally touched) + Indirect (depends on what was touched).

**Increasing Complexity (Lehman's 2nd law).** *"As a system evolves, its complexity increases unless work is done to maintain or reduce it."*

**Initiation.** Phase 1 of Rajlich's model: change request is received and scoped (Lec 2).

**Integration test.** A test that exercises two or more modules together (Lec 7).

**Interface Segregation Principle (ISP).** Clients should not depend on methods they don't use. The "I" in SOLID (Lec 5).

**Invariant.** A property that must hold throughout the execution of a code path. Documented in code via assertions (Lec 7).

**Invisibility.** Brooks's essential difficulty: code has no natural physical form.

---

## J

**JGiven.** A Java BDD framework with typed stage classes; developer-friendly (scenarios live in code) (Lec 9).

**JHotDraw.** The codebase under study — a Swing-based 2D drawing framework, fork at version 9.1-SNAPSHOT.

**JUnit.** The de-facto Java unit-testing framework, originally by Beck and Gamma (Lec 6, Lec 7).

---

## L

**Law of Demeter.** A method should only call methods of: itself, its parameters, objects it creates, and its instance fields. *Train wrecks* (`a.b().c().d()`) violate this (Lec 5).

**Legacy code.** Lec 11's two definitions: (1) code that lacks quality (relative); (2) code we didn't write ourselves.

**Lehman's Laws.** Manny Lehman's empirical observations about software evolution. The two most-cited are Continuing Change and Increasing Complexity (Lec 1, Lec 11).

**Liskov Substitution Principle (LSP).** Subtypes must be substitutable for their base types. The "L" in SOLID.

**Living documentation.** Documentation that the build refuses to let go stale — the central property of BDD (Lec 9).

---

## M

**Maintenance.** All work on a software system after initial delivery — corrective, adaptive, perfective, preventive (Lientz & Swanson 1980).

**Mars Climate Orbiter.** 1999 NASA loss caused by units mismatch (Metric m/s vs English ft/s); Lec 7's example of *bug in the specification*.

**Mock.** A test double that *records and verifies* method calls. The most powerful and most heavyweight kind of test double (Lec 7).

**Mockito.** A Java mock object framework. Used in Lab 7 and Lab 9.

**MoSCoW.** Must / Should / Could / Won't. Requirements prioritisation framework (Lec 2).

---

## N

**No Silver Bullet.** Brooks's 1986 essay arguing that no single technique will produce an order-of-magnitude productivity improvement in software engineering — because the essential difficulties are not amenable to elimination.

---

## O

**Object-Oriented Programming (OOP).** Programming paradigm organised around objects (data + behaviour together). JHotDraw is a paradigm OOP system.

**Off-boarding.** A developer leaving the team. Tornhill's CodeScene simulates the knowledge loss (Lec 11).

**Open / Closed Principle (OCP).** Software entities should be open for extension, closed for modification. The "O" in SOLID (Meyer 1988, Martin).

**Oracle.** In testing, the mechanism that decides whether the SUT's output is correct.

---

## P

**Perfective maintenance.** Improvements (new features, performance, UX). Largest of Lientz-Swanson's categories (~50%).

**Phase model (Rajlich).** Initiation → Concept Location → Impact Analysis → Prefactoring → Actualization → Postfactoring → Conclusion, with Verification spanning the right-hand side (Lec 2).

**Polymorphism.** OOP's mechanism for one interface to be implemented by many types. The fix for `switch`-on-type-code (Lec 4, Lec 6).

**Postfactoring.** Phase 6 of Rajlich's model: cleaning up duplication introduced by the change (Lec 2, Lec 4).

**Prefactoring.** Phase 4 of Rajlich's model: cleaning the affected code *before* the change, so the change is local (Lec 2, Lec 4).

**Preventive maintenance.** Refactoring to prevent future problems. Smallest Lientz-Swanson category (~4%).

**Principal × Interest = Hotspot.** Tornhill's formula. Principal = complexity; Interest = change frequency (Lec 11).

**Production code.** Code that ships to users (as opposed to test code).

---

## Q

**Quality assurance (QA).** The team / function responsible for verifying software meets its requirements before release. In agile / TDD, QA may be absorbed into the development workflow.

---

## R

**Random testing.** Test with unconstrained inputs to find bugs the developer didn't anticipate (Lec 7).

**Red / Green / Refactor.** The TDD cycle: write a failing test (red), make it pass (green), clean up (refactor) (Lec 7).

**Refactoring.** Changing the structure of code without changing its behaviour (Fowler 1999).

**Regression test.** A test that catches the re-introduction of a previously fixed bug or the loss of a previously working feature.

**Replace Conditional with Polymorphism (refactoring).** Replace a `switch` on a type code with subclasses; one of Fowler's most-cited moves.

**Repository.** (1) The git repository. (2) The DDD pattern abstracting persistence.

**Rice's theorem.** Extension of Turing: no general program decides any non-trivial semantic property of a program. Mentioned implicitly in Lec 7.

---

## S

**Scenario (BDD).** A Given-When-Then narrative describing a user-visible behaviour (Lec 9).

**Shotgun surgery (anti-pattern).** A single conceptual change requires edits in many places. Caused by missing abstraction; high change coupling (Lec 11).

**Single Responsibility Principle (SRP).** A class should have one and only one reason to change. The "S" in SOLID.

**Software Under Test (SUT).** The component being tested in a particular test (Lec 7).

**SOLID.** Single-responsibility, Open-closed, Liskov substitution, Interface segregation, Dependency inversion. Five OO design principles (Martin).

**SonarQube.** A static code analysis tool. Critiqued in Lec 11 as producing non-actionable data.

**Specification.** The intended behaviour of the system, often distinct from the implementation. Lec 7's "what is going on?" tree includes "bug in specification" as a category.

**Spy.** A *partial* mock — wraps a real object and overrides selected methods. The middle ground between stub and mock (Lec 7).

**Static analysis.** Analysis performed on source code without running it. SonarQube is a static analyser.

**Stress testing.** Test with workloads beyond normal capacity to find breaking points (Lec 7).

**Stub.** A test double that returns pre-defined data. The lightest, most static kind of test double (Lec 7).

**Sub-typing.** OOP's mechanism for one type to be substitutable for another. See *Liskov*.

**SUL / SUR.** Search Using Links (call graph) / Search Using Regexp (text search). Concept location strategies (Lec 2).

**System test.** A test of the whole assembled system (Lec 7).

---

## T

**Technical debt.** *"Stuff that isn't supposed to be there and is in the way"* (Ford, Parsons & Kia 2017, p. 110). Originally Cunningham's 1992 metaphor (Lec 11).

**Test code.** Code whose purpose is to test other code.

**Test double.** A generic term for stub / mock / spy / fake — any object substituted for a real dependency in a test (Lec 7).

**Test-Driven Development (TDD).** Write the failing test, then the minimum code to pass, then refactor. Beck's discipline (Lec 7).

**Test pyramid.** Conceptual diagram: many unit tests (broad base), fewer integration tests (middle), few system tests (apex).

**Test suite.** The collection of all tests for a system. Maintained over time per Lec 10's rules: keep unaffected as regression, remove obsolete, add new.

**Three Laws of TDD.** Beck's strict ordering: no production code without failing test; no more test than is sufficient to fail; no more code than is sufficient to pass (Lec 6, Lec 7).

**Train wreck.** A chained method call like `a.b().c().d()` that violates the Law of Demeter (Lec 5, Lec 6).

**Turing's halting problem.** The 1936 result that no general program decides whether another program halts. The theoretical limit on testing (Lec 7).

---

## U

**Unit test.** A test of one module / class in isolation, with dependencies mocked (Lec 7).

**User story.** A short description of a user-visible capability in the form *"As a [role], I want [goal] so that [benefit]"*. BDD scenarios are derived from user stories (Lec 9).

---

## V

**Verification.** The right-hand spine of Rajlich's V-shaped phase diagram; spans Prefactoring → Conclusion.

**Version control.** Git (or equivalent). Source of behavioural code analysis data (Lec 11).

---

## W

**White-box testing.** Testing that uses knowledge of internal structure. Typically unit tests.

**WTFs/minute.** Holwerda's humorous-but-serious metric of code quality: count the times a reviewer audibly reacts to surprising code (Lec 6).

---

## X

**XP (Extreme Programming).** See *Extreme Programming*.

**X-Ray (CodeScene).** Function-level drill-down inside a hotspot file. Surfaces the most-changed, most-complex functions (Lec 11).

---

**Quick acronym dictionary:**

| Acronym | Expansion |
|---|---|
| BDD | Behaviour-Driven Development |
| CHAOS | The Standish Group's report on software project outcomes |
| CI | Continuous Integration |
| CQS | Command-Query Separation |
| DI | Dependency Injection |
| DIP | Dependency Inversion Principle |
| DRY | Don't Repeat Yourself |
| F.I.R.S.T. | Fast, Independent, Repeatable, Self-validating, Timely |
| GoF | Gang of Four |
| GRASP | General Responsibility Assignment Software Patterns |
| ISP | Interface Segregation Principle |
| JPMS | Java Platform Module System |
| LOC | Lines of Code |
| LSP | Liskov Substitution Principle |
| MoSCoW | Must / Should / Could / Won't |
| OCP | Open / Closed Principle |
| OOP | Object-Oriented Programming |
| QA | Quality Assurance |
| ROI | Return on Investment |
| SDI / MDI | Single / Multiple Document Interface |
| SOLID | S+O+L+I+D principles |
| SRP | Single Responsibility Principle |
| SUT | System / Software Under Test |
| TDD | Test-Driven Development |
| TCO | Total Cost of Ownership |
| TOC | Table of Contents |
| XP | eXtreme Programming |

---

**Next:** open file 08 ([Concept Map](08-concept-map.md)).
