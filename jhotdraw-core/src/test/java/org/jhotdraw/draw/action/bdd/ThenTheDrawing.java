/*
 * @(#)ThenTheDrawing.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.action.bdd;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.figure.CompositeFigure;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.RectangleFigure;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * JGiven Then-stage for Group / Ungroup scenarios.
 *
 * All assertions use AssertJ's fluent API (per Lecture 9). Domain-specific
 * predicates ({@code containsExactlyOneGroup}, {@code hasGroupWithChildren})
 * could be lifted into a custom AssertJ {@code AbstractAssert<...>} for
 * even more readable failure messages; this stage instead keeps the
 * assertions inline so the readable JGiven report stays the primary
 * documentation.
 */
public class ThenTheDrawing extends Stage<ThenTheDrawing> {

    @ExpectedScenarioState
    Drawing drawing;

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

    public ThenTheDrawing contains_exactly_$_rectangle_figures_and_no_groups(int expected) {
        assertThat(drawing.getChildren())
                .as("drawing should now contain the ungrouped figures only")
                .hasSize(expected)
                .allMatch(f -> f instanceof RectangleFigure)
                .noneMatch(f -> f instanceof CompositeFigure);
        return self();
    }

    public ThenTheDrawing is_unchanged_with_$_figures(int expected) {
        assertThat(drawing.getChildren())
                .as("an invalid action attempt must not modify the drawing")
                .hasSize(expected);
        return self();
    }
}
