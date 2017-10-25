package com.fuse.anim;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Ignore;

import java.util.*;

public class CurveTest {

  @Test public void get(){
    Curve c = new Curve();
    assertEquals(c.get(0.4f), 0.4f, 0.0000001f);
    assertEquals(c.get(1.4f), 1.4f, 0.0000001f);
    assertEquals(c.get(-21.4f), -21.4f, 0.0000001f);
  }
}
