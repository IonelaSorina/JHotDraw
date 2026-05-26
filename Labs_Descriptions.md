# Soft Maintenance Labs

> **Run command:**
> ```bash
> cd jhotdraw-samples/jhotdraw-samples-misc && mvn exec:java "-Dexec.mainClass=org.jhotdraw.samples.svg.Main"
> ```

---

## Lab L1

1. Install Maven, JDK 11, downgrade to Java 11 if necessary
2. Fork and install JHotDraw
3. Run from VSCode — GUI opens
4. App opens

---

## Lab L2

### A. ChangeReqLab

### B. ConceptLocationLab

- Run just the debug
- Use `Cmd+P` (or the search icon on the left), search for `TextTool` → `TextCreationTool` (universal search)

**TextCreationTool — put breakpoints for:**

- Go to Run & Debug on the left (bug + play logo) to see which classes interact with what you breakpointed

| Domain Class | Responsibility |
|---|---|
| `TextCreationTool` | Handles user interaction for creating text figures |
| `CreationTool` | Clones prototype and initializes new figure |
| `SVGTextFigure` | Represents text element in SVG drawing |
| `QuadTreeDrawing` | Stores and manages figures in the drawing model |
| `DefaultDrawingView` *(NOT domain class)* | Provides access to the active drawing |

**Debugger trace results:**

- ✔ Breakpoint triggered in `CreationTool.mousePressed`
- ✔ `createFigure()` cloned `SVGTextFigure`
- ✔ The prototype was `SVGTextFigure`
- ✔ The figure was added to `QuadTreeDrawing`

> This is a correct dynamic concept location for Feature 53. You did not guess — you confirmed at runtime.

### Concept Location – Feature 53 (Text Tool: Write)

To localize the implementation of Feature 53, the IDE debugger in VS Code was used to perform dynamic analysis. A breakpoint was placed in `CreationTool.mousePressed(MouseEvent)` to observe the runtime behavior when the Text Tool is used.

After running the application in debug mode and clicking on the canvas with the Text Tool selected, execution paused at the breakpoint. By stepping through the code, the runtime call sequence was analyzed.

The method `createFigure()` was invoked, which cloned the prototype `SVGTextFigure`. This confirmed that `SVGTextFigure` is the domain class representing text elements in the drawing. After creation, the figure was added to the drawing model through `getDrawing().add(createdFigure)`, which resolved to the concrete class `QuadTreeDrawing`.

The dynamic trace showed that the feature involves:
- Interaction logic in `TextCreationTool`
- Object creation in `CreationTool`
- Domain representation in `SVGTextFigure`
- Model management in `QuadTreeDrawing`

---

## Lab 3

CI pipeline for building on pull request, with automatically executed tests in CI.

| Package Name | # of Classes | Comments |
|---|---|---|
| `org.jhotdraw.draw.tool` | 20 | Core entry point. `TextCreationTool` handles writing new text; `TextEditingTool` / `TextAreaEditingTool` handle in-place editing. `TextAreaCreationTool` supports multi-line creation. These directly implement both subfeatures. |
| `org.jhotdraw.draw.figure` | 27 | Defines the data model. `TextHolderFigure` is the interface; `TextFigure` (single-line) and `TextAreaFigure` (multi-line) are the concrete figures that store and render text content. |
| `org.jhotdraw.draw.text` | 3 | Contains `FloatingTextField` and `FloatingTextArea` — the temporary Swing widgets overlaid on the canvas during text editing. Central to how the edit subfeature works. |
| `org.jhotdraw.draw.handle` | 26 | `TextOverflowHandle` signals text overflow in `TextAreaFigure`; `FontSizeHandle` allows drag-resizing font. Both are activated when a text figure is selected. |
| `org.jhotdraw.draw.io` | 8 | `TextInputFormat` lets text be pasted/imported into figures. Relevant to how text content enters the drawing from external sources. |
| `org.jhotdraw.samples.svg.figures` | 15 | `SVGTextFigure` and `SVGTextAreaFigure` extend the core text figures for the SVG sample app, showing the feature is implemented via inheritance from the core. |
| `org.jhotdraw.draw.gui` | 7 | `JAttributeTextField` and `JAttributeTextArea` are attribute-bound GUI widgets used in tool option panels to set text properties (font, size). |

