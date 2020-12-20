package raptor.engine.game.entity;

import java.util.Comparator;

public class DrawDepthEntityComparator implements Comparator<IEntity> {
	@Override
	public int compare(final IEntity o1, final IEntity o2) {
		final int y1 = o1.getY();
		final int y2 = o2.getY();

		if (y1 < y2)
			return -1;
		else if (y1 > y2)
			return 1;

		return 0;
	}
}
