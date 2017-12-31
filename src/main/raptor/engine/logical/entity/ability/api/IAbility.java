package raptor.engine.logical.entity.ability.api;

import raptor.engine.logical.entity.ability.data.AbilityType;

public interface IAbility {
	public int getId();
	public AbilityType getType();
	public IAbilityFlavor getFlavor();
}
