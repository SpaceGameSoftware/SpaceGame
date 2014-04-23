package com.spacegamesoftware;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.spacegamesoftware.SceneManager.SceneType;

public class SplashScene extends BaseScene {

	private Sprite splash;
	
	@Override
	public void createScene() {
		splash = new Sprite(0, 0, resourceManager.splashRegion, vbom) {
			
			@Override
			protected void preDraw(GLState glState, Camera camera) {
				super.preDraw(glState, camera);
				glState.enableDither();
			}
			
		};
		
		splash.setScale(1.0f);
		splash.setPosition(0, 200);
		attachChild(splash);
		
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();
		
	}

}
