package com.fuse.anim;

import java.util.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for com.fuse.utils.Event.
 */
public class AnimatableTest extends TestCase
{
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public AnimatableTest( String testName )
  {
      super( testName );
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite()
  {
      return new TestSuite( AnimatableTest.class );
  }

  /**
   * Test Logic
   */
  private float result;
  private List<Float> values;

  public void testApp()
  {
    values = new ArrayList<Float>();

    { // stopAnimatingEvent triggered with final value
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
      assertEquals(anim.val(), 1.5f);
      anim.update(1.0f);
      assertEquals(result, 3.0f);
      assertEquals(anim.val(), 3.0f);
    }

    { // animatable calls update event only once for final update
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
      assertEquals(values.get(0), 0.5f);
      assertEquals(values.get(1), 1.0f);
    }
  }
}
