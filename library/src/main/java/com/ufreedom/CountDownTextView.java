package com.ufreedom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Author UFreedom
 */
public class CountDownTextView extends TextView {

    private static final String TAG = "CountDownTextView";


    private long scheduledTime;
    private String prefixText;
    private String finishedText;
    private boolean isShowDay;
    private boolean isAutoShowText;
    private CountDownCallback countDownCallback;
    private CountDownHelper  mCountDownHelper;

    private boolean mVisible;
    private boolean mStarted;
    private boolean mRunning;
    private int mTimeMode = CountDownHelper.TIME_RELATIVE;
    

    public CountDownTextView(Context context) {
        super(context);
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
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

        mCountDownHelper  = new CountDownHelper(scheduledTime, 1000,mTimeMode) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isAutoShowText) {
                    updateTime(millisUntilFinished);
                }
                if (countDownCallback != null) {
                    countDownCallback.onTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                setText(finishedText);
                if (countDownCallback != null) {
                    countDownCallback.onFinish();
                }
            }
        };
        mCountDownHelper.start();
        
    }

    /**
     * is auto show text
     * @param isAutoShowText
     */
    public void setAutoShowText(boolean isAutoShowText) {
        this.isAutoShowText = isAutoShowText;
    }

    /**
     * is show day
     * @param isShowDay
     */
    public void setShowDay(boolean isShowDay) {
        this.isShowDay = isShowDay;
    }

    private void updateTime(long timeInterval) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((prefixText != null ? prefixText : ""));
        if (isShowDay) {
            stringBuilder.append(ElapsedTimeUtil.MILLISECONDS.toDays(timeInterval) < 10 ? "0" : "").append(ElapsedTimeUtil.MILLISECONDS.toDays(timeInterval)).append(":");
        }
        stringBuilder.append(ElapsedTimeUtil.MILLISECONDS.toHours(timeInterval) < 10 ? "0" : "").append(ElapsedTimeUtil.MILLISECONDS.toHours(timeInterval)).append(":");
        stringBuilder.append(ElapsedTimeUtil.MILLISECONDS.toMinutes(timeInterval) < 10 ? "0" : "").append(ElapsedTimeUtil.MILLISECONDS.toMinutes(timeInterval)).append(":");
        stringBuilder.append(ElapsedTimeUtil.MILLISECONDS.toSeconds(timeInterval) < 10 ? "0" : "").append(ElapsedTimeUtil.MILLISECONDS.toSeconds(timeInterval));
        setText(stringBuilder.toString());
    }

    
    public interface CountDownCallback {

        /**
         * Callback fired on regular interval.
         * @param millisUntilFinished The amount of time until finished.
         */
        void onTick(long millisUntilFinished);

        /**
         * Callback fired when the time is up.
         */
        void onFinish();
        
    }

    
    
    /**
     * prefix text that that will be display
     * @param prefix
     */
    public void setPrefixText(String prefix) {
        prefixText = prefix;
    }

    /**
     * the text that should be display when the countdown finished 
     * @param text
     */
    public void setFinishedText(String text) {
        finishedText = text;
    }


    
    /**
     * @param seconds The number of seconds in the future since  Jan 1, 1970 GMT that the 
     *                countdown should finish ,
     *
     */
    public void setAbsoluteScheduledTimeBySeconds(int seconds) {
        setAbsoluteScheduledTime((long) seconds * 1000);
    }


    /**
     * @param milliseconds The number of seconds in the future since  Jan 1, 1970 GMT that 
     *                     the countdown should finish ,
     *
     */
    public void setAbsoluteScheduledTime(long milliseconds) {
        scheduledTime = milliseconds;
        mTimeMode = CountDownHelper.TIME_ABSOLUTE;
    }
    
  
    /**
     * @param seconds The number of seconds in the future from the call
     *   to {@link #start()} until the countdown is done
     */
    public void setRelativeScheduledTimeBySeconds(int seconds) {
        setRelativeScheduledTime((long) seconds * 1000);
    }

    /**
     * @param milliseconds The number of milliseconds in the future from the call
     *   to {@link #start()} until the countdown is done
     */
    public void setRelativeScheduledTime(long milliseconds) {
        scheduledTime = milliseconds;
        mTimeMode = CountDownHelper.TIME_RELATIVE;
    }

    /**
     * CountDown callback
     * @param callback
     */
    public void addCountDownCallback(CountDownCallback callback) {
        countDownCallback = callback;
    }
}
