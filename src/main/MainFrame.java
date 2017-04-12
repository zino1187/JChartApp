package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import db.DBManager;

public class MainFrame extends JFrame implements ActionListener{
	JPanel p_center, p_east, p_south;
	JTable table;
	JScrollPane scroll;
	JButton bt_save, bt_open, bt_result;
	MyModel model;
	DBManager manager=DBManager.getInstance();
	
	public MainFrame() {
		p_center = new JPanel();
		p_east = new PieChartPanel(manager);
		p_south = new JPanel();
		table = new JTable(model = new MyModel());
		scroll = new JScrollPane(table);
		bt_open = new JButton("열기");
		bt_save = new JButton("저장");
		bt_result = new JButton("그래프");
		
		p_east.setPreferredSize(new Dimension(950-500, 700));
		p_center.add(scroll);
		p_south.add(bt_open);
		p_south.add(bt_save);
		p_south.add(bt_result);
		
		add(p_center);
		add(p_east, BorderLayout.EAST);
		add(p_south, BorderLayout.SOUTH);
		
		
		bt_open.addActionListener(this);
		bt_save.addActionListener(this);
		bt_result.addActionListener(this);
		
		setSize(950, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	//엑셀 대량 등록 
	public void open(){
		
	}
	
	//엑셀에 저장
	public void save(){
		
	}
	
	//통계 보기 
	public void showGraph(){
		p_east.add(((PieChartPanel)p_east).getPiePanel());
		p_east.updateUI();
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		
		if(obj == bt_open){
			open();
		}else if(obj ==bt_save){
			save();
		}else if(obj==bt_result){
			showGraph();
		}
		
	}
	public static void main(String[] args) {
		new MainFrame();
	}
}
