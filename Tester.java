/**
	Created by Zachary Helfinstein
	All rights reserved.

	Version 1.0.0 (Last edited 9/30/13; Tested 9/30/13)

 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Tester implements ActionListener, KeyListener{

	private JComboBox methodChooser;
	private JTextArea out;
	private JTextField in, in2;
	private String[] methods;
	private JButton go;
	private JMenuItem quit;
	private BufferedImage image;

	public Tester(){
		in = new JTextField(10);
		in2 = new JTextField(10);
		out = new JTextArea(40, 40);
		out.setEditable(false);
		JScrollPane scroller = new JScrollPane(out);
		methods = new String[3];
		methods[0] = "Get Image";
		methods[1] = "Write Image";
		methods[2] = "Show Image";
/*		methods[3] = "Reset";
		methods[4] = "Set j1";
		methods[5] = "Set j2";
		methods[6] = "j1.compareTo(j2)";
		methods[7] = "Equals";
		methods[8] = "Append";
		methods[9] = "Sort";
		methods[10] = "Index Of";
	*/	go = new JButton("Go");
		go.addActionListener(this);
        go.addKeyListener(this);
		methodChooser = new JComboBox(methods);
		JPanel panel = new JPanel();
		panel.add(in);
		panel.add(in2);
		panel.add(methodChooser);
		panel.add(go);
		panel.add(scroller);
		quit = new JMenuItem("Quit");
		quit.setMnemonic('Q');
		quit.addActionListener(this);
		JMenu file = new JMenu("File");
		file.setMnemonic('F');
		file.add(quit);
		JMenuBar bar = new JMenuBar();
		bar.add(file);
		JFrame frame = new JFrame("Tester");
		frame.setSize(new Dimension(500, 700));
		frame.setJMenuBar(bar);
		frame.add(panel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

	public static void main(String[] args) {
		Tester ui = new Tester();
	}

	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();
		if(obj == go){
			setText();
		}else if(obj == quit){
			System.exit(0);
		}
	}

    public void keyPressed(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){
        Object obj = e.getSource();
        if(obj == go && (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ACCEPT)) {
            setText();
        }
    }
    
	private void setText(){
		if(methodChooser.getSelectedItem() == methods[0]){
			image = ImageFetch.getImage(in.getText());
			out.setText(out.getText()+"Image from "+in.getText()+" saved"+"\n");
		} else if(methodChooser.getSelectedItem() == methods[1]){
			ImageFetch.saveImage(image, in.getText(), in2.getText());
            out.setText(out.getText()+"Image saved as "+in.getText()+"\n");
		} else if(methodChooser.getSelectedItem() == methods[2]){
			out.setText(out.getText()+"....I haven't implemented this yet....."+"\n");
		}/* else if(methodChooser.getSelectedItem() == methods[3]){
			item.reset();
            out.setText(out.getText()+"Scheduler reset.\n");
		} else if(methodChooser.getSelectedItem() == methods[4]){
			j1 = new Job(getInt(in.getText()), getInt(in2.getText()), true);
            out.setText(out.getText()+"j1 = "+j1+"\n");
		} else if(methodChooser.getSelectedItem() == methods[5]){
			j2 = new Job(getInt(in.getText()), getInt(in2.getText()), true);
            out.setText(out.getText()+"j2 = "+j2+"\n");
		} else if(methodChooser.getSelectedItem() == methods[6]){
			out.setText(out.getText()+j1.compareTo(j2)+"\n");
		} else if(methodChooser.getSelectedItem() == methods[7]){
			int[] numbers = getArray(in.getText());
			out.setText(out.getText()+""+item.equals(new ResizableArray(numbers))+"\n");
		} else if(methodChooser.getSelectedItem() == methods[8]){
			int[] numbers = getArray(in.getText());
			item.append(numbers);
			out.setText(out.getText()+"Successfully added\n");
		} else if(methodChooser.getSelectedItem() == methods[9]){
			item.sort();
			out.setText(out.getText()+"Item Sorted.\n\n\n"+item.toString()+"\n");
		} else if(methodChooser.getSelectedItem() == methods[10]){
			out.setText(out.getText()+in.getText()+" is found at index "+item.indexOf(getInt(in.getText()))+"\n");
		} */
	}
	
	//use this if you need to get an int from the inputs
	private int getInt(String s){
		int n = 0;
		try{
			n = Integer.parseInt(s);
		} catch(NumberFormatException ex) {
			out.setText(out.getText()+"No detectable number.\nPlease do not put any non-number characters in the text field\nand try again.");
		}
		return n;
	}
	
	private int getInt(char c){
		return getInt(c+"");
	}
	
	private int[] getArray(String s){
		int[] temp = new int[(s.length()+1)/2];
		int l = 0;
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i) != ' '){
				temp[l] = getInt(s.charAt(i));
				l++;
			}
		}
		return temp;
	}
}