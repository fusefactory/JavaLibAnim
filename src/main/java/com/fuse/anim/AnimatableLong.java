package com.fuse.anim;

/**
 *
 * Class for animating single a long value, see Animatable class except
 * instead of a floating point value we're animating a 32-bit integer type value
 *
 * @author mark
 *
 */
public class AnimatableLong extends AnimatableBaseWithValue<Long> {

	  public AnimatableLong(){
	    super();
	    value = fromValue = 0l;
	    toValue = 1l;
	  }

	  public AnimatableLong(CurveType type){
	    super(type);
	    value = fromValue = 0l;
	    toValue = 1l;
	  }

	  public Long getValueDelta(){ return toValue - fromValue; }

	  @Override
	  protected Long calculateInterpolatedValue(){
			return fromValue + (long)((toValue - fromValue) * getCurveValue());
	  }
}
