package com.ufreedom.countdowntextview;

import android.net.Uri;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Author SunMeng
 * Date : 2015 十月 29
 */
public class AnalogHolder extends BaseViewHolder<AnalogData> {

    SimpleDraweeView simpleDraweeView;

    public AnalogHolder(View itemView) {
        super(itemView);
        simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.simpleDraweeView);
    }

    @Override
    public void onBindView(AnalogData object) {
        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(object.pic))
                        .setResizeOptions(new ResizeOptions(300, 300))
                        .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(simpleDraweeView.getController())
                .setAutoPlayAnimations(true)
                .build();
        simpleDraweeView.setController(draweeController);
    }
}
