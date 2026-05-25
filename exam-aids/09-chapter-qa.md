# 09 — Chapter Q&A

30+ exam-style questions and answers per lecture chapter. Shorter and faster than the essay-style outlines in [03-question-bank.md](03-question-bank.md). Designed for direct recall.

> **How to use:** read these like flashcards. The questions cover definitions, statements of laws, comparisons, applications, and short critical evaluations. If you can answer 80%+ of these without looking, you have the recall layer of the course.

---

## Table of Contents

- [Chapter 1 — Introduction to Software Maintenance](#chapter-1--introduction-to-software-maintenance)
- [Chapter 2 — Software Change Process and JHotDraw](#chapter-2--software-change-process-and-jhotdraw)
- [Chapter 3 — Software Processes, CI, and Impact Analysis](#chapter-3--software-processes-ci-and-impact-analysis)
- [Chapter 4 — Refactoring and Refactoring to Patterns](#chapter-4--refactoring-and-refactoring-to-patterns)
- [Chapter 5 — Actualization, OO Principles, and Clean Architecture](#chapter-5--actualization-oo-principles-and-clean-architecture)
- [Chapter 6 — Clean Code](#chapter-6--clean-code)
- [Chapter 7 — Software Testing](#chapter-7--software-testing)
- [Chapter 9 — Pragmatic BDD for Java](#chapter-9--pragmatic-bdd-for-java)
- [Chapter 10 — Example of Software Change + Conclusion](#chapter-10--example-of-software-change--conclusion)
- [Chapter 11 — Beyond Technical Debt: CodeScene](#chapter-11--beyond-technical-debt-codescene)

---

## Chapter 1 — Introduction to Software Maintenance

### Q1.1 — Define software maintenance.
Software maintenance is all work performed on a software system *after initial delivery* — bug fixes, feature additions, environment adaptations, and refactorings. Lientz & Swanson (1980) established that maintenance accounts for approximately 80% of a system's total cost of ownership, making it the *dominant* engineering activity rather than a marginal one.

### Q1.2 — Who identified the four essential difficulties of software?
**Fred Brooks**, in *The Mythical Man-Month* (1975) and *No Silver Bullet — Essence and Accidents of Software Engineering* (1986). Brooks distinguished *essential* difficulties (intrinsic to software's nature) from *accidental* difficulties (artefacts of poor tooling).

### Q1.3 — What are the four essential difficulties Brooks identified?
**Complexity, Invisibility, Changeability, and Conformity.** The lecture adds a fifth — **Discontinuity** — to reflect modern systems' propagation behaviour.

### Q1.4 — Distinguish essential from accidental difficulties.
*Essential* difficulties arise from software's *nature* and cannot be eliminated by tooling — they can only be *managed*. *Accidental* difficulties are artefacts of poor methodology, weak languages, or bad tools, and they *can* be eliminated by better engineering. Brooks's claim is that no single technique will produce an order-of-magnitude productivity gain because essential difficulties remain.

### Q1.5 — What is Complexity (in Brooks's sense)?
Real software handles many more cases than textbook examples. A drawing framework like JHotDraw must support multiple figure types, each with its own handles, undo semantics, and serialisation. The number of *interacting states* grows combinatorially, and no abstraction fully hides this.

### Q1.6 — What is Invisibility?
Software has no natural physical form. Unlike a bridge or circuit, code cannot be photographed or held. The only visualisations available — UML, call graphs, test output — are *partial views* of an invisible artefact, which makes reasoning about whole systems unusually hard.

### Q1.7 — What is Changeability?
Software can be changed at any moment, with no equivalent of pouring new concrete. This makes change *tempting* — there is no physical cost to discourage it — and constant change *compounds complexity*. Brooks's point is that this property is intrinsic to software, not a managerial failing.

### Q1.8 — What is Conformity?
Software must conform to its environment — OS APIs, file formats, hardware drivers, user expectations — and those environments change *independently* of the software. The software is therefore always in catch-up mode against a moving target. Lab 9's JDK 25 / JGiven 1.3.1 incompatibility is a concrete instance.

### Q1.9 — What is Discontinuity (the lecture's fifth difficulty)?
Small changes in code can have disproportionately large and *unpredictable* effects. A one-line change deep in a rendering pipeline may break dozens of features that share that path. The implication: tests are required to catch what reasoning cannot.

### Q1.10 — State Lehman's First Law.
**Continuing Change:** *"A system must be continually adapted or it becomes progressively less satisfactory."* The implication is that a *finished* system is one already in decay; usefulness requires ongoing adaptation.

### Q1.11 — State Lehman's Second Law.
**Increasing Complexity:** *"As a system evolves, its complexity increases unless work is done to maintain or reduce it."* The load-bearing clause is *"unless work is done"* — complexity is the *null hypothesis*, and holding it flat requires deliberate effort.

### Q1.12 — Who proposed Lehman's laws and when?
**Manny Lehman**, in *Programs, Life Cycles, and Laws of Software Evolution* (Proceedings of the IEEE 68(9):1060-1076, September 1980). The laws are *empirical observations* of how real software systems evolve, not normative prescriptions.

### Q1.13 — What are the four Lientz-Swanson maintenance categories?
**Corrective** (bug fixes ~21%), **Adaptive** (environment changes ~25%), **Perfective** (improvements ~50%), and **Preventive** (refactoring ~4%). Perfective dominates — meaning maintenance is mostly *enhancement*, not *repair*.

### Q1.14 — What percentage of a software system's TCO is maintenance?
Approximately **80%**. Lientz & Swanson (1980) established this benchmark, which has been repeatedly confirmed by subsequent studies. The implication: initial development is *one-fifth* of the engineering effort over the system's life.

### Q1.15 — Which Lientz-Swanson category dominates and why?
**Perfective maintenance** (~50%). Users continuously discover new uses for working software and request enhancements; competitors release features that must be matched; the codebase's owners themselves identify opportunities. Perfective work is therefore *value-creating*, not *value-restoring* like corrective work.

### Q1.16 — Define corrective maintenance.
Modifications made to fix discovered defects. Corrective is the *intuitive* form of maintenance ("software broke, fix it") but is empirically a minority of total maintenance effort. Lientz-Swanson place it at ~21%.

### Q1.17 — Define adaptive maintenance.
Modifications to keep software functioning despite changes in its environment — new OS versions, new database engines, new browsers, new compliance regulations. The software's *behaviour* may be unchanged but its surrounding world has moved.

### Q1.18 — Define perfective maintenance.
Modifications that *improve* software in ways that are not corrective — new features, performance enhancements, usability improvements, refactoring for clarity. The largest Lientz-Swanson category, reflecting that most of post-deployment work is *additive*.

### Q1.19 — Define preventive maintenance.
Changes made to *prevent future problems*, typically refactoring that reduces complexity before it causes incidents. Lientz-Swanson record this as ~4% — the smallest category — reflecting that teams chronically under-invest in it relative to its long-term value.

### Q1.20 — What is the CHAOS report?
The Standish Group's recurring industry report measuring software project outcomes against three criteria: on time, in budget, and in scope. Typical findings split projects into ~30% successful, ~50% challenged (delivered but with compromises), and ~20% failed (cancelled or unused).

### Q1.21 — What does the CHAOS report tell us about the field?
That the *majority* of software projects do not deliver on all three criteria. The implication for maintenance is that even *successful* projects often arrive with technical debt baked in, and *challenged* projects arrive needing immediate maintenance to recover lost scope.

### Q1.22 — Name the three software development paradigms discussed in Lecture 1.
**Heavyweight / waterfall**, **iterative / agile**, and **open source**. Each handles change differently — waterfall plans against change, agile embraces it, open source distributes it across volunteers. Maintenance practices differ accordingly.

### Q1.23 — What is meant by "code decay"?
The cumulative degradation of code quality over time as changes accumulate without compensating cleanup. Code decay is the *observable consequence* of Lehman's Second Law going unchecked, and is the *target* of practices like the Boy Scout Rule (Lec 6) and hotspot refactoring (Lec 11).

### Q1.24 — Why does Brooks say no methodology eliminates essential difficulties?
Because *essential* means intrinsic to the entity itself, not artefactual. Methodologies can eliminate accidental difficulties (poor languages, bad tools) but the essential ones — complexity, invisibility, changeability, conformity — derive from what software *is*. Better methodology *manages* them; nothing eliminates them.

### Q1.25 — What is the relationship between Lehman's 2nd law and refactoring?
Lehman's 2nd law says complexity *rises by default*. Refactoring is the *active counter-force* — the deliberate work that prevents the default. Without refactoring (or equivalent cleanup), Lehman's law predicts complexity will accumulate until the system becomes unmaintainable.

### Q1.26 — Why is invisibility a maintenance problem?
Because maintenance requires *understanding* the existing system, and an invisible artefact resists understanding. Engineers must build mental models from incomplete partial views (UML, tests, call graphs), and those mental models drift from reality as the code changes. This is why behavioural code analysis (Lec 11) and BDD (Lec 9) matter — they make pieces of the invisible visible.

### Q1.27 — What does "software does not stand still" mean?
That once deployed, software lives, ages, and dies. Users discover new needs, environments change, bugs are revealed, dependencies become obsolete. The system is in continuous flux. The phrase is the lecture's compact way of stating Lehman's First Law.

### Q1.28 — How does Conformity manifest in real codebases?
JDK upgrades break old library reflection patterns (Lab 9's JGiven 1.3.1 incompatibility). API providers deprecate endpoints. Browser vendors change CSS rendering. Operating systems remove syscalls. The software must update *not because it has changed*, but because the world around it has.

### Q1.29 — Why is preventive maintenance the smallest Lientz-Swanson category in practice?
Because its benefits are *long-term* and *invisible* — preventing problems that did not occur is hard to justify on a quarterly budget. Teams chronically under-invest in preventive work, then over-invest in corrective work when prevented problems eventually arrive. Tornhill's hotspot analysis (Lec 11) is partly an argument for making preventive work *targetable* and therefore justifiable.

### Q1.30 — Why is software maintenance often underestimated in budgeting?
Because (a) initial development is the *visible* artefact, (b) feature launches generate stakeholder attention while ongoing maintenance does not, and (c) Lientz-Swanson's 80% figure is *empirical*, established only by studying real systems over time — most planning happens before that data is available. The result is chronic under-budgeting and the slow accumulation of technical debt.

---

## Chapter 2 — Software Change Process and JHotDraw

### Q2.1 — Who proposed the phased model of software change?
**Václav Rajlich**, in *Software Engineering: The Current Practice* (CRC Press, 2012). The model formalises what every individual change to a codebase passes through, whether the engineer notices or not.

### Q2.2 — Name the seven phases of Rajlich's model in order.
**Initiation → Concept Location → Impact Analysis → Prefactoring → Actualization → Postfactoring → Conclusion.** *Verification* spans the right-hand side of the V from Prefactoring through Conclusion.

### Q2.3 — What happens in the Initiation phase?
A change request is received and scoped. The team determines whether the change is in-scope, who owns it, what its priority is, and what the rough size of the impact will be. Initiation produces a documented change request that anchors the rest of the phases.

### Q2.4 — What is concept location?
The act of traversing from a *concept* (in domain/user language) to the *code* that implements it. Rajlich frames it as walking the **concept triangle** — concept ↔ words ↔ code — and it is typically the most error-prone phase.

### Q2.5 — What is the concept triangle?
Three corners: **Concept** (the abstract idea, e.g. "ownership"), **Words** (terms used in the codebase, e.g. `OwnerIdentity`, `setOwner`), and **Code** (the actual implementation). Concept location moves around this triangle to bridge user-language and code-language.

### Q2.6 — What is SUR in concept location?
**Search Using Regular Expressions** — text-based search for keywords related to the concept. Fast, but suffers from false positives (the word may appear in unrelated contexts) and false negatives (the concept may be expressed with synonyms).

### Q2.7 — What is SUL?
**Search Using Links** — following the call graph from a starting point. More precise than SUR for *structural* concepts but requires a starting node and may miss concepts not invoked through method calls.

### Q2.8 — What is SUL3?
SUL extended to **three levels of indirection** — follow links three hops out. Trades precision for recall. Used when the initial link search returns too few results.

### Q2.9 — Why is concept location iterative?
Because the developer is searching an invisible artefact for an abstract idea, with no oracle for "this is the right place." Wrong paths are normal; backtracking is normal; *eventually finding the right place* is the success criterion, not finding it on the first try. Lecture 10's Drawlets diagrams (wrong way → backtrack → right way) make this explicit.

### Q2.10 — What is MoSCoW prioritisation?
**Must / Should / Could / Won't.** A requirements-prioritisation framework: *Must* = essential for delivery, *Should* = important but not critical, *Could* = nice to have, *Won't* = explicitly out of scope. Used during Initiation to scope the change.

### Q2.11 — Describe JHotDraw's module structure.
JHotDraw is a Maven multi-module project. Core modules include `jhotdraw-api` (interfaces), `jhotdraw-core` (default implementations), `jhotdraw-gui` (Swing components), `jhotdraw-app` (application shell), plus supporting modules (`actions`, `datatransfer`, `utils`, `xml`) and samples (`jhotdraw-samples-misc` etc.).

### Q2.12 — What is JHotDraw's design heritage?
JHotDraw originated as a Smalltalk drawing framework by **Erich Gamma** and others (one of the Gang of Four authors), and was later ported to Java. The framework is widely cited as a *paradigmatic OOP design exemplar* and was the case study used by the Gang of Four to derive multiple design patterns.

### Q2.13 — What were the three concept-classification categories in Lecture 10's Drawlets example?
**Irrelevant** (verbs and connectors with no implementation meaning, e.g. "implement", "allowed"), **External** (input from outside the system, e.g. "ID", "password"), and **Significant** (concepts that must be located in code, e.g. "figure", "canvas").

### Q2.14 — Why does the phase model put concept location *before* impact analysis?
Because impact analysis asks "what else is affected by changing this code?" and that question requires knowing *which code* — i.e., concept location must complete first. Asking "what is impacted" before knowing *what* is being changed is a category error.

### Q2.15 — What does it mean that the phase model is "prescriptive *and* descriptive"?
**Prescriptive:** if you follow the phases in order, your change will be safer and cheaper. **Descriptive:** *every change passes through these phases anyway* — including bad changes, where some phases are skipped or done badly. The model names what happens; following it makes it happen well.

### Q2.16 — In which phase does refactoring appear?
**Twice** — as **Prefactoring** (phase 4, clean the code *before* the change) and as **Postfactoring** (phase 6, clean up duplication *after* the change). Refactoring's twin placement reflects its role as the *enabler* and *cleaner* of the change.

### Q2.17 — What is Actualization?
Phase 5 — the *actual code modification* that implements the change request. By the time Actualization begins, the affected code has been located, the impact set is known, and any prefactoring is complete. Actualization is therefore the *smallest* phase by code volume, despite being the named "change."

### Q2.18 — Why is Actualization smaller than the surrounding phases?
Because most of the *thinking* happens upstream (concept location, impact analysis, prefactoring), and most of the *verification* happens downstream (postfactoring, conclusion). Actualization is *executing the now-known change*, which — if the upstream work was done well — is typically simple.

### Q2.19 — What is Postfactoring?
Phase 6 — cleaning up *duplication or smells* introduced by the change. Postfactoring removes code duplication created during Actualization, consolidates new helpers, and ensures the change didn't leave residue. Distinct from Prefactoring (which cleans *before* the change).

### Q2.20 — What is Verification in the phase model?
The *spine* of the V — the column running down the right side of the phase diagram from Prefactoring to Conclusion. Verification is the testing and validation that ensures the change behaves correctly; it spans multiple phases rather than being a single phase.

### Q2.21 — What is Conclusion in Rajlich's phase model?
Phase 7 — the final phase, comprising commit (return code to the repository), new baseline (certified known-good state), and new release (exposure to end users). Lecture 10 covers this in detail.

### Q2.22 — Why is concept location described as the most error-prone phase?
Because (1) the developer is searching an unfamiliar codebase, (2) the concept may be expressed in non-obvious terms, (3) there is no oracle that confirms "this is the right code," and (4) wrong-path discoveries waste time. Even experienced developers backtrack in concept location.

### Q2.23 — In Lab 2 of the author's portfolio, what feature was selected?
**Group / Ungroup** — the user-visible capability of selecting multiple figures and combining them into a single group (or reversing that). This feature became the *thread* running through Labs 2-9.

### Q2.24 — How was the Group / Ungroup feature located in JHotDraw?
Using a combination of SUR (regex search for "group") and SUL (following the call graph from `Action.actionPerformed`). The first path through `Toolbar` infrastructure was a wrong way; backtracking led to `GroupAction`, `UngroupAction`, and `GroupFigure` — the actual implementation.

### Q2.25 — Why is the wrong-way / backtrack / right-way pattern normal?
Because concept location is a search problem with no precomputed map. The first plausible path often turns out to be infrastructure (UI, dispatch, framework) rather than domain logic. Backtracking is the search-tree algorithm's *retract and try another branch* — entirely expected.

### Q2.26 — How does the phased model interact with continuous deployment / DevOps?
DevOps compresses the phases temporally — many changes pass through all phases multiple times per day — but does not *eliminate* the phases. Even a one-line change is still located, has impact analysed (often by CI), is actualised, verified, and committed. DevOps *automates* phases, it doesn't skip them.

### Q2.27 — What is a "change request"?
A textual description of the desired modification — what should change, why, by when, with what acceptance criteria. The change request is the input to Initiation and anchors all subsequent phases. Modern teams formalise change requests as JIRA tickets, GitHub issues, or user stories.

### Q2.28 — Why is JHotDraw a good case study for software maintenance?
It is (1) real production-grade code with a long history, (2) old enough to exhibit Lehman's complexity rise, (3) small enough to fit in a semester, (4) Java-based and OOP-paradigmatic, and (5) open source so all its history is available. JHotDraw is essentially a maintenance laboratory.

### Q2.29 — What is the relationship between concept location and the impact set?
Concept location finds the *direct* code (the location of the concept itself). Impact analysis then computes the *indirect* code (everything dependent on it). The impact set is concept-located code plus its transitive dependents. You cannot compute the impact set without first locating the concept.

### Q2.30 — Why is "find the right code" not a one-step process for an experienced developer?
Even an experienced developer is dealing with: (1) potentially unfamiliar parts of a large codebase, (2) historical drift between names and concepts, (3) deliberate indirection by past developers, and (4) the cognitive constraint that humans hold at most ~7 chunks in working memory. Concept location requires iteration even with experience.

---

## Chapter 3 — Software Processes, CI, and Impact Analysis

### Q3.1 — Define impact set.
The set of code elements (classes, methods, fields, configuration files) that will need to be modified for a change to be complete. The impact set has two parts: the **direct impact set** (code literally touched by the change) and the **indirect impact set** (code that depends on the direct set and must be updated to remain consistent).

### Q3.2 — Distinguish direct from indirect impact.
**Direct impact** = code being explicitly modified to implement the change. **Indirect impact** = code that compiles/runs against the direct impact and must be adjusted so the system as a whole stays consistent. For example, changing a method signature directly impacts the method; indirectly impacts every caller.

### Q3.3 — What is static impact analysis?
Computing the impact set by *reading the code* — call graphs, type dependencies, import statements. Static analysis is *language-aware* and *complete* for syntactic dependencies, but cannot see runtime dispatch, reflection, or implicit dependencies (e.g., resource bundles).

### Q3.4 — What is dynamic impact analysis?
Computing the impact set by *running the code* and observing which elements actually execute under realistic inputs. Dynamic analysis catches reflection-based dependencies that static analysis misses, but only sees code paths actually exercised by the test inputs — so it has *coverage gaps*.

### Q3.5 — What is historical / behavioural impact analysis?
Computing impact from the *git history*, finding files that have historically been modified together. This is **change coupling** (Lecture 11's Tornhill / CodeScene concept). It catches *hidden* dependencies that neither static nor dynamic analysis sees — files that always change together for reasons no automated tool can deduce.

### Q3.6 — Why does the disagreement between static and historical impact matter?
Disagreement is *informative*: (1) coupled in history but not in call graph = **hidden coupling**, often a sign of *shotgun surgery* (an anti-pattern); (2) coupled in call graph but not in history = a *dead path*, code that exists but is no longer exercised. Both findings are actionable.

### Q3.7 — Define Continuous Integration (CI).
The practice of mechanically running the full test suite on every commit (or every PR), so that the *baseline* status — green or red — is always known. CI is the technical implementation of Rajlich's Conclusion-phase baseline check.

### Q3.8 — What is the goal of CI?
To shorten the feedback loop between *commit* and *knowledge that something is broken*, from days (or never) to minutes. Short feedback loops catch regressions before they accumulate and before the developer's mental context is lost.

### Q3.9 — What does the CI build verify?
At minimum: the code compiles, all tests pass, and basic static analysis (style, common bugs) succeeds. More mature pipelines also verify code coverage thresholds, dependency security scans, license compliance, and integration tests against staging environments.

### Q3.10 — What tool did Lab 3 use for CI?
**GitHub Actions** — a CI runner integrated into GitHub repositories. The workflow runs `mvn -B -s .maven-settings.xml test` on every pull request to `develop`, gating merges on a green build.

### Q3.11 — What is the difference between CI and Continuous Deployment?
**CI** = automated *integration and verification* of changes into a shared branch. **CD (deployment)** = automated *promotion* of verified changes through environments to production. CI is the precondition for CD, but a team can have CI without CD (verify but deploy manually).

### Q3.12 — What is change propagation (Rajlich)?
The *temporal* shape of an impact set — a *wave* of inconsistencies spreading outward from each edit until the system is consistent again. Lecture 10's Drawlets diagrams visualise this with red (currently editing), orange (now inconsistent), green (re-consistent), and grey (explored, not impacted).

### Q3.13 — Why is change propagation modelled as a *wave* rather than a *set*?
Because edits happen in sequence, not simultaneously. Each edit potentially makes other code inconsistent; that code is then edited, which may propagate the inconsistency further. The set of *currently inconsistent* code changes over time — it is a wave passing through the codebase, not a fixed boundary.

### Q3.14 — Why is mechanised verification (CI) better than manual verification?
Manual verification is *expensive* (developer time), *unreliable* (humans forget steps), *delayed* (run only at named milestones), and *non-reproducible* (different testers, different environments). CI is cheap, reliable, immediate, and reproducible. The trade-off is the up-front cost of writing and maintaining the automation.

### Q3.15 — Name two empirical findings that justify hotspot-based impact analysis.
**Graves et al. (2000):** (1) *number of past changes* predicts future faults better than *code size*; (2) older modules have ~1/3 fewer faults than otherwise-equivalent younger modules. Both findings imply that *change history* is a richer signal than *static metrics* for predicting where work will be needed.

### Q3.16 — What is the relationship between impact analysis and refactoring?
A *large* impact set signals an opportunity for refactoring — the change is scattered across many classes, suggesting missing abstraction. *Refactoring shortens future impact sets* (Lecture 10's Drawlets data: 13 classes → 5 classes after refactoring). Impact analysis therefore both *informs* refactoring and *measures* its value.

### Q3.17 — What is a "regression"?
A bug introduced (or feature lost) in code that was previously known to be working. Regressions are particularly painful because the team already paid the cost of getting the feature right once. Regression *tests* are the testing-suite mechanism that catches them.

### Q3.18 — What is the "baseline" in CI vocabulary?
The current known-good state of the repository — the most recent commit (or branch state) for which the full test suite passed. The baseline moves forward with each green CI run and stalls (or moves backward via revert) when a red build occurs.

### Q3.19 — Why does CI typically gate pull request merges?
Because merging a red PR into a shared branch makes the *shared branch* red, blocking everyone else's work until the breakage is fixed. Gating merges on green PRs preserves the invariant *"the shared branch is always green"* — a property the rest of the team depends on.

### Q3.20 — What is a "build matrix"?
A CI configuration that runs the build under multiple combinations of JDK version, OS, and dependency versions in parallel. Catches conformity issues (Brooks's essential difficulty) early — code that passes on JDK 8 but fails on JDK 17, for instance.

### Q3.21 — What information should a change request contain?
At minimum: (1) a clear description of the desired behaviour change, (2) the rationale / user story, (3) acceptance criteria (how to verify the change is complete), and (4) priority and rough scope estimate. Without these, the Initiation phase cannot complete.

### Q3.22 — How does CI relate to Rajlich's Conclusion phase?
CI is the *mechanisation* of Conclusion's *new baseline* step. Every PR's green CI run produces a new baseline; every red CI run *prevents* a new baseline. The *human* parts of Conclusion (release decisions, stakeholder acceptance) remain manual.

### Q3.23 — What is `mvn -B test`?
Maven's batch-mode test runner. `-B` (batch mode) disables interactive prompts and produces compact log output suitable for CI. `test` runs the test goal, which executes all unit and integration tests configured by Surefire and Failsafe.

### Q3.24 — Why does Lab 3 specify `--no-transfer-progress` to Maven?
To suppress download-progress logging during dependency resolution. In CI, progress logging is noisy and slows the build. The `-B` batch flag does similar suppression, but `--no-transfer-progress` is more aggressive.

### Q3.25 — What is the impact-set table from Lab 3?
A two-column table where each row is an affected class. Column 1 = the class. Column 2 = whether it is *directly* or *indirectly* impacted, with a brief justification (e.g., "GroupAction — direct, the action class being modified"; "AbstractSelectedAction — indirect, GroupAction's superclass").

### Q3.26 — When does dynamic impact analysis fail?
When the test inputs do not exercise all execution paths. Dynamic analysis sees what *did* execute, not what *could*. Reflection-heavy or configuration-heavy paths often go unexercised by default test inputs and therefore unseen by dynamic analysis.

### Q3.27 — Why is a class with many outgoing dependencies risky?
Because the class depends on many others, so a change to any of those *may* affect it. High *fan-out* (outgoing dependencies) makes the class hard to test in isolation (many mocks needed) and creates fragility cascading down the dependency chain.

### Q3.28 — What is "fan-in" vs "fan-out"?
**Fan-in** = number of other classes that *depend on* this class. **Fan-out** = number of other classes that *this class depends on*. High fan-in means many callers (changing this class has wide impact); high fan-out means many dependencies (this class is fragile to upstream changes).

### Q3.29 — What is "code coverage" and how does it relate to impact analysis?
Code coverage = the percentage of code lines / branches / paths executed by tests. It is a *crude* proxy for impact-set knowledge: tests that exercise more of the impact set give better confidence that the change is safe. But coverage is *not* a guarantee of correctness — Dijkstra's dictum still applies.

### Q3.30 — What is the practical limit of static impact analysis in dynamic languages?
In dynamic languages (Python, Ruby, JavaScript), much dependency is resolved at runtime via duck typing, dynamic dispatch, and reflection. Static analysis catches less, and historical / behavioural analysis (Lec 11) becomes correspondingly more valuable. Java sits in between — mostly statically analysable, but reflection-heavy frameworks (Spring, Hibernate) leak past static tools.

---

## Chapter 4 — Refactoring and Refactoring to Patterns

### Q4.1 — Define refactoring.
**Refactoring is changing the *structure* of code without changing its *behaviour*.** The classical definition is from Martin Fowler's *Refactoring* (1999, p. xvi). The dual constraint — structural change *and* behavioural preservation — is what makes refactoring testable: the existing tests must continue to pass.

### Q4.2 — Who wrote the canonical refactoring book?
**Martin Fowler**, in *Refactoring: Improving the Design of Existing Code* (Addison-Wesley, 1999). The book catalogues approximately 70 named refactorings, each with mechanics (step-by-step transformation) and motivating smells.

### Q4.3 — What is the difference between refactoring and rewriting?
**Refactoring** preserves behaviour and changes structure incrementally; the system remains buildable and testable throughout. **Rewriting** discards the existing code and starts over; the system is non-functional during the rewrite. Joel Spolsky's *Things You Should Never Do, Part I* (2000) argues rewrites are nearly always a mistake.

### Q4.4 — Why is refactoring placed *twice* in Rajlich's phase model?
As **Prefactoring** (before the change, to make the change local) and as **Postfactoring** (after the change, to clean up duplication the change introduced). The two roles address different problems at different points; placing refactoring twice acknowledges this.

### Q4.5 — Define prefactoring.
Refactoring performed *before* a planned change, to restructure the affected code so that the planned change becomes local rather than scattered. Prefactoring trades a small up-front investment for a much smaller actual change.

### Q4.6 — Define postfactoring.
Refactoring performed *after* a change, to remove duplication or other smells that the change introduced. Often the change adds a feature by copying-and-modifying existing code; postfactoring consolidates the resulting duplication.

### Q4.7 — Name three classical refactorings from Fowler's catalogue.
**Extract Method** (move a code block into a new method with a meaningful name), **Move Method** (transfer a method to a more appropriate class), and **Replace Conditional with Polymorphism** (replace a `switch` on a type code with subclasses).

### Q4.8 — What is "Compose Method"?
A refactoring that *iteratively* extracts methods until each method does exactly one thing at exactly one level of abstraction. The result is a top-level method that reads as a sequence of high-level steps, each implemented by a lower-level method.

### Q4.9 — What is "Replace Conditional with Polymorphism"?
A refactoring that replaces a `switch` (or `if-else` chain) on a type code with subclasses of a base class. Each subclass implements its own version of the conditional behaviour, and the original `switch` becomes a single polymorphic method call.

### Q4.10 — Why is Replace Conditional with Polymorphism considered an important refactoring?
Because it simultaneously addresses two SOLID violations: **SRP** (the class doing the switch was handling multiple responsibilities) and **OCP** (adding a new type-code value previously required modifying the switch). The polymorphic form makes new types *additive* rather than *invasive*.

### Q4.11 — What is "Extract Method"?
Take a code fragment (a block within an existing method), give it a meaningful name, and turn it into its own method. The original location now calls the extracted method. Used to (1) reduce method size, (2) document intent through naming, and (3) enable reuse.

### Q4.12 — What is "Inline Method"?
The inverse of Extract Method — replace a method call with its body. Used when the method's body is *more* expressive than its name, or when the indirection no longer serves a purpose.

### Q4.13 — What is "Move Method"?
Take a method that more naturally belongs on another class and *transfer it*, updating callers. Often used when a method on class A uses more of class B's data than class A's own.

### Q4.14 — Who wrote *Refactoring to Patterns*?
**Joshua Kerievsky** (Addison-Wesley, 2004). The book bridges Fowler's *Refactoring* (1999) and the Gang of Four's *Design Patterns* (1994), describing refactoring *sequences* that take code from a smell to a recognisable pattern (Observer, Decorator, Adapter, etc.).

### Q4.15 — Give an example of a "refactoring to pattern."
**Replace Hard-coded Notifications with Observer** — start with code where a class directly calls multiple listeners hard-coded by type, end with code where the class maintains a `Listener` interface list and notifies them polymorphically. The intermediate steps are individual Fowler-style refactorings.

### Q4.16 — Name the four refactoring categories in Fowler's catalogue.
(There are more than four, but the main ones:) **Composing methods, Moving features between objects, Organising data, Simplifying conditional expressions, Making method calls simpler, Dealing with generalisation.** Each category groups refactorings that address a particular kind of smell.

### Q4.17 — What did Rajlich's Drawlets example show about refactoring's impact?
That two refactorings (Move function + Splitting roles) reduced the *number of classes modified* by a future change from **13 to 5** — a **62% reduction**. The *lines of code* modified barely changed (91 → 87), so refactoring reduces *scattering*, not *amount*.

### Q4.18 — What is the "splitting roles" refactoring?
When a single method serves two different roles (e.g., `move` is used both for user-initiated moves and as a substep of creation), split it into two methods (`move` + `secureMove`). Now a future change to one role doesn't touch the other.

### Q4.19 — Why is splitting roles important for change propagation?
Because each role evolves independently. If both roles are served by one method, any change to one role must consider whether it breaks the other — and changes propagate through callers of both roles. Splitting localises future changes to the affected role.

### Q4.20 — What is a "code smell"?
A surface symptom (a long method, duplicate code, large class, primitive obsession, feature envy) that suggests a deeper structural problem. Smells are *heuristics* for finding refactoring candidates, not bugs — code with smells works correctly, but is brittle to change.

### Q4.21 — What is "Feature Envy"?
A smell where a method on class A uses class B's data more than class A's own. The method is "envious" of B's data — and would be more naturally placed on B. The cure is *Move Method*.

### Q4.22 — What is "Primitive Obsession"?
A smell where primitives (strings, ints) are used to represent domain concepts that should be small classes. Example: representing a phone number as a `String` rather than a `PhoneNumber` class. The cure is *Replace Data Value with Object* and related refactorings.

### Q4.23 — What is "Shotgun Surgery"?
An anti-pattern where a single conceptual change requires edits in *many* unrelated places. Indicates missing abstraction — the concept is spread across the codebase rather than centralised. Causes high change coupling (Lec 11) and high impact sets.

### Q4.24 — What is the relationship between refactoring and testing?
Refactoring *requires* a working test suite to preserve behaviour during structural change. Without tests, refactoring is *gambling* — the developer cannot detect silently broken behaviour. This is why the author's Lab 4 deferred several larger refactorings until Lab 7 added the test floor.

### Q4.25 — What is the IDE's role in refactoring?
Modern IDEs (IntelliJ, Eclipse, VS Code) provide *automated* refactoring — Rename, Extract Method, Move Method, Inline Variable, etc. Automated refactoring is *behaviour-preserving by construction*, removing the principal risk (silent breakage) of manual refactoring.

### Q4.26 — What is "Compose Method" in code form?
A top-level method that is a sequence of well-named method calls, each at the same level of abstraction:
```java
public void actionPerformed(ActionEvent e) {
    if (isGroupingAction) {
        performGroup();
    } else {
        performUngroup();
    }
}
```
The `performGroup` and `performUngroup` methods then do their own work, possibly with their own sub-extractions.

### Q4.27 — What did Lab 4 of the author's portfolio refactor?
The `GroupAction.actionPerformed` method in JHotDraw — a 67-line method mixing levels of abstraction was decomposed via *Compose Method* into a 5-line dispatch plus single-purpose helpers (`performGroup`, `performUngroup`). Additionally, a dead `prototype` shadow field in `UngroupAction` and a stale `// XXX` comment were removed (Boy Scout cleanups).

### Q4.28 — Why is "small steps" the discipline of refactoring?
Because each small step preserves behaviour and is testable. A *large* refactoring is a sequence of small, individually-safe steps. The danger is in *combining* steps without intermediate verification — that's when behaviour silently breaks.

### Q4.29 — When should you NOT refactor?
(1) When you don't have tests and can't add them cheaply (refactoring without a safety net). (2) When the code is genuinely about to be deleted. (3) When you're under deadline and the refactor isn't required for the deadline. (4) When the refactor would make the change *less* clear (rare, but possible).

### Q4.30 — What is the long-term economic case for refactoring?
That continuous refactoring *amortises* the cost of future changes. Rajlich's Drawlets data (62% reduction in classes-modified) is the measurable form. Without refactoring, Lehman's 2nd law guarantees rising complexity; with refactoring, complexity is held flat or reduced. The investment pays back across the system's lifetime.

---

## Chapter 5 — Actualization, OO Principles, and Clean Architecture

### Q5.1 — What does SOLID stand for?
**S**ingle Responsibility, **O**pen / Closed, **L**iskov Substitution, **I**nterface Segregation, **D**ependency Inversion. Five OO design principles compiled and popularised by **Robert C. Martin** in the late 1990s and early 2000s.

### Q5.2 — State the Single Responsibility Principle.
*"A class should have one, and only one, reason to change."* — Robert C. Martin. A class with multiple responsibilities will be modified for multiple reasons, and these modifications often interfere with each other. SRP says split such classes.

### Q5.3 — State the Open / Closed Principle.
*"Software entities (classes, modules, functions) should be open for extension, but closed for modification."* — Bertrand Meyer (1988), as restated by Martin. Adding new functionality should be possible without modifying existing code, typically via inheritance, composition, or polymorphism.

### Q5.4 — State the Liskov Substitution Principle.
**Subtypes must be substitutable for their base types** — anywhere code expects an instance of the base, an instance of any subtype must work without breaking. From **Barbara Liskov** (1987). Violations include subclasses that throw `UnsupportedOperationException` for parent-class methods.

### Q5.5 — State the Interface Segregation Principle.
*"Clients should not be forced to depend on methods they do not use."* — Robert C. Martin. Fat interfaces (one interface declaring many methods serving multiple use cases) should be split into smaller, role-specific interfaces.

### Q5.6 — State the Dependency Inversion Principle.
**Depend on abstractions, not on concretions.** Two parts: (1) high-level modules should not depend on low-level modules — both should depend on abstractions; (2) abstractions should not depend on details — details should depend on abstractions.

### Q5.7 — Give an example of an SRP violation.
The author's `GroupAction` class in JHotDraw violates SRP: it does *both* grouping and ungrouping, with a boolean `isGroupingAction` flag dispatching between the two. The class has two reasons to change — group-logic changes and ungroup-logic changes — and these are independent.

### Q5.8 — Give an example of an OCP violation.
The same `GroupAction.isGroupingAction` dispatch also violates OCP. Adding a *third* action type (e.g., re-group, with different semantics) would require *modifying* `GroupAction` — adding a new branch to the dispatch. The polymorphic form (Replace Conditional with Polymorphism) is OCP-compliant.

### Q5.9 — Give an example of an LSP violation.
A `Square` class inheriting from `Rectangle` and overriding `setWidth` and `setHeight` to keep them equal violates LSP — a `Rectangle`-typed variable cannot reliably set width and height independently if the actual object is a `Square`. The "is-a" relationship (a square *is* a rectangle) does not survive substitution.

### Q5.10 — Give an example of an ISP violation.
JHotDraw's `DrawingView` interface has dozens of methods; the author's `GroupAction` only uses ~10. The other ~90 are *forced* dependencies that `GroupAction` doesn't need. A `Selectable` sub-interface containing only the methods `GroupAction` actually uses would honour ISP.

### Q5.11 — Give an example of DIP applied to time.
The **DateServer pattern** (Lecture 7): instead of calling `new GregorianCalendar()` directly inside business code, define a `DateServer` interface with a `getDate()` method, depend on it via injection. Production wires in a real implementation; tests wire in a stub returning fixed dates.

### Q5.12 — How does SRP enable testability?
A class with one responsibility has a *small* surface area to test. Setting up a test requires fewer mocks, and tests can target the responsibility directly. A multi-responsibility class requires complex setup because all its responsibilities must be made consistent.

### Q5.13 — How does DIP enable testability?
By depending on abstractions (interfaces), production code can be wired with real implementations and tests can substitute mocks. Direct dependency on concrete classes (e.g., `new GregorianCalendar()` inline) creates *hard-coded* coupling that tests cannot break.

### Q5.14 — Who wrote *Clean Architecture* and when?
**Robert C. Martin**, *Clean Architecture: A Craftsman's Guide to Software Structure and Design* (Prentice Hall, 2017). The book consolidates architectural ideas Martin had been developing since the late 1990s into a single coherent presentation.

### Q5.15 — What is the central rule of Clean Architecture?
**The Dependency Rule:** source code dependencies must point *inward* through the concentric layers. Outer layers know about inner layers; inner layers know nothing about outer layers. This makes inner layers replaceable without breaking outer layers, and vice versa.

### Q5.16 — Name the layers of Clean Architecture from inner to outer.
**Entities → Use Cases → Interface Adapters → Frameworks & Drivers.** Entities encode core business rules. Use Cases encode application-specific business rules. Interface Adapters translate between the application and external systems. Frameworks & Drivers are the outermost layer — databases, web, UI.

### Q5.17 — What is the practical implication of the Dependency Rule?
The framework / database / UI becomes a *plugin* to the business logic, not the other way around. Swap React for Angular, MySQL for Postgres, Swing for JavaFX — without changing the inner layers. Most large legacy systems violate this because they were built framework-first.

### Q5.18 — What is GRASP?
**General Responsibility Assignment Software Patterns** — a set of nine OO design patterns proposed by **Craig Larman** in *Applying UML and Patterns*. The nine: Information Expert, Creator, Controller, Low Coupling, High Cohesion, Polymorphism, Pure Fabrication, Indirection, Protected Variations.

### Q5.19 — What is "Information Expert" (GRASP)?
The pattern that says: assign a responsibility to the class with the information needed to fulfil it. If method `m` needs data `d`, and `d` lives on class `C`, then `m` should be on `C`. This is the GRASP form of Feature Envy avoidance.

### Q5.20 — What is "High Cohesion" (GRASP)?
The pattern that says: assign responsibilities such that each class is *focused* — its methods all relate to its core purpose. Low-cohesion classes do many unrelated things and are hard to understand and change. SRP is the load-bearing form of High Cohesion.

### Q5.21 — What is "Low Coupling" (GRASP)?
The pattern that says: minimise the dependencies between classes. Each class should know about *few* other classes. Low coupling makes classes independently changeable; high coupling makes change ripple. The Law of Demeter is a tactical rule for achieving low coupling.

### Q5.22 — What is the Law of Demeter / Principle of Least Knowledge?
A method `m` of class `C` should only call methods of: (1) itself, (2) its parameters, (3) objects it creates, (4) its instance fields. *Chained* calls like `a.getB().getC().getD()` violate Demeter because the caller now knows about `B`, `C`, and `D` even though it only directly holds `A`.

### Q5.23 — What is a "train wreck" in code?
A chained method call like `customer.getOrder().getProduct().getCategory().getName()`. The name comes from the visual appearance — coupling cars chained together. Violates the Law of Demeter and produces brittle code: any change in `Order`, `Product`, or `Category` may break this caller.

### Q5.24 — How is Law of Demeter related to SRP?
A class that respects Demeter touches few other classes — so its responsibilities are bounded by what it can directly reach. Demeter-respecting classes naturally tend toward SRP. Conversely, SRP-violating classes often violate Demeter because they reach into multiple object graphs to fulfil their multiple responsibilities.

### Q5.25 — What is "Protected Variations" (GRASP)?
The pattern that says: identify points of *predicted variation* and place stable interfaces around them. Behind the interface, implementation can vary without affecting clients. This is the GRASP form of OCP, generalised to any variation point (not just type extension).

### Q5.26 — What is "Pure Fabrication" (GRASP)?
The pattern that says: when no existing domain class is a natural home for a responsibility, *invent* a class for it. E.g., a `PaymentProcessor` does not correspond to a real-world entity but is a useful software construct. Pure Fabrications keep the domain model coherent while housing implementation responsibilities.

### Q5.27 — Why are SOLID and GRASP not contradictory?
GRASP is older and broader (1997, Larman) — it covers *responsibility assignment* in general. SOLID is narrower and more recent (1999-2003, Martin) — it covers *class design constraints*. SOLID can be derived from GRASP (SRP from High Cohesion, OCP from Protected Variations, etc.). They are layers, not rivals.

### Q5.28 — Why does the author's `canUngroup` method exhibit a "mockability tax"?
Because it relies on `Object.getClass().equals(prototype.getClass())` — a final method on `Object` that Mockito cannot stub on regular mocks. Tests of `canUngroup` must use *real* concrete figure classes, splitting the test strategy. The fix is **Splitting Roles** (Lec 10) plus **Replace Conditional with Polymorphism** (Lec 4): introduce `prototype.matches(figure)` as a polymorphic query.

### Q5.29 — What is the practical benefit of designing for substitutability (LSP)?
Tests become *polymorphic* — write a test against the base type, and it works for every subclass automatically. Production code becomes *flexible* — new subtypes can be added without touching callers. The trade-off is the design overhead of getting the base contract right.

### Q5.30 — Why is the dependency rule called "inversion"?
Because it inverts the *naive* dependency direction. Naively, high-level business code calls into low-level utilities, which call into the database, etc. — dependencies point *outward* from policy toward mechanism. The Dependency Inversion Principle reverses this: define abstractions at the policy level, and have mechanisms depend on them. Concretely: business code defines a `Repository` interface; the database implementation depends on the business code, not vice versa.

---

## Chapter 6 — Clean Code

### Q6.1 — Who wrote *Clean Code* and when?
**Robert C. Martin** (with contributions from multiple co-authors), *Clean Code: A Handbook of Agile Software Craftsmanship* (Prentice Hall, 2009). The book is the principal reference for line-by-line code quality discipline in the OO / Java tradition.

### Q6.2 — How does Stroustrup define clean code?
*"I like my code to be elegant and efficient. The logic should be straightforward to make it hard for bugs to hide … Clean code does one thing well."* — **Bjarne Stroustrup** (creator of C++), quoted in Martin 2009 ch. 1.

### Q6.3 — How does Booch define clean code?
*"Clean code is simple and direct. Clean code reads like well-written prose. Clean code never obscures the designer's intent."* — **Grady Booch**, quoted in Martin 2009.

### Q6.4 — How does Cunningham define clean code?
*"You know you are working on clean code when each routine you read turns out to be pretty much what you expected."* — **Ward Cunningham**, quoted in Martin 2009. The criterion is *intersubjective expectability* — would another competent reader find it readable?

### Q6.5 — State the Boy Scout Rule.
*"You should always leave the code cleaner than you found it."* — Robert C. Martin, paraphrasing the Boy Scouts of America's *Always leave the campground cleaner than you found it*. The rule operationalises continuous, small-cost cleanup as a discipline that compounds.

### Q6.6 — What is WTFs/minute?
**Thom Holwerda**'s humorous metric of code quality: count the times a reviewer reacts audibly to surprising code. A good code review provokes one "WTF"; a bad review provokes a stream. Popularised through a cartoon in Martin 2009.

### Q6.7 — Name three rules for naming variables.
(1) **Intension-revealing** — names should reveal what the variable is for. (2) **Avoid disinformation** — don't use names like `l` (looks like `1`) or `O` (looks like `0`). (3) **Searchable** — single-letter names like `j` are unfindable by grep, so avoid them outside short loops.

### Q6.8 — Why is Hungarian notation discouraged?
Because it encodes the *type* of a variable in its name (e.g., `iCount` for an integer count). When the type changes — and types change — the name *lies* unless every reference is updated. Modern IDEs surface types automatically, making the encoding redundant.

### Q6.9 — Why are member prefixes like `m_` discouraged?
Same reason as Hungarian — they encode information (membership) that the IDE highlights anyway. They also clutter the code without adding semantic value. Martin specifically calls out `m_dsc` versus `description`.

### Q6.10 — What is Martin's recommended function size?
**Under 20 lines.** Even better, under 10. The first rule of functions is they should be *small*; the second rule is they should be *smaller than that*. The author's Lab 4 work split a 67-line method into multiple methods of ~10-15 lines.

### Q6.11 — What is the "Stepdown Rule"?
A code file should read top-to-bottom like a newspaper: the highest-level concepts at the top, supporting details below. Each function should be followed by the functions it calls (one level lower in abstraction), so the file *cascades down* the abstraction hierarchy.

### Q6.12 — What does "one level of abstraction per function" mean?
A function should not mix high-level intent (`getHtml()`), intermediate operations (`PathParser.render(pagePath)`), and low-level details (`.append("\n")`) in the same body. Each function operates at one level; calls to other functions handle the lower levels.

### Q6.13 — What is the ideal number of function arguments?
**Zero.** One is acceptable. Two is harder (the caller must remember the order). Three should be avoided. Four or more is a sign the function needs a *parameter object* — a small class wrapping the arguments.

### Q6.14 — Why are flag arguments bad?
A function that takes a boolean flag is doing *two* different things, with the caller selecting which. `render(true)` violates "do one thing" — the function should be split into `renderForSuite()` and `renderForSingleTest()` or similar. Flag arguments hide a control-flow decision in a parameter.

### Q6.15 — What is Command-Query Separation?
A function should *either* do something (a command — has side effects, returns nothing useful) *or* answer something (a query — returns information, has no side effects), but not both. `boolean set(key, value)` that both sets the value and returns whether it succeeded is unclear.

### Q6.16 — Why are comments "failures" in Martin's view?
Because every comment is a place where the *code* could not speak for itself. The comment is compensation for unexpressed intent. The remedy is usually to refactor — extract a method with a meaningful name, rename a variable, introduce a constant — until the comment becomes redundant.

### Q6.17 — Name three categories of "good" comments.
(1) **Legal comments** — copyright headers, licence preambles. (2) **Informative comments** — when a function's name can't fully convey intent (e.g., explaining a regex pattern). (3) **Intent / amplification** — *why* the code looks the way it does, especially when the why is non-obvious.

### Q6.18 — Name three categories of "bad" comments.
(1) **Redundant comments** that restate what the code obviously does. (2) **Journal comments** that log who changed what — `git blame` does this better. (3) **Commented-out code** — the version-control history holds it; commenting it out in source is afraid-to-delete behaviour.

### Q6.19 — What is the "newspaper metaphor" of code formatting?
A code file should read top-to-bottom like a newspaper article — the *headline* (high-level overview) at the top, *supporting details* below. Reading the top tells you what the file does; reading further drills into the specifics. Files that don't follow this require the reader to scan back and forth.

### Q6.20 — Why is horizontal alignment discouraged?
Aligning the `=` of a block of declarations creates a visual column that emphasises *type names* over *variable names*. Reading flow is disrupted as the eye tracks columns rather than left-to-right. Modern formatters often auto-disable alignment for this reason.

### Q6.21 — Distinguish objects from data structures.
**Objects** hide their data behind abstractions and expose functions that operate on that data. **Data structures** expose their data and have no meaningful functions. *Hybrid* structures (some data exposed, some methods) suffer the problems of both — hard to add new types *and* hard to add new operations.

### Q6.22 — State the Law of Demeter in Martin's words.
*"A method `m` of a class `C` should only call methods of: itself, its parameters, objects it creates, and its instance fields."* Equivalently, Lecture 5's *Principle of Least Knowledge* — minimise what each unit needs to know about others.

### Q6.23 — Why is `return null` discouraged?
Because every caller must *check* for null, and forgetting the check produces `NullPointerException` at runtime. The cure is to return *special-case objects* — empty collections, default instances — that the caller can use unconditionally.

### Q6.24 — Why is `pass null` discouraged?
Methods that accept `null` as an argument must defensively check it, cluttering implementations. The cure is to *reject* null at the boundary (throw `InvalidArgumentException` or `NullPointerException` early) or *forbid* it via documentation / `@NotNull` annotations.

### Q6.25 — State the Three Laws of TDD.
(1) You may not write production code until you have written a failing unit test. (2) You may not write more of a unit test than is sufficient to fail — and not compiling is failing. (3) You may not write more production code than is sufficient to pass the currently failing test. *Beck, in Martin 2009 ch. 9.*

### Q6.26 — What does F.I.R.S.T. stand for?
**F**ast (runs quickly so it runs often), **I**ndependent (no test depends on another), **R**epeatable (works in any environment), **S**elf-validating (boolean pass/fail), **T**imely (written just before the production code). Martin's five properties of clean tests.

### Q6.27 — What is the standard class organisation order?
(1) Public static constants. (2) Private static variables. (3) Private instance variables. (4) Public functions. (5) Private utilities, placed right after the public functions that call them (stepdown rule applied within the class).

### Q6.28 — How are classes measured for size, per Clean Code?
Not in *lines*, but in *responsibilities*. A class should have **one and only one** responsibility (SRP). A class whose name needs `And`, `Or`, or `Processor`/`Manager`/`Util` suffixes is usually doing more than one thing.

### Q6.29 — Name Kent Beck's four rules of simple design, in priority order.
**(1) Runs all the tests. (2) No duplication. (3) Expressive. (4) Minimal classes and methods.** The order matters: tests come first because they verify the design *is* a design; duplication second because it is the cheapest improvement with the highest payoff.

### Q6.30 — Why is Beck's order for the four rules important?
Tests first because a design that doesn't pass its tests *isn't a design*. Duplication second because it's *easy to remove* and yields high payoff. Expressiveness third because it requires more thought but compounds over time. Minimisation last because the temptation to delete prematurely is real and dangerous.

---

## Chapter 7 — Software Testing

### Q7.1 — State Dijkstra's testing dictum.
*"Testing can demonstrate the presence of bugs, but not their absence."* — **Edsger Dijkstra**, *Notes on Structured Programming* (EWD249, 1972). The single most-cited statement in software testing literature; it sets the *theoretical ceiling* on what testing can achieve.

### Q7.2 — What is the theoretical reason testing cannot be complete?
**Turing's halting problem** (1936) — no general program decides whether another program halts. By extension (Rice's theorem), no general program decides arbitrary non-trivial semantic properties of programs, including "is this code correct?". Testing's incompleteness is a theorem, not a tooling limitation.

### Q7.3 — Define unit testing.
Testing of one *unit* of code — typically a single method or class — in isolation from its dependencies. Dependencies are replaced with test doubles (mocks, stubs). Fast, focused, and the *base* of the test pyramid.

### Q7.4 — Define integration testing.
Testing of two or more units *together*, with their real interactions exercised. Catches wiring bugs that unit tests cannot — broken dependency injection, misconfigured serialisation, mismatched interfaces. Slower than unit tests, fewer in number.

### Q7.5 — Define system testing.
Testing of the *entire assembled system* end-to-end, with all real components wired. Catches integration bugs that integration tests miss — the full stack including UI, database, network. Slowest test category, fewest in number.

### Q7.6 — Distinguish white-box from black-box testing.
**White-box** testing uses knowledge of the internal structure of the SUT — coverage of specific branches, paths, state transitions. **Black-box** testing only sees the API and inputs/outputs; it tests the *contract*, not the implementation.

### Q7.7 — What is differential testing?
Comparing two *implementations* of the same specification on the same inputs and flagging discrepancies. Useful when re-implementing a system (e.g., re-writing in a new language) — the old and new should agree on every input.

### Q7.8 — What is stress testing?
Pushing the SUT to its operational *limits* — high load, high concurrency, resource exhaustion — to find breaking points. Reveals failures that don't appear under normal use.

### Q7.9 — What is random testing?
Testing with *unconstrained* inputs — random strings, random byte sequences, fuzzed inputs. Catches bugs the developer did not anticipate, especially around input validation and edge cases. Modern *property-based testing* (e.g., jqwik) is a structured form of random testing.

### Q7.10 — Name the eight rules for creating testable software.
(1) Clean Code. (2) Refactor. (3) Describe what it does and how it interacts. (4) No extra Threads. (5) No swap of global variables. (6) No pointer soup. (7) Module unit tests. (8) Support fault injection. Plus the meta-rule: **Assertions, Assertions, Assertions !!!**

### Q7.11 — Define an assertion.
An *executable check* for a property that must be true at a given point — a documented invariant. In Java: `assert condition : "message";`. Disabled at runtime by default unless the JVM is started with `-ea`. Surefire enables assertions in tests.

### Q7.12 — Name the three rules for assertions.
**R1:** Assertions are not for error handling — use exceptions for *expected* failure modes. **R2:** NO SIDE EFFECTS — an assertion that mutates state is silently broken when disabled. **R3:** No silly assertions — `assert 1+1==2` adds nothing.

### Q7.13 — What is the rough assertion density in LLVM?
**~1 assertion per 110 lines of code** (~13,000 assertions across ~1.4M LOC). This is the working benchmark for production-grade assertion discipline. GCC is lower density (~1 per 800 LOC).

### Q7.14 — When should assertions be disabled?
In *mission-critical* code where it is better to continue with potential corruption than to fail completely — e.g., avionics, the landing-stage of a Mars probe. For ordinary application code, *failing early* is almost always safer than continuing in an undefined state.

### Q7.15 — State the "what is going on?" diagnostic tree.
When a test fails, ask in order: (1) **Bug in the SUT?** (2) **Bug in the test itself?** (3) **Bug in the specification?** (4) **Bug in the OS / compiler / libraries / hardware?** Each question eliminates a category before moving on.

### Q7.16 — Give an example of a "bug in the specification."
The **Mars Climate Orbiter (1999)** — one team specified force in pounds-force-seconds (English units), the other in newton-seconds (Metric). Both teams' code was correct against *their* spec. The spec itself was inconsistent. The orbiter burned up.

### Q7.17 — Name the four sensitivities of the Fragile Test Problem.
**Behaviour sensitivity** (business rule changed), **Interface sensitivity** (method/UI renamed), **Data sensitivity** (DB/fixture changed), **Context sensitivity** (OS / locale / time zone changed). The four-category split lets us *triage* fragility.

### Q7.18 — What is "testing under the UI"?
Routing automated tests *below* the presentation layer — testing the Application/Domain layers directly, bypassing UI controls and event handling. UI tests should be the exception (when the UI itself is the SUT), not the default.

### Q7.19 — What is fault injection?
Replacing a low-level call (e.g., `open()`) with a wrapper (e.g., `my-open()`) that *can fail on demand* — succeed 100 times, then fail every 100th call. Used to exercise recovery paths that production almost never executes naturally.

### Q7.20 — State the TDD cycle.
**Red → Green → Refactor.** (1) **Red:** write a failing test. (2) **Green:** write the minimum production code to make it pass. (3) **Refactor:** clean up. Repeat. One test at a time; implement only as much as the test requires.

### Q7.21 — What is the difference between "Moving to TDD" and "Real TDD"?
**Moving to TDD:** tests are written first, but defects are still handed off to a separate QA team. The dev → QA handoff remains. **Real TDD:** the developer's own QA loop — defects discovered later become *new failing tests* that the developer adds and fixes. The QA gate dissolves.

### Q7.22 — Define a mock.
A test double that *records and verifies* method calls on it. The most powerful and most heavyweight kind of double. Use when the SUT's correctness is in *which methods it called* — `verify(mock).someMethod()`. Mockito's `mock()` produces mocks.

### Q7.23 — Define a stub.
A test double that returns *pre-defined* data with no behaviour beyond returning. The lightest and most static double. Use when the SUT just needs a constant return value from a dependency — no verification of interactions.

### Q7.24 — Define a spy.
A *partial* mock that wraps a real object and overrides only selected methods. Use when most of the real behaviour is fine but one method needs to be controlled. Mockito's `spy()` produces spies.

### Q7.25 — What is the DateServer pattern?
A pattern for testing time-dependent code: wrap calls like `new GregorianCalendar()` in an injectable `DateServer` interface. Production wires in `() -> new GregorianCalendar()`; tests wire in a mock that returns specific dates. Dependency Inversion (Lec 5) applied to time.

### Q7.26 — What is Mockito's `getClass()` limitation?
`Object.getClass()` is final in Java. Mockito cannot stub final methods on regular `mock(Class.class)` instances. Code that depends on `getClass()` for type comparison (like the author's `canUngroup`) therefore creates a **mockability tax** — tests must use real concrete classes.

### Q7.27 — Define an acceptance test.
A test that verifies the system meets a *user-visible* requirement, defined *with* the user. Traditionally manual and performed by the customer after delivery; in Agile / TDD, automated (JGiven, FitNesse, Cucumber) and written *before* the user story is implemented.

### Q7.28 — Why is manual testing discouraged in modern practice?
Manual tests are *expensive* to run (developer time), *unreliable* (humans skip steps under pressure), and *delayed* (run only at named milestones). Automated tests are cheap, deterministic, and run on every commit — making them effectively a *floor* of regression coverage manual testing cannot match.

### Q7.29 — What is the relationship between TDD and refactoring?
Refactoring is the *third step* of the TDD cycle. With tests in place, refactoring is *safe* — the tests immediately reveal silent breakage. Without tests, refactoring is gambling. TDD therefore makes refactoring a *normal* part of development rather than a heroic intervention.

### Q7.30 — What is the F.I.R.S.T. acronym?
**Fast, Independent, Repeatable, Self-validating, Timely.** The five properties of clean tests (Martin 2009 ch. 9). Fast → tests run often. Independent → no test depends on another. Repeatable → deterministic, environment-independent. Self-validating → boolean pass/fail. Timely → written just before the code.

---

## Chapter 9 — Pragmatic BDD for Java

### Q9.1 — What does BDD stand for?
**Behaviour-Driven Development.** A testing / specification methodology where scenarios are written in domain language (Given-When-Then), executable as tests, and serve as living documentation for the system's behaviour.

### Q9.2 — Who coined the term BDD?
**Dan North**, in the mid-2000s. BDD emerged from his frustration with TDD's emphasis on *unit* over *behaviour*. North wanted tests that read as *specifications* understandable to non-developers.

### Q9.3 — Name the five typical issues with conventional unit tests that BDD addresses.
(1) Many technical and irrelevant details. (2) The point of the test is often hard to grasp. (3) Code duplication. (4) Can only be read by developers. (5) Cannot be used as documentation.

### Q9.4 — Name the four defining properties of BDD.
(1) Behaviour is described in a common **domain language** understandable by domain experts. (2) Domain experts and developers **collaborate** on defining behaviour. (3) Scenarios are **executed like normal tests**. (4) The result is **living documentation**.

### Q9.5 — What is Given-When-Then?
The universal BDD scenario shape: **Given** an initial state, **When** an action occurs, **Then** an expected outcome holds. Each clause may be joined by **And** for additional setup, action, or assertion details.

### Q9.6 — Show a Given-When-Then example.
*Scenario: a pancake can be fried out of egg, milk, and flour.* **Given** an egg, **And** some milk, **And** the ingredient flour, **When** the cook mangles everything to a dough, **And** the cook fries the dough in a pan, **Then** the resulting meal is a pancake.

### Q9.7 — Distinguish classical from developer-friendly BDD frameworks.
**Classical** (Cucumber, JBehave, Concordion, FitNesse) keeps the plain-text scenario *separate* from Java step definitions — domain experts can write the text, but maintenance cost doubles. **Developer-friendly** (JGiven, Spock, ScalaTest) puts the scenario *in code* — lower cost, but domain experts cannot author.

### Q9.8 — Name five classical BDD frameworks.
**Cucumber** (plain text + Java/Ruby), **JBehave** (plain text + Java), **Concordion** (HTML + Java), **FitNesse** (wiki + Java), **Robot Framework** (plain text + Python/Java).

### Q9.9 — Name five developer-friendly BDD frameworks.
**Spock** (Groovy), **ScalaTest** (Scala), **Jnario** (Xtend), **Serenity\*** (Java), **JGiven** (Java).

### Q9.10 — What is the "additional maintenance cost" of classical BDD?
Two artefacts must be kept in sync — the plain-text `.feature` file and the Java step definitions that implement it. If the feature file changes, step definitions must be updated; if step definitions are refactored, the feature file may need text updates. Developer-friendly frameworks eliminate this.

### Q9.11 — What is JGiven?
A Java-only BDD framework where scenarios are written as JUnit test methods using fluent Given-When-Then API calls, organised into stage classes. JGiven generates a human-readable report from the test code automatically. Open source under Apache 2.

### Q9.12 — What is a stage class in JGiven?
A Java class that encapsulates the steps for one phase of the scenario (Given, When, or Then). Typically one stage class per phase. The Given stage sets up state; the When stage performs the action; the Then stage asserts the outcome. Stage classes are a *unique feature* of JGiven.

### Q9.13 — What annotations does JGiven use for state transfer between stages?
**`@ScenarioState`** (readable and writable across stages), **`@ProvidedScenarioState`** (written by this stage, read by later), and **`@ExpectedScenarioState`** (read by this stage from earlier). The annotations make data flow between stages explicit and statically declared.

### Q9.14 — Why is the stage-class pattern important?
Because it enforces *Single Responsibility* at the test level — the Given stage cannot accidentally assert, the When stage cannot set up state. The framework's structure prevents tests from mixing phases, which would otherwise be a common error.

### Q9.15 — How does JGiven render scenario methods in its report?
Method names with **underscores** are rendered as **words with spaces**. The `$` placeholder in a method name is substituted with the argument at report-rendering time. So `given().$_rectangle_figures_on_the_canvas(2)` renders as "Given 2 rectangle figures on the canvas".

### Q9.16 — What is the `@Description` annotation in JGiven?
An optional annotation on a test method providing a human-readable description for the scenario. Useful when the method name (which becomes the rendered title by underscore→space substitution) doesn't fit the desired wording, or when the test ties to a user-story ID.

### Q9.17 — Why is JGiven called "developer-friendly"?
Because scenarios are pure Java — no separate file format, no parser, no extra build step. Developers use their existing IDE (autocomplete, refactor, navigate), and existing CI (JUnit runner). The trade-off is that domain experts cannot author scenarios — only read the generated reports.

### Q9.18 — What is JGiven's HTML5 report?
A generated HTML web app showing all scenarios, grouped by class, with tags / filters / search. Each scenario expands to show its Given/When/Then in human-readable form. Lecture 9's screenshot shows 53 scenarios with status indicators, durations, and tag filters.

### Q9.19 — What practical experience does TNG report with JGiven?
Three years of production use on a 70-developer Java enterprise project, with **3000+ scenarios**. Reported outcomes: greatly improved readability and reusability, reduced maintenance cost (no hard numbers), well accepted by developers, easy to learn for new joiners.

### Q9.20 — Who wrote *Building Evolutionary Architectures*?
**Neal Ford, Rebecca Parsons, and Patrick Kia** (O'Reilly, 2017). The book is the source of the technical-debt definition cited in Lecture 11: *"stuff that isn't supposed to be there and is in the way."*

### Q9.21 — What is AssertJ?
A fluent Java assertion library, alternative to JUnit's terse `assertEquals(expected, actual)` form. Provides `assertThat(value).is(...).and(...)` chains, type-specific assertions (`StringAssert`, `ListAssert`), and good failure messages. Used inside Lab 9's JGiven Then-stages.

### Q9.22 — Why does AssertJ exist when JUnit has assertions?
Because JUnit's assertions are "underpowered from the start" (Lecture 9). The community used to mix Hamcrest, Fest, and JUnit, creating inconsistent assertion styles. AssertJ replaces all three with a single fluent API: actively maintained, near-complete superset, easy to read.

### Q9.23 — Show a typical AssertJ assertion.
`assertThat(actualList).hasSize(3).contains("apple").doesNotContain("banana").allMatch(s -> s.length() > 1);` — chained predicates reading as a single sentence about the list. The `.as("description")` modifier adds a label that appears in failure messages.

### Q9.24 — What is a Custom Condition in AssertJ?
A reusable predicate, defined as a `Condition<T>` subclass with a `matches(T value)` method, that can be used with `.is(condition)` / `.isNot(condition)`. Allows the test author to name complex predicates: `assertThat(12).is(evenDivBySix);`.

### Q9.25 — What is a Custom Assertion in AssertJ?
A *subclass* of `AbstractAssert` for a domain type, adding type-specific assertions and a custom `assertThat(...)` factory. Example: `StudentAssert extends AbstractAssert<StudentAssert, Student>` with method `isInMiddleSchool()`, then `assertThat(student).isInMiddleSchool()`.

### Q9.26 — What is AssertJ-Swing?
An AssertJ-companion library for automating Swing GUI tests — simulates clicks, drags, keystrokes, menu navigation. Supports JUnit and TestNG. Embeds screenshots in HTML reports on failure. Can test Swing thread-rule violations.

### Q9.27 — Why was AssertJ-Swing not runnable in Lab 9?
Because AssertJ-Swing requires a real Swing display (X11 / Wayland). The author's environment (remote terminal) is headless. The Lab 9 scenario was kept as documentation but `@Ignore`d — runnable only on a workstation with a display.

### Q9.28 — What is "living documentation"?
Documentation that the build *refuses to let go stale*. BDD scenarios are executable: if production code drifts from the documented behaviour, the build fails. Therefore the scenarios *cannot* be both green and inaccurate — a property no static documentation system has.

### Q9.29 — Why is "living documentation" BDD's strongest justification?
Because every other form of documentation rots — Confluence pages drift, comments diverge from code, specifications stop matching implementation. Living documentation breaks this pattern by making the documentation *executable*. Drift cannot accumulate silently.

### Q9.30 — What is the principal *limitation* of developer-friendly BDD?
**Domain experts cannot author scenarios.** They can read the generated report, but they cannot write new tests. This is the explicit trade-off (stated in Lecture 9): low maintenance cost for developer-only authorship. Projects with non-developer domain experts who *write* scenarios should use classical BDD (Cucumber).

---

## Chapter 10 — Example of Software Change + Conclusion

### Q10.1 — What codebase does Lecture 10's worked example use?
**Drawlets** — a 40,000-LOC drawing framework originally implemented by **Kent Beck and Ward Cunningham**, later ported to Java. Approximately 100 classes and 35 interfaces. Used by Rajlich as a representative case study because it parallels JHotDraw structurally.

### Q10.2 — What change request does Lecture 10 work through?
*"Implement an owner for each figure. An owner is the user who put the figure onto the canvas, and only the owner should be allowed to modify it. At the beginning of a session, users input ID and password and become owners of all figures created during the session."*

### Q10.3 — In Lecture 10's concept classification, which words were "significant"?
**Figure** and **canvas**. These are the concepts that must be *located* in the code. The other words were either *irrelevant* (verbs like "implement", "allowed", "modify") or *external* (input from user: "owner", "ID", "password").

### Q10.4 — What was "external" in Lecture 10's concept classification?
Concepts that come *from outside* the system as input: **owner, ID, password**. These don't need to be located in the existing code — they will be *added* to the code (e.g., as fields, parameters) during Actualization.

### Q10.5 — What was "irrelevant" in Lecture 10's concept classification?
Verbs and connectors that don't have implementation correlates: **implement, allowed, modify, beginning, input, created**. These are part of the change-request prose but don't correspond to specific code elements to locate.

### Q10.6 — What did the "Wrong Way" diagram in Lecture 10 show?
A first concept-location attempt starting from `SimpleApplet` and following links to UI infrastructure (`StylePalette`, `ToolBar`, `ToolPalette`) — a dead end because the *canvas* concept lives elsewhere. The dead end is shown to illustrate that backtracking is normal.

### Q10.7 — What did the "Backtrack" diagram show?
The same diagram with the dead-end nodes *greyed out* — the developer has recognised the wrong path and retreated. Backtracking is not a failure; it is an explicit step in the concept-location process.

### Q10.8 — What did the "Right Way" diagram show?
The successful path from `SimpleApplet` directly down to `DrawingCanvas` — the actual location of the *canvas* concept. The right path was reached by following structural composition rather than UI infrastructure.

### Q10.9 — Name the two new classes added in Lecture 10's Actualization.
**OwnerIdentity** (a data class holding ID + password) and **SimpleListener** (a collaborator that subscribes to figure-modification events and rejects modifications from non-owners). These are *added* — the modifications happen on existing classes.

### Q10.10 — Why is change propagation modelled as a *wave*?
Because the edits are *sequential*, not simultaneous. Each edit can make additional code inconsistent (orange); editing that code propagates further; eventually the wave reaches the edges of the impact set and the system is consistent again (green). The wave is a *temporal* phenomenon.

### Q10.11 — What colour scheme does the change-propagation visualisation use?
**Red** = currently editing. **Orange** = inconsistent with red, needs propagation. **Green** = updated and consistent again. **Grey** = explored but not impacted. The wave moves through the diagram as edits accumulate.

### Q10.12 — What is the Drawlets test-to-production ratio?
**Test code 4,800 LOC / Production code 17,800 LOC ≈ 27%**. This is the baseline ratio. The change in the example modified 91 production lines and 124 test lines — about **1.4 test lines per production line**.

### Q10.13 — Why is 1.4 test lines per production line important?
Because it is an *empirical baseline* for the cost of running real software. Any team estimating that a change is "X lines of production code" should plan for approximately 1.4X lines of test code. The ratio is *not* heroic — it is *steady-state*.

### Q10.14 — What does Lecture 10's refactoring slide 37 show?
That applying *Move function* and then *Splitting roles* refactorings reduces the **number of classes modified** by a future change from **13 → 8 → 5** (a 62% total reduction). The lines of code modified barely changes — refactoring reduces *scattering*, not *amount*.

### Q10.15 — Name the three steps of Rajlich's Conclusion phase.
**(1) Commit:** programmers return updated code to the repository, resolving conflicts. **(2) New baseline:** thorough testing certifies the new known-good state. **(3) New release:** the baseline is exposed to end users (separate decision from baseline).

### Q10.16 — Why is baseline testing often done "overnight or over the weekend"?
Because thorough testing of a complex system takes substantial time — running the full test suite, integration tests, performance tests, and possibly manual exploratory testing. Running it during work hours blocks active development; running it overnight uses idle CI capacity.

### Q10.17 — What is the "baseline as deadline" concept?
The deadline to commit a feature is the time when baseline testing *starts*. Programmers who miss this deadline submit for the next baseline — accruing extra rebase / re-test work and visibility with management. The deadline is a *social* artefact, not just technical.

### Q10.18 — How does Rajlich handle minor bugs found during baseline testing?
The testing team can still certify the new baseline despite the minor bugs. The bugs go onto the *bug-report stack* and are fixed as part of future changes. Minor bugs don't block release; they accumulate as backlog.

### Q10.19 — How does Rajlich handle major bugs found during baseline testing?
The testing team *rejects* the buggy commits. In severe cases, the entire new baseline work is rejected — no new baseline is created, and all in-flight work is invalidated or postponed. The testing team can identify which programmer committed buggy files, and *"reputation of these programmers suffers."*

### Q10.20 — What is acceptance testing in Rajlich's Conclusion phase?
A separate testing pass performed *by stakeholders* (not the engineering team), focused on functional / user-visible behaviour. Acceptance testing approves software for *release* — distinct from the baseline (which is engineering's approval of the code).

### Q10.21 — Distinguish baseline from release in Rajlich's model.
**Baseline** = certified known-good repository state (engineering's verdict). **Release** = baseline exposed to end users (business's decision plus packaging work). Every release is a baseline; not every baseline becomes a release.

### Q10.22 — What is the "versioned model of software lifespan"?
A release pattern combining **less frequent large releases** (e.g., `AwesomeApp 4.2` — downloaded and installed) with **more frequent small releases** (patches incorporated via a *merge* tool). Modern Linux distros, semver-versioned libraries, and Maven artifact repositories use this pattern.

### Q10.23 — Why is releasing more expensive than baselining?
Releasing requires packaging (binary builds), release notes, version-numbering, signing / notarisation, distribution-channel updates, user-facing documentation, and customer communication — all *additional* to the engineering work that produced the baseline. Many teams baseline daily but release quarterly.

### Q10.24 — What is Rajlich's rule about old-test treatment after a change?
**Tests from the old version that are not affected by the change are kept as regression tests for the future.** They protect against accidental future regressions in the unchanged parts of the system.

### Q10.25 — What is Rajlich's rule about obsolete tests?
**Obsolete tests are removed.** Tests for behaviour that no longer exists (e.g., a removed feature) should not be kept — they add maintenance burden and may give false signals. Keep regression tests; delete obsolete tests.

### Q10.26 — What is Rajlich's rule about new-feature tests?
**Tests of the new features are added after the change.** Note "after" — Rajlich's worked example is *retrofitting* tests onto an existing change, not TDD. In a TDD context, the new tests would be added *before* (red) the change.

### Q10.27 — What two tools does Rajlich's Drawlets example use for acceptance testing?
**JGiven and Mockito** — slide 27 explicitly names these. This is striking because it validates the author's own tool choice in Labs 7 (Mockito) and 9 (JGiven). The textbook arrived at the same recommendation independently.

### Q10.28 — What does the *Move function* refactoring move?
A function that exists in multiple subclasses (duplicated) is moved *up* into the base class so it is defined once. The N subclasses now inherit the function rather than duplicating it. Next time the function changes, only 1 edit is needed instead of N.

### Q10.29 — What does the *Splitting roles* refactoring split?
A single method that serves two distinct roles (e.g., `move()` used for user-initiated moves *and* creation substeps) is split into two methods (`move()` + `secureMove()`). Each role evolves independently; future changes touch only one of them.

### Q10.30 — How does Lecture 10 close the entire course?
By showing that the phase model introduced in Lecture 2 *applies in full* to a real codebase change. Every previous lecture's concept (concept location, impact analysis, refactoring, SOLID, clean code, testing, BDD) maps to a phase in the worked example. Lecture 10 is the *capstone* — proof that the model is operational, not just academic.

---

## Chapter 11 — Beyond Technical Debt: CodeScene

### Q11.1 — Define technical debt per Ford, Parsons & Kia.
*"Stuff that isn't supposed to be there **and is in the way** of the stuff that is supposed to be there."* — *Building Evolutionary Architectures* (2017), p. 110. The bold clause is operative: code is debt only when it *obstructs* current work, not merely when it is regrettable.

### Q11.2 — Who coined the technical-debt metaphor?
**Ward Cunningham**, in *The WyCash Portfolio Management System* (OOPSLA 1992). His original analogy: shipping first-time code is like going into debt — useful short-term, dangerous if not repaid.

### Q11.3 — What two parts make Ford/Parsons/Kia's definition operational?
**(1) "Isn't supposed to be there"** — the quality dimension (the code shouldn't exist in this form). **(2) "Is in the way"** — the interference dimension (the code obstructs current work). *Both* are required. Regrettable but unobstructive code is not debt; it is *history*.

### Q11.4 — Why is technical debt described as *relational*?
Because whether code is "in the way" depends on what work is being done *now*. The same legacy module is debt for a team adding features atop it, and not-debt for a team that ships once a year without touching it. Debt is a property of code-in-context, not code-in-isolation.

### Q11.5 — State Lehman's Law as cited in Lecture 11.
**Continuing Change** — "a system must be continually adapted or it becomes progressively less satisfactory." **Increasing Complexity** — "as a system evolves, its complexity increases unless work is done to maintain or reduce it." Both originate in Lehman 1980.

### Q11.6 — Who is Rich Hickey and what is his Easy vs Simple claim?
**Rich Hickey** — creator of the Clojure programming language. His 2011 *Simple Made Easy* talk argues that "easy" code (fast initial velocity) decays asymptotically to zero productivity, while "simple" code (slower initial velocity) sustains productivity over time. *"If you ignore complexity, you will slow down."*

### Q11.7 — How does Hickey's curve relate to Lehman's laws?
Hickey's "easy" curve is the *consequence* of ignoring Lehman's 2nd law — complexity rises and velocity collapses. Hickey's "simple" curve is the consequence of *paying* the complexity tax up-front (preventive maintenance, refactoring) and maintaining slower-but-sustained velocity.

### Q11.8 — What are the two business symptoms of technical debt?
**(1) Roadmap symptom:** long lead times, lack of predictability (the Sisyphus image). Promised feature dates slip repeatedly. **(2) Product symptom:** bugs (the swarm-of-beetles image). Every fix produces another regression.

### Q11.9 — What is Tornhill's critique of SonarQube?
That SonarQube's output is *not actionable*. A typical dashboard shows 10,000+ violations and a triple-digit man-day estimate — telling the team *that* there's a problem but not *where to start*. The team cannot plausibly fix all violations, and the dashboard doesn't prioritise.

### Q11.10 — What is the SonarQube Tomcat example's "technical debt" estimate?
**11.0% = $341,563 = 683 man-days.** These figures are presented in Lecture 11 as an example of *non-actionable* output. The figures are large, vague, and provide no path to action.

### Q11.11 — What is CodeScene?
A behavioural code analysis SaaS product by **Adam Tornhill**. Uses git history + source code + (optionally) project management data to identify hotspots, change coupling, off-boarding risk, knowledge concentration, and trends. The product grew out of Tornhill's book *Software Design X-Rays* (2018).

### Q11.12 — What is the central CodeScene reframe of code analysis?
*"It's a movie rather than a snapshot."* Static analysis sees the code as it is *now*; CodeScene sees the code as it has *evolved over time*. The added dimensions are **time** (when did this change) and **organization & people** (who touched it).

### Q11.13 — What are CodeScene's three primary input sources?
**(1) Source code** (as for any static analyser). **(2) Version-control data** (the principal added input — git history). **(3) Project-management tools** (e.g., JIRA) — optional, used to correlate code changes with stories / bugs.

### Q11.14 — Define a hotspot.
**A complicated piece of code that you have to work with often.** Formally: the product of *complexity* (principal) and *change frequency* (interest rate). High-priority refactoring target — high principal × high interest = the team's effort is being silently consumed there.

### Q11.15 — State Tornhill's hotspot formula.
**Hotspot = Code complexity (Principal) × Code change frequency (Interest rate).** Both factors are required: complex code that never changes is not urgent; simple code that changes often is annoying but cheap.

### Q11.16 — What is the financial analogy of the hotspot formula?
*Principal* = the underlying debt amount (code complexity). *Interest rate* = how often you pay (change frequency). The *cost* is the product. A hotspot is *high interest on high principal* — the worst form of debt.

### Q11.17 — What are the empirical findings of Graves et al. (2000)?
**(1)** Process measures based on change history are more useful than product metrics for predicting fault rates — *the number of times code has been changed is a better indication of how many faults it will contain than is its length.* **(2)** Older modules have ~1/3 fewer faults than otherwise-equivalent younger modules.

### Q11.18 — What does the Graves et al. paper imply for hotspot analysis?
That hotspots are *not just* refactoring targets — they are also *bug-density* predictors. The same map that shows where complexity × change frequency is high also predicts where future bugs will appear. This is the empirical foundation of CodeScene's analysis.

### Q11.19 — What is CodeScene's X-Ray feature?
A drill-down inside a hotspot *file* to find the hotspot *functions*. For each function within the file, X-Ray reports change frequency, lines of code, cyclomatic complexity, and overloaded-method status. The result is a 5-20 row table of *the worst functions in the worst file* — actionable.

### Q11.20 — What is "change coupling"?
Pairs of files (or functions) that historically commit together in the git history. Change coupling is the *behavioural* counterpart to static call-graph coupling — it catches hidden dependencies that no automated static tool can see.

### Q11.21 — What does it mean when static and historical coupling disagree?
*Informative disagreement:* **(1)** coupled in history but not in call graph = hidden coupling (a *shotgun-surgery* smell). **(2)** coupled in call graph but not in history = dead path (static dependency exists but the code is no longer exercised).

### Q11.22 — What are Tornhill's two definitions of *legacy code*?
**(1)** Code that *lacks in quality* (relative perspective — what looks legacy to one team may be fine to another). **(2)** Code that *we didn't write ourselves*. The second definition is the surprising one — sometimes "legacy" means *unfamiliar*, not *bad*.

### Q11.23 — What is "the Technical Debt That Wasn't"?
A team encounters unfamiliar code (Product #3 in the slide), assumes it is debt, and rewrites — but the rewrite turns out to be unnecessary. The "debt" was the team's *unfamiliarity*, not the code's *quality*. Onboarding would have been cheaper than rewriting.

### Q11.24 — What is CodeScene's off-boarding simulation?
A what-if analysis: toggle a developer "off" — simulating their departure — and observe which files have *no remaining knowledge* (red dots, "Off-Boarding Risk"). Files where the off-boarded developer was the *primary* author become risk-coloured.

### Q11.25 — What does the off-boarding simulation typically reveal?
That the hotspot map and the knowledge-concentration map *overlap*. The files that change most (hotspots) are also the files with the most concentrated single-author knowledge — so they are *both* the bug-density risks and the off-boarding risks. Concentrated risk in concentrated files.

### Q11.26 — Name three additional features in CodeScene mentioned in Lecture 11.
**(1) Proactive warnings** — alerts at PR review time when a change touches a known hotspot. **(2) Retrospectives** — post-mortem correlations between delivery slippage and hotspot regressions. **(3) Delivery Performance** — DORA-style lead-time and change-failure metrics. (Also: Microservices analysis, Branch Analyses.)

### Q11.27 — What is "Shotgun Surgery" in microservices context?
An anti-pattern where one logical change touches *many* services, requiring coordinated deploys. Visible in CodeScene as high cross-service change coupling. Indicates missing abstraction or premature service decomposition.

### Q11.28 — State the first of Tornhill's four-line conclusion.
*"Technical debt is a real problem regardless of programming language."* — applies to Java, Python, Haskell, COBOL, JavaScript. The problem is structural, not linguistic.

### Q11.29 — State the third of Tornhill's four-line conclusion.
*"Ultimately, you need to rely on human expertise."* — striking after a 26-slide deck about a heavily quantitative tool. The tool *informs* expert judgement; it does not *replace* it. Data alone is not action.

### Q11.30 — State the fourth of Tornhill's four-line conclusion.
*"Support your developer's judgment and experience with data to get the highest ROI."* — the synthesis: *judgement* alone is incomplete (engineers under-prioritise long-term debt); *data* alone is overwhelming (10,000 SonarQube violations). The combination is the working stance.

---

## How to use this Q&A file

**For revision:** read top to bottom over 3-4 sessions. Cover the answer, ask the question, check.

**For the exam:** when an essay question references a concept, use Ctrl-F to find the chapter section. The Q&A there gives you the recall layer; the [03-question-bank.md](03-question-bank.md) gives you the essay structure layer.

**For diagnostic:** at the end of revision, randomly sample 30 questions across chapters and answer them on paper. If you score 80%+, you have the recall layer locked.

**Best paired with:** [02-comprehensive-summary.md](02-comprehensive-summary.md) (deeper context), [04-quotes-and-citations.md](04-quotes-and-citations.md) (verbatim attributions), [06-numbers-and-frameworks.md](06-numbers-and-frameworks.md) (specific data points).
