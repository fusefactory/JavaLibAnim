package com.fuse.anim;

import processing.core.PVector;

/**
 *
 * Class for animating 3D Vector values, for usage example see Animatable class
 *
 * @author mark
 *
 */
public class AnimatableVector extends AnimatableBaseWithValue<PVector> {

  public AnimatableVector(){
    super();
    value = fromValue = new PVector();
    toValue = new PVector(1.0f, 1.0f, 1.0f);
  }

  public AnimatableVector(CurveType type){
    super(type);
    value = fromValue = new PVector();
    toValue = new PVector(1.0f, 1.0f, 1.0f);
  }

  //public float getDistance(){ return fromValue.dist(toValue); }
  //public PVector getDeltaVector(){ return PVector.sub(toValue, fromValue); }

  @Override
  protected PVector calculateInterpolatedValue(){
    float curveValue = getCurveValue();
    PVector result = PVector.lerp(fromValue, toValue, curveValue);
    return result;
  }

  @Override
  public void setValue(PVector newValue, boolean stop){
    value = newValue.copy();
    changeEvent.trigger(value);

    if(stop)
      stop();
  }
}
