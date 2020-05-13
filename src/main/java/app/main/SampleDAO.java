package app.main;

public class SampleDAO {

	private DBConnecter connecter;

	public void select() {
		System.out.println("接続情報「" + connecter.getConnecter() + "」を使用してSELECTします。");
	}
}
