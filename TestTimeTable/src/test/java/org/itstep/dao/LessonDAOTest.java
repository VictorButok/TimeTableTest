package org.itstep.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.itstep.ApplicationRunner;
import org.itstep.model.Group;
import org.itstep.model.Lesson;
import org.itstep.model.Teacher;
import org.itstep.service.TeacherService;
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
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LessonDAOTest {
	Lesson lessonInDB;
	
	
	
	@Autowired
	LessonDAO lessonDAO;
	
	@Before
	public void setLessonDB() {
	   Group testGroup = new Group();
	   
		
		
	   Lesson lesson = new Lesson();
	   lesson.setId(12); 
	   lesson.setStartTime(System.currentTimeMillis());
	   lessonInDB = lessonDAO(lesson);
	   
	}
}
