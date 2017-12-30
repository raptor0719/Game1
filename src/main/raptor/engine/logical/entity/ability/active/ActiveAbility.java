package raptor.engine.logical.entity.ability.active;

import raptor.engine.logical.effect.api.IEffect;
import raptor.engine.logical.entity.ability.api.IAbility;
import raptor.engine.logical.entity.ability.api.IAbilityData;
import raptor.engine.logical.entity.ability.api.IAbilityFlavor;
import raptor.engine.logical.entity.ability.misc.AbilityType;

public class ActiveAbility implements IAbility {
	private final IAbilityData data;

	private int cooldown;
	private int castTime;
	private int range;

	private IEffect effect;

	public ActiveAbility(final IAbilityData data) {
		this.data = data;

		this.cooldown = data.getCooldown();
		this.castTime = data.getCastTime();
		this.range = data.getRange();

		this.effect = data.getEffect();
	}

	@Override
	public int getId() {
		return data.getId();
	}

	@Override
	public AbilityType getType() {
		return AbilityType.Active;
	}

	@Override
	public IAbilityFlavor getFlavor() {
		return data.getFlavor();
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

	public IEffect getEffect() {
		return effect;
	}

	public void setCooldown(final int cooldown) {
		this.cooldown = cooldown;
	}

	public void setCastTime(final int castTime) {
		this.castTime = castTime;
	}

	public void setRange(final int range) {
		this.range = range;
	}

	public void setEffect(final IEffect effect) {
		this.effect = effect;
	}
}
