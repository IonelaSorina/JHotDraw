# Portfolio

---

## Lab 3 — Dynamic Program Analysis: Initial Concept Location Results

**Feature:** Text Tool
**Subfeatures:** write (create new text figure), edit (modify existing text figure)

**Method:** IDE Debugger breakpoints placed at controller entry points (`TextCreationTool.mousePressed`, `TextFigure.getTool`, `TextEditingTool.mousePressed`). Application executed and user interactions triggered to trace the runtime call chain.

### Initial Set of Classes

| # | Class Name | Package | Subfeature | Role |
|---|---|---|---|---|
| 1 | `TextCreationTool` | `org.jhotdraw.draw.tool` | write | Controller — handles mouse press on empty canvas; orchestrates figure creation and text input |
| 2 | `CreationTool` | `org.jhotdraw.draw.tool` | write | Parent controller — clones prototype figure, adds it to drawing on mouse gesture |
| 3 | `TextEditingTool` | `org.jhotdraw.draw.tool` | edit | Controller — activated on double-click; opens floating field over existing figure |
| 4 | `TextFigure` | `org.jhotdraw.draw.figure` | write, edit | Domain model — stores text attribute, renders single-line text, provides editing tool via `getTool()` |
| 5 | `TextHolderFigure` | `org.jhotdraw.draw.figure` | write, edit | Interface — defines contract: `getText()`, `setText()`, font/insets accessors; used as prototype type |
| 6 | `FloatingTextField` | `org.jhotdraw.draw.text` | write, edit | UI component — creates a `JTextField` overlay on `DrawingView`; bridges Swing input to figure model |
| 7 | `AbstractTool` | `org.jhotdraw.draw.tool` | write, edit | Base class — provides access to `DrawingEditor`, `DrawingView`, and event-dispatch plumbing |

---

## Lab 4 — Change Impact Analysis: Packages Visited

**Table 1 — Packages visited during impact analysis**

| Package name | # of classes | Comments |
|---|---|---|
| `org.jhotdraw.draw.tool` | 6 | Core of the text tool feature. `TextCreationTool` and `TextEditingTool` are the CHANGED entry points; `CreationTool` and `AbstractTool` form their inheritance chain providing prototype-creation and editor-access infrastructure. `TextAreaCreationTool` and `TextAreaEditingTool` are sibling tools that PROPAGATE changes due to sharing the `TextHolderFigure` contract. The package showed that tool behaviour is cleanly separated: creation logic lives in `CreationTool`, text-specific overlay logic in `TextCreationTool`. |
| `org.jhotdraw.draw.figure` | 7 | `TextFigure` and `TextHolderFigure` are CHANGED. `TextAreaFigure` and `LabelFigure` (also implementing/extending `TextHolderFigure`/`TextFigure`) PROPAGATE. `AbstractAttributedDecoratedFigure` — the parent of `TextFigure` — PROPAGATES attribute-delegation changes. `Figure` and `CompositeFigure` are UNCHANGED base types. The package revealed how the figure hierarchy separates rendering, attribute management, and decoration into distinct abstract layers. |
| `org.jhotdraw.draw.text` | 1 | `FloatingTextField` is the sole class and a key CHANGED component. It bridges Swing (`JTextField`) to JHotDraw by overlaying an editable field on the `DrawingView` canvas. The package isolates all text-input UI from the main drawing package, which is a clean architectural boundary. |
| `org.jhotdraw.draw` | 8 | Framework core. `DrawingEditor` and `DrawingView` PROPAGATE because all tools use them for context. `AttributeKeys` PROPAGATES as the registry for `TEXT`, `FONT_SIZE`, and related constants referenced in `TextFigure`. `DrawingEditorProxy` PROPAGATES as it mirrors `DrawingEditor`. `Drawing` is UNCHANGED. The package is the central framework hub; any change ripples through it quickly. |
| `org.jhotdraw.draw.handle` | 4 | `BoundsOutlineHandle`, `MoveHandle`, and `Handle` are UNCHANGED generic handles reused by `TextFigure`. `FontSizeHandle` PROPAGATES because it directly manipulates font size on a `TextHolderFigure`, making it text-tool-specific. The package taught me that handles encode interactive editing gestures and are attached per figure, not per tool. |
| `org.jhotdraw.draw.event` | 5 | `FigureAdapter`, `FigureEvent`, and `FigureListener` are UNCHANGED; `FloatingTextField` uses them to react to attribute changes on the figure being edited (e.g. font changes while the overlay is open). `ToolEvent` and `ToolListener` are UNCHANGED and provide the tool activation/deactivation lifecycle. This package is the Observer/listener layer for both figures and tools. |
| `org.jhotdraw.util` | 1 | `ResourceBundleUtil` PROPAGATES. It loads i18n label strings used in undo presentation names (`"attribute.text.text"`). Any new user-visible text action needs a new key here. The class shows JHotDraw centralises all user-visible strings in resource bundles rather than hardcoding them. |
| `org.jhotdraw.draw.locator` | 1 | `RelativeLocator` is UNCHANGED. It positions handles at named corners (NW, NE, SW, SE) relative to a figure's bounds. Used by `TextFigure.createHandles()`. The package abstracts "where on the figure" from "what the handle does." |
| `org.jhotdraw.geom` | 3 | `Dimension2DDouble`, `Geom`, and `Insets2D` are UNCHANGED geometry utilities. Used by `TextFigure` for bounding-box calculations, inset handling, and layout math. The package provides double-precision geometry types missing from the JDK. |
| `org.jhotdraw.xml` | 2 | `DOMInput` and `DOMOutput` are UNCHANGED. They handle XML read/write for `TextFigure` (position and attributes). The serialisation layer is fully decoupled from tool behaviour — a change to how text is edited does not require touching persistence. |
| `org.jhotdraw.beans` | 1 | `AbstractBean` is UNCHANGED. It is the root base class of `AbstractTool`, providing `PropertyChangeSupport` for JavaBeans-style property events used in tool event dispatching. |
| `org.jhotdraw.samples.svg.figures` | 2 | `SVGTextFigure` and `SVGTextAreaFigure` PROPAGATE. They implement `TextHolderFigure` in the SVG sample application, so any change to the interface contract (e.g. a new method) must be reflected here. The package showed that the text-tool framework extends into sample applications, widening the real impact set beyond the core library. |

