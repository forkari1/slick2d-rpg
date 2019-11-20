package forSlick2D;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import prefab.Bullet;
import forSlick2D.GameObject;

public class BulletManager {
	
	public final int 	BULLET_SIZE = 50;
	
	public Bullet[] 	BulletArray;	// 총알을 관리하는 배열
	BasicScene 	scene;					// 씬
	GameObject 	parent;					// 부모 객체
	Vector2f 	destVec;				// 총알의 방향 벡터	
	
	public void initWithImage( String imgsrc )		// 총알은 hero위치에 종속되기 때문에 init함수의 매개변수일 필요는 없다.
	{		
		destVec = new Vector2f( 0.f, 0.f );		
		BulletArray = new Bullet[BULLET_SIZE];
		
		for( int i = 0; i < BULLET_SIZE ; i ++ )
		{
			BulletArray[i] = new Bullet();
			BulletArray[i].initWithImage(imgsrc, -1000, -1000);		// 의미없는 위치
			BulletArray[i].setSpeed( 500.f );
			BulletArray[i].setIsAbled( false );						// 사용하고 있지 않은 상태
			BulletArray[i].setScale(0.5f);							// scale은 width height의 비율
		}
	}
	
	public void generateBullet( float x, float y, Vector2f _destNormal )
	{
		// 정해진 위치와 방향으로 총알 생성 위치는 보통 hero의 pos
		// destNormal은 정규화된 방향벡터
		
		for( int i = 0 ; i < BULLET_SIZE ; i ++ )
		{
			if( BulletArray[i].getIsAbled() == true ) continue; 	// 이미사용중인 총알은 배제
			BulletArray[i].setIsAbled(true);
			BulletArray[i].setPosition( x, y );
			BulletArray[i].setDestNormal( _destNormal );
			break;
		}
	}
	
	public void setScene( BasicScene _scene, int zOrder )
	{
		scene = _scene;
		
		for( int i = 0; i < BULLET_SIZE ; i ++ )
		{
			scene.addChild( BulletArray[i], zOrder );
		}
	}	
	
	public void setParent( GameObject obj )
	{
		parent = obj;
		
		for( int i = 0; i < BULLET_SIZE ; i ++ )
		{
			BulletArray[i].attachParent( parent );				
		}
	}
	
	// 총알이 화면밖으로 나가면 비활성화
	public void checkCollisionWall()
	{
		for( int i = 0; i < BULLET_SIZE ; i ++ )
		{
			if( !BulletArray[i].getIsAbled() ) continue;
			Vector2f pos = BulletArray[i].getWorldPos();
			if( pos.x < 0 || pos.x > 1024 || pos.y < 0 || pos.y > 768 )
			{
				BulletArray[i].setIsAbled( false );
				BulletArray[i].setPosition( -4000, -4000 );
			}			
		}
	}
	
	// 현재 사용중인 총알의 갯수를 리턴
	public int getAbleBulletCount()
	{
		int count = 0;
		
		for( int i = 0 ; i < BULLET_SIZE ; i ++ )
		{
			if( BulletArray[i].getIsAbled() ) count++;
		}
		return count;
	}
	
	// 현재 미사용중인 총알의 갯수
	public int getDisableBulletCount()
	{
		int count = 0;
		
		for( int i = 0 ; i < BULLET_SIZE ; i ++ )
		{
			if( !BulletArray[i].getIsAbled() ) count++;
		}
		return count;
	}
}

