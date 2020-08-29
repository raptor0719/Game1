package raptor.engine.game.entity;

import raptor.engine.game.entity.component.IGraphicsComponent;
import raptor.engine.game.entity.component.IInputComponent;
import raptor.engine.game.entity.component.IMovementComponent;
import raptor.engine.util.geometry.DoubleVector;
import raptor.engine.util.geometry.Point;

public class BasicEntity implements IEntity {
	private final IInputComponent inputComponent;
	private final IMovementComponent movementComponent;
	private final IGraphicsComponent graphicsComponent;

	private final Point position;
	private final DoubleVector velocity;

	public BasicEntity(final IInputComponent inputComponent, final IMovementComponent movementComponent, final IGraphicsComponent graphicsComponent) {
		this.inputComponent = inputComponent;
		this.movementComponent = movementComponent;
		this.graphicsComponent = graphicsComponent;
		this.position = new Point(0, 0);
		this.velocity = new DoubleVector(0, 0);
	}

	@Override
	public void update() {
		inputComponent.update(this);
		movementComponent.update(this);
		graphicsComponent.update(this);
	}

	@Override
	public int getX() {
		return position.getX();
	}

	@Override
	public int getY() {
		return position.getY();
	}

	@Override
	public void setX(final int x) {
		position.setX(x);
	}

	@Override
	public void setY(int y) {
		position.setY(y);
	}

	@Override
	public double getVelocityX() {
		return velocity.getX();
	}

	@Override
	public double getVelocityY() {
		return velocity.getY();
	}

	@Override
	public void setVelocityX(double x) {
		velocity.setX(x);
	}

	@Override
	public void setVelocityY(double y) {
		velocity.setY(y);
	}
}
