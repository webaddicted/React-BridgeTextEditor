package com.chinalwb.are;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chinalwb.are.android.inner.Html;
import com.chinalwb.are.render.AreImageGetter;
import com.chinalwb.are.render.AreTagHandler;
import com.chinalwb.are.strategies.AtStrategy;
import com.chinalwb.are.strategies.ImageStrategy;
import com.chinalwb.are.strategies.VideoStrategy;
import com.chinalwb.are.styles.toolbar.ARE_Toolbar;

/**
 * All Rights Reserved.
 *
 * @author Wenbin Liu
 */
public class AREditorCustom extends RelativeLayout {
    String TAG = AREditorCustom.class.getSimpleName();

    public Activity mActivityContext;
    private ARE_Toolbar.DoneListener doneListener;
    private onEditChangeListener onEditChangeListener;
    private LinearLayout linearBottom;
    private View viewCustom;

    public void doneListener(ARE_Toolbar.DoneListener doneListener) {
        this.doneListener = doneListener;
        this.mToolbar.setDoneListener(doneListener);
    }

    public void onTextChangeListener(onEditChangeListener onEditChangeListener) {
        this.onEditChangeListener = onEditChangeListener;
    }

    /**
     * The toolbar alignment.
     */
    public enum ToolbarAlignment {
        /**
         * (Default) Below input area.
         */
        BOTTOM,

        /**
         * Above the input area.
         */
        TOP,
    }

    /**
     * The mode of the editor
     */
    public enum ExpandMode {
        /**
         * Take up all height available
         */
        FULL,

        /**
         * Take up min height
         */
        MIN,
    }

    /*
     * --------------------------------------------
     * Instance Fields Area
     * --------------------------------------------
     */

    /**
     * Context.
     */
    private Activity mContext;

    /**
     * The toolbar.
     */
    public ARE_Toolbar mToolbar;

    /**
     * The scroll view out of AREditText.
     */
//    private ScrollView mAreScrollView;

    /**
     * The are editor.
     */
    private AREditText mAre;

    /**
     * The alignment of toolbar.
     */
    private ToolbarAlignment mToolbarAlignment = ToolbarAlignment.BOTTOM;

    /**
     * The expand mode of ARE.
     */
    private ExpandMode mExpandMode = ExpandMode.FULL;

    /**
     * Whether to hide the toolbar.
     */
    private boolean mHideToolbar = false;

    /*
     * --------------------------------------------
     * Constructors Area
     * --------------------------------------------
     */

    /**
     * Constructor.
     *
     * @param context
     */
    public AREditorCustom(Activity context) {
        this(context, null);
    }

    /**
     * Constructor.
     *
     * @param context
     * @param attrs
     */
    public AREditorCustom(Activity context, AttributeSet attrs) {
        this(context, attrs, 0);
//        this.mToolbar.setDoneListener(mDoneListener);
    }

    /**
     * Constructor.
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public AREditorCustom(Activity context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
//        this.mToolbar.setDoneListener(mDoneListener);
        this.init(attrs);
    }

    /*
     * --------------------------------------------
     *  Business Methods Area
     * --------------------------------------------
     */

    /**
     * Initialization.
     */
    private void init(AttributeSet attrs) {

        initAttrs(attrs);
        initGlobal();
//        doLayout();

    } // # End of init()

