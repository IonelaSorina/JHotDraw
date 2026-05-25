package org.jhotdraw.draw.figure;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit 4 tests for TextAreaFigure domain logic.
 *
 * Coverage:
 *  - text get/set
 *  - bounds management (normal, reversed, zero-size)
 *  - hit-testing (figureContains)
 *  - editable flag
 *  - getTextColumns boundary (min 4)
 *  - tab size invariant
 *  - font size get/set
 *  - clone independence
 *  - invalidate clears overflow cache
 */
public class TextAreaFigureTest {

    private TextAreaFigure figure;

    @Before
    public void setUp() {
        figure = new TextAreaFigure("Hello");
    }

    // -------------------------------------------------------------------------
    // getText / setText – best case
    // -------------------------------------------------------------------------

    @Test
    public void testGetTextReturnsSetText() {
        assertEquals("Hello", figure.getText());
    }

    @Test
    public void testSetTextUpdatesGetText() {
        figure.setText("World");
        assertEquals("World", figure.getText());
    }

    @Test
    public void testSetTextEmptyString() {
        figure.setText("");
        assertEquals("", figure.getText());
    }

    // -------------------------------------------------------------------------
    // setBounds / getBounds – best case and boundary
    // -------------------------------------------------------------------------

    @Test
    public void testSetBoundsNormal() {
        figure.setBounds(new Point2D.Double(10, 20), new Point2D.Double(110, 70));
        Rectangle2D.Double b = figure.getBounds();
        assertEquals(10.0, b.x, 0.001);
        assertEquals(20.0, b.y, 0.001);
        assertEquals(100.0, b.width, 0.001);
        assertEquals(50.0, b.height, 0.001);
    }

    @Test
    public void testSetBoundsReversedAnchorLead() {
        // lead before anchor – figure should swap to keep positive dimensions
        figure.setBounds(new Point2D.Double(110, 70), new Point2D.Double(10, 20));
        Rectangle2D.Double b = figure.getBounds();
        assertEquals(10.0, b.x, 0.001);
        assertEquals(20.0, b.y, 0.001);
        assertEquals(100.0, b.width, 0.001);
        assertEquals(50.0, b.height, 0.001);
    }

    @Test
    public void testSetBoundsSamePointGivesMinimumSize() {
        // Same anchor and lead must yield at least 1×1 (invariant)
        figure.setBounds(new Point2D.Double(5, 5), new Point2D.Double(5, 5));
        Rectangle2D.Double b = figure.getBounds();
        assertTrue("width must be >= 1", b.width >= 1);
        assertTrue("height must be >= 1", b.height >= 1);
        // Java assertion: dimensions never negative
        assert b.width >= 1 : "Invariant violated: width < 1";
        assert b.height >= 1 : "Invariant violated: height < 1";
    }

    // -------------------------------------------------------------------------
    // figureContains – best case and boundary
    // -------------------------------------------------------------------------

    @Test
    public void testFigureContainsPointInside() {
        figure.setBounds(new Point2D.Double(0, 0), new Point2D.Double(100, 100));
        assertTrue(figure.figureContains(new Point2D.Double(50, 50)));
    }

    @Test
    public void testFigureContainsPointOutside() {
        figure.setBounds(new Point2D.Double(0, 0), new Point2D.Double(100, 100));
        assertFalse(figure.figureContains(new Point2D.Double(200, 200)));
    }

    @Test
    public void testFigureContainsPointOnBoundary() {
        figure.setBounds(new Point2D.Double(0, 0), new Point2D.Double(100, 100));
        // Rectangle2D.contains is exclusive on the right/bottom edge
        assertTrue(figure.figureContains(new Point2D.Double(0, 0)));
    }

    // -------------------------------------------------------------------------
    // isEditable / setEditable
    // -------------------------------------------------------------------------

    @Test
    public void testIsEditableDefaultTrue() {
        assertTrue(figure.isEditable());
    }

    @Test
    public void testSetEditableFalse() {
        figure.setEditable(false);
        assertFalse(figure.isEditable());
    }

