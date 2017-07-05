package com.fuse.anim;

import java.util.List;
import java.util.ArrayList;

import com.fuse.anim.Animatable;

public class Manager {
	private List<Animatable> anims;

	public Manager(){
		anims = new ArrayList<Animatable>();
	}

	public void update(float dt){
		for(int i=anims.size()-1; i>=0; i--){
			Animatable anim = anims.get(i);

			// update the active...
			if(anim.isActive()){
				anim.update(dt);
				continue;
			}

			// ...remove the finished
			anims.remove(i);
		}
	}

	public void add(Animatable anim){
		anims.add(anim);
	}
}
