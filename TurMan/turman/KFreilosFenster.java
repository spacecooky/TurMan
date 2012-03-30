package turman;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KFreilosFenster extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4005838746171262L;

	public KFreilosFenster(KHauptFenster hf){
		this.hf=hf;
		add(p);
		cancel.addActionListener(this);
		ok.addActionListener(this);
	}
	
	KHauptFenster hf;
	JPanel p = new JPanel();
	JTextField prim = new JTextField();
	JTextField sek = new JTextField();
	JButton cancel= new JButton("Abbrechen");
	JButton ok= new JButton("Hinzufügen");
	
	public void init(){
		p.removeAll();
		setSize(500,100);
		p.setLayout(new GridLayout(3,2));
		if(hf.optionenFeld.PSS.isSelected()){
			p.add(new JLabel("Primärpunkte"));
			p.add(new JLabel("Sekundärpunkte"));
		} else if(hf.optionenFeld.TS.isSelected()){
			p.add(new JLabel("Turnierpunkte"));
			p.add(new JLabel("Siegespunkte"));
		}
		p.add(prim);
		p.add(sek);
		p.add(cancel);
		p.add(ok);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==cancel){
			setVisible(false);
		} else if(arg0.getSource()==ok){
			for(int i=0;i<hf.teilnehmerVector.size();i++){
				KTeilnehmer t = hf.teilnehmerVector.get(i);
				if(t.vorname.equals("Freilos") && t.nachname.equals("Freilos") && t.nickname.equals("Freilos") && t.armee.equals("Freilos") && t.ort.equals("Freilos")){
					hf.freilosPrim=Integer.parseInt(prim.getText());
					hf.freilosSek=Integer.parseInt(sek.getText());
					t.deleted=false;
					hf.fillPanels();
					hf.fillTeamPanels();
					hf.refillPanels();
					hf.updatePanels();
					setVisible(false);
					return;
				}
			}
			
			try{
			hf.freilosPrim=Integer.parseInt(prim.getText());
			hf.freilosSek=Integer.parseInt(sek.getText());
			KTeilnehmer t =new KTeilnehmer("Freilos","Freilos");
			t.armee="Freilos";
			t.nickname="Freilos";
			t.ort="Freilos";
			hf.teilnehmerVector.add(t);
			hf.HauptPanel.removeAll();
			hf.fillPanels();
			hf.fillTeamPanels();
			hf.refillPanels();
			hf.updatePanels();
			setVisible(false);
			}catch (NumberFormatException e) {
				// TODO: Fehlerdialog
			}
		}
		
	}
}
