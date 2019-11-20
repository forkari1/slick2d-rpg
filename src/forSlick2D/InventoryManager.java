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
	Inventory inven;	// �κ��丮 �̹���
	boolean isOpened;	// �κ��丮�� ���� ���� �Ǿ� �ִ°�?
	
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
	
	// i,j�� �ε��������� �κ��丮�� �������� ����
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
	
	// �Ű������� �Ѿ�� ���콺 ��ġ�� ���ؼ� �κ��丮�� ���õƴ��� ���θ� �Ǵ�
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
				
				if( tileRect.contains( x, y ) )	 // �κ��丮 Ÿ�Ͼȿ� ���콺 ��ġ�� �ִ°�?
				{
					if( Director.getInstance().cursor.getItem() == null )	// ���� ��� �ִ� �������� ����
					{
						Item item = InvenTileSet[i][j].getItem();
						if( item != null )		// ���콺�� Ŭ���� �κ��丮 Ÿ�Ͽ� �������� �������
						{
							Director.getInstance().cursor.setItem( item );	// ���콺�� �������� pickup ��
							InvenTileSet[i][j].setItem(null);				// �κ��丮 Ÿ�Ͽ� �ִ� �������� null
						}
					}
					else
					{
						Item item = InvenTileSet[i][j].getItem();
						if( item == null )	// �κ��丮 Ÿ�Ͽ� �������� ���ٸ� �׳� ���콺�� �ִ� �������� �д�.
						{
							InvenTileSet[i][j].setItem( Director.getInstance().cursor.getItem() );
							Director.getInstance().cursor.setItem( null );
						}
						else	// �κ��丮�� �������� �ִ°�� ��ȯ
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
