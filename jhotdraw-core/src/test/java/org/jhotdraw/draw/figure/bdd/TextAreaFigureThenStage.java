package org.jhotdraw.draw.figure.bdd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.annotation.ScenarioState.Resolution;
import org.jhotdraw.draw.figure.TextAreaFigure;

public class TextAreaFigureThenStage extends Stage<TextAreaFigureThenStage> {

    @ScenarioState(resolution = Resolution.NAME)
    TextAreaFigure figure;

    @ScenarioState
    String retrievedText;

    @ScenarioState
    Boolean containsPoint;

    @ScenarioState(resolution = Resolution.NAME)
    TextAreaFigure clonedFigure;

    @ScenarioState
    Boolean editableState;

    @ScenarioState
    Integer textColumns;

    public TextAreaFigureThenStage the_text_should_be(String expected) {
        assertThat(figure.getText()).isEqualTo(expected);
        return self();
    }

    public TextAreaFigureThenStage the_retrieved_text_should_be(String expected) {
        assertThat(retrievedText).isEqualTo(expected);
        return self();
    }

    public TextAreaFigureThenStage the_bounds_should_have_x(double expectedX) {
        assertThat(figure.getBounds().x).isCloseTo(expectedX, offset(0.001));
        return self();
    }

    public TextAreaFigureThenStage the_bounds_should_have_y(double expectedY) {
        assertThat(figure.getBounds().y).isCloseTo(expectedY, offset(0.001));
        return self();
    }

    public TextAreaFigureThenStage the_bounds_should_have_width(double expectedWidth) {
        assertThat(figure.getBounds().width).isCloseTo(expectedWidth, offset(0.001));
        return self();
    }

    public TextAreaFigureThenStage the_bounds_should_have_height(double expectedHeight) {
        assertThat(figure.getBounds().height).isCloseTo(expectedHeight, offset(0.001));
        return self();
    }

    public TextAreaFigureThenStage the_point_should_be_inside() {
        assertThat(containsPoint).isTrue();
        return self();
    }

    public TextAreaFigureThenStage the_point_should_be_outside() {
        assertThat(containsPoint).isFalse();
        return self();
    }

    public TextAreaFigureThenStage the_figure_should_be_editable() {
        assertThat(figure.isEditable()).isTrue();
        return self();
    }

    public TextAreaFigureThenStage the_figure_should_not_be_editable() {
        assertThat(figure.isEditable()).isFalse();
        return self();
    }

    public TextAreaFigureThenStage the_clone_should_have_same_text() {
        assertThat(clonedFigure.getText()).isEqualTo(figure.getText());
        return self();
    }

    public TextAreaFigureThenStage the_original_width_should_still_be(double expected) {
        assertThat(figure.getBounds().width).isCloseTo(expected, offset(0.001));
        return self();
    }

    public TextAreaFigureThenStage the_text_columns_should_be_at_least(int min) {
        assertThat(textColumns).isGreaterThanOrEqualTo(min);
        return self();
    }

    public TextAreaFigureThenStage the_text_columns_should_equal(int expected) {
        assertThat(textColumns).isEqualTo(expected);
        return self();
    }
}
