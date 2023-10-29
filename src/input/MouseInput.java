package input;


public class MouseInput {
	
	public static int X, Y;
	public static boolean MLB;
	
	private int mouseCodeButton;
	
	public MouseInput(int mouseCodeButton) {
		this.mouseCodeButton= mouseCodeButton;
	}
	
	public void notifyMousePressed(int mouseCode) {
		if(mouseCodeButton == mouseCode) {
			MLB = true;
		}
	}
	
	public void notifyMouseReleased(int mouseCode) {
		if(mouseCodeButton == mouseCode) {
			MLB = false;
		}
	}

	public void notifyMouseDragged(int x, int y) {
		X = x;
		Y = y;	
	}
	
	public void notifyMouseMoved(int x, int y) {
		X = x;
		Y = y;	
	}
	
}
