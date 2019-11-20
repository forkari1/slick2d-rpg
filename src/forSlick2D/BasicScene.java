package forSlick2D;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import forSlick2D.GameObjectManager;;

public class BasicScene extends BasicGameState 
{
	public GameObjectManager GM = new GameObjectManager();
	protected StateBasedGame game;	//BasicGameState ó���� ���� ����
	protected GameContainer gc;
	int id = -1;
	
	public BasicScene() 
	{		
			
	}
	
	public BasicScene( int id ) 
	{
		this();		
		
		this.id = id;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException 
	{
		game = arg1;
		gc = arg0;
		
		GM.init();	// GameObject �ʱ�ȭ ������ ���� �۾����� ��� GM�� ó��
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException 
	{
		// TODO Auto-generated method stub
		GM.render( arg0, arg1, arg2 );
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException 
	{
		GM.update( arg2 );				
	}

	@Override
	public int getID() 
	{		
		return id;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)	throws SlickException 
	{
		// TODO Auto-generated method stub
		super.enter(container, game);
	}
	@Override
	public void leave(GameContainer container, StateBasedGame game)	throws SlickException 
	{
		// TODO Auto-generated method stub
		super.leave(container, game);
	}
	
	@Override
	public void keyPressed(int key, char c)
	{
		GM.keyPressed(key, c);
	}
	
	@Override
	public void keyReleased(int key, char c)
	{
		GM.keyReleased(key, c);
	}
	
	public void addChild(GameObject child){
		
		GM.addChild(child);
	}
	
	public void addChild(GameObject child, int zOrder){
		
		GM.addChild(child, zOrder);
	}
	
	public void removeChild(GameObject child){

		GM.removeChild(child);
	}	
}

/*
 * �� �����ڰ� ���ӿ�����Ʈ ���� �ý����� ���� �ͼӽ�Ų�� �˰� ����.
 *  ���ӿ�����Ʈ�� ���� �־���� ���� ������� �ٸ������� ���ο� ������Ʈ�� �������µ�
 *  �̷��� �Ǹ� �ٸ����� �ǵ� ���� �ֶ� ���� ������Ʈ�� �״�� ����
 *  ������ �׷������Ѱ� �������� �ϴ� ���� �׳� ����
 *  
 *  180505
 *  // ���� �� �˾Ҵ�. GOM �� ������������ ����� Scene�� ���ӵǾ �ֱ� ������ Scene�� �ٲٸ� �ڿ������� �׷����� Object�� �޶���
 *  ���� ������ �ܼ������� ���� ���������� ���� ������� ������ ���ӿ�����Ʈ�� ���� �ϴ°� �´°� ����.
 *  �������� gameobject�� render���� �Ѱ� �ƴѰ� ����. ��� ������ Ŭ������ ��� ���°� ��ȿ����
 * *
 */