---

## Lab 5 — Refactoring: Eliminating Bad Code Smells in the Text Tool

**Feature in scope:** Text Tool (`write` and `edit` subfeatures)

### Code Smells Identified

#### Smell 1 — Duplicated Code (primary, highest severity)

The most significant smell found was **Duplicated Code** (Fowler, Chapter 3) between `TextCreationTool` and `TextEditingTool`. Both classes contained a byte-for-byte identical anonymous `AbstractUndoableEdit` subclass inside their respective `endEdit()` methods. This anonymous class defined `getPresentationName()`, `undo()`, and `redo()` with identical bodies in both files. Any future change to undo/redo behaviour for a text edit — such as a different presentation name or an additional side-effect — would have required the same edit in two places, a fragile situation that is the canonical definition of this smell.

Concrete location: `TextCreationTool.endEdit()` (original lines 148–171) and `TextEditingTool.endEdit()` (original lines 105–127).

Beyond the anonymous class, both tools also share identical `isEditing()` and `updateCursor()` methods, indicating a broader structural duplication rooted in the absence of a shared text-specific base class. This wider observation informed the chosen refactoring strategy.

#### Smell 2 — Unnecessary If Statement (Simplify Conditional)

`TextFigure.figureContains()` wrapped a boolean expression in an `if`-statement that returned `true` in one branch and `false` in the other — a textbook instance of the simplifiable conditional smell:

```java
// before
if (getBounds().contains(p)) { return true; }
return false;
```

