package Scenes;

import java.awt.RenderingHints.Key;
import java.io.Console;

import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import forSlick2D.BasicScene;
import forSlick2D.Director;
import forSlick2D.GameObject;
import prefab.Hero;
import prefab.MainLayer;
import prefab.Bullet;
import prefab.Button;
import prefab.Item;

public class TitleScene extends BasicScene {
		
	int DisplayXmin;		// setViewPointCenter 메소드 실행을 위한 디스플레이 위치정보
	int DisplayXmax;
	int DisplayYmin;
	int DisplayYmax;
	
	float TileSetX;
	float TileSetY;
	MainLayer map;			// 눈에 보이는 배경 이미지  // parent의 존재는 map이동시에 map위에 존재하는 여러 객체들 tile enemy hero등 또한 같이 움직여야 자연스럽기 때문
	Hero hero;		
	Button btn1;
	Item item1;
	Item item2;
	
	
	public TitleScene() 
	{
		// TODO Auto-generated constructor stub
	}

	public TitleScene(int _id) 
	{
		// TODO Auto-generated constructor stub
		
		super(_id);
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException 
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
	public void init(GameContainer arg0, StateBasedGame arg1)throws SlickException 	// init이 왜 호출되는지는 모르겠지만 일단 되기는 된다.
	{
		// TODO Auto-generated method stub

		super.init(arg0, arg1);
		
		Director.getInstance().init();		// 싱글턴 클래스인 director init
		
		Rectangle screen = new Rectangle( 0,0, 1024,768 );	// screen
		
		DisplayXmin = (int)(screen.getWidth() *0.4f);
		DisplayXmax = (int)(screen.getWidth() *0.6f);
		DisplayYmin = (int)(screen.getHeight() *0.4f);
		DisplayYmax = (int)(screen.getHeight() *0.6f);
		
		map = new MainLayer();
		map.initWithImage("rsc/untitled1.png", 0.f, 0.f);			// Tiled 프로그램으로 만든 Png파일
		this.addChild( map,1 );
		
		Director.getInstance().TM.initWithImage("rsc/tile.jpg");	// map뒤편을 채우고 있는 타일들을 세팅
		Director.getInstance().TM.setParent( map );					// map과 타일은 한몸이므로 타일들의 부모를 map으로 세팅
		Director.getInstance().TM.setScene( this, 0 );				// 2번쨰 args는 zOrder		
		
		hero = new Hero();
		hero.initWithImage( "rsc/hero2.jpg", 150, 150 );
		hero.attachParent( map );
		this.addChild( hero,2 );
		
		btn1 = new Button();
		btn1.initWithImage( "rsc/button.png",100,100 );
		btn1.setScaleX(0.2f);
		btn1.setScaleY(0.5f);
		this.addChild( btn1,2 );
		btn1.setTag(0);
		Director.getInstance().BTM.addButton( btn1 );		
		
		Director.getInstance().EM.initWithImage("rsc/enemy2.png");
		Director.getInstance().EM.setParent( map );
		Director.getInstance().EM.setScene( this, 2 );
		
		Director.getInstance().BM.initWithImage("rsc/bullet2.jpg");
		Director.getInstance().BM.setParent(map);
		Director.getInstance().BM.setScene( this, 2 );
		
		Director.getInstance().IM.initWithImage("rsc/tile.png");
		Director.getInstance().IM.setScene( this, 2 );
		Director.getInstance().IM.addChild();
		
		item1 = new Item();
		item1.initWithImage( "rsc/item1.png", 0.f, 0.f, 30.f, 30.f );	// 시험을 위한 어떤 정보도 없는 아이템 생성
		item2 = new Item();
		item2.initWithImage( "rsc/apple.png", 0.f, 0.f, 30.f, 30.f );	// 시험을 위한 어떤 정보도 없는 아이템 생성
		
		Director.getInstance().IM.setItemByIndex(item1, 2, 1);			// 생성한 아이템을 인벤토리에 인덱스를 통해서 추가함
		Director.getInstance().IM.setItemByIndex(item2, 1, 1);
		this.addChild( Director.getInstance().cursor, 2 );				// 커서 생성	
	}	

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)	throws SlickException 
	{
		// TODO Auto-generated method stub

		super.render(arg0, arg1, g);
		
		g.drawString("Total GameObjects = " + GM.getNumOfChildren(), 150, 50);					// 디버깅용으로 좋을듯 사실은 일반 이미지 게임오브젝트처럼 string 오브젝트 만들어서 addchild하는게 정상 개발자가 직접 draw하는건 안좋음
		g.drawString("Able Bullet : "+Director.getInstance().BM.getAbleBulletCount(), 50, 70);
		g.drawString("Disble Bullet : "+Director.getInstance().BM.getDisableBulletCount(), 300, 70);
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2 ) throws SlickException 
	{
		super.update(arg0, arg1, arg2);
		
		float dt = arg2 / 1000.f;
		
		hero.controlMoveByKey( dt );						// 키입력을 통해 hero움직임 제어
		Director.getInstance().TM.checkTileBlock( hero );	// hero와 타일들 간의 충돌 검사 hero가 이동할때만 검사
		
		for( int i = 0 ; i < 10 ; i ++ )
		{
			Director.getInstance().TM.checkTileBlock( Director.getInstance().EM.EnemyArray[i] );			
		}		
		
		for( int j = 0 ; j < 50 ; j ++ )
		{
			Bullet obj = Director.getInstance().BM.BulletArray[j];
			Director.getInstance().EM.checkBulletCollision( obj ); 	// Enemy와 Bullet간의 충돌검사
		}
		
		setViewPointCenter( dt );							// hero가 화면의 일정부분을 넘어가면 map이동을 통해서 hero를 중앙으로 보정 결과적으로 자연스러운 맵 스크롤 효과
		Director.getInstance().BM.checkCollisionWall();		// Bullet이 벽(screen의 끝)과 닿으면 removeChild
	}	
		