    private void initGlobal() {
        if (viewCustom!=null)this.removeView(viewCustom);
        viewCustom = getLayout(R.layout.edt_text_editor);
        mAre = viewCustom.findViewById(R.id.are_editor);
        mAre.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_FLAG_NO_FULLSCREEN);
//        mAre.requestFocus();
        showKeyboard(mAre);
        mAre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (onEditChangeListener != null)
                    onEditChangeListener.onEditTextChange(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

//        dsfgdfgdfgfdg

        linearBottom = (LinearLayout) viewCustom.findViewById(R.id.linear_bottom);
        this.mToolbar = new ARE_Toolbar(mContext);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        parms.gravity = Gravity.CENTER;
        mToolbar.setLayoutParams(parms);
//        mAre.setText("kjlhkjhkhkhkhskdsd\nsdfsdfdsf\ndsfgshdsdj\nsdjhgsdjhgdshj\nhgfhsdf\nhjugdjkdsjk\nfgghfgh\nghdjkds");
        this.mToolbar.setEditText(mAre);
        linearBottom.addView(mToolbar);

        this.addView(viewCustom);
//        this.mToolbar.setId(R.id.are_toolbar);

//        this.mAreScrollView = new ScrollView(mContext);
//        this.mAreScrollView.setId(R.id.are_scrollview);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.are);

        // Toolbar alignment
        int toolbarAlignmentInt = ta.getInt(R.styleable.are_toolbarAlignment, ToolbarAlignment.BOTTOM.ordinal());
        this.mToolbarAlignment = ToolbarAlignment.values()[toolbarAlignmentInt];

        // Expand mode
        int expandMode = ta.getInt(R.styleable.are_expandMode, ExpandMode.FULL.ordinal());
        this.mExpandMode = ExpandMode.values()[expandMode];

        // Sets hide toolbar
        this.mHideToolbar = ta.getBoolean(R.styleable.are_hideToolbar, mHideToolbar);

        ta.recycle();
    }

    private void addToolbar(boolean isBelow, int belowId, boolean isFull) {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        if (Util.isTablet(mContext)) {
            layoutParams.setMargins(0, 0, 0, -25);
        } else layoutParams.setMargins(0, 0, 0, -35);

        if (mExpandMode == ExpandMode.FULL) {
            int ruleVerb = mToolbarAlignment == ToolbarAlignment.BOTTOM ? RelativeLayout.ALIGN_PARENT_BOTTOM : RelativeLayout.ALIGN_PARENT_TOP;
            layoutParams.addRule(ruleVerb, this.getId());
        } else if (isBelow) {
            layoutParams.addRule(BELOW, belowId);
        }

        mToolbar.setLayoutParams(layoutParams);

        this.addView(this.mToolbar);

//        if (mHideToolbar) {
//        } else {
//            mToolbar.setVisibility(View.VISIBLE);
//        }
    }

//    private void addEditText(boolean isBelow, int belowId) {
//        int height = mExpandMode == ExpandMode.FULL ? LayoutParams.MATCH_PARENT : LayoutParams.MATCH_PARENT;
//        int lines = mExpandMode == ExpandMode.FULL ? -1 : 3;
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        mContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int heightPixels = (displayMetrics.heightPixels) / 2;
//        int widthPixels = displayMetrics.widthPixels;
//        //LayoutParams scrollViewLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,heightPixels);
//        LayoutParams scrollViewLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        //if (isBelow) scrollViewLayoutParams.addRule(BELOW, belowId);
//        //scrollViewLayoutParams.setMargins(0,0,0,10);
//        mAreScrollView.setLayoutParams(scrollViewLayoutParams);
//        //mAreScrollView.setBackgroundColor(getResources().getColor(R.color.jits_color));
//        mAre = new AREditText(mContext);
//        //if (lines > 0) mAre.setMaxLines(lines);
//        mAre.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_FLAG_NO_FULLSCREEN);
//        //LayoutParams editTextLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,mAre.getHeight());
//
//        //LayoutParams editTextLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
//
//        //LayoutParams editTextLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,heightPixels);
//        //mAre.setLayoutParams(editTextLayoutParams);
//
////        mAre.requestFocus();
////        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
////        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
//        //mAreScrollView.addView(mAre, editTextLayoutParams);
//        mAreScrollView.addView(mAre);
////        mAre.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
////            @Override
////            public void onGlobalLayout() {
////                int heightDiff = mAreScrollView.getRootView().getHeight() - mAreScrollView.getHeight();
////                if (heightDiff > dpToPx(mActivityContext, 200)) { // if more than 200 dp, it's probably a keyboard...
////                    mToolbar.setVisibility(View.GONE);
////                } else {
////                    mToolbar.setVisibility(View.VISIBLE);
////                    touchEventListener.touchListener();
////                }
////            }
////        });
//
//        mAre.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (onEditChangeListener != null)
//                    onEditChangeListener.onEditTextChange(charSequence.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//        this.mToolbar.setEditText(mAre);
////        if (mExpandMode == ExpandMode.FULL) {
////            mAreScrollView.setBackgroundColor(Color.WHITE);
////        }
//        this.addView(mAreScrollView);
//    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

//    private void doLayout() {
//        if (this.indexOfChild(mToolbar) > -1) {
//            this.removeView(mToolbar);
//        }
//        if (this.indexOfChild(mAreScrollView) > -1) {
//            mAreScrollView.removeAllViews();
//            this.removeView(mAreScrollView);
//        }
//        if (mToolbarAlignment == ToolbarAlignment.BOTTOM) {
//            // EditText
//            // Toolbar
//            addEditText(false, -1);
//            addToolbar(true, mAreScrollView.getId(), true);
////            addToolbar(false, -1, false);
////            addEditText(true, mToolbar.getId());
//        } else {
//            // Toolbar is up, so EditText is below
//            // EditText is below
//            addToolbar(false, -1, false);
//            addEditText(true, mToolbar.getId());
//        }
//    }

