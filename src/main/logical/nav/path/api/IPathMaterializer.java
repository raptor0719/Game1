package logical.nav.path.api;

import java.util.List;

import logical.nav.graph.api.IGraphNode;
import util.geometry.Point;

public interface IPathMaterializer<D> {
	public List<Point> materialize(final List<? extends IGraphNode<D>> nodePath);
}
