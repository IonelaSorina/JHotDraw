# 04 — Quotes and Citations

Authoritative quotes organised by topic. Drop at least one quote per essay section for credibility.

> **Rule of thumb:** each quote should be ≤ 25 words and attributed inline. Use blockquote (`>`) formatting for quotes longer than one line. Use `(Author, year, p. X)` citation style consistently.

---

## Quick search by topic

- [§ 1. Software change / evolution / maintenance](#1-software-change--evolution--maintenance)
- [§ 2. Complexity](#2-complexity)
- [§ 3. Refactoring](#3-refactoring)
- [§ 4. SOLID / clean architecture](#4-solid--clean-architecture)
- [§ 5. Clean code](#5-clean-code)
- [§ 6. Testing](#6-testing)
- [§ 7. Behaviour-driven development](#7-behaviour-driven-development)
- [§ 8. Technical debt / behavioural analysis](#8-technical-debt--behavioural-analysis)
- [§ 9. The phased model](#9-the-phased-model)
- [§ 10. Method / engineering culture](#10-method--engineering-culture)

---

## 1. Software change / evolution / maintenance

**Lehman (1980) — Continuing Change:**
> *"A system must be continually adapted or it becomes progressively less satisfactory."*
> — Lehman, *Programs, Life Cycles, and Laws of Software Evolution*, IEEE 1980.

**Lehman (1980) — Increasing Complexity:**
> *"As a system evolves, its complexity increases unless work is done to maintain or reduce it."*
> — Lehman, *Programs, Life Cycles, and Laws of Software Evolution*, IEEE 1980.

**Brooks (1986) — No Silver Bullet:**
> *"The essence of a software entity is a construct of interlocking concepts: data sets, relationships among data items, algorithms, and invocations of functions. This essence is abstract, in that the conceptual construct is the same under many different representations."*
> — Brooks, *No Silver Bullet — Essence and Accidents of Software Engineering*, 1986.

**Lientz & Swanson (1980):**
> *"The maintenance effort is dominated by enhancement and modification, not corrective bug fixes. Perfective maintenance accounts for roughly half of all maintenance work."*
> — paraphrase of Lientz & Swanson 1980, used in SB5-MAI Lecture 1.

**Rajlich (2012) — software change:**
> *"Software change is not the exception in a software product's lifecycle, but the norm. The phased model formalises this norm into a workable engineering discipline."*
> — paraphrase from Rajlich 2012, chapter 2.

---

## 2. Complexity

**Hickey (2011) — Simple Made Easy:**
> *"It's my contention, based on experience, that if you ignore complexity, you will slow down. You will invariably slow down over the long haul … the complexity will eventually kill you. It will kill you in a way that will make every sprint accomplish less."*
> — Rich Hickey, *Simple Made Easy*, Strange Loop 2011.

**Brooks (1975) on essential complexity:**
> *"The complexity of software is an essential property, not an accidental one. Hence descriptions of a software entity that abstract away its complexity often abstract away its essence."*
> — Brooks, *The Mythical Man-Month*, 1975.

**Dijkstra on simplicity (paraphrase):**
> *"Simplicity is a great virtue but it requires hard work to achieve it and education to appreciate it."*
> — attributed to Edsger Dijkstra; cited in many software-engineering textbooks.

---

## 3. Refactoring

**Fowler (1999) — definition:**
> *"Refactoring is the process of changing a software system in such a way that it does not alter the external behavior of the code yet improves its internal structure."*
> — Fowler, *Refactoring*, 1999, p. xvi.

**Fowler (1999) — when to refactor:**
> *"Any fool can write code that a computer can understand. Good programmers write code that humans can understand."*
> — Fowler, *Refactoring*, 1999, p. 15 (attributed to himself).

**Kerievsky (2004) — pattern-directed refactoring:**
> *"Patterns are where you want to be; refactorings are ways to get there from somewhere else."*
> — paraphrase from Kerievsky, *Refactoring to Patterns*, 2004.

**Rajlich (2012) — refactoring shortens propagation:**
> *"Refactoring can shorten the change propagation by moving the code affected by change into fewer classes, or by splitting roles so that only one of the roles needs to be updated."*
> — Rajlich, *Software Engineering: The Current Practice*, 2012, ch. 17.

---

## 4. SOLID / clean architecture

**Martin (2003) — SRP:**
> *"A class should have one, and only one, reason to change."*
> — Robert C. Martin, *Agile Software Development: Principles, Patterns, and Practices*, 2003.

**Meyer (1988) — Open / Closed:**
> *"Software entities (classes, modules, functions, etc.) should be open for extension, but closed for modification."*
> — Bertrand Meyer, *Object-Oriented Software Construction*, 1988.

**Liskov (1987) — Liskov Substitution Principle:**
> *"What is wanted here is something like the following substitution property: if for each object o1 of type S there is an object o2 of type T such that for all programs P defined in terms of T, the behavior of P is unchanged when o1 is substituted for o2, then S is a subtype of T."*
> — Barbara Liskov, *Data Abstraction and Hierarchy*, 1987.

**Martin (2017) — Clean Architecture:**
> *"The overriding rule that makes this architecture work is The Dependency Rule. This rule says that source code dependencies can only point inwards."*
> — Robert C. Martin, *Clean Architecture*, 2017.

**Law of Demeter / Principle of Least Knowledge:**
> *"Each unit should have only limited knowledge about other units: only units 'closely' related to the current unit."*
> — Lieberherr et al., *Object-Oriented Programming: An Objective Sense of Style*, 1988.

---

## 5. Clean code

**Stroustrup (in Martin 2009):**
> *"I like my code to be elegant and efficient. The logic should be straightforward to make it hard for bugs to hide … Clean code does one thing well."*
> — Bjarne Stroustrup, quoted in Martin, *Clean Code*, 2009, ch. 1.

**Booch (in Martin 2009):**
> *"Clean code is simple and direct. Clean code reads like well-written prose. Clean code never obscures the designer's intent."*
> — Grady Booch, quoted in Martin, *Clean Code*, 2009, ch. 1.

**Cunningham (in Martin 2009):**
> *"You know you are working on clean code when each routine you read turns out to be pretty much what you expected."*
> — Ward Cunningham, quoted in Martin, *Clean Code*, 2009, ch. 1.

**Feathers (in Martin 2009):**
> *"Clean code always looks like it was written by someone who cares."*
> — Michael Feathers, quoted in Martin, *Clean Code*, 2009, ch. 1.

**Jeffries (in Martin 2009):**
> *"Reduced duplication, high expressiveness, and early building of simple abstractions."*
> — Ron Jeffries, quoted in Martin, *Clean Code*, 2009, ch. 1.

**Thomas (in Martin 2009):**
> *"Clean code can be read, and enhanced by a developer other than its original author. It has unit and acceptance tests. It has meaningful names."*
> — Dave Thomas, quoted in Martin, *Clean Code*, 2009, ch. 1.

**Martin (2009) — Boy Scout Rule:**
> *"Leave the campground cleaner than you found it. … If we all checked in our code a little cleaner than when we checked it out, the code simply could not rot."*
> — Robert C. Martin, *Clean Code*, 2009, ch. 1.

**Martin (2009) — Comments are failures:**
> *"The proper use of comments is to compensate for our failure to express ourselves in code. … So when you find yourself in a position where you need to write a comment, think it through and see whether there isn't some way to turn the tables and express yourself in code."*
> — Martin, *Clean Code*, 2009, ch. 4.

**Martin (2009) — Functions:**
> *"The first rule of functions is that they should be small. The second rule of functions is that they should be smaller than that."*
> — Martin, *Clean Code*, 2009, ch. 3.

**Holwerda — WTFs/minute:**
> *"The only valid measurement of code quality: WTFs/minute."*
> — Thom Holwerda, popularised by Robert C. Martin.

**Knuth on premature optimisation:**
> *"Premature optimization is the root of all evil."*
> — Donald Knuth, *Structured Programming with goto Statements*, 1974.

---

## 6. Testing

**Dijkstra (1972) — the foundational dictum:**
> *"Testing shows the presence, not the absence of bugs."*
> — Edsger Dijkstra, *Notes on Structured Programming*, EWD249, 1972.

**Beck (2002) — TDD:**
> *"Test-driven development is a way of managing fear during programming."*
> — Kent Beck, *Test-Driven Development by Example*, 2002.

**Beck (1999) — XP testing:**
> *"Any program feature without an automated test simply doesn't exist."*
> — Kent Beck, *Extreme Programming Explained*, 1999.

**Martin (2009) — Three Laws of TDD:**
> *"First Law: You may not write production code until you have written a failing unit test.
> Second Law: You may not write more of a unit test than is sufficient to fail, and not compiling is failing.
> Third Law: You may not write more production code than is sufficient to pass the currently failing unit test."*
> — Martin, *Clean Code*, 2009, ch. 9.

**Martin (2009) — F.I.R.S.T.:**
> *"Fast, Independent, Repeatable, Self-Validating, Timely. These five rules describe the qualities of a clean test."*
> — Martin, *Clean Code*, 2009, ch. 9.

**Lecture 7 — assertions:**
> *"Assertions, Assertions, Assertions !!! Assertion: Executable check for a property that must be true."*
> — SB5-MAI, Lecture 7 (*Software Testing*), slide 14 / 18.

**Lecture 7 — testing incompleteness:**
> *"Theoretical reason for testing incompleteness — it is theoretically impossible to create a perfect test suite. Programmers have been trying to do the best under the circumstances."*
> — SB5-MAI, Lecture 7, slide 5.

---

## 7. Behaviour-driven development

**JGiven principles (from Lecture 9):**
> *"Behavior is described in a common domain language understandable by domain experts; domain experts and developers collaborate on defining the behavior; scenarios are executed like normal tests; the result is a living documentation."*
> — SB5-MAI, Lecture 9, slide 4 (paraphrasing JGiven docs).

**Lecture 9 — why BDD:**
> *"Typical test issues: many technical and often irrelevant details; point of the test often hard to grasp; code duplication; can only be read by developers; cannot be used as documentation."*
> — SB5-MAI, Lecture 9, slide 3.

**Lecture 9 — JGiven trade-off:**
> *"Domain experts can not write scenarios in JGiven."*
> — SB5-MAI, Lecture 9, slide 20 (JGiven summary).

**Rajlich (2012) — acceptance test tooling:**
> *"Tool JGiven and Mockito used to run the functional tests."*
> — Rajlich, *Software Engineering: The Current Practice*, 2012, ch. 17, slide 27.

---

## 8. Technical debt / behavioural analysis

**Ford, Parsons & Kia (2017) — definition:**
> *"Stuff that isn't supposed to be there and is in the way of the stuff that is supposed to be there."*
> — Ford, Parsons & Kia, *Building Evolutionary Architectures*, 2017, p. 110.

**Cunningham (1992) — the original debt metaphor:**
> *"Shipping first-time code is like going into debt. A little debt speeds development so long as it is paid back promptly with a rewrite. … The danger occurs when the debt is not repaid."*
> — Ward Cunningham, *The WyCash Portfolio Management System*, OOPSLA 1992.

**Tornhill (2018) — hotspot definition:**
> *"A hotspot is a complicated code that you have to work with often."*
> — Adam Tornhill, *Software Design X-Rays*, 2018.

**Tornhill (Lec 11) — static analysis limit:**
> *"Static analysis will never be able to tell you if that excess code complexity actually matters — just because a piece of code is complex doesn't mean it's a problem."*
> — SB5-MAI, Lecture 11 (paraphrasing Tornhill).

**Graves et al. (2000) — process vs product:**
> *"Process measures based on the change history are more useful in predicting fault rates than product metrics of the code: the number of times code has been changed is a better indication of how many faults it will contain than is its length."*
> — Graves, Karr, Marron & Siy, *Predicting Fault Incidence Using Software Change History*, IEEE TSE 2000.

**Tornhill (Lec 11) — legacy code redefinition:**
> *"Legacy code is typically used to describe the code that lacks in quality (relative perspective) or we didn't write ourselves."*
> — SB5-MAI, Lecture 11, slide 21.

**Tornhill (Lec 11) — conclusions:**
> *"Technical debt is a real problem regardless of programming language. There's a huge amount of useful information stored in your version control system. Ultimately, you need to rely on human expertise."*
> — SB5-MAI, Lecture 11, slide 25.

---

## 9. The phased model

**Rajlich (2012) — phase model purpose:**
> *"The phased model is not bureaucracy. It is the rhythm every change in a codebase follows whether the engineer notices or not. The model's value lies in making the engineer notice."*
> — paraphrase from Rajlich, *Software Engineering: The Current Practice*, 2012, ch. 2.

**Rajlich (2012) — concept location:**
> *"Concept location is the most error-prone phase of the change model — wrong-way / backtrack / right-way is the normal shape, not a failure mode."*
> — paraphrase from Rajlich 2012, ch. 4 / 17.

**Rajlich (2012) — change propagation:**
> *"During change propagation, the developer makes one local edit and a wave of inconsistencies spreads outward, each requiring further edits until the system is consistent again."*
> — paraphrase from Rajlich 2012, ch. 6.

**Rajlich (2012) — conclusion phase:**
> *"The last phase of software change. The activities depend on the specific software process. … A thorough testing is required: this testing will guarantee that the new baseline is as bug free as possible — the new baseline represents a progress of the project, not a regression."*
> — paraphrase from Rajlich 2012, ch. 11.

**Rajlich (2012) — test maintenance:**
> *"Tests from the old version that are not affected by the change are kept as regression tests for the future. Obsolete tests are removed. Tests of the new features are added after the change."*
> — Rajlich 2012, ch. 17, slide 29.

---

## 10. Method / engineering culture

**Martin (2017) — architecture as a moral choice:**
> *"The goal of software architecture is to minimize the human resources required to build and maintain the required system."*
> — Robert C. Martin, *Clean Architecture*, 2017, ch. 2.

**Cunningham — Wiki/XP/TDD philosophy:**
> *"It's all talk until the tests run."*
> — Ward Cunningham, widely attributed.

**Beck (1999) — XP courage:**
> *"Code for today, design for tomorrow."*
> — Kent Beck, *Extreme Programming Explained*, 1999.

**Beck (1999) — simple design:**
> *"Make it work, make it right, make it fast."*
> — Kent Beck, attributed (and ordered).

**Beck — four rules of simple design (in priority order):**
> *"(1) Runs all the tests. (2) No duplication. (3) Expressive. (4) Minimal classes and methods."*
> — Kent Beck, in Martin 2009, ch. 12.

**Tornhill (Lec 11) — final recommendation:**
> *"Support your developer's judgment and experience with data to get the highest ROI."*
> — SB5-MAI, Lecture 11, slide 25.

---

## How to integrate quotes in essay prose

**Pattern 1 — quote as anchor (best for introducing a concept):**
> Lehman's first law states that *"a system must be continually adapted or it becomes progressively less satisfactory"* (Lehman, 1980). This empirical observation is the foundation on which …

**Pattern 2 — quote as evidence (best for defending a claim):**
> The author's Lab 7 work added 24 unit tests but only 6 production assertions. Compared to LLVM's ratio — approximately one assertion per 110 lines of code (SB5-MAI Lecture 7) — this is dense for the affected module but sparse for the project as a whole.

**Pattern 3 — quote in dialogue (best for evaluating two positions):**
> Where Martin argues that *"comments are failures"* (Martin, 2009, ch. 4), the more moderate view is that intent comments — the *why*, not the *what* — remain on the *good comments* list even in Martin's own taxonomy.

**Pattern 4 — quote at conclusion (best for landing the plane):**
> The course's final lecture closes with Tornhill's four-line summary: *"Technical debt is a real problem regardless of programming language. There's a huge amount of useful information stored in your version control system. Ultimately, you need to rely on human expertise. Support your developer's judgment and experience with data to get the highest ROI"* (SB5-MAI Lecture 11). The fourth line is the course's working stance.

---

**Next:** open file 05 ([Implementations and Code](05-implementations-and-code.md)).
