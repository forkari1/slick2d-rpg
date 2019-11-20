package Main;

import org.newdawn.slick.AppGameContainer;
import forSlick2D.Director;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Scenes.TitleScene;

public class ProjectMain extends StateBasedGame 
{
	
	public static final int STATE_TITLE = 0;
	public static final int STATE_GAME = 1;

	public ProjectMain(String name) 
	{
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException 
	{
		// TODO Auto-generated method stub

		TitleScene titleScene = new TitleScene(STATE_TITLE);
		addState(titleScene);
		//addState(new GameScene(STATE_GAME));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		try{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new ProjectMain("Slick2D Game Project"));
			appgc.setDisplayMode(1024, 768, false);
			appgc.setTargetFrameRate(60);
			appgc.start();
			
		}catch(SlickException e){
			e.printStackTrace();
		}
	}

}
