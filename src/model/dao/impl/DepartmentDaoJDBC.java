package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	// INSTANCE OF CONNECTION:
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	// INSERT:
	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO department (name) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			int rows = st.executeUpdate();
			if(rows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				rs.next();
				System.out.println("Rows affected: " + rows);
				System.out.println("New Depertment ID created: " + rs.getInt(1));
				DB.closeResultSet(rs);
			}
		}catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE department SET name = ? WHERE id = ? ",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			int rows = st.executeUpdate();
			if(rows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					System.out.println("Updated!");
					System.out.println("Rows affected: " + rows);
					System.out.println("ID of updated item: " + rs.getInt(1));
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DBException("Unexpected error! No rows affected.");
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
	public void delete(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE id = ?");
			st.setInt(1, id);
			int rows = st.executeUpdate();
			if (rows > 0) {
				System.out.println(rows + " row(s) deleted.");
			} else {
				throw new DBException("Unexpected error! No rows deleted. Verify if given ID exists.");
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT *  FROM department AS d WHERE d.id = ? ");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = createDepartmentInstance(rs);
				return dep;
			} else {
				throw new DBException("Unexpected error! Id may not exists.");
			}
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
	public List<Department> findAll() {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("SELECT * FROM department ORDER BY name");
			ResultSet rs = st.executeQuery();
			List<Department> list = new ArrayList<>();
			while (rs.next()) {
				Department dep = createDepartmentInstance(rs);
				list.add(dep);
			}
			DB.closeResultSet(rs);
			return list;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}
	
	private static Department createDepartmentInstance(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("id"));
		dep.setName(rs.getString("name"));
		return dep;
	}
	
}
