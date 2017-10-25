package com.fuse.anim;

import com.fuse.anim.CurveType;

public class Curve {

  private boolean bMirrorSecondHalf = false;
  private boolean bMirrorLeft = false;

  public float get(float percent){
    if(bMirrorLeft){
      if(percent < 0.5f)  return 0.5f - 0.5f * this.valueFor(1.0f - percent*2.0f);
      else                return 0.5f + 0.5f * this.valueFor((percent - 0.5f) * 2.0f);
    }

    if(bMirrorSecondHalf && percent < 0.5f){
      return this.valueFor(0.5f) * 2.0f - this.valueFor(1.0f - percent);
    }

    return this.valueFor(percent);
  }

  protected float valueFor(float x){
    return x;
  }

  public Curve setMirrorSecondHalf(boolean active){
    bMirrorSecondHalf = active;
    return this;
  }

  public Curve setMirrorLeft(boolean active){
    bMirrorLeft = active;
    return this;
  }
}