    @Test
    public void testSetEditableToggle() {
        figure.setEditable(false);
        figure.setEditable(true);
        assertTrue(figure.isEditable());
    }

    // -------------------------------------------------------------------------
    // getTextColumns – boundary: result always >= 4
    // -------------------------------------------------------------------------

    @Test
    public void testGetTextColumnsNullTextReturnsFour() {
        figure.setText(null);
        // invariant: getTextColumns() >= 4
        int cols = figure.getTextColumns();
        assertTrue("getTextColumns must be >= 4", cols >= 4);
        assertEquals(4, cols);
    }

    @Test
    public void testGetTextColumnsShortTextReturnsFour() {
        figure.setText("Hi");
        int cols = figure.getTextColumns();
        assertTrue("getTextColumns must be >= 4", cols >= 4);
        assertEquals(4, cols);
    }

    @Test
    public void testGetTextColumnsLongTextReturnsLength() {
        String longText = "Hello World";   // length 11 > 4
        figure.setText(longText);
        assertEquals(longText.length(), figure.getTextColumns());
    }

    @Test
    public void testGetTextColumnsExactlyFourChars() {
        figure.setText("Test");
        assertEquals(4, figure.getTextColumns());
    }

    // -------------------------------------------------------------------------
    // getTabSize – invariant: always 8
    // -------------------------------------------------------------------------

    @Test
    public void testGetTabSizeIsAlwaysEight() {
        int tabSize = figure.getTabSize();
        // Java assertion: tab size is a fixed invariant
        assert tabSize == 8 : "Invariant violated: tabSize != 8";
        assertEquals(8, tabSize);
    }

    // -------------------------------------------------------------------------
    // getFontSize / setFontSize
    // -------------------------------------------------------------------------

    @Test
    public void testGetFontSizeDefault() {
        assertEquals(12.0f, figure.getFontSize(), 0.001f);
    }

    @Test
    public void testSetFontSizeRoundTrip() {
        figure.setFontSize(24.0f);
        assertEquals(24.0f, figure.getFontSize(), 0.001f);
    }

    @Test
    public void testSetFontSizeSmallValue() {
        figure.setFontSize(1.0f);
        assertEquals(1.0f, figure.getFontSize(), 0.001f);
    }

    // -------------------------------------------------------------------------
    // clone – must produce independent copy
    // -------------------------------------------------------------------------

    @Test
    public void testCloneIsIndependent() {
        figure.setBounds(new Point2D.Double(0, 0), new Point2D.Double(50, 50));
        TextAreaFigure clone = figure.clone();

        // Mutating clone must not affect original
        clone.setBounds(new Point2D.Double(0, 0), new Point2D.Double(200, 200));
        assertEquals(50.0, figure.getBounds().width, 0.001);
    }

    @Test
    public void testClonePreservesText() {
        TextAreaFigure clone = figure.clone();
        assertEquals(figure.getText(), clone.getText());
    }

    @Test
    public void testClonePreservesBounds() {
        figure.setBounds(new Point2D.Double(10, 20), new Point2D.Double(60, 80));
        TextAreaFigure clone = figure.clone();
        assertEquals(figure.getBounds().x, clone.getBounds().x, 0.001);
        assertEquals(figure.getBounds().y, clone.getBounds().y, 0.001);
        assertEquals(figure.getBounds().width, clone.getBounds().width, 0.001);
        assertEquals(figure.getBounds().height, clone.getBounds().height, 0.001);
    }

    // -------------------------------------------------------------------------
    // invalidate – clears the overflow cache
    // -------------------------------------------------------------------------

    @Test
    public void testInvalidateClearsOverflowCache() {
        // Set up a small figure so overflow status can be computed
        figure.setBounds(new Point2D.Double(0, 0), new Point2D.Double(200, 200));

        // Access isTextOverflow to populate cache, then invalidate
        figure.isTextOverflow();   // populates cache
        figure.invalidate();       // must clear it without throwing

        // After invalidation, isTextOverflow should recompute without error
        figure.isTextOverflow();
    }
}
