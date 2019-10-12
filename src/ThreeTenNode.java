import org.apache.commons.collections15.Factory;

import java.awt.Color;

import java.util.*;

/**
 * The Class ThreeTenNode represents a graph vertex
 */
class ThreeTenNode implements Comparable<ThreeTenNode> {
	public static int nodeCount = 0;

	private int id;
	private String text;
	private Color c = Color.WHITE;

	/**
	 * Instantiates a new three ten node.
	 */
	public ThreeTenNode() {
		this.id = nodeCount++;
		this.text = "" + this.id;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return c;
	}

	/**
	 * Sets the color.
	 *
	 * @param c the new color
	 */
	public void setColor(Color c) {
		this.c = c;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	
	@Override
	public String toString() {
		return "" + text;
	}

	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ThreeTenNode) {
			return this.id == ((ThreeTenNode) o).id;
		}
		return false;
	}

	
	@Override
	public int compareTo(ThreeTenNode n) {
		return this.id - n.id;
	}


	@Override
	public int hashCode() {
		return id;
	}

	/**
	 * Gets  factory
	 *
	 * @return factory
	 */
	public static Factory<ThreeTenNode> getFactory() {
		return new Factory<ThreeTenNode>() {
			public ThreeTenNode create() {
				return new ThreeTenNode();
			}
		};
	}
}
