# Exam Day Playbook

Step-by-step actions for the day of the exam. Read in order; follow in order.

> **Use rule:** print this file or pin it to a second monitor. During the exam, refer to it whenever you feel unsure what to do next. Every step has a *concrete action* — no vague advice.

---

## Section 0 — The day before

### 0.1 The afternoon before (1 hour)

1. **Read [02-comprehensive-summary.md](02-comprehensive-summary.md) end to end.** This is the single most valuable hour. By the end, you should be able to summarise every lecture's central claim out loud in 30 seconds.
2. **Skim [03-question-bank.md](03-question-bank.md) table of contents.** Don't read every outline — just glance at all 33 question titles. Your brain will pattern-match faster tomorrow.
3. **Open [MASTER-ESSAY.md](MASTER-ESSAY.md) and read just the Abstract and the Introduction.** Get the voice and the central argument into your head.

### 0.2 The evening before

- **Charge laptop fully. Pack the charger.** A flat laptop during the exam is the single most preventable disaster.
- **Test that everything loads** on the machine you'll bring: open `MASTER-ESSAY.md`, `portfolio.md`, the `exam-aids/` folder. Make sure rendering works.
- **Set up a clean folder structure** so you can find files fast: `exam-aids/` open in your file explorer; main typing window in Word or your preferred editor; portfolio open in a third tab.
- **Sleep.** Eight hours beats one more hour of revision.

### 0.3 The morning of

- **Eat protein**, not sugar. You will be writing for 4-5 hours.
- **Arrive 30 minutes early.** Use the buffer for any technical setup the proctors require.
- **Final pre-exam scan (15 minutes):** open [EXAM-DAY-PLAYBOOK.md](EXAM-DAY-PLAYBOOK.md) (this file) and [01-essay-craft.md](01-essay-craft.md) Section 5 (transition phrases). These are the two pieces of working memory you want loaded as you start.

---

## Section 1 — Minute 0 to 5: Setup

Before you even read the question:

1. **Open these files in tabs in this order:**
   - **Tab 1 (main writing):** a fresh Word / LaTeX / typing document.
   - **Tab 2:** [MASTER-ESSAY.md](MASTER-ESSAY.md) — your primary paste source.
   - **Tab 3:** [03-question-bank.md](03-question-bank.md) — for finding the closest outline.
   - **Tab 4:** [02-comprehensive-summary.md](02-comprehensive-summary.md) — for fact-checking and Ctrl-F lookup.
   - **Tab 5:** [04-quotes-and-citations.md](04-quotes-and-citations.md) — for verbatim quotes.
   - **Tab 6:** [06-numbers-and-frameworks.md](06-numbers-and-frameworks.md) — for specific numbers to drop in.
   - **Tab 7:** `portfolio/portfolio.md` — your full portfolio for narrative depth.

2. **Open this playbook** in a separate window or print it.

3. **Set a timer** for the total exam duration. Glance at it every 30 minutes.

4. **Write the header** of your document immediately, before reading the question. Header is in `MASTER-ESSAY.md` at the very top — copy it, fill in `XXXXX` for student exam number and lecturer email if you know them. **This is one task you can complete in 60 seconds while your brain is still warming up.**

---

## Section 2 — Minute 5 to 15: Decode the question

Now read the question. Carefully. Twice.

### 2.1 Underline the verb

Find the verb in the question. It tells you the essay type:

| Verb you find | Essay type | Strategy |
|---|---|---|
| *describe, explain, outline, summarise* | **Descriptive** | Use Master Essay sections most relevant; cut Discussion to one paragraph; keep Abstract neutral. |
| *analyse, examine, investigate* | **Analytical** | Use Master Essay with full Discussion; emphasise findings and connections. |
| *compare, contrast, differ between* | **Comparative** | Use Master Essay's two layers (e.g., Lab 7 vs Lab 9) as your two compared things; expand the comparison points. |
| *evaluate, critique, assess, discuss, to what extent* | **Evaluative** | Full Master Essay including Discussion; defend the thesis explicitly; acknowledge counter-arguments. |
| *apply, demonstrate, use X to* | **Applied** | Master Essay's Lab sections are gold — show the technique on the Group/Ungroup feature. |

### 2.2 Identify the topic scope

Underline the topic noun(s). Examples:

