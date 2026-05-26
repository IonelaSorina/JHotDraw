# Classwork

---

## Lab 3 — Dynamic Program Analysis (Concept Location)

**Feature:** Text Tool (subfeatures: write, edit)

### Debugger Approach

Set breakpoints at the entry points of both subfeatures and run the application:

**Write subfeature** — user clicks on canvas to create a new text figure:

1. Breakpoint: `TextCreationTool.mousePressed()` — triggers when user clicks empty canvas area.
2. Step into `super.mousePressed(e)` → enters `CreationTool.mousePressed()` which instantiates a new `TextFigure` (cloned from prototype).
3. Returns to `TextCreationTool.mousePressed()` → calls `beginEdit(textHolder)`.
4. `beginEdit` constructs a `FloatingTextField`, calls `createOverlay(view, textHolder)` — overlays a Swing `JTextField` on the `DrawingView`.
5. User types → on Enter/focus-loss → `actionPerformed` fires → `endEdit()` called.
6. `endEdit()` calls `typingTarget.setText(newText)` on `TextFigure`, fires an `UndoableEdit` to `Drawing`.

**Edit subfeature** — user double-clicks existing text figure:

1. Breakpoint: `TextFigure.getTool()` — JHotDraw calls this when user double-clicks a figure.
2. Returns a `TextEditingTool` instance wrapping `this` (the `TextFigure`).
3. Breakpoint: `TextEditingTool.mousePressed()` → calls `beginEdit(typingTarget)`.
4. `beginEdit` reuses `FloatingTextField`, overlays it on existing figure bounds.
5. On commit → `endEdit()` → `typingTarget.setText(newText)`, fires `UndoableEdit`.

### Classes Localised via Debugger

| Class | Package | Role |
|---|---|---|
| `TextCreationTool` | `org.jhotdraw.draw.tool` | Entry controller — creates text figures |
| `TextEditingTool` | `org.jhotdraw.draw.tool` | Entry controller — edits existing text figures |
| `TextFigure` | `org.jhotdraw.draw.figure` | Domain figure — stores text, handles rendering |
| `TextHolderFigure` | `org.jhotdraw.draw.figure` | Interface — contract for text-holding figures |
| `FloatingTextField` | `org.jhotdraw.draw.text` | UI overlay — Swing field for text input |
| `CreationTool` | `org.jhotdraw.draw.tool` | Parent — figure instantiation from prototype |
| `AbstractTool` | `org.jhotdraw.draw.tool` | Base — event dispatch, view/editor access |

---

## Lab 4 — Change Impact Analysis (Static + Dynamic)

**Feature:** Text Tool (write + edit subfeatures)

### Algorithm — Figure 7.9

**Step 1:** Create interaction diagram; mark all classes **BLANK**.

**Step 2:** Mark the 7 concept-located classes as **CHANGED**.

**Steps 3–5 (BFS loop):** For each CHANGED/PROPAGATES class, mark its BLANK neighbors as NEXT; classify each NEXT class as UNCHANGED, PROPAGATES, or CHANGED; repeat until no NEXT classes remain.

### Estimated Impact Set

| Class | Package | Mark | Reason |
|---|---|---|---|
| `TextCreationTool` | `draw.tool` | **CHANGED** | Concept-located; primary write controller |
| `TextEditingTool` | `draw.tool` | **CHANGED** | Concept-located; primary edit controller |
| `CreationTool` | `draw.tool` | **CHANGED** | Concept-located; prototype-creation parent |
| `AbstractTool` | `draw.tool` | **CHANGED** | Concept-located; editor/view access base |
| `TextFigure` | `draw.figure` | **CHANGED** | Concept-located; domain model for text |
| `TextHolderFigure` | `draw.figure` | **CHANGED** | Concept-located; interface contract |
| `FloatingTextField` | `draw.text` | **CHANGED** | Concept-located; Swing overlay for text input |
| `TextAreaCreationTool` | `draw.tool` | PROPAGATES | Mirrors TextCreationTool for multi-line text; structural changes ripple |
| `TextAreaEditingTool` | `draw.tool` | PROPAGATES | Mirrors TextEditingTool; structural changes ripple |
| `DrawingEditor` | `draw` | PROPAGATES | All tools depend on it for editor lifecycle; any tool API change ripples here |
| `DrawingEditorProxy` | `draw` | PROPAGATES | Null-object proxy for DrawingEditor; must track editor changes |
| `DrawingView` | `draw` | PROPAGATES | FloatingTextField adds/removes overlay on the view component; cursor changes propagate |
| `AttributeKeys` | `draw` | PROPAGATES | Holds TEXT, FONT_SIZE constants referenced by TextFigure; new attributes add here |
| `ResourceBundleUtil` | `util` | PROPAGATES | Loads i18n label strings for undo presentation names; new text actions need new keys |
| `AbstractAttributedDecoratedFigure` | `draw.figure` | PROPAGATES | Superclass of TextFigure; attribute delegation flows through it |
| `TextAreaFigure` | `draw.figure` | PROPAGATES | Implements TextHolderFigure; interface contract changes must be reflected |
| `LabelFigure` | `draw.figure` | PROPAGATES | Extends TextFigure; inherits all changes |
| `FontSizeHandle` | `draw.handle` | PROPAGATES | Directly manipulates font size on TextHolderFigure; text-tool-aware |
| `SVGTextFigure` | `samples.svg.figures` | PROPAGATES | Implements TextHolderFigure in SVG sample; interface changes must be reflected |
| `SVGTextAreaFigure` | `samples.svg.figures` | PROPAGATES | Implements TextHolderFigure in SVG sample; interface changes must be reflected |
| `Drawing` | `draw` | UNCHANGED | Only used for `fireUndoableEditHappened`; no interface change needed |
| `Figure` | `draw.figure` | UNCHANGED | Base interface; no text-specific change needed |
| `CompositeFigure` | `draw.figure` | UNCHANGED | Used by CreationTool for containment; no text-specific logic |
| `BoundsOutlineHandle` | `draw.handle` | UNCHANGED | Generic handle; no text-specific logic |
| `MoveHandle` | `draw.handle` | UNCHANGED | Generic move handle; no text-specific logic |
| `Handle` | `draw.handle` | UNCHANGED | Base handle interface; no change needed |
| `FigureAdapter` | `draw.event` | UNCHANGED | Adapter helper used by FloatingTextField; no change needed |
| `FigureEvent` | `draw.event` | UNCHANGED | Event value object; no change needed |
| `FigureListener` | `draw.event` | UNCHANGED | Observer interface; no change needed |
| `ToolEvent` | `draw.event` | UNCHANGED | Tool lifecycle event; no change needed |
| `ToolListener` | `draw.event` | UNCHANGED | Tool lifecycle observer; no change needed |
| `RelativeLocator` | `draw.locator` | UNCHANGED | Positions handles relative to figure bounds; no change needed |
| `AbstractBean` | `beans` | UNCHANGED | JavaBeans base for AbstractTool; no change needed |
| `DOMInput` | `xml` | UNCHANGED | XML deserialisation for TextFigure; serialisation layer is independent |
| `DOMOutput` | `xml` | UNCHANGED | XML serialisation for TextFigure; serialisation layer is independent |
| `Dimension2DDouble` | `geom` | UNCHANGED | Geometry utility; no change needed |
| `Geom` | `geom` | UNCHANGED | Geometry utility; no change needed |
| `Insets2D` | `geom` | UNCHANGED | Geometry utility; no change needed |
