package org.jhotdraw.draw.figure.bdd;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.annotation.ScenarioState.Resolution;
import java.awt.geom.Point2D;
import org.jhotdraw.draw.figure.TextAreaFigure;

public class TextAreaFigureGivenStage extends Stage<TextAreaFigureGivenStage> {

    @ScenarioState(resolution = Resolution.NAME)
    TextAreaFigure figure;

    public TextAreaFigureGivenStage a_TextAreaFigure_with_text(String text) {
        figure = new TextAreaFigure(text);
        return self();
    }

    public TextAreaFigureGivenStage a_TextAreaFigure_with_bounds(
            double x1, double y1, double x2, double y2) {
        if (figure == null) {
            figure = new TextAreaFigure("text");
        }
        figure.setBounds(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2));
        return self();
    }

    public TextAreaFigureGivenStage the_figure_is_editable() {
        figure.setEditable(true);
        return self();
    }

    public TextAreaFigureGivenStage the_figure_is_not_editable() {
        figure.setEditable(false);
        return self();
    }

    public TextAreaFigureGivenStage a_TextAreaFigure_with_text_and_bounds(
            String text, double x1, double y1, double x2, double y2) {
        figure = new TextAreaFigure(text);
        figure.setBounds(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2));
        return self();
    }
}