| Topic noun in question | Master Essay section(s) to keep | Sections to cut |
|---|---|---|
| Refactoring, code smells, Fowler, prefactoring | §1, §2, §3, §4, **§5 (full)**, §8.1, §10, §11, §13 | Cut §6 details, §8.2 BDD details |
| SOLID, Clean Architecture, OO principles | §1, §2, §3, §4, §5 (brief), **§6 (full)**, §8.1, §10, §11, §13 | Cut §5 details, §8.2 BDD details |
| Testing, unit tests, assertions, TDD | §1, §2, §3, §4, §5 (brief), **§8.1 (full)**, §10, §11, §13 | Cut §6 SOLID details, §8.2 BDD details |
| BDD, JGiven, acceptance tests, user stories | §1, §2, §3, §4, §5 (brief), **§8.2 (full)**, §10, §11, §13 | Cut §6 SOLID, trim §8.1 unit tests |
| Phased model, software maintenance, change process | **All sections** — paste the whole Master Essay | (none — full paste) |
| Technical debt, hotspots, code analysis | §1, §2, §3, §4, §6 (brief), §8 (brief), §10, §11, §13 | Cut §5 refactoring details, §8.1, §8.2 details |
| Software evolution, Lehman's laws | §1, §2 (brief), §6 + §10 (synthesis), §11, §13 | Cut most phase detail; keep theoretical sections |
| Clean code, naming, comments, functions | §1, §5 (Clean Code subsection 5.4), §8.1 (test-name discipline), §10, §11, §13 | Cut §6 SOLID, §8.2 details |

### 2.3 Set the writing scope

Count the pages the exam requires (typically 15-20). At ~280 words per page:

| Pages required | Word count | Sections to include |
|---|---|---|
| 10-12 | ~3,000 | Abstract + Intro + 1 phase section + Verification + Conclusion + Discussion |
| 15-17 | ~4,500 | Abstract + Intro + Initiation + Concept Location + Impact Analysis + 1 phase section + Verification + Conclusion + Discussion + References |
| 18-20 | ~5,400 | Add another phase section or expand Discussion |
| 25-30 | ~7,500 | Paste full Master Essay |

---

## Section 3 — Minute 15 to 30: Outline and paste

### 3.1 Decide the spine

Pick **one of three** organising spines based on the question:

**Spine A — Phase-model spine (default; works for almost every question):**
Use Master Essay sections 1 → 2 → 3 → 4 → 5 (or 6 or 8 depending on topic) → 8.x → 9 → 10 → 11 → 13. The Master Essay is built on this spine.

**Spine B — Compare/contrast spine (for comparative questions):**
Intro → Topic A theory → Topic A application (Lab X) → Topic B theory → Topic B application (Lab Y) → Comparison table → Synthesis. Drop most phase-model scaffolding.

**Spine C — Single-concept deep dive (for questions that ask only about one concept):**
Intro → Theoretical foundation → Application in the Group/Ungroup feature → Reflection → Discussion. Use the Master Essay's relevant subsection as the application part; expand it with material from the portfolio.

### 3.2 Paste in skeleton

In your typing document, paste the Master Essay's header, abstract, and the section headings you'll use. **Do not paste body text yet.** You want the structure visible before you fill it in.

### 3.3 Modify the abstract

The Master Essay's abstract is generic across all topics. **Spend 5 minutes rewriting one or two sentences** to emphasise whichever sub-topic the exam asks about. For example:

- For a refactoring question: lead the abstract with the three refactorings and the 62% reduction figure.
- For a testing question: lead with the 24 unit tests + mockability tax discovery.
- For a BDD question: lead with the four scenarios + living documentation claim.

The rest of the abstract stays.

---

## Section 4 — Minute 30 to (deadline - 60): Write the body

### 4.1 Order of writing

Write in this order, not in section order:

1. **Introduction first** (~15 minutes) — adapt Master Essay §1 to lead into your specific thesis.
2. **The "core" section that matches the question** (~45 minutes) — paste the Master Essay's matching phase section (§5 for refactoring, §6 for SOLID, §8.1 for unit tests, §8.2 for BDD), then adapt the wording so it reads as an *answer to the question*, not as a generic phase description.
3. **The supporting phase sections** (~30 minutes each) — paste and adapt Initiation, Concept Location, Impact Analysis as needed. Keep them brief — these are *scaffolding*, not the main act.
4. **Verification section** (~20 minutes) — paste §8; trim whichever sub-section (unit tests or BDD) isn't the focus.
5. **Discussion section** (~20 minutes) — paste Master Essay §10. **Add one paragraph** specifically addressing the exam question's "evaluate" or "critique" demand, if the question is evaluative.
6. **Conclusion** (~10 minutes) — paste Master Essay §11; rewrite the last sentence to land on your thesis.
7. **References** (~5 minutes) — paste Master Essay §12. Keep them all — having too many references is never marked down.

