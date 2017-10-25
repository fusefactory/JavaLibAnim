package com.fuse.anim.curves;

import com.fuse.anim.Curve;

public class EaseOutElastic extends Curve {

  private float param1 = 1.0f;
  private float param2 = 1.0f;
  private float param3 = 1.0f;

  @Override
  protected float valueFor(float x){
    // https://github.com/armadillu/ofxAnimatable/blob/master/src/ofxAnimatable.cpp#L222

    if (x == 0.0f || x == 1.0f)
      return x;

    float p = 0.3f * param2;
    float s = p * 0.25f;
    return (float)Math.pow(2.0f + param3, -param1 * 10.0f * x) * (float)Math.sin((x*x-s) * Math.PI * 2.0f / p) + 1.0f;
  }
}
