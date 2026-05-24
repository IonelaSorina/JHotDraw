package org.jhotdraw.draw.figure;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;

public class TextFigureGivenStage extends Stage<TextFigureGivenStage> {

    @ScenarioState(resolution = ScenarioState.Resolution.NAME)
    TextFigure figure;

    public TextFigureGivenStage a_text_figure_with_text(String text) {
        figure = new TextFigure(text);
        return self();
    }

    public TextFigureGivenStage a_text_figure() {
        figure = new TextFigure("default");
        return self();
    }

    public TextFigureGivenStage an_editable_text_figure() {
        figure = new TextFigure("editable text");
        return self();
    }
}