### 4.2 The paste-then-adapt rhythm

For each section:

1. Copy from Master Essay.
2. Paste into your document.
3. **Read your first sentence aloud (silently if in exam hall).** Does it answer the question being asked? If not, rewrite that one sentence.
4. **Skim the rest of the section.** Look for any sentence that says "Lab N" or "the present work" — these are the rooted-in-evidence sentences. **Keep all of them.**
5. Look for any sentence that feels off-topic for the specific question. **Delete those.** Don't try to repurpose — just delete.
6. Move to the next section.

This rhythm — paste, check first sentence, keep evidence sentences, delete off-topic sentences — is how you adapt 5,000 words of pre-written material to a specific question in 90 minutes.

### 4.3 Add citations and numbers as you go

Every 3-4 paragraphs, glance at [04-quotes-and-citations.md](04-quotes-and-citations.md) and [06-numbers-and-frameworks.md](06-numbers-and-frameworks.md). **Drop in one quote and one number per major section.** The Master Essay already has many; add more if your question demands extra credibility.

Pattern for inserting a number mid-paragraph:
> *...and the empirical foundation is Lehman's two laws (1980), restated in Lecture 1.*

becomes:

> *...and the empirical foundation is Lehman's two laws (1980), restated in Lecture 1, both of which inform the Lientz and Swanson finding that maintenance accounts for approximately 80% of total cost of ownership (1980).*

Pattern for inserting a quote:
> *...refactoring shortens future change propagation.*

becomes:

> *...refactoring shortens future change propagation. As Rajlich (2012) puts it: "refactoring can shorten the change propagation by moving the code affected by change into fewer classes, or by splitting roles so that only one of the roles needs to be updated."*

### 4.4 If you're behind on time

Halfway through the writing window, count your words. If you're below 50% of target word count:

