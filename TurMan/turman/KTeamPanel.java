package turman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class KTeamPanel extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6596381980948989749L;
	public KTeamPanel(String teilnehmer,int teilnehmerZahl,int nummer, KHauptFenster hf){
		
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		
		nameLabel= new JButton(teilnehmer);
		nameLabel.setMaximumSize(new Dimension(250,20));
		nameLabel.setMinimumSize(new Dimension(250,20));
		nameLabel.setPreferredSize(new Dimension(250,20));
		nameLabel.setBorder(BorderFactory.createRaisedBevelBorder());
		this.add(nameLabel);
		nameLabel.addActionListener(this);
		
		for(int i=0;i<teilnehmerZahl;i++){

			//if(i==nummer){
				JPanel p = new JPanel();
				p.setBorder(BorderFactory.createRaisedBevelBorder());
				p.setMaximumSize(new Dimension(20,20));
				p.setMinimumSize(new Dimension(20,20));
				p.setPreferredSize(new Dimension(20,20));
				p.setBackground(Color.black);
				add(p);
			/*}
			else{
			this.nummer=nummer;
			this.hf=hf;
			KBegegnungen b = new KBegegnungen(hf,hf.teilnehmerVector.get(nummer),hf.teilnehmerVector.get(i),nummer,i);
			hf.alleBegegnungenVector.add(b);
			add(b);
			}*/
		}
		
	}
	JButton nameLabel;
	int nummer=0;
	KHauptFenster hf = null;
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(nameLabel)){
			//hf.spielerFenster.anzeigen(hf.teilnehmerVector.get(nummer));
		}
		
	}
}
