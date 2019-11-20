package forSlick2D;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import prefab.Bullet;
import forSlick2D.GameObject;

public class BulletManager {
	
	public final int 	BULLET_SIZE = 50;
	
	public Bullet[] 	BulletArray;	// �Ѿ��� �����ϴ� �迭
	BasicScene 	scene;					// ��
	GameObject 	parent;					// �θ� ��ü
	Vector2f 	destVec;				// �Ѿ��� ���� ����	
	
	public void initWithImage( String imgsrc )		// �Ѿ��� hero��ġ�� ���ӵǱ� ������ init�Լ��� �Ű������� �ʿ�� ����.
	{		
		destVec = new Vector2f( 0.f, 0.f );		
		BulletArray = new Bullet[BULLET_SIZE];
		
		for( int i = 0; i < BULLET_SIZE ; i ++ )
		{
			BulletArray[i] = new Bullet();
			BulletArray[i].initWithImage(imgsrc, -1000, -1000);		// �ǹ̾��� ��ġ
			BulletArray[i].setSpeed( 500.f );
			BulletArray[i].setIsAbled( false );						// ����ϰ� ���� ���� ����
			BulletArray[i].setScale(0.5f);							// scale�� width height�� ����
		}
	}
	
	public void generateBullet( float x, float y, Vector2f _destNormal )
	{
		// ������ ��ġ�� �������� �Ѿ� ���� ��ġ�� ���� hero�� pos
		// destNormal�� ����ȭ�� ���⺤��
		
		for( int i = 0 ; i < BULLET_SIZE ; i ++ )
		{
			if( BulletArray[i].getIsAbled() == true ) continue; 	// �̹̻������ �Ѿ��� ����
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
	
	// �Ѿ��� ȭ������� ������ ��Ȱ��ȭ
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
	
	// ���� ������� �Ѿ��� ������ ����
	public int getAbleBulletCount()
	{
		int count = 0;
		
		for( int i = 0 ; i < BULLET_SIZE ; i ++ )
		{
			if( BulletArray[i].getIsAbled() ) count++;
		}
		return count;
	}
	
	// ���� �̻������ �Ѿ��� ����
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

