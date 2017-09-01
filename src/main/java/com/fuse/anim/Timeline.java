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

		this.setInstantiator(() -> {
			return new AnimatableBase();
		});

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

	@Override
	public void destroy(){
		doneEvent.destroy();
		animDoneEvent.destroy();

		for(int idx=this.size()-1; idx>=0; idx--)
			this.get(idx).destroy();

		super.destroy();
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

	public void remove(Timeline other){
		this.stopSync(other);
	}

	/**
	 * Executes callback after specified amount of time.
	 * @param time Amount of time (in seconds) to wait before executing the callback
	 * @param func Callback to execute
	 * @return AnimatableBase The animation that was created internally to perform delay logic
	 */
	public AnimatableBase after(float time, Runnable func){
		AnimatableBase anim = this.create();
		anim.setDuration(time);
		anim.doneEvent.addListener((AnimatableBase a) -> func.run());
		return anim;
	}

	/** @return float The amount of time until the end of this timeline. Considers all animation's durations and delays */
	public float getTimeLeft(){
		List<Float> times = new ArrayList<>();

		each((AnimatableBase anim) -> {
			if(times.isEmpty() || times.get(times.size()-1) < anim.getTimeLeft()){
				times.add(anim.getTimeLeft());
			}
		});

		if(times.isEmpty())
			return 0.0f;

		return times.get(times.size()-1);
	}
}
