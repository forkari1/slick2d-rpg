package forSlick2D;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import prefab.Tile;
import prefab.Hero;
import forSlick2D.GameObject;


public class TileManager
{	
	final int 	TILE_WIDTH = 30;
	final int 	TILE_HEIGHT = 30;
	final float TILE_INTERVAL = 32;			// 타일과 타일의 거리
	final float TILE_START_POSITION = 0f;
	
	Tile[][] 	TileSet;
	BasicScene 	scene;
	GameObject 	parent;
	Vector2f 	destVec;
	
	
	// 여길 gameobject 아니면 layer라는 형식의 새로운 부모클래스를 설계....
	/*
	 * 목표.. 자식클래스를 부모에 붙여서 부모가 이동하면 자식도 이동하도록
	 */
	
	public void initWithImage( String imgsrc )
	{		
		destVec = new Vector2f( 0.f, 0.f );		
		
		TileSet = new Tile[TILE_HEIGHT][TILE_WIDTH];
		
		for( int i = 0; i < TILE_HEIGHT ; i ++ )
		{
			for( int j = 0; j < TILE_WIDTH ; j ++ )
			{
				TileSet[i][j] = new Tile();
				TileSet[i][j].initWithImage(imgsrc, (TILE_START_POSITION + (j % TILE_WIDTH) * TILE_INTERVAL)  , (TILE_START_POSITION + (i % TILE_HEIGHT) * TILE_INTERVAL) );
			}			
		}
	}
	
	public void setScene( BasicScene _scene, int zOrder )
	{
		scene = _scene;
		
		for( int i = 0; i < TILE_HEIGHT ; i ++ )
		{
			for( int j = 0; j < TILE_WIDTH ; j ++ )
			{				
				scene.addChild( TileSet[i][j], zOrder );
			}			
		}
	}	
	
	public void setParent( GameObject obj )
	{		
		for( int i = 0; i < TILE_HEIGHT ; i ++ )
		{
			for( int j = 0; j < TILE_WIDTH ; j ++ )
			{
				TileSet[i][j].attachParent( obj );
			}			
		}
	}
	
	// 클릭을 통해 타일의 이동여부를 스위치처럼 껏다 킴
	public void switchTileTypeByClick( int button, int x, int y )
	{
		for( int i = 0 ; i < TILE_WIDTH ; i++ )
		{
			for( int j = 0 ; j < TILE_HEIGHT ; j++ )
			{
				Rectangle tileRect = TileSet[i][j].getRect();
				Vector2f v = new Vector2f();
				v.x = TileSet[i][j].getPosition().x + TileSet[i][j].parent.getPosition().x;
				v.y = TileSet[i][j].getPosition().y + TileSet[i][j].parent.getPosition().y;
				tileRect.setLocation(v);
				
				if( tileRect.contains( x, y ) )
				{
					if( TileSet[i][j].isMoveAbled )
					{
						TileSet[i][j].img = TileSet[i][j].redTile;
						TileSet[i][j].isMoveAbled = false;
					}
					else
					{
						TileSet[i][j].img = TileSet[i][j].whiteTile;
						TileSet[i][j].isMoveAbled = true;
					}
				}
			}
		}
	}
	
	// hero와 타일간의 충돌
	public void checkTileBlock( Hero _hero )
	{
		if( !_hero.isMoving )return;
		for( int i = 0 ; i < TILE_HEIGHT ; i++ )
		{
			for( int j = 0 ; j < TILE_WIDTH ; j++ )
			{
				if( TileSet[i][j].isMoveAbled ) continue;	// 이동가능한 타일이라면 패스 의미없는 반복작업을 피하기위해서 이 코드 때문에 green tile white 타일 변화가 없음
				Rectangle heroRect = GameObject.makeRect( _hero.getPosition(), _hero.rc.getWidth(), _hero.rc.getHeight() );
				Rectangle tileRect = GameObject.makeRect( TileSet[i][j].getPosition(), TileSet[i][j].rc.getWidth(), TileSet[i][j].rc.getHeight() );

				if( heroRect.intersects( tileRect ) )
				{
					if( !TileSet[i][j].isMoveAbled )					// 이동할 수 없는 벽과 충돌시
					{
						Vector2f pos = _hero.getPosition();
						Rectangle Union = GameObject.makeUnionRect(heroRect, tileRect);
												
						if( Union.getWidth() < Union.getHeight() )		// 세로길이가 더 길면 x축  진행중 충돌
						{
							_hero.heroSpeed.x = 0.f;
							if( pos.x <= tileRect.getX() )				// 왼쪽충돌
							{
								pos.x += -( Union.getWidth() + 0.1f );
							}
							else										// 오른쪽 충돌
							{
								pos.x += Union.getWidth() + 0.1f;
							}
						}
						else											// y축 충돌
						{
							_hero.heroSpeed.y = 0.f;
							if( pos.y <= tileRect.getY() )				// 위쪽 충돌
							{
								pos.y += -( Union.getHeight() + 0.1f );
							}
							else										// 아래쪽 충돌
							{
								pos.y += Union.getHeight() + 0.1f;
							}
						}
						_hero.setPosition(pos);
					}
					else
					{
						TileSet[i][j].img = TileSet[i][j].greenTile;;
						_hero.Accelration = 400.f;
					}
				}
				else TileSet[i][j].img = TileSet[i][j].whiteTile;;
				if( !TileSet[i][j].isMoveAbled ) TileSet[i][j].img = TileSet[i][j].redTile;;
			}
		}
	}
	
	// hero가 아닌 다른 오브젝트들과의 충돌검사 이 프로젝트에서는 enemy한정 
	public void checkTileBlock( GameObject _obj )
	{
		for( int i = 0 ; i < TILE_HEIGHT ; i++ )
		{
			for( int j = 0 ; j < TILE_WIDTH ; j++ )
			{
				Rectangle heroRect = GameObject.makeRect( _obj.getPosition(), _obj.rc.getWidth(), _obj.rc.getHeight() );
				Rectangle tileRect = GameObject.makeRect( TileSet[i][j].getPosition(), TileSet[i][j].rc.getWidth(), TileSet[i][j].rc.getHeight() );

				if( heroRect.intersects( tileRect ) )
				{
					if( !TileSet[i][j].isMoveAbled )		// 벽과 충돌시
					{
						Vector2f pos = _obj.getPosition();
						Rectangle Union = GameObject.makeUnionRect(heroRect, tileRect);
												
						if( Union.getWidth() < Union.getHeight() )		// 세로길이가 더 길면 x축  진행중 충돌
						{
							if( pos.x < tileRect.getX() )				// 왼쪽충돌
							{
								pos.x += -( Union.getWidth() + 0.1f );
							}
							else										// 오른쪽 충돌
							{
								pos.x += Union.getWidth() + 0.1f;
							}
						}
						else											// y축 충돌
						{							
							if( pos.y < tileRect.getY() )				//위쪽 충돌
							{
								pos.y += -( Union.getHeight() + 0.1f );
							}
							else										// 아래쪽 충돌
							{
								pos.y += Union.getHeight() + 0.1f;
							}
						}
						_obj.setPosition(pos);
					}
				}
			}
		}
	}	
}
