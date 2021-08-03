package raptor.engine.ui.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Queue;

import raptor.engine.ui.UIAction;

public interface IInputManager extends MouseListener, KeyListener {
	void setInputMap(IInputMap inputMap);
	void setActionQueue(Queue<UIAction> actionQueue);
}