- **Skip Postfactoring entirely** (Master Essay §7 — it's already explicitly empty).
- **Cut Impact Analysis to one paragraph.**
- **Cut Concept Location to one paragraph.**
- **Keep the core section, Verification, Conclusion, Discussion.**

If you're above 100% of target:

- **Cut the Appendix entirely.**
- **Trim Discussion to one paragraph.**
- **Cut your second example refactoring from §5.**

---

## Section 5 — The last 60 minutes: Polish

### 5.1 Minutes 60-30 before deadline: First pass

1. **Read your introduction.** Does it state a thesis? If not, add a sentence: *"This report argues that..."*
2. **Read your conclusion.** Does it restate the thesis in different words? If not, fix it.
3. **Scan all section headings.** Do they form a coherent argument? If a heading feels orphaned, either delete the section or add a transition sentence at the top of it.
4. **Search for `XXXXX`** in your document. Replace with actual values (exam number, lecturer email).

### 5.2 Minutes 30-15 before deadline: Citation pass

1. **Skim all references in the body.** Every name in the body should be in the References section.
2. **Check that lab references are consistent** — every "Lab 4" should refer to the same lab consistently. Same for "Lab 7", "Lab 9".
3. **Check that every quote has an attribution.** If you see a quoted sentence without `(Author, year)`, add one.

### 5.3 Minutes 15-5 before deadline: Page count and final read

1. **Count pages.** If under the minimum, expand one section (most easily, the Discussion — add a paragraph from the portfolio's Capstone Reflection).
2. **If over the maximum, cut the Appendix** first; then the second half of Discussion; then the Concept Location section.
3. **Read your first paragraph and your last paragraph aloud silently.** These are what the grader remembers most. They should be your two best paragraphs.

### 5.4 Final 5 minutes: Submit

1. **Save in two formats** (your editor's native + PDF).
2. **Upload / hand in.**
3. **Walk out without rereading.** Anxiety about what you "could have said" doesn't change the grade — leaving with energy for the next exam does.

---

## Section 6 — Emergency procedures

### 6.1 If the question is completely unfamiliar

Rare, but possible. Procedure:

1. **Look at it for 30 seconds without panicking.**
2. **Identify any one keyword** you recognise — refactoring, testing, SOLID, BDD, phased model, hotspots.
3. **Go to [02-comprehensive-summary.md](02-comprehensive-summary.md)** and Ctrl-F that keyword.
4. **Read the surrounding paragraph.** That paragraph is your starting point.
5. **From the Master Essay, paste the section dealing with that keyword** and adapt outward.

The Master Essay covers every concept in the course; the question cannot be entirely outside the material.

### 6.2 If you blank on a definition

Procedure:

1. **Open [07-glossary.md](07-glossary.md).**
2. **Ctrl-F the term.**
3. **Paste the definition** into your document.
4. **Rewrite it in your own voice** before submitting (don't paste verbatim from a glossary; rewrite).

### 6.3 If the laptop fails

Procedure:

1. **Notify the proctor immediately.**
2. **Use the time the proctor takes** to write the *thesis sentence* and *outline* on paper from memory.
3. **When the laptop is restored**, you have a working spine to fill in.
4. **If the laptop is unrecoverable, ask for paper** and use the outline you wrote. Your essay will be shorter but coherent. Graders are usually flexible about technical failures if proctors confirm them.

### 6.4 If you have 30 minutes left and only the introduction written

Procedure:

1. **Paste Master Essay §11 (Conclusion) immediately.** A complete essay with a thin middle is graded higher than an incomplete essay with a fat introduction.
2. **Then paste the matching core section** (§5 / §6 / §8.1 / §8.2).
3. **Then paste Discussion.**
4. **Skip Verification, skip phases, skip References format.** Get a complete arc on paper first; refine only if time remains.

### 6.5 If a fact you cite turns out to be wrong

Procedure:

1. **Don't panic.** One factual error is rarely catastrophic if everything around it is rigorous.
2. **If you notice during the exam**, fix it. If you notice afterwards, accept it.
3. **Never make up a citation** to cover an error. Graders catch invented citations; honest mistakes are forgiven, fabrication is not.

---

## Section 7 — One-page summary (the actual answer to "what should I do")

Print this section. Keep it next to your laptop.

```
0:00–0:05    Open all 7 tabs. Paste header. Set timer.
0:05–0:15    Read question twice. Underline verb. Underline topic noun.
             Identify essay type (descriptive / analytical / comparative /
             evaluative / applied).
0:15–0:30    Pick spine. Decide which Master Essay sections to keep.
             Paste section headings into your document.
0:30–0:45    Adapt the abstract. Write the introduction.
0:45–2:00    Write the core section (the one matching the question).
             Paste from Master Essay, check first sentence, keep evidence
             sentences, delete off-topic sentences.
2:00–3:00    Write supporting phase sections. Brief Concept Location,
             brief Impact Analysis, full Verification.
3:00–3:30    Write Discussion. Add one paragraph addressing the
             question's evaluative demand.
3:30–3:50    Write Conclusion. Paste from Master Essay; rewrite the
             final sentence.
3:50–4:00    First polish pass: thesis check, transitions, citation pass.
4:00–4:15    Add references section. Check lab numbers consistent.
4:15–4:25    Page count. Trim or expand to fit.
4:25–4:30    Save (native + PDF). Submit. Walk out.
```

(Adjust if your exam is shorter or longer than 4.5 hours.)

---

## Section 8 — What to bring physically

- Charged laptop.
- Charger.
- Mouse (typing 5,000 words with a trackpad is slower).
- Notebook + pen for scratch outlining.
- Water bottle.
- Snacks (nut bars, fruit — protein and slow sugars).
- This playbook printed, plus [01-essay-craft.md](01-essay-craft.md) Section 5 (transition phrases) printed.
- Any allowed reference materials. **Confirm AI restriction with the proctor before opening tools.**

---

## Section 9 — What NOT to do

- **Do not** start writing without an outline.
- **Do not** copy-paste sections in their entirety without adapting the first sentence to the question.
- **Do not** spend more than 30 minutes on the introduction.
- **Do not** write your conclusion last-second from scratch; paste Master Essay §11 and edit.
- **Do not** invent citations.
- **Do not** use first-person ("I think...") unless the question explicitly asks for personal reflection.
- **Do not** check your phone during the exam.
- **Do not** open AI tools or unauthorised external sources — the exam prohibits this and proctors check.
- **Do not** restart sections you've finished. Move forward. Polish at the end.
- **Do not** panic if you fall behind — the Emergency Procedures in Section 6 have fallbacks.

---

## Section 10 — The one thing to remember

If you remember nothing else from this playbook:

> **Open Master Essay. Find the section matching the question. Paste. Adapt the first sentence. Keep the lab references. Submit.**

Everything else in this file is elaboration on that one rhythm.

Good luck.
