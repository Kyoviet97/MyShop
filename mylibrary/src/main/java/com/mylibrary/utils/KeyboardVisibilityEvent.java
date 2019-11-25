package com.mylibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

public class KeyboardVisibilityEvent {

    public static void checkHideKeyboard(final Context mContext, final View viewRoot, final KeyboardVisibilityEventListener keyboardVisibilityEvent) {
        viewRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private final Rect r = new Rect();
            private final int visibleThreshold = Math.round(convertDpToPx(mContext, 100));
            private boolean wasOpened = false;

            @Override
            public void onGlobalLayout() {
                viewRoot.getWindowVisibleDisplayFrame(r);
                int heightDiff = viewRoot.getRootView().getHeight() - r.height();
                boolean isOpen = heightDiff > visibleThreshold;
                if (isOpen == wasOpened) {
                    return;
                }
                wasOpened = isOpen;
                Loging.d(KeyboardVisibilityEvent.class, "show/hide keyboard " + wasOpened);
                keyboardVisibilityEvent.onVisibilityChanged(wasOpened);
            }

            public float convertDpToPx(Context context, float dp) {
                Resources res = context.getResources();
                return dp * (res.getDisplayMetrics().densityDpi / 160f);
            }
        });
    }

}
