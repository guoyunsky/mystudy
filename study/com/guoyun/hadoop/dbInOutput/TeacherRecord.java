package com.guoyun.hadoop.dbInOutput;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;
/**
 * represent one row in table
 * @author guoyun
 *
 */
public class TeacherRecord implements DBWritable, Writable {
	private int id;
	private String name;
	private int age;
	private int departmentID;

	@Override
	public void readFields(ResultSet rs) throws SQLException {
		this.id=rs.getInt(1);
		this.name=rs.getString(2);
		this.age=rs.getInt(3);
		this.departmentID=rs.getInt(4);
	}

	@Override
	public void write(PreparedStatement ps) throws SQLException {
		ps.setInt(1, this.id);
		ps.setString(2, this.name);
		ps.setInt(3, this.age);
		ps.setInt(4, this.departmentID);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.id=in.readInt();
		this.name=Text.readString(in);
		this.age=in.readInt();
		this.departmentID=in.readInt();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(this.id);
		Text.writeString(out, this.name);
		out.writeInt(this.age);
		out.writeInt(this.departmentID);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		return (this.name.concat("\t")+this.age).concat("\t")+this.departmentID;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}
	
	

}
