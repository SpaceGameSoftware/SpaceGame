package com.spacegamesoftware;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Spaceship extends Sprite  implements IOnSceneTouchListener {

	public Spaceship(float pX, float pY) {
		this(pX, pY, ResourceManager.getInstance().spaceshipRegion, ResourceManager.getInstance().vbom);
	}
	
	public Spaceship(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pSpriteVertexBufferObject) {
		super(pX, pY, pTextureRegion, pSpriteVertexBufferObject);
		
		setScale(1.4f);
	}
	
	private float lastX;

	@Override
	public boolean onSceneTouchEvent(Scene scene, TouchEvent event) {
		float x = event.getX();
		
		if(event.isActionDown()) {
			lastX = x;
			return true;
		}
		else if(event.isActionUp()) {
			return true;
		}
		else if(event.isActionMove()) {
			int w = MainActivity.CAMERA_WIDTH;
			float dx = x - lastX;
			float newX = mX + dx;
			if (newX >= 0 && newX + mWidth < w) {
				setPosition(newX, mY);
				lastX = x;
			}
			return true;
		}
		return false;
	}
	
}
