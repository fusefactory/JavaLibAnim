package com.fuse.anim.curves;

import com.fuse.anim.Curve;

public class EaseOutBounce extends Curve {

  @Override
  protected float valueFor(float x){
    // https://github.com/armadillu/ofxAnimatable/blob/master/src/ofxAnimatable.cpp#L148
    float s = 7.5625f;
    float p = 2.75f;
    float pow = 2.0f;
    float l;
    if (x < (1.0f/p)){
      l = s * (float)Math.pow(x, pow);
    }else{
      if (x < (2.0f/p)){
        x -= 1.5f/p;
        l = s * (float)Math.pow(x, pow) + 0.75f;
      }else{
        if (x < 2.5f/p){
          x -= 2.25f/p;
          l = s * (float)Math.pow(x, pow) + 0.9375f;
        }else{
          x -= 2.625f/p;
          l = s * (float)Math.pow(x, pow) + 0.984375f;
        }
      }
    }
    return l;
  }

}
