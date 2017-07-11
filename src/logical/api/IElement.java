package logical.api;

/**
 * Abstraction of a logical element in the game world.
 *
 * <p>Example: The player, a missile, an enemy, a destructible, etc...</p>
 */
public interface IElement {
	public int getX();
	public int getY();
}