	@Override
	public void mouseClicked(int _mouseButton, int _x, int _y, int clickCount ) 
	{
		super.mouseClicked( _mouseButton, _x, _y, clickCount );
		
		
		if( _mouseButton == Input.MOUSE_RIGHT_BUTTON )
		{
			Director.getInstance().TM.switchTileTypeByClick(_mouseButton, _x, _y);	// 우클릭으로 타일 타입을 바꿈
			int btnIdx = Director.getInstance().BTM.checkClicked( GameObject.makePos(_x, _y) );
			
			switch( btnIdx )
			{
			case 0:
				Btn1_Callback();
				break;
			}
		}		
		if( _mouseButton == Input.MOUSE_LEFT_BUTTON )
		{		
			if( Director.getInstance().IM.getIsOpened() ) Director.getInstance().IM.checkMousePos(_mouseButton, _x, _y);
			else hero.attack( GameObject.makePos( _x, _y ));						// 공격방향의 마우스 좌표를 넘긴다.			
		}
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy ) 
	{		
		super.mouseMoved(oldx, oldy, newx, newy);		
		
		// 현재 커서가 아이템을 들고있다면 아이템이 마우스 커서를 따라다니도록 
		if( Director.getInstance().cursor.getKeepItem() == true )Director.getInstance().cursor.setPosition( oldx, oldy );				
	}	
	
	@Override
	public void mouseReleased(int _button, int _x, int _y) 
	{
		// TODO Auto-generated method stub
		
		super.mouseReleased(_button, _x, _y);		
	}
	
	@Override
	public void keyPressed(int key, char c)
	{
		super.keyPressed(key, c);		
		
		// 인벤토리 토글 버튼 오픈이라면 클로즈 클로즈라면 오픈
		
		if( Input.KEY_I == key )
		{
			if( Director.getInstance().IM.getIsOpened() == true )
			{
				Director.getInstance().IM.closeInventory();
			}
			else
			{
				Director.getInstance().IM.openInventory();
			}
		}
	}
	
	@Override
	public void keyReleased(int key, char c)
	{
		super.keyReleased(key, c);		
		
	}
	
	public void Btn1_Callback()
	{
		if( Director.getInstance().IM.getIsOpened() == true )
		{
			Director.getInstance().IM.closeInventory();
		}
		else
		{
			Director.getInstance().IM.openInventory();
		}
	}
	
	public void setViewPointCenter( float dt ) 
	{			
		Vector2f layerPos = map.getPosition();
		
		if( hero.getWorldPos().x < DisplayXmin )	// hero가 좌측방향으로 Xmin값을 넘어갔을때 
		{
			float gapVector = hero.getWorldPos().x - DisplayXmin; // 

			gapVector /= 16;						// 부드럽게 보이기 위해서 초기 값이 가장크고 갈수록 점점 작아짐
			if( Math.abs( gapVector ) > (float)0.1 )
			{
				layerPos.x -= gapVector;
				map.setPosition( layerPos );		//  결과적으로 map을 우측으로 부드럽게 조금씩 이동
			}			
		}
		if( DisplayXmax < hero.getWorldPos().x )	// hero가 우측방향으로 xMax값을 넘어갔을때
		{
			float gapVector = hero.getWorldPos().x - DisplayXmax;

			gapVector /= 16;
			if( Math.abs( gapVector ) > ( float )0.1 )
			{
				layerPos.x -= gapVector;
				map.setPosition( layerPos );
			}			
		}
		if( hero.getWorldPos().y < DisplayYmin )
		{
			float gapVector = hero.getWorldPos().y - DisplayYmin;

			gapVector /= 16;
			if( Math.abs( gapVector ) > ( float )0.1 )
			{
				layerPos.y -= gapVector;
				map.setPosition( layerPos );
			}			
		}
		if( hero.getWorldPos().y > DisplayYmax )
		{
			float gapVector = hero.getWorldPos().y - DisplayYmax;

			gapVector /= 16;
			if( Math.abs( gapVector ) > ( float )0.1 )
			{
				layerPos.y -= gapVector;
				map.setPosition( layerPos );
			}			
		}
	}
	
	/* 아 어느정도 알았다 cocos2d는 함수를 만들고 함수포인터로 등록을 해서 그 함수가 callback되도록 했다면
	 * slick2d는 override 로 부모함수를 재정의 했기 때문에 반드시 부모 함수를 재정의 함수 안에서 반드시 실행시켜야하는듯..
	 * 확실히는 모르겠다.
	 * // TileMap은 Manager인 동시에 Layer 2개를 분리할 필요도 있을것 같다.
	 * 충돌벽으로 인정된 타일을관리하는 리스트를 만들면 시간이 더 적게 걸릴듯
	 * 
	 * 씬에서 모든 객체들간의 통신과 데이터의 처리가 이루어지는데 버튼내부에 콜백함수를 만들수는 없었다.
	 */
}
