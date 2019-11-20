package prefab;

import org.newdawn.slick.Image;

import forSlick2D.GameObject;
import prefab.Item;

public class InventoryTile extends GameObject {
	Item item;
	public boolean keepItem;
	
	// �κ��丮 ��ĭ�� Ÿ��
	@Override
	public void init()
	{
		super.init();
		
		item = null;
		keepItem = false;
	}
	
	public void setItem(Item _item)
	{
		item = _item;
		
		if( item != null )
		{
			setImage(item.getImage());
			setSize( 32.f, 32.f );
			keepItem = true;
		}
		else
		{
			setImage("rsc/tile.png");
			keepItem = false;
		}
	}
	
	public Item getItem()
	{
		return item;
	}
	
	@Override
	public void update( int dt ) 
	{
		
	}

	@Override
	public void remove() 
	{
		
	}

}
