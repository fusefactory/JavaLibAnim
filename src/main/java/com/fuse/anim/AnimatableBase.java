package com.fuse.anim;

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
 * @author mark
 *
 * @usage not designed to be used directly,
 * should be extended instead; see Animatable class
 */
public class AnimatableBase {

	Curve curve;
	public Event<Float> updateEvent;
	public Event<AnimatableBase> startAnimatingEvent, stopAnimatingEvent;

	private boolean isAnimating, isDelaying;
	private float	duration,
					progressTime,
					delayDuration,
					delayTime;

	// constructor(s)
	public AnimatableBase(){
		_init();
		curve = new Curve();
	}

	public AnimatableBase(CurveType type){
		_init();
		setCurveType(type);
	}

	private void _init(){
		isAnimating = false;
		isDelaying = false;
		duration = 1f;
		delayDuration = 0f;
		curve = new Curve();
		updateEvent = new Event<Float>();
		startAnimatingEvent = new Event<AnimatableBase>();
		stopAnimatingEvent = new Event<AnimatableBase>();
	}

	// common methods
	public void update(float dt){
		if(isDelaying){
			delayTime+= dt;

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

			if(progressTime >= duration)
				finish();
		}
	}

	// returns a value between 0.0 (zero) and 1.0 (one)
	public float getProgress(){
		// avoid divide by zero; if duration is zero, progress is always 1.0
		return duration == 0f ? 1f : PApplet.constrain(progressTime / duration, 0f, 1f);
	}

	public float getCurveValue(){
		return curve.get(getProgress());
	}

	public float getDuration(){
		return duration;
	}

	public void setDuration(float newDuration){
		duration = newDuration;
	}

	public void setCurveType(CurveType type){
		curve.setType(type);
	}

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

	public void start(){
		progressTime = 0f;

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

	public void stop(){
		stopAnimatingEvent.trigger(this);
		isAnimating = false;
	}

	protected void finish(){
		stop();
	}
}
