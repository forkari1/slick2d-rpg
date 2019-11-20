package prefab;

import org.newdawn.slick.Image;

import forSlick2D.GameObject;

public class Cursor extends GameObject 
{
	Item item;
	boolean keepItem;
	
	@Override
	public void init()
	{
		super.init();
		
		keepItem = false;
		item = null;
	}

	public void setItem(Item _item)
	{
		if( _item != null )
		{
			item = _item;
			setImage(item.getImage());
			setSize( 32.f, 32.f );
			keepItem = true;
		}
		else
		{
			item = null;
			setImage("rsc/tile.png");
			keepItem = false;
		}
	}
	
	public void setKeepItem( boolean _arg )
	{
		keepItem = _arg;
	}
	
	public boolean getKeepItem()
	{
		return keepItem;
	}
	
	public Item getItem()
	{
		return item;
	}
	
	@Override
	public void remove() 
	{
		
	}
}
