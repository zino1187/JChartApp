package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import db.DBManager;

public class MyModel extends AbstractTableModel{
	Vector<String> columnName=new Vector<String>();
	Vector<Vector> data = new Vector<Vector>();
	DBManager manager;
	Connection con;
	
	public MyModel(){
		manager = DBManager.getInstance();
		con=manager.getConnection();
		
		columnName.add("score_id");
		columnName.add("학년");
		columnName.add("성별");
		columnName.add("국어");
		columnName.add("영어");
		columnName.add("수학");
		
		getList();
	}
	
	public void getList(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from score order by score_id asc";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				Vector vec = new Vector();
				for(int i=1;i<=6;i++){
					vec.addElement(rs.getString(i));
				}
				data.addElement(vec);
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
	}
	
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnName.size();
	}
	@Override
	public String getColumnName(int col) {
		return columnName.get(col);
	}
	@Override
	public boolean isCellEditable(int row, int col) {
		return true;
	}
	@Override
	public void setValueAt(Object value, int row, int col) {
		data.elementAt(row).set(col, value);
	}
	@Override
	public Object getValueAt(int row, int col) {
		return data.elementAt(row).elementAt(col);
	}
	
}
