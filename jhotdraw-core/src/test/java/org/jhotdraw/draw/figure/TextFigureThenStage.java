package org.jhotdraw.draw.figure;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.assertj.core.api.Assertions;
import java.awt.geom.Point2D;

public class TextFigureThenStage extends Stage<TextFigureThenStage> {

    @ScenarioState(resolution = ScenarioState.Resolution.NAME)
    TextFigure figure;

    @ScenarioState
    String retrievedText;

    @ScenarioState(resolution = ScenarioState.Resolution.NAME)
    TextFigure clonedFigure;

    public TextFigureThenStage the_text_should_be(String expected) {
        Assertions.assertThat(figure.getText()).isEqualTo(expected);
        return self();
    }

    public TextFigureThenStage the_retrieved_text_should_be(String expected) {
        Assertions.assertThat(retrievedText).isEqualTo(expected);
        return self();
    }

    public TextFigureThenStage the_figure_should_not_be_editable() {
        Assertions.assertThat(figure.isEditable()).isFalse();
        return self();
    }

    public TextFigureThenStage the_clone_should_have_text(String expected) {
        Assertions.assertThat(clonedFigure.getText()).isEqualTo(expected);
        return self();
    }

    public TextFigureThenStage the_original_text_should_still_be(String expected) {
        Assertions.assertThat(figure.getText()).isEqualTo(expected);
        return self();
    }

    public TextFigureThenStage the_clone_should_be_a_different_instance() {
        Assertions.assertThat(clonedFigure).isNotSameAs(figure);
        return self();
    }

    public TextFigureThenStage the_font_size_should_be(float expected) {
        Assertions.assertThat(figure.getFontSize()).isEqualTo(expected);
        return self();
    }

    public TextFigureThenStage the_origin_should_be_at(double expectedX, double expectedY) {
        Point2D.Double origin = (Point2D.Double) figure.getTransformRestoreData();
        Assertions.assertThat(origin.x).isEqualTo(expectedX);
        Assertions.assertThat(origin.y).isEqualTo(expectedY);
        return self();
    }
}
