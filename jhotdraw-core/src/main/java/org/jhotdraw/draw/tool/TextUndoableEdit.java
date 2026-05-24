/*
 * Copyright (c) 2009-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.tool;

import javax.swing.undo.AbstractUndoableEdit;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 * Undoable edit for text changes in TextHolderFigure instances.
 * Extracted from duplicated anonymous classes in TextCreationTool,
 * TextEditingTool, and TextAreaEditingTool.
 */
class TextUndoableEdit extends AbstractUndoableEdit {

    private static final long serialVersionUID = 1L;

    private final TextHolderFigure figure;
    private final String oldText;
    private final String newText;

    TextUndoableEdit(TextHolderFigure figure, String oldText, String newText) {
        assert figure != null : "figure must not be null";
        assert oldText != null : "oldText must not be null";
        assert newText != null : "newText must not be null";
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
