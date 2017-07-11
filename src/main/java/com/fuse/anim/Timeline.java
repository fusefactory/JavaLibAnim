package com.fuse.anim;

import com.fuse.utils.Event;
import com.fuse.cms.Collection;

import java.util.function.Consumer;
import java.util.List;
import java.util.ArrayList;

import com.fuse.anim.Animatable;

public class Timeline extends Collection<AnimatableBase> {
	public Event<Timeline> doneEvent;
	public Event<AnimatableBase> animDoneEvent;

	public Timeline(){
		doneEvent = new Event<>();
		animDoneEvent = new Event<>();

		this.addEvent.addListener((AnimatableBase newAnim) -> {
			if(!newAnim.isActive())
				newAnim.start();
		}, this);
	}

	public void update(float dt){
		boolean doneBefore = isDone();
		this.each((AnimatableBase anim) -> {
			// update the active...
			if(anim.isActive()){
				anim.update(dt);

				// still active? leave it, otherwise remove it
				if(anim.isActive())
					return;
			}

			// remove the finished animation
			animDoneEvent.trigger(anim);
			remove(anim);
		});

		if(isDone() && !doneBefore){
			doneEvent.trigger(this);
		}
	}

	public boolean isDone(){
		return isEmpty();
	}

	public Timeline whenAllDone(Runnable func){
		Consumer<Timeline> consumer = (Timeline man) -> {
			func.run();
		};

		doneEvent.addListener(consumer);

		if(isDone())
			func.run();

		return this;
	}
}
