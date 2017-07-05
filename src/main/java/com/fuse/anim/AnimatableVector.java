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

  public float getDistance(){ return fromValue.dist(toValue); }

  @Override
  protected PVector calculateInterpolatedValue(){
    PVector result = fromValue.get(); // no .copy()?!
    result.lerp(toValue, getCurveValue());
    return result;
  }
}
