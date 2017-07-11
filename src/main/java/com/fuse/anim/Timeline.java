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
			// we'll notify our listener when any of our anims completes
			this.animDoneEvent.forward(newAnim.doneEvent);

			// auto-start added animations
			if(!newAnim.isActive())
				newAnim.start();
		}, this);

		this.animDoneEvent.addListener((AnimatableBase doneAnim) -> {
			if(this.size() == 1){
				// our only animation just finished
				this.doneEvent.trigger(this);
			}
		});
	}

	public void update(float dt){
		this.each((AnimatableBase anim) -> {
			// update the active...
			if(anim.isActive()){
				anim.update(dt);

				// animation just finished? remove it
				if(!anim.isActive())
					remove(anim);
			}
		});
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

	public void add(Timeline other){
		this.sync(other);
	}
}
