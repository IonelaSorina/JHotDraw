# Essay 03 — Software Testing Applied to the Group / Ungroup Feature

> Template: a 15-20 page Maintenance Report focused on **software testing** — unit tests, production assertions, mocks/stubs/spies, the testing pyramid, and the theoretical limits of testing — applied to the Group / Ungroup feature in JHotDraw.

---

## Header

**Full Name:** Alex Baduca
**Student Exam Number:** [INSERT]
**Student Email:** baducualexandrudaniel@gmail.com
**Course Number:** SB5-MAI Software Maintenance
**Number of Pages:** [page numbers in top-right]
**Name of Lecturer:** Jan Corfixen Sørensen
**Lecturer Email:** [INSERT]

---

## Abstract

This report investigates **software testing** as a maintenance discipline, applying the techniques of Beck (1999), Martin (2009), and the lecture's *Testing — How to Make Software Fail* deck (SB5-MAI Lec 7) to the *Group / Ungroup* feature of JHotDraw. After locating the feature's classes and analysing its impact set, the report documents the implementation of **24 JUnit 4 unit tests** and **6 production assertions** added in Lab 7, covering best-case, boundary, and failure-mode paths through `GroupAction`, `UngroupAction`, and `GroupFigure`. The work surfaces three findings: (1) a *mockability tax* — `Object.getClass()` is final and cannot be stubbed by Mockito, forcing tests to use real concrete figure classes; (2) the production assertion density of the feature (~1 per 30 LOC) is *six times* the LLVM benchmark (~1 per 110 LOC), but the project as a whole is far below; (3) testing's theoretical limits (Turing's halting problem, Dijkstra's dictum) mean that even 24 tests do not prove correctness — they demonstrate the *absence of 24 specific bugs* at a specific moment. The report concludes that tests are an *asset*, not a deliverable: they make refactoring safe, document expected behaviour, and constitute the only durable proof that a change is correct.

---

## 1. Introduction

In 1972, Edsger Dijkstra delivered the foundational dictum of software testing: *"Testing can demonstrate the presence of bugs, but not their absence."* The statement is not a counsel of despair but a precondition for *humble* testing — the kind that actually catches bugs. This report studies testing through a single concrete instance: the Lab 7 work that added 24 unit tests and 6 production assertions to the Group / Ungroup feature of JHotDraw. The report begins with the theoretical limits of testing, proceeds through the practical mechanics of JUnit + Mockito, and ends with a critical evaluation of what the test suite does *not* prove.

### 1.1 What is JHotDraw?

JHotDraw is a Java drawing framework (v9.1-SNAPSHOT, LGPL 2.1) — a multi-module Maven project providing a Swing-based 2D drawing canvas. The relevant module for this report is `jhotdraw-core`, which contains the action and figure layers. Before Lab 7, `jhotdraw-core` had exactly two test files — both TestNG-based, both testing `AbstractFigure` rather than the Group / Ungroup feature. The lab's work was therefore not just *adding* tests but *establishing a test floor* where none existed.

### 1.2 The selected feature: Group / Ungroup

The Group / Ungroup feature is implemented in:

- `GroupAction` — the Swing Action; ~180 LOC; uses `isGroupingAction` boolean to dispatch.
- `UngroupAction` — subclass; ~40 LOC; flips the boolean.
- `GroupFigure` — composite figure holding grouped children; ~30 LOC.

The feature was selected for testing because (1) it has identifiable user-facing behaviour suitable for both unit and BDD tests, (2) it spans concrete classes (`GroupAction`) and abstractions (`Drawing`, `DrawingView`) that exercise the mock vs real-object distinction, and (3) it has *boundary cases* (empty selection, single-figure selection, no active view) that boundary-case testing techniques (Lec 7) are well-suited to.

---

## 2. Initiation

### 2.1 Testing in Rajlich's phased model

Verification — the testing column — runs along the right-hand side of Rajlich's phased model from Prefactoring through Conclusion. Every phase that *modifies* code has a corresponding verification step. In the present work, the Lab 7 verification is *retroactive* — tests were added to code that already existed, rather than as part of TDD. This is the normal mode for legacy codebases (Lec 7 distinguishes *Moving to TDD* from *Real TDD*; both differ from *retroactive* testing of pre-existing code).

### 2.2 User story

