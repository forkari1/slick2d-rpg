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
	final float TILE_INTERVAL = 32;			// Ÿ�ϰ� Ÿ���� �Ÿ�
	final float TILE_START_POSITION = 0f;
	
	Tile[][] 	TileSet;
	BasicScene 	scene;
	GameObject 	parent;
	Vector2f 	destVec;
	
	
	// ���� gameobject �ƴϸ� layer��� ������ ���ο� �θ�Ŭ������ ����....
	/*
	 * ��ǥ.. �ڽ�Ŭ������ �θ� �ٿ��� �θ� �̵��ϸ� �ڽĵ� �̵��ϵ���
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
	
	// Ŭ���� ���� Ÿ���� �̵����θ� ����ġó�� ���� Ŵ
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
	
	// hero�� Ÿ�ϰ��� �浹
	public void checkTileBlock( Hero _hero )
	{
		if( !_hero.isMoving )return;
		for( int i = 0 ; i < TILE_HEIGHT ; i++ )
		{
			for( int j = 0 ; j < TILE_WIDTH ; j++ )
			{
				if( TileSet[i][j].isMoveAbled ) continue;	// �̵������� Ÿ���̶�� �н� �ǹ̾��� �ݺ��۾��� ���ϱ����ؼ� �� �ڵ� ������ green tile white Ÿ�� ��ȭ�� ����
				Rectangle heroRect = GameObject.makeRect( _hero.getPosition(), _hero.rc.getWidth(), _hero.rc.getHeight() );
				Rectangle tileRect = GameObject.makeRect( TileSet[i][j].getPosition(), TileSet[i][j].rc.getWidth(), TileSet[i][j].rc.getHeight() );

				if( heroRect.intersects( tileRect ) )
				{
					if( !TileSet[i][j].isMoveAbled )					// �̵��� �� ���� ���� �浹��
					{
						Vector2f pos = _hero.getPosition();
						Rectangle Union = GameObject.makeUnionRect(heroRect, tileRect);
												
						if( Union.getWidth() < Union.getHeight() )		// ���α��̰� �� ��� x��  ������ �浹
						{
							_hero.heroSpeed.x = 0.f;
							if( pos.x <= tileRect.getX() )				// �����浹
							{
								pos.x += -( Union.getWidth() + 0.1f );
							}
							else										// ������ �浹
							{
								pos.x += Union.getWidth() + 0.1f;
							}
						}
						else											// y�� �浹
						{
							_hero.heroSpeed.y = 0.f;
							if( pos.y <= tileRect.getY() )				// ���� �浹
							{
								pos.y += -( Union.getHeight() + 0.1f );
							}
							else										// �Ʒ��� �浹
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
	
	// hero�� �ƴ� �ٸ� ������Ʈ����� �浹�˻� �� ������Ʈ������ enemy���� 
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
					if( !TileSet[i][j].isMoveAbled )		// ���� �浹��
					{
						Vector2f pos = _obj.getPosition();
						Rectangle Union = GameObject.makeUnionRect(heroRect, tileRect);
												
						if( Union.getWidth() < Union.getHeight() )		// ���α��̰� �� ��� x��  ������ �浹
						{
							if( pos.x < tileRect.getX() )				// �����浹
							{
								pos.x += -( Union.getWidth() + 0.1f );
							}
							else										// ������ �浹
							{
								pos.x += Union.getWidth() + 0.1f;
							}
						}
						else											// y�� �浹
						{							
							if( pos.y < tileRect.getY() )				//���� �浹
							{
								pos.y += -( Union.getHeight() + 0.1f );
							}
							else										// �Ʒ��� �浹
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
