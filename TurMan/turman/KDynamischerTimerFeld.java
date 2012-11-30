package turman;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
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
	
	//WertungsOptionen
	JRadioButton agenda = new JRadioButton("Agenda");
	JRadioButton timer = new JRadioButton("Timer");
	JLabel timerLabel = new JLabel("Spielzeit(Minuten)");
	JTextField timerFeld = new JTextField("");
	ButtonGroup timerAgendaGruppe = new ButtonGroup();
	JButton aktualisieren= new JButton("Starten/Aktualisieren");
	
	JRadioButton punkte = new JRadioButton("Punkte");
	JRadioButton begegnungen = new JRadioButton("Begegnungen");
	ButtonGroup punkteBegegnungenGruppe = new ButtonGroup();
	JCheckBox punkteBegegnungenAnzeigen= new JCheckBox("Punkte/Begegnungen anzeigen");
	JLabel taktLabel = new JLabel("Aktualisierungszeit (*100ms)");
	JTextField taktFeld = new JTextField("10");
	
	//Agendaberechnung
	JButton add = new JButton("Neue Zeile anhängen");
	JButton remove = new JButton("Aktuelle Zeile löschen");
	JTable table;
	JScrollPane scrollPane;
	JPanel ablauf= new JPanel();
	String [] headers={"Ereignis","TT","MM","JJJJ","HH","MM"};
	String [][] eintraege={{"Anmeldung","13","07","2012","09","00"},
						   {"Infos","13","07","2012","09","45"},
						   {"Turnierrunde 1","13","07","2012","10","00"},
						   {"Mittagspause","13","07","2012","12","00"}};
	Vector<KAgendaEintrag> agendaEintraege=new Vector<KAgendaEintrag>();
	JLabel aheader = new JLabel("Turnierablauf");
	
	public void init(){
		//WertungsOptionen
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel p5 = new JPanel();
		p5.setBorder(BorderFactory.createTitledBorder("Timer/Agenda"));
		p5.setLayout(new GridLayout(1,2));
		
		//Agenda
		JPanel p51=new JPanel();
		p51.setLayout(new BoxLayout(p51, BoxLayout.Y_AXIS));
		p51.setBorder(BorderFactory.createEtchedBorder());
		p51.add(agenda);
		
		table=new JTable(new DefaultTableModel(eintraege,headers));
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		p51.add(aheader);
		p51.add(scrollPane);
		p51.add(ablauf);
		ablauf.setLayout(new BoxLayout(ablauf, BoxLayout.Y_AXIS));
		aheader.setFont(new Font("Comic Sans Serif",1,18));
		aheader.setHorizontalAlignment(JLabel.LEFT);
		table.setFont(new Font("Comic Sans Serif",1,18));
		p51.add(add);
		add.addActionListener(this);
		p51.add(remove);
		remove.addActionListener(this);

		//Timer
		JPanel p52=new JPanel();
		p52.setLayout(new BoxLayout(p52, BoxLayout.Y_AXIS));
		p52.setBorder(BorderFactory.createEtchedBorder());
		p52.add(timer);
		p52.add(timerLabel);
		p52.add(timerFeld);
		
		timerAgendaGruppe.add(agenda);
		timerAgendaGruppe.add(timer);
		agenda.setSelected(true);
		agenda.addActionListener(this);
		timer.addActionListener(this);
		
		p5.add(p51);
		p5.add(p52);
		add(p5);
		
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
		
		add(p6);
		
		JPanel p7 = new JPanel();
		p7.setLayout(new GridLayout(1,1));
		p7.add(aktualisieren);
		aktualisieren.addActionListener(this);
		add(p7);
		
		JPanel p8 = new JPanel();
		p8.setLayout(new GridLayout(40,1));
		p8.add(new JLabel("a"));
		p8.getComponent(0).setForeground(p8.getBackground());
		
		add(p8);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object src=arg0.getSource();
		if(src==agenda || src==timer){
			//TODO
		}else if(src==aktualisieren){
			//TODO
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
		} 
	}	
}
