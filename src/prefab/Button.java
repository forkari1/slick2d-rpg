package prefab;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import forSlick2D.GameObject;

public class Button extends GameObject{
	public Image idleImage;
	public Image clickImage;	
	
	@Override
	public void init()
	{
		super.init();		
	}
	
	public void initWithImage( String idlesrc, String clicksrc, float x, float y, float width, float height )
	{
		this.init();
		pos = makePos( x, y );
		rc = new Rectangle( x, y, width, height );
		
		try {
			idleImage = new Image( idlesrc );
			clickImage = new Image( clicksrc );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void remove() 
	{
	}
}
