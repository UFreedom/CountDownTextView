package com.ufreedom.countdowntextview;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.ufreedom.CountDownTextView;

/**
 * Author UFreedom
 * Date : 2015 十月 29
 */
public class AnalogHolder extends RecyclerView.ViewHolder {

    private SimpleDraweeView simpleDraweeView;
    private CountDownTextView countDownTextView;

    public AnalogHolder(View itemView) {
        super(itemView);
        simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.simpleDraweeView);
        countDownTextView = (CountDownTextView) itemView.findViewById(R.id.countDownTextView);
    }

    public void onBindView(AnalogData object) {
        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(object.getPic()))
                        .setResizeOptions(new ResizeOptions(300, 300))
                        .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(simpleDraweeView.getController())
                .setAutoPlayAnimations(true)
                .build();
        simpleDraweeView.setController(draweeController);
        countDownTextView.setTimeInFuture(object.getScheduleTime());
        countDownTextView.setAutoDisplayText(true);
        countDownTextView.start();
        countDownTextView.addCountDownCallback(new CountDownTextView.CountDownCallback() {
            @Override
            public void onTick(CountDownTextView countDownTextView, long millisUntilFinished) {
                
            }

            @Override
            public void onFinish(CountDownTextView countDownTextView) {
                countDownTextView.setText("00:00:00");
            }
        });
        
    }
}
