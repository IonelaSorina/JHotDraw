# 05 — Implementations and Code

Code patterns from the labs, ready to *describe* (not retype) in the essay.

> **Use rule:** in the essay, you describe what the pattern does and cite the file path. Don't paste large code blocks unless explicitly required. The patterns here are what *you wrote*, so you can speak about them with authority.

---

## Pattern 1 — Compose Method refactoring

**Where:** [GroupAction.java](../jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java) — Lab 4 prefactoring.

**Before (one 67-line method, mixing levels of abstraction):**

```java
@Override
public void actionPerformed(ActionEvent e) {
    if (isGroupingAction) {
        // 30 lines: clone prototype, build undo edit, call groupFigures
        ...
    } else {
        // 30 lines: get selected group, build undo edit, call ungroupFigures
        ...
    }
}
```

**After (one dispatch + two single-purpose methods):**

```java
@Override
public void actionPerformed(ActionEvent e) {
    if (isGroupingAction) {
        performGroup();
    } else {
        performUngroup();
    }
}

private void performGroup() { ... }
private void performUngroup() { ... }
```

**Essay use:** *"In the author's own work on JHotDraw, the Compose Method refactoring reduced a 67-line `actionPerformed` method to a 5-line dispatch plus two single-purpose helpers, demonstrating Fowler's principle that each method should operate at one level of abstraction."*

**Connects to:** Lec 4 (refactoring catalogue), Lec 6 (functions should be small, do one thing).

---

## Pattern 2 — Dead-code removal as Boy Scout cleanup

**Where:** [UngroupAction.java](../jhotdraw-core/src/main/java/org/jhotdraw/draw/action/UngroupAction.java) — Lab 4.

**Removed:**
- A `private CompositeFigure prototype` field that shadowed the parent class field.
- A stale `// XXX - This code is redundant with UngroupAction` comment.

**Essay use:** *"The author's Lab 4 work included two Boy Scout Rule cleanups in `UngroupAction.java`: removing a dead `prototype` shadow field (4 lines) and a stale `XXX` comment (1 line). The cost was approximately five minutes; the benefit is one class of future confusion eliminated permanently."*

**Connects to:** Lec 6 (Boy Scout Rule, comments are failures), Lec 4 (refactoring).

---

## Pattern 3 — Production assertion as invariant documentation

**Where:** [GroupAction.java](../jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java) `groupFigures()` and `ungroupFigures()` — Lab 7.

**Code added:**

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

**Essay use:** *"Six production assertions added to JHotDraw's `GroupAction` document invariants that the existing guard methods (`canGroup`, `canUngroup`) are believed to uphold. The assertions cost almost nothing at runtime (disabled by default unless `-ea` is passed) but catch future callers that bypass the guards. Lecture 7's recommended density — approximately one assertion per 110 lines of code in LLVM (SB5-MAI Lecture 7) — places this file at six times the average density and the project as a whole far below it."*

**Connects to:** Lec 7 (assertions, invariants, three assertion rules), Lec 6 (express yourself in code, not comments).

---

## Pattern 4 — JUnit 4 unit test with Mockito

**Where:** [GroupActionTest.java](../jhotdraw-core/src/test/java/org/jhotdraw/draw/action/GroupActionTest.java) — Lab 7.

**Skeleton:**

```java
public class GroupActionTest {
    private DrawingEditor editor;
    private DrawingView view;
    private Drawing drawing;
    private GroupFigure prototype;
    private GroupAction action;

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

    @Test
    public void canGroup_returnsTrue_whenSelectionHasMoreThanOneFigure() {
        when(view.getSelectionCount()).thenReturn(3);
        assertTrue(action.canGroup());
    }

    @Test
    public void canGroup_returnsFalse_whenSelectionHasExactlyOneFigure() {
        when(view.getSelectionCount()).thenReturn(1);
        assertFalse(action.canGroup());
    }
    // ... 22 more tests
}
```

**Key features for the essay:**
- **`@Before` setUp** establishes the same fixture for all tests (F.I.R.S.T. Independence).
- **Mocks (DrawingEditor, DrawingView, Drawing) + real instances (GroupFigure)** — used real where `getClass()` identity matters (the "mockability tax").
- **One test = one boundary or one path** through the SUT.

**Essay use:** *"The author's Lab 7 added 24 such unit tests covering best case, boundary case, and failure-mode paths through `GroupAction`. Each test is named after the property it pins — `canGroup_returnsFalse_whenSelectionHasExactlyOneFigure` is both a test method and a one-sentence specification. This naming pattern is what Lecture 6 calls 'intention-revealing names' applied to tests."*

