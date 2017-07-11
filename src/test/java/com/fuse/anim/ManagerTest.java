package com.fuse.anim;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.*;

public class ManagerTest {
  @Test public void doneEvent(){

    Animatable a1 = new Animatable();
    a1.setDuration(5.0f);
    a1.start();
    Animatable a2 = new Animatable();
    a2.setDelayDuration(1.0f);
    a2.setDuration(5.0f);
    a2.start();

    Manager man = new Manager();
    man.add(a1);
    man.add(a2);

    List<String> messages = new ArrayList<String>();

    man.whenAllDone(() -> {
      messages.add("all done");
    });

    man.itemDoneEvent.addListener((Animatable anim) -> {
      messages.add("item done");
    });

    assertEquals(messages.size(), 0);
    man.update(4.0f);
    assertEquals(messages.size(), 0);
    man.update(1.5f);
    assertEquals(messages.size(), 1);
    assertEquals(messages.get(0), "item done");
    man.update(0.5f);
    assertEquals(messages.size(), 3);
    assertEquals(messages.get(1), "item done");
    assertEquals(messages.get(2), "all done");
  }
}
