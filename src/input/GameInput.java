package input;

public class GameInput implements InputController{
	
	public static boolean UP, DOWN, LEFT, RIGHT, SHOOT, PAUSE;
	private boolean isMoveUp;
	private boolean isMoveLeft;
	private boolean isMoveRight;
	private boolean isShoot;
	private boolean isPause;
	
	private int keyCodeMoveUp;
	private int keyCodeMoveLeft;
	private int keyCodeMoveRight;
	private int keyCodeShoot;
	private int keyCodePause;
	
	public GameInput(int keyCodeMoveUp, int keyCodeMoveLeft, int keyCodeMoveRight,int keyCodeShoot, int keyCodePause){
		
		this.keyCodeMoveUp = keyCodeMoveUp;
		this.keyCodeMoveLeft = keyCodeMoveLeft;
		this.keyCodeMoveRight = keyCodeMoveRight;
		this.keyCodeShoot = keyCodeShoot;
		this.keyCodePause = keyCodePause;
		
		UP = false;
		LEFT = false;
		RIGHT = false;
		SHOOT = false;
		PAUSE = false;
	}
	
	public void update()
	{
		UP = isMoveUp;
		LEFT = isMoveLeft;
		RIGHT = isMoveRight;
		SHOOT = isShoot;
		PAUSE = isPause;
	}	
	
	@Override
	public boolean isMoveUp() {
		return isMoveUp;
	}

	@Override
	public boolean isMoveLeft() {
		return isMoveLeft;
	}

	@Override
	public boolean isMoveRight() {
		return isMoveRight;
	}
	
	@Override
	public boolean isShoot() {
		return isShoot;
	}
	
	@Override
	public boolean isPause() {
		return isPause;
	}
	
	public void notifyKeyPressed(int keyCode) {
		if (keyCode == keyCodeMoveUp){
			isMoveUp = true;
		} else if (keyCode == keyCodeMoveRight){
			isMoveRight = true;
		} else if (keyCode == keyCodeMoveLeft){
			isMoveLeft = true;
		}  else if (keyCode == keyCodeShoot){
			isShoot = true;	
		} else if (keyCode == keyCodePause){
			isPause = true;
		}
	}

	public void notifyKeyReleased(int keyCode) {
		if (keyCode == keyCodeMoveUp){
			isMoveUp = false;
		} else if (keyCode == keyCodeMoveRight){
			isMoveRight = false;
		} else if (keyCode == keyCodeMoveLeft){
			isMoveLeft = false;
		}  else if (keyCode == keyCodeShoot){
			isShoot = false;	
		} else if (keyCode == keyCodePause){
			isPause = false;
		}		
	}

}