---

## Lab 4

**Commit:** https://github.com/IonelaSorina/JHotDraw/commit/e50c4f97f2fa9cc4facc07a34854844c2a86331f

Link to commit with refactoring of code smells in text tool feature of choice.

### Code Smells Identified (Chapter 4, [Ker05])

**Smell 1: Duplicated Code**

`TextCreationTool.endEdit()` and `TextEditingTool.endEdit()` both contain an anonymous `AbstractUndoableEdit` subclass that is copy-pasted verbatim. Each defines identical `getPresentationName()`, `undo()`, and `redo()` methods — the same resource bundle lookup, the same `willChange()` / `setText()` / `changed()` call sequence. Any future change to undo/redo behavior would need to be applied in two places, which is fragile and error-prone. This is textbook **Duplicated Code** (Fowler / [Ker05] Ch. 4).

**Smell 2: Refused Bequest**

`TextEditingTool` extends `AbstractTool` and is required to implement `mouseDragged(MouseEvent)`. Instead of providing a valid (even empty) override, it throws `new UnsupportedOperationException("Not supported yet.")`. This violates the Liskov Substitution Principle — any caller holding an `AbstractTool` reference and invoking `mouseDragged` will crash at runtime. The subclass refuses to honor its parent's contract, which is the definition of **Refused Bequest** ([Ker05] Ch. 4).

### What Was Changed

- A new class `TextUndoableEdit` was created in the `draw.tool` package to encapsulate the undo/redo behavior for text changes on a `TextHolderFigure`.
- The duplicated anonymous `AbstractUndoableEdit` blocks in both `TextCreationTool.endEdit()` and `TextEditingTool.endEdit()` were replaced with `new TextUndoableEdit(editedFigure, oldText, newText)`.
- The `mouseDragged` override in `TextEditingTool` was changed to an empty method body, correctly fulfilling the parent contract without throwing.
- Now-unused imports (`AbstractUndoableEdit`, `UndoableEdit`, `ResourceBundleUtil`) were removed from both tool classes.

### Refactoring Strategy

**Applied: Extract Class** ([Ker05])

The strategy was to identify the repeated anonymous class as an implicit concept — "an undoable text edit" — that existed in the code but had no explicit representation. By extracting it into `TextUndoableEdit`, the concept becomes named, testable, and owned in one place. The two call sites become one-liners that clearly express intent.

The reasoning: anonymous inner classes that repeat across unrelated class hierarchies are a strong signal that a concept is being implemented implicitly. Making it explicit via **Extract Class** eliminates the duplication, reduces the blast radius of future changes, and improves readability.

**Applied: Remove Dead Code / Correct Override**

For the `mouseDragged` smell, simply replacing the exception with an empty body corrects the Refused Bequest. An empty override is the appropriate implementation for a tool that does nothing on drag.

### Files Changed

| File | Change |
|---|---|
| `TextUndoableEdit.java` | New class — extracted undo/redo logic |
| `TextCreationTool.java` | `endEdit()` uses `TextUndoableEdit`; unused imports removed |
| `TextEditingTool.java` | Same + `mouseDragged` fixed |

---

## Lab 5

### SOLID Principles in JHotDraw

**S — Single Responsibility**

`Handle` interface — each `Handle` subclass does exactly one manipulation task:
- `Handle.java` — interface defines "manipulate one figure aspect"
- `MoveHandle` moves, `ResizeHandle` resizes, `DragHandle` drags — zero overlap

`Locator` interface — one job: return a point on a figure:
- `Locator.java`
- `RelativeLocator`, `BezierPointLocator`, `FontSizeLocator` — each one strategy

**O — Open/Closed**

Tool hierarchy — open for extension, closed for modification:
- `Tool.java` — stable interface
- `AbstractTool.java` — base
- `SelectionTool`, `CreationTool`, `TextAreaEditingTool` extend without touching `AbstractTool`

Figure hierarchy:
- `Figure.java` — core contract never changes
- `AbstractFigure.java` — common behavior
- `RectangleFigure`, `TextFigure`, `EllipseFigure` add shape-specific logic only

**L — Liskov Substitution**

