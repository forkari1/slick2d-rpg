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
	 * x,y,width height�� Enemy generateEnemy
	 * EnemyRoaming
	 * Roaming�� 4������ �������� 1�ʵ��� �����Ÿ��� �̵�
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
	
	// ������ init�� ���������� ������ initwithimage�� �����Ҷ� this.init�� �ִµ� �����Ǹ� �߱⶧���� init�� ȣ���ϴ� �׼��� enemy�� init�� ����Ǵµ�
	/*
	 * 4�ʿ� �ѹ��� �̵������� ��������.
	 * 4�ʿ� �ѹ��� 4�ʵ��� �̵��Ѵ�.	  
	 * �̵��ϰ� ���� ���⺤�Ͱ� 0,0���� �ʱ�ȭ
	 */
	
	@Override
	public void update( int arg2 ) 
	{
		// TODO Auto-generated method stub
		
		float dt = arg2 / 1000.f;
		restTime+= dt;
		
		if( isMoved )		// ���� �̵��� �Ǵ� �̵��ص� �ȴٴ� ���
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
			setEnemyPosition( dt );		// �������� �̵�			
		}
		else
		{
			if( restTime > 3.f )
			{	
				setMoveDirection();		// ���⺤�� ���� 
			}
		}		
	}
	
	// Enemy�� �����̴� ������ ����
	public void setMoveDirection()
	{		
		float dirX = (float)( Math.random() * 2 ) - 1;	// -1 < 1 ������ ���� �򵵷�
		float dirY = (float)( Math.random() * 2 ) - 1;
		
		dirX = Float.parseFloat(String.format("%.1f", dirX) );	// -1 < 1 ������ ������ �򵵷�
		dirY = Float.parseFloat(String.format("%.1f", dirY) );
		
		destNormal.x = dirX;
		destNormal.y = dirY;
						
		restTime = 0.f;
		isMoved = true;		
	}
	
	// set get�� �����Ƽ�
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
