package forSlick2D;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

abstract public class GameObject 
{
	public GameObject parent;
	
	protected Vector2f pos = new Vector2f();	// x,y 선상의 2차원 위치
	protected int tag;					// 의미없는 태그값
	protected String name;				// 의미없는 이름
	protected Image img;				// Slick에서 제공하는 2D 이미지클래스
	protected Rectangle rc;				// x,y,width,height로 이루어지는 사각형의 정보
	protected Vector2f drawStartPos;	// draw offset 변수
	protected int zOrder;				// 그려지는 순서 zIndex
	protected float scaleX;				// width의 비율 1.f 은 원래 width값
	protected float scaleY;				// height의 비율
	protected Vector2f worldPos;		// this.pos + parent.pos 
	protected float speed;				// 속도
	
	
	// gameobject가 removeObject될떄 호출되도록 만든 메소드
	abstract public void remove();
	

	public void init()
	{
		scaleX = 1.f;
		scaleY = 1.f;
		zOrder = 0;
		tag = 0;
		pos = makePos(0f,0f);
		worldPos = makePos( 0.f, 0.f );
		img = null;
		name = "";
		rc = new Rectangle(0.f, 0.f, 0.f, 0.f );
		parent = null;
		drawStartPos = makePos(0.f,0.f);		// local position 0.f, 0.f 여도 rect의 위치에 그려짐 rect가 parent 일종의 offset
		speed = 0.f;
	}
	
	public void initWithImage( String imgPath )
	{
		this.init();
		
		this.setImage( imgPath );
		rc.setWidth(img.getWidth());
		rc.setHeight(img.getHeight());
	}
	
	public void initWithImage( String imgPath, float posX, float posY )
	{
		this.initWithImage( imgPath );
		
		this.setPosition( posX, posY );
		rc = makeRect(posX, posY, img.getWidth(), img.getHeight());
	}
	
	public void initWithImage( String imgPath, float posX, float posY, float width, float height )
	{
		this.initWithImage(imgPath);
		
		this.setPosition( posX, posY );
		rc = makeRect( posX, posY, width, height );
	}
	
	// 추상클래스이기 때문에 자식클래스에서 사용하려면 오버라이딩해서 사용하도록
	public void update( int dt )
	{
		
	}
	
	// set + get
	public void addPosition( float _x, float _y )
	{
		Vector2f pos = this.getPosition();
		pos.x += _x;
		pos.y += _y;
		this.setPosition( pos );
	}
	
