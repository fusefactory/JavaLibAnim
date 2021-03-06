package com.fuse.anim;

import com.fuse.anim.curves.LinearCurve;
import com.fuse.anim.curves.QuadraticEaseOutCurve;
import com.fuse.utils.Event;

import processing.core.PApplet;

/**
 * Base class for animations; takes care of interpolation, duration
 * and delay but doesn't hold any animated _value_.
 * Instead, it's designed as a base to animate different types of value
 * like a single float, a 3D vector, a color, etc. etc.
 *
 * Concept is stolen from ofxAnimatable by armadillu;
 * https://github.com/armadillu/ofxAnimatable/blob/master/src/ofxAnimatable.h
 *
 * Note that this class is not designed to be used directly,
 * should be extended instead; see Animatable class
 *
 * @author mark
 */
public class AnimatableBase {

  private Curve curve;
  public Event<Float> progressEvent;
  public Event<AnimatableBase> startAnimatingEvent, stopAnimatingEvent, doneEvent;

  private boolean isAnimating, isDelaying;
  private float  duration,
          progressTime,
          delayDuration,
          delayTime;

  /**
   * Default constructor; initializes an idle animation default values:
   * * Duration: 1.0 (seconds)
   * * Delay: 0.0
   * * Curve: LINEAR
   */
  public AnimatableBase(){
    _init();
    this.curve = new Curve(); // default curve (linear)
  }

  /**
   * Constructor which loads the same default values as the default constructor, but lets the caller specify the curve-type
   *
   * @param type Curve type (easing) to use for this animation
   */
  public AnimatableBase(CurveType type){
    _init();
    this.setCurveType(type);
  }

  /**
   * Constructor which loads the same default values as the default constructor, but lets the caller specify the duration in seconds
   *
   * @param type Curve type (easing) to use for this animation
   */
  public AnimatableBase(float duration){
    _init();
    this.setDuration(duration);
  }

  /** Initializes this event with default values, used by the constructors */
  private void _init(){
    isAnimating = false;
    isDelaying = false;
    duration = 1f;
    delayDuration = 0f;
    curve = new Curve();
    progressEvent = new Event<Float>();
    startAnimatingEvent = new Event<AnimatableBase>();
    stopAnimatingEvent = new Event<AnimatableBase>();
    doneEvent = new Event<AnimatableBase>();
  }

  public void destroy(){
    progressEvent.destroy();
    startAnimatingEvent.destroy();
    stopAnimatingEvent.destroy();
    doneEvent.destroy();
    isAnimating = false;
    isDelaying = false;
  }

  /**
   * Progresses the animation the specified amount of time.
   * Triggers the startAnimatingEvent if this progression completes the delay duration
   * Triggers the stopAnimatingEvent if this progression completes the animation duration (by calling the finish() method, which call the stop() method)
   *
   * @param dt DelaTime; amount of time to progress the animation (in seconds)
   */
  public void update(float dt){
    if(isDelaying){
      delayTime += dt;

      // still in delay
      if(delayTime < delayDuration)
        return;

      // delay finished, run update again with the amount of time
      // that we've surpassed the delay duration
      isDelaying = false;
      isAnimating = true;
      startAnimatingEvent.trigger(this);

      //update(delayTime - delayDuration);
      // return;

      // continue to animating part with remained of time
      dt = delayTime - delayDuration;
    }

    if(isAnimating){
      progressTime += dt;
      progressEvent.trigger(getProgress());

      if(progressTime >= duration)
        finish();
    }
  }

  /** @return float A value between 0.0 (zero; still at beginning) and 1.0 (one; completed) */
  public float getProgress(){
    // avoid divide by zero; if duration is zero, progress is always 1.0
    return duration == 0f ? 1f : PApplet.constrain(progressTime / duration, 0f, 1f);
  }

  /** @return float This animation's curve-type-specific value for the current progress value */
  public float getCurveValue(){
    return curve.get(getProgress());
  }

  /** @return float The animation's duration in seconds */
  public float getDuration(){
    return duration;
  }

  /**
   * Lets the caller specify the animation duration
   * @param newDuration New duration in seconds
   */
  public void setDuration(float newDuration){
    duration = newDuration;
  }

  /**
   * Let the caller update the curve type (ease-type) for the animation
   * @param type The type of curve to use, se the CurveType enum
   */
  public void setCurveType(CurveType type){
    switch(type){
      case LINEAR: this.setCurve(new LinearCurve()); break;
      case QUADRATIC_EASE_OUT: this.setCurve(new QuadraticEaseOutCurve()); break;
      default: this.setCurve(new Curve());
    }
  }

  public void setCurve(Curve curve){
    this.curve = curve;
  }

  /** Returns the duration of the delay in seconds */
  public float getDelayDuration(){
    return delayDuration;
  }

  public void setDelayDuration(float newValue){
    delayDuration = newValue;
  }

  public boolean isActive(){
    return isAnimating || isDelaying;
  }

  public boolean isAnimating(){
    return isAnimating;
  }

  public boolean isDone(){
    return progressTime >= duration;
  }

  /** Resets progress, starts delay-cycle if delayDuration is not zero, otherwise starts animating */
  public void start(){
    progressTime = 0f;
    progressEvent.trigger(getProgress());

    if(delayDuration > 0f){
      delayTime = 0f;
      isDelaying = true;
      if(isAnimating)
        stop();
      return;
    }

    isDelaying = false;
    isAnimating = true;
    startAnimatingEvent.trigger(this);
  }

  /** Triggers the stopAnimatingEvent and sets isAnimating flag to false. Does not affect progress */
  public void stop(){
    stopAnimatingEvent.trigger(this);
    isAnimating = false;
  }

  /** Just an alias for the stop method (for now) */
  public void pause(){
    this.stop();
  }

  public void resume(){
    if(isActive())
      return;

    if(delayTime < delayDuration)
      isDelaying = true;
    else
      isAnimating = true;
  }

  protected void finish(){
    // System.out.println("_finish");
    stop();
    doneEvent.trigger(this);
  }

  public float getTimeLeft(){
    float t = getDuration() - progressTime;
    if(delayTime < delayDuration)
      t += delayDuration - delayTime;
    return t;
  }
}
