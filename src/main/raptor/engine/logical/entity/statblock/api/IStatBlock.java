package raptor.engine.logical.entity.statblock.api;

public interface IStatBlock {
	public int getMaxHealth();
	public int getPhysicalResistance();
	public int getMagicalResistance();
	public int getPhysicalDamage();
	public int getMagicalDamage();
	public int getAttackRate();
	public int getMovementRate();
	public int getCooldownReduction();
	public int getBasicAttackCriticalChance();
	public int getAbilityCriticalChance();
	public int getCriticalStrikeDamageModifier();
}
