package com.fuse.anim;

import processing.core.PApplet;

/**
 * Class for animating single a float value.
 * Features: custom from/to value, duration, delay, different curve-types (for easing)
 * and lambda event notifications for when the animation starts, updates and stop.
 *
 * <p><strong>Usage</strong></p>
 * <pre>
 * Animatable anim = new Animatable();
 * anim.animateFromTo(0, 5, 0.5); // animate from zero to five in half a second. The last parameter (duration) is optional and defaults to 1.0f
 * if(anim.isActive()){ // see if animation isn't finished
 * 		anim.update(dt); // provide amount of time to progress the animation
 * 		float value = anim.val(); // get current interpolated value
 *    if(!anim.isActive()){ // just finished
 *      // do Post-animation processing
 *  }
 * }
 * </pre>
 *
 * @author mark
 */
public class Animatable extends AnimatableBaseWithValue<Float> {

  /**
   * Default constructor; intializes with default values;
   * * Duration: 1.0 (seconds)
   * * Delay: 0.0
   * * Curve: LINEAR
   * * From: 0.0
   * * To: 1.0
   * * Current value: 0.0
   */
  public Animatable(){
    super();
    value = fromValue = 0f;
    toValue = 1f;
  }

  /**
   * Constructor initializes with the same values as the default constructor,
   * except it lets the caller specify a curve type
   *
   * @param type Curve type (easing) to use for this animation
   */
  public Animatable(CurveType type){
    super(type);
    value = fromValue = 0f;
    toValue = 1f;
  }

  /**
   * Returns the difference between the "to" value and the "from" value
   * @return float Difference between the to and from value
   */
  public float getValueDelta(){ return toValue - fromValue; }

  /**
   * Interpolates from the "from" value to the "to" value using the current curve-value (see getCurveValue)
   * @return Float current animation value vased on from-value, to-value and current curve-value
   */
  @Override protected Float calculateInterpolatedValue(){
    return PApplet.lerp(fromValue, toValue, getCurveValue());
  }
}
