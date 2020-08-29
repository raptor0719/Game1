package raptor.engine.game.entity.component;

import raptor.engine.game.entity.IEntity;

public interface IInputComponent extends IComponent {
	void process(IEntity entity);
}
