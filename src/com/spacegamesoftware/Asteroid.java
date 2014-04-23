package com.spacegamesoftware;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Asteroid extends Sprite {

	private Vector velocity;
	
	public Asteroid(float pX, float pY) {
		this(pX, pY,
				ResourceManager.getInstance().asteroidRegion[ResourceManager.getInstance().rand.nextInt(4)], 
				ResourceManager.getInstance().vbom);
	}
	
	public Asteroid(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pSpriteVertexBufferObject) {
		super(pX, pY, pTextureRegion, pSpriteVertexBufferObject); 
	}
	
	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}
	
	public void update() {
		setPosition(mX + velocity.x, mY + velocity.y);
	}

}
