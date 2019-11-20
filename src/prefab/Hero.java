package prefab;

import org.newdawn.slick.Input;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import forSlick2D.GameObject;
import forSlick2D.Director;

public class Hero extends GameObject 
{
	public final float MAX_HEROSPEED = 130.f;				
	public float Accelration;		// 가속도
	public Vector2f heroSpeed;		// 현재 속도
	int keyPressCount;				// 키보드를 몇개 눌렀는지 검사
	boolean UpArrow;				// upArrow 눌림
	boolean DownArrow;				
	boolean LeftArrow;
	boolean RightArrow;
	float accrueTime;				// 시간을 계산하기 위한 누적 dt
	public boolean isKeyPressed;	// Key입력이 됐는가
	public boolean isMoving;		// 현재 움직이고 있는중인가?
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
		 * hero width가 hero rect보다 크다고 하면 상관은 없겠지만  ( hero image width - herorect.width ) / 2 
		 */
		drawStartPos = makePos( -1.f, -1.f ); 	// 일종의 offset 그려지는 위치를 보정 현재는 좌표가 좌상단 
		destNormal = makePos( 0.f, 0.f );		// hero의 방향벡터
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
		Vector2f v = getDiffVec( destPos, this.getWorldPos());	// 마우스 좌표와 Hero 위치와의 방향벡터 생성
		Director.getInstance().BM.generateBullet( this.getPosition().x, this.getPosition().y, v );  // 총알 생성
	}
	
	public void controlMoveByKey( float dt )
	{
		if( heroSpeed.x == 0.f && heroSpeed.y == 0.f ) isMoving = false;
		else isMoving = true;
		
		if( UpArrow )
		{
			if( heroSpeed.y > -MAX_HEROSPEED ) heroSpeed.y += ( -1 )*Accelration*dt;	// MAxSpeed를 초과하지 못하도록
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

		if( !LeftArrow && !RightArrow && heroSpeed.x != 0 )	// 정지했을때 자연감속 똥피하기 처럼
		{
			if( heroSpeed.x > 0 ) heroSpeed.x -= Accelration * dt;
			if( heroSpeed.x < 0 ) heroSpeed.x += Accelration * dt;
			if( Math.abs( heroSpeed.x ) < 4.f )
			{
				heroSpeed.x = 0.f;	// int로 나누어서 0사이에서 왔다갔다하는 현상을 막음 ex> 0.1 / 1 은 0
			}
		}
		if( !UpArrow && !DownArrow && heroSpeed.y != 0 )	// 자연 감속 처리
		{
			if( heroSpeed.y > 0 ) heroSpeed.y -= Accelration*dt;
			if( heroSpeed.y < 0 ) heroSpeed.y += Accelration*dt;
			if( Math.abs( heroSpeed.y ) < 4.f )
			{
				heroSpeed.y = 0.f;	// int로 나누어서 0사이에서 왔다갔다하는 현상을 막음 ex> 0.1 / 1 은 0
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
