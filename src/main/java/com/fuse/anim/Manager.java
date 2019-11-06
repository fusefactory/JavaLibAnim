package com.fuse.anim;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.fuse.utils.Event;

public class Manager {
	private List<Animatable> anims;
	public Event<Manager> doneEvent;
	public Event<Animatable> itemDoneEvent;

	public Manager(){
		anims = new ArrayList<Animatable>();
		doneEvent = new Event<>();
		itemDoneEvent = new Event<>();
	}

	public void update(float dt){
		for(int i=anims.size()-1; i>=0; i--){
			Animatable anim = anims.get(i);

			// update the active...
			if(anim.isActive()){
				anim.update(dt);

				// still active? leave it, otherwise remove it
				if(anim.isActive())
					continue;
			}

			// ...remove the finished
			itemDoneEvent.trigger(anim);
			anims.remove(i);
		}

		if(isDone()){
			doneEvent.trigger(this);
		}
	}

	public boolean isDone(){
		return anims.isEmpty();
	}

	public void add(Animatable anim){
		anims.add(anim);
	}

	public Manager whenAllDone(Runnable func){
		Consumer<Manager> consumer = (Manager man) -> {
			func.run();
		};

		doneEvent.addListener(consumer);

		if(isDone())
			func.run();

		return this;
	}
}
