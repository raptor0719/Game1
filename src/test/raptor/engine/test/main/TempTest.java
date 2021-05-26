package raptor.engine.test.main;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import raptor.engine.nav.mesh.NavMeshNavigator;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.Polygon;

public class TempTest {
	@Test
	public void buildNavigatorTest() {
		final List<Point> parentPoints = new ArrayList<>();
		parentPoints.add(new Point(300, 0));
		parentPoints.add(new Point(600, 200));
		parentPoints.add(new Point(500, 400));
		parentPoints.add(new Point(100, 400));
		parentPoints.add(new Point(0, 200));

		final Polygon parent = new Polygon(parentPoints);

		final List<Point> holePoints = new ArrayList<>();
		holePoints.add(new Point(300, 150));
		holePoints.add(new Point(300, 350));
		holePoints.add(new Point(450, 200));

		final List<Polygon> holes = new ArrayList<>();
		holes.add(new Polygon(holePoints));

		final NavMeshNavigator navigator = NavMeshNavigator.buildNavigator(parent, holes);

		final List<Point> path = navigator.findPath(new Point(200, 300), new Point(500, 300));

		for (final Point p : path)
			System.out.println(p);
	}
}
