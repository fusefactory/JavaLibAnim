package com.fuse.anim;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import processing.core.PGraphics;

public class AnimatableColorTest {
  @Test public void interpolation(){
    PGraphics pg = new PGraphics();

    AnimatableColor anim = new AnimatableColor();
    anim.animateFromTo(pg.color(255,0,0), pg.color(0, 255, 0), 4.0f); // red-to-green in 4 seconds
    anim.update(1.0f); // move 1.0 second into the future; quarter into the animation
    assertEquals((int)anim.val(), (int)pg.lerpColor(pg.color(255,0,0), pg.color(0, 255,0), 0.25f));
    anim.update(1.0f); // move 1.0 second into the future; quarter into the animation
    assertEquals((int)anim.val(), (int)pg.lerpColor(pg.color(255,0,0), pg.color(0, 255,0), 0.5f));
  }
}
