package com.mylibrary.ui.input;

/**
 * Created by Annv on 8/15/17.
 */

public interface DrawableClickListener {
    public static enum DrawablePosition {TOP, BOTTOM, LEFT, RIGHT}

    public void onClick(DrawablePosition target);
}
