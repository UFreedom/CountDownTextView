package com.ufreedom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;

import java.util.Formatter;
import java.util.IllegalFormatException;
import java.util.Locale;

/**
 * Custom TextView that implements a simple CountDown.
 * 
 * 
 * 
 * Author UFreedom
 */
public class CountDownTextView extends TextView {

    private static final String TAG = "CountDownTextView";

    public static final int TIME_SHOW_D_H_M_S = 10;
    public static final int TIME_SHOW_H_M_S = 20;
    public static final int TIME_SHOW_M_S = 30;
    public static final int TIME_SHOW_S = 40;
    
    private static final String TIME_FORMAT_D_H_M_S = "%1$02d:%2$02d:%3$02d:%4$02d";
    private static final String TIME_FORMAT_H_M_S = "%1$02d:%2$02d:%3$02d";
    private static final String TIME_FORMAT_M_S = "%1$02d:%2$02d";
    private static final String TIME_FORMAT_S = "%1$02d";
    
    private long scheduledTime;
    private boolean isAutoShowText;
    private CountDownCallback countDownCallback;
    private CountDownHelper  mCountDownHelper;
    private boolean mVisible;
    private boolean mStarted;
    private boolean mRunning;
    private boolean mLogged;
    private String mFormat;
    private String mTimeFormat; //DD:HH:MM:SS
    private Formatter mFormatter;
    private Locale mFormatterLocale;
    private Object[] mFormatterArgs = new Object[1];
    private StringBuilder mFormatBuilder;
    private int mTimeFlag;


