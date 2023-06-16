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

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/**
 * SwipeDrawer is an layout class
 * Created by Leaqi.
 * Github: https://github.com/Leaqi
 */
public class SwipeDrawer extends ViewGroup {

    public static final int MODE_DRAWER = 0;
    public static final int MODE_COVER = 1;
    public static final int MODE_FIXED = 2;

    public static final int DIRECTION_ORDER = -1;
    public static final int DIRECTION_MAIN = 0;
    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_TOP = 2;
    public static final int DIRECTION_RIGHT = 3;
    public static final int DIRECTION_BOTTOM = 4;

    public static final int MASK_NONE = 0;
    public static final int MASK_GENERAL = 1;
    public static final int MASK_GRADUAL = 2;

    public static final int TYPE_VIEW = 0;
    public static final int TYPE_SCROLL_Y = 1;
    public static final int TYPE_SCROLL_X = 2;

    public static final int MAIN_OPEN_NONE = 0;
    public static final int MAIN_OPEN_CLICK = 1;
    public static final int MAIN_OPEN_INTERCEPT = 2;

    public static final int CLOSE_TYPE_ALL = 0;
    public static final int CLOSE_TYPE_MAIN = 1;
    public static final int CLOSE_TYPE_DRAWER = 2;

    public static final int STATE_START = 0;
    public static final int STATE_OPEN = 1;
    public static final int STATE_CLOSE = 2;
    public static final int STATE_PROGRESS = 3;
    public static final int STATE_ANIM_OVER = 4;
    public static final int STATE_CALL_OPEN = 5;
    public static final int STATE_CALL_CLOSE = 6;
    public static final int STATE_CANCEL = 7;
    public static final int STATE_DRAG_INTO = 8;
    public static final int STATE_DRAG_OUT = 9;

    private static final int INVALID_POINTER = -1;

    private int open = 0;
    private int mask = 0;
    private int mode = 0;
    private int leftMode = -1;
    private int topMode = -1;
    private int rightMode = -1;
    private int bottomMode = -1;
    private int mainType = 0;
    private int leftType = 0;
    private int topType = 0;
    private int rightType = 0;
    private int bottomType = 0;
    private int mainOpen = 0;
    private int parentId = -1;
    private int mainScrollId = -1;
    private int leftScrollId = -1;
    private int topScrollId = -1;
    private int rightScrollId = -1;
    private int bottomScrollId = -1;
    private int mainId = -1;
    private int leftId = -1;
    private int topId = -1;
    private int rightId = -1;
    private int bottomId = -1;
    private SwipeDrawer parentDrawer = null;
    private ViewUtils mainScroll = null;
    private ViewUtils leftScroll = null;
    private ViewUtils topScroll = null;
    private ViewUtils rightScroll = null;
    private ViewUtils bottomScroll = null;
    private ViewUtils mainLayout = null;
    private ViewUtils leftLayout = null;
    private ViewUtils topLayout = null;
    private ViewUtils rightLayout = null;
    private ViewUtils bottomLayout = null;
    private View maskView = null;
    private float shrinkRange = 5;
    private int dragDamping = 10;
    private int dragRange = 0;
    private int leftDragRange = 0;
    private int topDragRange = 0;
    private int rightDragRange = 0;
    private int bottomDragRange = 0;
    private int dragSlop = 5;
    private int turnCancel = 10;
    private int maxDragSize = 0;
    private int dragCloseType = 0;
    private int duration = 200;
    private int maskColor = -2147483648;
    private int leftOffset = 0;
    private int topOffset = 0;
    private int rightOffset = 0;
    private int bottomOffset = 0;
    private boolean leftDragOpen = true;
    private boolean topDragOpen = true;
    private boolean rightDragOpen = true;
    private boolean bottomDragOpen = true;
    private boolean showLayout = false;
    private boolean autoClose = false;
    private boolean maskClose = false;
    private boolean dragClose = true;
    private boolean scrollOuterDrag = false;
    private boolean autoLayout = true;
    private static boolean allIntercept = false;
    private static SwipeDrawer autoDrawer = null;

    private int downX = 0;
    private int downY = 0;
    private int downMs = 0;
    private int moveSize = 0;
    private int lastSize = 0;
    private int turnSize = 0;
    private int getPointerId = INVALID_POINTER;
    private int getLastMoveX = 0;
    private int getLastMoveY = 0;
    private int getLastShiftX = 0;
    private int getLastShiftY = 0;

    private boolean isInit = false;
    private boolean isFinish = false;
    private boolean isTouch = false;
    private boolean isIntercept = false;
    private boolean isEmploy = false;
    private boolean isShow = false;
    private boolean arriveRange = false;
    private boolean isArriveCall = false;
    private boolean reLayout = false;
    private boolean isLayout = false;
    private int parentIntercept = 0;
    private int inDirection = -1;
    private float lastProgress = -1;

    private AnimThread animThread = null;

    private OnDrawerState onDrawerState = null;
    private OnDrawerSwitch onDrawerSwitch = null;
    private OnDrawerChange onDrawerChange = null;
    private Interpolator animInterpolator = null;

    public SwipeDrawer(Context context) {
        super(context);
        attrInit(context, null);
    }

