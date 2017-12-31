package raptor.engine.logical.entity.ability.data;

import raptor.engine.logical.effect.api.IEffect;
import raptor.engine.logical.entity.ability.api.IAbilityFlavor;

public class ActiveAbilityData {
	private final int id;
	private final TargetingType targetingType;
	private final IAbilityFlavor flavor;

	private final int cooldown;
	private final int castTime;
	private final int range;
	private final int radius;

	private final IEffect effect;

	public ActiveAbilityData(final int id, final TargetingType targetingType, final IAbilityFlavor flavor, final int cooldown, final int castTime, final int range, final int radius, final IEffect effect) {
		this.id = id;
		this.targetingType = targetingType;
		this.flavor = flavor;
		this.cooldown = cooldown;
		this.castTime = castTime;
		this.range = range;
		this.radius = radius;
		this.effect = effect;
	}

	public int getId() {
		return id;
	}

	public TargetingType getTargetingType() {
		return targetingType;
	}

	public IAbilityFlavor getFlavor() {
		return flavor;
	}

	public int getCooldown() {
		return cooldown;
	}

	public int getCastTime() {
		return castTime;
	}

	public int getRange() {
		return range;
	}

	public int getRadius() {
		return radius;
	}

	public IEffect getEffect() {
		return effect;
	}
}
