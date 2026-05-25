# 01 — Essay Craft

How to write a 15-20 page academic essay when you haven't written one in five years.

> **Read this file first.** Templates, intro patterns, transitions, time plan, common mistakes. Every other aid in this folder feeds into the structure laid out here.

---

## 1. The five essay types — match the question's verb

Read the exam question. Underline the **verb**. That tells you what type of essay to write.

| Verb in question | Essay type | What the marker wants |
|---|---|---|
| *describe*, *explain*, *outline*, *summarise* | **Descriptive** | A clear, accurate, organised presentation of facts. Less argument, more clarity. |
| *analyse*, *examine*, *investigate* | **Analytical** | Break the topic into parts, explain how each works, show how they relate. |
| *compare*, *contrast*, *differ* | **Comparative** | Two or more things put side by side. **Use a comparison framework**, not parallel monologues. |
| *evaluate*, *critique*, *assess*, *discuss*, *to what extent* | **Evaluative** | A judgement, defended with evidence on both sides. **The most common at master's level.** |
| *apply*, *demonstrate*, *use X to* | **Applied** | Use a concept on a worked example — your portfolio is gold here. |

**Most master's-level questions are evaluative.** They want a position, defended with evidence, with the counter-position acknowledged.

---

## 2. The 15-20 page essay skeleton

A 15-20 page essay at ~280 words/page = 4,200–5,600 words. Structure:

```
INTRODUCTION                        (1 page  / ~280 words)
   - Hook (1 sentence)
   - Background (3-4 sentences)
   - Thesis statement (1-2 sentences)
   - Roadmap of the essay (2-3 sentences)

MAIN BODY                            (12-17 pages / ~3,400-4,800 words)
   Section 1 — first main argument  (3-4 pages)
      Topic sentence
      Evidence (quote + number + example)
      Counter-evidence (if any)
      Mini-conclusion + transition

   Section 2 — second main argument (3-4 pages)
      [same shape]

   Section 3 — third main argument  (3-4 pages)
      [same shape]

   Section 4 — synthesis / case study (3-4 pages)
      How the arguments converge on a single insight,
      illustrated by your Group/Ungroup feature work

CONCLUSION                           (1-2 pages / ~280-560 words)
   - Restate thesis (in new words)
   - Summarise the three arguments
   - Implication beyond the essay
   - Final sentence
```

**Rule of thumb:** the essay has *one* central argument (the thesis). The body sections are different *kinds of evidence* for that argument, not different topics.

---

## 3. Intro template — fill in the blanks

```
Software systems do not stand still: [HOOK SENTENCE about evolution / decay / change].

Since [LEHMAN 1980 / BROOKS 1975 / RAJLICH 2012], the field of software maintenance has
developed [SHORT CONTEXTUALISING CLAIM relevant to the question].

This essay argues that [THESIS — one specific claim defended in the essay].

The argument proceeds in four steps. First, [SECTION 1 PREVIEW]. Second, [SECTION 2 PREVIEW].
Third, [SECTION 3 PREVIEW]. Finally, [SECTION 4 PREVIEW — synthesis / case study].
The essay draws throughout on a concrete instance of the maintenance cycle: the Group / Ungroup
feature of the open-source drawing framework JHotDraw, which the author refactored,
tested, and documented in line with the phased model over a single semester.
```

**Length:** roughly 280–300 words. One page in a Word document at 12pt double-spaced.

### Three example intros for different question types

**Type 1 — descriptive ("Describe the phased model of software change"):**
> Software systems do not stand still — once deployed, they accumulate change, complexity, and the residue of every prior decision. Rajlich's (2012) phased model breaks this perpetual change into seven discrete activities: Initiation, Concept Location, Impact Analysis, Prefactoring, Actualization, Postfactoring, and Conclusion, with Verification spanning the right-hand side of the V. This essay describes each phase in turn, illustrating each with a concrete example drawn from the author's semester-long maintenance work on the JHotDraw drawing framework. The phases are presented not as bureaucracy but as a *working rhythm* — what every change in a codebase passes through, regardless of whether the engineer notices.

**Type 2 — evaluative ("Discuss whether technical debt is a useful concept"):**
> *Technical debt* is one of the most-cited concepts in modern software engineering, but also one of the most contested. Ward Cunningham's 1992 financial-debt metaphor has been variously refined, narrowed, and abandoned over thirty years. This essay defends the position that the metaphor is **useful but only when grounded in concrete, behavioural data — not in static-analysis snapshots.** The argument runs in four parts. The first part introduces the classical definition (Ford, Parsons & Kia, 2017) and the structural critique. The second examines the snapshot-vs-trajectory disagreement using SonarQube and CodeScene as two opposing tools. The third applies the framework to the author's own work on the JHotDraw Group / Ungroup feature. The fourth concludes that *technical debt as a financial-language artefact* is misleading, but *technical debt as a behavioural quantity* — the product of code complexity and change frequency — has empirical force.

