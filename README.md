# CountDownTextView
A simple widget and easy to use.

###Simple
![demo](./screenshots/demo.gif)

###Usage
------
CountDownTextView accept two kinds of time
e.g. assume now is 2015.11.8.9 AM，Your schedule time is 2015.11.8.12 AM.
1.Absolute Time：a timestamp since Jan 1, 1970 

2.Relative Time：It is a relative time difference from a time to your scheduld time，so the time is 3 hour,If use the time ，before use setRelativeScheduledTime(long)，you should make your time add SystemClock.elapsedRealtime();

``` java
CountDownTextView countDownTextView = findViewById(R.id.countDownTextView)
countDownTextView.setRelativeScheduledTime(relativeTime);
countDownTextView.setAutoShowText(true);
countDownTextView.start();

```

setAutoShowText()
If true，CountDownTextView auto show the time,the formater is "12:23:45"(Hour:Minute:Seconds)
setShowDay(true)
If you wang to display day in auto show text。

setPrefixText()
set the prefix text "倒数计时 : 12:23:45 "

###Callback

``` java
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
```
