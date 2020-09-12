package raptor.engine.util;

public interface IIdProvider {
	long get();
	long allocate(long request);
	void free(long id);
	void freeAll();
	boolean isFree(long value);
}
