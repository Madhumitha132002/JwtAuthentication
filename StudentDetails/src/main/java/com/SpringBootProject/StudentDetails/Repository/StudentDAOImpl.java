package com.SpringBootProject.StudentDetails.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.SpringBootProject.StudentDetails.Model.StudentModel;


import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {

    private static final Logger logger = LoggerFactory.getLogger(StudentDAOImpl.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addStudentDetails(StudentModel studentModel) {
        try {
            String insertQuery = "INSERT INTO Student VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
            return jdbcTemplate.update(insertQuery, studentModel.getStudentId(), studentModel.getName(), studentModel.getRegisterNo(), studentModel.getGender(),
                    studentModel.getAge(), studentModel.getPhoneNumber(), studentModel.getCurrentStatus(),
                    studentModel.getEmailId(), studentModel.getCourse(), studentModel.getBatch(), studentModel.getFees(),studentModel.getRole(),studentModel.getPassword());
        } catch (Exception e) {
            logger.error("Error occurred while adding student details: {}", e.getMessage());
            return -1; // Indicate failure
        }
    }

    @Override
    public int deleteStudentDetails(int studentId) {
        try {
            String deleteQuery = "DELETE FROM Student WHERE Student_id = ?";
            return jdbcTemplate.update(deleteQuery, studentId);
        } catch (Exception e) {
            logger.error("Error occurred while deleting student details: {}", e.getMessage());
            return -1; // Indicate failure
        }
    }

    @Override
    public int updateStudentDetails(StudentModel studentModel) {
        try {
            String updateQuery = "UPDATE Student SET Name=?, Register_No=?, Gender=?, Age=?, PhoneNumber=?, Current_Status=?, Email_Id=?, Course=?, Batch=?, Fees=? WHERE Student_id=?";
            return jdbcTemplate.update(updateQuery, studentModel.getName(), studentModel.getRegisterNo(), studentModel.getGender(),
                    studentModel.getAge(), studentModel.getPhoneNumber(), studentModel.getCurrentStatus(),
                    studentModel.getEmailId(), studentModel.getCourse(), studentModel.getBatch(), studentModel.getFees(), studentModel.getStudentId());
        } catch (Exception e) {
            logger.error("Error occurred while updating student details: {}", e.getMessage());
            return -1; // Indicate failure
        }
    }

    @Override
    public StudentModel findById(int studentId) {
        try {
            String selectQuery = "SELECT * FROM Student WHERE Student_id = ?";
            //simplifies this mapping process by automatically mapping columns in the ResultSet to properties of a JavaBean class
            List<StudentModel> students = jdbcTemplate.query(selectQuery, new BeanPropertyRowMapper<>(StudentModel.class), studentId);
            if (students.isEmpty()) {
                logger.info("Student not found with ID: {}", studentId);
                return null;
            } else {
                return students.get(0); // Assuming only one student should be found with the given ID
            }
        } catch (Exception e) {
            logger.error("Error occurred while finding student by ID: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public List<StudentModel> getAllStudentDetails() {
        try {
            String selectQuery = "SELECT * FROM Student";
            return jdbcTemplate.query(selectQuery, new BeanPropertyRowMapper<>(StudentModel.class));
        } catch (Exception e) {
            logger.error("Error occurred while retrieving all student details: {}", e.getMessage());
            return null;
        }
    }

	@Override
	public StudentModel findByUsername(String name) {
		try {
            String selectQuery = "SELECT * FROM Student WHERE Name = ?";
            //simplifies this mapping process by automatically mapping columns in the ResultSet to properties of a JavaBean class
            List<StudentModel> students = jdbcTemplate.query(selectQuery, new BeanPropertyRowMapper<>(StudentModel.class), name);
            if (students.isEmpty()) {
                logger.info("Student not found with ID: {}", name);
                return null;
            } else {
                return students.get(0); // Assuming only one student should be found with the given ID
            }
        } catch (Exception e) {
            logger.error("Error occurred while finding student by ID: {}", e.getMessage());
            return null;
        }	
	}

	
}
