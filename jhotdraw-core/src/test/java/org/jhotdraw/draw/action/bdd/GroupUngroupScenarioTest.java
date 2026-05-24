/*
 * @(#)GroupUngroupScenarioTest.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.action.bdd;

import com.tngtech.jgiven.annotation.Description;
import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

/**
 * BDD scenarios for the Group / Ungroup feature.
 *
 * Each scenario maps a user story (Lab 9, classwork item 1) to a
 * Given-When-Then sentence (classwork item 2), and is automated through
 * JGiven (classwork item 3) with AssertJ assertions inside the
 * {@link ThenTheDrawing} stage (classwork item 4).
 *
 * <p>User stories covered:
 * <ul>
 *   <li><strong>US-1</strong>: As a Draw user, I want to group multiple
 *       selected figures so that I can move them as a single unit.</li>
 *   <li><strong>US-2</strong>: As a Draw user, I want to ungroup a
 *       previously grouped figure so that I can edit its children
 *       independently.</li>
 *   <li><strong>US-3</strong>: As a Draw user, I want the Group menu
 *       item disabled when only one figure is selected, so that I am
 *       not allowed to create meaningless single-figure groups.</li>
 * </ul>
 */
public class GroupUngroupScenarioTest
        extends ScenarioTest<GivenADrawing, WhenTheUser, ThenTheDrawing> {

    @Test
    @Description("US-1: grouping two selected rectangles merges them into a single group")
    public void grouping_two_selected_rectangles_replaces_them_with_a_single_group_of_two() {
        given().a_drawing_editor()
           .and().$_rectangle_figures_on_the_canvas(2)
           .and().all_figures_are_selected();

        when().invokes_the_group_action();

        then().contains_exactly_one_group_with_$_rectangle_children(2);
    }

    @Test
    @Description("US-1: grouping three selected rectangles produces a single group of three")
    public void grouping_three_selected_rectangles_replaces_them_with_a_single_group_of_three() {
        given().a_drawing_editor()
           .and().$_rectangle_figures_on_the_canvas(3)
           .and().all_figures_are_selected();

        when().invokes_the_group_action();

        then().contains_exactly_one_group_with_$_rectangle_children(3);
    }

    @Test
    @Description("US-2: ungrouping a group restores the children into the drawing")
    public void ungrouping_a_group_of_two_restores_two_rectangles_to_the_drawing() {
        given().a_drawing_editor()
           .and().a_group_containing_$_rectangle_figures(2)
           .and().the_group_is_selected();

        when().invokes_the_ungroup_action();

        then().contains_exactly_$_rectangle_figures_and_no_groups(2);
    }

    @Test
    @Description("US-3: invoking group with only one selected figure leaves the drawing unchanged")
    public void invoking_group_with_one_selected_figure_does_not_change_the_drawing() {
        given().a_drawing_editor()
           .and().$_rectangle_figures_on_the_canvas(2)
           .and().only_the_first_figure_is_selected();

        when().invokes_the_group_action();

        then().is_unchanged_with_$_figures(2);
    }
}
