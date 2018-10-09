package com.fuse.anim.example;

// JRE
import java.util.List;
import java.util.ArrayList;
// processing
import processing.core.*;
// local
import com.fuse.anim.*;
import com.fuse.anim.curves.*;

public class App extends PApplet {

  private PApplet papplet;
  private PGraphics pg;
  private int tLastFrame = 0;

  private List<Curve> curves1 = new ArrayList<>(),
                      curves2 = new ArrayList<>();
  private float curveTimer = 0.0f;
  private float curveTimerDuration = 1.0f;
  private float curveSpacing = 10.0f;
  private float curveSize  = 120.0f;

  public static void main( String[] args ){
    PApplet.main("com.fuse.anim.example.App");
  }

  public App(){
    super();
    papplet = this;
  }

  public void settings(){
    papplet.size(530, 270, P3D);
  }

  public void setup(){
    papplet.frameRate(30.0f);
    this.pg = papplet.createGraphics(papplet.width, papplet.height, P3D);
    //this.pg.clear(0);

    curves1.add(new LinearCurve());
    curves1.add(new QuadraticEaseOutCurve());
    curves1.add(new EaseOutBounce());
    curves1.add(new EaseOutElastic());

    curves2.add(new LinearCurve().setMirrorLeft(true));
    curves2.add(new QuadraticEaseOutCurve().setMirrorLeft(true));
    curves2.add(new EaseOutBounce().setMirrorLeft(true));
    curves2.add(new EaseOutElastic().setMirrorLeft(true));

    this.drawCurves(this.pg, curves1, curveSpacing);
    this.drawCurves(this.pg, curves2, curveSpacing + curveSize + curveSpacing);

    tLastFrame = papplet.millis();
  }

  public void update(float dt){
    curveTimer = (curveTimer + (1.0f / curveTimerDuration) * dt) % 1.0f;
  }

  public void draw(){
    int t = papplet.millis();
    float dt = (float)(t - this.tLastFrame) / 1000.0f;
    this.update(dt);
    this.tLastFrame = t;

    papplet.background(0);
    papplet.clear();
    papplet.image(pg, 0f,0f);

    this.drawCurveTimers(curves1, curveSpacing);
    this.drawCurveTimers(curves2, curveSpacing + curveSize + curveSpacing);
  }

  public void keyPressed(){
    switch(key){
      case 'd': {
      }
    }
  }

  private void drawCurves(PGraphics pg, List<Curve> curves, float y){
    float x = curveSpacing;
    float curveStep = 1.0f / curveSize;
    float bottom = y+curveSize;

    pg.beginDraw();
    {
      for(Curve c : curves){
        // curve background/frame
        pg.noStroke();
        pg.fill(pg.color(255,20));
        pg.rect(x,bottom-curveSize,curveSize,curveSize);

        // curve foreground/curve
        pg.noStroke();
        pg.fill(pg.color(255,150));

        for(float curveX=0.0f; curveX<=1.0f; curveX += curveStep){
          float height = c.get(curveX) * curveSize;
          pg.rect(x, bottom - height,
                  1.0f, height);

          x+=1.0f;
        }

        x += curveSpacing;
      }
    }
    pg.endDraw();
  }

  private void drawCurveTimers(List<Curve> curves, float y){
    float x = curveSpacing;
    float bottom = y+curveSize;

    papplet.noStroke();
    papplet.fill(papplet.color(255,0,0,150));
    papplet.ellipseMode(CENTER);

    for(Curve c : curves){
      float val = c.get(this.curveTimer);
      float curveX = val*curveSize;
      val = c.get(c.get(this.curveTimer));
      papplet.ellipse(x+curveX, bottom, 10.0f, 10.0f);
      papplet.ellipse(x+curveX, bottom-val*curveSize, 10.0f, 10.0f);
      x += curveSize + curveSpacing;
    }
  }
}
