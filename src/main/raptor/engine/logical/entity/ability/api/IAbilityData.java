package raptor.engine.logical.entity.ability.api;

import raptor.engine.logical.effect.api.IEffect;
import raptor.engine.logical.entity.ability.misc.TargetingType;

public interface IAbilityData {
	public int getId();
	public int getCooldown();
	public TargetingType getTargetingType();
	public int getCastTime();
	public int getRange();
	public IEffect getEffect();
	public IAbilityFlavor getFlavor();
}
