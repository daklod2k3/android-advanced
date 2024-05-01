package com.daklod.techshop;

import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class LoadingController{
//    Integer animationRaw = R.raw.loading_animation;
    LottieAnimationView view;
    Boolean isPlay;

    public LoadingController(LottieAnimationView view){
        this.view = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay)
                    Stop();
                else
                    Play();
            }
        });
        isPlay = true;
    }



    public void Play(){
        if (isPlay) return;
        view.playAnimation();
        isPlay = true;
    }

    public void Stop(){
        if (!isPlay) return;
        view.setProgress(0);
        isPlay = false;
    }

}
