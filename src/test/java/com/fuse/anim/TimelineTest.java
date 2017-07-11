package com.fuse.anim;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.*;

public class TimelineTest {
  @Test public void doneEvent(){

    Animatable a1 = new Animatable();
    a1.setDuration(5.0f);
    a1.start();
    Animatable a2 = new Animatable();
    a2.setDelayDuration(1.0f);
    a2.setDuration(5.0f);
    a2.start();

    Timeline timeline = new Timeline();
    timeline.add(a1);
    timeline.add(a2);

    List<String> messages = new ArrayList<String>();

    timeline.whenAllDone(() -> {
      messages.add("all done");
    });

    timeline.animDoneEvent.addListener((AnimatableBase anim) -> {
      messages.add("item done");
    });

    assertEquals(messages.size(), 0);
    timeline.update(4.0f);
    assertEquals(messages.size(), 0);
    timeline.update(1.5f);
    assertEquals(messages.size(), 1);
    assertEquals(messages.get(0), "item done");
    timeline.update(0.5f);
    assertEquals(messages.size(), 3);
    assertEquals(messages.get(1), "item done");
    assertEquals(messages.get(2), "all done");
  }

  @Test public void autoStart(){
    Animatable a1 = new Animatable();
    a1.setDuration(5.0f);
    Animatable a2 = new Animatable();
    a2.setDelayDuration(1.0f);
    a2.setDuration(5.0f);

    Timeline timeline = new Timeline();

    assertEquals(a1.isActive(), false);
    timeline.add(a1);
    assertEquals(a1.isActive(), true);
    assertEquals(a1.isAnimating(), true);

    assertEquals(a2.isActive(), false);
    timeline.add(a2);
    assertEquals(a2.isActive(), true);
    assertEquals(a2.isAnimating(), false);
  }
}