**Connects to:** Lec 6 (clean tests F.I.R.S.T.), Lec 7 (mock-vs-stub-vs-spy taxonomy), Lec 9 (the layer above this is BDD).

---

## Pattern 5 — The "mockability tax" — why a refactor would help

**Where:** [GroupAction.java](../jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java) `canUngroup()` — Lab 7 reflection.

**The current code:**

```java
protected boolean canUngroup() {
    return getView() != null
            && getView().getSelectionCount() == 1
            && prototype != null
            && getView().getSelectedFigures().iterator().next().getClass().equals(
                    prototype.getClass());
}
```

**Why it's a problem for testing:**
- `Object.getClass()` is final in Java.
- Mockito cannot stub final methods on regular mocks.
- Therefore the *only* way to test the class-equality branch is with **real** `GroupFigure` / `RectangleFigure` instances.
- Lab 7 had to use real figures specifically for these tests, splitting the test code's mocking strategy in two.

**The refactor that would fix it (Lec 10's *Splitting Roles* / Lec 5's polymorphism):**

```java
// In CompositeFigure interface:
boolean matches(Figure other);

// In GroupAction.canUngroup:
return ...
    && prototype.matches(getView().getSelectedFigures().iterator().next());
```

Now `matches()` is a regular method, fully stubbable on a Mockito mock.

**Essay use:** *"The author's Lab 7 testing of `GroupAction.canUngroup` exposed a 'mockability tax': because Java's `Object#getClass()` is final and not stubbable, the test code is forced to use real concrete figure classes. The fix — known in Lecture 10 as 'Splitting Roles' and in Lecture 6 as 'Replace switch on type code with polymorphism' — is to introduce a `prototype.matches(figure)` query on the prototype itself. Three lectures converge on the same single-line change."*

**Connects to:** Lec 5 (DIP, polymorphism), Lec 6 (replace type-code switch with polymorphism), Lec 7 (mock-vs-stub-vs-spy), Lec 10 (splitting roles).

---

## Pattern 6 — JGiven stage class for BDD

**Where:** [GivenADrawing.java](../jhotdraw-core/src/test/java/org/jhotdraw/draw/action/bdd/GivenADrawing.java) — Lab 9.

**Skeleton (Given stage):**

```java
public class GivenADrawing extends Stage<GivenADrawing> {

    @ProvidedScenarioState
    DrawingEditor editor;
    @ProvidedScenarioState
    DrawingView view;
    @ProvidedScenarioState
    Drawing drawing;
    @ProvidedScenarioState
    Set<Figure> selection = new LinkedHashSet<>();

    public GivenADrawing a_drawing_editor() {
        editor = mock(DrawingEditor.class);
        view = mock(DrawingView.class);
        drawing = new DefaultDrawing();
        Mockito.when(editor.getActiveView()).thenReturn(view);
        // ...
        return self();
    }

    public GivenADrawing $_rectangle_figures_on_the_canvas(int count) {
        for (int i = 0; i < count; i++) {
            drawing.add(new RectangleFigure(i * 10, i * 10, 8, 8));
        }
        return self();
    }
    // ...
}
```

**Key features:**
- **`Stage<GivenADrawing>`** — extends JGiven's typed stage base class.
- **`@ProvidedScenarioState`** — fields written here, read by When and Then stages.
- **Fluent return type `GivenADrawing`** — enables `given().a_drawing_editor().and().$_rectangle_figures_on_the_canvas(2)`.
- **`$` placeholder in method name** — JGiven substitutes the argument in the rendered report.

**Essay use:** *"JGiven's stage classes are the framework's unique feature (SB5-MAI Lecture 9): one class per Given/When/Then phase, with typed `@ScenarioState` fields making the data flow between phases explicit. The author's `GivenADrawing` class in Lab 9 builds a real `DefaultDrawing` but mocks the `DrawingView` and `DrawingEditor` — a deliberate mixing of integration-level state (the drawing) with unit-level isolation (the Swing wiring)."*

**Connects to:** Lec 5 (SRP applied to test classes), Lec 9 (JGiven, stage classes, data flow annotations).

---

## Pattern 7 — JGiven scenario method (the BDD payoff)

**Where:** [GroupUngroupScenarioTest.java](../jhotdraw-core/src/test/java/org/jhotdraw/draw/action/bdd/GroupUngroupScenarioTest.java) — Lab 9.

**A complete scenario:**

