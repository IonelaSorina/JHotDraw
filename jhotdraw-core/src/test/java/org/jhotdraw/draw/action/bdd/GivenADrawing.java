/*
 * @(#)GivenADrawing.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.action.bdd;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import java.util.LinkedHashSet;
import java.util.Set;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.GroupFigure;
import org.jhotdraw.draw.figure.RectangleFigure;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

/**
 * JGiven Given-stage for Group / Ungroup scenarios.
 *
 * Provides a real {@link DefaultDrawing} so that assertions in the Then-stage
 * can inspect the actual figure tree, but mocks {@link DrawingEditor} and
 * {@link DrawingView} because those depend on Swing event-dispatch
 * infrastructure that the headless test JVM cannot provide.
 */
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
        Mockito.when(view.getDrawing()).thenReturn(drawing);
        Mockito.when(view.isEnabled()).thenReturn(true);
        Mockito.when(view.getSelectedFigures()).thenReturn(selection);
        return self();
    }

    public GivenADrawing $_rectangle_figures_on_the_canvas(int count) {
        for (int i = 0; i < count; i++) {
            drawing.add(new RectangleFigure(i * 10, i * 10, 8, 8));
        }
        return self();
    }

    public GivenADrawing a_group_containing_$_rectangle_figures(int count) {
        GroupFigure group = new GroupFigure();
        for (int i = 0; i < count; i++) {
            group.basicAdd(new RectangleFigure(i * 10, i * 10, 8, 8));
        }
        drawing.add(group);
        return self();
    }

    public GivenADrawing all_figures_are_selected() {
        selection.clear();
        selection.addAll(drawing.getChildren());
        Mockito.when(view.getSelectionCount()).thenReturn(selection.size());
        return self();
    }

    public GivenADrawing only_the_first_figure_is_selected() {
        selection.clear();
        selection.add(drawing.getChildren().get(0));
        Mockito.when(view.getSelectionCount()).thenReturn(1);
        return self();
    }

    public GivenADrawing the_group_is_selected() {
        selection.clear();
        for (Figure f : drawing.getChildren()) {
            if (f instanceof GroupFigure) {
                selection.add(f);
                break;
            }
        }
        Mockito.when(view.getSelectionCount()).thenReturn(selection.size());
        return self();
    }
}
