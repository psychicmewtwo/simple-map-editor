import java.awt.Graphics;

/**
 * all types of sprites extend this interface. This is
 * necessary to be able to render them easily.
 *
 * All sprites also have a logic() method. this is used by AI
 * to update positions, or simply might check a value on the 
 * controller or keyboard and move the character...
 */
public interface Sprite
{
	public int x=0;
	public int y=0;
	
	/**
	 * render this sprite to the graphics.
	 */
	public void render(Graphics g, int offsetX, int offsetY);
	
	/**
	 * override this method to update AI. Only use this method,
	 * as the render method may be called even when the game is
	 * paused.
	 */
	public void logic();
}