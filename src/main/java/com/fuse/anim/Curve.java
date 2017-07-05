package com.fuse.anim;

import com.fuse.anim.CurveType;

public class Curve {

	private CurveType type;
	private final static CurveType DEFUALT_TYPE = CurveType.LINEAR;

	public Curve(){
		this.type = DEFUALT_TYPE;
	}

	public Curve(CurveType type){
		this.type = type;
	}

	public float get(float percent){
		switch(type){
			case LINEAR:
				return percent;

			// QUADRATIC_EASE_OUT; name and implementation stolen from ofxAnimatable by armadillu;
			// https://github.com/armadillu/ofxAnimatable/blob/master/src/ofxAnimatable.h
			case QUADRATIC_EASE_OUT:
				return 1.0f - (percent - 1.0f) * (percent - 1.0f);
		}
		
		return 0f;
	}

	public void setType(CurveType newType){
		type = newType;
	}
}
