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

    timeline.itemDoneEvent.addListener((Animatable anim) -> {
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
}
