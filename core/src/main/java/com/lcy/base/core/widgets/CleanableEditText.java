package com.lcy.base.core.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import androidx.appcompat.widget.AppCompatEditText;

import com.lcy.base.core.R;

/**
 * 带删除按钮的EditText
 *
 * @author lcy
 */
public class CleanableEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
    private Drawable mRightDrawable;
    private Drawable mLeftDrawable;
    private FocusChangeListenerImpl mFocusChangeListener;
    private TextWatcherImpl mTextChangedListener;

    //当前模式
    private Mode mode = Mode.CLEAR;

    public enum Mode {
        CLEAR(0), PASSWORD(1);
        private final int value;

        Mode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Mode valueOf(int value) {
            switch (value) {
                case 0:
                    return CLEAR;//清除输入
                case 1:
                    return PASSWORD;//显示密码
            }
            return CLEAR;
        }
    }

    public void setFocusChangeListener(FocusChangeListenerImpl focusChangeListener) {
        this.mFocusChangeListener = focusChangeListener;
    }

    public void removeFocusChangeListener(FocusChangeListenerImpl focusChangeListener) {
        if (focusChangeListener != null && mFocusChangeListener == focusChangeListener) {
            this.mFocusChangeListener = null;
        }

    }

    public void removeTextWatcherImpl(TextWatcherImpl textChangedListener) {
        if (textChangedListener != null && this.mTextChangedListener == textChangedListener) {
            this.mTextChangedListener = null;
        }
    }

    public void setTextChangedListener(TextWatcherImpl textChangedListener) {
        this.mTextChangedListener = textChangedListener;
    }

    public CleanableEditText(Context context) {
        this(context, null);
    }

    public CleanableEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public CleanableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CleanableEditText);
            int modeValue = a.getInt(R.styleable.CleanableEditText_mode, Mode.CLEAR.getValue());
            mode = Mode.valueOf(modeValue);
            a.recycle();
        }
        init();
    }

    private void init() {
        Drawable[] drawables = this.getCompoundDrawables();
        mRightDrawable = drawables[2];
        mLeftDrawable = drawables[0];
        if (mRightDrawable == null) {
            setDrawableRight(getResources().getDrawable(R.drawable.base_core_ic_common_clear_text));
        }
        // 设置焦点变化的监听
        this.setOnFocusChangeListener(this);
        // 初始化时让右边clean图标不可见
        setClearDrawableVisible(false);
    }

    /**
     * 设置文本框右边显示的图标
     *
     * @param drawable
     */
    public void setDrawableRight(Drawable drawable) {
        mRightDrawable = drawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        setCompoundDrawables(mLeftDrawable, null, mRightDrawable, null);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight()))
                        && (event.getX() < (getWidth() - getPaddingRight()));
                if (isClean) {
                    if (mode == Mode.CLEAR) {
                        setText("");
                    } else {
                        // 这一步必须要做,否则不会显示.
                        if (getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
                            mRightDrawable = getResources().getDrawable(R.drawable.base_core_ic_common_password_unable);
                            setTransformationMethod(PasswordTransformationMethod.getInstance());
                        } else {
                            setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            mRightDrawable = getResources().getDrawable(R.drawable.base_core_ic_common_password_enable);
                        }
                        mRightDrawable.setBounds(0, 0, mRightDrawable.getMinimumWidth(), mRightDrawable.getMinimumHeight());
                        setCompoundDrawables(mLeftDrawable, null, mRightDrawable, null);
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    public interface FocusChangeListenerImpl {
        void onFocusChange(View v, boolean hasFocus);
    }

    public interface TextWatcherImpl {
        void afterTextChanged(Editable s);

        void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter);
    }

    // 隐藏或者显示右边clean的图标
    protected void setClearDrawableVisible(boolean isVisible) {
        Drawable rightDrawable;
        if (isVisible) {
            rightDrawable = mRightDrawable;
        } else {
            rightDrawable = null;
        }
        // 使用代码设置该控件left, top, right, and bottom处的图标
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], rightDrawable,
                getCompoundDrawables()[3]);
    }

    // 显示一个动画,以提示用户输入
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    // CycleTimes动画重复的次数
    public Animation shakeAnimation(int CycleTimes) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (mFocusChangeListener != null) {
            mFocusChangeListener.onFocusChange(view, hasFocus);
        }
        if (hasFocus && getText() != null) {
            boolean isVisible = getText().toString().length() >= 1;
            // 设置EditText文字变化的监听，当活的焦点时才监听
            CleanableEditText.this.addTextChangedListener(this);
            setClearDrawableVisible(isVisible);
        } else {
            setClearDrawableVisible(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


    }

    @Override
    public void afterTextChanged(Editable editable) {
        boolean isVisible = getText() != null && getText().toString().length() >= 1;
        setClearDrawableVisible(isVisible);
        if (mTextChangedListener != null) {
            mTextChangedListener.afterTextChanged(editable);
        }
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (mTextChangedListener != null) {
            mTextChangedListener.onTextChanged(text, start, lengthBefore, lengthAfter);
        }
    }

    public void onCustomAfterTextChanged() {
        boolean isVisible = getText() != null && getText().toString().length() >= 1;
        setClearDrawableVisible(isVisible);
    }
}
