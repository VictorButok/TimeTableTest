package org.itstep.service;

import static org.junit.Assert.*;

import java.util.List;

import org.itstep.ApplicationRunner;
import org.itstep.dao.SubjectDAO;
import org.itstep.dao.TeacherDAO;
import org.itstep.model.Subject;
import org.itstep.model.Teacher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TeacherServiceTest {

	Subject subjectInDB;
	@Autowired
	TeacherService service;
	@Autowired
	TeacherDAO teacherDAO;
	@Autowired
	SubjectDAO subjectDAO;
	@Before
	public void setDataToDB() {
		Subject subject = new Subject();
		subject.setName("C++");
		Subject subjectInDB = subjectDAO.save(subject);
		for(int i=0; i<3; i++) {
			Teacher teacher = new Teacher();
			teacher.setLogin("ignatenko"+(i+1));
			teacher.setSubject(subjectInDB);
			teacher.setPassword("123456");
			teacher.setFirstName("Alex");
			teacher.setSecondName("Ignatenko");
			teacherDAO.save(teacher);
	 }
	}
	
	@Test
	public void testFindAllBySubject() {
		List<Teacher> teachers = teacherDAO.findAllBySubject(subjectInDB.getName());
		
		assertNotNull(teachers);
		assertEquals(3, teachers.size());
	}
	
	@After
	public void deleteDataFromDB() {
		for(Teacher teacher : teacherDAO.findAllBySubject("C++")) {
			teacherDAO.delete(teacher);
		}
		subjectDAO.delete(subjectInDB);
	}

}