	public void setSpeed( float _speed )
	{
		this.speed = _speed;
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
	
	public Vector2f getWorldPos()
	{
		worldPos.x = this.getPositionX() + parent.getPositionX();
		worldPos.y = this.getPositionY() + parent.getPositionY();
		
		return worldPos;
	}
	
	public Image getImage()
	{
		return img;
	}
	
	public void setImage( Image _img )
	{
		if( _img == null )
		{
			img = null;
		}
		else
		{
			img = _img;		
			rc = makeRect(pos.x, pos.y, img.getWidth(), img.getHeight());
		}		
	}
	
	public void setImage( String imgPath )
	{
		try 
		{
			img = new Image(imgPath);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		rc = makeRect(pos.x, pos.y, img.getWidth(), img.getHeight());
	}
	
	public void setScale( float _scale )
	{
		this.setScaleX(_scale);
		this.setScaleY(_scale);
	}
	
	public void setScaleX( float _scaleX )
	{
		scaleX = _scaleX;
		rc.setWidth(rc.getWidth()*scaleX);
	}
	
	public void setScaleY( float _scaleY )
	{
		scaleY = _scaleY;
		rc.setHeight(rc.getHeight()*scaleY);
	}	
	
	public void setzOrder( int _zOrder )
	{
		zOrder = _zOrder;
	}
	
	public int getzOrder()
	{
		return zOrder;
	}
	
	public void setRectangle( Rectangle _rc )
	{
		this.rc = _rc;
	}
	
	public Rectangle getRect()
	{
		return rc;
	}
	
	public void setSize( float width, float height )
	{
		rc.setSize(width, height);
	}
	
	public void setPosition(Vector2f _pos)
	{		
		pos = _pos;
		
		rc.setLocation(_pos);		// Rect의 위치값을 보정 하지않으면 rect의 정보와 pos의 값이 달라지는 오류발생
		
		if( parent != null )
		{
			worldPos.x = _pos.x + parent.getPositionX();
			worldPos.y = _pos.y + parent.getPositionY();
		}				
	}
	
	public void setParent( GameObject _parent )
	{
		parent = _parent;
		this.setPosition(this.getPosition());
	}
	
	public GameObject getParent()
	{
		return parent;
	}
	
	public void setPosition(float x, float y)
	{		
		this.setPosition(makePos(x, y));		
	}

	public float getPositionX(){
		
		return pos.x;
	}
	public float getPositionY(){
		
		return pos.y;
	}
	
	public Vector2f getPosition(){
		
		return pos;
	}
	
	public void setName(String name){
		
		this.name = name;
	}
	
	public String getName(){
		
		return this.name;
	}
	
	public void setTag(int tag){
		
		this.tag = tag;
	}
	
	public int getTag(){
		
		return this.tag;
	}
	
	// parent를 세팅
	public void attachParent( GameObject obj )
	{
		parent = obj;
		this.setPosition(this.getPosition());
	}
	
	public void detachParent()
	{
		parent = null;
		this.setPosition(this.getPosition());
	}
	
	
	//////////////////////////계산하는것을 도와주는 일종의 인터페이스들///////////////////////////////////////////////////////////////////////
	
	public static Vector2f makePos( float _x, float _y )
	{
		Vector2f v = new Vector2f(_x,_y);
		
		return v;
	}
	
	public static Vector2f makePos( Vector2f _v1, Vector2f _v2 )
	{
		Vector2f v3 = new Vector2f();
		v3.x += _v1.x + _v2.x;
		v3.y += _v1.y + _v2.y;
		
		return v3;
	}
	
	public static Rectangle makeRect( Rectangle r1, Rectangle r2 )
	{
		Rectangle r3 = new Rectangle(r1.getX()+r2.getX(),r1.getY()+r2.getY(),r1.getWidth()+r2.getWidth(),r1.getHeight()+r2.getHeight());
		
		return r3;
	}
	
	public static Rectangle makeRect( Vector2f pos, float width, float height )
	{
		Rectangle r3 = new Rectangle( pos.x, pos.y, width, height );
		
		return r3;
	}
	
	public static Rectangle makeRect( float _x, float _y, float width, float height )
	{
		Rectangle r3 = new Rectangle( _x, _y, width, height );
		
		return r3;
	}
	
	public static Vector2f normalizeVector( Vector2f v )
	{
		float fDistance = getDistance( v );
		v.x /= fDistance;
		v.y /= fDistance;
		
		return v;
	}
	
	public static float getDistance( Vector2f v )
	{		
		float f =  (float)Math.sqrt( v.x*v.x + v.y*v.y );
		
		return f;
	}
	
	public static Vector2f getDiffVec( Vector2f _destVector, Vector2f _sourceVector )
	{
		Vector2f v = new Vector2f();
		v.x += _destVector.x - _sourceVector.x;
		v.y += _destVector.y - _sourceVector.y;		
		v = normalizeVector( v );
		
		return v;
	}
			
	public static Rectangle makeUnionRect( Rectangle r1, Rectangle r2 )
	{
		float thisLeftX = r1.getX();
	    float thisRightX = r1.getX() + r1.getWidth();
	    float thisTopY = r1.getY();
	    float thisBottomY = r1.getY() + r1.getHeight();
	    
	    if (thisRightX < thisLeftX)
	    {	        
	        float temp = thisRightX;
	        thisRightX = thisLeftX;
	        thisLeftX = temp; 
	    }	    
	    if (thisTopY < thisBottomY)
	    {
	        float temp = thisTopY;
	        thisTopY = thisBottomY;
	        thisBottomY = temp; 
	    }
	    
	    float otherLeftX = r2.getX();
	    float otherRightX = r2.getX() + r2.getWidth();
	    float otherTopY = r2.getY() ;
	    float otherBottomY = r2.getY() + r2.getHeight();
	    
	    if (otherRightX < otherLeftX)
	    {
	        float temp = otherRightX;
	        otherRightX = otherLeftX;
	        otherLeftX = temp; 
	    }	    
	    if (otherTopY < otherBottomY)
	    {
	        float temp = otherTopY;
	        otherTopY = otherBottomY;
	        otherBottomY = temp; 
	    }
	    
	    float combinedLeftX 	= Math.max( thisLeftX, otherLeftX );
	    float combinedRightX 	= Math.min( thisRightX, otherRightX );
	    float combinedTopY 		= Math.min( thisTopY, otherTopY );
	    float combinedBottomY 	= Math.max( thisBottomY, otherBottomY );
	    
	    return makeRect( combinedLeftX, combinedBottomY, combinedRightX - combinedLeftX, combinedTopY - combinedBottomY );
	}		
	
	public void keyPressed(int key, char c)
	{
		
	}
	public void keyReleased(int key, char c)
	{
		
	}
}

//마치 cocos2d와 같은 함수를 만들어봤다. 추상함수의 생성자함수라는건 별 의미가 없는것 같다. 단지 null로 초기화한다는것 이외에는
// 내생각에 자식 생성자의 형태가 추상클래스의 생성자가 될거라고 착각했는데 그게 아니고 cocos에서 미리 부모크래스에 init
// 함수를 여러개 만들어놔서 헷갈렸음
