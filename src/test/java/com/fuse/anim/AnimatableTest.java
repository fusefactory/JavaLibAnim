package com.fuse.anim;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.*;

public class AnimatableTest {
  private float result;
  private List<Float> values;

  @Test public void stopAnimatingEvent(){
    values = new ArrayList<Float>();

    Animatable anim = new Animatable();
    anim.setDuration(2.0f); // 2 seconds
    anim.animateFromTo(0.0f, 3.0f);

    result = -1.0f;
    anim.stopAnimatingEvent.addListener((AnimatableBase a) -> {
      result = ((Animatable)a).val();
    });

    assertEquals(result, -1,0f);
    anim.update(1.0f);
    assertEquals(result, -1,0f);
    assertEquals((float)anim.val(), 1.5f, 0.00001f);
    anim.update(1.0f);
    assertEquals(result, 3.0f, 0.00001f);
    assertEquals((float)anim.val(), 3.0f, 0.00001f);
  }

  @Test public void updateOnlyOnceOnFinish(){
    values = new ArrayList<Float>();

    Animatable anim = new Animatable();
    anim.setDuration(1.0f);
    anim.animateFromTo(0.0f, 1.0f);

    anim.updateEvent.addListener((Float val) -> {
      values.add(val);
    });

    assertEquals(values.size(), 0);
    anim.update(0.5f);
    assertEquals(values.size(), 1);
    anim.update(0.5f);
    assertEquals(values.size(), 2);
    assertEquals((float)values.get(0), 0.5f, 0.00001f);
    assertEquals((float)values.get(1), 1.0f, 0.00001f);
  }
}