**Type 3 — comparative ("Compare unit testing and BDD"):**
> Software testing is not a single activity. Unit testing pins individual code paths; behavior-driven testing pins user-facing contracts. The two are often discussed as competitors, but a closer reading of the literature suggests they occupy different strata of the same pyramid. This essay compares the two approaches along four dimensions: *audience* (who reads the test), *level of abstraction* (what code path is exercised), *failure semantics* (what a failure tells the reader), and *cost of maintenance* (how the test ages). Each dimension is illustrated with the author's own JUnit and JGiven implementations of the same feature, allowing direct comparison of two tests of the same behaviour at two different layers.

---

## 4. Body section template

Each main body section follows the same shape:

```
TOPIC SENTENCE
   One sentence stating the section's claim. Should be clearly subordinate
   to the thesis stated in the intro.

CONCEPT EXPANSION
   2-3 sentences explaining the concept(s) the section deals with.
   Use the GLOSSARY (file 07) if you need precise definitions.

EVIDENCE — quote
   A direct quote from an authority. Use the QUOTE BANK (file 04).
   Cite as: (Author, year, p. X) or (Lecture N).

EVIDENCE — number
   A specific number, percentage, or ratio. Use NUMBERS-AND-FRAMEWORKS (file 06).
   Specific numbers are credibility multipliers.

EVIDENCE — example
   A concrete worked example. Your Group/Ungroup feature labs are the
   gold mine here. Use IMPLEMENTATIONS-AND-CODE (file 05).

COUNTER-POINT (for evaluative essays)
   Acknowledge the strongest objection. Either rebut it ("however, this
   misses...") or concede it partially ("while this is true in domain X,
   the present essay restricts itself to domain Y...").

MINI-CONCLUSION + TRANSITION
   One sentence that closes this section's claim and bridges to the next.
   E.g., "If complexity rises by default, the question becomes how to
   measure that rise — which is the subject of the next section."
```

**Length per section:** ~700–1,000 words = 2.5–3.5 pages. Three to four such sections fit the page budget.

---

## 5. Transition phrases — your secret weapon

Master's-level essays are judged partly on **how the paragraphs connect**, not just what they contain. Use these explicitly:

### Building / extending
- *Building on this argument,...*
- *This implication extends to...*
- *A further consequence of [X] is...*
- *Following the same logic,...*

### Contrasting / qualifying
- *However, this view is not universally held.*
- *In contrast,...*
- *Yet [X] does not account for...*
- *A more subtle reading suggests...*
- *On the other hand,...*

### Causing / explaining
- *Because of this,...*
- *This explains why...*
- *The root cause is therefore...*
- *It follows that...*

### Exemplifying
- *A concrete example is...*
- *This is illustrated by the author's own work on...*
- *For instance,...*
- *Consider the case of...*

### Concluding / synthesising
- *Taken together, these three observations imply...*
- *The cumulative weight of the evidence is...*
- *What unites these arguments is...*
- *In sum,...*

### Citing authority
- *As Martin (2009) argues,...*
- *Rajlich (2012, ch. 17) provides a worked example in which...*
- *Lecture N introduced this as...*
- *In the words of [author],* "..."

---

## 6. Conclusion template

Three moves, in order:

```
RESTATE THESIS — in new words, not copy-paste
   "This essay has argued that..."

SUMMARISE THE THREE ARGUMENTS
   "The first section established X. The second showed that Y. The third
    illustrated this in practice through..."

IMPLICATION BEYOND THE ESSAY
   "If the thesis is correct, then [practical / theoretical consequence].
    [Specifically what should change in how software is built/maintained/taught.]"

FINAL SENTENCE
   A single memorable line. Often returns to the hook from the intro.
```

**Length:** 1-2 pages. The conclusion is where you *land the plane* — don't introduce new evidence here, only synthesise existing evidence.

---

## 7. Time plan for a 4-5 hour exam (15-20 page essay)

| Time | Activity | Notes |
|---|---|---|
| 0:00 – 0:10 | Read question. Identify verb. Sketch thesis on paper. | Don't write yet. |
| 0:10 – 0:30 | Find closest question in file 03. Copy its outline. Adapt to your thesis. | This is the planning phase. |
| 0:30 – 0:50 | Pull evidence from files 02, 04, 05, 06 for each section. Annotate outline. | Have file paths next to each bullet point. |
| 0:50 – 1:10 | Write introduction. Refine thesis as you write. | Aim for one page. |
| 1:10 – 2:30 | Write body sections 1, 2, 3. Aim for 700-1000 words each. | Use a timer. Don't dwell. |
| 2:30 – 3:30 | Write body section 4 (synthesis / case study). Use the Group/Ungroup work heavily. | This is the deepest section. |
| 3:30 – 3:50 | Write conclusion. | Restate thesis, summarise, implication. |
| 3:50 – 4:30 | Re-read, fix transitions, add missing citations. | Critical. Don't skip. |
| 4:30 – 5:00 | Final polish — typos, page count, citations consistent. | |

**If short on time:** write the intro and conclusion *last* if you must. Examiners read both first; a strong intro/conclusion can save a middling body.

---

## 8. Common mistakes to avoid

