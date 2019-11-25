package com.mylibrary.ui.progress;

public interface IDelegateProgress {

    void onShow(boolean delay, String message);

    void onHide();

}
