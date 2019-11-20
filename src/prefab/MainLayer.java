package prefab;

import org.newdawn.slick.geom.Rectangle;

import forSlick2D.GameObject;

public class MainLayer extends GameObject
{	
	@Override
	public void init()
	{
		super.init();
		
		 rc = new Rectangle( pos.x, pos.y, 1024, 768 );
	}	
		
	@Override
	public void remove() 
	{		
	}
	
	@Override
	public void update( int dt )
	{		
	}	
	
}


//keypressed에 super.func를 안하는 이유는 호출을 진짜 콜백함수 안에서 하고 이건 list로 관리해서 일괄적으로내가 호출하는 함수 이기 때문에 진짜 이 함수가 있는 클래스를 상속해서 오버라이딩 한게 아니라 관리하고자 하는 함수 게임오브젝트에
// 구현한뒤에 (완전가상함수로) for문 안에서 호출되어야 하기 때문 그리고 어차피 완전 가상함수라 비어있어서 super호출이 아무런 의미도 없음
