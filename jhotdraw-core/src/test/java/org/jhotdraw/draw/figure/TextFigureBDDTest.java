package org.jhotdraw.draw.figure;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

/**
 * BDD scenarios for the TextFigure text-tool feature.
 *
 * User Stories:
 *   US1: As a user, I want to create a text figure with custom text
 *        so that I can add annotations to my drawings.
 *   US2: As a user, I want to update the text of an existing figure
 *        so that I can correct or modify my annotations.
 *   US3: As a user, I want to lock a text figure
 *        so that I can prevent accidental editing.
 *   US4: As a user, I want to duplicate a text figure
 *        so that I can reuse similar text without affecting the original.
 *   US5: As a user, I want to resize text by changing the font size
 *        so that I can adjust text prominence in my drawing.
 *   US6: As a user, I want to position a text figure at exact coordinates
 *        so that I can precisely place my annotations.
 */
public class TextFigureBDDTest
        extends ScenarioTest<TextFigureGivenStage, TextFigureWhenStage, TextFigureThenStage> {

    // US1 -----------------------------------------------------------------------

    @Test
    public void user_creates_a_text_figure_with_custom_text() {
        given().a_text_figure_with_text("Hello World");
        when().I_retrieve_the_text();
        then().the_retrieved_text_should_be("Hello World");
    }

    // US2 -----------------------------------------------------------------------

    @Test
    public void user_updates_text_of_an_existing_figure() {
        given().a_text_figure_with_text("Old Text");
        when().I_set_the_text_to("New Text");
        then().the_text_should_be("New Text");
    }

    @Test
    public void user_clears_text_of_a_figure() {
        given().a_text_figure_with_text("Some Text");
        when().I_set_the_text_to("");
        then().the_text_should_be("");
    }

    // US3 -----------------------------------------------------------------------

    @Test
    public void user_locks_a_text_figure_to_prevent_editing() {
        given().an_editable_text_figure();
        when().I_set_editable_to(false);
        then().the_figure_should_not_be_editable();
    }

    // US4 -----------------------------------------------------------------------

    @Test
    public void user_duplicates_a_figure_and_modifies_clone_independently() {
        given().a_text_figure_with_text("Original");
        when().I_clone_the_figure()
                .I_modify_the_clone_text_to("Clone Changed");
        then().the_original_text_should_still_be("Original")
                .the_clone_should_have_text("Clone Changed")
                .the_clone_should_be_a_different_instance();
    }

    // US5 -----------------------------------------------------------------------

    @Test
    public void user_resizes_text_by_setting_font_size() {
        given().a_text_figure();
        when().I_set_font_size_to(24f);
        then().the_font_size_should_be(24f);
    }

    // US6 -----------------------------------------------------------------------

    @Test
    public void user_positions_figure_at_exact_coordinates() {
        given().a_text_figure();
        when().I_set_bounds_to(10.0, 20.0);
        then().the_origin_should_be_at(10.0, 20.0);
    }
}
