package raptor.engine.game;

public interface IIdProvider {
	long get();
	void free(long id);
}
