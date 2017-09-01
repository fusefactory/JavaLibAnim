package com.fuse.anim;

import com.fuse.utils.Event;

public class AnimatableBaseWithValue<T> extends AnimatableBase {

  public Event<T> updateEvent, changeEvent;

  protected T value,
          fromValue,
          toValue;

  // constructor(s)
  public AnimatableBaseWithValue(){
    super();
    updateEvent = new Event<T>();
    changeEvent = new Event<T>();
  }

  public AnimatableBaseWithValue(CurveType type){
    super(type);
    updateEvent = new Event<T>();
    changeEvent = new Event<T>();
  }

  @Override public void destroy(){
    updateEvent.destroy();
    changeEvent.destroy();
    super.destroy();
  }

  // common methods
  @Override
  public void update(float dt){
    super.update(dt);

    // is we just finished the animation, the last updateValue got called in the finish() methods
    if(isAnimating()){
      updateValue();
    }
  }

  private void updateValue(){
    setValue(calculateInterpolatedValue(), false);
    updateEvent.trigger(value);
  }

  public T updateAndGive(float dt){
    // call regular update
    update(dt);
    // and return value
    return value;
  }

  // getter / setter methods
  public T val(){ return value; }
  public T getValue(){ return value; }
  public T getFromValue(){ return fromValue; }
  public T getToValue(){ return toValue; }

  public void setToValue(T val){ toValue = val; }
  public void setFromValue(T val){ fromValue = val; }

  // helper methods
  public void animateFromTo(T from, T to){
    fromValue = from;
    toValue = to;
    // value = from;
    setValue(from, false);
    start();
  }

  public void animateFromTo(T from, T to, float duration){
    setDuration(duration);
    animateFromTo(from, to);
  }

  public void animateFromToWithDelay(T from, T to, float delay){
    setDelayDuration(delay);
    animateFromTo(from, to);
  }

  public void animateTo(T to){
    animateFromTo(this.getValue(), to);
  }

  public void animateToWithDelay(T to, float delay){
    animateFromToWithDelay(this.getValue(), to, delay);
  }

  protected T calculateInterpolatedValue(){
    // return PApplet.lerp(fromValue, toValue, getCurveValue());
    return null;
  }

  public void start(){
    super.start();
    setValue(calculateInterpolatedValue(), false /* don't stop */);
  }

  public void setValue(T value){
    setValue(value, true /* stop animating */);
  }

  public void setValue(T newValue, boolean stop){
    value = newValue;
    changeEvent.trigger(value);

    if(stop)
      stop();
  }

  @Override
  protected void finish(){
    updateValue();
    super.finish();
  }
}
