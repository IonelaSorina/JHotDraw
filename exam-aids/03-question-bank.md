# 03 — Question Bank

30+ anticipated exam questions with full outlines. Each outline is a 15-20 page essay skeleton you can adapt in minutes.

> **How to use:** Read the exam question. Scan the table of contents below. If a question matches or is close, copy its outline and adapt. If none matches, find two whose outlines you can combine.

---

## Table of contents

### Foundational
- [Q1. What is software maintenance and why does it matter?](#q1-what-is-software-maintenance-and-why-does-it-matter)
- [Q2. Explain Lehman's laws and their relevance to modern software engineering.](#q2-explain-lehmans-laws-and-their-relevance-to-modern-software-engineering)
- [Q3. Describe Brooks's essential difficulties.](#q3-describe-brookss-essential-difficulties)

### Phased model
- [Q4. Explain Rajlich's phased model of software change.](#q4-explain-rajlichs-phased-model-of-software-change)
- [Q5. Describe concept location with a concrete example.](#q5-describe-concept-location-with-a-concrete-example)
- [Q6. Explain impact analysis and its role in safe change.](#q6-explain-impact-analysis-and-its-role-in-safe-change)
- [Q7. What is the Conclusion phase and why is it important?](#q7-what-is-the-conclusion-phase-and-why-is-it-important)

### Refactoring
- [Q8. Define refactoring and discuss its role in maintenance.](#q8-define-refactoring-and-discuss-its-role-in-maintenance)
- [Q9. Compare prefactoring and postfactoring.](#q9-compare-prefactoring-and-postfactoring)
- [Q10. Discuss how refactoring shortens change propagation.](#q10-discuss-how-refactoring-shortens-change-propagation)

### SOLID and architecture
- [Q11. Explain the SOLID principles with examples.](#q11-explain-the-solid-principles-with-examples)
- [Q12. How does SOLID enable testability?](#q12-how-does-solid-enable-testability)
- [Q13. Discuss Clean Architecture and the Dependency Rule.](#q13-discuss-clean-architecture-and-the-dependency-rule)

### Clean Code
- [Q14. What makes code "clean"? Defend a definition.](#q14-what-makes-code-clean-defend-a-definition)
- [Q15. Discuss the role of comments in clean code.](#q15-discuss-the-role-of-comments-in-clean-code)
- [Q16. Explain the Boy Scout Rule and its operational significance.](#q16-explain-the-boy-scout-rule-and-its-operational-significance)

### Testing
- [Q17. Discuss the role of testing in software maintenance.](#q17-discuss-the-role-of-testing-in-software-maintenance)
- [Q18. Explain why testing is incomplete (Turing / Dijkstra).](#q18-explain-why-testing-is-incomplete-turing--dijkstra)
- [Q19. Compare unit testing, integration testing, and system testing.](#q19-compare-unit-testing-integration-testing-and-system-testing)
- [Q20. Discuss assertions in production code.](#q20-discuss-assertions-in-production-code)
- [Q21. Explain TDD and discuss whether it is always appropriate.](#q21-explain-tdd-and-discuss-whether-it-is-always-appropriate)
- [Q22. Compare mocks, stubs, and spies.](#q22-compare-mocks-stubs-and-spies)
- [Q23. Discuss the Fragile Test Problem.](#q23-discuss-the-fragile-test-problem)

### BDD
- [Q24. Compare unit testing and behaviour-driven testing.](#q24-compare-unit-testing-and-behaviour-driven-testing)
- [Q25. Discuss "living documentation" as a property of BDD.](#q25-discuss-living-documentation-as-a-property-of-bdd)
- [Q26. Compare classical and developer-friendly BDD frameworks.](#q26-compare-classical-and-developer-friendly-bdd-frameworks)

### Technical debt
- [Q27. Define technical debt and discuss its usefulness.](#q27-define-technical-debt-and-discuss-its-usefulness)
- [Q28. Compare static and behavioural code analysis.](#q28-compare-static-and-behavioural-code-analysis)
- [Q29. Discuss hotspots and their predictive power.](#q29-discuss-hotspots-and-their-predictive-power)
- [Q30. Discuss the social dimension of technical debt (legacy, off-boarding).](#q30-discuss-the-social-dimension-of-technical-debt-legacy-off-boarding)

### Synthesis
- [Q31. Apply the phased model to a real change you have made.](#q31-apply-the-phased-model-to-a-real-change-you-have-made)
- [Q32. Discuss the relationship between maintenance, evolution, and design.](#q32-discuss-the-relationship-between-maintenance-evolution-and-design)
- [Q33. What is the single most important lesson from the course?](#q33-what-is-the-single-most-important-lesson-from-the-course)

---

## Foundational

### Q1. What is software maintenance and why does it matter?

**Type:** descriptive + evaluative.

**Thesis:** Software maintenance is the dominant activity in any working software system — not its inception. Defending this requires showing both the *empirical* dominance of maintenance and the *theoretical* reasons it cannot be eliminated.

**Outline:**

1. **Intro** — software doesn't stand still; deployed code lives, ages, and dies.
2. **Empirical evidence** — Lientz-Swanson percentages (80% of TCO), CHAOS report on project outcomes, Drawlets baseline (Rajlich 2012) showing 1.4 test-line / production-line cost ratio.
3. **Theoretical justification** — Lehman's laws (continuing change, increasing complexity); Brooks's essential difficulties make change unavoidable.
4. **The phased model** as a working schedule for maintenance (Rajlich's seven phases).
5. **Case study** — author's JHotDraw work: Group/Ungroup feature traced through Initiation → Concept Location → Impact Analysis → Prefactoring → Actualization → Verification.
6. **Counter-arguments** — DevOps / continuous deployment blurs maintenance and development. Discuss whether this dissolves the distinction or just rebadges it.
7. **Conclusion** — maintenance is the activity software engineering is *actually* paid for.

**Key citations:** Lientz & Swanson 1980; Brooks 1975; Lehman 1980; Rajlich 2012.

**Key numbers:** ~80% of TCO is maintenance; Drawlets 1.4 ratio; Lientz-Swanson percentages.

---

### Q2. Explain Lehman's laws and their relevance to modern software engineering.

**Type:** descriptive + analytical.

**Thesis:** Lehman's empirical laws (1980) describe the *default trajectory* of software systems. The two most-cited — Continuing Change and Increasing Complexity — are the foundation on which modern refactoring, testing, and behavioural-analysis practices stand.

**Outline:**

1. **Intro** — Lehman as the empirical anchor for software-engineering's softer claims.
2. **Continuing Change:** *"A system must be continually adapted or it becomes progressively less satisfactory."*
   - Implication: a *finished* system is one already decaying.
   - Connection to Lientz-Swanson's "Adaptive" maintenance.
3. **Increasing Complexity:** *"As a system evolves, its complexity increases unless work is done to maintain or reduce it."*
   - The *unless work is done* clause is the load-bearing part.
   - Complexity is the *null hypothesis*.
4. **Modern echoes:**
   - Hickey's Easy vs Simple curve (2011): if you ignore complexity, you slow down.
   - Tornhill's hotspots (2018): complexity × change frequency = where the cost lives.
   - Martin's Boy Scout Rule (2009): the *active counter-force*.
5. **Case study** — JHotDraw is a Lehman case study in slow motion: 8 years of no refactoring → the dead code, mockability tax, and `XXX` comments found in Lab 4 and Lab 7.
6. **Critical evaluation** — Lehman's laws are descriptive, not prescriptive. They tell us what happens *by default*, not what *should* happen. The course's value is in giving the disciplines that push back.
7. **Conclusion** — without Lehman's laws, refactoring is optional cleanup. With them, refactoring is the *mandatory* counter-force to entropy.

**Key citations:** Lehman 1980; Hickey 2011; Tornhill 2018; Martin 2009.

---

### Q3. Describe Brooks's essential difficulties.

**Type:** descriptive + analytical.

**Thesis:** Brooks identifies four (lecture adds a fifth) properties intrinsic to software that no methodology can fully eliminate. Understanding these difficulties is the precondition for setting realistic expectations of any maintenance activity.

**Outline:**

1. **Intro** — Brooks 1975, *No Silver Bullet*. The distinction between *essential* (intrinsic) and *accidental* (tooling) difficulties.
2. **Complexity** — software handles many more cases than textbook examples. JHotDraw's figure tree is the example.
3. **Invisibility** — code has no physical form; UML/call-graphs/test output are partial views.
4. **Changeability** — no cost-of-concrete; software is tempting to constantly change. This compounds complexity.
5. **Conformity** — software must conform to OS, hardware, user expectations; those environments move independently.
6. **(Discontinuity — lecture's fifth)** — small changes can have disproportionate effects.
7. **Why "essential":** these arise from software's nature, not poor tooling. No methodology eliminates them.
8. **Case study** — JHotDraw's JDK 25 + JGiven 1.3.1 incompatibility (Lab 9) = conformity in action. The fix (`--add-opens`) is engineering response; the difficulty itself is essential.
9. **Conclusion** — accepting Brooks means accepting that maintenance is a *permanent* activity, not a *temporary* repair.

**Key citations:** Brooks 1975; Brooks 1986 (*No Silver Bullet*).

---

## Phased model

### Q4. Explain Rajlich's phased model of software change.

**Type:** descriptive + applied.

**Thesis:** The phased model is not bureaucracy. It is the *rhythm* every change in a codebase follows whether the engineer notices or not. The model's value lies in *making the engineer notice*.

**Outline:**

1. **Intro** — Rajlich 2012, the phase diagram.
2. **The seven phases** in order:
   - **Initiation** — change request received and scoped.
   - **Concept Location** — find the code that implements the affected concept.
   - **Impact Analysis** — determine what else is affected.
   - **Prefactoring** — clean the affected code *before* the change.
   - **Actualization** — make the change.
   - **Postfactoring** — clean up duplication introduced by the change.
   - **Conclusion** — commit, baseline, release.
3. **Verification** spans Prefactoring → Conclusion (right-hand side of the V).
4. **Why phases matter (theoretical):**
   - Each phase is a *checkpoint* for engineering judgement.
   - Skipping a phase moves its work to a later phase, where it costs more.
   - The model is *prescriptive* (do it in this order) and *descriptive* (this is what happens anyway).
5. **Case study** — JHotDraw Group/Ungroup feature mapped phase-by-phase:
   - Lab 2 = Initiation + Concept Location.
   - Lab 3 = Impact Analysis + CI.
   - Lab 4 = Prefactoring.
   - Lab 5 = SOLID audit (Actualization preparation).
   - Lab 7 = Verification (unit tests).
   - Lab 9 = Verification (BDD scenarios).
   - All commits on `alex` branch = Conclusion.
6. **Limitation** — Agile / DevOps continuous-deployment workflows blur the phase boundaries. Discussion: does this *replace* the model or *compress* it?
7. **Conclusion** — the phase model is the spine of the course and the practical schedule for any sane maintenance work.

**Key citations:** Rajlich 2012, ch. 2; Lecture 2 slides.

---

### Q5. Describe concept location with a concrete example.

**Type:** descriptive + applied.

**Thesis:** Concept location is the act of traversing from a concept (in user/domain language) to its implementation (in code). It is *iterative*, *backtracking*, and the most error-prone phase of the model.

**Outline:**

1. **Intro** — Rajlich's concept triangle (Concept ↔ Words ↔ Code).
2. **Strategies:**
   - **SUR** (Search Using Regexp) — grep for keywords.
   - **SUL** (Search Using Links) — follow the call graph.
   - **SUL3** — SUL extended to 3 levels of indirection.
3. **The trial-and-error nature** — Rajlich (Lec 10) shows three diagrams of the same case: *Wrong way → Backtrack → Right way*. This is normal, not a failure.
4. **Case study — Group/Ungroup in JHotDraw (Lab 2):**
   - Started with regex search for "group".
   - First path: through `Toolbar`-related Action infrastructure → dead end.
   - Backtracked.
   - Right path: `GroupAction` → `GroupFigure` extends `AbstractCompositeFigure`.
   - Found three concepts: *group* (action), *group figure* (data), *ungroup* (inverse).
5. **Concept classification (Lec 10):** label each noun in the change request as Irrelevant / External / Significant. *"figure"* and *"canvas"* significant; *"ID"*, *"password"* external; *"implement"*, *"allowed"* irrelevant.
6. **Why concept location is critical** — if you miss a concept, the impact set is wrong, the change is incomplete.
7. **Conclusion** — concept location is the *concept-to-code* dictionary the engineer must compile before every change.

**Key citations:** Rajlich 2012 ch. 4; Lecture 2; Lab 2 portfolio entry.

---

### Q6. Explain impact analysis and its role in safe change.

**Type:** analytical + applied.

**Thesis:** Impact analysis is the *prediction* of which code will require modification. It can be computed *statically* (read the call graph) or *historically* (read the git log). Both views are partial; both are necessary.

**Outline:**

1. **Intro** — once a concept is located, what else does the change affect?
2. **The impact set:**
   - Direct impact set — what is literally edited.
   - Indirect impact set — what depends on what is edited.
3. **Static impact analysis** — read the call graph. Catches *possible* but not *probable* impacts.
4. **Historical impact analysis (Tornhill, Lec 11)** — git log reveals *change coupling*. Files that always commit together are coupled, even if the call graph doesn't show it.
5. **The disagreement is informative:**
   - Coupled in history but not in call graph = hidden coupling (shotgun surgery).
   - Coupled in call graph but not in history = possibly dead code.
6. **Case study (Lab 3):** Group/Ungroup impact set:
   - Direct: `GroupAction`, `UngroupAction`.
   - Indirect: `AbstractSelectedAction` (superclass), `GroupFigure`, `AbstractCompositeFigure`, `DefaultDrawing` (caller).
7. **Mechanising the verification:** GitHub Actions CI on every PR. *The build is the impact-set check.*
8. **Change propagation (Lec 10):** Rajlich's *temporal* visualisation — red → orange → green → grey. The impact set *moves over time* as edits propagate.
9. **Conclusion** — impact analysis is the bridge between concept location (what is touched) and prefactoring (how to make the touch local).

**Key citations:** Rajlich 2012 ch. 6; Tornhill 2018; Graves et al. 2000.

---

### Q7. What is the Conclusion phase and why is it important?

**Type:** descriptive + analytical.

**Thesis:** The Conclusion phase is the *social* and *organisational* end of the change lifecycle. Most of the course's technical content is upstream of Conclusion; most of the *career-relevant* practice lives in it.

**Outline:**

1. **Intro** — Rajlich 2012 ch. 11, the bottom of the phase diagram.
2. **The three steps:** Commit → New Baseline → New Release.
3. **Commit:** programmers return updated code, resolve conflicts. Mechanised by git + pull requests + CI.
4. **New Baseline:**
   - Thorough testing → certified known-good state.
   - "Often done overnight or over the weekend; a specialised testing team conducts testing."
   - Baseline = repository state after a green CI run.
5. **New Release:**
   - Substantial extra work — packaging, release notes, distribution.
   - Frequency = business decision, not technical.
   - Large + small releases (patches via *merge*).
6. **Baseline as deadline (social):**
   - Deadline to commit = time when baseline testing starts.
   - Miss = additional work + management notices.
   - Reputation accrues.
7. **Bugs in baseline:**
   - Minor → certify anyway, add to backlog.
   - Serious → reject commits, possibly reject whole baseline.
8. **Stakeholder role:** acceptance testing by stakeholders gates the release.
9. **Why "important":** every technical practice in the course is *evaluated* at Conclusion. Tests run here. Refactoring is checked here. The build is green or it isn't.
10. **Case study:** the author's GitHub Actions CI (Lab 3) is the Conclusion mechanism for the JHotDraw fork.
11. **Conclusion** — Conclusion is where engineering becomes *engineering* — i.e., where the code is judged against reality.

**Key citations:** Rajlich 2012 ch. 11; Lecture 10 slides.

---

## Refactoring

### Q8. Define refactoring and discuss its role in maintenance.

**Type:** definitional + evaluative.

**Thesis:** Refactoring is not optional cleanup. It is the structural precondition that makes every other phase of the change lifecycle feasible.

**Outline:**

1. **Definition (Fowler 1999):** changing the *structure* of code without changing its *behaviour*.
2. **Why refactor:**
   - Make code easier to understand.
   - Make change easier to localise.
   - Reduce future cost of inevitable change (Lehman).
3. **The phased-model positions of refactoring:**
   - Prefactoring (before change).
   - Postfactoring (after change).
   - **Twice in the model** for a reason.
4. **The Fowler catalogue:** Compose Method, Extract Method, Replace Conditional with Polymorphism, Move Method, Introduce Parameter Object, Replace Magic Number with Symbolic Constant.
5. **Empirical evidence (Lec 10, slide 37):**
   - No refactoring = 13 classes modified.
   - Splitting roles = 5 classes — **62% reduction.**
   - LOC modified barely changes — refactoring reduces *scattering*.
6. **Case study (Lab 4):** Compose Method on `GroupAction.actionPerformed`:
   - 67-line method → multiple methods of 10-15 lines each.
   - Extracted `performGroup`, `performUngroup`, `getLabels()`.
   - Removed dead `prototype` field and stale `// XXX` comment.
7. **Refactoring requires tests** — Lab 4 deferred *Replace Conditional with Polymorphism* because no tests existed. Lab 7 added tests; the refactoring is now safe to do.
8. **Counter-argument:** "if it ain't broke, don't fix it." Rebut with Lehman: complexity rises by default; *not refactoring* is a choice that compounds.
9. **Conclusion** — refactoring is the active counter-force to Lehman's Increasing Complexity.

**Key citations:** Fowler 1999; Kerievsky 2004; Rajlich 2012, ch. 7+17; Lehman 1980.

---

### Q9. Compare prefactoring and postfactoring.

**Type:** comparative.

**Thesis:** Prefactoring and postfactoring are not redundant. They serve different goals at different points in the change lifecycle, and the phased model places them at both ends for that reason.

**Outline:**

1. **Intro** — refactoring sits at two phases in the model.
2. **Comparison table:**

   | | Prefactoring | Postfactoring |
   |---|---|---|
   | **When** | Before the change | After the change |
   | **Goal** | Make the change localised | Remove duplication introduced by the change |
   | **Trigger** | Concept location found a tangled implementation | Actualization left a smell |
   | **Risk** | Refactor + change conflated | Less risk (change is done) |
   | **Example** | Compose Method on `GroupAction` before adding a new action type | Extract a helper after copy-pasting code into two new classes |

3. **Why both matter:**
   - Prefactoring without postfactoring → the change is local but leaves residue.
   - Postfactoring without prefactoring → the change is tangled in old smells.
4. **Case study:** Lab 4 = prefactoring (Compose Method, dead-code removal). Postfactoring would happen after a future "add LineGroup action" feature — by which time the helpers extracted in Lab 4 may need further splitting.
5. **Counter-argument:** "refactor everything continuously, don't separate them." Rebut: the phased model separates them because *context matters* — prefactoring is bounded by the change scope; postfactoring is bounded by the smells the change introduced.
6. **Conclusion** — the two halves of refactoring are not duplication but *bracketing*.

---

### Q10. Discuss how refactoring shortens change propagation.

**Type:** analytical + applied.

**Thesis:** Refactoring's value is measurable: it reduces the *number of classes affected by future changes*, not the total amount of code written.

**Outline:**

1. **Intro** — Rajlich's worked example on Drawlets (Lec 10, slide 37).
2. **The data:**
   - No refactoring: 13 classes modified, 91 LOC modified.
   - Move function refactoring: 8 classes, 95 LOC.
   - Splitting roles: 5 classes, 87 LOC.
3. **The insight:** LOC barely moves. **Refactoring reduces *scattering*, not *amount*.**
4. **Two mechanisms:**
   - **Move function** — code that was duplicated in N subclasses moves into the base class. N edits → 1 edit.
   - **Splitting roles** — one method serving two roles is split into two methods serving one role each. Only one needs updating.
5. **Connection to change coupling (Lec 11):** refactoring reduces *historical* change coupling by reducing *static* coupling.
6. **Case study:** the deferred *Replace Conditional with Polymorphism* refactor in `GroupAction.isGroupingAction`. Currently, adding a third action type requires touching the dispatch in two places. After the refactor, it requires zero touches in `GroupAction`.
7. **Conclusion** — refactoring is the *amortisation* of future change cost.

**Key citations:** Rajlich 2012 ch. 17; Lec 10 slide 37.

---

## SOLID and architecture

### Q11. Explain the SOLID principles with examples.

**Type:** descriptive + applied.

**Thesis:** SOLID is five rules that, together, define a class as a *small, focused, replaceable unit*. The course's testing labs depend on every one of them.

**Outline:**

1. **Intro** — Martin 2003, SOLID as the OO design heuristic.
2. **Each principle in turn:**

   - **S — Single Responsibility:** *one and only one reason to change.*
     - Example: `GroupAction` violates SRP — it does both grouping and ungrouping (dispatched via `isGroupingAction`).

   - **O — Open / Closed:** *open for extension, closed for modification.*
     - Example: the same `isGroupingAction` dispatch also violates OCP. Adding a new action type requires modifying `GroupAction`.

   - **L — Liskov Substitution:** *subtypes must be substitutable for their base types.*
     - Example: `UngroupAction extends GroupAction` is correct only because `UngroupAction` constrains rather than expands behaviour.

   - **I — Interface Segregation:** *clients should not depend on methods they don't use.*
     - Example: `DrawingView` has 100+ methods; `GroupAction` uses ~10. A `Selectable` sub-interface would honour ISP.

   - **D — Dependency Inversion:** *depend on abstractions, not concretions.*
     - Example: Lec 7's DateServer pattern — wrap `new GregorianCalendar()` in an injectable interface. Lab 7's `DrawingEditor`/`DrawingView` are already DIP-compliant (interfaces).

3. **Why all five together:** each principle reinforces the others. Small (S) classes are easier to substitute (L). Open (O) hierarchies require dependency on abstractions (D).
4. **Testing connection:** SOLID = testability. Mockable code is SOLID code.
5. **Conclusion** — SOLID is not OO ceremony; it is the architectural form of *changeable code*.

**Key citations:** Martin 2003; Liskov 1987.

---

### Q12. How does SOLID enable testability?

**Type:** analytical.

**Thesis:** Testability is not an extra property added to good design; it *is* good design. Each SOLID principle removes a specific obstacle to writing fast, isolated, deterministic tests.

**Outline:**

1. **Intro** — Lab 7's mockability tax as the negative example.
2. **SRP → small mocks:** a class with one responsibility has a small surface to mock.
3. **OCP → no test rewrites:** extending behaviour by adding subclasses doesn't break existing tests.
4. **LSP → polymorphic tests:** a test written against the base type works for every subclass.
5. **ISP → minimal mock setup:** if a class depends on a 4-method interface instead of a 40-method one, the mock setup is 10× smaller.
6. **DIP → mockable seams:** the DateServer pattern — injectable abstractions are mockable; `new GregorianCalendar()` is not.
7. **Counter-example from Lab 7:** `GroupAction.canUngroup` uses `selectedFigure.getClass().equals(prototype.getClass())`. `Object#getClass()` is final → cannot be stubbed by Mockito → the *mockability tax*. This violates DIP (depending on concrete `Class` identity). The fix = *Splitting Roles* (Lec 10) → introduce `prototype.matches(figure)` query → mockable.
8. **Conclusion** — testability is the *automated* test of whether your design is SOLID. If a method is hard to test, the code is hard to change.

**Key citations:** Martin 2003; SB5-MAI Lec 5, Lec 7.

---

### Q13. Discuss Clean Architecture and the Dependency Rule.

**Type:** descriptive + analytical.

**Thesis:** Clean Architecture's single hard rule — source code dependencies point inward — is the architectural form of the Dependency Inversion Principle, and it is the property that makes large systems sustainable.

**Outline:**

1. **Intro** — Martin 2017, the concentric layers diagram.
2. **The layers:**
   - Entities (innermost) — domain rules.
   - Use Cases — application-specific business rules.
   - Interface Adapters — controllers, presenters.
   - Frameworks & Drivers (outermost) — web, DB, UI.
3. **The dependency rule:** source code dependencies must point *inward*.
4. **Why this rule:**
   - Outer layers can be replaced without touching inner.
   - Tests can mock outer layers without touching inner.
   - Frameworks become *plug-ins*, not the application itself.
5. **Case study:** JHotDraw's `Drawing` interface (inner) vs `DefaultDrawing` implementation (outer). `GroupAction` depends on `Drawing` (inner) — correct. If it depended on `DefaultDrawing`, it would violate Clean Architecture.
6. **Connection to SOLID:** Clean Architecture is DIP at scale.
7. **Counter-argument:** "for a small project, this is overkill." Concede: yes, the cost is upfront. But the *benefit* (sustainable change) compounds with size, which is precisely the case for legacy maintenance.
8. **Conclusion** — the Dependency Rule is the single architectural insight that distinguishes 10-year-old codebases that are usable from those that are not.

**Key citations:** Martin 2017; SB5-MAI Lec 5.

---

## Clean Code

### Q14. What makes code "clean"? Defend a definition.

**Type:** definitional + evaluative.

**Thesis:** "Clean code" is not a single property but the *intersection* of multiple practitioner definitions: it does one thing well (Stroustrup), reads like prose (Booch), and meets the reader's expectations (Cunningham).

**Outline:**

1. **Intro** — Martin 2009 collects definitions from six authorities.
2. **The six definitions** (cite verbatim — see Quote Bank):
   - Stroustrup: elegant + efficient, does one thing well.
   - Booch: reads like well-written prose.
   - Thomas: literate.
   - Feathers: written by someone who cares.
   - Jeffries: reduced duplication, expressiveness, simple abstractions.
   - Cunningham: meets reader expectations.
3. **What these have in common:**
   - **Clarity of intent** (Stroustrup, Booch, Thomas).
   - **Care** (Feathers).
   - **Minimalism** (Jeffries, Cunningham).
4. **Operational tests:**
   - WTFs/minute (Holwerda).
   - The Boy Scout Rule (Martin).
5. **Negative space:** clean code is *not* clever, not dense, not "optimised for the writer." Knuth: "premature optimisation is the root of all evil."
6. **Case study:** `GroupAction` before Lab 4 vs after. The 67-line `actionPerformed` did multiple things; the post-refactor versions read top-to-bottom (Stepdown Rule).
7. **Counter-argument:** "clean is subjective." Concede partially: yes, taste varies. But *intersubjective* clarity (would another engineer find it readable?) is testable.
8. **Conclusion** — clean code is the property that makes a codebase *maintainable* — which is what 80% of software work consists of.

**Key citations:** Martin 2009 ch. 1; Stroustrup; Booch; Cunningham; Holwerda.

---

### Q15. Discuss the role of comments in clean code.

**Type:** evaluative.

**Thesis:** Martin's position — *"comments are failures"* — is correct in spirit but overstated. Comments are appropriate in narrow categories; the test is whether the comment *could be replaced by clearer code*.

**Outline:**

1. **Intro** — Martin 2009 ch. 4.
2. **Martin's two foundational rules:**
   - Don't comment bad code — rewrite it.
   - Explain yourself in code.
3. **The good list:** legal, informative, intent, clarification, amplification, public API javadocs.
4. **The bad list:** mumbling, redundant, mandated, journal, noise, position markers, closing-brace, attributions, commented-out code.
5. **The test:** can a comment be replaced by an extracted method or variable?
   - `// check eligibility` → `if (employee.isEligibleForFullBenefits())`.
6. **Case study (Lab 4):** removed the stale `// XXX - This code is redundant with UngroupAction` comment from `GroupAction`. The comment had outlived the redundancy it described.
7. **Counter-argument:** *intent* comments are sometimes necessary (the *why*, not the *what*). Concede: yes, these are on the good list. But the burden of justification falls on the comment, not on its absence.
8. **Conclusion** — comments are a tax. Each comment is rent paid for code that couldn't speak. Good code minimises rent.

**Key citations:** Martin 2009 ch. 4.

---

### Q16. Explain the Boy Scout Rule and its operational significance.

**Type:** descriptive + analytical.

**Thesis:** The Boy Scout Rule — *leave the code cleaner than you found it* — is the operational form of Lehman's Increasing Complexity. It is the smallest possible move that bends the entropy curve.

**Outline:**

1. **Intro** — Martin 2009; the rule borrowed from scouting.
2. **The rule:** every time you visit a file, leave it fractionally cleaner.
3. **Why "smallest move":**
   - Big refactoring projects are political (scheduling, sign-off).
   - Small in-passing cleanups are unpolitical.
   - Sum of small cleanups = big refactoring, without the politics.
4. **Mathematical framing:** Lehman's complexity rises at rate `+k` per change. Boy Scout cleanups subtract `-ε` per visit. If `Σε > Σk`, complexity *decreases* over time.
5. **Tornhill connection (Lec 11):** the rule is more powerful when targeted at hotspots — visit the hotspot anyway, clean it then.
6. **Case study (Lab 4):** the dead `prototype` field in `UngroupAction` was a Boy Scout cleanup. Took 2 minutes. Removed an entire class of future confusion.
7. **Counter-argument:** "this scope-creeps every PR." Rebut: scope creep is a process problem (PR size limits), not a Boy-Scout-Rule problem.
8. **Conclusion** — the Boy Scout Rule is what separates codebases that get better over time from those that decay.

**Key citations:** Martin 2009 ch. 1.

---

## Testing

### Q17. Discuss the role of testing in software maintenance.

**Type:** evaluative.

**Thesis:** Tests are not a deliverable; they are an *asset*. They make refactoring safe, document behaviour, and constitute the only durable proof that a change is correct.

**Outline:**

1. **Intro** — the testing arc of the course (Lec 7 + Lec 9 + Labs 7, 9).
2. **What tests do:**
   - Pin behaviour (regression detection).
   - Document expected behaviour (executable spec).
   - Enable refactoring (the safety net for Boy Scout cleanups).
   - Communicate to future maintainers.
3. **The four-layer test pyramid:**
   - Unit tests (Lab 7).
   - Integration tests.
   - BDD / acceptance tests (Lab 9).
   - End-to-end GUI tests (Lab 9's `@Ignore`d AssertJ-Swing).
4. **Empirical evidence (Lec 10):**
   - Rajlich's Drawlets: 1.4 test lines per production line.
   - 27% test-to-production ratio in baseline.
   - 385 unit + 141 functional tests on a 40,000 LOC codebase.
5. **Testing incompleteness (Lec 7):** Turing/Dijkstra — tests show presence of bugs, not absence. Tests are a *floor*, not a ceiling.
6. **Case study:** Lab 7's 24 unit tests pin the Group/Ungroup feature's `canGroup`, `canUngroup`, `groupFigures`, `ungroupFigures`. Lab 9's 4 BDD scenarios pin the user-facing contract.
7. **Counter-argument:** "tests slow down development." Rebut with Rajlich: 1.4 test lines per production line is the steady-state cost. The *alternative* (slow regression) is 10× more expensive at the 1-year mark.
8. **Conclusion** — without tests, every refactoring is gambling.

**Key citations:** Beck 1999; Rajlich 2012 ch. 17; Dijkstra 1972.

---

### Q18. Explain why testing is incomplete (Turing / Dijkstra).

**Type:** theoretical + descriptive.

**Thesis:** Testing's incompleteness is a *theorem*, not a tooling limitation. Recognising this is the precondition for sane testing practice.

**Outline:**

1. **Intro** — Lec 7 opens with Turing's halting problem.
2. **The halting problem:** no general program decides whether another program halts.
3. **Rice's theorem (implicit in lecture):** by extension, no general program decides arbitrary non-trivial semantic properties — including "is this code correct?"
4. **Dijkstra dictum (1972):** *"Testing can demonstrate the presence of bugs, but not their absence."*
5. **Operational implications:**
   - No test suite proves correctness.
   - Tests demonstrate the *absence of specific bugs* at a specific moment.
   - Coverage is a proxy, not a guarantee.
6. **What good tests achieve:**
   - Pin specific behaviours.
   - Cover boundary conditions (Lab 7's empty-selection, single-figure, no-active-view cases).
   - Document expected behaviour.
7. **Case study:** Lab 7 has 24 tests demonstrating 24 specific behaviours of `GroupAction`. None of them proves `GroupAction` is correct. Together they make accidental regression unlikely.
8. **Connection to assertions (Lec 7):** assertions extend the safety net into production — invariants that *would never happen* but are checked anyway.
9. **Counter-argument:** "formal methods can prove correctness." Concede: yes, for narrow domains (avionics, OS kernels). Not for general application code.
10. **Conclusion** — accepting Dijkstra is the precondition for *humble* testing — the kind that actually catches bugs.

**Key citations:** Turing 1936; Dijkstra 1972; SB5-MAI Lec 7.

---

### Q19. Compare unit testing, integration testing, and system testing.

**Type:** comparative.

**Thesis:** The three test scopes form a *pyramid*: unit (broadest base) → integration (middle) → system (apex). Each scope answers a different question; the three are complementary, not interchangeable.

**Outline:**

1. **Intro** — Lec 7's six-way diagram.
2. **Comparison table:**

   | | Unit | Integration | System |
   |---|---|---|---|
   | **Scope** | One method / class | Two or more modules | Whole assembled system |
   | **Speed** | Milliseconds | Seconds | Minutes |
   | **Mocks** | All dependencies | Some real, some mock | None |
   | **Failure tells you** | This logic is broken | This wiring is broken | This product is broken |
   | **Example from labs** | Lab 7 `GroupActionTest` | Lab 9 `GroupUngroupScenarioTest` (real `DefaultDrawing` + mocked view) | Lab 9 `@Ignore`d AssertJ-Swing |

3. **Why the pyramid shape:**
   - Unit = many, fast, isolated → broad base.
   - System = few, slow, integrated → narrow apex.
   - Cost-of-running scales inversely with quantity.
4. **The fragile-test problem at higher levels** (Lec 7): system tests are sensitive to behaviour, interface, data, and context changes — all four dimensions. Unit tests sensitive to only behaviour + interface.
5. **Connection to BDD (Lec 9):** BDD scenarios *can* live at any level. Lab 9's JGiven scenarios are integration tests in scope (real Drawing + mocked View).
6. **Case study comparison:** the same Group/Ungroup behaviour tested at three layers — Lab 7's unit test (with mocked Drawing) + Lab 9's JGiven scenario (with real Drawing) + the `@Ignore`d AssertJ-Swing scenario (full system).
7. **Conclusion** — a healthy test suite has all three. Each catches a different failure mode.

**Key citations:** SB5-MAI Lec 7; Lab 7; Lab 9.

---

### Q20. Discuss assertions in production code.

**Type:** evaluative.

**Thesis:** Production assertions are an under-used safety net. Density data from major projects (GCC, LLVM) supports a target of ~1 per 110 LOC; most projects, including JHotDraw, are far below this.

**Outline:**

1. **Intro** — Lec 7's framing: assertions, assertions, assertions !!!
2. **What is an assertion:** executable check for an invariant. `assert condition : "message";` in Java.
3. **The three rules (Lec 7):**
   - R1: Not for error handling (use exceptions).
   - R2: NO SIDE EFFECTS.
   - R3: No silly assertions.
4. **Why they matter:**
   - Self-checking code.
   - Fail early, closer to the bug.
   - Document invariants in executable form.
5. **Production density:**
   - GCC: ~9000 assertions across ~7M LOC = 1 per ~800 LOC.
   - LLVM: ~13,000 across ~1.4M LOC = **1 per 110 LOC**.
6. **Disable in mission-critical?** Lec 7's nuance: disable when the cost of *failing* is worse than the cost of *continuing with potential corruption* (Mars Climate Orbiter, Rosetta lander).
7. **Case study (Lab 7):** added 6 production `assert` statements in `GroupAction.groupFigures` / `ungroupFigures`:
   - Non-null view, non-null group, non-empty figures.
   - Group must already belong to the drawing.
   - Density: 6 per 180 LOC = 1 per 30 LOC (denser than LLVM).
8. **Counter-argument:** "assertions in production are paranoid." Rebut with the *Disable Assertions?* analysis: even in production, failing early is usually safer than continuing in an undefined state.
9. **Conclusion** — assertions are the production-code form of the unit test. JHotDraw should have ~80x more of them than it currently does.

**Key citations:** SB5-MAI Lec 7; Lab 7 portfolio entry.

---

### Q21. Explain TDD and discuss whether it is always appropriate.

**Type:** descriptive + evaluative.

**Thesis:** TDD is a powerful design discipline for *new* code. It is less applicable — but still useful — for *legacy* code, where retroactive test addition (the author's Lab 7 mode) is the realistic alternative.

**Outline:**

1. **Intro** — Beck (2002) the *Test-Driven Development by Example* book.
2. **The three laws (Martin 2009 / Beck 2002):**
   1. No production code until you have a failing test.
   2. No more test than is sufficient to fail.
   3. No more production code than is sufficient to pass.
3. **The cycle:** Red → Green → Refactor. One test at a time.
4. **Benefits:**
   - Forces interface-before-implementation thinking.
   - Produces tests that *should* fail at first — proving coverage.
   - 100% coverage by construction.
5. **Real TDD vs Moving-to-TDD (Lec 7):**
   - Real TDD: developer's own QA loop. No separate QA gate.
   - Moving-to-TDD: tests written first, but still handed off to a QA team. Halfway state.
6. **Where TDD fails:**
   - Legacy code with no existing test infrastructure.
   - Spike / prototype code where the design isn't known.
   - GUI code where the test API doesn't exist yet (Lab 9's AssertJ-Swing problem).
7. **The author's Lab 7 mode:** *retroactive* test addition — write tests for code that already exists. Not TDD, but the realistic path for any legacy codebase.
8. **Counter-argument:** "TDD slows down development." Rebut with empirical evidence (Beck) — TDD's overhead is amortised by reduced bug-fix time at later stages.
9. **Conclusion** — TDD is the *right default* for new code. Retroactive testing is the *right default* for legacy code. Both are testing disciplines.

**Key citations:** Beck 2002; Martin 2009 ch. 9; SB5-MAI Lec 7.

---

### Q22. Compare mocks, stubs, and spies.

**Type:** comparative.

**Thesis:** The three test doubles are *not* interchangeable. Each fits a different testing question, and using the wrong one is a sign the test is asking the wrong question.

**Outline:**

1. **Intro** — Lec 7's taxonomy.
2. **Comparison table:**

   | | Stub | Mock | Spy |
   |---|---|---|---|
   | **Built from** | Predefined data | Auto-generated | Wraps a real object |
   | **Verifies?** | No | Yes (interactions) | Yes (selectively) |
   | **Weight** | Lightest | Middle | Heaviest |
   | **Use when** | You just need a return value | You care which methods got called | Most real behaviour is fine, you override one method |
   | **Mockito** | `Mockito.mock` + `when().thenReturn()` (no `verify`) | `Mockito.mock` + `verify(...)` | `Mockito.spy(realObject)` |

3. **The Lab 7 confession:** the author used Mockito's `mock()` everywhere, never `stub` or `spy()`. Re-reading through the taxonomy:
   - `editor`, `view`, `drawing` are mocks (verified with `InOrder`).
   - A *stub* would have been sufficient for tests that never call `verify`.
   - A *spy* on a real `GroupFigure` would have fixed the *mockability tax* (preserve `getClass()`, override `clone()`).
4. **Architectural implication:** if you need a Mock, your test cares about the *interaction*. If you need a Stub, your test cares about the *return value*. Knowing which you need clarifies what you're testing.
5. **The DateServer pattern (Lec 7):** uses a stub (returns fixed date). Not a mock — the test doesn't verify that `getDate()` was called, only that the result is correct given a fixed date.
6. **Counter-argument:** "stub vs mock is academic." Rebut: the distinction matters when test failures need diagnosis — a mock failure says *the interaction was wrong*; a stub failure says *the SUT mis-used the stubbed value*.
7. **Conclusion** — Stub / Mock / Spy is not a vocabulary lesson. It is a *diagnostic* lesson — picking the right double clarifies what the test is for.

**Key citations:** SB5-MAI Lec 7; Mockito documentation.

---

### Q23. Discuss the Fragile Test Problem.

**Type:** analytical.

**Thesis:** Tests can break for the *wrong* reasons. Lec 7's four-sensitivity taxonomy explains *why*, and the cure is in test design, not test framework.

**Outline:**

1. **Intro** — *"In Agile, these are all changing all the time."*
2. **The four sensitivities:**
   - **Behaviour sensitivity** — business-rule change. Expected fragility.
   - **Interface sensitivity** — method rename / GUI change. Mostly expected.
   - **Data sensitivity** — database / fixture change. Often unexpected.
   - **Context sensitivity** — OS / time-zone / locale change. Almost always unexpected.
3. **Why each happens:**
   - **Behaviour:** the spec changed; the test correctly fails.
   - **Interface:** rename without IDE refactor support.
   - **Data:** test fixtures hard-coded to specific DB rows.
   - **Context:** tests assume a specific time zone, default locale, file system.
4. **Case study (Lab 7):** the `Labels.properties` resource bundle in JHotDraw is locale-sensitive (`Labels_de.properties` exists). The author's tests don't assert on label content, so the fragility is *dormant*. Identifying it required Lec 7's framing — without it, a tester running under `-Duser.language=de` could see a mysterious failure.
5. **Cures by category:**
   - Behaviour: nothing to cure; the test is doing its job.
   - Interface: use IDE refactor tools; keep API surface small (ISP).
   - Data: use builders, not fixtures.
   - Context: explicitly set locale / time zone in test setup; control time via DateServer.
6. **Connection to maintainability (Rajlich):** fragile tests = high test-code-modification ratio per production change. Goal: 1.4 test lines per production line (Drawlets baseline). Fragile tests push the ratio higher.
7. **Counter-argument:** "all tests are fragile to enough change." Rebut: not all fragility is equal. The four-category split lets us *triage* fragility.
8. **Conclusion** — the Fragile Test Problem is the silent failure mode of test culture. The cure is structural, not framework-based.

**Key citations:** SB5-MAI Lec 7; Lec 9.

---

## BDD

### Q24. Compare unit testing and behaviour-driven testing.

**Type:** comparative.

**Thesis:** Unit testing and BDD are not competitors but *layers of the same pyramid*. Unit tests pin the implementation; BDD pins the contract.

**Outline:**

1. **Intro** — Lab 7 + Lab 9 = two layers on the same feature.
2. **Comparison framework — four dimensions:**

   | Dimension | Unit testing | BDD |
   |---|---|---|
   | **Audience** | Developers | Developers + domain experts (read the report) |
   | **Abstraction** | One method | A user story |
   | **Failure semantics** | "This logic is broken" | "This behaviour is wrong" |
   | **Cost of maintenance** | High (interface-sensitive) | Lower (behaviour-sensitive) |

3. **What unit testing does well:**
   - Fast.
   - Pinpoints specific code paths.
   - Boundary conditions / failure modes.
4. **What BDD does well:**
   - Readable as documentation.
   - Survives refactoring (less interface-sensitive).
   - Encodes user stories executably.
5. **What unit testing does poorly:**
   - Unreadable to non-developers.
   - Cannot serve as documentation.
   - Fragile to method renames.
6. **What BDD does poorly:**
   - Slow.
   - Not granular enough to debug.
   - Setup overhead (stage classes).
7. **Case study:** the same Group/Ungroup feature has 24 unit tests *and* 4 BDD scenarios. **The two are complementary, not redundant.** Unit tests catch "did I break the algorithm?" Scenarios catch "did I break the user-facing contract?"
8. **Lec 10 textbook validation:** Rajlich (slide 27) explicitly recommends *JGiven + Mockito* — i.e., BDD + mocks — together.
9. **Conclusion** — choosing between unit testing and BDD is a category error. The right question is *how many of each*.

**Key citations:** SB5-MAI Lec 7, Lec 9; Lab 7; Lab 9; Rajlich 2012 ch. 17.

---

### Q25. Discuss "living documentation" as a property of BDD.

**Type:** evaluative.

**Thesis:** Living documentation — documentation that the build refuses to let go stale — is BDD's most distinctive property and BDD's strongest justification.

**Outline:**

1. **Intro** — Lec 9's central claim.
2. **The problem with static documentation:**
   - Confluence pages rot.
   - Comments drift from code (Lec 6).
   - Specifications stop matching implementation.
3. **The BDD solution:**
   - Scenarios are written in domain language.
   - Scenarios execute as tests.
   - If the code drifts from the spec, the build *fails*.
   - Therefore the documentation *cannot* be stale and the build green.
4. **Operational form:** JGiven HTML5 report. Lec 9's TNG slide: 53 scenarios with tags, filter, expand — readable to a non-developer.
5. **Connection to Lec 6 / 10:**
   - Lec 6: "comments are failures." BDD's scenarios are *succeed-as-documentation*.
   - Lec 10: stakeholders perform acceptance testing. BDD's scenarios are the acceptance-test artefact.
6. **Case study (Lab 9):** the author's 4 JGiven scenarios for Group/Ungroup describe what the feature does in English. A new contributor reads them first to understand the feature.
7. **Limitation:** JGiven scenarios are written by *developers*, not domain experts. Lec 9 is explicit about this trade-off. The cost of developer-friendly BDD is domain-expert exclusion.
8. **Counter-argument:** "good comments + good naming achieves the same thing." Rebut: comments + names are not *executable*. The build cannot enforce their accuracy.
9. **Conclusion** — living documentation is the property that distinguishes BDD from "unit tests with prettier names."

**Key citations:** SB5-MAI Lec 9; JGiven documentation.

---

### Q26. Compare classical and developer-friendly BDD frameworks.

**Type:** comparative.

**Thesis:** Classical BDD (Cucumber, JBehave) maximises domain-expert collaboration at the cost of dual-artefact maintenance. Developer-friendly BDD (JGiven, Spock) reverses the trade-off. The right choice depends on whether your project has *separate* domain experts.

**Outline:**

1. **Intro** — Lec 9's two-family taxonomy.
2. **Classical family:**
   - Cucumber (plain text + Java step definitions).
   - JBehave (plain text + Java).
   - Concordion (HTML + Java).
   - FitNesse (wiki + Java).
   - Cost: two artefacts (the `.feature` file + the step definitions).
3. **Developer-friendly family:**
   - Spock (Groovy).
   - ScalaTest (Scala).
   - Jnario (Xtend).
   - Serenity (Java).
   - JGiven (Java).
   - Cost: scenarios live in code; not writeable by domain experts.
4. **The trade-off in one sentence:** *classical BDD pays maintenance cost for domain-expert authorship; developer-friendly BDD pays domain-expert exclusion for low maintenance.*
5. **Which one fits which project:**
   - If you have separate domain experts who *will write* scenarios → classical.
   - If your "domain experts" are also developers → developer-friendly.
   - Open-source infrastructure code (JHotDraw, library code) → developer-friendly.
   - Enterprise business application → classical.
6. **Case study:** Lab 9 used JGiven because JHotDraw has no domain expert separate from the developer. The author *is* the user.
7. **Counter-argument:** "Cucumber's plain-text scenarios are still readable to developers, so why not always use it?" Rebut: dual-artefact maintenance is a real cost (Lec 9 explicit); pay it only when the second artefact (the `.feature` file) is *actually* used by a non-developer.
8. **Conclusion** — the BDD family choice is a *team-shape* decision, not a *test-quality* decision.

**Key citations:** SB5-MAI Lec 9.

---

## Technical debt

### Q27. Define technical debt and discuss its usefulness.

**Type:** definitional + evaluative.

**Thesis:** *Technical debt as a financial metaphor* (Cunningham 1992) is useful but limited. *Technical debt as a behavioural quantity* (Tornhill 2018) — the product of complexity and change frequency — has empirical force.

**Outline:**

1. **Intro** — Cunningham's 1992 metaphor; Ward's analogy of shipping code "as a loan."
2. **Ford / Parsons / Kia definition (2017):** *"Stuff that isn't supposed to be there and is in the way of the stuff that is supposed to be there."*
3. **The two clauses:**
   - "Not supposed to be there" — quality dimension.
   - "In the way" — interference dimension. **The load-bearing clause.**
4. **Why the metaphor is useful:**
   - Translates to business stakeholders.
   - Makes the *interest cost* visible.
   - Encourages explicit trade-off decisions.
5. **Why the metaphor is limited:**
   - SonarQube-style estimates ("$341,563, 683 man-days") are misleading.
   - You don't pay debt linearly.
   - Not all debt is equal — Lehman's complexity rises everywhere; the *expensive* debt is in hotspots.
6. **Tornhill's reformulation (Lec 11):**
   - Principal = code complexity.
   - Interest rate = code change frequency.
   - **Debt is the product** — high principal × high frequency = high cost.
7. **Case study:** JHotDraw has *many* SonarQube-detectable issues (comments compensating for bad code, dead fields, magic numbers). The *expensive* debt is concentrated — likely `AbstractCompositeFigure`, `DefaultDrawingView`, `AbstractSelectedAction` (Lab 7 reflections). Running CodeScene would confirm.
8. **Counter-argument:** "the metaphor's vagueness is a feature — it lets stakeholders talk about quality." Concede partially: yes, vagueness has rhetorical value. But it should not substitute for measurement.
9. **Conclusion** — *technical debt as a slogan* is dangerous; *technical debt as a measurement* is critical.

**Key citations:** Cunningham 1992; Ford et al. 2017; Tornhill 2018; SB5-MAI Lec 11.

---

### Q28. Compare static and behavioural code analysis.

**Type:** comparative.

**Thesis:** Static analysis sees *the code as it is now*; behavioural analysis sees *the code as it has evolved*. Both are necessary; neither is sufficient.

**Outline:**

1. **Intro** — Lec 11's central pivot.
2. **Comparison framework:**

   | Dimension | Static | Behavioural |
   |---|---|---|
   | **Input** | Source code | Source code + git history + (PM tools) |
   | **Sees** | Snapshot | Movie |
   | **Tells you** | What is wrong | What matters |
   | **Example tool** | SonarQube | CodeScene |
   | **Actionable?** | Often not (10,072 violations) | Yes (top 10 hotspots) |

3. **What static analysis catches:**
   - Cyclomatic complexity.
   - Code duplication.
   - Pattern violations (uninitialized variables, unused imports).
4. **What behavioural analysis adds:**
   - Hotspots (complexity × change frequency).
   - Change coupling (files that commit together).
   - Off-boarding risk (knowledge concentration).
   - Trends over time.
5. **Why static alone is not actionable:** Lec 11's Tomcat slide — 10,072 SonarQube violations, $341,563 of "debt." Tells the team *that* there's a problem, not *where to start*.
6. **Why behavioural complements rather than replaces:**
   - Behavioural can't see code quality without static metrics.
   - Static can't see *use* without history.
   - Together they answer *"which complex code matters."*
7. **Empirical foundation (Graves et al. 2000):** *"Process measures based on change history are more useful in predicting fault rates than product metrics of the code."*
8. **Case study:** JHotDraw with no CodeScene access — the author can guess (`AbstractCompositeFigure`, `DefaultDrawingView`) but cannot verify. With CodeScene, the top-10 list would be objective.
9. **Counter-argument:** "behavioural analysis is just expensive static analysis with extra data." Rebut: the *data* is the analysis. Without git, you have no way to weigh which complex code matters.
10. **Conclusion** — modern code analysis is *static + behavioural*. SonarQube alone is incomplete.

**Key citations:** SB5-MAI Lec 11; Graves et al. 2000; Tornhill 2018.

---

### Q29. Discuss hotspots and their predictive power.

**Type:** analytical.

**Thesis:** A hotspot — the product of complexity (principal) and change frequency (interest) — is *also* a bug-density predictor (Graves et al. 2000). Hotspot maps therefore double as risk maps.

**Outline:**

1. **Intro** — Tornhill's central operational concept.
2. **The hotspot formula:**
   - Principal = code complexity (cyclomatic, lines).
   - Interest rate = code change frequency (commits per file).
   - Hotspot = product.
3. **The financial analogy:**
   - Principal only = "I owe a lot but never pay interest." Not urgent.
   - Interest only = "I pay often but the amount is small." Annoying, not expensive.
   - Both = the team's effort silently consumed.
4. **The predictive claim (Graves et al. 2000):**
   - Process measures (change history) > product metrics for fault prediction.
   - **Number of past changes > size** in predicting future bugs.
5. **Lehman echo:** older modules have ~1/3 fewer faults per equivalent module. Code that has *survived* many changes is, paradoxically, more stable.
6. **Case study:** the author's guess for JHotDraw hotspots:
   - `AbstractCompositeFigure` — high complexity, low change frequency.
   - `DefaultDrawingView` — high both.
   - `AbstractSelectedAction` — low complexity, high change frequency.
   - The middle one is the priority refactor target.
7. **X-Ray drill-down (Lec 11):** zoom into a hotspot *file* to find the hotspot *function*. The 12-row table beats the 10,072-row SonarQube list.
8. **Counter-argument:** "complexity metrics are notoriously poor predictors." Rebut: alone, yes. Combined with change frequency, predictive power rises significantly (Graves et al.).
9. **Conclusion** — hotspots are the closest thing the field has to a *prioritised refactor backlog*.

**Key citations:** Tornhill 2018; Graves et al. 2000.

---

### Q30. Discuss the social dimension of technical debt (legacy, off-boarding).

**Type:** evaluative.

**Thesis:** Technical debt is not purely technical — it is also a *knowledge concentration* problem. Legacy code is often *unfamiliar code*, and off-boarding risk maps onto hotspot maps.

**Outline:**

1. **Intro** — Lec 11's *legacy code* slide.
2. **Two definitions of legacy:**
   - Code that lacks quality (relative).
   - Code *we didn't write ourselves*.
3. **"The Technical Debt That Wasn't":** sometimes the problem is *unfamiliarity*, not *bad code*. Onboarding fixes one; refactoring fixes the other; the team must diagnose which is which.
4. **Off-boarding simulation (Lec 11, slide 22-23):** simulate a developer leaving → red dots show "Off-Boarding Risk" files. The hotspot map and the knowledge-concentration map *overlap*.
5. **Implications:**
   - Hot files are where bugs cluster (Graves).
   - Hot files are where knowledge concentrates (Tornhill).
   - **Therefore: where you most need redundant knowledge, you most often have one expert.**
6. **Case study:** JHotDraw's `git log` shows Werner Randelshofer as primary author of most files. He left active maintenance years ago. **Every JHotDraw file is an off-boarded file by Tornhill's definition.**
7. **Mitigations (from the course):**
   - Document hotspots before touching (Lab 2 concept location).
   - Add tests around hotspots before refactoring (Lab 7).
   - Apply Boy Scout Rule (Lec 6).
   - Use BDD scenarios for living documentation (Lab 9).
8. **Counter-argument:** "knowledge silos are a HR problem, not a technical-debt problem." Rebut with Lec 11: the *measurement* of knowledge silos via git authorship makes it a technical-debt problem too. The two are inseparable.
9. **Conclusion** — Technical debt's *social* dimension is what makes it expensive in *organisations*, not just *codebases*.

**Key citations:** SB5-MAI Lec 11; Tornhill 2018.

---

## Synthesis

### Q31. Apply the phased model to a real change you have made.

**Type:** applied.

**Thesis:** The author's JHotDraw Group/Ungroup work is *one complete pass* through Rajlich's phased model. Documenting it phase-by-phase makes the model's value explicit.

**Outline:**

1. **Intro** — choose Group/Ungroup as the feature.
2. **Initiation (Lab 2):**
   - Change request: "support grouping figures."
   - Scope: confined to `jhotdraw-core` Action layer.
3. **Concept Location (Lab 2):**
   - Started with SUR (regex "group").
   - First path through `Toolbar` was wrong-way.
   - Backtracked.
   - Right path: `GroupAction` → `GroupFigure`.
4. **Impact Analysis (Lab 3):**
   - Direct: `GroupAction`, `UngroupAction`.
   - Indirect: `AbstractSelectedAction`, `GroupFigure`, `AbstractCompositeFigure`, `DefaultDrawing`.
   - Mechanised: GitHub Actions CI runs full test suite on every PR.
5. **Prefactoring (Lab 4):**
   - Compose Method on 67-line `actionPerformed`.
   - Removed dead `prototype` field.
   - Removed stale `XXX` comment.
   - **Deferred** `Replace Conditional with Polymorphism` (no tests existed).
6. **Actualization (Lab 5 — audit only):**
   - SOLID review.
   - Identified SRP + OCP violation in `isGroupingAction`.
   - The actual *change* (adding a feature) was not performed — Lab 5 was the audit lap.
7. **Postfactoring (not performed):** would happen after a hypothetical new action type is added.
8. **Verification (Labs 7 + 9):**
   - 24 unit tests (Lab 7).
   - 6 production assertions (Lab 7).
   - 4 BDD scenarios (Lab 9).
9. **Conclusion phase:**
   - All commits on `alex` branch.
   - CI green throughout.
10. **What the model gave the author:**
    - A *schedule* — no skipped steps.
    - A *vocabulary* — "this is the prefactoring phase, not the change yet."
    - A *retrospective* — every commit slots into a phase.
11. **What the model missed:** the *social* phase (Conclusion's deadline / reputation dynamics) was simulated only — there was no team, no real deadline.
12. **Conclusion** — the phased model is a *working schedule*, validated by application.

**Key citations:** Rajlich 2012; portfolio Labs 2-9.

---

### Q32. Discuss the relationship between maintenance, evolution, and design.

**Type:** evaluative + synthesis.

**Thesis:** Maintenance, evolution, and design are not three activities but *one continuous activity at three time-scales*. Treating them as separate creates the silos the course's lectures repeatedly attack.

**Outline:**

1. **Intro** — the three terms are commonly separated; the course argues otherwise.
2. **The naive view:**
   - Design = before deployment.
   - Maintenance = after deployment.
   - Evolution = the cumulative arc.
3. **Why the naive view is wrong:**
   - **Lehman:** systems must be continually adapted. There is no "after deployment" if the system is alive.
   - **Brooks:** essential difficulties are present at every stage.
   - **Rajlich:** the phased model applies to *every change*, including those during initial development.
4. **The continuous view:**
   - Every change re-runs the phased model.
   - Every refactoring is a *re-design* at small scale.
   - Every commit is a maintenance event.
5. **Empirical evidence:**
   - Lientz-Swanson: 80% of TCO is *post-deployment* — but the activities are indistinguishable from pre-deployment development.
   - Beck (TDD): tests are written before code — *design* via tests is the same activity as *maintenance* via tests.
6. **The role of architecture (Lec 5):**
   - Clean Architecture is the *long-time-scale* form of SRP.
   - SRP is the *short-time-scale* form of Clean Architecture.
   - **They are the same principle at different magnification.**
7. **Case study:** the author's JHotDraw work is *all three at once*:
   - Maintenance (modifying existing code).
   - Evolution (adding tests, refactoring, improving structure).
   - Design (every refactoring is a design micro-decision).
8. **Counter-argument:** "the distinction is useful for project planning." Concede: yes, in waterfall. In modern DevOps, the distinction has dissolved.
9. **Conclusion** — the course's deepest argument is that *maintenance is not a phase of software work; maintenance is what software work* is.

**Key citations:** Lehman 1980; Rajlich 2012; Beck 2002; Martin 2017.

---

### Q33. What is the single most important lesson from the course?

**Type:** evaluative.

**Thesis:** The single most important lesson is the *Boy Scout Rule* — applied as a continuous, small-cost discipline that compounds. Every other concept in the course is either *justification for* or *implementation of* this rule.

**Outline:**

1. **Intro** — Martin's seemingly trivial scouting metaphor.
2. **The rule:** leave the code cleaner than you found it.
3. **Why it's the *most important*:**
   - It's the smallest possible move (zero scheduling overhead).
   - It compounds (Σε > Σk).
   - It's the operational form of *every* other principle:
     - Lehman → if complexity rises by default, Boy Scout pushes back.
     - Brooks → essential difficulties can't be eliminated, but Boy Scout makes them tractable.
     - SOLID → applying SRP/OCP/etc. *during a visit* is Boy Scout.
     - Clean Code → naming, function size, dead-code removal are Boy Scout moves.
     - Tests → adding one test per visit is Boy Scout.
     - BDD → adding one scenario per feature understanding is Boy Scout.
     - Tornhill → fixing the hotspot you visit *is* the hotspot strategy.
4. **Why it's *operationally* powerful:**
   - No politics (no scheduling).
   - No risk (small changes).
   - High ROI (every visit pays).
5. **Case study:** the author's Lab 4 work — removing one dead field + one comment + 67-line method split. Cumulative cost: 20 minutes. Cumulative benefit: a class of future confusion eliminated.
6. **The deeper claim:** *every senior engineer the author has worked with applies this rule, regardless of company or culture.* The Boy Scout Rule is what distinguishes engineers from people who happen to write code.
7. **Counter-argument:** "this scope-creeps PRs." Rebut: that's a PR-size policy problem, not a rule problem.
8. **Conclusion** — internalising the Boy Scout Rule is the single behavioural change that, if it survives the exam, will pay back the course's tuition for the rest of the author's career.

**Key citations:** Martin 2009; SB5-MAI Lectures 1, 6, 11; portfolio Lab 4.

---

**Next:** open file 04 ([Quotes and Citations](04-quotes-and-citations.md)).
