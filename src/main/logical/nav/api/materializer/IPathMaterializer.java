package logical.nav.api.materializer;

import java.util.List;

import logical.nav.api.graph.structures.IGraphNode;
import util.geometry.Point;

public interface IPathMaterializer<D extends IGraphNode> {
	public List<Point> materialize(final List<D> nodePath, final Point start, final Point end);
}
