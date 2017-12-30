package raptor.engine.logical.entity.ability.active;

import raptor.engine.logical.entity.ability.api.IAbilityData;

public class AreaActiveAbility extends ActiveAbility {
	private int radius;

	public AreaActiveAbility(final IAbilityData data, final int radius) {
		super(data);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(final int radius) {
		this.radius = radius;
	}
}