> *As a JHotDraw maintainer, I want the Group / Ungroup feature to have a comprehensive unit-test suite so that future refactoring (the Lab 4 deferred refactorings and beyond) is safe.*

The user story acknowledges that the tests serve maintenance, not user functionality. Tests are *infrastructure for future change*, not features themselves.

### 2.3 Team pipeline

Lab 7 added JUnit 4.13.2 and Mockito 4.11.0 as Maven test-scope dependencies in `jhotdraw-core/pom.xml`. The CI workflow (Lab 3, `.github/workflows/maven.yml`) was unchanged — `mvn test` already ran the test suite; the new tests simply joined it.

[INSERT SCREENSHOT: GitHub Actions run showing the test count increased]

---

## 3. Concept Location

### 3.1 What needs testing?

The concept-location for testing is *which methods exhibit user-visible behaviour*. The audit identified seven such methods on `GroupAction`:

1. `canGroup()` — guards the *Group* menu item.
2. `canUngroup()` — guards the *Ungroup* menu item.
3. `groupFigures(view, group, figures)` — performs grouping.
4. `ungroupFigures(view, group)` — performs ungrouping.
5. `actionPerformed(event)` — Swing dispatch entry point.
6. `updateEnabledState()` — keeps the action's enabled state consistent with selection.
7. (UngroupAction's constructor + dispatch.)

And one method on `GroupFigure`:

8. `isTransformable()` — pure collective predicate (group is transformable iff all children are).

### 3.2 Domain class / responsibility table for tests

| Test Class | Methods Covered | Test Count |
|---|---|---:|
| `GroupActionTest` | canGroup, canUngroup, groupFigures, ungroupFigures, actionPerformed | 16 |
| `UngroupActionTest` | constructor + dispatch + class-identity | 5 |
| `GroupFigureTest` | isTransformable | 3 |
| (existing) `AbstractFigureNGTest` | AbstractFigure helper methods | 2 |
| **Total** | | **26** |

(Lab 9 added a further 4 BDD scenarios for 30 total.)

---

## 4. Impact Analysis

### 4.1 Static impact analysis

Adding tests does not modify production code — the static impact set is the *test code only*. The new files:

- `jhotdraw-core/src/test/java/org/jhotdraw/draw/action/GroupActionTest.java` (16 tests)
- `jhotdraw-core/src/test/java/org/jhotdraw/draw/action/UngroupActionTest.java` (5 tests)
- `jhotdraw-core/src/test/java/org/jhotdraw/draw/figure/GroupFigureTest.java` (3 tests)

Plus modifications to `jhotdraw-core/pom.xml` (adding JUnit + Mockito dependencies) and to `jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java` (adding 6 production assertions).

[INSERT UML: class diagram of test classes with their dependencies on the SUT classes]

### 4.2 Dynamic impact analysis

After Lab 7, `mvn test -pl jhotdraw-core` reports `Tests run: 26, Failures: 0, Errors: 0, Skipped: 0`. The CI workflow's green check is the dynamic confirmation that the tests build, run, and pass.

[INSERT SCREENSHOT: `mvn test` output showing 26 tests green]

### 4.3 Package list

| Package | # New Files | # New LOC |
|---|---:|---:|
| `org.jhotdraw.draw.action` (test) | 2 | ~350 |
| `org.jhotdraw.draw.figure` (test) | 1 | ~80 |
| `org.jhotdraw.draw.action` (main, assertions) | 0 new files; 6 new lines | 6 |
| `pom.xml` | 0 new files; ~10 new lines | 10 |

---

## 5. The testing theory — what the tests cannot prove

### 5.1 Turing's halting problem

Alan Turing (1936) proved that *no general program decides whether another program halts*. By Rice's theorem (1953), this generalises: *no general program decides any non-trivial semantic property of programs*, including the property "this code is correct." Testing's incompleteness is therefore not a tooling limitation but a *theorem* — established before electronic computers existed.

### 5.2 Dijkstra's dictum

Edsger Dijkstra (1972) compressed the Turing/Rice result into a single sentence usable by working engineers: *"Testing can demonstrate the presence of bugs, but not their absence."* The dictum is not a counsel of despair; it is a precondition for *honest* testing.

### 5.3 What the 24 tests of Lab 7 do prove

Each test demonstrates the *absence of one specific bug* at the moment the test was run. The 24 tests therefore demonstrate the absence of 24 specific bugs. They demonstrate this *under the test fixtures used* — the bugs are absent when `DrawingView` is mocked, when the prototype is a `GroupFigure`, when the selection has the specific compositions tested. Outside the fixtures, the tests say nothing.

This is the *humble* framing of test-suite confidence: the suite is *a floor under which we can detect breakage*. It is not a ceiling proving correctness.

### 5.4 The "what is going on?" diagnostic tree

When a test fails, Lecture 7's diagnostic tree asks in order:

1. **Bug in the SUT?** — the obvious answer; usually correct.
2. **Bug in the test itself?** — the second most common; especially in newly-added tests.
3. **Bug in the specification?** — the canonical example is the Mars Climate Orbiter (1999): one team's spec was metric, the other's English; both teams' code was correct against their own spec; the spec itself was wrong.
4. **Bug in OS / compiler / libraries / hardware?** — rare but real (cf. Lab 9's JDK 25 + JGiven incompatibility).

Asking these questions in order is the difference between a working test culture and cargo-cult red-green.

---

## 6. The testing implementation — what was built in Lab 7

### 6.1 JUnit 4 + Mockito setup

The Maven dependencies added to `jhotdraw-core/pom.xml`:

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

JUnit 4 over JUnit 5: per the *TestLab1* handout's recommendation that *"Swing and JUnit extensions often work best with JUnit 4."* Mockito 4 over Mockito 5: because the project's source target is Java 1.8 and Mockito 5 requires Java 11+. **Both choices are environment-driven (Brooks's conformity).**

### 6.2 Test fixture pattern

Every test in `GroupActionTest` uses a shared fixture established in `@Before`:

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

[INSERT SCREENSHOT: the setUp method in IDE]

Three mocks (editor, view, drawing) plus one real instance (prototype). The mocks substitute for Swing infrastructure that cannot run headless. The real `GroupFigure` instance is required because **`Object.getClass()` is final and cannot be stubbed** — the *mockability tax*.

### 6.3 The 16 tests in `GroupActionTest`

Organised by method and case:

**canGroup (4 tests):**
- `canGroup_returnsTrue_whenSelectionHasMoreThanOneFigure` — best case.
- `canGroup_returnsFalse_whenSelectionHasExactlyOneFigure` — boundary case.
- `canGroup_returnsFalse_whenSelectionIsEmpty` — boundary case.
- `canGroup_returnsFalse_whenNoActiveView` — failure mode.

**canUngroup (5 tests):**
- `canUngroup_returnsTrue_whenSelectionIsSingleMatchingFigure` — best case.
- `canUngroup_returnsFalse_whenSelectionIsSingleNonMatchingFigure` — failure mode (wrong class).
- `canUngroup_returnsFalse_whenMultipleFiguresSelected` — boundary case.
- `canUngroup_returnsFalse_whenSelectionEmpty` — boundary case.
- `canUngroup_returnsFalse_whenNoActiveView` — failure mode.

**groupFigures / ungroupFigures (3 tests):**
- `groupFigures_clearsSelection_addsGroupAtFirstFigureIndex_andReselectsTheGroup` — best case with `InOrder` verification.
- `ungroupFigures_movesChildrenOutAndRemovesGroup_inOrder` — best case with `InOrder`.
- `ungroupFigures_returnsEmptyCollection_whenGroupHasNoChildren` — boundary case.

**actionPerformed dispatch (3 tests):**
- `actionPerformed_performsGroup_whenIsGroupingActionAndCanGroup`.
- `actionPerformed_doesNothing_whenCannotGroup` — guard verification.
- `actionPerformed_performsUngroup_whenNotGroupingActionAndCanUngroup`.

**Production assertion (1 test):**
- `groupFigures_failsAssertion_whenFiguresCollectionIsEmpty(expected=AssertionError.class)` — verifies the production `assert` fires.

### 6.4 The mockability tax — discovered in Lab 7

The `canUngroup` method in `GroupAction` uses:

```java
getView().getSelectedFigures().iterator().next()
       .getClass().equals(prototype.getClass());
```

Two `getClass()` calls. `Object.getClass()` is **final** in Java. **Mockito cannot stub final methods on regular mocks.** Therefore the test for `canUngroup` *cannot* use a mocked figure — it must use a real `GroupFigure` or `RectangleFigure`. The result: the test code's mocking strategy is *split in two* — most tests use mocks; the class-equality tests use real instances.

This is what Lecture 7 calls the *mockability tax*: code that depends on language-final methods cannot be tested with mocks. The fix is architectural — replace the `getClass()` check with a polymorphic `prototype.matches(figure)` query (Lec 5's DIP, Lec 10's Splitting Roles, Lec 6's *Replace switch on type code with polymorphism*). Three lectures converge on the same one-line refactor. Lab 7 *exhibited* the tax but did not *fix* it — the refactor remains in the deferred backlog.

### 6.5 The 5 tests in `UngroupActionTest`

Tests of the inheritance wiring: `UngroupAction extends GroupAction` with `isGroupingAction=false`. Tests verify the constructor sets the flag correctly and that `canGroup` / `canUngroup` behave inversely to `GroupAction`.

### 6.6 The 3 tests in `GroupFigureTest`

Tests of `isTransformable()`:

- `isTransformable_returnsTrue_forEmptyGroup` — vacuous-truth boundary.
- `isTransformable_returnsTrue_whenAllChildrenTransformable` — best case.
- `isTransformable_returnsFalse_whenAnyChildNotTransformable` — failure case.

Three small tests pin a small collective predicate. The vacuous-truth boundary is included because *"an empty group has no untransformable child"* is the kind of boundary that experienced developers occasionally get wrong.

---

## 7. Production assertions — the under-used tool

### 7.1 Java's `assert` statement

Java has had an `assert` statement since 1.4 (2002): `assert condition : "message";`. The assertion is *disabled by default at runtime*; the JVM flag `-ea` enables it. Surefire enables assertions in tests by default — the assert *will* fire when violated by test code.

### 7.2 The three assertion rules (Lec 7)

- **R1:** Assertions are not for error handling. Use exceptions for *expected* failure modes.
- **R2:** NO SIDE EFFECTS. An assertion that mutates state is *silently disabled* when assertions are off.
- **R3:** No silly assertions. `assert 1+1==2` adds nothing.

### 7.3 Six assertions added in Lab 7

Added to `GroupAction.groupFigures` and `GroupAction.ungroupFigures`:

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

[INSERT SCREENSHOT: the assertions in IDE]

### 7.4 Why these assertions

Each documents an *invariant* that the production code's upstream guards (`canGroup`, `canUngroup`) already enforce. If a future caller bypasses the guards, the assertion catches it — failing *early* and *near* the bug, rather than producing a `NullPointerException` or `IndexOutOfBoundsException` five frames down the stack.

### 7.5 Production assertion density

| Project | Assertions | LOC | Density |
|---|---:|---:|---:|
| GCC | ~9,000 | ~7,000,000 | ~1 per 800 |
| LLVM | ~13,000 | ~1,400,000 | **~1 per 110** |
| JHotDraw (this feature) | 6 | ~180 | 1 per 30 |
| JHotDraw (project) | ~0 elsewhere | hundreds of K | far below LLVM |

Lab 7's density in `GroupAction` is *six times* the LLVM benchmark — denser than necessary for that one method. But for JHotDraw as a whole, the density is essentially zero. **The honest reading is not "Lab 7 over-asserted" but "JHotDraw is starved of assertions, and Lab 7 began filling the gap in one feature."** A Boy-Scout-Rule-style policy of adding one assertion per visit would bring the project to LLVM density over hundreds of commits.

---

## 8. The test pyramid — where Lab 7 sits

Test scopes form a *pyramid*: many fast small tests at the base, fewer slow large tests at the apex.

```
                   /\
                  /  \
                 /    \
                /System\        ← few (1 @Ignored, Lab 9)
               /  Tests \
              /----------\
             /            \
            / Integration  \    ← 4 (Lab 9 BDD scenarios)
           /    Tests       \
          /------------------\
         /                    \
        /     Unit Tests       \  ← 24 (Lab 7)
       /                        \
      /__________________________\
```

[INSERT DIAGRAM: a clean version of the test pyramid]

Each layer answers a different question:
- **Unit:** "is this logic correct?"
- **Integration:** "is this wiring correct?"
- **System:** "does this product work?"

A healthy test suite has *all three*. Lab 7 built the unit layer; Lab 9 built the integration / BDD layer; the system layer remains as Lab 9's `@Ignore`d AssertJ-Swing scenario, which would run on a workstation with a display.

---

## 9. Verification of the verification

### 9.1 The build is green

`mvn test -pl jhotdraw-core` reports `Tests run: 26, Failures: 0, Errors: 0, Skipped: 0` after Lab 7, rising to 30 after Lab 9. The CI workflow runs this on every PR; the green check is the public confirmation.

[INSERT SCREENSHOT: green CI run]

### 9.2 The tests demonstrate boundary coverage

The test catalogue (Section 6.3) explicitly covers best case, boundary case, and failure mode for each method. The TestLab1 handout's classwork item 4 — *"Write JUnit tests for identified boundary cases"* — is fully satisfied.

### 9.3 The tests are F.I.R.S.T.

- **Fast** — the full suite runs in under a second.
- **Independent** — each test creates its own mocks in `@Before`.
- **Repeatable** — no external dependencies; runs deterministically.
- **Self-validating** — JUnit assertions produce boolean pass/fail.
- **Timely** — written immediately after the code under test was identified (retroactive, not TDD, but timely in the sense that they were not deferred indefinitely).

---

## 10. Conclusion

This report has investigated **software testing** through the Lab 7 work of adding 24 JUnit 4 unit tests and 6 production assertions to the JHotDraw Group / Ungroup feature. The work demonstrated four concrete practices:

1. **JUnit + Mockito** as the standard Java unit-testing pair (the same pair Rajlich (2012, slide 27) recommends).
2. **Best / boundary / failure-mode partition** as the testing-coverage discipline (TestLab1 classwork rule 4).
3. **Production assertions** as the invariant-documentation discipline (TestLab1 rule 5; Lec 7's assertion rules).
4. **In-order verification** of method-call sequences (`Mockito.inOrder`) as the way to pin behavioural ordering invariants.

The work also surfaced one architectural finding — the *mockability tax* — that connects this report directly to the SOLID essay (Essay 02): the `getClass()`-based check in `canUngroup` violates DIP and manifests as a testability obstacle. **The architectural failing and the testability failing are the same failing**.

The most important theoretical point is Dijkstra's: 24 tests *do not* prove correctness. They prove the absence of 24 specific bugs under specific fixtures. The suite is a *floor*, not a ceiling. Within that floor, however, the tests do their job — they make refactoring safe, document expected behaviour, and constitute the durable proof that future changes preserve the current contract.

The deepest claim is that **testability is the operational test of good design**. Code that is hard to test is, almost by definition, hard to change. The Lab 7 work *measured* the testability of the Group / Ungroup feature and found it adequate in most places (mocks worked) and inadequate in one (the `getClass()` check). Both findings are useful: the adequate places give confidence; the inadequate places give a refactor target.

---

## 11. Discussion

**What could have been better.** Three concrete improvements. First, the unit tests should be paired with *property-based* tests (jqwik) for the figure-collection operations — random input generators would catch boundary cases the manual partition misses. Second, the production assertion density across the whole `jhotdraw-core` module should be lifted toward LLVM's 1-per-110 benchmark; the current 6-assertion patch in one method is a start, not a finish. Third, the `getClass()` mockability tax should be fixed — the test floor is now in place, so the refactor is safe.

**What failed.** The first run of the test suite revealed that `Object.getClass()` cannot be stubbed by Mockito, forcing a rewrite of the test fixture strategy in mid-Lab. The eventual fix — using real concrete figure classes for class-identity tests — works but splits the test code's mocking discipline in two. Documenting this as the *mockability tax* turned the failure into a finding.

**The deeper concern this raises** is the gap between *test code* and *test quality*. The lab's 24 tests, 6 assertions, and green CI are all measurable. But *whether those tests would catch a real bug introduced by a real refactoring* is not measurable from the test count alone. Mutation testing (e.g., PIT) would close this gap by *deliberately introducing* small mutations and measuring how many tests catch them. The present report does not include mutation testing, but it is the natural next step.

**A final point.** Dijkstra's dictum is most often quoted as a caution against over-confidence in test coverage. It is also a caution against *under-confidence* — the dictum does not say tests are *useless*, it says they prove only the *presence* of bugs. Within that scope, well-designed tests prove a lot. The 24 tests of Lab 7 prove that, as of the commit at which they were written, the Group / Ungroup feature behaves correctly across 24 specific paths. That is not nothing; that is the entire point of having tests.

---

## 12. References & Sources

- Beck, K. (1999). *Extreme Programming Explained: Embrace Change*. Addison-Wesley.
- Beck, K. (2002). *Test-Driven Development by Example*. Addison-Wesley.
- Dijkstra, E. W. (1972). *Notes on Structured Programming* (EWD249). Technische Hogeschool Eindhoven.
- Fowler, M. (1999). *Refactoring: Improving the Design of Existing Code*. Addison-Wesley.
- Martin, R. C. (2009). *Clean Code*. Prentice Hall.
- Rajlich, V. (2012). *Software Engineering: The Current Practice*, Chapter 17. CRC Press.
- Turing, A. (1936). *On Computable Numbers, with an Application to the Entscheidungsproblem*. Proceedings of the London Mathematical Society.
- Mockito documentation. https://site.mockito.org.
- SB5-MAI Software Maintenance Course (Sørensen, J. C.). Lectures 6, 7, 10. University of Southern Denmark.

---

## 13. Appendix — Extra Questions and Reflections

### A.1 If you could only add three tests to JHotDraw, which would they be?

(1) `canGroup_returnsFalse_whenSelectionHasExactlyOneFigure` — the boundary case that catches off-by-one (`>` vs `>=`) bugs in the selection-count guard. (2) `groupFigures_clearsSelection_addsGroupAtFirstFigureIndex_andReselectsTheGroup` — the *ordering* test using `InOrder`, because the ordering invariant (select-after-changed) is critical for undo correctness. (3) `ungroupFigures_returnsEmptyCollection_whenGroupHasNoChildren` — the empty-group boundary, because nobody intuitively thinks "ungroup an empty group" but the menu can be triggered in that state.

### A.2 How does the mockability tax connect to the SOLID essay?

The `getClass()` check in `canUngroup` violates DIP (depends on concrete class identity, not on an abstraction). The DIP violation *manifests* as a testability obstacle. Fixing DIP (polymorphic `prototype.matches()` query) fixes the testability simultaneously. **This is the strongest single piece of evidence in the entire portfolio that SOLID is operational, not aesthetic.**

### A.3 Why was TDD not used in Lab 7?

Because the code already existed. TDD writes the failing test first; Lab 7 retroactively added tests to existing code. The mode is *legacy testing*, not TDD. The TDD discipline would apply if a new feature were being added — Lab 9's BDD scenarios are closer to TDD because they were written *before* the test-only stage classes that would support them.

### A.4 What would mutation testing reveal?

Mutation testing (PIT, MutMut) would introduce small mutations into the SUT — flip a `>` to `>=`, change `&&` to `||`, etc. — and measure how many tests catch the mutation. A test suite that catches *every* mutation is *mutation-adequate*. The Lab 7 suite has not been run through mutation testing. A reasonable prediction is *moderate adequacy* — the boundary cases catch the off-by-one mutations, but the *combination* mutations (multiple flips at once) likely escape.

### A.5 What is the relationship between assertions and tests?

Tests check behaviour from *outside*; assertions check invariants from *inside*. Tests catch bugs at known input points; assertions catch bugs at *every* execution point. They are complementary: tests verify the *external contract*; assertions verify the *internal consistency*. A bug introduced into production code is caught by a test only if a test exercises that path; the assertion catches it on every invocation.

### A.6 Could BDD scenarios (Lab 9) replace unit tests (Lab 7)?

No — they answer different questions. Unit tests pin *logic*; BDD scenarios pin *user-facing contract*. A scenario that says "given 2 selected figures, when I group, then one group is created" doesn't tell you which line of `groupFigures` is correct — only that the *outcome* is correct. Unit tests tell you *how* the code is correct; BDD tells you *what* the code does. Both layers are needed; neither alone is sufficient.

### A.7 Should production code ship with `-ea` on?

It depends on the cost of *failing* versus the cost of *continuing with potential corruption*. For mission-critical code (avionics, the landing stage of a Mars probe), the cost of failing may exceed the cost of continuing — disable assertions. For ordinary application code, *failing early* is almost always safer — failing produces a stack trace and a known broken state; continuing produces a *silently* broken state. The Lab 7 assertions would be reasonable to ship with `-ea` on, but JHotDraw's deployment is left to the integrator.

---

*End of essay. Word count: ~5,400. Estimated pages at 11pt with 1.15 line spacing: 17–19.*
