/*
 * @(#)GroupActionTest.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.swing.undo.UndoableEdit;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.CompositeFigure;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.GroupFigure;
import org.jhotdraw.draw.figure.RectangleFigure;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * JUnit 4 tests for {@link GroupAction}.
 *
 * Covers the domain logic of the Group / Ungroup feature: the guards
 * ({@code canGroup}, {@code canUngroup}), the two mutators
 * ({@code groupFigures}, {@code ungroupFigures}), and the
 * {@code actionPerformed} dispatch path. {@link DrawingEditor},
 * {@link DrawingView} and {@link Drawing} are mocked with Mockito; real
 * {@link GroupFigure} / {@link RectangleFigure} instances are used where
 * class identity matters because {@code getClass()} is final and cannot
 * be stubbed.
 */
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

    // ---------- canGroup ----------

    @Test
    public void canGroup_returnsTrue_whenSelectionHasMoreThanOneFigure() {
        when(view.getSelectionCount()).thenReturn(3);

        assertTrue("two or more selected figures should be groupable",
                action.canGroup());
    }

    @Test
    public void canGroup_returnsFalse_whenSelectionHasExactlyOneFigure() {
        when(view.getSelectionCount()).thenReturn(1);

        assertFalse("a single figure is not enough to form a group",
                action.canGroup());
    }

    @Test
    public void canGroup_returnsFalse_whenSelectionIsEmpty() {
        when(view.getSelectionCount()).thenReturn(0);

        assertFalse("an empty selection cannot be grouped",
                action.canGroup());
    }

    @Test
    public void canGroup_returnsFalse_whenNoActiveView() {
        when(editor.getActiveView()).thenReturn(null);

        assertFalse("without an active view, grouping is impossible",
                action.canGroup());
    }

    // ---------- canUngroup ----------

    @Test
    public void canUngroup_returnsTrue_whenSelectionIsSingleMatchingFigure() {
        GroupFigure realGroup = new GroupFigure();
        whenSelectionIs(realGroup);
        when(view.getSelectionCount()).thenReturn(1);

        GroupAction ungroupAction = new GroupAction(editor, prototype, false);

        assertTrue("a single figure of the prototype class is ungroupable",
                ungroupAction.canUngroup());
    }

    @Test
    public void canUngroup_returnsFalse_whenSelectionIsSingleNonMatchingFigure() {
        RectangleFigure nonGroup = new RectangleFigure();
        whenSelectionIs(nonGroup);
        when(view.getSelectionCount()).thenReturn(1);

        GroupAction ungroupAction = new GroupAction(editor, prototype, false);

        assertFalse("a single figure of the wrong class cannot be ungrouped",
                ungroupAction.canUngroup());
    }

    @Test
    public void canUngroup_returnsFalse_whenMultipleFiguresSelected() {
        when(view.getSelectionCount()).thenReturn(2);

        assertFalse("ungrouping requires exactly one selected figure",
                action.canUngroup());
    }

    @Test
    public void canUngroup_returnsFalse_whenSelectionEmpty() {
        when(view.getSelectionCount()).thenReturn(0);

        assertFalse("an empty selection cannot be ungrouped",
                action.canUngroup());
    }

    @Test
    public void canUngroup_returnsFalse_whenNoActiveView() {
        when(editor.getActiveView()).thenReturn(null);

        assertFalse("without an active view, ungrouping is impossible",
                action.canUngroup());
    }

    // ---------- groupFigures (best case) ----------

    @Test
    public void groupFigures_clearsSelection_addsGroupAtFirstFigureIndex_andReselectsTheGroup() {
        CompositeFigure group = mock(CompositeFigure.class);
        Figure f1 = mock(Figure.class);
        Figure f2 = mock(Figure.class);
        List<Figure> figures = Arrays.asList(f1, f2);
        when(drawing.sort(figures)).thenReturn(new ArrayList<>(figures));
        when(drawing.indexOf(f1)).thenReturn(4);

        action.groupFigures(view, group, figures);

        InOrder ordered = inOrder(drawing, view, group);
        ordered.verify(drawing).basicRemoveAll(figures);
        ordered.verify(view).clearSelection();
        ordered.verify(drawing).add(4, group);
        ordered.verify(group).willChange();
        ordered.verify(group).basicAdd(f1);
        ordered.verify(group).basicAdd(f2);
        ordered.verify(group).changed();
        ordered.verify(view).addToSelection(group);
    }

    // ---------- ungroupFigures (best case) ----------

    @Test
    public void ungroupFigures_movesChildrenOutAndRemovesGroup_inOrder() {
        CompositeFigure group = mock(CompositeFigure.class);
        Figure c1 = mock(Figure.class);
        Figure c2 = mock(Figure.class);
        List<Figure> children = Arrays.asList(c1, c2);
        when(group.getChildren()).thenReturn(children);
        when(drawing.indexOf(group)).thenReturn(7);

        Collection<Figure> returned = action.ungroupFigures(view, group);

        InOrder ordered = inOrder(view, drawing, group);
        ordered.verify(view).clearSelection();
        ordered.verify(group).basicRemoveAllChildren();
        ordered.verify(drawing).basicAddAll(eq(7), eq(children));
        ordered.verify(drawing).remove(group);
        ordered.verify(view).addToSelection(any(Collection.class));

        assertEquals("returned collection lists the freed children",
                children, new ArrayList<>(returned));
    }

    // ---------- ungroupFigures (boundary: empty group) ----------

    @Test
    public void ungroupFigures_returnsEmptyCollection_whenGroupHasNoChildren() {
        CompositeFigure group = mock(CompositeFigure.class);
        when(group.getChildren()).thenReturn(Collections.<Figure>emptyList());
        when(drawing.indexOf(group)).thenReturn(0);

        Collection<Figure> result = action.ungroupFigures(view, group);

        assertNotNull("return value must never be null", result);
        assertTrue("empty group ungroups to an empty collection", result.isEmpty());
        verify(drawing).remove(group);
    }

    // ---------- actionPerformed dispatch ----------

    @Test
    public void actionPerformed_performsGroup_whenIsGroupingActionAndCanGroup() {
        CompositeFigure mockPrototype = mock(CompositeFigure.class);
        CompositeFigure cloned = mock(CompositeFigure.class);
        when(mockPrototype.clone()).thenReturn(cloned);
        GroupAction groupAction = new GroupAction(editor, mockPrototype, true);

        when(view.getSelectionCount()).thenReturn(2);
        Figure f1 = mock(Figure.class);
        Figure f2 = mock(Figure.class);
        whenSelectionIs(f1, f2);
        when(drawing.sort(any(Collection.class))).thenAnswer(inv ->
                new ArrayList<>((Collection<Figure>) inv.getArgument(0)));
        when(drawing.indexOf(any(Figure.class))).thenReturn(0);

        groupAction.actionPerformed(new ActionEvent(this, 0, "group"));

        verify(drawing).add(anyInt(), eq(cloned));
        verify(cloned).basicAdd(f1);
        verify(cloned).basicAdd(f2);
        verify(drawing).fireUndoableEditHappened(any(UndoableEdit.class));
    }

    @Test
    public void actionPerformed_doesNothing_whenCannotGroup() {
        when(view.getSelectionCount()).thenReturn(1);

        action.actionPerformed(new ActionEvent(this, 0, "group"));

        verify(drawing, never()).add(anyInt(), any(Figure.class));
        verify(drawing, never()).fireUndoableEditHappened(any(UndoableEdit.class));
    }

    @Test
    public void actionPerformed_performsUngroup_whenNotGroupingActionAndCanUngroup() {
        GroupAction ungroupAction = new GroupAction(editor, prototype, false);
        GroupFigure realGroup = new GroupFigure();
        realGroup.basicAdd(new RectangleFigure());
        whenSelectionIs(realGroup);
        when(view.getSelectionCount()).thenReturn(1);
        when(drawing.indexOf(realGroup)).thenReturn(0);

        ungroupAction.actionPerformed(new ActionEvent(this, 0, "ungroup"));

        verify(drawing).remove(realGroup);
        verify(drawing).fireUndoableEditHappened(any(UndoableEdit.class));
    }

    // ---------- production assertion (invariant) ----------

    /**
     * Asserts are enabled by default in Surefire (-ea). Calling
     * {@code groupFigures} with an empty figure collection violates the
     * documented invariant and must trip the JVM assertion.
     */
    @Test(expected = AssertionError.class)
    public void groupFigures_failsAssertion_whenFiguresCollectionIsEmpty() {
        action.groupFigures(view, mock(CompositeFigure.class),
                Collections.<Figure>emptyList());
    }

    // ---------- helpers ----------

    private void whenSelectionIs(Figure... figures) {
        Set<Figure> set = new LinkedHashSet<>(Arrays.asList(figures));
        when(view.getSelectedFigures()).thenReturn(set);
    }
}