| Mistake | Fix |
|---|---|
| **"In this essay I will argue..."** (autobiographical) | Use "This essay argues..." (third-person academic). |
| **One huge paragraph per page.** | Break every ~150-200 words. Each paragraph = one idea. |
| **No quotes / no numbers.** | Drop at least one quote (file 04) and one number (file 06) per major section. |
| **Restating the question.** | Don't. Move directly into the thesis. |
| **No examples.** | Every theoretical claim needs a concrete example — usually from your Group/Ungroup work (file 09 in the portfolio). |
| **No engagement with counter-arguments.** | For evaluative essays, you *must* acknowledge the strongest objection to your thesis. Otherwise it reads as one-sided. |
| **Lists without analysis.** | If you list 4 SOLID principles, explain *why* the list matters, don't just list them. |
| **Bullet points in body paragraphs.** | Academic essays use prose. Use bullets *only* if explicitly demonstrating a list. |
| **Conclusion introduces new material.** | The conclusion synthesises only. New evidence belongs in body sections. |
| **Inconsistent citation style.** | Pick one (Harvard / APA / numbered) and stick to it for the whole essay. |

---

## 9. Citing sources in the essay

For SDU-style academic writing, use **author-year (Harvard)** citation:

- *(Rajlich, 2012)* — generic citation.
- *(Rajlich, 2012, p. 110)* — page-specific.
- *(Martin, 2009, ch. 6)* — chapter-specific.
- *(Lehman, 1980)* — paper citation.
- *(SB5-MAI, Lecture 7)* — course material.

Direct quotes get quotation marks and citation:
> *"Testing can demonstrate the presence of bugs, but not their absence"* (Dijkstra, 1972).

At the end of the essay, list all references in a **References** section, alphabetical by surname. Use the portfolio's [Bibliography](../portfolio/portfolio.md#bibliography) as the source.

---

## 10. Quick essay-quality checklist (last 10 minutes)

Print this and tick before submitting:

- [ ] Introduction has a clear one-sentence thesis.
- [ ] Each body section has a topic sentence.
- [ ] Each body section contains: 1+ quote, 1+ number, 1+ concrete example.
- [ ] Each section ends with a transition to the next.
- [ ] Counter-arguments addressed (for evaluative essays).
- [ ] Conclusion restates thesis in new words and does *not* introduce new evidence.
- [ ] All quotes are attributed; all numbers have a source.
- [ ] Page count is within 15-20 pages.
- [ ] References section is included and alphabetical.
- [ ] No first-person pronouns ("I", "my") unless explicitly allowed.
- [ ] Section headings are descriptive (not just "Section 1").

---

## 11. Worked example — the *one* essay you should have rehearsed mentally

Question: *"Discuss the role of refactoring in the software maintenance lifecycle."*

**Thesis:** *Refactoring is not optional cleanup but the structural pre-condition that makes every other phase of the software-change lifecycle feasible.*

**Outline (paste into exam, adapt):**

```
INTRO
   - Hook: Lehman's Law of Increasing Complexity — complexity rises by default.
   - Background: Rajlich's phased model places refactoring at *two* points (Prefactoring + Postfactoring).
   - Thesis (above).
   - Roadmap: theoretical justification → empirical evidence → case study → implications.

SECTION 1 — Theoretical justification
   - Lehman's two laws (Lec 1, Lec 11).
   - Brooks's essential difficulties (Lec 1).
   - Why complexity kills speed: Hickey's Easy vs Simple curve (Lec 11).
   - Refactoring as the *active* counter-force to entropy.
   - Quote: Martin's Boy Scout Rule.

SECTION 2 — Empirical evidence
   - Rajlich's Drawlets case (Lec 10, slide 37):
     no-refactoring = 13 classes modified, splitting roles = 5 → 62% reduction in change scope.
   - Tornhill's hotspot analysis: bugs cluster where change clusters (Graves et al. 2000).
   - Sonar's 11% / 683 man-day debt estimates — the wrong kind of evidence.

SECTION 3 — The cost of NOT refactoring
   - Lehman's complexity rise — quantitative.
   - Increasing test fragility (Lec 7 — fragile test problem).
   - Lost knowledge (Tornhill's off-boarding simulation).
   - Risk: "the technical debt that wasn't" — sometimes legacy is *unfamiliarity*, not bad code.

SECTION 4 — Case study: the author's JHotDraw work
   - Lab 4 prefactoring on GroupAction (Compose Method, dead-code removal).
   - The deferred refactor (Replace Conditional with Polymorphism) — the cost of skipping.
   - Lab 7's mockability tax — direct evidence of past refactoring debt.
   - Lab 10's "splitting roles" refactor that three lectures converge on.

CONCLUSION
   - Refactoring is not a phase that happens *after* the change; it is the precondition that lets the change happen.
   - The phased model places it twice (pre/post) for this reason.
   - Implication: a maintenance culture without refactoring is a maintenance culture without progress.
   - Final sentence: "The Boy Scout Rule is not optional advice; it is the rule that keeps the lifecycle running."
```

You now have a 15-20 page essay scaffold for this question in under 10 minutes. Repeat this exercise for the 30+ questions in file 03 and your exam is rehearsed.

---

**Next:** open file 02 ([Comprehensive Summary](02-comprehensive-summary.md)).
