package com.krisi.inventory;

import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TaskServiceApplicationTests {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Before
	public void before() throws Exception {
		Stream.of("Buy croassants","Drink coffee")
			.forEach(n -> {
				Task task = new Task();
				task.setName(n);
				taskRepository.save(task);
			});
	}
	
	@Test
	public void tasksReflectedInRead() throws Exception {
		MediaType json = MediaType.parseMediaType("application/json");
		this.mvc
		.perform(get("/tasks/v2"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(json))
		.andExpect(
				mvcResult -> {
					MockHttpServletResponse response = mvcResult.getResponse();
					System.out.println(response.getContentAsString());
				});
	}

}
