package prefab;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import forSlick2D.GameObject;

public class Bullet extends GameObject{

	Image img;
	boolean isAbled;
	Vector2f destNormal;
	
	@Override
	public void init()
	{
		super.init();		
		
		destNormal = new Vector2f(0.f, 0.f);				
		try 
		{
			img = new Image("rsc/bullet2.jpg");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void update( int args ) 
	{
		// TODO Auto-generated method stub
		float dt = args / 1000.f;
		if( this.isAbled )
		{
			addPosition(destNormal.x * dt * speed, destNormal.y * dt * speed);	// get set  실질적인 move
		}				
	}
	
	public void setIsAbled( boolean isAbled )
	{
		this.isAbled = isAbled;
	}
	
	public boolean getIsAbled()
	{
		return this.isAbled;
	}
	
	public void setDestNormal( Vector2f _destNormal )
	{
		this.destNormal = _destNormal;
	}
	
	public Vector2f getDestNormal()
	{
		return destNormal;
	}
	
	public void move()
	{
		
	}

	@Override
	public void remove() 
	{
		// TODO Auto-generated method stub
	}
	
}
