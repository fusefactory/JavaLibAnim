package com.fuse.anim;

import processing.core.PApplet;

/**
 *
 * Class for animating single a float value, supports different
 * "easing" curves through the AnimatableBase class.
 *
 * @author mark
 *
 * @usage
 *
 * Animatable anim = new Animatable();
 * anim.animateFromTo(0, 5, 0.5); // animate from 0 to 5 in 0.5 seconds, duration is optional and defaults to 1.0f
 * if(anim.isActive()){ // see if animation isn't finished
 * 		anim.update(dt); // provide amount of time to progress the animation
 * 		anim.val(); // get current interpolated value
 * }
 *
 */
public class Animatable extends AnimatableBaseWithValue<Float> {

  public Animatable(){
    super();
    value = fromValue = 0f;
    toValue = 1f;
  }

  public Animatable(CurveType type){
    super(type);
    value = fromValue = 0f;
    toValue = 1f;
  }

  public float getValueDelta(){ return toValue - fromValue; }

  @Override
  protected Float calculateInterpolatedValue(){
    return PApplet.lerp(fromValue, toValue, getCurveValue());
  }
}
