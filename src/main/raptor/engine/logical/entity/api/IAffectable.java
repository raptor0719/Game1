package raptor.engine.logical.entity.api;

import raptor.engine.logical.effect.api.IEffect;

public interface IAffectable extends IEntity {
	public void affect(final IEffect e);
}