    public CountDownTextView(Context context) {
        super(context);
        init();
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    private void init(){
        setTimeFormat(TIME_SHOW_H_M_S);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateRunning();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVisible = false;
        updateRunning();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        mVisible = visibility == VISIBLE;
        updateRunning();
    }


    private void updateRunning() {
        boolean running = mVisible && mStarted;
        if (running != mRunning) {
            if (running) {
                mCountDownHelper.start();
            } else {
                mCountDownHelper.cancel();
            }
            mRunning = running;
        }
    }


    /**
     * Start the countdown
     */
    public void start() {
        startCountDown();
        mStarted = true;
        updateRunning();

    }

    /**
     * Cancel the countdown
     */
    public void cancel() {
        mStarted = false;
        updateRunning();
    }
    
    private void startCountDown(){

        mCountDownHelper  = new CountDownHelper(scheduledTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isAutoShowText) {
                  //  updateText(millisUntilFinished);
                    update(millisUntilFinished);
                }
                if (countDownCallback != null) {
                    countDownCallback.onTick(CountDownTextView.this,millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                if (countDownCallback != null) {
                    countDownCallback.onFinish(CountDownTextView.this);
                }
            }
        };
        mCountDownHelper.start();
        
    }

    /**
     * is auto show time
     * @param isAutoShowText
     */
    public void setAutoShowText(boolean isAutoShowText) {
        this.isAutoShowText = isAutoShowText;
    }


    
    
    public interface CountDownCallback {

        /**
         * Callback fired on regular interval.
         * @param millisUntilFinished The amount of time until finished.
         */
        void onTick(CountDownTextView countDownTextView,long millisUntilFinished);

        /**
         * Callback fired when the time is up.
         */
        void onFinish(CountDownTextView countDownTextView);
        
    }

    /**
     * Sets the format string used for display.  The CountDownTextView will display
     * this string, with the first "%s" replaced by the current timer value in
     * "MM:SS" or "HH:MM:SS" form which dependents on the time format {@link #setTimeFormat(int)}.
     *
     * If the format string is null, or if you never call setFormat(), the
     * CountDownTextView will simply display the timer value in "MM:SS" or "HH:MM:SS"
     * form.
     *
     * @param format the format string.
     */
    public void setFormat(String format){
        mFormat = format;
        if (format != null && mFormatBuilder == null) {
            mFormatBuilder = new StringBuilder(format.length() * 2);
        }
    }

    /**
     * Returns the current format string as set through {@link #setFormat}.
     */
    public String getFormat() {
        return mFormat;
    }


    
    
    private String getFormatTime(long now){
        Object [] args ;
        long day = ElapsedTimeUtil.MILLISECONDS.toDays(now);
        long hour = ElapsedTimeUtil.MILLISECONDS.toHours(now);
        long minute = ElapsedTimeUtil.MILLISECONDS.toMinutes(now);
        long seconds = ElapsedTimeUtil.MILLISECONDS.toSeconds(now);

        switch (mTimeFlag) {
            case TIME_SHOW_D_H_M_S:
                mTimeFormat = TIME_FORMAT_D_H_M_S;
                args = new Object[]{day,hour,minute,seconds};
                break;
            case TIME_SHOW_H_M_S:
                mTimeFormat = TIME_FORMAT_H_M_S;
                args = new Object[]{hour,minute,seconds};
                break;

            case TIME_SHOW_M_S:
                mTimeFormat = TIME_FORMAT_M_S;
                args = new Object[]{minute,seconds};
                break;

            case TIME_SHOW_S:
                mTimeFormat = TIME_FORMAT_S;
                args = new Object[]{seconds};
                break;
            default:
                mTimeFormat = TIME_FORMAT_H_M_S;
                args = new Object[]{hour,minute,seconds};
                break;
        }
        
      return String.format(mTimeFormat,args).toString();        
    }
    
    private synchronized void update(long now){
        long day = ElapsedTimeUtil.MILLISECONDS.toDays(now);
        long hour = ElapsedTimeUtil.MILLISECONDS.toHours(now);
        long minute = ElapsedTimeUtil.MILLISECONDS.toMinutes(now);
        long seconds = ElapsedTimeUtil.MILLISECONDS.toSeconds(now);

        String text;
        
        switch (mTimeFlag) {
            case TIME_SHOW_D_H_M_S:
                text = mFormatter.format(TIME_FORMAT_D_H_M_S,day,hour,minute,seconds).toString();
                break;
            case TIME_SHOW_H_M_S:
                text = mFormatter.format(TIME_FORMAT_H_M_S,hour,minute,seconds).toString();

                break;

            case TIME_SHOW_M_S:
                text = mFormatter.format(TIME_FORMAT_M_S,minute,seconds).toString();
                break;

            case TIME_SHOW_S:
                text = mFormatter.format(TIME_FORMAT_S,seconds).toString();
                break;
            default:
                text = mFormatter.format(TIME_FORMAT_H_M_S,seconds).toString();
                break;
        }
        
        if (mFormat != null){
            mFormatterArgs[0] = text;
            text =  mFormatter.format(mFormat,mFormatterArgs).toString();
        }
        
        setText(text);
    }
    
    private synchronized void updateText(long now) {
        String text = getFormatTime(now);

        if (mFormat != null) {
            Locale loc = Locale.getDefault();
            if (mFormatter == null || !loc.equals(mFormatterLocale)) {
                mFormatterLocale = loc;
                mFormatter = new Formatter(mFormatBuilder, loc);
            }
            mFormatBuilder.setLength(0);
            mFormatterArgs[0] = text;
            try {
                mFormatter.format(mFormat, mFormatterArgs);
                text = mFormatBuilder.toString();
            } catch (IllegalFormatException ex) {
                if (!mLogged) {
                    Log.w(TAG, "Illegal format string: " + mFormat);
                    mLogged = true;
                }
            }
        }
        setText(text);
    }

 
    
    public void setTimeInFuture(long millisInFuture){
        scheduledTime = millisInFuture;
    }
    
    
    /**
     * CountDown callback
     * @param callback
     */
    public void addCountDownCallback(CountDownCallback callback) {
        countDownCallback = callback;
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(CountDownTextView.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(CountDownTextView.class.getName());
    }

    /**
     * Sets the format string used for time display.The default display format is "HH:MM:SS"
     * 
     */
    public void setTimeFormat(/*String mTimeFormat,*/int timeFlag) {
      //  this.mTimeFormat = mTimeFormat;
        mTimeFlag = timeFlag;
        initFormatter();        
    }
    
    private void initFormatter(){
        Locale loc = Locale.getDefault();
        if (mFormatter == null || !loc.equals(mFormatterLocale)) {
            mFormatterLocale = loc;
            mFormatter = new Formatter(loc);
        }
    }

    /**
     * Returns the current time format string as set through {@link #setTimeFormat}.
     */
    private String getTimeFormat() {
        return mTimeFormat;
    }

}
