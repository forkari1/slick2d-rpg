package forSlick2D;

import java.util.Vector;
import java.util.Collections;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;


public class GameObjectManager 
{
	Vector<GameObject> node;			//모든 GameObject관리하기 위한 vector
	
	public void init()
	{
		node = new Vector<GameObject>();
		node.clear();		
	}
	
	// 2d image가 screen에 그려지는곳
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
	{		
		for( int i=0; i < node.size(); i++ )
		{
			GameObject child = node.get(i);
			
			if( child.img != null )	// img가 존재하면 그리고 아니면 패쓰
			{
				if( child.getParent() != null )
				{
					child.img.draw(child.drawStartPos.x+child.getWorldPos().x, child.drawStartPos.y+child.getWorldPos().y,child.getRect().getWidth(),child.getRect().getHeight());
				}
				else
				{
					child.img.draw(child.drawStartPos.x+child.getPosition().x, child.getPosition().y, child.drawStartPos.y+child.getRect().getWidth(), child.getRect().getHeight());
				}				
			}					
		}
	}
	
	// 게임내의 모든 데이터들의 값이 처리, 연산되는곳
	public void update( int dt )
	{		
		for( int i=0; i < node.size(); i++ )
		{
			GameObject temp = node.get(i);
			temp.update(dt);
		}
	}
		
	// zOrder를 위한 sort
	public void sortChildByzOrder()
	{
		for( int i=0; i < node.size()-1; i++ )
		{
			GameObject child1 = node.get(i);
			for( int j=i+1; j < node.size(); j++ )
			{				
				GameObject child2 = node.get(j);
				
				if( child1.getzOrder() > child2.getzOrder() )
				{
					Collections.swap(node, i, j);
				}
			}
		}
	}
	
	public void addChild( GameObject child )
	{
		for( GameObject obj : node )
		{
			if( obj.equals(child) ) return;
		}
		
		node.add( child );						
	}
	
	public void addChild( GameObject child, int zOrder )
	{
		this.addChild(child);
		
		child.setzOrder( zOrder );
		sortChildByzOrder();		
	}
		
	public void removeChild(GameObject child)
	{
		child.remove();		// 지워지기 전에 실행되는 일종의 파괴자 함수
		node.remove(child);		
	}	

	public int getNumOfChildren()
	{		
		return node.size();		//node에 직접 억세스할 수 없으므로 갯수를 알려주는 함수가 필요하다
	}
	
	public GameObject getChildByTag(int tag){
		
		//tag로 특정 오브젝트를 찾아내 돌려준다. tag가 중복된다면 제일 먼저 발견된 오브젝트가 리턴값이 된다.
		GameObject ret = null;
		
		for(int i=0;i<node.size();i++)
		{
			GameObject temp = node.get(i);
			if( temp.getTag() == tag ){				
				ret = temp;
				break;
			}
		}		
		return ret;
	}
	
	public GameObject getChildByName(String name)
	{
		
		//name으로 특정 오브젝트를 찾아내 돌려준다. name이 중복된다면 제일 먼저 발견된 오브젝트가 리턴값이 된다.
		GameObject ret = null;
		for(int i=0;i<node.size();i++)
		{
			GameObject temp = node.get(i);
			if( temp.name.equals(name) )
			{				
				ret = temp;
				break;
			}
		}		
		return ret;
	}
	
	public Vector<GameObject> getChildrenByTag(int tag)
	{

		//동일한 tag를 갖는 오브젝트 묶음(벡터)을 리턴한다
		Vector<GameObject> ret = new Vector<GameObject>(); 
		
		for(int i=0;i<node.size();i++)
		{
			GameObject temp = node.get(i);
			if( temp.tag==tag )
			{				
				ret.add(temp);
			}
		}
		
		return ret;
	}
	public Vector<GameObject> getChildrenByName(String name)
	{
		//동일한 name을 갖는 오브젝트 묶음(벡터)을 리턴한다
		Vector<GameObject> ret = new Vector<GameObject>(); 
		
		for(int i=0;i<node.size();i++)
		{
			GameObject temp = node.get(i);
			if( temp.name.equals(name) )
			{				
				ret.add(temp);
			}
		}		
		return ret;
	}
	
	public void keyPressed(int key, char c)
	{
		for( int i=0; i < node.size(); i++ )
		{
			GameObject temp = node.get(i);
			temp.keyPressed(key, c);
		}
	}
	
	public void keyReleased(int key, char c)
	{
		for( int i=0; i < node.size(); i++ )
		{
			GameObject temp = node.get(i);
			temp.keyReleased(key, c);
		}
	}
}
