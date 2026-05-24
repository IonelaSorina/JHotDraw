package org.jhotdraw.draw.figure;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import java.awt.geom.Point2D;

public class TextFigureWhenStage extends Stage<TextFigureWhenStage> {

    @ScenarioState(resolution = ScenarioState.Resolution.NAME)
    TextFigure figure;

    @ScenarioState
    String retrievedText;

    @ScenarioState(resolution = ScenarioState.Resolution.NAME)
    TextFigure clonedFigure;

    public TextFigureWhenStage I_retrieve_the_text() {
        retrievedText = figure.getText();
        return self();
    }

    public TextFigureWhenStage I_set_the_text_to(String text) {
        figure.setText(text);
        return self();
    }

    public TextFigureWhenStage I_set_editable_to(boolean editable) {
        figure.setEditable(editable);
        return self();
    }

    public TextFigureWhenStage I_clone_the_figure() {
        clonedFigure = figure.clone();
        return self();
    }

    public TextFigureWhenStage I_modify_the_clone_text_to(String text) {
        clonedFigure.setText(text);
        return self();
    }

    public TextFigureWhenStage I_set_font_size_to(float size) {
        figure.setFontSize(size);
        return self();
    }

    public TextFigureWhenStage I_set_bounds_to(double x, double y) {
        figure.setBounds(
                new Point2D.Double(x, y),
                new Point2D.Double(0, 0));
        return self();
    }
}
