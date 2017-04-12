package main;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import db.DBManager;

public class PieChartPanel extends JPanel{
	JFreeChart chart;
	DBManager manager;
	
	public PieChartPanel(DBManager manager){
		this.manager=manager;
	}
	
	//�г⺰ ������ 
	public HashMap getGroupByGrade(){
		Connection con=manager.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		HashMap<String, Integer> map=new HashMap<String, Integer>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select grade, (count(grade)/(select count(*) from score))*100 as ����");
		sql.append(" from score");
		sql.append(" group by grade");
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				map.put(rs.getInt("grade")+"�г�", rs.getInt("����"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		return map;		
	}
	
	public ChartPanel getPiePanel(){
		DefaultPieDataset dataSet = new DefaultPieDataset();
		try {
			
			HashMap<String, Integer> map=getGroupByGrade();
			Set set = map.keySet();
			
			Iterator it=set.iterator();
			
			while(it.hasNext()){
				String key=(String)it.next();
				dataSet.setValue(key, map.get(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		chart = ChartFactory.createPieChart("���� ��� �׷���", dataSet, true, false ,false);
		
		//�ѱ� ����
		Font labelFont = chart.getTitle().getFont();
		chart.getTitle().setFont(new Font("����", labelFont.getStyle(), labelFont.getSize()));
		chart.getLegend().setItemFont(new Font("����", labelFont.getStyle(), labelFont.getSize()));
		PiePlot plot=(PiePlot)chart.getPlot();
		plot.setLabelFont(new Font("����", labelFont.getStyle(), labelFont.getSize()-5));
		
		ChartPanel chartPanel=new ChartPanel(chart);
		return chartPanel;
	}
}
