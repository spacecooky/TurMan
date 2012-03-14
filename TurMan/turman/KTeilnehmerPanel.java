package turman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class KTeilnehmerPanel extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6596381980948989749L;
	public KTeilnehmerPanel(String teilnehmer,int teilnehmerZahl,int nummer, KHauptFenster hf){
		
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		
		nameLabel= new JButton(teilnehmer);
		nameLabel.setMaximumSize(new Dimension(150,20));
		nameLabel.setMinimumSize(new Dimension(150,20));
		nameLabel.setPreferredSize(new Dimension(150,20));
		nameLabel.setBorder(BorderFactory.createRaisedBevelBorder());
		//this.add(nameLabel);
		nameLabel.addActionListener(this);
		
		for(int i=0;i<teilnehmerZahl;i++){

			if(i==nummer){
				JPanel body= new JPanel();
				body.setBorder(BorderFactory.createRaisedBevelBorder());
				body.setMaximumSize(new Dimension(20,20));
				body.setMinimumSize(new Dimension(20,20));
				body.setPreferredSize(new Dimension(20,20));
				body.setBackground(Color.black);
				add(body);
			}
			else{
			this.nummer=nummer;
			this.hf=hf;
			KBegegnungen b = new KBegegnungen(hf,hf.teilnehmerVector.get(nummer),hf.teilnehmerVector.get(i),nummer,i);
			hf.alleBegegnungenVector.add(b);
			add(b);
			}
		}
		
	}
	
	
	JButton nameLabel;
	
	int nummer=0;
	KHauptFenster hf = null;
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(nameLabel)){
			hf.spielerFenster.anzeigen(hf.teilnehmerVector.get(nummer));
		}
		
	}
}
