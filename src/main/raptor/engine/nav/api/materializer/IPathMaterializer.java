package raptor.engine.nav.api.materializer;

import java.util.List;

import raptor.engine.nav.api.graph.structures.IGraphNode;
import raptor.engine.util.geometry.Point;

public interface IPathMaterializer<D extends IGraphNode> {
	List<Point> materialize(final List<D> nodePath, final Point start, final Point end);
}
