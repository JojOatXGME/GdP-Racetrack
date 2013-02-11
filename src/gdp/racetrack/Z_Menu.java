package gdp.racetrack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Z_Menu extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JButton New_Game;
	private JButton Load;
	private JButton Exit;
	
	public Z_Menu(){
		super();
		Dimension dim =java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setSize(220,190);
		setLocation(dim.width/4,dim.height/4);
		setUndecorated(true);
		setVisible(true);
		panel=new JPanel();
		panel.setLayout(null);
		New_Game =new JButton("New Game");
		Load=new JButton("Load");
		Exit=new JButton("Exit");
		panel.add(New_Game);
		panel.add(Load);
		panel.add(Exit);
		New_Game.setLocation(5,10);
		New_Game.setSize(200, 50);
		New_Game.setFont(new Font("New_Game",1,25));
		New_Game.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Z_RA.create();
				dispose();
			}
			
			
		});
		
		Exit.setLocation(5,(20+50));
		Exit.setSize(200,50);
		Exit.setFont(new Font("Exit Game",1,25));
		Exit.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent e) {
				Z_Exit.End();
				
			}
			
			
		});		
		getContentPane().add(panel);
		setVisible(true);
	}



	public static void main(String[] args) {
		Z_Menu Z=new Z_Menu();
	}

}
