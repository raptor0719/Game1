package raptor.engine.game.entity;

import java.util.Comparator;

import raptor.engine.display.render.RenderPlane;

public class DrawDepthEntityComparator implements Comparator<IEntity> {
	@Override
	public int compare(final IEntity o1, final IEntity o2) {
		final RenderPlane r1 = o1.getRenderPlane();
		final RenderPlane r2 = o2.getRenderPlane();
		
		if (r1 != r2) {
			if (r1 == RenderPlane.BACKGROUND)
				return -1;
			else if (r1 == RenderPlane.FOREGROUND)
				return 1;
			
			if (r2 == RenderPlane.BACKGROUND)
				return 1;
			
			return -1;
		}
		
		final int y1 = o1.getY();
		final int y2 = o2.getY();

		if (y1 < y2)
			return -1;
		else if (y1 > y2)
			return 1;

		return 0;
	}
}
