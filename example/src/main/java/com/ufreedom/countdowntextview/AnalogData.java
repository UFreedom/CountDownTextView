package com.ufreedom.countdowntextview;

/**
 * Author SunMeng
 * Date : 2015 十月 29
 */
public class AnalogData {

    // Item background pic
    private String pic;

    // Start time to be shown on CountDownTextView
    private long scheduleTime;

    private int timeFormat;

    public AnalogData(String pic, long scheduleTime, int timeFormat) {
        this.scheduleTime = scheduleTime;
        this.pic = pic;
        this.timeFormat = timeFormat;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public long getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(long scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public int getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(int timeFormat) {
        this.timeFormat = timeFormat;
    }
}
