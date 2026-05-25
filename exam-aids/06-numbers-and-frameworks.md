# 06 — Numbers and Frameworks

Specific statistics, frameworks, and tables. **Specific numbers in an essay = credibility multiplier.**

> **Use rule:** drop at least one number from this file into every major essay section. *"~80% of TCO"* beats *"a lot of"* every time.

---

## 1. Lientz-Swanson maintenance breakdown (1980)

| Category | Share | What it means |
|---|---:|---|
| **Perfective** | ~50% | Improvements, new features, performance |
| **Adaptive** | ~25% | Adapt to environment changes (OS, hardware, APIs) |
| **Corrective** | ~21% | Bug fixes |
| **Preventive** | ~4% | Refactoring to prevent future problems |

**Headline number:** Maintenance = ~80% of TCO (Total Cost of Ownership).

**Essay use:** *"Lientz and Swanson's 1980 study established that maintenance accounts for approximately 80% of a software system's total cost of ownership, with perfective maintenance — improvements rather than bug fixes — dominating at roughly half the total effort."*

---

## 2. CHAOS report — project outcomes (Standish Group)

Percentages vary by year; typical figures from course materials:

| Outcome | Approx. share |
|---|---:|
| **Successful** (on time, in budget, in scope) | ~30% |
| **Challenged** (delivered but late / over budget / reduced scope) | ~50% |
| **Failed** (cancelled or never used) | ~20% |

**Essay use:** *"The Standish Group's CHAOS report consistently shows that only roughly a third of software projects are delivered successfully on time, in budget, and in scope, while half are 'challenged' and the remainder fail outright."*

---

## 3. Brooks's essential difficulties