    public SwipeDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        attrInit(context, attrs);
    }

    public SwipeDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        attrInit(context, attrs);
    }

    private void attrInit(Context context, AttributeSet attr) {
        if (attr != null) {
            final TypedArray attrArr = context.obtainStyledAttributes(attr, R.styleable.SwipeDrawer);
            open = attrArr.getInteger(R.styleable.SwipeDrawer_open, open);
            final int getMode = attrArr.getInteger(R.styleable.SwipeDrawer_mode, mode);
            final int getMask = attrArr.getInteger(R.styleable.SwipeDrawer_mask, mask);
            final int getLeftMode = attrArr.getInteger(R.styleable.SwipeDrawer_leftMode, leftMode);
            final int getTopMode = attrArr.getInteger(R.styleable.SwipeDrawer_topMode, topMode);
            final int getRightMode = attrArr.getInteger(R.styleable.SwipeDrawer_rightMode, rightMode);
            final int getBottomMode = attrArr.getInteger(R.styleable.SwipeDrawer_bottomMode, bottomMode);
            final int getMainType = attrArr.getInteger(R.styleable.SwipeDrawer_mainType, mainType);
            final int getLeftType = attrArr.getInteger(R.styleable.SwipeDrawer_leftType, leftType);
            final int getTopType = attrArr.getInteger(R.styleable.SwipeDrawer_topType, topType);
            final int getRightType = attrArr.getInteger(R.styleable.SwipeDrawer_rightType, rightType);
            final int getBottomType = attrArr.getInteger(R.styleable.SwipeDrawer_bottomType, bottomType);
            final int getMainOpen = attrArr.getInteger(R.styleable.SwipeDrawer_mainOpen, mainOpen);
            final float getShrinkRange = attrArr.getFloat(R.styleable.SwipeDrawer_shrinkRange, shrinkRange);
            final int getDragDamping = attrArr.getInteger(R.styleable.SwipeDrawer_dragDamping, dragDamping);
            final int getDragRange = attrArr.getDimensionPixelSize(R.styleable.SwipeDrawer_dragRange, dragRange);
            final int getLeftDragRange = attrArr.getDimensionPixelSize(R.styleable.SwipeDrawer_leftDragRange, leftDragRange);
            final int getTopDragRange = attrArr.getDimensionPixelSize(R.styleable.SwipeDrawer_topDragRange, topDragRange);
            final int getRightDragRange = attrArr.getDimensionPixelSize(R.styleable.SwipeDrawer_rightDragRange, rightDragRange);
            final int getBottomDragRange = attrArr.getDimensionPixelSize(R.styleable.SwipeDrawer_bottomDragRange, bottomDragRange);
            final int getDragSlop = attrArr.getDimensionPixelSize(R.styleable.SwipeDrawer_dragSlop, dragSlop);
            final int getTurnCancel = attrArr.getDimensionPixelSize(R.styleable.SwipeDrawer_turnCancel, turnCancel);
            final int getDragCloseType = attrArr.getInteger(R.styleable.SwipeDrawer_dragCloseType, dragCloseType);
            final int getMaxDragSize = attrArr.getDimensionPixelSize(R.styleable.SwipeDrawer_maxDragSize, maxDragSize);
            final int getDuration = attrArr.getInteger(R.styleable.SwipeDrawer_duration, duration);
            final int getMaskColor = attrArr.getColor(R.styleable.SwipeDrawer_maskColor, maskColor);
            final int getInterpolatorId = attrArr.getResourceId(R.styleable.SwipeDrawer_interpolator, -1);
            final int getLeftOffset = attrArr.getDimensionPixelSize(R.styleable.SwipeDrawer_leftOffset, leftOffset);
            final int getTopOffset = attrArr.getDimensionPixelSize(R.styleable.SwipeDrawer_topOffset, topOffset);
            final int getRightOffset = attrArr.getDimensionPixelSize(R.styleable.SwipeDrawer_rightOffset, rightOffset);
            final int getBottomOffset = attrArr.getDimensionPixelSize(R.styleable.SwipeDrawer_bottomOffset, bottomOffset);
            leftDragOpen = attrArr.getBoolean(R.styleable.SwipeDrawer_leftDragOpen, leftDragOpen);
            topDragOpen = attrArr.getBoolean(R.styleable.SwipeDrawer_topDragOpen, topDragOpen);
            rightDragOpen = attrArr.getBoolean(R.styleable.SwipeDrawer_rightDragOpen, rightDragOpen);
            bottomDragOpen = attrArr.getBoolean(R.styleable.SwipeDrawer_bottomDragOpen, bottomDragOpen);
            showLayout = attrArr.getBoolean(R.styleable.SwipeDrawer_showLayout, showLayout);
            autoClose = attrArr.getBoolean(R.styleable.SwipeDrawer_autoClose, autoClose);
            maskClose = attrArr.getBoolean(R.styleable.SwipeDrawer_maskClose, maskClose);
            dragClose = attrArr.getBoolean(R.styleable.SwipeDrawer_dragClose, dragClose);
            scrollOuterDrag = attrArr.getBoolean(R.styleable.SwipeDrawer_scrollOuterDrag, scrollOuterDrag);
            autoLayout = attrArr.getBoolean(R.styleable.SwipeDrawer_autoLayout, autoLayout);
            parentId = attrArr.getResourceId(R.styleable.SwipeDrawer_parentDrawer, parentId);
            mainScrollId = attrArr.getResourceId(R.styleable.SwipeDrawer_mainScroll, mainScrollId);
            leftScrollId = attrArr.getResourceId(R.styleable.SwipeDrawer_leftScroll, leftScrollId);
            topScrollId = attrArr.getResourceId(R.styleable.SwipeDrawer_topScroll, topScrollId);
            rightScrollId = attrArr.getResourceId(R.styleable.SwipeDrawer_rightScroll, rightScrollId);
            bottomScrollId = attrArr.getResourceId(R.styleable.SwipeDrawer_bottomScroll, bottomScrollId);
            mainId = attrArr.getResourceId(R.styleable.SwipeDrawer_mainLayout, mainId);
            leftId = attrArr.getResourceId(R.styleable.SwipeDrawer_leftLayout, leftId);
            topId = attrArr.getResourceId(R.styleable.SwipeDrawer_topLayout, topId);
            rightId = attrArr.getResourceId(R.styleable.SwipeDrawer_rightLayout, rightId);
            bottomId = attrArr.getResourceId(R.styleable.SwipeDrawer_bottomLayout, bottomId);
            if (getMode == MODE_DRAWER || getMode == MODE_COVER || getMode == MODE_FIXED) mode = getMode;
            if (getLeftMode == MODE_DRAWER || getLeftMode == MODE_COVER || getLeftMode == MODE_FIXED) leftMode = getLeftMode;
            if (getTopMode == MODE_DRAWER || getTopMode == MODE_COVER || getTopMode == MODE_FIXED) topMode = getTopMode;
            if (getRightMode == MODE_DRAWER || getRightMode == MODE_COVER || getRightMode == MODE_FIXED) rightMode = getRightMode;
            if (getBottomMode == MODE_DRAWER || getBottomMode == MODE_COVER || getBottomMode == MODE_FIXED) bottomMode = getBottomMode;
            if (getDragCloseType == CLOSE_TYPE_ALL || getDragCloseType == CLOSE_TYPE_MAIN || getDragCloseType == CLOSE_TYPE_DRAWER) dragCloseType = getDragCloseType;
            setMask(getMask);
            setMainType(getMainType);
            setLeftType(getLeftType);
            setTopType(getTopType);
            setRightType(getRightType);
            setBottomType(getBottomType);
            setMainOpen(getMainOpen);
            setShrinkRange(getShrinkRange);
            setDragDamping(getDragDamping);
            setDragRange(getDragRange);
            setLeftDragRange(getLeftDragRange);
            setTopDragRange(getTopDragRange);
            setRightDragRange(getRightDragRange);
            setBottomDragRange(getBottomDragRange);
            setDragSlop(getDragSlop);
            setTurnCancel(getTurnCancel);
            setMaxDragSize(getMaxDragSize);
            setMaskColor(getMaskColor);
            setDuration(getDuration);
            setLeftOffset(getLeftOffset);
            setTopOffset(getTopOffset);
            setRightOffset(getRightOffset);
            setBottomOffset(getBottomOffset);
            if (getInterpolatorId != -1) {
                animInterpolator = AnimationUtils.loadInterpolator(getContext(), getInterpolatorId);
            }
            attrArr.recycle();
        }
    }

    public void setMode(int type) {
        if (isShow) return;
        if (type == MODE_DRAWER || type == MODE_COVER || type == MODE_FIXED) {
            mode = type;
            checkTouch();
            updateLayout();
        }
    }

    public void setLeftMode(int type) {
        if (isShow) return;
        if (type == MODE_DRAWER || type == MODE_COVER || type == MODE_FIXED) {
            leftMode = type;
            checkTouch();
            updateLayout();
        }
    }

    public void setRightMode(int type) {
        if (isShow) return;
        if (type == MODE_DRAWER || type == MODE_COVER || type == MODE_FIXED) {
            rightMode = type;
            checkTouch();
            updateLayout();
        }
    }

    public void setTopMode(int type) {
        if (isShow) return;
        if (type == MODE_DRAWER || type == MODE_COVER || type == MODE_FIXED) {
            topMode = type;
            checkTouch();
            updateLayout();
        }
    }

    public void setBottomMode(int type) {
        if (isShow) return;
        if (type == MODE_DRAWER || type == MODE_COVER || type == MODE_FIXED) {
            bottomMode = type;
            checkTouch();
            updateLayout();
        }
    }

    public void setMask(int type) {
        if (type == MASK_NONE || type == MASK_GENERAL || type == MASK_GRADUAL) {
            mask = type;
            checkTouch();
            requestLayout();
        }
    }

    public void setMainType(int type) {
        if (type == TYPE_VIEW || type == TYPE_SCROLL_Y || type == TYPE_SCROLL_X) {
            mainType = type;
        }
    }

    public void setLeftType(int type) {
        if (type == TYPE_VIEW || type == TYPE_SCROLL_Y || type == TYPE_SCROLL_X) {
            leftType = type;
        }
    }

    public void setTopType(int type) {
        if (type == TYPE_VIEW || type == TYPE_SCROLL_Y || type == TYPE_SCROLL_X) {
            topType = type;
        }
    }

    public void setRightType(int type) {
        if (type == TYPE_VIEW || type == TYPE_SCROLL_Y || type == TYPE_SCROLL_X) {
            rightType = type;
        }
    }

    public void setBottomType(int type) {
        if (type == TYPE_VIEW || type == TYPE_SCROLL_Y || type == TYPE_SCROLL_X) {
            bottomType = type;
        }
    }

    public void setMainOpen(int type) {
        if (type == MAIN_OPEN_NONE || type == MAIN_OPEN_CLICK || type == MAIN_OPEN_INTERCEPT) {
            mainOpen = type;
        }
    }

    public void setShrinkRange(float num) {
        if (num < 0) num = 0;
        shrinkRange = num;
    }

    public void setDragDamping(int num) {
        if (num < 0 || num > 100) num = 10;
        dragDamping = num;
    }

    public void setDragRange(int num) {
        if (num < -1) num = -1;
        dragRange = num;
    }

    public void setLeftDragRange(int num) {
        if (num < 0) num = 0;
        leftDragRange = num;
    }

    public void setTopDragRange(int num) {
        if (num < 0) num = 0;
        topDragRange = num;
    }

    public void setRightDragRange(int num) {
        if (num < 0) num = 0;
        rightDragRange = num;
    }

    public void setBottomDragRange(int num) {
        if (num < 0) num = 0;
        bottomDragRange = num;
    }

    public void setDragSlop(int num) {
        if (num < 0) num = 0;
        dragSlop = num;
    }

    public void setTurnCancel(int num) {
        if (num < 0) num = 0;
        turnCancel = num;
    }

    public void setMaxDragSize(int num) {
        if (num < 0) num = 0;
        maxDragSize = num;
    }

    public void setDuration(int ms) {
        if (ms < 0) return;
        duration = ms;
    }

    public void setLeftOffset(int num) {
        if (isShow) return;
        if (num < 0) num = 0;
        leftOffset = num;
        if (isFinish) {
            checkTouch();
            updateLayout();
        }
    }

    public void setTopOffset(int num) {
        if (isShow) return;
        if (num < 0) num = 0;
        topOffset = num;
        if (isFinish) {
            checkTouch();
            updateLayout();
        }
    }

    public void setRightOffset(int num) {
        if (isShow) return;
        if (num < 0) num = 0;
        rightOffset = num;
        if (isFinish) {
            checkTouch();
            updateLayout();
        }
    }

    public void setBottomOffset(int num) {
        if (isShow) return;
        if (num < 0) num = 0;
        bottomOffset = num;
        if (isFinish) {
            checkTouch();
            updateLayout();
        }
    }

    public void setLeftDragOpen(boolean bool) {
        leftDragOpen = bool;
    }

    public void setTopDragOpen(boolean bool) {
        topDragOpen = bool;
    }

    public void setRightDragOpen(boolean bool) {
        rightDragOpen = bool;
    }

    public void setBottomDragOpen(boolean bool) {
        bottomDragOpen = bool;
    }

    public void setShowLayout(boolean bool) {
        showLayout = bool;
    }

    public void setAutoClose(boolean bool) {
        autoClose = bool;
    }

    public void setMaskClose(boolean bool) {
        maskClose = bool;
    }

    public void setMaskColor(int color) {
        maskColor = color;
        if (maskView != null) {
            maskView.setBackgroundColor(maskColor);
        }
    }

    public void setDragClose(boolean bool) {
        dragClose = bool;
    }

    public void setDragCloseType(int type) {
        if (type == CLOSE_TYPE_ALL || type == CLOSE_TYPE_MAIN || type == CLOSE_TYPE_DRAWER) {
            dragCloseType = type;
        }
    }

    public void setScrollOuterDrag(boolean bool) {
        scrollOuterDrag = bool;
    }

    public void setAutoLayout(boolean bool) {
        autoLayout = bool;
    }

    public void setParentDrawer(final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ViewParent view = SwipeDrawer.this;
                    while ((view = view.getParent()) != null) {
                        if (view instanceof SwipeDrawer) {
                            if (((SwipeDrawer) view).getId() == id) {
                                parentDrawer = (SwipeDrawer) view;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setParentDrawer(SwipeDrawer view) {
        if (view == null) return;
        parentDrawer = view;
    }

    public void setMainScroll(int id) {
        setMainScroll(findViewById(id));
    }

    public void setMainScroll(View view) {
        if (view != null) {
            mainScroll = new ViewUtils(view, DIRECTION_MAIN, true);
            requestLayout();
        }
    }

    public void setLeftScroll(int id) {
        setLeftScroll(findViewById(id));
    }

    public void setLeftScroll(View view) {
        if (view != null) {
            leftScroll = new ViewUtils(view, DIRECTION_LEFT, true);
            requestLayout();
        }
    }

    public void setTopScroll(int id) {
        setTopScroll(findViewById(id));
    }

    public void setTopScroll(View view) {
        if (view != null) {
            topScroll = new ViewUtils(view, DIRECTION_TOP, true);
            requestLayout();
        }
    }

    public void setRightScroll(int id) {
        setRightScroll(findViewById(id));
    }

    public void setRightScroll(View view) {
        if (view != null) {
            rightScroll = new ViewUtils(view, DIRECTION_RIGHT, true);
            requestLayout();
        }
    }

    public void setBottomScroll(int id) {
        setBottomScroll(findViewById(id));
    }

    public void setBottomScroll(View view) {
        if (view != null) {
            bottomScroll = new ViewUtils(view, DIRECTION_BOTTOM, true);
            requestLayout();
        }
    }

    public void setMainLayout(int id) {
        setMainLayout(findViewById(id));
    }

    public void setMainLayout(View view) {
        if (isShow) return;
        if (view != null) {
            mainLayout = new ViewUtils(view, DIRECTION_MAIN, true);
            checkTouch();
            requestLayout();
        }
    }

    public void setLeftLayout(int id) {
        setLeftLayout(findViewById(id));
    }

    public void setLeftLayout(View view) {
        if (isShow) return;
        if (view != null) {
            leftLayout = new ViewUtils(view, DIRECTION_LEFT, true);
            checkTouch();
            requestLayout();
        }
    }

    public void setTopLayout(int id) {
        setTopLayout(findViewById(id));
    }

    public void setTopLayout(View view) {
        if (isShow) return;
        if (view != null) {
            topLayout = new ViewUtils(view, DIRECTION_TOP, true);
            checkTouch();
            requestLayout();
        }
    }

    public void setRightLayout(int id) {
        setRightLayout(findViewById(id));
    }

    public void setRightLayout(View view) {
        if (isShow) return;
        if (view != null) {
            rightLayout = new ViewUtils(view, DIRECTION_RIGHT, true);
            checkTouch();
            requestLayout();
        }
    }

    public void setBottomLayout(int id) {
        setBottomLayout(findViewById(id));
    }

    public void setBottomLayout(View view) {
        if (isShow) return;
        if (view != null) {
            bottomLayout = new ViewUtils(view, DIRECTION_BOTTOM, true);
            checkTouch();
            requestLayout();
        }
    }

    public void setMainScale(float scale) {
        if (isTouch) {
            mainLayout.setScale(scale);
        }
    }

    public void setMainRotation(float rotation) {
        if (isTouch) {
            mainLayout.setRotation(rotation);
        }
    }

    public boolean setIntercept(boolean intercept) {
        return setIntercept(DIRECTION_LEFT, intercept) || setIntercept(DIRECTION_TOP, intercept) || setIntercept(DIRECTION_RIGHT, intercept)|| setIntercept(DIRECTION_BOTTOM, intercept);
    }

    public boolean setIntercept(View view, boolean intercept) {
        final int type = getDirection(view);
        return setIntercept(type, intercept);
    }

    public boolean setIntercept(int type, boolean intercept) {
        final ViewUtils utils = getViewUtils(type);
        if (utils != null) {
            utils.intercept = intercept;
        }
        return utils != null;
    }

    public void setOnDrawerState(OnDrawerState listener) {
        onDrawerState = listener;
    }

    public void setOnDrawerSwitch(OnDrawerSwitch listener) {
        onDrawerSwitch = listener;
    }

    public void setOnDrawerChange(OnDrawerChange listener) {
        onDrawerChange = listener;
    }

    public void setInterpolator(Interpolator interpolator) {
        animInterpolator = interpolator;
    }

    public static void setAllIntercept(boolean bool) {
        allIntercept = bool;
    }

    public int getMode() {
        return mode;
    }

    public int getLeftMode() {
        return leftMode;
    }

    public int getTopMode() {
        return topMode;
    }

    public int getRightMode() {
        return rightMode;
    }

    public int getBottomMode() {
        return bottomMode;
    }

    public int getMask() {
        return mask;
    }

    public View getMaskView() {
        return maskView;
    }

    public View getMainView() {
        return mainLayout == null ? null : mainLayout.view;
    }

    public View getLeftView() {
        return leftLayout == null ? null : leftLayout.view;
    }

    public View getTopView() {
        return topLayout == null ? null : topLayout.view;
    }

    public View getRightView() {
        return rightLayout == null ? null : rightLayout.view;
    }

    public View getBottomView() {
        return bottomLayout == null ? null : bottomLayout.view;
    }

    public int getMainType() {
        return mainType;
    }

    public int getLeftType() {
        return leftType;
    }

    public int getTopType() {
        return topType;
    }

    public int getRightType() {
        return rightType;
    }

    public int getBottomType() {
        return bottomType;
    }

    public int getMainOpen() {
        return mainOpen;
    }

    public float getShrinkRange() {
        return shrinkRange;
    }

    public int getDragDamping() {
        return dragDamping;
    }

    public int getDragRange() {
        return dragRange;
    }

    public int getLeftDragRange() {
        return leftDragRange;
    }

    public int getTopDragRange() {
        return topDragRange;
    }

    public int getRightDragRange() {
        return rightDragRange;
    }

    public int getBottomDragRange() {
        return bottomDragRange;
    }

    public int getDragSlop() {
        return dragSlop;
    }

    public int getTurnCancel() {
        return turnCancel;
    }

    public int getMaxDragSize() {
        return maxDragSize;
    }

    public int getDuration() {
        return duration;
    }

    public int getLeftOffset() {
        return leftOffset;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public int getRightOffset() {
        return rightOffset;
    }

    public int getBottomOffset() {
        return bottomOffset;
    }

    public boolean getLeftDragOpen() {
        return leftDragOpen;
    }

    public boolean getTopDragOpen() {
        return topDragOpen;
    }

    public boolean getRightDragOpen() {
        return rightDragOpen;
    }

    public boolean getBottomDragOpen() {
        return bottomDragOpen;
    }

    public boolean getShowLayout() {
        return showLayout;
    }

    public boolean getAutoClose() {
        return autoClose;
    }

    public boolean getMaskClose() {
        return maskClose;
    }

    public int getDragCloseType() {
        return dragCloseType;
    }

    public boolean getScrollOuterDrag() {
        return scrollOuterDrag;
    }

    public boolean getAutoLayout() {
        return autoLayout;
    }

    public boolean getIntercept() {
        return isIntercept;
    }

    public boolean getShow() {
        return isShow;
    }

    public boolean getArriveRange() {
        return arriveRange;
    }

    public int getDirection() {
        return inDirection;
    }

    public int getMaskColor() {
        return maskColor;
    }

    public static boolean getAllIntercept() {
        return allIntercept;
    }

    public static SwipeDrawer getParentDrawer(View view) {
        try {
            if (view != null) {
                ViewParent parent = view.getParent();
                while (parent != null) {
                    if (parent instanceof SwipeDrawer) {
                        return (SwipeDrawer) parent;
                    } else {
                        parent = parent.getParent();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getDirection(View view) {
        if (mainLayout != null) {
            if (view == mainLayout.view) return DIRECTION_MAIN;
        }
        if (leftLayout != null) {
            if (view == leftLayout.view) return DIRECTION_LEFT;
        }
        if (topLayout != null) {
            if (view == topLayout.view) return DIRECTION_TOP;
        }
        if (rightLayout != null) {
            if (view == rightLayout.view) return DIRECTION_RIGHT;
        }
        if (bottomLayout != null) {
            if (view == bottomLayout.view) return DIRECTION_BOTTOM;
        }
        return DIRECTION_ORDER;
    }

    public int getDirectionMode(int type) {
        if (type == DIRECTION_LEFT && leftMode != -1) {
            return leftMode;
        } else if (type == DIRECTION_TOP && topMode != -1) {
            return topMode;
        } else if (type == DIRECTION_RIGHT && rightMode != -1) {
            return rightMode;
        } else if (type == DIRECTION_BOTTOM && bottomMode != -1) {
            return bottomMode;
        }
        return mode;
    }

    public int getDragRange(int type) {
        if (type == DIRECTION_LEFT && leftDragRange != 0) {
            return leftDragRange;
        } else if (type == DIRECTION_TOP && topDragRange != 0) {
            return topDragRange;
        } else if (type == DIRECTION_RIGHT && rightDragRange != 0) {
            return rightDragRange;
        } else if (type == DIRECTION_BOTTOM && bottomDragRange != 0) {
            return bottomDragRange;
        }
        return dragRange;
    }

    public void openDrawer(int type) {
        openDrawer(type, true, true);
    }

    public void openDrawer(int type, boolean anim) {
        openDrawer(type, anim, true);
    }

    public void openDrawer(int type, boolean anim, boolean call) {
        if (isShow || AnimThread.isThread(animThread)) return;
        final ViewUtils viewUtils = getViewUtils(type);
        if (viewUtils != null) {
            if (!isInit) {
                open = type;
                return;
            }
            inDirection = type;
            frontLayout(viewUtils);
            isShow = true;
            if (onDrawerChange != null) onDrawerChange.onChange(this, STATE_CALL_OPEN, 0);
            toggleLayout();
            animRecovery(true, anim, call);
            if (call) onOpen();
            if (mask == MASK_GENERAL) {
                progressMask(1f);
            }
        }
    }

    public void closeDrawer() {
        closeDrawer(inDirection, true, true);
    }

    public void closeDrawer(boolean anim) {
        closeDrawer(inDirection, anim, true);
    }

    public void closeDrawer(boolean anim, boolean call) {
        closeDrawer(inDirection, anim, call);
    }

    public void closeDrawer(int type) {
        closeDrawer(type, true, true);
    }

    public void closeDrawer(int type, boolean anim) {
        closeDrawer(type, anim, true);
    }

    public void closeDrawer(int type, boolean anim, boolean call) {
        if (!isShow || type != inDirection || AnimThread.isThread(animThread)) return;
        final ViewUtils viewUtils = getViewUtils(type);
        if (viewUtils != null) {
            inDirection = type;
            isShow = false;
            if (onDrawerChange != null) onDrawerChange.onChange(this, STATE_CALL_CLOSE, 0);
            toggleLayout();
            animRecovery(false, anim, call);
            if (call) onClose();
            if (mask == MASK_GENERAL) {
                progressMask(0f);
            }
        }
    }

    public void forceClose(boolean anim) {
        forceClose(anim, true);
    }

    public void forceClose(boolean anim, boolean call) {
        if (isShow) {
            if (AnimThread.isThread(animThread)) {
                animThread.setStop();
                animThread.setAlive();
            }
            isShow = false;
            if (onDrawerChange != null) onDrawerChange.onChange(this, STATE_CALL_CLOSE, 0);
            toggleLayout();
            animRecovery(false, anim, call);
            if (call) onClose();
            if (mask == MASK_GENERAL) {
                progressMask(0f);
            }
        }
    }

    public void toggleDrawer(int type) {
        toggleDrawer(type, true, true);
    }

    public void toggleDrawer(int type, boolean anim) {
        toggleDrawer(type, anim, true);
    }

    public void toggleDrawer(int type, boolean anim, boolean call) {
        if (isShow) {
            closeDrawer(type, anim, call);
        } else {
            openDrawer(type, anim, call);
        }
    }

    public void updateLayout() {
        reLayout = true;
        requestLayout();
    }

    public void drawerLayout() {
        isLayout = true;
        requestLayout();
    }

    public void updateSize(boolean isAll) {
        if (mainScroll != null) mainScroll.Update(isAll);
        if (leftScroll != null) leftScroll.Update(isAll);
        if (topScroll != null) topScroll.Update(isAll);
        if (rightScroll != null) rightScroll.Update(isAll);
        if (bottomScroll != null) bottomScroll.Update(isAll);
        if (mainLayout != null) mainLayout.Update(isAll);
        if (leftLayout != null) leftLayout.Update(isAll);
        if (topLayout != null) topLayout.Update(isAll);
        if (rightLayout != null) rightLayout.Update(isAll);
        if (bottomLayout != null) bottomLayout.Update(isAll);
    }

    private void setParentIntercept(int intercept, boolean isAll) {
        try {
            if (parentDrawer != null) {
                SwipeDrawer view = parentDrawer;
                view.parentIntercept = intercept;
                if (isAll) {
                    while ((view = view.parentDrawer) != null) {
                        view.parentIntercept = intercept;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void progressMask(float alpha) {
        if (maskView != null) {
            maskView.setAlpha(alpha);
        }
    }

    private void requestMask(boolean bool) {
        if (maskView != null) {
            if (bool) {
                maskView.bringToFront();
                maskView.setVisibility(VISIBLE);
            } else {
                maskView.setAlpha(0f);
                maskView.setVisibility(GONE);
            }
        }
    }

    private void requestIntercept() {
        try {
            final ViewParent parent = getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int[] getLocation() {
        int[] location = new int[2];
        getLocationOnScreen(location);
        return location;
    }

    private ViewUtils getViewUtils(int type) {
        if (type == DIRECTION_MAIN && mainLayout != null) {
            return mainLayout;
        } else if (type == DIRECTION_LEFT && leftLayout != null) {
            return leftLayout;
        } else if (type == DIRECTION_TOP && topLayout != null) {
            return topLayout;
        } else if (type == DIRECTION_RIGHT && rightLayout != null) {
            return rightLayout;
        } else if (type == DIRECTION_BOTTOM && bottomLayout != null) {
            return bottomLayout;
        }
        return null;
    }

    private ViewUtils getScrollUtils(int type) {
        if (type == DIRECTION_MAIN && mainScroll != null) {
            return mainScroll;
        } else if (type == DIRECTION_LEFT && leftScroll != null) {
            return leftScroll;
        } else if (type == DIRECTION_TOP && topScroll != null) {
            return topScroll;
        } else if (type == DIRECTION_RIGHT && rightScroll != null) {
            return rightScroll;
        } else if (type == DIRECTION_BOTTOM && bottomScroll != null) {
            return bottomScroll;
        }
        return null;
    }

    private boolean isScrollType(int type) {
        final int getMode = getDirectionMode(type);
        final boolean isDrawer = getMode == MODE_DRAWER;
        final boolean isFixed = getMode == MODE_FIXED;
        final boolean isCover = getMode == MODE_COVER;
        final boolean isLeft = type == DIRECTION_LEFT;
        final boolean isTop = type == DIRECTION_TOP;
        final boolean isRight = type == DIRECTION_RIGHT;
        final boolean isBottom = type == DIRECTION_BOTTOM;
        final ViewUtils mainUtils = getViewUtils(DIRECTION_MAIN);
        final ViewUtils drawerUtils = getViewUtils(type);
        boolean bool = true;
        ViewUtils viewUtils;
        if (mainUtils != null && drawerUtils != null) {
            if (isShow) {
                if (dragCloseType == CLOSE_TYPE_MAIN) {
                    if (isDrawer || isFixed) {
                        bool = mainUtils.isActionDown(downX, downY, getLocation());
                    } else if (isCover) {
                        bool = !drawerUtils.isActionDown(downX, downY, getLocation());
                    }
                } else if (dragCloseType == CLOSE_TYPE_DRAWER) {
                    bool = drawerUtils.isActionDown(downX, downY, getLocation());
                }
                if (bool) {
                    viewUtils = getScrollUtils(type);
                    if (viewUtils != null) {
                        if (isLeft && !viewUtils.isScrollRight()) bool = false;
                        if (isTop && !viewUtils.isScrollBottom()) bool = false;
                        if (isRight && !viewUtils.isScrollLeft()) bool = false;
                        if (isBottom && !viewUtils.isScrollTop()) bool = false;
                        if (scrollOuterDrag) {
                            if (!viewUtils.isActionDown(downX, downY, getLocation())) {
                                bool = true;
                            }
                        }
                    } else {
                        int setType = 0;
                        if (isLeft) setType = leftType;
                        if (isTop) setType = topType;
                        if (isRight) setType = rightType;
                        if (isBottom) setType = bottomType;
                        if (setType == TYPE_SCROLL_Y) {
                            if (isTop && !drawerUtils.isScrollBottom()) bool = false;
                            if (isBottom && !drawerUtils.isScrollTop()) bool = false;
                        } else if (setType == TYPE_SCROLL_X) {
                            if (isLeft && !drawerUtils.isScrollRight()) bool = false;
                            if (isRight && !drawerUtils.isScrollLeft()) bool = false;
                        }
                    }
                }
            } else {
                viewUtils = getScrollUtils(DIRECTION_MAIN);
                if (viewUtils != null) {
                    if (isLeft && !viewUtils.isScrollLeft()) bool = false;
                    if (isTop && !viewUtils.isScrollTop()) bool = false;
                    if (isRight && !viewUtils.isScrollRight()) bool = false;
                    if (isBottom && !viewUtils.isScrollBottom()) bool = false;
                } else {
                    if (mainType == TYPE_SCROLL_Y) {
                        if (isTop && !mainUtils.isScrollTop()) bool = false;
                        if (isBottom && !mainUtils.isScrollBottom()) bool = false;
                    } else if (mainType == TYPE_SCROLL_X) {
                        if (isLeft && !mainUtils.isScrollLeft()) bool = false;
                        if (isRight && !mainUtils.isScrollRight()) bool = false;
                    }
                }
            }
        }
        return bool;
    }

    private ViewUtils getIntercept(int shiftX, int shiftY) {
        if (leftLayout != null && ((isShow && inDirection == DIRECTION_LEFT && shiftX < 0 && Math.abs(shiftX) > Math.abs(shiftY)) || (!isShow && leftDragOpen && shiftX > Math.abs(shiftY)))) {
            int leftRange = getDragRange(DIRECTION_LEFT);
            if (leftLayout.intercept && isScrollType(DIRECTION_LEFT) && (isShow || leftRange == 0 || leftRange >= downX)) {
                return leftLayout;
            }
        }
        if (topLayout != null && ((isShow && inDirection == DIRECTION_TOP && shiftY < 0 && Math.abs(shiftY) > Math.abs(shiftX)) || (!isShow && topDragOpen && shiftY > Math.abs(shiftX)))) {
            int topRange = getDragRange(DIRECTION_TOP);
            if (topLayout.intercept && isScrollType(DIRECTION_TOP) && (isShow || topRange == 0 || topRange >= downY)) {
                return topLayout;
            }
        }
        if (rightLayout != null && ((!isShow && rightDragOpen && shiftX < 0 && Math.abs(shiftX) > Math.abs(shiftY)) || (isShow && inDirection == DIRECTION_RIGHT && shiftX > Math.abs(shiftY)))) {
            int rightRange = getDragRange(DIRECTION_RIGHT);
            if (rightLayout.intercept && isScrollType(DIRECTION_RIGHT) && (isShow || rightRange == 0 || rightRange >= (mainLayout.width - downX))) {
                return rightLayout;
            }
        }
        if (bottomLayout != null && ((!isShow && bottomDragOpen && shiftY < 0 && Math.abs(shiftY) > Math.abs(shiftX)) || (isShow && inDirection == DIRECTION_BOTTOM && shiftY > Math.abs(shiftX)))) {
            int bottomRange = getDragRange(DIRECTION_BOTTOM);
            if (bottomLayout.intercept && isScrollType(DIRECTION_BOTTOM) && (isShow || bottomRange == 0 || bottomRange >= (mainLayout.height - downY))) {
                return bottomLayout;
            }
        }
        setParentIntercept(0, false);
        return null;
    }

    private void animRecovery(final boolean bool, final boolean anim, final boolean call) {
        if (!isTouch) return;
        final ViewUtils viewUtils = getViewUtils(inDirection);
        if (viewUtils == null) return;
        final int getMode = getDirectionMode(inDirection);
        final boolean isDrawer = getMode == MODE_DRAWER;
        final boolean isFixed = getMode == MODE_FIXED;
        final boolean isCover = getMode == MODE_COVER;
        final boolean isLeft = inDirection == DIRECTION_LEFT;
        final boolean isTop = inDirection == DIRECTION_TOP;
        final boolean isRight = inDirection == DIRECTION_RIGHT;
        final boolean isBottom = inDirection == DIRECTION_BOTTOM;
        final int offNum = getOffset();
        int setNum = isLeft || isRight ? (isLeft ? leftLayout.width : -rightLayout.width) : (isTop ? topLayout.height : -bottomLayout.height);
        int oldNum = isLeft || isRight ? mainLayout.left : mainLayout.top;
        if (isCover) {
            if (isLeft) oldNum = viewUtils.width + viewUtils.left;
            if (isTop) oldNum = viewUtils.height + viewUtils.top;
            if (isRight) oldNum = viewUtils.left - mainLayout.width;
            if (isBottom) oldNum = viewUtils.top - mainLayout.height;
        }
        final int offValue = bool ? 0 : isLeft || isTop ? -offNum : offNum;
        final int oldValue = oldNum;
        final int setValue = (bool ? setNum : 0) - oldValue;
        isIntercept = false;
        if (anim && duration > 0) {
            animThread = new AnimThread(duration, animInterpolator) {
                @Override
                protected void onUpdate(final float value) {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (getStop()) return;
                            int newValue = (int) Math.ceil((setValue - offValue) * value);
                            if (isLeft || isRight) {
                                if (isDrawer || isFixed) mainLayout.setLeft(oldValue + newValue);
                                if (isDrawer || isCover)
                                    viewUtils.setLeft(oldValue + newValue + (isLeft ? -viewUtils.width : mainLayout.width));
                            } else if (isTop || isBottom) {
                                if (isDrawer || isFixed) mainLayout.setTop(oldValue + newValue);
                                if (isDrawer || isCover)
                                    viewUtils.setTop(oldValue + newValue + (isTop ? -viewUtils.height : mainLayout.height));
                            }
                            offsetMove(getMode);
                            if (isCover && bool && offNum > 0) newValue = (int) Math.ceil(setValue * value);
                            float progress = (float) Math.abs(oldValue + newValue - (isRight || isBottom ? -offNum : offNum)) / ((isLeft || isRight ? viewUtils.width : viewUtils.height) - offNum);
                            if (lastProgress != progress) {
                                if(call) onMove(progress);
                                lastProgress = progress;
                            }
                            if (mask == MASK_GRADUAL) {
                                progressMask(progress);
                            }
                            if (value >= 1) {
                                setStop();
                                setAlive();
                                if (!isShow) {
                                    requestMask(false);
                                }
                                if (onDrawerChange != null && call) {
                                    onDrawerChange.onChange(SwipeDrawer.this, STATE_ANIM_OVER, 0);
                                }
                                isEmploy = false;
                            }
                        }
                    });
                }
            };
        } else {
            if (AnimThread.isThread(animThread)) {
                animThread.setStop();
                animThread.setAlive();
            }
            if (isLeft || isRight) {
                if (isDrawer || isFixed) mainLayout.setLeft(oldValue + setValue);
                if (isDrawer || isCover) viewUtils.setLeft(oldValue + setValue + (isLeft ? -viewUtils.width : mainLayout.width) - offValue);
            } else if (isTop || isBottom) {
                if (isDrawer || isFixed) mainLayout.setTop(oldValue + setValue);
                if (isDrawer || isCover) viewUtils.setTop(oldValue + setValue + (isTop ? -viewUtils.height : mainLayout.height) - offValue);
            }
            offsetMove(getMode);
            float progress = isShow ? 1f : 0f;
            if(call) onMove(progress);
            progressMask(progress);
            if (!isShow) {
                requestMask(false);
            }
            if (onDrawerChange != null && call) {
                onDrawerChange.onChange(SwipeDrawer.this, STATE_ANIM_OVER, 0);
            }
            isEmploy = false;
        }
        if (isShow && autoClose) autoDrawer = this;
    }

    private void onOpen() {
        if (isShow) {
            if (onDrawerState != null) onDrawerState.onOpen(inDirection);
            if (onDrawerSwitch != null) onDrawerSwitch.onOpen(this);
            if (onDrawerChange != null) onDrawerChange.onChange(this, STATE_OPEN, 0);
        }
    }

    private void onClose() {
        if (!isShow) {
            if (onDrawerState != null) onDrawerState.onClose(inDirection);
            if (onDrawerSwitch != null) onDrawerSwitch.onClose(this);
            if (onDrawerChange != null) onDrawerChange.onChange(this, STATE_CLOSE, 0);
        }
    }

    private void onMove(float progress) {
        if (onDrawerState != null) onDrawerState.onMove(inDirection, progress);
        if (onDrawerChange != null) onDrawerChange.onChange(this, STATE_PROGRESS, progress);
    }

    private void cacheDrawer(boolean anim) {
        try {
            if (autoClose && autoDrawer != null && autoDrawer != this) {
                autoDrawer.forceClose(anim);
                autoDrawer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getOffset() {
        return getOffset(inDirection);
    }

    private int getOffset(int type) {
        int num = 0;
        if (checkOffset(type)) {
            switch (type) {
                case DIRECTION_LEFT:
                    num = leftOffset;
                    break;
                case DIRECTION_TOP:
                    num = topOffset;
                    break;
                case DIRECTION_RIGHT:
                    num = rightOffset;
                    break;
                case DIRECTION_BOTTOM:
                    num = bottomOffset;
                    break;
            }
        }
        return num;
    }

    private void offsetMove(int mode) {
        if (!showLayout || mode == MODE_COVER) return;
        int[] directionList = {DIRECTION_LEFT, DIRECTION_TOP, DIRECTION_RIGHT, DIRECTION_BOTTOM};
        for (int val : directionList) {
            if (val != inDirection && checkOffset(val)) {
                ViewUtils viewUtils = getViewUtils(val);
                if (viewUtils != null)  {
                    viewUtils.setOffset(mainLayout.left, mainLayout.top);
                }
            }
        }
    }

    private void frontOffset() {
        int[] directionList = {DIRECTION_LEFT, DIRECTION_TOP, DIRECTION_RIGHT, DIRECTION_BOTTOM};
        for (int val : directionList) {
            ViewUtils viewUtils = getViewUtils(val);
            if (viewUtils != null && checkOffset(val))  viewUtils.setFront();
        }
    }

    private void frontLayout(ViewUtils viewUtils) {
        int getMode = getDirectionMode(inDirection);
        if (getMode == MODE_FIXED) {
            viewUtils.setFront();
            mainLayout.setFront();
            frontOffset();
            requestMask(true);
        } else {
            mainLayout.setFront();
            frontOffset();
            requestMask(true);
            viewUtils.setFront();
        }
    }

    private void toggleLayout() {
        if (showLayout || (!isInit && (checkOffset(DIRECTION_LEFT) || checkOffset(DIRECTION_TOP) || checkOffset(DIRECTION_RIGHT) || checkOffset(DIRECTION_BOTTOM)))) return;
        if (leftLayout != null) leftLayout.setVisibility(inDirection == leftLayout.type);
        if (topLayout != null) topLayout.setVisibility(inDirection == topLayout.type);
        if (rightLayout != null) rightLayout.setVisibility(inDirection == rightLayout.type);
        if (bottomLayout != null) bottomLayout.setVisibility(inDirection == bottomLayout.type);
    }

    private boolean checkOffset(int type) {
        boolean bool = false;
        switch (type) {
            case DIRECTION_LEFT:
                bool = leftOffset > 0 && leftLayout != null && getDirectionMode(DIRECTION_LEFT) == MODE_COVER;
                break;
            case DIRECTION_TOP:
                bool = topOffset > 0 && topLayout != null && getDirectionMode(DIRECTION_TOP) == MODE_COVER;
                break;
            case DIRECTION_RIGHT:
                bool = rightOffset > 0 && rightLayout != null && getDirectionMode(DIRECTION_RIGHT) == MODE_COVER;
                break;
            case DIRECTION_BOTTOM:
                bool = bottomOffset > 0 && bottomLayout != null && getDirectionMode(DIRECTION_BOTTOM) == MODE_COVER;
                break;
        }
        return bool;
    }

    private void checkMask() {
        if (mask != MASK_NONE) {
            if (maskView == null) {
                View view = null;
                try {
                    view = new View(getContext());
                    view.setAlpha(0f);
                    view.setVisibility(GONE);
                    view.setBackgroundColor(maskColor);
                    addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (view != null) maskView = view;
            }
        } else {
            if (maskView != null) {
                removeView(maskView);
                maskView = null;
                mainLayout.setMask(null);
            }
        }
    }

    private void checkTouch() {
        if (isFinish) {
            checkMask();
            toggleLayout();
            isTouch = mainLayout != null && (leftLayout != null || topLayout != null || rightLayout != null || bottomLayout != null);
            if (isTouch) {
                mainLayout.setFront();
                if (maskView != null) {
                    mainLayout.setMask(maskView);
                    maskView.setLayoutParams(mainLayout.view.getLayoutParams());
                }
                frontOffset();
            }
        }
    }

    private boolean checkIntercept(MotionEvent ev) {
        if (isIntercept) return false;
        if (AnimThread.isThread(animThread)) return true;
        if (!isShow && dragRange == -1) {
            setParentIntercept(0, false);
            return false;
        }
        final int getX = (int) ev.getX();
        final int getY = (int) ev.getY();
        final int shiftX = getX - downX;
        final int shiftY = getY - downY;
        if (dragSlop > 0 && Math.abs(shiftX) < dragSlop && Math.abs(shiftY) < dragSlop){
            return false;
        }
        final ViewUtils viewUtils = getIntercept(shiftX, shiftY);
        if (isShow && (mainOpen == MAIN_OPEN_CLICK || mainOpen == MAIN_OPEN_INTERCEPT)) {
            final ViewUtils showUtils = getViewUtils(inDirection);
            if (showUtils != null && viewUtils == null) {
                if (maskView != null) {
                    requestIntercept();
                    return false;
                }else if (!showUtils.isActionDown(downX, downY, getLocation())) {
                    return true;
                }
            }
        }
        if (!isIntercept && !AnimThread.isThread(animThread) && viewUtils != null) {
            downX = getX;
            downY = getY;
            isIntercept = true;
            isEmploy = true;
            inDirection = viewUtils.type;
            toggleLayout();
            frontLayout(viewUtils);
            if (onDrawerState != null) onDrawerState.onStart(inDirection);
            if (onDrawerChange != null) onDrawerChange.onChange(this, STATE_START, 0);
            requestIntercept();
        }
        return false;
    }


    private void checkRange(ViewUtils viewUtils, boolean isCover) {
        if (shrinkRange == 0) {
            arriveRange = false;
        } else {
            final boolean isLeft = inDirection == DIRECTION_LEFT;
            final boolean isTop = inDirection == DIRECTION_TOP;
            final boolean isRight = inDirection == DIRECTION_RIGHT;
            final boolean isBottom = inDirection == DIRECTION_BOTTOM;
            int viewSize = isLeft || isRight ? viewUtils.width : viewUtils.height;
            int mainSize = Math.abs(isLeft || isRight ? mainLayout.left : mainLayout.top);
            int offSize = getOffset();
            if (isCover) {
                if (isLeft) mainSize = viewUtils.width + viewUtils.left;
                if (isTop) mainSize = viewUtils.height + viewUtils.top;
                if (isRight) mainSize = mainLayout.width - viewUtils.left;
                if (isBottom) mainSize = mainLayout.height - viewUtils.top;
                mainSize -= offSize;
                viewSize -= offSize;
            }
            if (isShow) mainSize = viewSize - mainSize;
            arriveRange = mainSize >= Math.round(viewSize / shrinkRange);
            if (turnCancel > 0) {
                final boolean isReverse = isShow ? (isRight || isBottom) : (isLeft || isTop);
                if ((isReverse && moveSize <= lastSize) || (!isReverse && moveSize >= lastSize)) {
                    turnSize += isReverse ? lastSize - moveSize : moveSize - lastSize;
                    if (turnSize > turnCancel) {
                        arriveRange = false;
                    }
                } else {
                    turnSize = 0;
                }
                lastSize = moveSize;
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setClickable(true);
        if (getChildCount() > 1) {
            View view;
            if (mainScrollId != -1) {
                if ((view = findViewById(mainScrollId)) != null) mainScroll = new ViewUtils(view, DIRECTION_MAIN);
            }
            if (leftScrollId != -1) {
                if ((view = findViewById(leftScrollId)) != null) leftScroll = new ViewUtils(view, DIRECTION_LEFT);
            }
            if (topScrollId != -1) {
                if ((view = findViewById(topScrollId)) != null) topScroll = new ViewUtils(view, DIRECTION_TOP);
            }
            if (rightScrollId != -1) {
                if ((view = findViewById(rightScrollId)) != null) rightScroll = new ViewUtils(view, DIRECTION_RIGHT);
            }
            if (bottomScrollId != -1) {
                if ((view = findViewById(bottomScrollId)) != null) bottomScroll = new ViewUtils(view, DIRECTION_BOTTOM);
            }
            if (mainId != -1) {
                if ((view = findViewById(mainId)) != null) mainLayout = new ViewUtils(view, DIRECTION_MAIN);
            }
            if (leftId != -1) {
                if ((view = findViewById(leftId)) != null) leftLayout = new ViewUtils(view, DIRECTION_LEFT);
            }
            if (topId != -1) {
                if ((view = findViewById(topId)) != null) topLayout = new ViewUtils(view, DIRECTION_TOP);
            }
            if (rightId != -1) {
                if ((view = findViewById(rightId)) != null) rightLayout = new ViewUtils(view, DIRECTION_RIGHT);
            }
            if (bottomId != -1) {
                if ((view = findViewById(bottomId)) != null) bottomLayout = new ViewUtils(view, DIRECTION_BOTTOM);
            }
            if (mainLayout == null) {
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    if (getDirection(child) == DIRECTION_ORDER && child != maskView) {
                        mainLayout = new ViewUtils(child, DIRECTION_MAIN);
                        break;
                    }
                }
            }
            isFinish = true;
            checkTouch();
        }
    }

    @Override
    public boolean canScrollVertically(int direction) {
        if (direction == -1) {
            if (topLayout == null) return false;
            return !(isShow && inDirection != DIRECTION_BOTTOM);
        } else if (direction == 1) {
            if (bottomLayout == null) return false;
            return !(isShow && inDirection != DIRECTION_TOP);
        } else {
            return super.canScrollVertically(direction);
        }
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        if (direction == -1) {
            if (leftLayout == null) return false;
            return !(isShow && inDirection != DIRECTION_RIGHT);
        } else if (direction == 1) {
            if (rightLayout == null) return false;
            return !(isShow && inDirection != DIRECTION_LEFT);
        } else {
            return super.canScrollHorizontally(direction);
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isEmploy || !isTouch || (!dragClose && isShow) || (parentIntercept == 1 && !isShow) || parentIntercept == 2 || allIntercept) return super.onInterceptTouchEvent(ev);
        final int getX = (int) ev.getX();
        final int getY = (int) ev.getY();
        final int getMs = (int) ev.getEventTime();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = getX;
                downY = getY;
                downMs = getMs;
                getPointerId = ev.getPointerId(0);
                cacheDrawer(true);
                setParentIntercept(1, true);
                if (isShow && mainOpen == MAIN_OPEN_INTERCEPT) {
                    if (maskView != null) {
                        requestIntercept();
                        setParentIntercept(2, true);
                    } else {
                        final ViewUtils showUtils = getViewUtils(inDirection);
                        if (showUtils != null) {
                            if (!showUtils.isActionDown(downX, downY, getLocation())) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (checkIntercept(ev)) {
                    requestIntercept();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                final int getMoveX = Math.abs(getX - downX);
                final int getMoveY = Math.abs(getY - downY);
                if (!isIntercept && ((getMoveX == 0 && getMoveY == 0) || (getMs - downMs) < ViewConfiguration.getLongPressTimeout()) && (dragSlop == 0 || (getMoveX < dragSlop && getMoveY < dragSlop))){
                    boolean isClose = false;
                    if (isShow && maskClose && maskView != null) {
                        final ViewUtils showUtils = getViewUtils(inDirection);
                        if (showUtils != null) {
                            if (!showUtils.isActionDown(downX, downY, getLocation())) {
                                closeDrawer();
                                isClose = true;
                            }
                        }
                    }
                    if (!isClose) {
                        performClick();
                    }
                }
                isIntercept = false;
                isEmploy = false;
                getPointerId = INVALID_POINTER;
                getLastMoveX = 0;
                getLastMoveY = 0;
                setParentIntercept(0, true);
                break;
        }
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isTouch || (!dragClose && isShow) || (parentIntercept == 1 && !isShow) || parentIntercept == 2 || allIntercept) return super.onTouchEvent(ev);
        final int getX = (int) ev.getX();
        final int getY = (int) ev.getY();
        final int getMs = (int) ev.getEventTime();
        final int getMode = getDirectionMode(inDirection);
        final boolean isDrawer = getMode == MODE_DRAWER;
        final boolean isFixed = getMode == MODE_FIXED;
        final boolean isCover = getMode == MODE_COVER;
        final boolean isLeft = inDirection == DIRECTION_LEFT;
        final boolean isTop = inDirection == DIRECTION_TOP;
        final boolean isRight = inDirection == DIRECTION_RIGHT;
        final boolean isBottom = inDirection == DIRECTION_BOTTOM;
        final int offNum = getOffset();
        final int getIndex = ev.getActionIndex();
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_MOVE:
                if (checkIntercept(ev)) {
                    requestIntercept();
                    return true;
                }
                if (isIntercept) {
                    final int activePointerIndex = ev.findPointerIndex(getPointerId);
                    if (activePointerIndex == -1) break;
                    final ViewUtils viewUtils = getViewUtils(inDirection);
                    if (viewUtils != null) {
                        final int moveX = (int) ev.getX(activePointerIndex);
                        final int moveY = (int) ev.getY(activePointerIndex);
                        final int mainSize = isLeft || isRight ? mainLayout.width : mainLayout.height;
                        final int viewSize = isLeft || isRight ? viewUtils.width : viewUtils.height;
                        final int newSize = (maxDragSize > 0 && !isShow ? maxDragSize : viewSize) - offNum;
                        final float getDamping = dragDamping > 0 && dragDamping < 100 ? (float)dragDamping / 10 : 1;
                        getLastShiftX = moveX - downX + getLastMoveX;
                        getLastShiftY = moveY - downY + getLastMoveY;
                        moveSize = isLeft || isRight ? getLastShiftX : getLastShiftY;
                        moveSize *= getDamping;
                        if ((((isLeft || isTop) && ((isShow && moveSize > 0) || (!isShow && moveSize < 0)))) || (((isRight || isBottom) && ((isShow && moveSize < 0) || (!isShow && moveSize > 0))))) moveSize = 0;
                        if (Math.abs(moveSize) >= newSize) {
                            moveSize = (isShow && (isLeft || isTop)) || (!isShow && (isRight || isBottom)) ? -newSize : newSize;
                            downX += getLastShiftX - moveSize / getDamping;
                            downY += getLastShiftY - moveSize / getDamping;
                        }
                        if (isShow) {
                            if (isLeft || isRight) {
                                if (isDrawer || isFixed) mainLayout.setRight(moveSize + mainSize + (isLeft ? viewSize : -viewSize));
                                if (isDrawer || isCover) viewUtils.setRight(moveSize + (isLeft ? viewSize : mainSize));
                            } else if (isTop || isBottom) {
                                if (isDrawer || isFixed) mainLayout.setBottom(moveSize + mainSize + (isTop ? viewSize : -viewSize));
                                if (isDrawer || isCover) viewUtils.setBottom(moveSize + (isTop ? viewSize : mainSize));
                            }
                        } else {
                            if (isLeft || isRight) {
                                if (isDrawer || isFixed) mainLayout.setLeft(moveSize);
                                if (isDrawer || isCover) viewUtils.setLeft(moveSize + (isLeft ? -viewSize + offNum : mainSize - offNum));
                            } else if (isTop || isBottom) {
                                if (isDrawer || isFixed) mainLayout.setTop(moveSize);
                                if (isDrawer || isCover) viewUtils.setTop(moveSize + (isTop ? -viewSize + offNum : mainSize - offNum));
                            }
                        }
                        offsetMove(getMode);
                        checkRange(viewUtils, isCover);
                        final float progress = (float) Math.abs(moveSize) / (viewSize - offNum);
                        if (lastProgress != progress) {
                            onMove(isShow ? (1 - progress) : progress);
                            lastProgress = progress;
                        }
                        if (onDrawerChange != null) {
                            if (arriveRange) {
                                if (!isArriveCall) {
                                    onDrawerChange.onChange(this, STATE_DRAG_INTO, 0);
                                    isArriveCall = true;
                                }
                            } else {
                                if (isArriveCall) {
                                    onDrawerChange.onChange(this, STATE_DRAG_OUT, 0);
                                    isArriveCall = false;
                                }
                            }
                        }
                        if (mask == MASK_GRADUAL) {
                            progressMask(isShow ? (1 - progress) : progress);
                        }
                        requestIntercept();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isIntercept) {
                    final ViewUtils viewUtils = getViewUtils(inDirection);
                    if (viewUtils != null) {
                        checkRange(viewUtils, isCover);
                        if (arriveRange && shrinkRange > 0) {
                            isShow = !isShow;
                            animRecovery(isShow, true, true);
                            if (isShow) {
                                onOpen();
                            } else {
                                onClose();
                            }
                            if (mask == MASK_GENERAL) {
                                progressMask(isShow ? 1f : 0f);
                            }
                        } else {
                            animRecovery(isShow, true, true);
                            if (onDrawerState != null) onDrawerState.onCancel(inDirection);
                            if (onDrawerChange != null) onDrawerChange.onChange(this, STATE_CANCEL, 0);
                        }
                    }
                    isIntercept = false;
                    getPointerId = INVALID_POINTER;
                    getLastMoveX = 0;
                    getLastMoveY = 0;
                    setParentIntercept(0, true);
                } else {
                    final int getMoveX = Math.abs(getX - downX);
                    final int getMoveY = Math.abs(getY - downY);
                    if (((getMoveX == 0 && getMoveY == 0) || (getMs - downMs) < ViewConfiguration.getLongPressTimeout()) && (dragSlop == 0 || (getMoveX < dragSlop && getMoveY < dragSlop))) performClick();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                downX = (int) ev.getX(getIndex);
                downY = (int) ev.getY(getIndex);
                getPointerId = ev.getPointerId(getIndex);
                getLastMoveX = getLastShiftX;
                getLastMoveY = getLastShiftY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                final int pointerId = ev.getPointerId(getIndex);
                if (pointerId == getPointerId) {
                    final int newPointerIndex = getIndex == 0 ? 1 : 0;
                    downX = (int) ev.getX(newPointerIndex);
                    downY = (int) ev.getY(newPointerIndex);
                    getPointerId = ev.getPointerId(newPointerIndex);
                    getLastMoveX = getLastShiftX;
                    getLastMoveY = getLastShiftY;
                }
                break;
        }
        if (!isIntercept) super.onTouchEvent(ev);
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        cacheDrawer(false);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int setWidth = MeasureSpec.getSize(widthMeasureSpec);
        int setHeight = 0;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.getVisibility() != GONE) {
                view.setClickable(true);
                measureChild(view, widthMeasureSpec, heightMeasureSpec);
                setHeight = Math.max(setHeight, view.getMeasuredHeight());
            }
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            setHeight = MeasureSpec.getSize(heightMeasureSpec);
        }
        setMeasuredDimension(setWidth, setHeight);
        updateSize(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        if(isInit && autoLayout) {
            if(isShow) {
                closeDrawer(inDirection, false, false);
                open = inDirection;
            }
            isLayout = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.getVisibility() != GONE) {
                if ((view.getLeft() == 0 && view.getTop() == 0 && view.getRight() == 0 && view.getBottom() == 0) || reLayout || isLayout) {
                    int setLeft = 0;
                    int setTop = 0;
                    int setRight = view.getMeasuredWidth();
                    int setBottom = view.getMeasuredHeight();
                    if (isTouch) {
                        int getDirection = getDirection(view);
                        int getMode = getDirectionMode(getDirection);
                        int offNum = getOffset(getDirection);
                        if (getMode == MODE_DRAWER || getMode == MODE_COVER) {
                            if (getDirection == DIRECTION_LEFT) {
                                setLeft = -setRight + offNum;
                                setRight = offNum;
                            } else if (getDirection == DIRECTION_RIGHT) {
                                setLeft = mainLayout.width - offNum;
                                setRight += setLeft;
                            } else if (getDirection == DIRECTION_TOP) {
                                setTop = -setBottom + offNum;
                                setBottom = offNum;
                            } else if (getDirection == DIRECTION_BOTTOM) {
                                setTop = mainLayout.height - offNum;
                                setBottom += setTop;
                            }
                        } else {
                            if (getDirection == DIRECTION_RIGHT) {
                                setLeft = getMeasuredWidth() - setRight;
                                setRight = getMeasuredWidth();
                            } else if (getDirection == DIRECTION_BOTTOM) {
                                setTop = getMeasuredHeight() - setBottom;
                                setBottom = getMeasuredHeight();
                            }
                        }
                    }
                    view.layout(setLeft, setTop, setRight, setBottom);
                } else {
                    view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                }
            }
        }
        updateSize(true);
        if (!isInit) {
            isInit = true;
        }
        if (open > 0) {
            openDrawer(open, false, false);
            open = 0;
        }
        if (reLayout) {
            reLayout = false;
        }
        if (parentId != -1) {
            setParentDrawer(parentId);
            parentId = -1;
        }
        if (changed && isLayout) {
            mainLayout.upMask();
            isLayout = false;
        }
    }

}
