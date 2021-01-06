package com.krisi.inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TaskServiceApplicationTests {

	private static final String K = "K";

	private static final String DESCRIPTION_FOR = "description for ";
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private TaskRepository taskRepository;

	private final String[] testNames = { "Buy croassants", "Drink coffee" };
	private final String[] subTaskNames = {"Make coffee","Enjoy Coffee"};

	private static final ObjectMapper MAPPER = new ObjectMapper()
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

	@Before
	public void before() throws Exception {
		
		List<String> subTasks = new ArrayList<String>();
		Stream.of(subTaskNames).forEach(subTaskName -> {
			final Task subTask = new Task();
			subTask.setName(subTaskName);
			subTask.setStatus(Status.NEW);
			subTask.setSubTask(true);
			Mockito.when(taskRepository.save(subTask)).thenReturn(subTask);
			Task createdSubTask = taskRepository.save(subTask);
			subTasks.add(createdSubTask.getId());
		});

		Stream.of(testNames).forEach(n -> {
			final Task task = generateTask(subTasks, n);
			Mockito.when(taskRepository.save(task)).thenReturn(task);
			taskRepository.save(task);
		});
	}

	private Task generateTask(List<String> subTasks, String taskName) {
		final Task task = new Task();
		task.setName(taskName);
		task.setAssigneeName(K);
		task.setDescription(DESCRIPTION_FOR + taskName);
		task.setStatus(Status.NEW);
		task.setCreatedDate(Calendar.getInstance().getTime());
		task.setPriority(1);
		task.setSubTasks(subTasks);
		return task;
	}

	@Test
	public void tasksReflectedInRead() throws Exception {
		final MvcResult resultResponse = this.mvc.perform(get("/tasks/v2")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

		final MappingIterator<Object> allTasks = parseResponse(resultResponse, Task.class);
		final int index = 0;
		allTasks.forEachRemaining(t -> {
			final Task task = (Task) t;
			final String expectedTaskName = testNames[index];
			if(task.getName().equals(expectedTaskName)) {
				assertEquals(task.getDescription(), DESCRIPTION_FOR + expectedTaskName);
				assertEquals(task.getAssigneeName(), K);
				assertNotNull(task.getId());
				assertEquals(task.getStatus(), Status.NEW);
				assertEquals(task.getSubTasks().size(), subTaskNames.length);
			}
		});
	}

	@Test
	public void deleteTest() throws Exception {
		this.mvc
		.perform(delete("/tasks/v2"))
		.andExpect(status()
		.isOk()).andReturn();
	}
	
	@Test
	public void postTest() throws Exception {
		this.mvc
		.perform(post("/tasks/v2")
		.contentType(MediaType.APPLICATION_JSON)
		.content(generateTask(null, "TestTask").toJson()))
		.andExpect(status()
		.isOk()).andReturn();
	}

	@After
	public void after() {
		Stream.of(testNames).forEach(n -> {
			final List<Task> tasks = taskRepository.findByName(n);
			tasks.forEach(currentTask -> {
				taskRepository.delete(currentTask);
			});
		});
	}

	public static <T> MappingIterator<Object> parseResponse(final MvcResult result, final Class<T> responseClass) {
		try {
			final String contentAsString = result.getResponse().getContentAsString();
			return MAPPER.readerFor(responseClass).readValues(contentAsString);
		} catch (final IOException e) {
		  throw new RuntimeException(e);
		}
	  }

}
