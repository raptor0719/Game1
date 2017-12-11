package logical.nav.api;

import java.util.List;

import util.geometry.Point;

public interface INavAgent {
	public Point getPosition();
	public boolean setPath(final List<Point> path);
	public void move();
}
