package com.fuse.anim.curves;

import com.fuse.anim.Curve;

public class QuadraticEaseOutCurve extends Curve {

  @Override
  protected float valueFor(float x){
    return 1.0f - (x - 1.0f) * (x - 1.0f);
  }

}
