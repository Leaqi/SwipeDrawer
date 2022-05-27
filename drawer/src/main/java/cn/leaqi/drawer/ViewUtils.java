/*
    Copyright 2022 Leaqi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package cn.leaqi.drawer;

import android.view.View;

/**
 * ViewUtils is an view operation class
 * Created by Leaqi.
 * Github: https://github.com/Leaqi
 */
public class ViewUtils {
    View view = null;
    View mask = null;
    int type = -1;
    int width = 0;
    int height = 0;
    int left = 0;
    int top = 0;
    int right = 0;
    int bottom = 0;
    int paddingLeft = 0;
    int paddingTop = 0;
    int paddingRight = 0;
    int paddingBottom = 0;
    boolean intercept = true;

    ViewUtils(View v, int t) {
        view = v;
        type = t;
    }

    ViewUtils(View v, int t, boolean u) {
        view = v;
        type = t;
        Update(u);
    }

    void Update(boolean isAll) {
        width = view.getMeasuredWidth();
        height = view.getMeasuredHeight();
        if (isAll) {
            left = view.getLeft();
            top = view.getTop();
            right = view.getRight();
            bottom = view.getBottom();
            paddingLeft = view.getPaddingLeft();
            paddingTop = view.getPaddingTop();
            paddingRight = view.getPaddingRight();
            paddingBottom = view.getPaddingBottom();
        }
    }

    void setMask(View view) {
        mask = view;
    }

    void setScale(float scale) {
        view.setScaleX(scale);
        view.setScaleY(scale);
        if (mask != null) {
            mask.setScaleX(scale);
            mask.setScaleY(scale);
        }
    }

    void setRotation(float rotation) {
        view.setRotation(rotation);
        if (mask != null) {
            mask.setRotation(rotation);
        }
    }

    void setLeft(int l) {
        left = l;
        view.setLeft(left);
        right = l + width;
        view.setRight(right);
        upMask();
    }

    void setTop(int t) {
        top = t;
        view.setTop(top);
        bottom = t + height;
        view.setBottom(bottom);
        upMask();
    }

    void setRight(int r) {
        right = r;
        view.setRight(right);
        left = r - width;
        view.setLeft(left);
        upMask();
    }

    void setBottom(int b) {
        bottom = b;
        view.setBottom(bottom);
        top = b - height;
        view.setTop(top);
        upMask();
    }

    void setVisibility(boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    void setFront() {
        view.bringToFront();
        if (mask != null) {
            mask.bringToFront();
        }
    }

    void upMask(){
        if (mask != null) {
            mask.setLeft(left);
            mask.setRight(right);
            mask.setTop(top);
            mask.setBottom(bottom);
        }
    }

    boolean isScrollLeft() {
        return !view.canScrollHorizontally(-1);
    }

    boolean isScrollTop() {
        return !view.canScrollVertically(-1);
    }

    boolean isScrollRight() {
        return !view.canScrollHorizontally(1);
    }

    boolean isScrollBottom() {
        return !view.canScrollVertically(1);
    }


    boolean isActionDown(int downX, int downY, int[] location) {
        int[] iLocation = getLocation();
        int getX = iLocation[0] - location[0];
        int getY = iLocation[1] - location[1];
        return downX > getX && downY > getY && downX < (getX + width) && downY < (getY + height);
    }

    int[] getLocation() {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

}