The method communicates the same thing more directly as a single `return` expression. Similarly, `getTool()` introduced a local variable `t` solely to return it on the very next line (Fowler's *Inline Temp* candidate).

#### Smell 3 — Dead Code (commented-out code)

Three locations contained commented-out code with no explanatory context:

- `TextCreationTool.endEdit()`: `// view().checkDamage();`
- `TextEditingTool.endEdit()`: `// view().checkDamage();`
- `TextFigure.getTextColumns()`: an alternative implementation left as a comment

Commented-out code is dead weight: it obscures intent, misleads readers about what the code does, and is preserved more reliably by version control than by inline comments.

#### Smell 4 — Deprecated API Usage

`TextFigure.setFontSize()` called `new Double(size)`, a constructor deprecated since Java 9 in favour of `Double.valueOf(size)`. Using deprecated APIs introduces future compatibility risk with no compensating benefit.

#### Smell 5 — Redundant Method Invocation (bug-level smell)

`TextEditingTool.endEdit()` called `typingTarget.willChange()` twice before committing the text: once at the top of the method and once again inside the `if (newText.length() > 0)` branch. The `willChange()` / `changed()` pair is a notification contract; calling `willChange()` twice without an intervening `changed()` breaks the protocol and can cause stale damage regions in the drawing view.

---

### Refactoring Plan and Strategy

The strategy followed Kerievsky's general principle that duplication should be eliminated before introducing higher-level patterns. The changes were applied in order of increasing scope: small localised fixes first, structural extraction last.

**Step 1 — Inline Temp and Simplify Conditional** (`TextFigure`)

Applied Fowler's *Inline Temp* to `getTool()` and *Simplify Conditional Expression* to `figureContains()`. Both are mechanical, behaviour-preserving simplifications that reduce cognitive load without altering any logic.

**Step 2 — Replace Deprecated API** (`TextFigure.setFontSize`)

Replaced `new Double(size)` with `Double.valueOf(size)`. This is a straightforward modernisation; the boxed value returned is identical, but the factory method allows the JVM to cache common values and removes the deprecation warning.

**Step 3 — Remove Dead Code**

Deleted the three commented-out blocks. Version control (git) preserves the history; the inline comments add nothing a reader could not retrieve from `git log` if ever needed.

**Step 4 — Fix Redundant `willChange()` Call** (`TextEditingTool.endEdit`)

Removed the duplicate `willChange()` call from inside the `if`-branch, since `willChange()` was already called unconditionally at the start of the guard block. The outer call correctly marks the figure as about to change; the inner call was redundant and protocol-violating.

**Step 5 — Extract Class: `TextChangeEdit`** (primary structural refactoring)

The duplicated anonymous `AbstractUndoableEdit` was extracted into a named package-private class `TextChangeEdit` in the `org.jhotdraw.draw.tool` package. This is the *Extract Class* refactoring (Fowler, also referenced throughout Kerievsky as a foundational step before applying patterns).

`TextChangeEdit` receives the edited figure, the old text, and the new text as constructor arguments, and encapsulates all undo/redo logic:

```java
class TextChangeEdit extends AbstractUndoableEdit {
    private final TextHolderFigure figure;
    private final String oldText;
    private final String newText;

    TextChangeEdit(TextHolderFigure figure, String oldText, String newText) { … }

    @Override public String getPresentationName() { … }
    @Override public void undo() { … }
    @Override public void redo() { … }
}
```

Both `TextCreationTool.endEdit()` and `TextEditingTool.endEdit()` now replace the 22-line anonymous class with a single constructor call:

```java
getDrawing().fireUndoableEditHappened(new TextChangeEdit(editedFigure, oldText, newText));
```

The unused `javax.swing.undo.AbstractUndoableEdit`, `javax.swing.undo.UndoableEdit`, and `org.jhotdraw.util.ResourceBundleUtil` imports were removed from both tool classes as a consequence.

---

### Applied Refactorings and Reasoning

| # | Refactoring | Location | Smell addressed | Reasoning |
|---|---|---|---|---|
| 1 | Inline Temp | `TextFigure.getTool()` | Unnecessary local variable | Variable `t` served no purpose beyond one line; removing it makes the return intent explicit |
| 2 | Simplify Conditional Expression | `TextFigure.figureContains()` | Unnecessary If Statement | `if (x) return true; return false;` is always replaceable by `return x;` |
| 3 | Replace Deprecated API | `TextFigure.setFontSize()` | Deprecated API | `new Double(float)` deprecated since Java 9; `Double.valueOf` is the canonical replacement |
| 4 | Remove Dead Code | `TextFigure`, `TextCreationTool`, `TextEditingTool` | Commented-out code | Three locations; git history preserves all removed code |
| 5 | Remove Duplicate Code | `TextEditingTool.endEdit()` | Redundant method call | Double `willChange()` breaks the `willChange`/`changed` notification protocol |
| 6 | Extract Class | `TextCreationTool`, `TextEditingTool` → `TextChangeEdit` | Duplicated Code | 22-line anonymous `AbstractUndoableEdit` was identical in both tools; one named class eliminates the duplication and gives the concept an explicit name |

The most architecturally meaningful refactoring is **Extract Class** (step 6). Kerievsky emphasises that giving a concept an explicit name — rather than encoding it as an anonymous structure — makes the design more communicative and easier to extend. `TextChangeEdit` now expresses the domain concept "a reversible change to a text figure's content" as a first-class type, rather than burying it as an anonymous implementation detail inside two different tool classes.
