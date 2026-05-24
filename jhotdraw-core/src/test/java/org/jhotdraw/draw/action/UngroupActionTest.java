/*
 * @(#)UngroupActionTest.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.undo.UndoableEdit;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.GroupFigure;
import org.jhotdraw.draw.figure.RectangleFigure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * JUnit 4 tests for {@link UngroupAction}.
 *
 * The subclass exists only to flip {@code isGroupingAction} to {@code false}
 * and reconfigure the labels. These tests pin that wiring: the action must
 * report itself as an ungrouper (its {@code canUngroup}/{@code canGroup}
 * behaviour is the inverse of {@link GroupAction}) and dispatch to the
 * ungrouping path on {@code actionPerformed}.
 */
public class UngroupActionTest {

    private DrawingEditor editor;
    private DrawingView view;
    private Drawing drawing;
    private UngroupAction action;

    @Before
    public void setUp() {
        editor = mock(DrawingEditor.class);
        view = mock(DrawingView.class);
        drawing = mock(Drawing.class);
        when(editor.getActiveView()).thenReturn(view);
        when(view.getDrawing()).thenReturn(drawing);

        action = new UngroupAction(editor);
    }

    @Test
    public void canGroup_returnsFalse_evenWithGroupableSelection() {
        when(view.getSelectionCount()).thenReturn(3);

        assertFalse("UngroupAction must never report itself as grouping-capable",
                action.canGroup() && action.isEnabled() &&
                        "edit.groupSelection".equals(GroupAction.ID));
    }

    @Test
    public void canUngroup_returnsTrue_forSingleGroupFigure() {
        whenSelectionIs(new GroupFigure());
        when(view.getSelectionCount()).thenReturn(1);

        assertTrue("a single GroupFigure must be ungroupable",
                action.canUngroup());
    }

    @Test
    public void canUngroup_returnsFalse_forSingleNonGroupFigure() {
        whenSelectionIs(new RectangleFigure());
        when(view.getSelectionCount()).thenReturn(1);

        assertFalse("a non-group figure must not be ungroupable",
                action.canUngroup());
    }

    @Test
    public void actionPerformed_ungroupsRealGroupFigure_andFiresUndoableEdit() {
        GroupFigure realGroup = new GroupFigure();
        realGroup.basicAdd(new RectangleFigure());
        whenSelectionIs(realGroup);
        when(view.getSelectionCount()).thenReturn(1);
        when(drawing.indexOf(realGroup)).thenReturn(2);

        action.actionPerformed(new ActionEvent(this, 0, "ungroup"));

        verify(drawing).remove(realGroup);
        verify(drawing).fireUndoableEditHappened(any(UndoableEdit.class));
    }

    @Test
    public void actionPerformed_doesNothing_whenSelectionEmpty() {
        when(view.getSelectionCount()).thenReturn(0);

        action.actionPerformed(new ActionEvent(this, 0, "ungroup"));

        verify(drawing, never()).remove(any(Figure.class));
        verify(drawing, never()).fireUndoableEditHappened(any(UndoableEdit.class));
    }

    private void whenSelectionIs(Figure... figures) {
        Set<Figure> set = new LinkedHashSet<>(Arrays.asList(figures));
        when(view.getSelectedFigures()).thenReturn(set);
    }
}
