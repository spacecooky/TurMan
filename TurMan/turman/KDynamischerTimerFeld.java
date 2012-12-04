package turman;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class KDynamischerTimerFeld extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KDynamischerTimerFeld(KHauptFenster hf){
		this.hf=hf;
		init();
	}
	
	KHauptFenster hf;
	
	//dyn
	TDynamischerTimer dynTimer=null;
	int timerTyp=0;
	int zusTyp=0;
	int takt=0;
	int zeit=0;
	
	//Logo
	JCheckBox logoAnzeigen= new JCheckBox("Logo anzeigen");
	JButton logoButton= new JButton("LogoAuswählen");
	String imageName="";
	JLabel logoLabel=new JLabel();
	
	//Agenda/Timer
	JRadioButton agenda = new JRadioButton("Agenda");
	JRadioButton timer = new JRadioButton("Timer   ");
	JLabel timerLabel = new JLabel("Spielzeit(Minuten)");
	JTextField timerFeld = new JTextField("10");
	ButtonGroup timerAgendaGruppe = new ButtonGroup();
	JButton aktualisieren= new JButton("Starten/Aktualisieren");
	
	//Pukte/Begegnungen
	JRadioButton punkte = new JRadioButton("Punkte");
	JRadioButton begegnungen = new JRadioButton("Begegnungen");
	ButtonGroup punkteBegegnungenGruppe = new ButtonGroup();
	JCheckBox punkteBegegnungenAnzeigen= new JCheckBox("Punkte/Begegnungen anzeigen");
	JLabel taktLabel = new JLabel("Aktualisierungszeit (*10ms)");
	JTextField taktFeld = new JTextField("5");
	
	//Agendaberechnung
	JButton add = new JButton("Neue Zeile anhängen");
	JButton remove = new JButton("Aktuelle Zeile löschen");
	JTable table;
	JScrollPane scrollPane;
	JPanel ablauf= new JPanel();
	
	
	JLabel aheader = new JLabel("Turnierablauf");
	
	public void init(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//Dateiauswahl für das Logo
		JPanel p4 = new JPanel();
		p4.setBorder(BorderFactory.createTitledBorder("Logo"));
		p4.setLayout(new GridLayout(1,5));
		p4.add(logoAnzeigen);
		p4.add(logoButton);
		p4.add(new JLabel());
		p4.add(logoLabel);
		p4.add(new JLabel());
		logoAnzeigen.addActionListener(this);
		logoButton.setEnabled(false);
		logoButton.addActionListener(this);
		
		//WertungsOptionen
		JPanel p5 = new JPanel();
		p5.setBorder(BorderFactory.createTitledBorder("Timer/Agenda"));
		p5.setLayout(new BoxLayout(p5, BoxLayout.Y_AXIS));
		
		//Agenda
		JPanel p51=new JPanel();
		JPanel p511=new JPanel();
		p51.setLayout(new BoxLayout(p51, BoxLayout.X_AXIS));
		p51.setBorder(BorderFactory.createEtchedBorder());
		p51.add(agenda);
		
		p511.setLayout(new BoxLayout(p511, BoxLayout.Y_AXIS));
		table=new JTable(new DefaultTableModel(hf.agendaEintraege,hf.agendaHeaders));
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		JPanel hPanel= new JPanel();
		hPanel.add(aheader);
		hPanel.setLayout(new GridLayout(1,1));
		p511.add(hPanel);
		p511.add(scrollPane);
		p511.add(ablauf);
		ablauf.setLayout(new BoxLayout(ablauf, BoxLayout.Y_AXIS));
		aheader.setFont(new Font("Comic Sans Serif",1,18));
		aheader.setHorizontalAlignment(JLabel.LEFT);
		table.setFont(new Font("Comic Sans Serif",1,18));
		JPanel aPanel= new JPanel();
		aPanel.add(add);
		aPanel.setLayout(new GridLayout(1,1));
		p511.add(aPanel);
		add.addActionListener(this);
		JPanel rPanel= new JPanel();
		rPanel.add(remove);
		rPanel.setLayout(new GridLayout(1,1));
		p511.add(rPanel);
		remove.addActionListener(this);
		
		p51.add(p511);

		//Timer
		JPanel p52=new JPanel();
		p52.setLayout(new BoxLayout(p52, BoxLayout.X_AXIS));
		p52.setBorder(BorderFactory.createEtchedBorder());
		p52.add(timer);
		p52.add(timerLabel);
		p52.add(timerFeld);
		p52.add(Box.createVerticalStrut(p52.getHeight()-timer.getHeight()-timerLabel.getHeight()-timerFeld.getHeight()));
		
		timerAgendaGruppe.add(agenda);
		timerAgendaGruppe.add(timer);
		timer.setSelected(true);
		agenda.addActionListener(this);
		timer.addActionListener(this);
		agenda.setEnabled(false);
		add.setEnabled(false);
		remove.setEnabled(false);
		aheader.setEnabled(false);
		
		p5.add(p52);
		p5.add(p51);
		
		
		JPanel p6 = new JPanel();
		p6.setBorder(BorderFactory.createTitledBorder("Zusatzanzeige"));
		p6.setLayout(new GridLayout(1,5));
		p6.add(punkteBegegnungenAnzeigen);
		p6.add(punkte);
		p6.add(begegnungen);
		p6.add(taktLabel);
		p6.add(taktFeld);
		
		punkteBegegnungenGruppe.add(punkte);
		punkteBegegnungenGruppe.add(begegnungen);
		
		punkte.addActionListener(this);
		punkteBegegnungenAnzeigen.addActionListener(this);
		begegnungen.addActionListener(this);
		
		punkte.setSelected(true);
		punkte.setEnabled(false);
		begegnungen.setEnabled(false);
		
		
		
		JPanel p7 = new JPanel();
		p7.setLayout(new GridLayout(1,1));
		p7.add(aktualisieren);
		aktualisieren.addActionListener(this);
		
		
		JPanel p8 = new JPanel();
		p8.setLayout(new GridLayout(40,1));
		p8.add(new JLabel("a"));
		p8.getComponent(0).setForeground(p8.getBackground());
		
		add(p4);
		add(p5);
		add(p6);
		add(p7);
		add(p8);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object src=arg0.getSource();
		if(src==agenda || src==timer){
			//TODO
		}else if(src==aktualisieren){
			if(dynTimer==null || (dynTimer!=null && !dynTimer.frame.isVisible())){
				timerTyp=agenda.isSelected()?0:1;
				zusTyp=begegnungen.isSelected()?1:0;
				zeit=Integer.parseInt(timerFeld.getText());
				takt=Integer.parseInt(taktFeld.getText());
				dynTimer=new TDynamischerTimer(hf, zeit, takt, timerTyp,zusTyp,punkteBegegnungenAnzeigen.isSelected(),imageName);
			}else if(dynTimer.frame.isVisible()){
				timerTyp=agenda.isSelected()?0:1;
				zusTyp=begegnungen.isSelected()?1:0;
				zeit=Integer.parseInt(timerFeld.getText());
				takt=Integer.parseInt(taktFeld.getText());
				dynTimer.setVars(zeit, takt, timerTyp,zusTyp,punkteBegegnungenAnzeigen.isSelected(),imageName);
				dynTimer.frame.setVisible(true);
			}
		}else if(src==punkte || src==begegnungen){
			//TODO
		}else if(src==punkteBegegnungenAnzeigen){
				punkte.setEnabled(punkteBegegnungenAnzeigen.isSelected());
				begegnungen.setEnabled(punkteBegegnungenAnzeigen.isSelected());
				taktFeld.setEditable(punkteBegegnungenAnzeigen.isSelected());
		} else if(src==add){
			((DefaultTableModel)table.getModel()).addRow(new Object[]{"","","","","",""});
		} else if(src==remove){
			if(table.getSelectedRow()>-1){
				((DefaultTableModel)table.getModel()).removeRow(table.getSelectedRow());
			}
		} else if(src==logoAnzeigen){
			logoButton.setEnabled(logoAnzeigen.isSelected());
		} else if(src==logoButton){
			JFileChooser fileChooser=new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

			if(fileChooser.showSaveDialog(hf)==JFileChooser.APPROVE_OPTION){
				File f =new File(fileChooser.getSelectedFile().toString());
				if(f !=null){
						imageName=f.getAbsolutePath();
						logoLabel.setText(imageName);
				}
			}
		} 
	}	
}
