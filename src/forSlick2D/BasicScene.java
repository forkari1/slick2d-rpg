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
	protected StateBasedGame game;	//BasicGameState 처리를 위한 변수
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
		
		GM.init();	// GameObject 초기화 씬내의 내부 작업들을 모두 GM이 처리
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
 * 왜 개발자가 게임오브젝트 관리 시스템을 씬에 귀속시킨지 알것 같다.
 *  게임오브젝트가 씬에 있어야지 씬이 사라지면 다른씬에서 새로운 오브젝트가 ㅐ워지는데
 *  이렇게 되면 다른씬이 되도 전에 있떤 씬의 오브젝트가 그대로 있음
 *  하지만 그런자잘한거 생각말고 일단 제발 그냥 하자
 *  
 *  180505
 *  // 이제 좀 알았다. GOM 는 독립적이지만 사실은 Scene에 종속되어서 있기 때문에 Scene만 바꾸면 자연스럽게 그려지는 Object도 달라짐
 *  씬에 구조가 단순해져서 보기 편해졌을뿐 원래 구조대로 씬에서 게임오브젝트를 관리 하는게 맞는거 같다.
 *  만든사람이 gameobject에 render구현 한건 아닌거 같다. 모든 자잘한 클래스에 모두 쓰는건 비효율적
 * *
 */
