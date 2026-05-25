# 08 — Concept Map

How the course's concepts connect. Use this file to weave a *coherent* essay — not a list of paragraphs.

> A 15-20 page essay that consists of paragraphs sitting next to each other reads as a *list*. An essay where each section *follows from* the previous reads as an *argument*. This file shows where the connections live.

---

## 1. The master diagram — one argument repeated at increasing resolution

```
                    LECTURE 1
              (essential difficulties)
                          │
                          v
     ┌──────── Software does not stand still ────────┐
     │                                                │
     │   Lehman's Laws (Lec 1, Lec 11)               │
     │     - Continuing Change                        │
     │     - Increasing Complexity                    │
     │                                                │
     │                                                │
     │   Brooks's essential difficulties (Lec 1)      │
     │     - Complexity, Invisibility, Changeability  │
     │     - Conformity, Discontinuity                │
     │                                                │
     │   Hickey's Easy vs Simple (Lec 11)             │
     │     - Complexity rises by default              │
     │                                                │
     └──────────────────┬─────────────────────────────┘
                        │
                        │  the foundational claim
                        │  is the *same claim* at
                        │  increasing magnification:
                        │
              MAGNIFICATION × 1
              ────────────────────
                LECTURE 2
              (phased model)
                Initiation → Concept Location → Impact
                → Prefactoring → Actualization → Postfactoring
                → Conclusion / Verification
                        │
                        │
              MAGNIFICATION × 10
              ────────────────────
                LECTURES 4 + 5
              (refactoring + SOLID)
                - SRP makes change local
                - DIP makes change mockable
                - Refactoring catalogue makes
                  change cheap
                        │
                        │
              MAGNIFICATION × 100
              ────────────────────
                LECTURE 6
              (Clean Code)
                - Naming, function size,
                  comments, formatting
                - Boy Scout Rule operationalises
                  the entire stack
                        │
                        │
              MAGNIFICATION × 1000
              ────────────────────
                LECTURES 7 + 9
              (Testing + BDD)
                - Tests pin behaviour
                - BDD pins user-facing contract
                - Together = the change is safe
                        │
                        │
              VERIFICATION
              ────────────────────
                LECTURE 10
              (worked example)
                - The phased model on Drawlets
                - JGiven + Mockito
                - 1.4 test lines per production line
                        │
                        │
              MEASUREMENT
              ────────────────────
                LECTURE 11
              (CodeScene / hotspots)
                - Where to apply all of the above
                - Principal × Interest = priority
                - Behavioural code analysis
```

**Essay use:** if you can frame the entire essay as *"the course's central argument operates at multiple magnifications,"* you have a coherent thesis that touches every lecture without listing them.

---

## 2. Cross-cutting threads — the five threads to weave through any essay

### Thread A — Complexity rises by default; pushing back is a discipline

| Lecture | Contribution |
|---|---|
| Lec 1 | Brooks's *essential* difficulties — complexity is inherent |
| Lec 1 | Lehman's 2nd law — complexity rises by default |
| Lec 4 | Refactoring as the *active counter-force* |
| Lec 6 | Boy Scout Rule as the *smallest* possible counter-move |
| Lec 11 | Hickey: ignoring complexity slows you down |
| Lec 11 | Hotspots = where the counter-force is most needed |

**Argument:** Every refactoring / testing / clean-code practice in the course is one of two things — either a *measurement* of complexity rise, or a *push-back* against it.

---

### Thread B — Testability is an architectural property

| Lecture | Contribution |
|---|---|
| Lec 5 | SRP makes classes small → mocks small |
| Lec 5 | DIP makes dependencies injectable → mockable |
| Lec 5 | ISP minimises mock surface area |
| Lec 6 | Small functions = isolated test targets |
| Lec 7 | The DateServer pattern = DIP applied to time |
| Lec 7 | The mockability tax = a *failure* of testability-as-architecture |
| Lec 9 | JGiven stage classes = SRP applied to test code |

**Argument:** If your code is hard to test, your design is bad. Testability is not a property *added* to a good design; it *is* the test of a good design.

---

### Thread C — Documentation that cannot rot

| Lecture | Contribution |
|---|---|
| Lec 6 | Comments rot ("comments are failures") |
| Lec 6 | Naming carries the documentation load |
| Lec 7 | Unit tests = executable spec |
| Lec 9 | BDD scenarios = readable executable spec |
| Lec 10 | Test suite maintenance = living documentation upkeep |
| Lec 11 | Behavioural analysis recovers lost knowledge from git |

**Argument:** Every form of *static* documentation rots. The only durable documentation is the kind the build *refuses to let* go stale.

---

### Thread D — Maintenance is the social activity

| Lecture | Contribution |
|---|---|
| Lec 10 | Baseline as deadline → reputation accrues |
| Lec 10 | Stakeholder acceptance testing → external gate |
| Lec 11 | Off-boarding simulation → knowledge concentration as debt |
| Lec 11 | "Ultimately, rely on human expertise" |

**Argument:** All the technical practices in the course are *enabled* or *blocked* by social factors. The careful PR, the named commit, the readable BDD scenario — these are *social* artefacts.

