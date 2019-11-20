package forSlick2D;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import prefab.InventoryTile;
import prefab.Item;
import prefab.Inventory;
import forSlick2D.GameObject;


public class InventoryManager
{	
	final int 	INVEN_TILE_WIDTH = 4;
	final int 	INVEN_TILE_HEIGHT = 4;
	final int 	INVEN_TILE_START_POSITION = 10;
	final int 	INVEN_TILE_INTERVAL = 50;
	
	InventoryTile[][] 	InvenTileSet;
	BasicScene 	scene;
	Inventory inven;	// 인벤토리 이미지
	boolean isOpened;	// 인벤토리가 현재 오픈 되어 있는가?
	
	public void initWithImage( String imgsrc )
	{		
		isOpened = false;
		
		inven = new Inventory();
		inven.initWithImage("rsc/inventory.png", -200.f, -200.f);
		
		InvenTileSet = new InventoryTile[INVEN_TILE_HEIGHT][INVEN_TILE_WIDTH];		
		
		for( int i = 0; i < INVEN_TILE_HEIGHT ; i ++ )
		{
			for( int j = 0; j < INVEN_TILE_WIDTH ; j ++ )
			{
				InvenTileSet[i][j] = new InventoryTile();
				InvenTileSet[i][j].initWithImage(imgsrc, INVEN_TILE_START_POSITION + (j * INVEN_TILE_INTERVAL)  , INVEN_TILE_START_POSITION + (i * INVEN_TILE_INTERVAL) );
				InvenTileSet[i][j].setSize(32.f, 32.f);
				InvenTileSet[i][j].setParent( inven );
			}			
		}
	}
	
	// i,j의 인덱스값으로 인벤토리에 아이템을 세팅
	public void setItemByIndex( Item item, int iIndex, int jIndex )
	{
		InvenTileSet[iIndex][jIndex].setItem(item);
	}
	
	public Item getItemByIndex( int iIndex, int jIndex )
	{
		Item item = InvenTileSet[iIndex][jIndex].getItem();
		
		return item;
	}
	
	public void setScene( BasicScene _scene, int zOrder )
	{
		scene = _scene;		
	}	
	
	public boolean getIsOpened()
	{
		return isOpened;
	}
	
	public void openInventory()
	{
		isOpened = true;
		
		inven.setPosition( 250.f, 250.f );
	}
	
	public void closeInventory()
	{
		isOpened = false;
		
		inven.setPosition( -2500.f, -2500.f );
	}
	
	public void addChild()
	{
		if( scene == null ) return;
		
		scene.addChild(inven, 2);
		
		for( int i = 0; i < INVEN_TILE_HEIGHT ; i ++ )
		{
			for( int j = 0; j < INVEN_TILE_WIDTH ; j ++ )
			{				
				scene.addChild( InvenTileSet[i][j], 2 );
			}			
		}
	}
	
	public void removeChild()
	{
		for( int i = 0; i < INVEN_TILE_HEIGHT ; i ++ )
		{
			for( int j = 0; j < INVEN_TILE_WIDTH ; j ++ )
			{
				scene.removeChild(InvenTileSet[i][j]);
			}			
		}
		scene.removeChild(inven);
		isOpened = false;
	}
	
	// 매개변수로 넘어온 마우스 위치를 통해서 인벤토리가 선택됐는지 여부를 판단
	public void checkMousePos( int button, int x, int y )
	{
		for( int i = 0 ; i < INVEN_TILE_WIDTH ; i++ )
		{
			for( int j = 0 ; j < INVEN_TILE_HEIGHT ; j++ )
			{
				Rectangle tileRect = InvenTileSet[i][j].getRect();
				Vector2f v = new Vector2f();
				v.x = InvenTileSet[i][j].getWorldPos().x;
				v.y = InvenTileSet[i][j].getWorldPos().y;
				tileRect.setLocation(v);
				
				if( tileRect.contains( x, y ) )	 // 인벤토리 타일안에 마우스 위치가 있는가?
				{
					if( Director.getInstance().cursor.getItem() == null )	// 현재 들고 있는 아이템이 없음
					{
						Item item = InvenTileSet[i][j].getItem();
						if( item != null )		// 마우스로 클릭한 인벤토리 타일에 아이템이 있을경우
						{
							Director.getInstance().cursor.setItem( item );	// 마우스로 아이템을 pickup 함
							InvenTileSet[i][j].setItem(null);				// 인벤토리 타일에 있는 아이템은 null
						}
					}
					else
					{
						Item item = InvenTileSet[i][j].getItem();
						if( item == null )	// 인벤토리 타일에 아이템이 없다면 그냥 마우스에 있는 아이템을 둔다.
						{
							InvenTileSet[i][j].setItem( Director.getInstance().cursor.getItem() );
							Director.getInstance().cursor.setItem( null );
						}
						else	// 인벤토리에 아이템이 있는경우 교환
						{
							InvenTileSet[i][j].setItem( Director.getInstance().cursor.getItem() );
							Director.getInstance().cursor.setItem( item );
						}
					}
				}
			}
		}
	}	
}
