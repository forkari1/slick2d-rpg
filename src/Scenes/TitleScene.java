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
		
	int DisplayXmin;		// setViewPointCenter �޼ҵ� ������ ���� ���÷��� ��ġ����
	int DisplayXmax;
	int DisplayYmin;
	int DisplayYmax;
	
	float TileSetX;
	float TileSetY;
	MainLayer map;			// ���� ���̴� ��� �̹���  // parent�� ����� map�̵��ÿ� map���� �����ϴ� ���� ��ü�� tile enemy hero�� ���� ���� �������� �ڿ������� ����
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
	public void init(GameContainer arg0, StateBasedGame arg1)throws SlickException 	// init�� �� ȣ��Ǵ����� �𸣰����� �ϴ� �Ǳ�� �ȴ�.
	{
		// TODO Auto-generated method stub

		super.init(arg0, arg1);
		
		Director.getInstance().init();		// �̱��� Ŭ������ director init
		
		Rectangle screen = new Rectangle( 0,0, 1024,768 );	// screen
		
		DisplayXmin = (int)(screen.getWidth() *0.4f);
		DisplayXmax = (int)(screen.getWidth() *0.6f);
		DisplayYmin = (int)(screen.getHeight() *0.4f);
		DisplayYmax = (int)(screen.getHeight() *0.6f);
		
		map = new MainLayer();
		map.initWithImage("rsc/untitled1.png", 0.f, 0.f);			// Tiled ���α׷����� ���� Png����
		this.addChild( map,1 );
		
		Director.getInstance().TM.initWithImage("rsc/tile.jpg");	// map������ ä��� �ִ� Ÿ�ϵ��� ����
		Director.getInstance().TM.setParent( map );					// map�� Ÿ���� �Ѹ��̹Ƿ� Ÿ�ϵ��� �θ� map���� ����
		Director.getInstance().TM.setScene( this, 0 );				// 2���� args�� zOrder		
		
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
		item1.initWithImage( "rsc/item1.png", 0.f, 0.f, 30.f, 30.f );	// ������ ���� � ������ ���� ������ ����
		item2 = new Item();
		item2.initWithImage( "rsc/apple.png", 0.f, 0.f, 30.f, 30.f );	// ������ ���� � ������ ���� ������ ����
		
		Director.getInstance().IM.setItemByIndex(item1, 2, 1);			// ������ �������� �κ��丮�� �ε����� ���ؼ� �߰���
		Director.getInstance().IM.setItemByIndex(item2, 1, 1);
		this.addChild( Director.getInstance().cursor, 2 );				// Ŀ�� ����	
	}	

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)	throws SlickException 
	{
		// TODO Auto-generated method stub

		super.render(arg0, arg1, g);
		
		g.drawString("Total GameObjects = " + GM.getNumOfChildren(), 150, 50);					// ���������� ������ ����� �Ϲ� �̹��� ���ӿ�����Ʈó�� string ������Ʈ ���� addchild�ϴ°� ���� �����ڰ� ���� draw�ϴ°� ������
		g.drawString("Able Bullet : "+Director.getInstance().BM.getAbleBulletCount(), 50, 70);
		g.drawString("Disble Bullet : "+Director.getInstance().BM.getDisableBulletCount(), 300, 70);
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2 ) throws SlickException 
	{
		super.update(arg0, arg1, arg2);
		
		float dt = arg2 / 1000.f;
		
		hero.controlMoveByKey( dt );						// Ű�Է��� ���� hero������ ����
		Director.getInstance().TM.checkTileBlock( hero );	// hero�� Ÿ�ϵ� ���� �浹 �˻� hero�� �̵��Ҷ��� �˻�
		
		for( int i = 0 ; i < 10 ; i ++ )
		{
			Director.getInstance().TM.checkTileBlock( Director.getInstance().EM.EnemyArray[i] );			
		}		
		
		for( int j = 0 ; j < 50 ; j ++ )
		{
			Bullet obj = Director.getInstance().BM.BulletArray[j];
			Director.getInstance().EM.checkBulletCollision( obj ); 	// Enemy�� Bullet���� �浹�˻�
		}
		
		setViewPointCenter( dt );							// hero�� ȭ���� �����κ��� �Ѿ�� map�̵��� ���ؼ� hero�� �߾����� ���� ��������� �ڿ������� �� ��ũ�� ȿ��
		Director.getInstance().BM.checkCollisionWall();		// Bullet�� ��(screen�� ��)�� ������ removeChild
	}	
		
	@Override
	public void mouseClicked(int _mouseButton, int _x, int _y, int clickCount ) 
	{
		super.mouseClicked( _mouseButton, _x, _y, clickCount );
		
		
		if( _mouseButton == Input.MOUSE_RIGHT_BUTTON )
		{
			Director.getInstance().TM.switchTileTypeByClick(_mouseButton, _x, _y);	// ��Ŭ������ Ÿ�� Ÿ���� �ٲ�
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
			else hero.attack( GameObject.makePos( _x, _y ));						// ���ݹ����� ���콺 ��ǥ�� �ѱ��.			
		}
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy ) 
	{		
		super.mouseMoved(oldx, oldy, newx, newy);		
		
		// ���� Ŀ���� �������� ����ִٸ� �������� ���콺 Ŀ���� ����ٴϵ��� 
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
		
		// �κ��丮 ��� ��ư �����̶�� Ŭ���� Ŭ������ ����
		
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
		
		if( hero.getWorldPos().x < DisplayXmin )	// hero�� ������������ Xmin���� �Ѿ���� 
		{
			float gapVector = hero.getWorldPos().x - DisplayXmin; // 

			gapVector /= 16;						// �ε巴�� ���̱� ���ؼ� �ʱ� ���� ����ũ�� ������ ���� �۾���
			if( Math.abs( gapVector ) > (float)0.1 )
			{
				layerPos.x -= gapVector;
				map.setPosition( layerPos );		//  ��������� map�� �������� �ε巴�� ���ݾ� �̵�
			}			
		}
		if( DisplayXmax < hero.getWorldPos().x )	// hero�� ������������ xMax���� �Ѿ����
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
	
	/* �� ������� �˾Ҵ� cocos2d�� �Լ��� ����� �Լ������ͷ� ����� �ؼ� �� �Լ��� callback�ǵ��� �ߴٸ�
	 * slick2d�� override �� �θ��Լ��� ������ �߱� ������ �ݵ�� �θ� �Լ��� ������ �Լ� �ȿ��� �ݵ�� ������Ѿ��ϴµ�..
	 * Ȯ������ �𸣰ڴ�.
	 * // TileMap�� Manager�� ���ÿ� Layer 2���� �и��� �ʿ䵵 ������ ����.
	 * �浹������ ������ Ÿ���������ϴ� ����Ʈ�� ����� �ð��� �� ���� �ɸ���
	 * 
	 * ������ ��� ��ü�鰣�� ��Ű� �������� ó���� �̷�����µ� ��ư���ο� �ݹ��Լ��� ������� ������.
	 */
}
