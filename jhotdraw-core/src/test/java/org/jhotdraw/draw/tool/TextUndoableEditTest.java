package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.figure.TextHolderFigure;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * JUnit 4 tests for TextUndoableEdit.
 *
 * Covers: undo/redo behaviour, presentation name, and boundary inputs.
 * TextHolderFigure is mocked to keep each test a pure unit test.
 */
public class TextUndoableEditTest {

    private TextHolderFigure mockFigure;

    @Before
    public void setUp() {
        mockFigure = mock(TextHolderFigure.class);
    }

    // ------------------------------------------------------------------ best case

    @Test
    public void testUndo_setsOldText() {
        TextUndoableEdit edit = new TextUndoableEdit(mockFigure, "original", "updated");
        edit.undo();
        verify(mockFigure).setText("original");
    }

    @Test
    public void testRedo_setsNewText() {
        TextUndoableEdit edit = new TextUndoableEdit(mockFigure, "original", "updated");
        edit.undo(); // must undo before redo is allowed
        edit.redo();
        verify(mockFigure).setText("updated");
    }

    @Test
    public void testUndoRedo_sequence_callsWillChangeAndChanged() {
        TextUndoableEdit edit = new TextUndoableEdit(mockFigure, "original", "updated");
        edit.undo();

        InOrder order = inOrder(mockFigure);
        order.verify(mockFigure).willChange();
        order.verify(mockFigure).setText("original");
        order.verify(mockFigure).changed();
    }

    @Test
    public void testRedo_sequence_callsWillChangeAndChanged() {
        TextUndoableEdit edit = new TextUndoableEdit(mockFigure, "original", "updated");
        edit.undo();
        reset(mockFigure); // clear undo calls

        edit.redo();

        InOrder order = inOrder(mockFigure);
        order.verify(mockFigure).willChange();
        order.verify(mockFigure).setText("updated");
        order.verify(mockFigure).changed();
    }

    @Test
    public void testGetPresentationName_notNullAndNotEmpty() {
        TextUndoableEdit edit = new TextUndoableEdit(mockFigure, "a", "b");
        String name = edit.getPresentationName();
        assertNotNull(name);
        assertFalse(name.isEmpty());
    }

    @Test
    public void testCanUndo_trueAfterCreation() {
        TextUndoableEdit edit = new TextUndoableEdit(mockFigure, "a", "b");
        assertTrue(edit.canUndo());
    }

    @Test
    public void testCanRedo_trueAfterUndo() {
        TextUndoableEdit edit = new TextUndoableEdit(mockFigure, "a", "b");
        edit.undo();
        assertTrue(edit.canRedo());
    }

    // ------------------------------------------------------------------ boundary cases

    @Test
    public void testUndo_emptyOldText_setsEmptyString() {
        TextUndoableEdit edit = new TextUndoableEdit(mockFigure, "", "some text");
        edit.undo();
        verify(mockFigure).setText("");
    }

    @Test
    public void testRedo_emptyNewText_setsEmptyString() {
        TextUndoableEdit edit = new TextUndoableEdit(mockFigure, "some text", "");
        edit.undo();
        reset(mockFigure);
        edit.redo();
        verify(mockFigure).setText("");
    }

    @Test
    public void testUndo_sameOldAndNewText_setsTextUnchanged() {
        TextUndoableEdit edit = new TextUndoableEdit(mockFigure, "same", "same");
        edit.undo();
        verify(mockFigure).setText("same");
    }

    @Test
    public void testUndo_longText_handled() {
        String longText = "a".repeat(10_000);
        TextUndoableEdit edit = new TextUndoableEdit(mockFigure, longText, "short");
        edit.undo();
        verify(mockFigure).setText(longText);
    }

    @Test
    public void testUndo_specialCharacters_preserved() {
        String special = "<>&\"'\n\t";
        TextUndoableEdit edit = new TextUndoableEdit(mockFigure, special, "normal");
        edit.undo();
        verify(mockFigure).setText(special);
    }
}
