package com.spacegamesoftware;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Coin extends Sprite {
	
	private Vector velocity;
	private int value;
	
	public Coin(float pX, float pY, int value) {
		this(pX, pY, ResourceManager.getInstance().coinRegion, ResourceManager.getInstance().vbom);
		this.value = value;
	}
	
	public Coin(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pSpriteVertexBufferObject) {
		super(pX, pY, pTextureRegion, pSpriteVertexBufferObject); 
	}
	
	public int getValue() {
		return value;
	}
	
	public void update() {
		setPosition(mX + velocity.x, mY + velocity.y);
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

}
