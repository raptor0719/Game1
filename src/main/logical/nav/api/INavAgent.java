package logical.nav.api;

import util.geometry.Vector;

public interface INavAgent {
	public int getX();
	public int getY();
	public Vector getMovementSpeed();
	public void move();
}
