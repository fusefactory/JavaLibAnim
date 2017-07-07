package com.fuse.anim;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import processing.core.PVector;

public class AnimatableVectorTest {
  @Test public void interpolate(){
    AnimatableVector anim = new AnimatableVector();
    anim.animateFromTo(new PVector(1000.0f, 0.0f, 0.0f), new PVector(100.0f, 0.0f, 0.0f), 3.0f);
    anim.update(1.5f);
    assertEquals((float)anim.val().x, 550.0f, 0.00001);

    anim.setDelayDuration(0.3f);
    anim.animateFromTo(new PVector(2211.0f, 0.0f, 0.0f), new PVector(0.0f, 0.0f, 0.0f), 1.0f);
    assertEquals((float)anim.val().x, 2211.0f, 0.00001);
    anim.update(0.3f);
    assertEquals((float)anim.val().x, 2211.0f, 0.00001);
    anim.update(0.5f);
    assertEquals((float)anim.val().x, 1105.5f, 0.00001);
    anim.update(0.5f);
    assertEquals((float)anim.val().x, 0.0f, 0.00001);
  }
}
