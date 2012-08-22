package jas.util.border;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

/**
 * Draws a drop shadow border. Designed to be used as part of
 * a compound border in combination with a line border.
 */
public class ShadowBorder extends AbstractBorder
{
	public final int NORTHEAST = 1;
	public final int NORTHWEST = 2;
	public final int SOUTHEAST = 3;
	public final int SOUTHWEST = 4;
	/**
	 * Creates a ShadowBorder with default properties
	 */
	public ShadowBorder()
	{
		this(null,0,5);
	}
	/**
	 * Creates a ShadowBorder
	 * @param c The color for the shadow. If <code>null</code> the border will be drawn using a darker version of the components background color.
	 * @param orientation One of NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST
	 * @param size The size of the shadow in pixels
	 */
	public ShadowBorder(Color c,int orientation, int size)
	{
		this.color = c;
		this.orientation = orientation;
		this.size = size;
	}
	public static ShadowBorder createShadowBorder()
	{
		return defaultInstance;
	}
	public void paintBorder(Component comp, Graphics g, int x, int y, int width, int height)
	{
		g.setColor(color == null ? comp.getBackground().darker() : color);
		switch (orientation)
		{
		case NORTHWEST:
			g.fillRect(x,y,width-size,size); // horiz
			g.fillRect(x,y,size,height-size); // vert
		case NORTHEAST:
			g.fillRect(x+size,y,width-size,size); // horiz
			g.fillRect(x+width-size,y,size,height-size); // vert
		case SOUTHWEST:
			g.fillRect(x,y+height-size,width-size,size); // horiz
			g.fillRect(x,y+size,size,height-size); // vert
		case SOUTHEAST:
		default:
			g.fillRect(x+size,y+height-size,width-size,size); // horiz
			g.fillRect(x+width-size,y+size,size,height-size); // vert
		}
	}
	public Insets getBorderInsets(Component c)
	{
		return getBorderInsets(c,new Insets(0,0,0,0));
	}
	public Insets getBorderInsets(Component c, Insets i)
	{
		switch (orientation)
		{
		case NORTHWEST:
			i.left=size;
			i.right=0;
			i.top=size;
			i.bottom=0;
			break;
		case NORTHEAST:
			i.left=0;
			i.right=size;
			i.top=size;
			i.bottom=0;
			break;
		case SOUTHWEST:
			i.left=size;
			i.right=0;
			i.top=0;
			i.bottom=size;
			break;
		case SOUTHEAST:
		default:
			i.left=0;
			i.right=size;
			i.top=0;
			i.bottom=size;
		}
		return i;
	}
	private int size;
	private Color color;
	private int orientation;
	private static ShadowBorder defaultInstance = new ShadowBorder();
}
