import java.awt.Color;

public class Car {
	Point2D center;
	Component body;
	Component wheels;
	int mass;
	double speed = 0.0;
	double acceleration = 0.0;
	double mu = 1.0;

	public class Component {
		public double angle;
		public int length;
		public int width;

		public Component(double angle, int length, int width) {
			this.angle = angle;
			this.length = length;
			this.width = width;
		}
	}

	/**
	 * Create a new car. <br/>
	 * The car is initially faced North, upright (at 0 radians relative to
	 * screen's height).<br/>
	 * Front wheels are initially faced North, upright (at 0 degrees relative to
	 * body's length).<br/>
	 * Car's initial speed and acceleration are 0 px/s and 0 px/s^2;
	 * <b>Temporary</b>: Car's initial lengths, widths and mass are set in the
	 * method.
	 * 
	 * @param center
	 *            = centre of mass of the car
	 */

	public Car(Point2D center) {
		this.center = center;
		body = new Component(0, 4500, 2000);
		wheels = new Component(0, 1000, 500);
		mass = 3000;
	}

	/**
	 * Rotate the front wheels.
	 * 
	 * @param angle
	 *            = angle between wheel's length and car's length. Initial angle
	 *            is 0 degrees.
	 */

	public void turnLeft() {
		double p = Math.PI / 40.0;
		wheels.angle = Math.min(Math.PI / 4.0, wheels.angle + p);
		int x = (int) (wheels.angle / p);
		wheels.angle = x * p;
	}

	public void turnRight() {
		double p = Math.PI / 40.0;
		wheels.angle = Math.max(-Math.PI / 4.0, wheels.angle - p);
		int x = (int) (wheels.angle / p);
		wheels.angle = x * p;
	}

	public void turnLeftSlowly() {
		double p = Math.PI / 2400.0;
		wheels.angle = Math.min(Math.PI / 4.0, wheels.angle + p);
		int x = (int) (wheels.angle / p);
		wheels.angle = x * p;
	}

	public void turnRightSlowly() {
		double p = Math.PI / 2400.0;
		wheels.angle = Math.max(-Math.PI / 4.0, wheels.angle - p);
		int x = (int) (wheels.angle / p);
		wheels.angle = x * p;
	}

	public void accelerate() {
		if (0 <= speed && speed <= 10000)
			acceleration = 5000.0;
		else if (10000 < speed && speed <= 35000)
			acceleration = 1000.0;
		else if (35000 < speed && speed <= 55000)
			acceleration = 500.0;
		else if (55000 < speed && speed <= 80000)
			acceleration = 100.0;
		else
			acceleration = 50.0;
	}

	public void decelerate() {
		double weigth = mass * 150.0;
		double friction = mu * weigth;

		acceleration = -friction / mass;
		mu = 1.0;
	}

	public void brake() {
		mu = 6.0;
		decelerate();
	}

	/**
	 * Rotate point A with x radians in counterclockwise around point O.
	 * 
	 * @param O
	 *            = central point around whom we do the rotation
	 * @param A
	 *            = point we want to rotate
	 * @param x
	 *            = number of radians for the rotation counterclockwise
	 * @return the rotated point
	 */

	Point2D rotate(Point2D O, Point2D A, double x) {
		double newx = O.x() + (A.x() - O.x()) * Math.cos(x) - (A.y() - O.y()) * Math.sin(x);
		double newy = O.y() + (A.y() - O.y()) * Math.cos(x) + (A.x() - O.x()) * Math.sin(x);
		return new Point2D(newx, newy);
	}

	Point2D getRotatingCenter() {
		double speed = this.speed / 4.0;
		double R = speed / Math.abs(wheels.angle);
		if (Double.isInfinite(R) || Double.isNaN(R))
			R = 200000.0;
		double x = center.x() - Math.signum(wheels.angle) * R;
		double y = center.y() - body.length / 4.0;

		Point2D B = rotate(center, new Point2D(x, y), body.angle);

		StdDraw.setPenColor(Color.BLUE);
		StdDraw.filledCircle(B.x(), B.y(), 400);
		StdDraw.setPenColor(Color.ORANGE);
		StdDraw.filledCircle(B.x(), B.y(), 300);

		return B;
	}

	public void move(int direction) {
		if (this.speed < 0.0)
			this.speed *= -1;

		this.speed = Math.max(0, this.speed + acceleration);

		if (direction == -1)
			this.speed *= -1;

		double speed = this.speed / 40.0;

		if (direction == 1 && speed < 0.0)
			this.speed *= -1;
		if (direction == -1 && speed > 0.0)
			this.speed *= -1;

		Point2D rotatingCenter = getRotatingCenter();

		StdOut.println(body.angle + " :: " + wheels.angle);

		if (Math.abs(wheels.angle) < Math.PI / 40.0 || Math.abs(speed) < 1.0) {
			double x = center.x() - speed * Math.sin(body.angle);
			double y = center.y() + speed * Math.cos(body.angle);
			center = new Point2D(x, y);
		} else {
			double R = this.speed / 4.0 / Math.abs(wheels.angle);
			double ang = Math.acos(1.0 - (this.speed * this.speed / 16.0) / (2.0 * R * R)) / 16.0;
			center = rotate(rotatingCenter, center, Math.signum(wheels.angle) * ang);
			body.angle = (body.angle + direction * Math.signum(wheels.angle) * ang);
		}
	}

	public static void main(String[] args) {
	
	}
}
