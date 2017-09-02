package com.fuse.anim;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Ignore;

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

  @Test public void events(){
    Animatable anim = new Animatable();
    anim.animateFromTo(0.0f, 1.0f, 2.0f); // 2 seconds
    List<String> messages = new ArrayList<>();
    anim.updateEvent.addListener((Float val) -> messages.add("anim update"));
    anim.changeEvent.addListener((Float val) -> messages.add("anim change"));
    anim.doneEvent.addListener((AnimatableBase an) -> messages.add("anim done"));
    anim.stopAnimatingEvent.addListener((AnimatableBase an) -> messages.add("anim stop"));
    assertEquals(messages.size(), 0);
    anim.update(1.5f);
    assertEquals(messages.size(), 2);
    assertEquals(messages.get(0), "anim change");
    assertEquals(messages.get(1), "anim update");
    anim.update(1.0f);
    assertEquals(messages.size(), 6);
    assertEquals(messages.get(2), "anim change");
    assertEquals(messages.get(3), "anim update");
    assertEquals(messages.get(4), "anim stop");
    assertEquals(messages.get(5), "anim done");
  }

  @Test public void progressEvent(){
    Animatable anim = new Animatable();
    anim.progressEvent.enableHistory();
    assertEquals(anim.progressEvent.getHistory().size(), 0);
    anim.animateFromTo(0.0f, 1.0f, 10.0f); // duration = 10 seconds
    assertEquals((float)anim.progressEvent.getHistory().get(0), 0.0f, 0.00001f);
    assertEquals(anim.progressEvent.getHistory().size(), 1);
    anim.update(1.0f);
    assertEquals((float)anim.progressEvent.getHistory().get(1), 0.1f, 0.00001f);
    assertEquals(anim.progressEvent.getHistory().size(), 2);
    anim.update(4.0f);
    assertEquals((float)anim.progressEvent.getHistory().get(2), 0.5f, 0.00001f);
    assertEquals(anim.progressEvent.getHistory().size(), 3);
  }

  @Test public void detroy(){
    Animatable anim = new Animatable();
    anim.progressEvent.addListener((Float v) -> {});
    anim.startAnimatingEvent.addListener((AnimatableBase a) -> {});
    anim.stopAnimatingEvent.addListener((AnimatableBase a) -> {});
    anim.doneEvent.addListener((AnimatableBase a) -> {});
    anim.updateEvent.addListener((Float v) -> {});
    anim.changeEvent.addListener((Float v) -> {});
    anim.start();

    assertEquals(anim.progressEvent.size(), 1);
    assertEquals(anim.startAnimatingEvent.size(), 1);
    assertEquals(anim.stopAnimatingEvent.size(), 1);
    assertEquals(anim.doneEvent.size(), 1);
    assertEquals(anim.updateEvent.size(), 1);
    assertEquals(anim.changeEvent.size(), 1);
    assertEquals(anim.isActive(), true);
    assertEquals(anim.isAnimating(), true);

    anim.destroy();

    assertEquals(anim.progressEvent.size(), 0);
    assertEquals(anim.startAnimatingEvent.size(), 0);
    assertEquals(anim.stopAnimatingEvent.size(), 0);
    assertEquals(anim.doneEvent.size(), 0);
    assertEquals(anim.updateEvent.size(), 0);
    assertEquals(anim.changeEvent.size(), 0);
    assertEquals(anim.isActive(), false);
    assertEquals(anim.isAnimating(), false);
  }

  @Test public void isDone(){
    Animatable anim = new Animatable();
    assertEquals(anim.isDone(), false);
    anim.setDuration(10.0f);
    assertEquals(anim.isDone(), false);
    anim.start();
    assertEquals(anim.isDone(), false);
    anim.update(5.0f);
    assertEquals(anim.isDone(), false);
    anim.update(4.5f);
    assertEquals(anim.isDone(), false);
    anim.update(0.5f);
    assertEquals(anim.isDone(), true);
  }

  @Ignore @Test public void pause_resume(){

  }
}
