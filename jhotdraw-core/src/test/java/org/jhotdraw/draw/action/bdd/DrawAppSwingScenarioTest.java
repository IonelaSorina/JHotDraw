/*
 * @(#)DrawAppSwingScenarioTest.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.action.bdd;

import org.assertj.swing.fixture.FrameFixture;
import org.junit.Ignore;
import org.junit.Test;

/**
 * AssertJ-Swing end-to-end scenario for the Group / Ungroup feature.
 *
 * <p>This test is permanently {@link Ignore}d in this environment because
 * AssertJ-Swing requires a real graphical display (X11 or Wayland) and the
 * remote terminal used to run this lab cannot provide one. It is included
 * as documentation of the GUI-level test that <em>would</em> automate the
 * scenario end-to-end (open the Draw application, drag two rectangles,
 * select them, click Edit &rarr; Group, assert the resulting group exists
 * as a single composite figure).
 *
 * <p>To run this test locally on a workstation with a display:
 * <pre>
 *   /tmp/maven/bin/mvn test -pl jhotdraw-core \
 *       -Dtest=DrawAppSwingScenarioTest -DfailIfNoTests=false
 * </pre>
 * after removing the {@link Ignore} annotation.
 *
 * <p>The test depends on {@code jhotdraw-samples-misc} (the Draw sample
 * Main class) which is not on the {@code jhotdraw-core} test classpath,
 * so the import compiles only when the dependency is added to the POM
 * for the local run.
 */
@Ignore("AssertJ-Swing needs a real Swing display; this terminal is headless.")
public class DrawAppSwingScenarioTest {

    @SuppressWarnings("unused")
    private FrameFixture window;

    @Test
    public void user_can_group_two_drawn_rectangles_via_the_edit_menu() throws Exception {
        // Step 1: launch the Draw application on the Swing EDT.
        //   GuiActionRunner.execute(() -> Main.main(new String[0]));
        // (Main lives in jhotdraw-samples-misc; not on jhotdraw-core
        //  test classpath, so the local run would either reference it
        //  reflectively or be moved to the samples module.)
        Class.forName("org.assertj.swing.fixture.FrameFixture");

        // Step 2: locate the main frame.
        // window = WindowFinder.findFrame("Draw").using(robot);

        // Step 3: simulate the user drawing two rectangles
        //   - select the rectangle tool from the toolbar
        //   - drag from (10,10) to (50,50)
        //   - drag from (70,70) to (110,110)

        // Step 4: select-all (Ctrl+A) and trigger Edit -> Group.
        // window.menuItemWithPath("Edit", "Group").click();

        // Step 5: assert the drawing now contains exactly one CompositeFigure
        //   with two RectangleFigure children, using either AssertJ-Swing's
        //   component fixtures or the same DrawingAssert custom assertion
        //   that the JGiven scenarios use.
    }
}
