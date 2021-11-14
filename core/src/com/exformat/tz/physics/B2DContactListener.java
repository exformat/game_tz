package com.exformat.tz.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.exformat.tz.GameConstants;
import com.exformat.tz.game_objects.Player;
import com.exformat.tz.managers.AudioManager;

public class B2DContactListener implements ContactListener
{
	@Override
	public void beginContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		if(a.getFilterData().categoryBits == GameConstants.CATEGORY_PLATFORM ||
		   b.getFilterData().categoryBits == GameConstants.CATEGORY_PLATFORM){
				AudioManager.collision.play();
			    Player.contactPlatform = true;
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		if(a.getFilterData().categoryBits == GameConstants.CATEGORY_PLATFORM || 
		   b.getFilterData().categoryBits == GameConstants.CATEGORY_PLATFORM){
			Player.contactPlatform = false;
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold manifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}
}
