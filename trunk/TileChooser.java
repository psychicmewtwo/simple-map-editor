import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;


public class TileChooser extends JPanel
{
	ArrayList tiles;
	GridLayout layout;
	int tileWidth = 32;
	Tile selectedTile;
	
	public TileChooser(GraphicsBank gfx)
	{
		ButtonGroup group = new ButtonGroup();
		layout = new GridLayout(0,5);
		this.setLayout(layout);
		Iterator i = gfx.getTileIterator();
		
		while(i.hasNext())
		{
			TileButton b = new TileButton((Tile)i.next());
			this.add(b);
			group.add(b);
		}
	}
	
	public void setWidth(int width)
	{
		if(width >= tileWidth+8)
		{
			layout.setColumns(width/(tileWidth+15));
			revalidate();
		}
		
	}
	
	public Tile getSelectedTile()
	{
		return selectedTile;
	}
	
	
	class TileButton extends JToggleButton
	{
		Tile tile;
		public TileButton(Tile t)
		{
			super();
			Image i = t.getImage();
			
			Image i2 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
			i2.getGraphics().drawImage(i, 0, 0, 32, 32, null);
			
			setIcon(new ImageIcon(i2));
			
			
			setToolTipText(t.getName());
			setMargin(new Insets(2,2,2,2));
			tile = t;
			
			this.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					selectedTile = tile;
				}
				
			});
		}
		
		public Tile getTile()
		{
			return tile;
		}
		
	}
	
}
	