package forSlick2D;

import java.util.Vector;
import java.util.Collections;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;


public class GameObjectManager 
{
	Vector<GameObject> node;			//��� GameObject�����ϱ� ���� vector
	
	public void init()
	{
		node = new Vector<GameObject>();
		node.clear();		
	}
	
	// 2d image�� screen�� �׷����°�
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
	{		
		for( int i=0; i < node.size(); i++ )
		{
			GameObject child = node.get(i);
			
			if( child.img != null )	// img�� �����ϸ� �׸��� �ƴϸ� �о�
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
	
	// ���ӳ��� ��� �����͵��� ���� ó��, ����Ǵ°�
	public void update( int dt )
	{		
		for( int i=0; i < node.size(); i++ )
		{
			GameObject temp = node.get(i);
			temp.update(dt);
		}
	}
		
	// zOrder�� ���� sort
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
		child.remove();		// �������� ���� ����Ǵ� ������ �ı��� �Լ�
		node.remove(child);		
	}	

	public int getNumOfChildren()
	{		
		return node.size();		//node�� ���� �＼���� �� �����Ƿ� ������ �˷��ִ� �Լ��� �ʿ��ϴ�
	}
	
	public GameObject getChildByTag(int tag){
		
		//tag�� Ư�� ������Ʈ�� ã�Ƴ� �����ش�. tag�� �ߺ��ȴٸ� ���� ���� �߰ߵ� ������Ʈ�� ���ϰ��� �ȴ�.
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
		
		//name���� Ư�� ������Ʈ�� ã�Ƴ� �����ش�. name�� �ߺ��ȴٸ� ���� ���� �߰ߵ� ������Ʈ�� ���ϰ��� �ȴ�.
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

		//������ tag�� ���� ������Ʈ ����(����)�� �����Ѵ�
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
		//������ name�� ���� ������Ʈ ����(����)�� �����Ѵ�
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
