package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*, department.name as depname "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.departmentid = department.id "
					+ "WHERE seller.id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Department dep = createDepartmentInstance(rs);
				Seller sel = createSellerInstance(rs, dep);
				return sel;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	private Department createDepartmentInstance(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("departmentid"));
		dep.setName(rs.getString("depname"));
		return dep;
	}
	
	private Seller createSellerInstance(ResultSet rs, Department department) throws SQLException {
		Seller sel = new Seller();
		sel.setId(rs.getInt("id"));
		sel.setName(rs.getString("name"));
		sel.setEmail(rs.getString("email"));
		sel.setBirthDate(rs.getDate("birthdate"));
		sel.setBaseSalary(rs.getDouble("basesalary"));
		sel.setDepartment(department);
		return sel;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Seller> findByDepartment(Department department) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("select s.*, d.name as depname " + "from seller as s inner join department as d "
					+ "on s.departmentid = d.id " + "where departmentid = ? " + "ORDER BY s.name");
			st.setInt(1, department.getId());
			rs = st.executeQuery();

			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("departmentid"));

				if (dep == null) {
					dep = createDepartmentInstance(rs);
					map.put(rs.getInt("departmentid"), dep);
				}

				Seller sel = createSellerInstance(rs, dep);
				list.add(sel);
			}
			return list;
			
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

}