Drawing implementations fully interchangeable:
- `Drawing.java` — interface
- `DefaultDrawing` (linear search) ↔ `QuadTreeDrawing` (spatial index) — swap at runtime, callers unaware

`DragTracker` implementations:
- `DragTracker.java`
- `DefaultDragTracker` ↔ `DnDTracker` — `SelectionTool` uses either transparently

**I — Interface Segregation**

`CompositeFigure` — composition methods only, not in base `Figure`:
- `CompositeFigure.java` — `add()`, `remove()`, `getChild()` isolated

`DecoratedFigure` — decorator methods only:
- `DecoratedFigure.java` — just `setDecorator()` / `getDecorator()`

I/O split — readers don't carry write methods:
- `InputFormat.java` — read only
- `OutputFormat.java` — write only

**D — Dependency Inversion**

`DrawingEditor` — high-level module depends on abstraction:
- `DrawingEditor.java` — abstract interface
- `DefaultDrawingEditor.java` — concrete impl
- Tools and Views code against `DrawingEditor`, never `DefaultDrawingEditor`

Strategy abstractions — concrete implementations injected, not hardcoded:
- `Constrainer.java` — `DrawingView` holds this abstraction
- `Layouter.java` — `CompositeFigure` holds this

---

### Clean Architecture in JHotDraw

JHotDraw maps directly onto Clean Architecture's concentric rings:

```
┌──────────────────────────────────────┐
│  UI / Application (JHotDraw Apps)    │  ← outermost, knows everything
├──────────────────────────────────────┤
│  DrawingEditor (Mediator/Controller) │  ← orchestrates views + tools
├──────────────────────────────────────┤
│  DrawingView + Tool + Handle         │  ← interaction layer
├──────────────────────────────────────┤
│  Drawing + Figure (Domain Model)     │  ← innermost, knows nothing else
└──────────────────────────────────────┘
         Dependencies point INWARD only
```

- **Domain layer** (`Drawing.java`, `Figure.java`) — zero imports of View, Editor, or Tool. Model can exist and render without any UI.
- **View layer** (`DrawingView.java`) — renders the domain model; defined painting sequence: background → canvas → constrainer → figures → handles → tool.
- **Controller layer** (`DrawingEditor.java`) — Mediator pattern: routes active tool/view, never couples tools directly to views.
- **Tool/Handle layer** (`Tool.java`, `Handle.java`) — user gestures translated to model mutations; tools never touch Swing directly.
- **I/O layer** (`InputFormat`, `OutputFormat`) — completely outside core; `Drawing` holds format abstractions only, never concrete XML/image parsers.

**Actualization connection:** adding new functionality (e.g., text tool) means extending `AbstractTool` → zero modification to core domain. Change propagation is minimal — only the tool class and any new `Figure` subclass. This is Clean Architecture's main promise: new features stay in outer rings.

---

## Lab 7

### 1. Dependencies Added — `jhotdraw-core/pom.xml`

- `junit:junit:4.13.2` (test scope)
- `org.mockito:mockito-core:4.11.0` (test scope, last version supporting Java 8)

### 2. Java Assert Invariants Added to Production Code

- `TextUndoableEdit.java:25-27` — constructor asserts `figure`, `oldText`, `newText` are never null (invariant: these are required for undo/redo to make sense; a null here would cause an NPE at an unexpected call site and the program can't continue meaningfully)
- `TextFigure.java:243` — `setFontSize` asserts `size > 0` (invariant: a zero/negative font size is a programming error that should never happen at runtime)

### 3. Test Files Created (JUnit 4, Mockito)

| File | Tests | What's Covered |
|---|---|---|
| `TextUndoableEditTest.java` | 12 | undo/redo sets correct text, `willChange`/`changed` call order (mocked), presentation name, boundary: empty, same, long, special chars |
| `TextFigureTest.java` | 19 | `setText`/`getText`, editability, fixed constants (4 cols, 8 tab), clone independence, `setBounds`/`restoreTransformTo` |
| `TextEditingToolTest.java` | 4 | `isEditing()` returns true when `typingTarget` set in constructor; two independent tool instances |

Each test touches a single code path; Mockito stubs out `TextHolderFigure` wherever figure interaction is a dependency.