| # | Difficulty | One-line meaning |
|---|---|---|
| 1 | **Complexity** | Software handles many more cases than textbook examples |
| 2 | **Invisibility** | No natural physical form |
| 3 | **Changeability** | No cost-of-concrete; tempting to constantly change |
| 4 | **Conformity** | Must conform to moving environments (OS, hardware, expectations) |
| 5 | **Discontinuity** (Lec 1's addition) | Small changes can have disproportionate effects |

**Essay use:** *"Brooks's four essential difficulties (1975), with the lecture's addition of *Discontinuity*, are properties intrinsic to software's nature — not artefacts of poor tooling — and therefore not eliminable by methodology."*

---

## 4. Lehman's Laws of Software Evolution (1980)

| # | Law | Statement |
|---|---|---|
| 1 | **Continuing Change** | *"A system must be continually adapted or it becomes progressively less satisfactory"* |
| 2 | **Increasing Complexity** | *"As a system evolves, its complexity increases unless work is done to maintain or reduce it"* |
| (3-8) | Additional laws | Self-regulation, conservation of organizational stability, conservation of familiarity, continuing growth, declining quality, feedback system |

**Essay use:** *"Lehman's second law — that complexity rises by default unless actively counteracted — is the empirical foundation on which every refactoring practice in the course stands."*

---

## 5. Rajlich's phased model — the seven phases

```
Initiation
   ↓
Concept Location
   ↓
Impact Analysis  ──────────┐
   ↓                       │
Prefactoring               │ Verification
   ↓                       │   spans
Actualization              │   right-hand
   ↓                       │   side of
Postfactoring              │   the V
   ↓                       │
Conclusion ────────────────┘
```

**Essay use:** *"Rajlich's phased model (2012) breaks every software change into seven sequential phases, with Verification spanning the lower four. Each phase is both a checkpoint for engineering judgement and a description of what happens anyway when an engineer changes code."*

---

## 6. Drawlets case study — empirical baseline (Rajlich Lec 10)

| Metric | Value |
|---|---:|
| Production LOC | **17,800** |
| Unit test LOC | **4,800** |
| **Test ratio (test : prod)** | **~27%** |
| Unit tests | 385 |
| Test assertions | 1,369 |
| Functional tests | 141 |

**The change (adding "owner per figure" feature):**

| Metric | Value |
|---|---:|
| Production LOC modified | 91 (0.5%) |
| Test LOC modified | 124 (2.5%) |
| **Test-lines / production-lines ratio** | **1.4** |

**Essay use:** *"Rajlich's worked example on the Drawlets framework gives the field's clearest empirical baseline: 27% of the codebase is test code, and every line of production change requires approximately 1.4 lines of test change (Rajlich, 2012, ch. 17). These ratios are the steady-state cost of running software, not heroic investments."*

---

## 7. Refactoring impact data (Rajlich Lec 10, slide 37)

| Approach | Classes added | Interfaces modified | Classes modified | LOC modified |
|---|---:|---:|---:|---:|
| No refactoring | 2 | 1 | **13** | 91 |
| With Move function | 2 | 1 | 8 | 95 |
| With Splitting roles | 2 | 1 | **5** | 87 |

**Key insight:** classes modified drops from 13 → 5 (**−62%**) but LOC barely moves. **Refactoring reduces scattering, not amount.**

**Essay use:** *"Rajlich's measurement of refactoring impact (Lecture 10, slide 37) is empirically compelling: applying Move function plus Splitting roles cuts the number of classes affected by a future change from 13 to 5 — a 62% reduction — while the lines-of-code modified barely moves. Refactoring reduces the *scattering* of change, not its quantity."*

---

## 8. Production assertion density (Lec 7)

| Project | Assertions | LOC | Density |
|---|---:|---:|---:|
| **GCC** | ~9,000 | ~7,000,000 | ~1 per 800 LOC |
| **LLVM** | ~13,000 | ~1,400,000 | **~1 per 110 LOC** |
| **JHotDraw (before Lab 7)** | ~0 in this feature | n/a | far below |
| **Author's `GroupAction` (after Lab 7)** | 6 | ~180 | 1 per 30 LOC |

**Target benchmark:** LLVM's ~1 per 110 LOC = healthy production assertion discipline.

**Essay use:** *"Lecture 7 cites LLVM's roughly one assertion per 110 lines of code as the working benchmark for production-grade assertion discipline. The author's Lab 7 added six assertions to a 180-line section of `GroupAction.java`, a density of one per 30 lines — denser than LLVM for that module, but far below LLVM's density for the project as a whole, which contains effectively zero assertions in the rest of the codebase."*

---

## 9. SonarQube's "not actionable" example (Lec 11, slide 7)

Apache Tomcat 6.x SonarQube dashboard:

| Metric | Value |
|---|---:|
| Lines of code | 162,306 |
| Classes | 1,447 |
| **Violations** | **10,072** (8,794 Major) |
| Duplications | 7.1% (22,998 lines) |
| Comments | 26.6% |
| Cyclomatic complexity / method | 3.1 |
| Tags (FIXME/TODO) | 356 |
| **Technical debt** | **11.0% = $341,563 = 683 man-days** |

**Tornhill's critique:** *not actionable*. 10,072 violations and 683 man-days tells the team *that* there's debt, not *where to start*.

**Essay use:** *"Lecture 11 cites a SonarQube dashboard on Apache Tomcat reporting 10,072 violations and an estimated 683 man-days of technical debt — figures so large they are not actionable. The team cannot plausibly fix 10,072 items; the dashboard does not say which to fix first. This is the gap behavioural code analysis fills."*

---

## 10. Tornhill's hotspot formula (Lec 11)

```
Hotspot = Code complexity (Principal) × Code change frequency (Interest rate)
```

| Type of file | Verdict | Why |
|---|---|---|
| Complex, never changes | Not urgent | Principal only, no interest |
| Simple, changes often | Annoying, not expensive | Interest only, low principal |
| **Complex AND changes often** | **HOTSPOT — fix first** | High interest on high principal |
| Simple, never changes | Fine, leave alone | Negligible debt |

**Essay use:** *"Tornhill's hotspot formula (2018) reframes technical debt as a *product*, not a single property: code complexity is the principal, change frequency is the interest rate, and the team's effort is consumed where both are high."*

---

## 11. Graves et al. 2000 — process measures vs product metrics

The paper's two empirical findings:

1. *"Process measures based on the change history are more useful in predicting fault rates than product metrics of the code: the **number of times code has been changed is a better indication of how many faults it will contain than is its length**."*

2. *"If a module is, on the average, **a year older than an otherwise similar module, the older module will have roughly a third fewer faults**."*

**Essay use:** *"The empirical claim underpinning hotspot analysis comes from Graves, Karr, Marron and Siy's 2000 paper *Predicting Fault Incidence Using Software Change History* (IEEE TSE 26(7)). Two findings stand out: change history predicts faults better than code size, and modules survive *into* stability rather than starting that way — an older module has roughly a third fewer faults than an equivalent younger one."*

---

## 12. Three Laws of TDD (Beck / Martin)

1. **You may not write production code until you have written a failing unit test.**
2. **You may not write more of a unit test than is sufficient to fail (not compiling = failing).**
3. **You may not write more production code than is sufficient to pass the currently failing test.**

**Essay use:** *"Beck's Three Laws of TDD (Martin 2009, ch. 9) reverse the conventional ordering of test and code: production code is written *only* in response to a failing test, and only as much as the test requires. The discipline is severe but produces tests that are coverage-proven by construction."*

---

## 13. F.I.R.S.T. — properties of a clean test

| Letter | Property | Meaning |
|---|---|---|
| **F** | Fast | Tests must run quickly so they run often |
| **I** | Independent | Tests must not depend on each other; any order works |
| **R** | Repeatable | Works deterministically in any environment |
| **S** | Self-validating | Boolean pass/fail, no manual inspection |
| **T** | Timely | Written just before the production code that makes them pass |

**Essay use:** *"F.I.R.S.T. (Martin 2009, ch. 9) is the five-property test of test quality: Fast, Independent, Repeatable, Self-validating, Timely. A test that fails any of these constraints is a candidate for refactoring."*

---

## 14. Clean Code function rules (Lec 6 / Martin 2009 ch. 3)

| Rule | Working number |
|---|---|
| Function length | < 20 lines |
| Line length | < 150 characters |
| Function arguments | 0 ideal, 1 acceptable, 2 hard, 3+ avoid |
| Levels of abstraction per function | 1 |

**Essay use:** *"Martin's working numbers for clean functions (2009) — fewer than 20 lines, fewer than 150 characters per line, at most one or two arguments — are not aesthetic preferences but operational thresholds beyond which complexity demonstrably starts to bite."*

---

## 15. SOLID — the five principles

| Letter | Principle | Author | Year |
|---|---|---|---|
| **S** | Single Responsibility | Martin | 2003 |
| **O** | Open / Closed | Meyer / Martin | 1988 / 1996 |
| **L** | Liskov Substitution | Liskov | 1987 |
| **I** | Interface Segregation | Martin | 1996 |
| **D** | Dependency Inversion | Martin | 1996 |

---

## 16. JGiven adoption data (Lec 9, slide 19 — TNG experience)

| Metric | Value |
|---|---|
| Years in production | 3+ |
| Team size | up to 70 developers |
| Scenarios | 3,000+ |
| Acceptance | "Well accepted by developers; easy to learn by new developers" |

**Essay use:** *"JGiven's maintainer TNG reports three years of production use on a 70-developer Java enterprise project with over 3,000 scenarios (Lecture 9), supporting the framework's central claim that developer-friendly BDD scales to enterprise codebases."*

---

## 17. The author's own portfolio numbers

| Metric | Value |
|---|---|
| **Portfolio** lines (total) | ~3,900 |
| **Lectures** documented | 11 (1–6, 7, 9, 10, 11) |
| **Labs** documented | 7 (1, 2, 3, 4, 5, 7, 9) |
| **Tests added (Lab 7)** | 24 JUnit 4 unit tests |
| **Tests added (Lab 9)** | 4 JGiven BDD scenarios + 1 `@Ignore`d AssertJ-Swing |
| **Tests total** | 30 (26 prior + new) |
| **Failures** | 0 |
| **Production assertions added (Lab 7)** | 6 |
| **Production LOC modified (Lab 4 + Lab 7)** | ~60 |
| **Test LOC added** | ~600 |
| **Test : prod ratio of changes** | ~10:1 (anomalous — JHotDraw started near 0%) |
| **Commits on `alex` branch** | 10+ |

**Essay use:** *"The author's own JHotDraw work added approximately 600 lines of test code in response to approximately 60 lines of production code changes, an unusually high ratio of 10 to 1. The anomaly is informative: it reflects a codebase that started with essentially no test coverage for this feature, so the work was not 'testing the change' (Rajlich's 1.4 baseline) but 'building the test floor that should have existed before the change was contemplated'."*

---

## 18. Kent Beck's four rules of simple design (Lec 6)

In priority order:

1. **Runs all the tests.**
2. **No duplication.**
3. **Expressive.**
4. **Minimal classes and methods.**

**Why the order matters:**
- Tests come first — a design that doesn't pass its tests isn't a design.
- Duplication second — cheapest improvement with highest payoff.
- Expressiveness third — harder to achieve than removing duplication; compounds.
- Minimalism last — temptation to delete classes prematurely is real.

**Essay use:** *"Kent Beck's four rules of simple design (in Martin 2009, ch. 12), in priority order — runs all the tests, no duplication, expressive, minimal — are not interchangeable. Tests are the verification that any design *is* a design; duplication is the cheapest improvement; expressiveness compounds; minimisation is last because the temptation to delete prematurely is real."*

---

## 19. The "what is going on?" diagnostic tree (Lec 7)

When a test fails, ask in order:

```
test output not OK
   ↓
Is the bug in the SUT?   ──── YES → fix the SUT
   ↓ NO
Is the bug in the test? ──── YES → fix the test
   ↓ NO
Is the bug in the spec? ──── YES → fix the spec (e.g., Mars Climate Orbiter)
   ↓ NO
Is the bug in OS/compiler/libs/hardware? ──── YES → escalate
   ↓ NO
   ?? (the rarest case)
```

**Essay use:** *"Lecture 7's 'what is going on?' tree is the single most useful diagnostic device the course offers: when a test fails, the bug may be in the SUT, the test, the specification, or the environment. The Mars Climate Orbiter case (1999, lost to a units mismatch between metric and English specifications) is the canonical example of *bug in the specification* and a reminder that test failures are not always code problems."*

---

## 20. Types of testing (Lec 7)

| Type | Scope | Visibility | Trigger |
|---|---|---|---|
| **Unit** | One module | white-box or black-box | Code change |
| **Integration** | Two+ modules | usually white-box | Wiring change |
| **System** | Whole product | black-box | Feature complete |
| **Differential** | Two implementations | black-box | Re-implementation |
| **Stress** | Whole product | black-box | Performance requirement |
| **Random** | Anywhere | black-box | Exploratory |
| **Acceptance** (Lec 9) | User-visible behaviour | black-box | Story complete |

---

## 21. Fragile Test — four sensitivities (Lec 7)

| Sensitivity | Trigger | Cure |
|---|---|---|
| **Behaviour** | Business rule change | None (expected fragility) |
| **Interface** | Method/GUI rename | IDE refactor + ISP |
| **Data** | Database/fixture change | Builders not fixtures |
| **Context** | OS / locale / time-zone | Explicit setup, DateServer pattern |

---

## 22. Three families of refactoring (Fowler 1999)

| Family | Examples | When |
|---|---|---|
| **Composing methods** | Extract Method, Inline Method, Compose Method | When functions are long |
| **Moving features between objects** | Move Method, Move Field, Extract Class | When responsibilities are misplaced |
| **Organising data** | Replace Magic Number with Symbolic Constant, Encapsulate Field | When data is exposed or hard to read |
| **Simplifying conditionals** | Replace Conditional with Polymorphism, Decompose Conditional | When `switch` / `if` chains are doing type dispatch |
| **Making method calls simpler** | Rename Method, Introduce Parameter Object | When signatures are unclear or verbose |
| **Dealing with generalisation** | Pull Up Method, Form Template Method | When subclasses share structure |

---

## 23. Common mistakes — the negative checklist

Numbers from the course's repeated warnings:

| Mistake | Lecture | Cost |
|---|---|---|
| Commented-out code | Lec 6 | Future developer afraid to delete |
| `// XXX`, `// FIXME`, `// TODO` comments | Lec 6 | Each one a marker of unfinished refactoring |
| Hungarian notation / member prefixes (`m_`) | Lec 6 | Lies the moment the type changes |
| Flag arguments (`render(true)`) | Lec 6 | "Do one thing" violation |
| `assert` with side effect | Lec 7 | Silently broken when assertions are disabled |
| `null` return / `null` argument | Lec 6 | Forces every caller to check |
| One-letter loop variables outside `i`, `j`, `k` | Lec 6 | Disinformation |
| Train wreck calls (`a.b().c().d()`) | Lec 5, Lec 6 | Demeter violation |
| Class that does two things | Lec 5, Lec 6 | SRP violation |
| Test that depends on another test's state | Lec 6 | F.I.R.S.T. Independence violation |

---

## How to use numbers in the essay

**Pattern A — single specific figure:**
> "Lientz and Swanson (1980) found that perfective maintenance — improvements rather than bug fixes — accounts for approximately 50% of all maintenance effort, dominating the category."

**Pattern B — ratio comparison:**
> "Rajlich's Drawlets baseline shows a 27% test-to-production code ratio with 1.4 test lines modified per production line of change. The author's own JHotDraw work showed an anomalous 10:1 ratio, reflecting the absence of a pre-existing test floor."

**Pattern C — number to anchor an argument:**
> "Refactoring's empirical value is measurable: Rajlich's worked example shows the number of classes touched by a future change falling from 13 to 5 — a 62% reduction — when two refactorings (Move function + Splitting roles) are applied (2012, ch. 17)."

**Pattern D — number to defend a counter-position:**
> "The claim that production assertions are 'paranoid' is not supported by the data. LLVM, a mature compiler infrastructure, contains roughly one assertion per 110 lines of code (Lecture 7) — a density that does not affect production performance because Java's `assert` is disabled by default."

---

**Next:** open file 07 ([Glossary](07-glossary.md)).