---

### Thread E — Measurement is the precondition for action

| Lecture | Contribution |
|---|---|
| Lec 1 | Lientz-Swanson percentages |
| Lec 1 | CHAOS report |
| Lec 3 | CI = automated measurement |
| Lec 6 | WTFs/minute as a measurement |
| Lec 7 | Coverage metrics |
| Lec 10 | Drawlets baseline numbers (1.4 ratio) |
| Lec 11 | Hotspots, change coupling, off-boarding risk |

**Argument:** Untill you measure, you cannot *prioritise*. SonarQube's 10,072 violations is measurement; the team still cannot act. CodeScene's top-10 hotspot list is measurement; the team can act tomorrow morning.

---

## 3. Concept dependency graph — what depends on what

```
        LIENTZ-SWANSON                  BROOKS
       (~80% TCO is maintenance)    (essential difficulties)
              │                              │
              └──────────┐    ┌──────────────┘
                         v    v
                       LEHMAN'S LAWS
                  (complexity rises by default)
                              │
              ┌───────────────┼───────────────┐
              v               v               v
        PHASED MODEL    REFACTORING       CLEAN CODE
        (Rajlich)       (Fowler)          (Martin)
              │               │               │
              │               │               │
              │       ┌───────┴───────┐       │
              │       v               v       │
              │     SOLID         GRASP / Clean
              │   (Martin)        Architecture
              │       │               │
              │       └───────┬───────┘
              │               │
              │               v
              │           TESTABILITY
              │      (SRP + DIP = mockable)
              │               │
              v               v
            TESTING        BDD
        (Beck, Dijkstra) (JGiven)
              │               │
              └───────┬───────┘
                      │
                      v
              CONCLUSION PHASE
            (commit, baseline, release)
                      │
                      v
              BEHAVIOURAL ANALYSIS
          (CodeScene, hotspots, Tornhill)
                      │
                      v
              WHERE TO APPLY
              ALL THE ABOVE
```

**Reading this diagram:** the *foundational* claims sit at the top. Lower nodes *operationalise* upper nodes. An essay that builds top-down explains *why* each lower concept exists.

---

## 4. Concrete cross-references — every lecture pair's intersection

The most useful table for an essay is *how lecture X relates to lecture Y*. Pick any two lectures and you have an essay section.

### Lecture 1 ↔ Lecture 11

- Both anchor in Lehman's laws.
- Lec 1 introduces the laws; Lec 11 *measures* them via hotspots.
- **Connection:** Lec 11 is Lec 1 with thirty years of empirical refinement.

### Lecture 2 ↔ Lecture 10

- Lec 2 introduces the phase model abstractly; Lec 10 applies it to Drawlets.
- **Connection:** Lec 10 is the *worked example* of Lec 2; together they bracket the course.

### Lecture 3 ↔ Lecture 11

- Lec 3's static impact analysis ↔ Lec 11's historical change coupling.
- **Connection:** *the same graph computed two ways*. Both views matter; the disagreement is informative.

### Lecture 4 ↔ Lecture 10

- Lec 4 introduces refactoring catalogue; Lec 10 quantifies the impact (13 → 5 classes).
- **Connection:** Lec 10 supplies the *evidence* for Lec 4's claim that refactoring shortens change propagation.

### Lecture 5 ↔ Lecture 7

- Lec 5's SOLID enables Lec 7's testability.
- Specifically: DIP enables the DateServer pattern; SRP enables small mocks.
- **Connection:** *testability is SOLID's operational form*.

### Lecture 6 ↔ Lecture 4

- Lec 6's Boy Scout Rule operationalises Lec 4's refactoring.
- Refactoring as scheduled work → Boy Scout Rule as continuous work.
- **Connection:** *Boy Scout Rule = refactoring without the scheduling overhead*.

### Lecture 7 ↔ Lecture 9

- Lec 7 introduces testing taxonomy; Lec 9 adds the acceptance / BDD layer.
- **Connection:** *BDD is the acceptance-test layer Lec 7 pointed at*.

### Lecture 9 ↔ Lecture 10

- Lec 9's JGiven + Mockito = Lec 10's recommended acceptance-test stack (slide 27).
- **Connection:** *textbook validation of practical tool choice*.

### Lecture 10 ↔ Lecture 11

- Lec 10's static phase model + Lec 11's behavioural measurement = the whole maintenance discipline.
- **Connection:** Lec 11 tells you *where* to apply Lec 10's phases.

---

## 5. The Group/Ungroup feature — the concrete spine

The author's feature is the *concrete* thread that runs through every lab and every essay section.

```
LAB 1: read JHotDraw
   │
   v
LAB 2: pick Group/Ungroup
       concept location (with backtrack)
   │
   v
LAB 3: impact set + CI workflow
   │
   v
LAB 4: prefactor GroupAction
       (Compose Method, dead code, comment)
   │
   v
LAB 5: SOLID audit
       (SRP/OCP violation in isGroupingAction)
   │
   v
LAB 7: 24 unit tests + 6 assertions
       (revealing the mockability tax)
   │
   v
LAB 9: 4 JGiven scenarios + 1 @Ignored AssertJ-Swing
       (BDD as living documentation)
```

