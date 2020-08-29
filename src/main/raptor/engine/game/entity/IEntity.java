package raptor.engine.game.entity;

public interface IEntity {
	void update();
	int getX();
	int getY();
	void setX(int x);
	void setY(int y);
	double getVelocityX();
	double getVelocityY();
	void setVelocityX(double x);
	void setVelocityY(double y);
}
