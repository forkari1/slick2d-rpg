package forSlick2D;

import java.util.Vector;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import prefab.Button;

public class ButtonManager {
	public final int BUTTON_SIZE = 20;

	Vector<Button> ButtonList;
	
	public void init()
	{
		ButtonList = new Vector<Button>();
		ButtonList.clear();		
	}
	
	// 마우스 위치와 버튼간의 위치를 비교해서 충돌여부 즉 버튼의 클릭여부를 판단
	public int checkClicked( Vector2f mousePos )
	{
		for( int i = 0  ; i < ButtonList.size() ; i++ )
		{
			Rectangle rc = ButtonList.get(i).getRect();
			if( rc.contains( mousePos.x, mousePos.y ) )
			{
				return i;
			}			
		}
		return -1;		// 클릭된 버튼이 없을 경우
	}	
	
	public void addButton( Button btn )
	{
		ButtonList.add( btn );
	}
}
