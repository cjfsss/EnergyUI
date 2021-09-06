package hos.ui.bar;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.CheckResult;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleableRes;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import hos.ui.R;
import hos.util.compat.ImageViewContext;
import hos.util.compat.ResContext;
import hos.util.compat.TextViewContext;

/**
 * <p>Title: MaterialAppBar </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/5/17 22:52
 */
public class MaterialAppBar extends MaterialToolbar {

    private final LayoutParams menuLp;

    private final List<View> menuList;
    private TextView mTitleView;
    private TextView mSubtitleView;

    private int mTitleTextAppearance;
    private ColorStateList mTitleTextColor;

    public MaterialAppBar(@NonNull Context context) {
        this(context, null);
    }

    public MaterialAppBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.toolbarStyle);
    }

    public MaterialAppBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        menuList = new ArrayList<>();
        menuLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, Gravity.RIGHT);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialAppBar, defStyleAttr, 0);

        final String navBtnGravity = a.getString(R.styleable.MaterialAppBar_navigationGravity);
        menuLp.leftMargin = (int) a.getDimension(R.styleable.MaterialAppBar_menuLeftMargin, dp2px(6));
        menuLp.rightMargin = (int) a.getDimension(R.styleable.MaterialAppBar_menuRightMargin, dp2px(12));
        final int[] styleableResIds = {R.styleable.MaterialAppBar_menu1,
                R.styleable.MaterialAppBar_menu2, R.styleable.MaterialAppBar_menu3,
                R.styleable.MaterialAppBar_menu4, R.styleable.MaterialAppBar_menu5};
        final int[] textSizeResIds = {R.styleable.MaterialAppBar_menu1TextSize,
                R.styleable.MaterialAppBar_menu2TextSize, R.styleable.MaterialAppBar_menu3TextSize,
                R.styleable.MaterialAppBar_menu4TextSize, R.styleable.MaterialAppBar_menu5TextSize};
        final int[] colorResIds = {R.styleable.MaterialAppBar_menu1Color,
                R.styleable.MaterialAppBar_menu2Color, R.styleable.MaterialAppBar_menu3Color,
                R.styleable.MaterialAppBar_menu4Color, R.styleable.MaterialAppBar_menu5Color};
        List<Integer> menuIds = getResIds(styleableResIds, a);
        List<Integer> menuSizes = getResIds(textSizeResIds, a);
        List<Integer> menuColors = getResIds(colorResIds, a);

        a.recycle();

        // 1.set nav button
        if (!TextUtils.equals(navBtnGravity, "0")) {
            ImageButton navButton = getNavButton();
            if (navButton != null) {
                LayoutParams lp = (LayoutParams) navButton.getLayoutParams();
                lp.gravity = Gravity.CENTER_VERTICAL;
                navButton.setLayoutParams(lp);
            }
        }
        // 2.set menu views
        for (int i = 0; i < menuIds.size(); i++) {
            int menuId = menuIds.get(i);
            if (menuId == 0) {
                continue;
            }
            int menuSize = menuSizes.get(i);
            int menuColor = menuColors.get(i);
            final View menuV = initMenuVIew(context, menuId, menuSize, menuColor);
            menuList.add(menuV);
            addView(menuV, menuLp);
        }
    }

    public MaterialAppBar addMenu(View v) {
        addView(v, menuLp);
        return this;
    }

    /**
     * 通过资源的id添加menu
     *
     * @param menuId [layoutResId,DrawableResId,StringResId]
     */
    public <T extends View> T addMenu(@LayoutRes @DrawableRes @StringRes int menuId, @DimenRes int menuSize, @ColorRes int menuColor) {
        final View menuV = initMenuVIew(getContext(), menuId, menuSize, menuColor);
        addView(menuV, menuLp);
        return (T) menuV;
    }

    @NonNull
    private View initMenuVIew(Context context, int menuId, int menuSize, int menuColor) {
        final View menuV;

        final String str = getResources().getString(menuId);
        String menuSizeText = null;
        if (menuSize != 0) {
            menuSizeText = getResources().getString(menuSize);
        }
        String menuColorText = null;
        if (menuSize != 0) {
            menuColorText = getResources().getString(menuColor);
        }
        if (str.startsWith("res/drawable") || str.startsWith("res/mipmap")) {
            // 是图片
            menuV = new AppCompatImageButton(context, null, R.attr.toolbarNavigationButtonStyle);
            ((AppCompatImageButton) menuV).setImageResource(menuId);
            if (menuColorText != null) {
                ImageViewContext.setImageTintListRes((AppCompatImageButton) menuV, menuColor);
            }
        } else if (str.startsWith("res/layout")) {
            // 是view的布局文件
            menuV = LayoutInflater.from(context).inflate(menuId, null);
        } else {
            // 是文本
            menuV = new AppCompatTextView(context, null, R.attr.toolbarMenuTextStyle);
            ((TextView) menuV).setSingleLine();
            ((TextView) menuV).setEllipsize(TextUtils.TruncateAt.END);
            ((TextView) menuV).setText(str);
            if (getTitleTextAppearance() != 0) {
                ((TextView) menuV).setTextAppearance(context, mTitleTextAppearance);
            }
            if (menuSizeText != null) {
                TextViewContext.setTextSizeRes(menuSize, (TextView) menuV);
            }
            if (menuColorText != null) {
                ((TextView) menuV).setTextColor(ResContext.getColor(context, menuColor));
            }
            /**
             * Just for preview
             */
            if (isInEditMode()) {
                if (str.endsWith(".xml") || str.endsWith(".png")
                        || str.endsWith(".jpg")) {
                    ((TextView) menuV).setText("★");
                }
            }
        }
        return menuV;
    }


    /**
     * @return menu视图的id数组
     */
    @NonNull
    private List<Integer> getResIds(@StyleableRes int[] styleResIds, TypedArray a) {
        List<Integer> ids = new ArrayList<>();
        for (int resId : styleResIds) {
            ids.add(a.getResourceId(resId, 0));
        }
        return ids;
    }

    public void canFinishActivity() {
        final Context context = getContext();
        if (context instanceof Activity) {
            setNavigationOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) context).finish();
                }
            });
        }
    }

    /**
     * 隐藏返回按钮
     * @return
     */
    public ImageButton goneNavButton() {
        ImageButton navButton = getNavButton();
        if (navButton != null) {
            navButton.setImageDrawable(null);
            navButton.setVisibility(GONE);
        }
        return navButton;
    }

    /**
     * 设置标题到中间
     *
     * @param title    标题
     * @param isBold   是否加粗
     * @param isCenter 是否居中
     */
    public void setCenterTitle(@Nullable CharSequence title, @Nullable Boolean isBold, @Nullable Boolean isCenter) {
        if (mTitleView == null) {
            mTitleView = getTitleView();
        }
        if (mTitleView == null) {
            loadView();
        }
        if (mTitleView == null) {
            return;
        }
        mTitleView.setText(title);
        if (isBold != null && isBold) {
            // 是否加粗
            mTitleView.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        }
        if (isCenter != null && isCenter) {
            // 是否居中
            mTitleView.setGravity(Gravity.CENTER);
            LayoutParams layoutParams = (LayoutParams) mTitleView.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
            }
            layoutParams.gravity = Gravity.CENTER;
            mTitleView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 设置标题到中间
     *
     * @param isBold   是否加粗
     * @param isCenter 是否居中
     */
    public void setCenterTitle(@Nullable Boolean isBold, @Nullable Boolean isCenter) {
        setCenterTitle(getTitle(), isBold, isCenter);
    }

    /**
     * 设置标题到中间
     *
     * @param isBold 是否加粗
     */
    public void setCenterTitleBold(@Nullable Boolean isBold) {
        setCenterTitle(getTitle(), isBold, null);
    }

    /**
     * 设置标题到中间
     *
     * @param isCenter 是否居中
     */
    public void setCenterTitle(@Nullable Boolean isCenter) {
        setCenterTitle(getTitle(), null, isCenter);
    }

    private void loadView() {
        CharSequence oldTitle = getTitle();
        CharSequence oldSubtitle = getSubtitle();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                if (TextUtils.equals(((TextView) view).getText(), oldTitle)) {
                    mTitleView = (TextView) view;
                }
                if (TextUtils.equals(((TextView) view).getText(), oldSubtitle)) {
                    mSubtitleView = (TextView) view;
                }
            }
        }
    }

    public
    @CheckResult
    <T extends View> T getMenu01() {
        return (T) menuList.get(0);
    }

    public
    @CheckResult
    <T extends View> T getMenu02() {
        return (T) menuList.get(1);
    }

    public
    @CheckResult
    <T extends View> T getMenu03() {
        return (T) menuList.get(2);
    }

    public
    @CheckResult
    <T extends View> T getMenu04() {
        return (T) menuList.get(3);
    }

    public
    @CheckResult
    <T extends View> T getMenu05() {
        return (T) menuList.get(4);
    }

    /**
     * 得到标题按钮
     */
    public
    @CheckResult
    @Nullable
    TextView getTitleView() {
        if (mTitleView == null) {
            return mTitleView = (TextView) getSubView("mTitleTextView");
        }
        return mTitleView;
    }

    /**
     * 得到子标题
     */
    public
    @CheckResult
    @Nullable
    TextView getSubtitleView() {
        if (mSubtitleView != null) {
            return mSubtitleView;
        }
        return mSubtitleView = ((TextView) getSubView("mSubtitleTextView"));
    }

    /**
     * 得到左边的导航按钮
     */
    public
    @CheckResult
    @Nullable
    ImageButton getNavButton() {
        return (ImageButton) getSubView("mNavButtonView");
    }

    /**
     * 得到logo的视图
     */
    public
    @CheckResult
    @Nullable
    ImageView getLogoView() {
        return ((ImageView) getSubView("mLogoView"));
    }

    /**
     * 得到最右边的可折叠按钮视图
     */
    public
    @CheckResult
    @Nullable
    ImageButton getCollapseButton() {
        return (ImageButton) getSubView("mCollapseButtonView");
    }
    /**
     * 得到标题的样式
     */
    @SuppressWarnings("ConstantConditions")
    public
    @CheckResult
    int getTitleTextAppearance() {
        if (mTitleTextAppearance != 0) {
            return mTitleTextAppearance;
        }
        return mTitleTextAppearance = (int) get("mTitleTextAppearance");
    }

    /**
     * 得到标题的颜色
     */
    public
    @CheckResult
    @Nullable
    ColorStateList getTitleTextColor() {
        if (mTitleTextColor != null) {
            return mTitleTextColor;
        }
        return mTitleTextColor = (ColorStateList) get("mTitleTextColor");
    }

    @Nullable
    private Object get(String name) {
        Field field;
        try {
            field = Toolbar.class.getDeclaredField(name);
            field.setAccessible(true);
            Object fieldObject = field.get(this);
            field.setAccessible(false);
            return fieldObject;
        } catch (Exception e) {
            Log.e("AppBar", "getSubView: 反射错误，请尽快上报给开发者", e);
        }
        return null;
    }
    @Nullable
    private View getSubView(String name) {
        Field field;
        try {
            field = Toolbar.class.getDeclaredField(name);
            field.setAccessible(true);
            View v = (View) field.get(this);
            field.setAccessible(false);
            return v;
        } catch (Exception e) {
            Log.e("MaterialAppBar", "getSubView: 反射错误，请尽快上报给开发者", e);
        }
        return null;
    }

    public int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
