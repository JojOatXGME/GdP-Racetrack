package gdp.racetrack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Z_RA1 extends JFrame{
	private JButton Regel1;
	private JButton Regel2;
	private JButton Regel3;
	private JButton Regel4;
	private JButton Zuruck;
	
	public Z_RA1(String a, String b, String c, String d, int counter){
		super();
		final int blue=counter;
		Dimension dim =java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setSize(220,280);
		setLocation(dim.width/4,dim.height/4);
		setUndecorated(true);
		setVisible(true);
		JPanel panel=new JPanel();
		panel.setLayout(null);
		Regel1=new JButton(a);
		Regel2=new JButton(b);
		Regel3=new JButton(c);
		Regel4=new JButton(d);
		Zuruck=new JButton("Zuruck");
		panel.add(Regel1);
		panel.add(Regel2);
		panel.add(Regel3);
		panel.add(Regel3);
		panel.add(Regel4);
		panel.add(Zuruck);
		Regel1.setLocation(5, 10);
		Regel1.setSize(200, 50);
		Regel1.setFont(new Font(a,1,25));
		Regel1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if ((blue+1)==1){Z_RA2.create();dispose();}
			}
			
			
		});

		Regel2.setLocation(5, 20+50);
		Regel2.setSize(200, 50);
		Regel2.setFont(new Font(b,1,25));

		Regel3.setLocation(5, 30+100);
		Regel3.setSize(200, 50);
		Regel3.setFont(new Font(c,1,25));

		Regel4.setLocation(5, 40+150);
		Regel4.setSize(200, 50);
		Regel4.setFont(new Font(d,1,25));
		getContentPane().add(panel);
				
		Zuruck.setLocation(20,50+200);
		Zuruck.setSize(100,25);
		Zuruck.setFont(new Font("Zuruck",1,13));
		Zuruck.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (blue+1==1) {Z_Menu Z=new Z_Menu();}
				if (blue+1==2) {Z_RA1.create();}
			dispose();
			}
			
			
		});
		
		
	}
	
	
	
	
	
	public static void create(){
		Z_RA Next=new Z_RA("SiegRegel1","SiegRegel2","Wurst","SiegRegel4", 0);
		}
	
	
	

}
