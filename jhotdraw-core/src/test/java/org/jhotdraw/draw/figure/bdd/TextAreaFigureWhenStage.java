package org.jhotdraw.draw.figure.bdd;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.annotation.ScenarioState.Resolution;
import java.awt.geom.Point2D;
import org.jhotdraw.draw.figure.TextAreaFigure;

public class TextAreaFigureWhenStage extends Stage<TextAreaFigureWhenStage> {

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

    public TextAreaFigureWhenStage the_user_sets_the_text_to(String text) {
        figure.setText(text);
        return self();
    }

    public TextAreaFigureWhenStage the_user_retrieves_the_text() {
        retrievedText = figure.getText();
        return self();
    }

    public TextAreaFigureWhenStage the_user_sets_bounds(
            double x1, double y1, double x2, double y2) {
        figure.setBounds(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2));
        return self();
    }

    public TextAreaFigureWhenStage the_user_checks_if_point(double x, double y) {
        containsPoint = figure.figureContains(new Point2D.Double(x, y));
        return self();
    }

    public TextAreaFigureWhenStage the_user_clones_the_figure() {
        clonedFigure = figure.clone();
        return self();
    }

    public TextAreaFigureWhenStage the_user_sets_editable_to(boolean editable) {
        figure.setEditable(editable);
        editableState = figure.isEditable();
        return self();
    }

    public TextAreaFigureWhenStage the_user_checks_text_columns() {
        textColumns = figure.getTextColumns();
        return self();
    }

    public TextAreaFigureWhenStage the_user_mutates_cloned_bounds(
            double x1, double y1, double x2, double y2) {
        clonedFigure.setBounds(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2));
        return self();
    }
}
