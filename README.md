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

## Usage: Animate a float value using the Animatable class
```java
import com.fuse.anim.*;

Animatable anim;
float framerate = 60;

void setup(){
    Animatable anim = new Animatable();
    anim.setDelay(0.5f); // half a second delay
    anim.setDuration(10.0f); // seconds; default = 1.0f
    anim.setCurveType(CurveType.QUADRATIC_EASE_OUT); // default: LINEAR
    anim.animateFromTo(0.0f, 100.0f); // starts animation (including delay) from start value zero to end value hundred
}

void update(){
    float dt = 1.0f / framerate;

    if(anim.isActive()){
        anim.update(dt); // provide a "delta-time" (in seconds) every update

        if(anim.isDone()){
            setStatus("Animation finished!");
        }
    }
}

void draw(){
    float value = anim.getValue(); // get current animated value between 0.0f and 100.0f

    float progress = anim.getProgress(); // gives the current progress in a 0.0-1.0 range, 0.0 meaning just started, 1.0 meaning finished (regardless of duration)

    float countDown = anim.getTimeLeft(); // included delay time
}
```

## Usage: event listeners
```java
import com.fuse.anim.*;

Animatable anim;
float framerate = 60;
float drawPosition = 0.0f;
int color = 0.5;

void setup(){
    Animatable anim = new Animatable();
    anim.setDelay(0.5f); // half a second delay
    anim.setDuration(10.0f); // seconds; default = 1.0f
    anim.setCurveType(CurveType.QUADRATIC_EASE_OUT); // default: LINEAR
    anim.animateFromTo(0.0f, 100.0f); // starts animation (including delay) from start value zero to end value hundred

    // register an event listener that updates our drawPosition
    // every time the animation's value changes
    anim.changeEvent.addListener((Float newValue) -> {
        drawPosition = newValue;
    });

    anim.progressEvent.addListener((Float progress) -> {
        setProgressBarPercentage(progress);
    });

    anim.doneEvent.whenTriggered(()-> {
        setStatus("Done!");
    });
}

void update(){
    float dt = 1.0f / framerate;
    anim.update(dt); // provide a "delta-time" (in seconds) every update
}
```

## Usage: Animated vectors with AnimatableVector
AnimatableVector has exactly the same API as Animatable, but works with the processing vector class PVector.

```java
AnimatableVector anim = new AnimatableVector();
anim.animateFromTo(new PVector(10.0f, 10.0f, 0.0f), new PVector(100.0f, 10.0f, 0.0f));
anim.update(frameTimeInSeconds);
moveTo(anim.getValue());
drawObject();
```

## Usage: creating your own animatable type

This package includes the following animatable types;
* Animatable (float)
* AnimatableLong (long)
* AnimatableColor (processing color / int)
* AnimatableVector (PVector)

You can create your own animatable type by inheriting from the template classes
AnimatableBaseWithValue<T> and overriding the calculateInterpolatedValue method.

## Usage: Timeline
_TODO_
