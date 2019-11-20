package prefab;

import org.newdawn.slick.Image;

import forSlick2D.GameObject;

public class Tile extends GameObject {
	
	public boolean isMoveAbled;
	public Image greenTile;
	public Image redTile;		// 이동 불가능한 타일 색깔
	public Image whiteTile;		

	@Override
	public void init()
	{
		super.init();
		
		isMoveAbled = true;		// 이동가능한 타일
		
		try 
		{
			redTile = new Image("rsc/redtile.jpg");
			greenTile = new Image("rsc/greentile.jpg");
			whiteTile = new Image("rsc/tile.jpg");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