```java
@Test
@Description("US-1: grouping two selected rectangles merges them into a single group")
public void grouping_two_selected_rectangles_replaces_them_with_a_single_group_of_two() {
    given().a_drawing_editor()
       .and().$_rectangle_figures_on_the_canvas(2)
       .and().all_figures_are_selected();

    when().invokes_the_group_action();

    then().contains_exactly_one_group_with_$_rectangle_children(2);
}
```

**Why this is the BDD payoff:**
- The test *reads like English*.
- Method names with underscores → JGiven renders them as words in the HTML5 report.
- The `@Description` is the *user story* the test pins.
- The test body has no setup boilerplate — that's in the Given stage.

**Generated report excerpt (console output, Lab 9):**

```
US-1: grouping two selected rectangles merges them into a single group

   Given a drawing editor
     And 2 rectangle figures on the canvas
     And all figures are selected
    When invokes the group action
    Then contains exactly one group with 2 rectangle children
```

**Essay use:** *"BDD's value is most visible in the generated report (SB5-MAI Lecture 9, slide 17). The above scenario produces an English-readable acceptance-test record without the author writing any prose — the source code and the documentation are the same artefact. This is the 'living documentation' property: if the production code stops behaving as the scenario describes, the build fails, and the documentation cannot drift silently."*

**Connects to:** Lec 9 (BDD, living documentation), Lec 6 (intention-revealing names, comments are failures — the scenario *is* the comment).

---

## Pattern 8 — AssertJ fluent assertion

**Where:** [ThenTheDrawing.java](../jhotdraw-core/src/test/java/org/jhotdraw/draw/action/bdd/ThenTheDrawing.java) — Lab 9.

**Example:**

```java
public ThenTheDrawing contains_exactly_one_group_with_$_rectangle_children(int expected) {
    assertThat(drawing.getChildren())
            .as("drawing should now contain a single composite figure")
            .hasSize(1);
    Figure only = drawing.getChildren().get(0);
    assertThat(only)
            .as("the remaining figure should be a CompositeFigure")
            .isInstanceOf(CompositeFigure.class);
    CompositeFigure group = (CompositeFigure) only;
    assertThat(group.getChildren())
            .as("the group should contain the originally-selected figures")
            .hasSize(expected)
            .allMatch(f -> f instanceof RectangleFigure);
    return self();
}
```

**Key features for the essay:**
- **`assertThat(collection).hasSize(n).allMatch(...)`** — fluent chaining reads as one sentence.
- **`.as("...")`** — labels the assertion so failure messages are in domain language.
- **Predicate as inline lambda** — for simple checks; a *custom Condition* (Lec 9) would be appropriate for reuse.

**Essay use:** *"AssertJ's fluent API (SB5-MAI Lecture 9) is a deliberate response to JUnit's terse `assertEquals(expected, actual)` form. The chained predicates read as a single sentence about the value under test, and the `.as(...)` description is the failure message the developer reads at 3 AM. Lab 9 used AssertJ inside JGiven Then-stages, demonstrating the recommended pairing."*

**Connects to:** Lec 9 (AssertJ rationale, custom Conditions, custom AbstractAssert subclasses).

---

## Pattern 9 — Maven POM dependency addition

**Where:** [jhotdraw-core/pom.xml](../jhotdraw-core/pom.xml) — Lab 7 + Lab 9.

**Lab 7 additions (JUnit + Mockito):**

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

**Lab 9 additions (JGiven + AssertJ):**

```xml
<dependency>
    <groupId>com.tngtech.jgiven</groupId>
    <artifactId>jgiven-junit</artifactId>
    <version>1.3.1</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.25.3</version>
    <scope>test</scope>
</dependency>
```

**Lab 9 Surefire workaround (JDK 25 + JGiven 1.3.1):**

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <argLine>--add-opens=java.base/java.lang=ALL-UNNAMED</argLine>
    </configuration>
</plugin>
```

**Essay use:** *"The author's Lab 9 work surfaced an interesting reproducibility issue: JGiven 1.3.1 bundles an older ByteBuddy that cannot define classes under JDK 25's strict module system. The fix — a one-line Surefire `argLine` to open `java.base/java.lang` — illustrates Brooks's *Conformity* essential difficulty (SB5-MAI Lecture 1): software must conform to its evolving environment, and even a green-field test framework can collide with a JDK release it was never built against."*

**Connects to:** Lec 1 (essential difficulties, especially Conformity), build/CI mechanics (Lab 3).

---

## Pattern 10 — DateServer pattern (DI for time-dependent code)

**Where:** Discussed in [Lecture 7](../portfolio/portfolio.md#lecture-7--software-testing-how-to-make-software-fail) — not in the JHotDraw codebase, but a pattern the author can describe.

**Naive form (untestable):**

```java
public class LibraryApp {
    public Calendar getDate() {
        return new GregorianCalendar();  // hard-coded to "now"
    }
}
```

**Testable form (DateServer pattern):**

```java
public class LibraryApp {
    private DateServer dateServer;
    public LibraryApp(DateServer dateServer) {
        this.dateServer = dateServer;
    }
    public Calendar getDate() {
        return dateServer.getDate();
    }
}

