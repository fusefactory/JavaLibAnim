package com.fuse.anim;

import processing.core.PGraphics;

public class AnimatableColor extends AnimatableBaseWithValue<Integer> {

  public AnimatableColor(){
    super();
    PGraphics pg = getPg();
    value = fromValue = pg.color(0,0,0);
    toValue = pg.color(255,255,255);
  }

  public AnimatableColor(CurveType type){
    super(type);
    PGraphics pg = getPg();
    value = fromValue = pg.color(0,0,0);
    toValue = pg.color(255,255,255);
  }

  //public float getDistance(){ return fromValue.dist(toValue); }
  //public PVector getDeltaVector(){ return PVector.sub(toValue, fromValue); }

  @Override
  protected Integer calculateInterpolatedValue(){
    PGraphics pg = getPg();
    float curveValue = getCurveValue();
    return pg.lerpColor(fromValue, toValue, curveValue);
  }

  private PGraphics getPg(){
    return new PGraphics();
  }
}
