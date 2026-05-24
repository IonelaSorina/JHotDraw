/*
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.tool;

import javax.swing.undo.AbstractUndoableEdit;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 * Undoable edit for a text change on a {@link TextHolderFigure}.
 * Extracted from the anonymous inner classes that were duplicated in
 * {@link TextCreationTool} and {@link TextEditingTool}.
 */
class TextChangeEdit extends AbstractUndoableEdit {

    private static final long serialVersionUID = 1L;

    private final TextHolderFigure figure;
    private final String oldText;
    private final String newText;

    TextChangeEdit(TextHolderFigure figure, String oldText, String newText) {
        this.figure = figure;
        this.oldText = oldText;
        this.newText = newText;
    }

    @Override
    public String getPresentationName() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
        return labels.getString("attribute.text.text");
    }

    @Override
    public void undo() {
        super.undo();
        figure.willChange();
        figure.setText(oldText);
        figure.changed();
    }

    @Override
    public void redo() {
        super.redo();
        figure.willChange();
        figure.setText(newText);
        figure.changed();
    }
}
