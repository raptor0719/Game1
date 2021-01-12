package raptor.engine.ui;

public enum UIAnchorPoint {
	TOP_LEFT {
		@Override
		public int translateX(final int x, final int width) {
			return x;
		}
		@Override
		public int translateY(final int y, final int height) {
			return y;
		}
	},
	TOP_CENTER {
		@Override
		public int translateX(final int x, final int width) {
			return x - (width/2);
		}
		@Override
		public int translateY(final int y, final int height) {
			return y;
		}
	},
	TOP_RIGHT {
		@Override
		public int translateX(final int x, final int width) {
			return x - width;
		}
		@Override
		public int translateY(final int y, final int height) {
			return y;
		}
	},
	CENTER_LEFT {
		@Override
		public int translateX(final int x, final int width) {
			return x;
		}
		@Override
		public int translateY(final int y, final int height) {
			return y - (height/2);
		}
	},
	CENTER_RIGHT {
		@Override
		public int translateX(final int x, final int width) {
			return x - width;
		}
		@Override
		public int translateY(final int y, final int height) {
			return y - (height/2);
		}
	},
	BOTTOM_LEFT {
		@Override
		public int translateX(final int x, final int width) {
			return x;
		}
		@Override
		public int translateY(final int y, final int height) {
			return  y - height;
		}
	},
	BOTTOM_CENTER {
		@Override
		public int translateX(final int x, final int width) {
			return x - (width/2);
		}
		@Override
		public int translateY(final int y, final int height) {
			return y - height;
		}
	},
	BOTTOM_RIGHT {
		@Override
		public int translateX(final int x, final int width) {
			return x - width;
		}
		@Override
		public int translateY(final int y, final int height) {
			return y - height;
		}
	},
	CENTER {
		@Override
		public int translateX(final int x, final int width) {
			return x - (width/2);
		}
		@Override
		public int translateY(final int y, final int height) {
			return y - (height/2);
		}
	};

	public abstract int translateX(final int x, final int width);
	public abstract int translateY(final int y, final int height);
}
