package turman;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class KInfoFenster extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2924742194067324526L;

	public KInfoFenster() {
		super("TurMan");
		setIconImage(Toolkit.getDefaultToolkit().getImage("tm.jpg"));
		init();
	}
	
	
	public void init(){
		//Font f = new Font("Dialog", Font.BOLD, 16);
		
		setContentPane(area);
		//hauptPanel.setLayout(new BoxLayout(hauptPanel,BoxLayout.X_AXIS));
		int width=400;
		int height=160;
		setSize(new Dimension(width,height));
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-width)/2, (Toolkit.getDefaultToolkit().getScreenSize().height-height)/2);
		area.setEditable(false);
		area.setFocusable(false);
		area.setContentType("text/html");
		area.setText("Turnier Manager\n"+
						"Version: "+KHauptFenster.version+"<br/>"+
						"Autor: Jan \"spacecooky\" Koch<br/>"+
						"Kontakt: <a href='mailto:cooky2k@web.de'>Email</a> <br/>"+
						"Projektseite: <a href='http://www.javaforge.com/project/4891'>http://www.javaforge.com/project/4891</a>");
		
		 area.addHyperlinkListener(new HyperlinkListener() {
		        @Override
		        public void hyperlinkUpdate(HyperlinkEvent e) {
		            if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		                System.out.println(e.getURL());
		                if(e.getURL().toString().contains("mailto")){
		                	try {
		                	    Desktop.getDesktop().mail(new URI(e.getURL() + ""));
		                	} catch (IOException e1) {
		                	    e1.printStackTrace();
		                	} catch (URISyntaxException e1) {
		                	    e1.printStackTrace();
		                	}

		                } else{
		                	try {
		                	    Desktop.getDesktop().browse(new URI(e.getURL() + ""));
		                	} catch (IOException e1) {
		                	    e1.printStackTrace();
		                	} catch (URISyntaxException e1) {
		                	    e1.printStackTrace();
		                	}
		                }
		            }
		        }
		    });

		
		setVisible(false);
	}
	
	
	JTextPane area= new JTextPane();
	
}