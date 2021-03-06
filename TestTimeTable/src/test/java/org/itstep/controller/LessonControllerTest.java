package org.itstep.controller;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.itstep.ApplicationRunner;
import org.itstep.model.Group;
import org.itstep.model.Lesson;
import org.itstep.model.Subject;
import org.itstep.model.Teacher;
import org.itstep.service.LessonService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LessonControllerTest {

	List<Lesson> lessons = new ArrayList<Lesson>();

	@Autowired
	TestRestTemplate restTemplate;

	@MockBean
	LessonService lessonService;

	@Before
	public void setPreData() {

		Group group = new Group();
		group.setCourse("TestCourse");
		group.setName("TestName");
		group.setSpecialization("TestSpec");

		Subject subject = new Subject();
		subject.setName("QWAS");

		Teacher teacher = new Teacher();

		teacher.setLogin("TestLogin");
		teacher.setPassword("TestPassword");
		teacher.setFirstName("TestFirstName");
		teacher.setSecondName("TestSecondName");
		teacher.setSubject(subject);

		for (int i = 1; i <= 3; i++) {
			Lesson lesson = new Lesson();

			lesson.setCabinet("TestCabinet" + i);
			lesson.setGroup(group);
			lesson.setSubject(subject);
			lesson.setTeacher(teacher);
			lesson.setStartTime((long) (i*45));

			lessons.add(lesson);
		}

	}

	@Test
	public void testSave() throws URISyntaxException {
		
		Mockito.when(lessonService.save(Mockito.any(Lesson.class))).thenReturn(lessons.get(0));
		
		
		RequestEntity<Lesson> request = new RequestEntity<Lesson>(lessons.get(0), HttpMethod.POST, new URI("/lesson"));
		ResponseEntity<Lesson> response = restTemplate.exchange(request, Lesson.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("QWAS", response.getBody().getSubject().getName());

		Mockito.verify(lessonService, Mockito.times(1)).save(Mockito.any(Lesson.class));
	}
	
	@Test
	public void testDelete() throws URISyntaxException {
		Mockito.doNothing().when(lessonService).delete(Mockito.any(Lesson.class));
		
		RequestEntity<Lesson> request = new RequestEntity<Lesson>(lessons.get(0), HttpMethod.DELETE, new URI("/lesson"));
		ResponseEntity response = restTemplate.exchange(request, HttpStatus.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Mockito.verify(lessonService, Mockito.times(1)).delete(Mockito.any(Lesson.class));
	}

	@Test
	public void testGetList() throws URISyntaxException {
		Mockito.when(lessonService.findAllByStartTime(Mockito.anyLong(), Mockito.anyLong())).thenReturn(lessons);
		HttpHeaders headers = new HttpHeaders();
		headers.add("start", "44");
		headers.add("end", "100");

		RequestEntity request = new RequestEntity(headers, HttpMethod.GET, new URI("/lesson/get-all-by-time"));
		ResponseEntity<List> response = restTemplate.exchange(request, List.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Mockito.verify(lessonService, Mockito.times(1)).findAllByStartTime(Mockito.anyLong(), Mockito.anyLong());
	}
//
//	@Test
//	public void testUpdate() {
//	}
//
//	@Test
//	public void testGetOne() {
//	}

}