    /**
     * Sets html content to EditText.
     *
     * @param html
     * @return
     */
    public void fromHtml(String html) {
        Html.sContext = mContext;
        Html.ImageGetter imageGetter = new AreImageGetter(mContext, this.mAre);
        Html.TagHandler tagHandler = new AreTagHandler();
        Spanned spanned = Html.fromHtml(html, Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH, imageGetter, tagHandler);
        AREditText.stopMonitor();
        this.mAre.getEditableText().append(spanned);
        AREditText.startMonitor();
//        log();
    }

    private void log() {
        Editable editable = this.mAre.getEditableText();
        Object[] spans = editable.getSpans(0, editable.length(), Object.class);
        for (Object span : spans) {
            int spanStart = editable.getSpanStart(span);
            int spanEnd = editable.getSpanEnd(span);
            Util.log("span == " + span + ", start == " + spanStart + ", end == " + spanEnd);
        }
    }

    /**
     *
     */
    public String getHtml() {
        if (mAre.getText().toString().trim().length() == 0) return "";
        StringBuffer html = new StringBuffer();
        html.append("<html><body>");
        appendAREditText(mAre, html);
        html.append("</body></html>");
        String htmlContent = html.toString().replaceAll(Constants.ZERO_WIDTH_SPACE_STR_ESCAPE, "");
        System.out.println(htmlContent);
        return htmlContent;
    }

    private static void appendAREditText(AREditText editText, StringBuffer html) {
        String editTextHtml = Html.toHtml(editText.getEditableText(), Html.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL);
        html.append(editTextHtml);
    }

    public AREditText getARE() {
        return this.mAre;
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mToolbar.onActivityResult(requestCode, resultCode, data);
    } // #End of onActivityResult(..)

    /* ----------------------
     * Customization part
     * ---------------------- */

//    public void setToolbarAlignment(ToolbarAlignment alignment) {
//        mToolbarAlignment = alignment;
//        doLayout();
//    }
//
//    public void setExpandMode(ExpandMode mode) {
//        mExpandMode = mode;
//        doLayout();
//    }
//
//    public void setHideToolbar(boolean hideToolbar) {
//        mHideToolbar = hideToolbar;
//        doLayout();
//    }

    public void setAtStrategy(AtStrategy atStrategy) {
        this.mAre.setAtStrategy(atStrategy);
    }

    public void setVideoStrategy(VideoStrategy videoStrategy) {
        this.mAre.setVideoStrategy(videoStrategy);
    }

    public void setImageStrategy(ImageStrategy imageStrategy) {
        this.mAre.setImageStrategy(imageStrategy);
    }

    public interface onEditChangeListener {
        void onEditTextChange(String textValue);
    }

    public View getLayout(int layout) {
        LayoutInflater layoutInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(layout, null);
    }

    protected void showKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                view.requestFocus();
                imm.showSoftInput(view, 0);
            }
        }
    }

}
