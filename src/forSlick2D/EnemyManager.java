package forSlick2D;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import prefab.Bullet;
import prefab.Enemy;
import prefab.Hero;

public class EnemyManager 
{
	public Enemy[]	EnemyArray;		// 에너미를 담고 있는 배열
	BasicScene 	scene;
	GameObject 	parent;				// 이 프로젝트의 parent는 MainLayer map
	public final int ENEMY_SIZE = 10;
	
		
	public void initWithImage( String imgsrc )
	{
		EnemyArray = new Enemy[ENEMY_SIZE];		
		
		for( int i = 0 ; i < 10 ; i ++ )
		{
			EnemyArray[i] = new Enemy();
			EnemyArray[i].initWithImage( imgsrc, (float)(Math.random()*300 + 300)  , (float)(Math.random()*300 + 300) );
			EnemyArray[i].setRectangle( GameObject.makeRect(EnemyArray[i].getPosition(), 30.f, 30.f) );
		}
	}	
	
	public void setScene( BasicScene _scene, int zOrder )
	{
		scene = _scene;
		
		for( int i = 0; i < ENEMY_SIZE ; i ++ )
		{
			scene.addChild( EnemyArray[i], zOrder );			
		}
	}	
	
	public void setParent( GameObject obj )
	{		
		for( int i = 0; i < ENEMY_SIZE ; i ++ )
		{
			EnemyArray[i].attachParent( obj );
		}
	}
	
	public void checkCollision( Hero _hero )
	{
		// hero와 충돌 검사 미구현
	}
	
	// 총알과 Enemy의 충돌을 검사
	public void checkBulletCollision( Bullet obj )	
	{
		Vector2f v = obj.getPosition();
		for( int i = 0 ; i < ENEMY_SIZE; i++ )
		{
			Rectangle tileRect = new Rectangle( EnemyArray[i].getPosition().getX(), EnemyArray[i].getPosition().getY(), EnemyArray[i].getRect().getWidth(), EnemyArray[i].getRect().getHeight() );
			if( tileRect.contains( v.x, v.y ) )
			{
				EnemyArray[i].setPosition( -100, -100 );
				scene.removeChild( EnemyArray[i] );				
				obj.setIsAbled(false);
				obj.setPosition(-400, -400);
			}
		}
	}
}
