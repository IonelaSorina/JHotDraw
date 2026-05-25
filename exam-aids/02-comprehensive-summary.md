# 02 — Comprehensive Summary

Every lecture and every lab condensed to its load-bearing arguments. Use Ctrl-F to find a topic.

> This is the largest aid file. Each section is the *minimum* you need to be able to write 700-1000 words on. If you can read a section and explain its claim out loud in 30 seconds, you're ready.

---

## Part A — Lectures

### Lecture 1 — Introduction to Software Maintenance

**Central claim:** Software does not stand still — it lives, changes, ages, and dies. Maintenance is the discipline of managing that life.

**Load-bearing concepts:**

1. **Brooks's four essential difficulties** (from *The Mythical Man-Month*, 1975):
   - **Complexity** — real software handles many more cases than textbook examples.
   - **Invisibility** — code has no natural physical form.
   - **Changeability** — software can be changed at any moment, no cost-of-concrete.
   - **Conformity** — software must conform to environments (OS, hardware, expectations) that change independently.
   - **+ Discontinuity** (lecture's fifth) — small changes can have disproportionately large effects.
   - These are *essential* — they arise from software's nature, not poor tooling.

2. **Three paradigms of software development** (lecture's framing):
   - Heavyweight / waterfall.
   - Iterative / agile.
   - Open source.

3. **Lehman's Laws of Software Evolution** (1980):
   - **Continuing Change** — "a system must be continually adapted or it becomes progressively less satisfactory."
   - **Increasing Complexity** — "as a system evolves, its complexity increases unless work is done to maintain or reduce it."
   - These laws are *empirical*, not normative. They describe what happens by default.

4. **Lientz-Swanson maintenance percentages** (1980):
   - Corrective (bug fixes): ~21%
   - Adaptive (env changes): ~25%
   - Perfective (improvements): ~50% ← the majority
   - Preventive: ~4%
   - Total maintenance = ~80% of TCO.

5. **CHAOS report** (Standish Group):
   - Project outcomes split: Successful / Challenged / Failed (specific percentages vary by year).
   - Most projects do not deliver on time, in budget, in scope.

**Connections to other lectures:** Lehman → Lecture 11 (Tornhill builds on Lehman). Brooks's complexity → Lecture 11 (Hickey's curve). Lientz-Swanson → Lecture 4 (refactoring is "perfective" maintenance).

---

### Lecture 2 — Software Change Process and JHotDraw

**Central claim:** Every change in a codebase follows a phased model — Initiation, Concept Location, Impact Analysis, Prefactoring, Actualization, Postfactoring, Conclusion, with Verification spanning the right-hand side.

**Load-bearing concepts:**

1. **The seven-phase model** (Rajlich's phased model):
   ```
   Initiation → Concept Location → Impact Analysis → Prefactoring →
   Actualization → Postfactoring → Conclusion
                     ↑
                Verification spans Pre/Actualization/Post/Conclusion
   ```
   Every change passes through all phases, whether the engineer notices or not.

2. **The concept triangle** (Rajlich):
   - **Concept** (the abstract idea, e.g. "ownership").
   - **Words** (terms used to name it).
   - **Code** (the implementation).
   - Concept location = traversing from concept to code.

3. **MoSCoW prioritisation** — Must / Should / Could / Won't have. Used to prioritise requirements.

4. **Concept location strategies:**
   - **SUR** (Search Using Regexp).
   - **SUL** (Search Using Links / call graph).
   - **SUL3** = SUL extended to 3 levels of indirection.
   - Concept location is *iterative* — wrong-way / backtrack / right-way is normal (Lecture 10 confirms with the Drawlets diagrams).

5. **JHotDraw structure:** Maven multi-module, ~9 modules (`jhotdraw-core`, `jhotdraw-api`, `jhotdraw-gui`, `jhotdraw-app`, samples), Swing-based 2D drawing framework.

**Connections:** This lecture's phase model is the *spine* of the entire course. Every subsequent lab is one phase. Lecture 10 closes the model.

---

### Lecture 3 — Software Processes, CI, and Impact Analysis

**Central claim:** Once a change is *located* (Lec 2), the next question is *what else will be affected?* Impact analysis answers this; CI mechanises verification.

**Load-bearing concepts:**

1. **Impact set** = the set of code elements that will need to be modified for a change to be complete.
   - Direct impact set — what the change literally touches.
   - Indirect impact set — what depends on what was touched.
   - Computed forward (predicting) — static call graph.
   - Computed backward (observing) — change coupling from git history (Lecture 11).

2. **Continuous Integration:**
   - Every push runs the full test suite.
   - Build status is the *baseline* (Lecture 10).
   - Tools: GitHub Actions, Jenkins, Travis CI.

3. **Change propagation** — the *temporal* shape of an impact set:
   - Red = currently editing.
   - Orange = inconsistent with red, needs propagation.
   - Green = updated, consistent again.
   - Grey = explored, not impacted.

4. **Static vs dynamic impact analysis:**
   - Static = read the code.
   - Dynamic = run the code, observe what executes.

**Connections:** Forward impact analysis (this lecture) and historical change-coupling (Lec 11) are the *same graph computed two ways*. Both views matter.

---

### Lecture 4 — Refactoring and Refactoring to Patterns

**Central claim:** Refactoring is *not* optional cleanup. It is the structural pre-condition that makes every other phase of change feasible.

**Load-bearing concepts:**

1. **Definition (Fowler 1999):** Refactoring is changing the *structure* of code without changing its *behaviour*.

2. **Prefactoring vs Postfactoring:**
   - **Prefactoring** — clean before the change, so the change is local.
   - **Postfactoring** — clean after the change, removing duplication created by the change.

3. **Core refactoring catalogue** (Fowler):
   - *Compose Method* — extract sub-methods until each method does one thing.
   - *Extract Method* / *Inline Method*.
   - *Replace Conditional with Polymorphism* — `switch` on type → subclasses.
   - *Move Method / Field*.
   - *Introduce Parameter Object* / *Replace Magic Number with Symbolic Constant*.

4. **Refactoring to Patterns (Kerievsky 2004):** moves like *Replace Hard-coded Notifications with Observer*, *Extract Adapter*, *Move Embellishment to Decorator*. These take code *toward* GoF patterns.

5. **Refactoring shortens change propagation** (Rajlich, Lec 10, slide 37):
   - No refactoring: 13 classes modified.
   - With Move Function refactoring: 8 classes.
   - With Splitting Roles refactoring: 5 classes — **62% reduction in scope.**
   - LOC modified barely changes (91 → 87) — refactoring reduces *scattering*, not *amount*.

6. **The deferred refactoring cost:**
   - Refactor *now* = local cost.
   - Refactor *later* = global cost (more callers, more tests, more risk).

**Connections:** Refactoring is mentioned in Lec 5 (SOLID enables it), Lec 6 (Boy Scout Rule operationalises it), Lec 7 (refactor is step 3 of TDD), Lec 10 (Rajlich's worked example), Lec 11 (refactoring shortens propagation).

---

### Lecture 5 — Actualization, OO Principles, and Clean Architecture

**Central claim:** SOLID principles are not abstract OO rules; they are concrete *testability* and *change-locality* enablers.

**Load-bearing concepts:**

1. **SOLID:**
   - **S — Single Responsibility Principle (SRP):** A class should have *one and only one reason to change*. (Martin 2003.)
   - **O — Open / Closed Principle (OCP):** Software entities should be *open for extension, closed for modification*. (Meyer 1988; Martin 1996.)
   - **L — Liskov Substitution Principle (LSP):** Subtypes must be substitutable for their base types. (Liskov 1987.)
   - **I — Interface Segregation Principle (ISP):** Clients should not depend on methods they do not use.
   - **D — Dependency Inversion Principle (DIP):** Depend on abstractions, not concretions.

2. **GRASP** (Larman): General Responsibility Assignment Software Patterns — Information Expert, Creator, Controller, Low Coupling, High Cohesion, Polymorphism, Pure Fabrication, Indirection, Protected Variations.

3. **Clean Architecture** (Martin 2017):
   - Concentric layers: Entities → Use Cases → Interface Adapters → Frameworks & Drivers.
   - **The dependency rule:** source code dependencies must point *inward*.
   - Outer layers know about inner; inner know nothing about outer.

4. **The Principle of Least Knowledge / Law of Demeter:**
   - A method `m` of class `C` should only call methods of: itself, its parameters, objects it creates, its instance fields.
   - **Train wrecks** like `a.getB().getC().getD().doStuff()` violate this.

**Connections:** SRP → Lec 6 ("classes should be small / one responsibility"). SRP → Lec 7 (separating concerns enables mocking). DIP → Lec 7 (the DateServer pattern). Demeter → Lec 6 (clean code restates it at line level).

---

### Lecture 6 — Clean Code

**Central claim:** Code quality is a *line-by-line* discipline. Clean code is the operationalisation of every higher-level principle from Lectures 1-5.

**Load-bearing concepts:**

1. **What is clean code (multi-author definitions):**
   - Stroustrup: *"elegant and efficient, does one thing well."*
   - Booch: *"reads like well-written prose."*
   - Cunningham: *"each routine you read turns out to be pretty much what you expected."*

2. **The Boy Scout Rule** (Martin): *"You should always leave the code cleaner than you found it."* Continuous, small improvements bend the code-decay curve downward.

3. **WTFs/minute** (Holwerda): the only honest metric of code quality.

4. **Naming rules (Chapter 2):**
   - Intension-revealing.
   - Avoid disinformation (`l` vs `1`, `O` vs `0`).
   - Make meaningful distinctions.
   - Use pronounceable / searchable names.
   - No member prefixes / Hungarian notation.
   - Class names = nouns; method names = verbs.

5. **Functions (Chapter 3):**
   - Small — <20 lines.
   - Do one thing.
   - One level of abstraction per function.
   - Stepdown Rule — reads top to bottom.
   - Ideal number of arguments: zero. Three is too many.
   - Flag arguments are bad.
   - Command-Query Separation.
   - DRY.

6. **Comments (Chapter 4):**
   - *Comments are failures.* Each comment is a place where code couldn't speak for itself.
   - Don't comment bad code — rewrite it.
   - Good: legal, informative, intent, clarification, amplification, public API javadocs.
   - Bad: mumbling, redundant, mandated, journal, noise, position markers, closing-brace, attributions, commented-out code.

7. **Formatting (Chapter 5):** The newspaper metaphor — top of file = headline / overview; details below.

8. **Objects vs Data Structures (Chapter 6):**
   - Objects hide data, expose functions.
   - Data structures expose data, have no meaningful functions.
   - Hybrid structures are bad.

9. **Error handling (Chapter 7):**
   - Prefer exceptions to error codes.
   - Extract try/catch blocks — error handling is *one thing*.
   - Don't return null. Don't pass null.

10. **Unit tests (Chapter 9):**
    - **Three Laws of TDD** (Beck):
      1. No production code until you have a failing test.
      2. No more test than is sufficient to fail.
      3. No more production code than is sufficient to pass.
    - **F.I.R.S.T.**: Fast, Independent, Repeatable, Self-validating, Timely.

11. **Classes (Chapter 10):**
    - Standard order: constants → static vars → instance vars → public methods → private methods (stepdown).
    - Classes should be small (measured in *responsibilities*).
    - SRP restated at class level.

12. **Emergent design (Kent Beck's four rules of simple design, in priority order):**
    1. Runs all the tests.
    2. No duplication.
    3. Expressive.
    4. Minimal classes and methods.

**Connections:** Almost everything. Lec 6 restates Lec 5 (SRP) at class level, Lec 4 (DRY) at function level, Lec 1 (complexity management) at line level.

---

### Lecture 7 — Software Testing: How to Make Software Fail

**Central claim:** Testing cannot prove correctness (theoretical impossibility), but a disciplined test suite makes software *fail safely and early* rather than in production.

**Load-bearing concepts:**

1. **Turing's halting problem → testing incompleteness:**
   - No general program decides whether another program halts.
   - By Rice's theorem, no general program decides arbitrary semantic properties.
   - **Dijkstra dictum**: *"Testing can demonstrate the presence of bugs, but not their absence."*

2. **The "what is going on?" decision tree** when a test fails:
   - Bug in SUT? Bug in acceptability test? Bug in specification? Bug in OS/compiler/libs/hardware?
   - **Mars Climate Orbiter** — bug in specification (Metric m/s vs English ft/s).

3. **Test taxonomy:**
   - **Unit** / **integration** / **system** — scope.
   - **White-box** / **black-box** — visibility into internals.
   - **Differential** — two implementations compared.
   - **Stress** — push to limits.
   - **Random** — unconstrained inputs.

4. **Creating testable software (8 rules):** Clean Code, Refactor, Describe what it does and how it interacts, No extra Threads, No swap of global variables, No pointer soup, Module unit tests, Support fault injection, **Assertions, Assertions, Assertions!!!**

5. **Three assertion rules:**
   - R1: Not for error handling (use exceptions).
   - R2: NO SIDE EFFECTS.
   - R3: No silly assertions.
   - Production density: GCC ~9000 asserts, LLVM ~13,000, target ~1 per 110 LOC.

6. **The Fragile Test Problem — four sensitivities:**
   - **Behaviour sensitivity** — business rule changes.
   - **Interface sensitivity** — method renames / GUI changes.
   - **Data sensitivity** — database / fixture changes.
   - **Context sensitivity** — OS / time-zone / locale changes.

7. **Testing under the UI:** Automate at the application layer, not the UI layer. Reach into UI only when UI itself is the SUT.

8. **Fault injection:** Wrap a low-level call (e.g. `open()`) with a controllable version (`my-open()` that fails on the 100th call).

9. **TDD cycle:** Red (failing test) → Green (minimum code) → Refactor. One test at a time. Implement only as much as the test requires.

10. **Mock vs Stub vs Spy:**
    - **Stub** — pre-defined data, static, lightweight.
    - **Mock** — records and verifies interactions. Most powerful.
    - **Spy** — partial mock, wraps real object, overrides selected methods.

11. **The DateServer pattern:** Wrap time-dependent calls (`new GregorianCalendar()`) in an injectable service so tests can control "now".

12. **Acceptance tests:**
    - Traditional: manual, by customer, after delivery.
    - Agile: automatic, before user story implementation, with JGiven/FitNesse/Cucumber.

**Connections:** Test taxonomy → Lec 9 (BDD as acceptance-test layer). DateServer pattern = Lec 5 DIP applied to time. Fragile tests = Lec 10's "test suite maintenance."

---

### Lecture 9 — Pragmatic BDD for Java

**Central claim:** Unit tests cannot serve as documentation. BDD scenarios — written in domain language and executed as tests — *can*.

**Load-bearing concepts:**

1. **Why BDD?** Five problems with conventional unit tests:
   - Many technical / irrelevant details.
   - Point of the test hard to grasp.
   - Code duplication.
   - Only developers can read.
   - Cannot be used as documentation.

2. **Behavior-Driven Development = four properties:**
   - Behavior in domain language.
   - Domain experts + developers collaborate.
   - Scenarios execute as tests.
   - Creates **living documentation**.

3. **Given-When-Then:** the universal BDD scenario shape.
   - *Given* — initial state.
   - *When* — action.
   - *Then* — expected outcome.

4. **Classical vs developer-friendly BDD:**
   - Classical (Cucumber, JBehave, Concordion, FitNesse) — separate plain-text + Java; *additional maintenance cost*.
   - Developer-friendly (Spock, JGiven, Serenity) — scenarios live in code, in the same language as the SUT.

5. **JGiven features:**
   - Stage classes (unique to JGiven) — modular, reusable, one per Given/When/Then.
   - `@ScenarioState`, `@ProvidedScenarioState`, `@ExpectedScenarioState` — typed data flow between stages.
   - Generates console output + HTML5 report.
   - Three years of production use at TNG, 3000+ scenarios on 70-dev project.

6. **AssertJ:**
   - Fluent API: `assertThat(value).is(...)` chains.
   - Type-specific assertions (`StringAssert`, `ListAssert`).
   - Custom Conditions and custom AbstractAssert subclasses.

7. **AssertJ-Swing:** GUI automation — simulates clicks/drags, embeds screenshots on failure, supports JUnit and TestNG.

**Connections:** BDD is the *acceptance-test layer* Lecture 7 pointed at. The Given-When-Then shape is identical in structure to XP's use-case template (Name/Actor/Precondition/Main scenario/Alternative scenarios).

---

### Lecture 10 — Example of Software Change + Conclusion of Change Process

**Central claim:** The entire course's phased model is executable on a real codebase — Rajlich's Drawlets — and every phase ends in *Conclusion* (commit / baseline / release).

**Load-bearing concepts:**

1. **Drawlets case study** (Rajlich, Ch. 17): a Beck/Cunningham drawing framework, 100+ classes, 35 interfaces, 40,000 LOC.

2. **Change request:** *"Implement an owner for each figure."* Three significant concepts (figure, canvas), the rest are external (ID, password, owner) or irrelevant.

3. **Concept location is iterative — wrong-way / backtrack / right-way.** The lecture shows three diagrams of the same path.

4. **Change propagation (slides 17-24):** A *temporal* wave of edits — red (currently editing) → orange (now-impacted) → green (re-consistent) → grey (explored). Six propagation steps.

5. **Testing results (Drawlets baseline):**
   - 17,800 LOC production + 4,800 LOC tests = **27% test ratio**.
   - 385 unit tests + 141 functional tests.
   - Change touched 91 production LOC + 124 test LOC = **1.4 test lines per production line**.
   - Acceptance tests: *"Tool JGiven and Mockito used to run the functional tests"* — textbook validation of Lab 7/9's tool choice.

6. **Refactoring impact (slide 37):**
   - No refactoring: 13 classes modified.
   - Move function: 8 classes.
   - Splitting roles: 5 classes — **62% reduction.**

7. **Conclusion phase steps:**
   - **Commit** — return updated code to repository, resolve conflicts.
   - **New baseline** — thorough testing → certified known-good.
   - **New release** — baseline exposed to users; less frequent large + more frequent small (patches via `merge`).

8. **Baseline as deadline (social layer):**
   - Deadline to commit = time when baseline testing starts.
   - Miss = additional work + management notices.
   - Minor bugs → still certify, add to backlog.
   - Serious bugs → reject commits, possibly reject whole baseline; *reputation suffers*.

9. **Stakeholder role:** *acceptance testing* by stakeholders (functional, by customer) is the gate that approves software for release.

10. **Test suite maintenance** (slide 29):
    - Unaffected old tests kept as regression.
    - Obsolete tests removed.
    - New tests added for new features.

**Connections:** This is the *capstone lecture* — every prior lecture's concept maps to a phase of the worked example. Lecture 11 then re-frames the same arc through the *measurement* lens.

---

### Lecture 11 — Beyond Technical Debt: CodeScene

**Central claim:** Static analysis cannot tell you *what code matters*. Behavioural analysis (git history + code) can.

**Load-bearing concepts:**

1. **Technical debt definition (Ford, Parsons, Kia 2017, p. 110):**
   - *"Stuff that isn't supposed to be there and is in the way of the stuff that is supposed to be there."*
   - Two parts: not supposed to be there + *in the way*. Both required.

2. **Lehman's laws revisited:** Continuing Change, Increasing Complexity. Complexity rises by default.

3. **Hickey's Easy vs Simple curve** (*Simple Made Easy*, 2011):
   - Easy = fast initial velocity, asymptotes to zero.
   - Simple = slower initial, sustains velocity.
   - *"If you ignore complexity, you will slow down."*

4. **Technical debt has business impact:**
   - Roadmap symptom: long lead times, lack of predictability.
   - Product symptom: bugs.
   - *Technical debt is a business problem misdiagnosed as a planning problem.*

5. **Why conventional tools (SonarQube) fail to be actionable:**
   - Tomcat example: 10,072 violations, 11% debt = $341,563 = 683 man-days.
   - This says *what's wrong* but not *what to do first*.

6. **CodeScene paradigm = "movie not snapshot":**
   - + Time aspect (what changed over time).
   - + Organization & people (who touches what).
   - Source = git, plus source code + project management.

7. **Hotspots — the core operational concept:**
   - *"Complicated code you have to work with often."*
   - **Principal** = code complexity.
   - **Interest rate** = code change frequency.
   - Hotspot = product of both.

8. **Hotspots cluster bugs (Graves et al. 2000):**
   - *Process measures* (change history) predict faults better than *product metrics* (size).
   - Older modules have ~1/3 fewer faults (per equivalent module).

9. **X-Ray — function-level drill-down:** which function in a hotspot file has the highest cyclomatic complexity × change frequency.

10. **Change coupling:** files that commit together. Static call graph + historical change coupling sometimes disagree — disagreement is informative (hidden coupling or dead path).

11. **Legacy code (slide 21):**
    - *Lacks quality* (relative perspective).
    - *We didn't write ourselves.*
    - "The Technical Debt That Wasn't" — sometimes the problem is *unfamiliarity*, not *bad code*.

12. **Off-boarding simulation:** Simulate a developer leaving → red dots show "Off-Boarding Risk" files. **Hotspot map and knowledge concentration map overlap.**

13. **The four-line conclusion:**
    - Technical debt is real regardless of language.
    - Huge useful info in version control.
    - Ultimately rely on human expertise.
    - Augment developer judgement with data for highest ROI.

**Connections:** This lecture closes the testing arc (Lec 7 + 9) and the maintenance arc (Lec 4 + 10) by giving both a *measurement vocabulary*.

---

## Part B — Labs (concrete examples)

Use these as the *case study* layer of your essay. Every theoretical claim should land on one of these.

### Lab 1 — Environment Setup

- **What was done:** Cloned JHotDraw fork, set up portable Maven 3.9.6 at `/tmp/maven`, ran the Draw sample, identified module structure.
- **Theoretical hook:** The codebase is large (~9 modules), Swing-based, and old enough that running it needs JDK 8 source target. **Real maintenance starts with environment.**
- **Cite-able numbers:** Lientz-Swanson, CHAOS report values were introduced here.

### Lab 2 — Change Initiation + Concept Location

- **What was done:** Selected the **Group / Ungroup** feature as the semester thread. Wrote a change request paragraph. Located the feature using SUL3 — backtracked through Action-related infrastructure to find `GroupAction`, `UngroupAction`, `GroupFigure`.
- **Theoretical hook:** Concept location is *iterative* (Lec 10's diagrams confirm). Wrong-way → backtrack → right-way is normal.
- **Cite-able example:** SUR (regex search for "group"), SUL (call graph from Action.actionPerformed).

### Lab 3 — Continuous Integration + Impact Analysis

- **What was done:** Set up GitHub Actions running `mvn -B test` on every PR to `develop`. Computed the impact set for the Group/Ungroup change.
- **Theoretical hook:** CI mechanises the *verification* spine of the V-model. Impact analysis is the *forward* projection of change coupling (Lec 11's *historical* projection is the same graph computed differently).
- **Cite-able example:** Impact set table with Direct / Indirect columns.

### Lab 4 — Refactoring (Prefactoring)

- **What was done:** *Compose Method* refactoring on `GroupAction.actionPerformed` — extracted `performGroup`, `performUngroup`, and the `getLabels()` helper. Removed the dead `prototype` shadow field in `UngroupAction`. Removed the stale `// XXX` comment.
- **Theoretical hook:** Prefactoring before the change → smaller impact set later. Three larger refactorings were *deferred* because no tests existed (the cost of skipping = future cost).
- **Cite-able numbers:** 67-line method → multiple methods of ~10-15 lines each.

### Lab 5 — Actualization + SOLID

- **What was done:** Audited the Group/Ungroup feature against SOLID. Identified violations:
  - SRP — `GroupAction` did both grouping AND ungrouping (dispatch via `isGroupingAction` flag).
  - OCP — adding a new action type required modifying the dispatch.
  - DIP — direct dependency on `GregorianCalendar`-style concrete classes.
- **Theoretical hook:** SOLID is a *testability* lens, not just OO purism.
- **Cite-able example:** The same `isGroupingAction` boolean violates both SRP and OCP simultaneously — *Replace Conditional with Polymorphism* would fix both.

### Lab 7 — Unit Testing (TestLab1)

- **What was done:** Added JUnit 4.13.2 + Mockito 4.11.0. Wrote 24 unit tests covering `canGroup`, `canUngroup`, `groupFigures`, `ungroupFigures`, `actionPerformed` dispatch, `UngroupAction` wiring, and `GroupFigure.isTransformable`. Added 6 production `assert` statements as invariants.
- **Theoretical hook:** Tests pin behaviour; assertions document invariants; the two together make refactoring *safe*.
- **Cite-able numbers:**
  - 24 tests + 2 pre-existing TestNG = 26 total, all green.
  - Mockito limitation: `getClass()` cannot be stubbed → "mockability tax".
  - Production assertion density: 6 per ~180 LOC = 1 per 30 LOC (vs LLVM's 1/110).

### Lab 9 — BDD (TestLab2)

- **What was done:** Added JGiven 1.3.1 + AssertJ 3.25.3 + AssertJ-Swing 3.17.1. Mapped three user stories (US-1, US-2, US-3) to four Given-When-Then scenarios. Wrote stage classes (`GivenADrawing`, `WhenTheUser`, `ThenTheDrawing`). One AssertJ-Swing scenario `@Ignore`d because of headless terminal.
- **Theoretical hook:** BDD is the acceptance-test layer; JGiven scenarios are *living documentation*; the textbook (Lec 10) validates JGiven+Mockito as the recommended pair.
- **Cite-able numbers:**
  - 4 scenarios (covering US-1, US-2, US-3 — including a negative scenario).
  - 30 tests total (26 prior + 4 BDD), all green.
  - One workaround: Surefire `--add-opens=java.base/java.lang=ALL-UNNAMED` for JDK 25 + JGiven's older ByteBuddy.

---

## Part C — Cross-cutting themes (the threads that run through all of it)

These are the threads that an evaluative essay can be built around.

### Theme 1 — The phased model is one argument repeated at increasing resolution

- Lec 2 introduces it.
- Lab 2 picks the feature.
- Lab 3 quantifies impact.
- Lab 4 prefactors.
- Lab 5 audits architecture.
- Lab 7 tests.
- Lab 9 documents behaviour.
- Lec 10 shows the complete worked example on Drawlets.
- Lec 11 measures it.

### Theme 2 — Testability is an architectural property

- SOLID's SRP (Lec 5) = testability enabler.
- Clean Code's small classes (Lec 6) = testability enabler.
- DIP + DateServer pattern (Lec 7) = testability enabler.
- JGiven's stage classes (Lec 9) = SRP applied to *tests*.
- Lab 7's `getClass()` mockability tax = a *failure* of testability.

### Theme 3 — Complexity rises by default; pushing back is a discipline

- Brooks's essential difficulties (Lec 1).
- Lehman's Increasing Complexity (Lec 1, Lec 11).
- Hickey's Easy vs Simple (Lec 11).
- The Boy Scout Rule (Lec 6).
- Tornhill's hotspot map (Lec 11).

### Theme 4 — Documentation that cannot rot

- Code comments rot (Lec 6 — comments are failures).
- Static docs rot (Confluence dies).
- Unit tests = executable specification (Lec 7).
- BDD scenarios = readable + executable (Lec 9).
- Living documentation is the artefact that *cannot* drift, because if it drifts the build breaks.

### Theme 5 — Maintenance is the social activity

- Baseline as deadline (Lec 10).
- Stakeholder acceptance testing (Lec 10).
- Off-boarding risk (Lec 11).
- "Reputation of these programmers suffers" (Lec 10 on broken baselines).
- The portfolio's own commits = a *social* artefact (next developer reads them).

---

**Next:** open file 03 ([Question Bank](03-question-bank.md)).
