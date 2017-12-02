package logical.nav.graph.search.heuristic.api;

import util.structures.ValuePair;

public interface IHeuristicGenerator {
	public ValuePair<Object, Object> generate();
}
