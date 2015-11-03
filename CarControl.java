import java.awt.event.KeyEvent;

public class CarControl {

	/**
	 * Control the car.
	 * 
	 * @param A
	 *            = the car to control
	 */

	static public void control(Car A) {
		turning(A);
		movingForward(A);
		movingBackward(A);
	}

	// Turn the car.

	static private void turning(Car A) {
		if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
			A.turnLeft();
		else if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
			A.turnRight();
		else {
			if (A.wheels.angle > 0.0)
				A.turnRightSlowly();
			else if (A.wheels.angle < 0.0)
				A.turnLeftSlowly();
		}
	}

	// Move forward.

	static private void movingForward(Car A) {
		if (A.speed < 0)
			return;

		if (StdDraw.isKeyPressed(KeyEvent.VK_UP))
			A.accelerate();
		if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN))
			A.brake();

		A.move(1);
		A.decelerate();

		if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN) && Math.abs(A.speed) < 100.0)
			StdDraw.show(600);
	}

	// Move backward.

	static private void movingBackward(Car A) {
		if (A.speed > 0)
			return;

		if (StdDraw.isKeyPressed(KeyEvent.VK_UP))
			A.brake();

		if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN))
			A.accelerate();
		A.move(-1);
		A.decelerate();

		if (StdDraw.isKeyPressed(KeyEvent.VK_UP) && Math.abs(A.speed) < 100.0)
			StdDraw.show(800);
	}

	/**
	 * Unit testing (not required).
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
	
	}
}
