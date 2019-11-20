package forSlick2D;

import forSlick2D.TileManager;
import forSlick2D.BulletManager;
import forSlick2D.EnemyManager;
import forSlick2D.ButtonManager;
import forSlick2D.InventoryManager;
import prefab.Cursor;

public class Director {
	// ��� �Ŵ����� �������� ���̴� ������ �����ϴ� Ŭ����
	
	private static Director uniqueInstance = new Director();	
	
	private Director(){}	
	public BulletManager BM;
	public TileManager TM;
	public ButtonManager BTM;
	public EnemyManager EM;
	public InventoryManager IM;
	public Cursor cursor;
		
	public static Director getInstance(){

		if (uniqueInstance == null) uniqueInstance = new Director();
		return uniqueInstance;
	}
	
	public void init()
	{
		cursor = new Cursor();
		cursor.init();
		
		BM = new BulletManager();
		TM = new TileManager();
		EM = new EnemyManager();
		BTM = new ButtonManager();
		BTM.init();
		IM = new InventoryManager();
	}
 }
