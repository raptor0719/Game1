package raptor.engine.logical.entity.statblock.statblocks;

import raptor.engine.logical.entity.statblock.api.IStatBlock;

public class RawStatBlock implements IStatBlock {
	private final int maxHp;
	private final int physRes;
	private final int magRes;
	private final int physDmg;
	private final int magDmg;
	private final int atkRate;
	private final int moveRate;
	private final int cdr;
	private final int atkCrit;
	private final int abilCrit;
	private final int critDmg;

	public RawStatBlock(final int maxHp, final int physRes, final int magRes, final int physDmg, final int magDmg, final int atkRate, final int moveRate, final int cdr, final int atkCrit, final int abilCrit, final int critDmg) {
		this.maxHp = maxHp;
		this.physRes = physRes;
		this.magRes = magRes;
		this.physDmg = physDmg;
		this.magDmg = magDmg;
		this.atkRate = atkRate;
		this.moveRate = moveRate;
		this.cdr = cdr;
		this.atkCrit = atkCrit;
		this.abilCrit = abilCrit;
		this.critDmg = critDmg;
	}

	@Override
	public int getMaxHealth() {
		return maxHp;
	}

	@Override
	public int getPhysicalResistance() {
		return physRes;
	}

	@Override
	public int getMagicalResistance() {
		return magRes;
	}

	@Override
	public int getPhysicalDamage() {
		return physDmg;
	}

	@Override
	public int getMagicalDamage() {
		return magDmg;
	}

	@Override
	public int getAttackRate() {
		return atkRate;
	}

	@Override
	public int getMovementRate() {
		return moveRate;
	}

	@Override
	public int getCooldownReduction() {
		return cdr;
	}

	@Override
	public int getBasicAttackCriticalChance() {
		return atkCrit;
	}

	@Override
	public int getAbilityCriticalChance() {
		return abilCrit;
	}

	@Override
	public int getCriticalStrikeDamageModifier() {
		return critDmg;
	}
}
