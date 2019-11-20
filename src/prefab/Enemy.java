package prefab;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import forSlick2D.GameObject;

public class Enemy extends GameObject {

	
	int keyPressCount;
	float restTime;
	float moveTime;
	boolean isMoved;
	boolean isAbled;
	
	Vector2f destNormal;	
	
	/*
	 * x,y,width height에 Enemy generateEnemy
	 * EnemyRoaming
	 * Roaming은 4방향중 랜덤으로 1초동안 일정거리를 이동
	 */
	
	public Enemy()
	{
		
	}
	
	@Override
	public void init()
	{
		super.init();
		
		isMoved = false;
		isAbled = false;
		speed = 50.f;
		moveTime = 0.f;
		restTime = 0.f;
		destNormal = makePos( 0.f, 0.f );
		
	}
	
	// 실제로 init을 실행하지는 않지만 initwithimage를 실행할때 this.init이 있는데 재정의를 했기때문에 init을 호출하는 그순간 enemy의 init이 실행되는듯
	/*
	 * 4초에 한번씩 이동방향이 정해진다.
	 * 4초에 한번씩 4초동안 이동한다.	  
	 * 이동하고 나면 방향벡터가 0,0으로 초기화
	 */
	
	@Override
	public void update( int arg2 ) 
	{
		// TODO Auto-generated method stub
		
		float dt = arg2 / 1000.f;
		restTime+= dt;
		
		if( isMoved )		// 현재 이동중 또는 이동해도 된다는 허락
		{
			if( moveTime > 2.f )
			{
				moveTime = 0.f;
				restTime = 0.f;
				destNormal.x = 0;
				destNormal.y = 0;
				isMoved = false;
			}
			moveTime += dt;
			setEnemyPosition( dt );		// 실질적인 이동			
		}
		else
		{
			if( restTime > 3.f )
			{	
				setMoveDirection();		// 방향벡터 설정 
			}
		}		
	}
	
	// Enemy가 움직이는 방향을 결정
	public void setMoveDirection()
	{		
		float dirX = (float)( Math.random() * 2 ) - 1;	// -1 < 1 사이의 값을 얻도록
		float dirY = (float)( Math.random() * 2 ) - 1;
		
		dirX = Float.parseFloat(String.format("%.1f", dirX) );	// -1 < 1 사이의 값ㅇㄹ 얻도록
		dirY = Float.parseFloat(String.format("%.1f", dirY) );
		
		destNormal.x = dirX;
		destNormal.y = dirY;
						
		restTime = 0.f;
		isMoved = true;		
	}
	
	// set get이 귀찮아서
	public void setEnemyPosition( float dt )
	{
		Vector2f enemyPos = this.getPosition();
		enemyPos.x += destNormal.x * dt * speed;
		enemyPos.y += destNormal.y * dt * speed;
		this.setPosition( enemyPos );
	}
		
	@Override
	public void remove() 
	{
		// TODO Auto-generated method stub

	}
	

}
