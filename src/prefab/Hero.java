package prefab;

import org.newdawn.slick.Input;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import forSlick2D.GameObject;
import forSlick2D.Director;

public class Hero extends GameObject 
{
	public final float MAX_HEROSPEED = 130.f;				
	public float Accelration;		// ���ӵ�
	public Vector2f heroSpeed;		// ���� �ӵ�
	int keyPressCount;				// Ű���带 � �������� �˻�
	boolean UpArrow;				// upArrow ����
	boolean DownArrow;				
	boolean LeftArrow;
	boolean RightArrow;
	float accrueTime;				// �ð��� ����ϱ� ���� ���� dt
	public boolean isKeyPressed;	// Key�Է��� �ƴ°�
	public boolean isMoving;		// ���� �����̰� �ִ����ΰ�?
	Vector2f destNormal;	
	
	public Hero()
	{		
	}
	
	@Override
	public void init()
	{
		super.init();
		
		Accelration = 400.f;
		heroSpeed = GameObject.makePos( 0.f, 0.f );
		
		isKeyPressed = false;
		isMoving = false;
		keyPressCount = 0;
		UpArrow = false;
		DownArrow = false;
		LeftArrow = false;
		RightArrow = false;
		accrueTime = 0.f;
		
		/*
		 * hero width�� hero rect���� ũ�ٰ� �ϸ� ����� ��������  ( hero image width - herorect.width ) / 2 
		 */
		drawStartPos = makePos( -1.f, -1.f ); 	// ������ offset �׷����� ��ġ�� ���� ����� ��ǥ�� �»�� 
		destNormal = makePos( 0.f, 0.f );		// hero�� ���⺤��
	}
	
	@Override
	public void update( int arg2 ) 
	{
		// TODO Auto-generated method stub
		
		if( !isMoving ) return;
		float dt = arg2 / 1000.f;
		this.addPosition( heroSpeed.x * dt, heroSpeed.y * dt );
	}
	
	public void attack( Vector2f destPos )
	{		
		Vector2f v = getDiffVec( destPos, this.getWorldPos());	// ���콺 ��ǥ�� Hero ��ġ���� ���⺤�� ����
		Director.getInstance().BM.generateBullet( this.getPosition().x, this.getPosition().y, v );  // �Ѿ� ����
	}
	
	public void controlMoveByKey( float dt )
	{
		if( heroSpeed.x == 0.f && heroSpeed.y == 0.f ) isMoving = false;
		else isMoving = true;
		
		if( UpArrow )
		{
			if( heroSpeed.y > -MAX_HEROSPEED ) heroSpeed.y += ( -1 )*Accelration*dt;	// MAxSpeed�� �ʰ����� ���ϵ���
		}
		if( DownArrow )
		{
			if( heroSpeed.y < MAX_HEROSPEED ) heroSpeed.y += Accelration*dt;
		}
		if( LeftArrow )
		{
			if( heroSpeed.x > -MAX_HEROSPEED ) heroSpeed.x += ( -1 )*Accelration*dt;
		}
		if( RightArrow )
		{
			if( heroSpeed.x < MAX_HEROSPEED ) heroSpeed.x += Accelration*dt;
		}

		if( !LeftArrow && !RightArrow && heroSpeed.x != 0 )	// ���������� �ڿ����� �����ϱ� ó��
		{
			if( heroSpeed.x > 0 ) heroSpeed.x -= Accelration * dt;
			if( heroSpeed.x < 0 ) heroSpeed.x += Accelration * dt;
			if( Math.abs( heroSpeed.x ) < 4.f )
			{
				heroSpeed.x = 0.f;	// int�� ����� 0���̿��� �Դٰ����ϴ� ������ ���� ex> 0.1 / 1 �� 0
			}
		}
		if( !UpArrow && !DownArrow && heroSpeed.y != 0 )	// �ڿ� ���� ó��
		{
			if( heroSpeed.y > 0 ) heroSpeed.y -= Accelration*dt;
			if( heroSpeed.y < 0 ) heroSpeed.y += Accelration*dt;
			if( Math.abs( heroSpeed.y ) < 4.f )
			{
				heroSpeed.y = 0.f;	// int�� ����� 0���̿��� �Դٰ����ϴ� ������ ���� ex> 0.1 / 1 �� 0
			}
		}
	}	
	
	@Override
	public void keyPressed(int key, char c)
	{
		if( keyPressCount >= 3 ) return;
		
		switch( key )
		{
		case Input.KEY_W:
			UpArrow = true;
			break;
		
		case Input.KEY_S:
			DownArrow = true;
			break;
		
		case Input.KEY_A:
			LeftArrow = true;
			break;
		
		case Input.KEY_D:
			RightArrow = true;
			break;
		}
		
		keyPressCount++;
		isKeyPressed = true;		
	}
	
	@Override
	public void keyReleased(int key, char c)
	{
		switch( key )
		{
		case Input.KEY_W:
			UpArrow = false;
			break;
		
		case Input.KEY_S:
			DownArrow = false;
			break;
		
		case Input.KEY_A:
			LeftArrow = false;
			break;
		
		case Input.KEY_D:
			RightArrow = false;
			break;
		}
		
		keyPressCount--;
		if( keyPressCount == 0 ) isKeyPressed = false;
	}

	@Override
	public void remove() 
	{
		// TODO Auto-generated method stub

	}

}
