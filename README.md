# JavaLibAnim

[![Build Status](https://travis-ci.org/fusefactory/JavaLibAnim.svg?branch=master)](https://travis-ci.org/fusefactory/JavaLibAnim)

_Java animation classes inspired by armadillu's OpenFrameworks (C++) addon: [ofxAnimatable](https://github.com/armadillu/ofxAnimatable)._


## Installation

Use as maven/gradle/sbt/leiningen dependency with [JitPack](https://github.com/jitpack/maven-modular)
* https://jitpack.io/#fusefactory/JavaLibAnim

For more info on jitpack see;
* https://github.com/jitpack/maven-simple
* https://jitpack.io/docs/?#building-with-jitpack

## JavaDocs
* https://fusefactory.github.io/JavaLibAnim/site/apidocs/index.html

## Usage: Animated float value
```java
import com.fuse.anim.*;

Animatable anim;
float framerate = 60;;

void setup(){
    Animatable anim = new Animatable();
    anim.setDelay(0.5f); // half a second delay
    anim.setDuration(10.0f); // seconds; default = 1.0f
    anim.setCurveType(CurveType.QUADRATIC_EASE_OUT); // default: LINEAR
    anim.animateFromTo(0.0f, 100.0f); // starts animation (including delay) from start value zero and end value hundred
}

void update(){
    float dt = 1.0f / framerate;
    anim.update(dt); // provide a "delta-time" (in seconds) every update
}

void draw(){
    float value = anim.getValue(); // get current animated value between 0.0f and 100.0f

    float progress = anim.getProgress(); // gives the current progress in a 0.0-1.0 range, 0.0 meaning just started, 1.0 meaning finished (regardless of duration)

    float countDown = anim.getTimeLeft(); // included delay time

    // ... do something with values ...
}

## Usage: event listeners
_TODO_

## Usage: Timeline
_TODO_

## Usage: Color animation
_TODO_
