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

    public AnalogData(String pic, long scheduleTime) {
        this.scheduleTime = scheduleTime;
        this.pic = pic;
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
}
