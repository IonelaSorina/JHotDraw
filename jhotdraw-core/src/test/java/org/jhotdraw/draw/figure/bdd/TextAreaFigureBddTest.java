package org.jhotdraw.draw.figure.bdd;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

/**
 * BDD scenarios for TextAreaFigure using JGiven + AssertJ.
 *
 * User Stories:
 *  1. As a drawing user, I want to set and retrieve text so that I can display content.
 *  2. As a drawing user, I want to position a figure using bounds so that I can lay out my drawing.
 *  3. As a drawing user, I want to hit-test a figure so that I can select it with a click.
 *  4. As a drawing user, I want to control editability so that I can protect figure content.
 *  5. As a drawing user, I want to clone a figure so that I can duplicate it independently.
 */
public class TextAreaFigureBddTest extends ScenarioTest<
        TextAreaFigureGivenStage,
        TextAreaFigureWhenStage,
        TextAreaFigureThenStage> {

    // -------------------------------------------------------------------------
    // User Story 1: Text management
    // -------------------------------------------------------------------------

    @Test
    public void user_can_set_text_on_a_figure() {
        given().a_TextAreaFigure_with_text("Hello");
        when().the_user_sets_the_text_to("World");
        then().the_text_should_be("World");
    }

    @Test
    public void user_can_retrieve_initial_text() {
        given().a_TextAreaFigure_with_text("Hello");
        when().the_user_retrieves_the_text();
        then().the_retrieved_text_should_be("Hello");
    }

    @Test
    public void user_can_clear_text_with_empty_string() {
        given().a_TextAreaFigure_with_text("Hello");
        when().the_user_sets_the_text_to("");
        then().the_text_should_be("");
    }

    @Test
    public void text_columns_are_at_least_four_for_short_text() {
        given().a_TextAreaFigure_with_text("Hi");
        when().the_user_checks_text_columns();
        then().the_text_columns_should_be_at_least(4)
              .the_text_columns_should_equal(4);
    }

    @Test
    public void text_columns_match_length_for_long_text() {
        given().a_TextAreaFigure_with_text("Hello World");
        when().the_user_checks_text_columns();
        then().the_text_columns_should_equal(11);
    }

    // -------------------------------------------------------------------------
    // User Story 2: Bounds management
    // -------------------------------------------------------------------------

    @Test
    public void user_can_position_figure_with_normal_bounds() {
        given().a_TextAreaFigure_with_text("text");
        when().the_user_sets_bounds(10, 20, 110, 70);
        then().the_bounds_should_have_x(10)
              .the_bounds_should_have_y(20)
              .the_bounds_should_have_width(100)
              .the_bounds_should_have_height(50);
    }

    @Test
    public void figure_normalises_reversed_bounds() {
        given().a_TextAreaFigure_with_text("text");
        when().the_user_sets_bounds(110, 70, 10, 20);
        then().the_bounds_should_have_x(10)
              .the_bounds_should_have_y(20)
              .the_bounds_should_have_width(100)
              .the_bounds_should_have_height(50);
    }

    // -------------------------------------------------------------------------
    // User Story 3: Hit-testing
    // -------------------------------------------------------------------------

    @Test
    public void user_can_detect_click_inside_figure() {
        given().a_TextAreaFigure_with_text_and_bounds("text", 0, 0, 100, 100);
        when().the_user_checks_if_point(50, 50);
        then().the_point_should_be_inside();
    }

    @Test
    public void user_can_detect_click_outside_figure() {
        given().a_TextAreaFigure_with_text_and_bounds("text", 0, 0, 100, 100);
        when().the_user_checks_if_point(200, 200);
        then().the_point_should_be_outside();
    }

    @Test
    public void user_can_detect_click_on_top_left_corner() {
        given().a_TextAreaFigure_with_text_and_bounds("text", 0, 0, 100, 100);
        when().the_user_checks_if_point(0, 0);
        then().the_point_should_be_inside();
    }

    // -------------------------------------------------------------------------
    // User Story 4: Editability control
    // -------------------------------------------------------------------------

    @Test
    public void figure_is_editable_by_default() {
        given().a_TextAreaFigure_with_text("text");
        when().the_user_sets_editable_to(true);
        then().the_figure_should_be_editable();
    }

    @Test
    public void user_can_disable_editing() {
        given().a_TextAreaFigure_with_text("text")
               .the_figure_is_editable();
        when().the_user_sets_editable_to(false);
        then().the_figure_should_not_be_editable();
    }

    @Test
    public void user_can_re_enable_editing() {
        given().a_TextAreaFigure_with_text("text")
               .the_figure_is_not_editable();
        when().the_user_sets_editable_to(true);
        then().the_figure_should_be_editable();
    }

    // -------------------------------------------------------------------------
    // User Story 5: Clone independence
    // -------------------------------------------------------------------------

    @Test
    public void clone_preserves_original_text() {
        given().a_TextAreaFigure_with_text("Hello");
        when().the_user_clones_the_figure();
        then().the_clone_should_have_same_text();
    }

    @Test
    public void mutating_clone_does_not_affect_original() {
        given().a_TextAreaFigure_with_text_and_bounds("Hello", 0, 0, 50, 50);
        when().the_user_clones_the_figure()
              .the_user_mutates_cloned_bounds(0, 0, 200, 200);
        then().the_original_width_should_still_be(50);
    }
}
