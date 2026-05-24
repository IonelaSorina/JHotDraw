/*
 * @(#)GroupFigureTest.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.figure;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * JUnit 4 tests for {@link GroupFigure}.
 *
 * The {@code isTransformable()} method exposes a small piece of pure
 * collective behaviour: the group is transformable iff *all* of its
 * children are. This is exactly the kind of invariant boundary case
 * worth pinning with a unit test (best case, empty case, single
 * non-transformable child).
 */
public class GroupFigureTest {

    private GroupFigure group;

    @Before
    public void setUp() {
        group = new GroupFigure();
    }

    @Test
    public void isTransformable_returnsTrue_forEmptyGroup() {
        assertTrue("vacuous truth: an empty group has no untransformable child",
                group.isTransformable());
    }

    @Test
    public void isTransformable_returnsTrue_whenAllChildrenTransformable() {
        Figure transformable1 = mock(Figure.class);
        Figure transformable2 = mock(Figure.class);
        when(transformable1.isTransformable()).thenReturn(true);
        when(transformable2.isTransformable()).thenReturn(true);
        group.basicAdd(transformable1);
        group.basicAdd(transformable2);

        assertTrue("group with all-transformable children is transformable",
                group.isTransformable());
    }

    @Test
    public void isTransformable_returnsFalse_whenAnyChildNotTransformable() {
        Figure transformable = mock(Figure.class);
        Figure fixed = mock(Figure.class);
        when(transformable.isTransformable()).thenReturn(true);
        when(fixed.isTransformable()).thenReturn(false);
        group.basicAdd(transformable);
        group.basicAdd(fixed);

        assertFalse("a single non-transformable child poisons the group",
                group.isTransformable());
    }
}