public interface DateServer {
    Calendar getDate();
}

// In production:
new LibraryApp(() -> new GregorianCalendar());

// In tests:
DateServer mockClock = mock(DateServer.class);
when(mockClock.getDate()).thenReturn(specificDate);
new LibraryApp(mockClock);
```

**Essay use:** *"The DateServer pattern (SB5-MAI Lecture 7) is the Dependency Inversion Principle applied to time. The naive way to write the code — `new GregorianCalendar()` directly in the method — is the *untestable* way; the testable form requires one level of indirection. The pattern generalises: any hard-coded dependency on an external service (clock, file system, network) is a testability obstacle that DIP removes."*

**Connects to:** Lec 5 (DIP), Lec 7 (DateServer specifically, mock-vs-stub).

---

## Pattern 11 — GitHub Actions CI workflow (Lab 3)

**Where:** `.github/workflows/...` (set up in Lab 3).

**Essence:**

```yaml
name: Java CI
on:
  pull_request:
    branches: [develop]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with: { distribution: temurin, java-version: '8' }
      - run: mvn -B -s .maven-settings.xml test
```

**Essay use:** *"The author's Lab 3 GitHub Actions workflow mechanises the *Conclusion* phase of Rajlich's model: every pull request to `develop` triggers `mvn test`, and the build is the baseline check. This is the technical implementation of *baseline as deadline* (SB5-MAI Lecture 10): the deadline to commit is the time when CI starts running, and a missed deadline means the PR sits red until fixed."*

**Connects to:** Lec 3 (CI), Lec 10 (Conclusion phase, baseline mechanics).

---

## Pattern 12 — Resource bundle locale fragility (the Fragile Test catch)

**Where:** JHotDraw `Labels.properties` + `Labels_de.properties`.

**The latent bug:**

```java
// In GroupAction:
private static ResourceBundleUtil getLabels() {
    return ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
}
```

The bundle is resolved via the JVM default locale. `Labels_de.properties` exists in JHotDraw. A test running under `-Duser.language=de` resolves different label strings.

**Why it's *latent*:** Lab 7's tests do not assert on label *content*, so the fragility never manifests. But the *category* — Lec 7's Context Sensitivity — is real.

**Essay use:** *"The author's Lab 7 test suite carries a latent context-sensitivity (SB5-MAI Lecture 7): the `Labels.properties` resource bundle is locale-resolved against the JVM default. A tester running the suite under `-Duser.language=de` would resolve `Labels_de.properties` instead. The current tests do not assert on label content so the fragility is dormant — but a future test that *does* would silently fail under a non-default locale. This is exactly the fragility category that Lecture 7's framing names but that the author would not have noticed without it."*

**Connects to:** Lec 7 (Fragile Test Problem, context sensitivity).

---

## How to use code patterns in the essay

**Pattern A — single reference (most common):**
> "The author's Lab 4 work refactored `GroupAction.actionPerformed` from a 67-line method into a 5-line dispatch plus two single-purpose helpers ([GroupAction.java](../jhotdraw-core/src/main/java/org/jhotdraw/draw/action/GroupAction.java))."

**Pattern B — small inline snippet (when 1-3 lines clarify the claim):**
> "The production assertion `assert figures != null && !figures.isEmpty() : "groupFigures requires at least one figure";` documents an invariant that the existing `canGroup()` guard upstream is believed to uphold."

**Pattern C — comparison with the literature:**
> "JGiven's stage class form — `public class GivenADrawing extends Stage<GivenADrawing>` — is the framework's structural innovation (Lecture 9): the typed self-reference allows fluent chaining while making the stage's identity explicit."

**Pattern D — pattern as evidence in a larger argument:**
> "The Boy Scout Rule is operational, not aspirational. The author's Lab 4 removed a dead field, a stale comment, and a 67-line method — three Boy-Scout-Rule moves in one PR. Each took less than ten minutes. Cumulatively, they remove an entire class of confusion from the codebase."

---

**Next:** open file 06 ([Numbers and Frameworks](06-numbers-and-frameworks.md)).
