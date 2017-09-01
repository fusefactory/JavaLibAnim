package com.fuse.anim;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

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

    timeline.whenAllDone(() -> { messages.add("all done"); });
    timeline.animDoneEvent.addListener((AnimatableBase anim) -> { messages.add("item done"); });

    assertEquals(messages.size(), 0);
    timeline.update(4.0f);
    assertEquals(messages.size(), 0);
    timeline.update(1.5f);
    assertEquals(messages.size(), 1);
    assertEquals(messages.get(0), "item done");
    timeline.update(0.5f);
    assertEquals(messages.get(1), "all done");
    assertEquals(messages.get(2), "item done");
    assertEquals(messages.size(), 3);
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

  @Test public void addOtherTimelines(){
    // System.out.println("addOtherTimelines");
    Timeline main = new Timeline();
    Timeline sub = new Timeline();

    Animatable a1 = new Animatable();
    a1.setDuration(5.0f);
    sub.add(a1);

    assertEquals(main.size(), 0);
    assertEquals(sub.size(), 1);
    main.add(sub);
    assertEquals(main.size(), 1); // main adopts animations from sub
    assertEquals(main.get(0), a1);

    Animatable a2 = new Animatable();
    a2.setDelayDuration(1.0f);
    a2.setDuration(5.0f);
    sub.add(a2);

    // main copies newly added animations from sub
    assertEquals(main.size(), 2);
    assertEquals(sub.size(), 2);

    List<String> strings = new ArrayList<>();
    main.animDoneEvent.addListener((AnimatableBase anim) -> strings.add("main anim done"));
    main.whenAllDone(() -> strings.add("all main done"));
    sub.animDoneEvent.addListener((AnimatableBase anim) -> strings.add("sub anim done"));
    sub.whenAllDone(() -> strings.add("all sub done"));

    assertEquals(strings.size(), 0);
    sub.update(5.0f);
    assertEquals(strings.stream().collect(Collectors.joining(",")), "main anim done,sub anim done");
    sub.update(1.0f);
    assertEquals(strings.stream().collect(Collectors.joining(",")), "main anim done,sub anim done,all main done,main anim done,all sub done,sub anim done");
  }

  @Test public void after(){
    Timeline timeline = new Timeline();
    List<String> messages = new ArrayList<>();
    timeline.after(3.0f, () -> messages.add("3 seconds passed"));
    timeline.after(5.0f, () -> messages.add("5 seconds passed"));
    timeline.update(2.0f);
    assertEquals(messages.size(), 0);
    timeline.update(2.0f);
    assertEquals(messages.size(), 1);
    assertEquals(messages.get(0), "3 seconds passed");
    timeline.update(2.0f);
    assertEquals(messages.size(), 2);
    assertEquals(messages.get(1), "5 seconds passed");
    timeline.update(2.0f);
    timeline.update(2.0f);
    assertEquals(messages.size(), 2);
  }

  @Test public void destroy(){
    Timeline t = new Timeline();
    t.doneEvent.addListener((Timeline tt) -> {});
    t.animDoneEvent.addListener((AnimatableBase a) -> {});
    t.add(new Animatable());

    assertEquals(t.doneEvent.size(), 1);
    assertEquals(t.animDoneEvent.size(), 2);
    assertEquals(t.size(), 1);

    t.destroy();

    assertEquals(t.doneEvent.size(), 0);
    assertEquals(t.animDoneEvent.size(), 0);
    assertEquals(t.size(), 0);
  }

  @Test public void destroy_when_iteration(){
    Timeline t = new Timeline();
    Animatable anim = new Animatable();

    anim.updateEvent.whenTriggered(() -> {
      t.destroy();
    });

    t.add(anim);

    assertEquals(t.size(), 1);
    assertEquals(anim.isActive(), true);
    t.update(0.4f);
    assertEquals(t.size(), 0);
    assertEquals(anim.isActive(), false);
  }
}
