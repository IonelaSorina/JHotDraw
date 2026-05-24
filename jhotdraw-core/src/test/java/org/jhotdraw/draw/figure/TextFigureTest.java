package org.jhotdraw.draw.figure;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit 4 tests for TextFigure domain logic.
 *
 * Tests cover text attribute access, editability, fixed constants (tab size,
 * text columns), and clone independence.  Rendering/layout methods (getBounds,
 * drawText) are excluded because they require a display or FontRenderContext.
 */
public class TextFigureTest {

    private TextFigure figure;

    @Before
    public void setUp() {
        figure = new TextFigure("hello");
    }

    // ------------------------------------------------------------------ setText / getText

    @Test
    public void testGetText_afterConstructor_returnsInitialText() {
        assertEquals("hello", figure.getText());
    }

    @Test
    public void testSetText_normalString_storedCorrectly() {
        figure.setText("world");
        assertEquals("world", figure.getText());
    }

    @Test
    public void testSetText_emptyString_boundaryCasePersisted() {
        figure.setText("");
        assertEquals("", figure.getText());
    }

    @Test
    public void testSetText_null_boundaryCaseAllowed() {
        figure.setText(null);
        assertNull(figure.getText());
    }

    @Test
    public void testSetText_specialCharacters_preserved() {
        String special = "<>&\"'\n\t";
        figure.setText(special);
        assertEquals(special, figure.getText());
    }

    @Test
    public void testSetText_veryLongString_stored() {
        String longText = "x".repeat(10_000);
        figure.setText(longText);
        assertEquals(longText, figure.getText());
    }

    @Test
    public void testSetText_unicodeCharacters_stored() {
        String unicode = "Aé中文";
        figure.setText(unicode);
        assertEquals(unicode, figure.getText());
    }

    // ------------------------------------------------------------------ editability

    @Test
    public void testIsEditable_default_isTrue() {
        assertTrue(figure.isEditable());
    }

    @Test
    public void testSetEditable_false_disablesEditing() {
        figure.setEditable(false);
        assertFalse(figure.isEditable());
    }

    @Test
    public void testSetEditable_trueAfterFalse_reEnablesEditing() {
        figure.setEditable(false);
        figure.setEditable(true);
        assertTrue(figure.isEditable());
    }

    // ------------------------------------------------------------------ fixed constants

    @Test
    public void testGetTextColumns_alwaysFour() {
        assertEquals(4, figure.getTextColumns());
    }

    @Test
    public void testGetTabSize_alwaysEight() {
        assertEquals(8, figure.getTabSize());
    }

    @Test
    public void testIsTextOverflow_alwaysFalse() {
        assertFalse(figure.isTextOverflow());
    }

    @Test
    public void testGetLabelFor_returnsSelf() {
        assertSame(figure, figure.getLabelFor());
    }

    // ------------------------------------------------------------------ clone independence

    @Test
    public void testClone_textIsIndependent() {
        TextFigure clone = figure.clone();
        clone.setText("changed");
        assertEquals("hello", figure.getText());
    }

    @Test
    public void testClone_editabilityIsIndependent() {
        TextFigure clone = figure.clone();
        clone.setEditable(false);
        assertTrue(figure.isEditable());
    }

    @Test
    public void testClone_notSameInstance() {
        TextFigure clone = figure.clone();
        assertNotSame(figure, clone);
    }

    // ------------------------------------------------------------------ setBounds / origin

    @Test
    public void testSetBounds_originUpdated() {
        java.awt.geom.Point2D.Double anchor = new java.awt.geom.Point2D.Double(10.0, 20.0);
        java.awt.geom.Point2D.Double lead = new java.awt.geom.Point2D.Double(0, 0);
        figure.setBounds(anchor, lead);
        // origin is reflected in getTransformRestoreData
        java.awt.geom.Point2D.Double saved = (java.awt.geom.Point2D.Double) figure.getTransformRestoreData();
        assertEquals(10.0, saved.x, 1e-9);
        assertEquals(20.0, saved.y, 1e-9);
    }

    @Test
    public void testRestoreTransformTo_restoresOrigin() {
        java.awt.geom.Point2D.Double original = new java.awt.geom.Point2D.Double(5.0, 7.0);
        figure.restoreTransformTo(original);
        java.awt.geom.Point2D.Double saved = (java.awt.geom.Point2D.Double) figure.getTransformRestoreData();
        assertEquals(5.0, saved.x, 1e-9);
        assertEquals(7.0, saved.y, 1e-9);
    }
}
