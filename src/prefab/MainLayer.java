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


//keypressed�� super.func�� ���ϴ� ������ ȣ���� ��¥ �ݹ��Լ� �ȿ��� �ϰ� �̰� list�� �����ؼ� �ϰ������γ��� ȣ���ϴ� �Լ� �̱� ������ ��¥ �� �Լ��� �ִ� Ŭ������ ����ؼ� �������̵� �Ѱ� �ƴ϶� �����ϰ��� �ϴ� �Լ� ���ӿ�����Ʈ��
// �����ѵڿ� (���������Լ���) for�� �ȿ��� ȣ��Ǿ�� �ϱ� ���� �׸��� ������ ���� �����Լ��� ����־ superȣ���� �ƹ��� �ǹ̵� ����
