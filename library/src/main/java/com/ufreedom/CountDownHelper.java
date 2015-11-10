package com.ufreedom;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
/**
 * Helper Class that implements a simple CountDown.
 * <p>You can give it a start time in the {@link SystemClock#elapsedRealtime} timebase,
 * and it counts down from that.
 * 
 * Author SunMeng
 * Date : 2015 十一月 02
 * The help to CountDown
 */
public abstract  class CountDownHelper  {

    

    /**
     * Millis since epoch when alarm should stop.
     */
    private final long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval;
    

    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;


    public static final int UPDATE_TIME = 0;


    /**
     * @param millisInFuture The number of millis in the future from the call
     *   to {@link #start()} until the countdown is done and {@link #onFinish()}
     *   is called.
     * @param countDownInterval The interval along the way to refresh date
     *  
     */
    public CountDownHelper(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }
    
    /**
     * Callback fired on regular interval.
     * @param millisUntilFinished The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * Callback fired when the time is up.
     */
    public abstract void onFinish();


    /**
     * Cancel the countdown.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(UPDATE_TIME);
    }

    /**
     * Start the countdown.
     */
    public synchronized final CountDownHelper start() {
        mCancelled = false;
        if (mMillisInFuture <= 0L) {
            onFinish();
            return this;
        }
        mHandler.sendMessage(mHandler.obtainMessage(UPDATE_TIME));
        return this;
    }
    
    
    // handles counting down
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (CountDownHelper.this) {
                if (mCancelled) {
                    return;
                }

             /*   final long millisLeft = getTimeLeft(mMillisInFuture);
                if (millisLeft <= 0){
                    onFinish();
                    removeMessages(UPDATE_TIME);
                }else {
                    onTick(millisLeft);
                    sendEmptyMessageDelayed(UPDATE_TIME, mCountdownInterval);
                }*/

                final long millisLeft = mMillisInFuture - SystemClock.elapsedRealtime();

                if (millisLeft <= 0) {
                    onFinish();
                } else if (millisLeft < mCountdownInterval) {
                    // no tick, just delay until done
                    sendMessageDelayed(obtainMessage(UPDATE_TIME), millisLeft);
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();
                    onTick(millisLeft);

                    // take into account user's onTick taking time to execute
                    long delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime();

                    // special case: user's onTick took more than interval to
                    // complete, skip to next interval
                    while (delay < 0) delay += mCountdownInterval;

                    sendMessageDelayed(obtainMessage(UPDATE_TIME), delay);
                }

            }
        }
    };


/*    private long getTimeLeftInAbsolute(long reqMilliseconds){
        *//*Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTimeInMillis();*//*
        return reqMilliseconds - System.currentTimeMillis();
    }
    
    private long getTimeLeftInRelative(long reqMilliseconds){
        return reqMilliseconds - SystemClock.elapsedRealtime();
    }
    
    private long getTimeLeft(long reqMilliseconds){
        return mTimeMode == TIME_ABSOLUTE ? getTimeLeftInAbsolute(reqMilliseconds) : getTimeLeftInRelative(reqMilliseconds);
    }*/
    
    
}
