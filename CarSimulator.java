import java.awt.Color;

public class CarSimulator {
	static int xmin = 0;
	static int xmax = 100 * 512;
	static int ymin = 0;
	static int ymax = 100 * 512;
	Car A;

	/**
	 * Simulate the car's movement.
	 */

	public void simulation() {
		setupScreen();
		A = createCar();

		while (true) {
			setupScreen();
			drawTrajectory();
			CarGraphics.draw(A);
			CarControl.control(A);
			preventOffScreen();
			StdDraw.show(1);
		}
	}

	// Create a car.

	private Car createCar() {
		Point2D center = null;

		center = new Point2D((xmin + xmax) / 2.0, (ymin + ymax) / 2.0);

		while (center == null)
			if (StdDraw.mousePressed())
				center = new Point2D(StdDraw.mouseX(), StdDraw.mouseY());

		return new Car(center);
	}

	// Setup the screen.

	private void setupScreen() {
		StdDraw.clear();
		StdDraw.setXscale(xmin, xmax);
		StdDraw.setYscale(ymin, ymax);

		Grid.draw();
		Tree.draw();
	}

	private static class Tree {

		/**
		 * Draw the tree network.
		 */

		static public void draw() {
			final int DIM = 60 * 512;
			for (int i = xmin / DIM - 2; i <= xmax / DIM + 2; i++)
				for (int j = ymin / DIM - 2; j <= ymax / DIM + 2; j++)
					Tree.drawTree(i * DIM, j * DIM);
		}

		// Draw a tree centred in (x,y).

		static private void drawTree(int x, int y) {
			StdDraw.setPenColor(0, 128, 0);
			StdDraw.filledCircle(x, y, 2000);
		}
	}

	private static class Grid {

		/**
		 * Draw the grid.
		 */

		static public void draw() {
			final int DIM = 10 * 512;
			for (int i = xmin / DIM - 2; i <= xmax / DIM + 2; i++)
				for (int j = ymin / DIM - 2; j <= ymax / DIM + 2; j++)
					drawLine(i * DIM, j * DIM);
		}

		// Draw a line of the grid, centred in (x,y).

		static private void drawLine(int x, int y) {
			StdDraw.setPenColor(220, 220, 220);
			StdDraw.setPenRadius(0.005);
			StdDraw.line(x, y - 3000000, x, y + 3000000);
			StdDraw.line(x - 3000000, y, x + 3000000, y);
		}
	}

	// Draw the trajectory of the car.

	final int DIM = 500;
	Point2D[] trajectory = new Point2D[DIM];
	int cnt = 0;

	private void drawTrajectory() {
		trajectory[cnt] = A.center;
		cnt = (cnt + 1) % DIM;

		for (int i = 0; i < DIM; i++)
			if (trajectory[i] == null)
				continue;
			else {
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.filledCircle(trajectory[i].x(), trajectory[i].y(), 100);
			}
	}

	// Prevents the main car from getting off the screen.

	private void preventOffScreen() {
		int excess = 1 * A.body.length;
		if (xmin + excess >= A.center.x()) {
			int delta = excess - ((int) A.center.x() - xmin);
			xmin -= delta;
			xmax -= delta;
		}
		if (xmax - excess <= A.center.x()) {
			int delta = excess - (xmax - (int) A.center.x());
			xmin += delta;
			xmax += delta;
		}
		if (ymin + excess >= A.center.y()) {
			int delta = excess - ((int) A.center.y() - ymin);
			ymin -= delta;
			ymax -= delta;
		}
		if (ymax - excess <= A.center.y()) {
			int delta = excess - (ymax - (int) A.center.y());
			ymin += delta;
			ymax += delta;
		}
	}

	/**
	 * Unit testing of the program and its methods.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		CarSimulator A = new CarSimulator();
		A.simulation();
	}
}
