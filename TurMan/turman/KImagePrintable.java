package turman;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import javax.swing.JPanel;

public class KImagePrintable implements Printable
{

	public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
    if (pageIndex > 0) {
      return(NO_SUCH_PAGE);
    } else {
      Graphics2D g2d = (Graphics2D)g;
      g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
      // Turn off double buffering
      df.setDoubleBuffered(false);
      //df.setMaximumSize(new Dimension(595, 842));
      //df.setPreferredSize(new Dimension(595, 842));
      df.paint(g2d);
      // Turn double buffering back on
      df.setDoubleBuffered(true);
      return(PAGE_EXISTS);
    }
  }
	
	JPanel df=null;
}
