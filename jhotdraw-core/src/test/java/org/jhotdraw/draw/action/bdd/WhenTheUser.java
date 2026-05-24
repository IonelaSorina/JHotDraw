/*
 * @(#)WhenTheUser.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.action.bdd;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import java.awt.event.ActionEvent;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.GroupAction;
import org.jhotdraw.draw.action.UngroupAction;

/**
 * JGiven When-stage for Group / Ungroup scenarios.
 *
 * The action is constructed lazily inside each step (rather than in the
 * Given-stage) because that is the natural reading order of a BDD
 * scenario: the user has a drawing first, then *invokes* an action.
 */
public class WhenTheUser extends Stage<WhenTheUser> {

    @ExpectedScenarioState
    DrawingEditor editor;

    public WhenTheUser invokes_the_group_action() {
        new GroupAction(editor).actionPerformed(
                new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "group"));
        return self();
    }

    public WhenTheUser invokes_the_ungroup_action() {
        new UngroupAction(editor).actionPerformed(
                new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "ungroup"));
        return self();
    }
}
