package raptor.engine.logical.map.api;

import java.util.List;

import raptor.engine.logical.entity.api.ICollideable;
import raptor.engine.logical.entity.api.IEntity;
import raptor.engine.util.geometry.Point;

public interface IMapContext {
	public List<ICollideable> getLocalCollideables(final Point pos);
	public void noticeOfDeath(final IEntity e);
	public long getCurrentTime();
}