**Every essay paragraph should be able to cite a point on this spine as a concrete example.**

---

## 6. The "magnification" pattern — how to use it in an essay

The pattern that makes a 15-20 page essay feel coherent rather than list-like:

**Open at high magnification (the foundational claim):**
> *"Software systems do not stand still. Lehman's two laws — Continuing Change and Increasing Complexity — establish the default trajectory of every working system."*

**Zoom in to the operational level:**
> *"To push back against this default, the field has developed a phased model of change (Rajlich, 2012) and an associated set of practices: refactoring, SOLID, clean code, testing, and behavioural analysis."*

**Zoom in to the concrete example:**
> *"The author's own work on the JHotDraw drawing framework provides a complete pass through this model: from concept location of the Group / Ungroup feature (Lab 2) to its unit-tested and BDD-documented form (Labs 7 and 9)."*

**Zoom back out for the conclusion:**
> *"The Group / Ungroup feature is one instance of a general pattern: maintenance is not a phase that happens after development — it is the activity all post-deployment engineering consists of. Lehman's laws guarantee this; Rajlich's model schedules it; Lab 7 and Lab 9 demonstrate it."*

**This pattern — open wide, zoom in, zoom in again, zoom back out — is the structure that turns "facts about the course" into "a single argument."**

---

## 7. The "connection sentence" — your magic phrase

A connection sentence is the *transition* between sections. It is the most important sentence in each section because it makes the essay coherent.

**Templates:**

> *"If [claim of previous section] holds, then [claim of next section] follows because..."*

> *"The previous section established [X]. The natural next question is [Y], which is the subject of this section."*

> *"This is consistent with [X from earlier], but extends it in one important respect..."*

> *"Where [earlier section] focused on [Z], the present section examines..."*

> *"This raises a subtle issue, which [next section] addresses..."*

**Example in context (transition from Refactoring section to Testing section):**

> *"Refactoring without tests is gambling — every restructuring may have silently broken behaviour the developer cannot detect. The next section therefore turns to the role of testing in making refactoring safe."*

This sentence does three things:
1. Restates the prior section's takeaway.
2. Identifies the *gap* that motivates the next section.
3. Forecasts the next section's claim.

**Drop one of these between every major section. Your essay will read as one continuous argument.**

---

## 8. The lecture-to-lab mapping (for case-study sections)

| If your essay section discusses... | ...your case-study evidence is... |
|---|---|
| Phased model / concept location | Lab 2 (Group/Ungroup selection + backtracking) |
| Impact analysis / CI | Lab 3 (GitHub Actions + impact set table) |
| Refactoring | Lab 4 (Compose Method, dead-code removal) |
| SOLID / clean architecture | Lab 5 (SRP/OCP audit of `isGroupingAction`) |
| Unit testing | Lab 7 (24 tests, 6 assertions, mockability tax) |
| BDD / acceptance testing | Lab 9 (4 JGiven scenarios, AssertJ-Swing) |
| Clean code | Lab 4's comment removal, Lab 7's intention-revealing test names |
| Technical debt / hotspots | Lab 7 reflection on which JHotDraw files are likely hotspots |

**For every essay section, the corresponding lab is your "but here is a concrete instance" paragraph.**

---

## 9. The "argument" vs "list" diagnostic

How to check (before submitting) whether your essay reads as an *argument* or a *list*:

**It's a list if:**
- Section headings could be in any order.
- Each section starts fresh ("In this section we will discuss...").
- The intro and conclusion don't refer to each other.
- Sections don't reference each other.

**It's an argument if:**
- Section order is forced by the argument's logic.
- Each section begins by referring to what came before.
- The conclusion explicitly restates the thesis from the intro.
- Sections frequently reference each other ("As shown in Section 2...").

**A 15-20 page essay must be the second.** Use the connection-sentence template (section 7 above) on every transition.

---

## 10. The final coherence check

Before submitting, ensure your essay passes this five-question check:

1. **Does the intro state a thesis?** (Not a topic — a *claim* the essay defends.)
2. **Does each section advance the thesis?** (Not just relate to it.)
3. **Does the conclusion restate the thesis in new words?** (Not paste from the intro.)
4. **Do the sections reference each other?** (At least 2-3 cross-references in a 15-20 page essay.)
5. **Could you state the thesis in one sentence to someone unfamiliar with the course?** (If not, the thesis is too narrow or too broad.)

If all five answers are *yes*, you have an argument, not a list.

---

**The most important phrase in this aid file:** *"As established in Section X, ..."*

Use it at least three times in the body of the essay. Each occurrence ties a current claim to a prior one. Each occurrence pushes the essay toward *argument* and away from *list*.

---

**Now:** open file 01 again and start the essay.

The aids are a circle: you began at 01 (essay craft), used 02-07 as the material library, and end at 08 (this file) to weave the material into a coherent shape. Then back to 01 for the actual writing.
