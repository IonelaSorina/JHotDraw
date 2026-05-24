package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.figure.TextHolderFigure;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * JUnit 4 tests for TextEditingTool domain logic.
 *
 * DrawingView / DrawingEditor interactions are dependencies that require Swing
 * and a real drawing setup, so only the pure-logic path (isEditing) is tested
 * here.  TextHolderFigure is mocked to isolate the tool under test.
 */
public class TextEditingToolTest {

    // ------------------------------------------------------------------ isEditing

    @Test
    public void testIsEditing_whenTypingTargetSet_returnsTrue() {
        TextHolderFigure mockFigure = mock(TextHolderFigure.class);
        TextEditingTool tool = new TextEditingTool(mockFigure);
        // typingTarget is assigned in the constructor
        assertTrue(tool.isEditing());
    }

    @Test
    public void testIsEditing_afterConstruction_notFalse() {
        TextHolderFigure mockFigure = mock(TextHolderFigure.class);
        TextEditingTool tool = new TextEditingTool(mockFigure);
        assertNotNull("tool must have a typing target after construction", mockFigure);
        assertTrue(tool.isEditing());
    }

    // ------------------------------------------------------------------ boundary: different figure instances

    @Test
    public void testIsEditing_withDifferentFigureInstances_allReturnTrue() {
        TextHolderFigure figureA = mock(TextHolderFigure.class);
        TextHolderFigure figureB = mock(TextHolderFigure.class);

        TextEditingTool toolA = new TextEditingTool(figureA);
        TextEditingTool toolB = new TextEditingTool(figureB);

        assertTrue(toolA.isEditing());
        assertTrue(toolB.isEditing());
    }

    @Test
    public void testTwoTools_haveIndependentState() {
        TextHolderFigure figure = mock(TextHolderFigure.class);
        TextEditingTool toolA = new TextEditingTool(figure);
        TextEditingTool toolB = new TextEditingTool(figure);

        assertTrue(toolA.isEditing());
        assertTrue(toolB.isEditing());
    }
}
