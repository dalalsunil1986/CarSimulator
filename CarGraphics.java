import java.awt.Color;

public class CarGraphics {

	/**
	 * Draw a car and display basic information about it.
	 * 
	 * @param A
	 *            = car to draw
	 */

	static public void draw(Car A) {
		displayInformation(A);
		displayWheels(A);
		displayBody(A);
	}

	// Display basic information about the car, such as speed, acceleration
	// etc., in its proximity.

	private static void displayInformation(Car A) {
		StdDraw.setPenColor(Color.BLACK);

		double x = A.center.x();
		double y = A.center.y() + A.body.length * 0.75;

		StdDraw.text(x, y, "Speed: " + (int) A.speed / 1000);
	}

	// Display the wheels of the car.

	private static void displayWheels(Car A) {
		Point2D NE, NW, SE, SW;
		NE = new Point2D(A.center.x() - A.body.width / 2.0, A.center.y() + A.body.length / 4.0);
		NW = new Point2D(A.center.x() + A.body.width / 2.0, A.center.y() + A.body.length / 4.0);
		SE = new Point2D(A.center.x() - A.body.width / 2.0, A.center.y() - A.body.length / 4.0);
		SW = new Point2D(A.center.x() + A.body.width / 2.0, A.center.y() - A.body.length / 4.0);
		NE = rotate(NE, A.center, A.body.angle);
		NW = rotate(NW, A.center, A.body.angle);
		SE = rotate(SE, A.center, A.body.angle);
		SW = rotate(SW, A.center, A.body.angle);
		drawWheel(A, NE, A.wheels.angle);
		drawWheel(A, NW, A.wheels.angle);
		drawWheel(A, SE, 0);
		drawWheel(A, SW, 0);
	}

	// Draw a wheel.

	private static void drawWheel(Car A, Point2D center, double angle) {
		double x[] = new double[4];
		double y[] = new double[4];

		angle += A.body.angle;

		x[0] = center.x() - A.wheels.width / 2.0;
		x[1] = center.x() + A.wheels.width / 2.0;
		x[3] = center.x() - A.wheels.width / 2.0;
		x[2] = center.x() + A.wheels.width / 2.0;
		y[0] = center.y() + A.wheels.length / 2.0;
		y[1] = center.y() + A.wheels.length / 2.0;
		y[3] = center.y() - A.wheels.length / 2.0;
		y[2] = center.y() - A.wheels.length / 2.0;

		for (int i = 0; i < 4; i++) {
			double xnew, ynew;
			xnew = Math.cos(angle) * (x[i] - center.x()) - Math.sin(angle) * (y[i] - center.y()) + center.x();
			ynew = Math.sin(angle) * (x[i] - center.x()) + Math.cos(angle) * (y[i] - center.y()) + center.y();
			x[i] = xnew;
			y[i] = ynew;
		}

		StdDraw.setPenColor(Color.BLACK);
		StdDraw.filledPolygon(x, y);
	}

	// Display the body of the car.

	static private void displayBody(Car A) {
		double x[] = new double[4];
		double y[] = new double[4];
		double angle = A.body.angle;

		x[0] = A.center.x() - A.body.width / 2.0;
		x[1] = A.center.x() + A.body.width / 2.0;
		x[3] = A.center.x() - A.body.width / 2.0;
		x[2] = A.center.x() + A.body.width / 2.0;
		y[0] = A.center.y() + A.body.length / 2.0;
		y[1] = A.center.y() + A.body.length / 2.0;
		y[3] = A.center.y() - A.body.length / 2.0;
		y[2] = A.center.y() - A.body.length / 2.0;

		for (int i = 0; i < 4; i++) {
			double xnew, ynew;
			xnew = Math.cos(angle) * (x[i] - A.center.x()) - Math.sin(angle) * (y[i] - A.center.y()) + A.center.x();
			ynew = Math.sin(angle) * (x[i] - A.center.x()) + Math.cos(angle) * (y[i] - A.center.y()) + A.center.y();
			x[i] = xnew;
			y[i] = ynew;
		}

		StdDraw.setPenColor(Color.RED);
		StdDraw.filledPolygon(x, y);
	}

	// Rotate a point around a centre.

	private static Point2D rotate(Point2D A, Point2D center, double angle) {
		double x, y;
		x = Math.cos(angle) * (A.x() - center.x()) - Math.sin(angle) * (A.y() - center.y()) + center.x();
		y = Math.sin(angle) * (A.x() - center.x()) + Math.cos(angle) * (A.y() - center.y()) + center.y();
		return new Point2D(x, y);
	}

	/**
	 * Unit testing (not required).
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
	
	}
}
