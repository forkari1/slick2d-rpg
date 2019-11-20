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
	
	// ���콺 ��ġ�� ��ư���� ��ġ�� ���ؼ� �浹���� �� ��ư�� Ŭ�����θ� �Ǵ�
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
		return -1;		// Ŭ���� ��ư�� ���� ���
	}	
	
	public void addButton( Button btn )
	{
		ButtonList.add( btn );
	}
}
