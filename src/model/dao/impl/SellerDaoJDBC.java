package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO seller (name, email, birthdate, basesalary, departmentid) "
					+ "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DBException("Unexpected error! Row not created.");
			}

		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Seller obj) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("UPDATE seller "
					+ "SET name = ?, email = ?, birthdate = ?, basesalary = ?, departmentid = ? "
					+ "WHERE seller.id = ?", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new Date(obj.getBirthDate().getTime()));
			st.setDouble(4, 2500.0);
			st.setObject(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
	
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
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
			st = conn.prepareStatement(
					"SELECT seller.*, department.name as depname " + "FROM seller INNER JOIN department "
							+ "ON seller.departmentid = department.id " + "WHERE seller.id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Department dep = createDepartmentInstance(rs);
				Seller sel = createSellerInstance(rs, dep);
				return sel;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT s.*, d.name as depname " + "FROM seller as s INNER JOIN department as d "
					+ "ON s.departmentid = d.id " + "ORDER BY s.name");

			rs = st.executeQuery();
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> mapDep = new HashMap<>();

			while (rs.next()) {
				Department dep = mapDep.get(rs.getInt("departmentid"));
				if (dep == null) {
					dep = createDepartmentInstance(rs);
					mapDep.put(rs.getInt("departmentid"), dep);
					System.out.println(dep);
				}
				Seller sel = createSellerInstance(rs, dep);
				list.add(sel);
			}
			return list;

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("select s.*, d.name as depname " 
		+ "from seller as s inner join department as d "		
		+ "on s.departmentid = d.id " 
		+ "where departmentid = ? " 
		+ "ORDER BY s.name");
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

}
