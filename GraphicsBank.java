import javax.swing.*;
import java.awt.Graphics.*;
import java.io.*;
import java.awt.*;
import java.util.*;
import java.net.*;

/* TODO: Method to add and remove tiles.
 * TilesetChangeListener class
 * add tilechangelisteners to all classes that need to be notified of changes to tilesets.
 *etc...
 **/
public class GraphicsBank
{
	
	ArrayList tiles;
	ArrayList decorations;
	ArrayList sprites;
	
	URL url;
	
	public GraphicsBank(URL url)
	{
		this.url = url;
		tiles = new ArrayList(); //not using generics as they arent compatible with 
		decorations = new ArrayList();
		sprites = new ArrayList();
		
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			
			String line = getLine(reader);
			while(line != null)
			{
				if(line.equals("Tiles{"))
				{
					line = getLine(reader);
					while(!line.equals("}"))
					{
						String[] param = line.split(" ", 3);
						int number = Integer.parseInt(param[0]);
						int type = Integer.parseInt(param[1]);
						String name = param[2];
						
						line = getLine(reader);
						System.out.println(line);
						
						Image tileImg = new ImageIcon(getClass().getResource(line)).getImage();
						
						Tile t = new Tile(number, type, name, tileImg, line);
						
						tiles.add(t);
						
						line = getLine(reader);
					}
				}
				
				/* Legacy code. Not really supported separately from normal tiles and wont be saved
				 * separately */
				else if(line.equals("Edges{"))
				{
					line = getLine(reader);
					while(!line.equals("}"))
					{
						String name = line.substring(0, line.length()-1);
						System.out.println(name);
						int num = 1;
						
						line = getLine(reader);
						while(!line.equals("}"))
						{
							System.out.println(line);
							String[] param = line.split(" ", 3);
							int number = Integer.parseInt(param[0]);
							int type = Integer.parseInt(param[1]);
							String path = param[2];
							
							
							Image tileImg = new ImageIcon(getClass().getResource(line)).getImage();
							
							Tile t = new Tile(number, type, name, tileImg, line);
							
							tiles.add(t);
							
							line = getLine(reader);
							
						}
						line = getLine(reader);
					}
				}
				
				/* Decoration class is not used or implemented yet.
				else if(line.equals("Decorations{"))
				{
					line = getLine(reader);
					while(!line.equals("}"))
					{
						String[] param = line.split(" ", 2);
						int number = Integer.parseInt(param[0]);
						String name = param[1];
						line = getLine(reader);
						
						Image tileImg = new ImageIcon(getClass().getResource(line)).getImage();
						
						Decoration d = new Decoration(number, type, name, tileImage, line);
						
						decorations.add(d);
						line = getLine(reader);
					}
				}
				*/
				
				//Sprites are not completed yet.
				else if(line.equals("Sprites{"))
				{
					line = getLine(reader);
					while(!line.equals("}"))
					{
						line = getLine(reader);
					}
				}
				
				line = getLine(reader);
			}
		}
		catch(IOException e)
		{
			throw new RuntimeException("The graphics failed to load");
		}
		
		//Image temp = new ImageIcon(getClass().getResource( "tiles/tile" + (i+1) +".gif" )).getImage();
		
		
	}
	
	public void save(File to) throws IOException {
		System.out.println("Save");
		String line;
		
		PrintWriter out = new PrintWriter(new FileWriter(to));
		
		Iterator itr = tiles.iterator();
		
		
		out.println("Tiles{");
		
		while(itr.hasNext()) {
			Tile t = (Tile)itr.next();
			
			if(t.imageLocation != null) {
				line = t.number + " " + t.type + " " + t.name;
				out.println(line);
				out.println(t.imageLocation);
			} else {
				System.out.println("Error: Can't save "+t.name+". Dont know where the image file is.");
			}
			
			System.out.println("Save tile "+t.name);
		}
		out.println("}");
		
		out.close();
	}
	
	
	public void save(String filename) throws MalformedURLException, IOException {
		save(new File(filename));
	}
	
	/* Skips over comments and blank lines */
	private String getLine(BufferedReader r) throws IOException
	{
		String line = r.readLine();
		while(true)
		{
			if(line == null) return null;
			
			else if(line.length() != 0)
			{
				if(!(line.length() >= 2 && line.substring(0,2).equals("//")))
				{
					return line;
				}
			}
			line = r.readLine();
		}
	}
	
	public Tile getTile(int i)
	{
		Iterator itr = tiles.iterator();
		while(itr.hasNext()) {
			Tile t = (Tile)itr.next();
			if(t.number == i) {
				return t;
			}
		}
		
		itr = decorations.iterator();
		while(itr.hasNext()) {
			Tile t = (Tile)itr.next();
			if(t.number == i) {
				return t;
			}
		}
		return null;
	}
	public Tile getTile(String tilename)
	{
		Iterator itr = tiles.iterator();
		while(itr.hasNext()) {
			Tile t = (Tile)itr.next();
			if(t.name.equals(tilename)) {
				return t;
			}
		}
		itr = decorations.iterator();
		while(itr.hasNext()) {
			Tile t = (Tile)itr.next();
			if(t.name.equals(tilename)) {
				return t;
			}
		}
		return null;
	}
	
	/* changed for arrayList */
	Iterator getTileIterator()
	{
		return tiles.iterator();
	}
	Iterator getDecorationIterator()
	{
		return decorations.iterator();
	}
	
	public URL getURL()
	{
		return url;
	}
	
	public void setEffect(float r, float g, float b, float h, float s, float z)
	{
		Iterator i = getTileIterator();
		while(i.hasNext()) {
			((Tile)(i.next())).adjustRGBHS(r, g, b, h, s, z);
		}
	}
	
	
	
}