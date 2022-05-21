package com.tecnotree.sadeghkhanzadi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tecnotree.sadeghkhanzadi.models.CommentsModel;
import com.tecnotree.sadeghkhanzadi.modules.controller.CommentsController;
import com.tecnotree.sadeghkhanzadi.modules.service.CommentsService;
import com.tecnotree.sadeghkhanzadi.modules.service.PostsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(value = CommentsController.class)
public class CommentsConrollerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CommentsModel commentsModel;
    @MockBean
    private CommentsService commentsService;
    @MockBean
    private PostsService postsService;

    @Test
    public void testGetAllWithPagination() throws Exception{
        List<CommentsModel> commentsModels = new ArrayList<>();

        when(commentsService.findAll()).thenReturn(commentsModels);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/comments/pagination?page=2&size=10");
        ResultActions perform = mockMvc.perform(builder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(200 , status);
    }

    @Test
    public void testGetAllCommentsWithPostID() throws Exception{
        List<CommentsModel> commentsModels = new ArrayList<>();
        long postId = 1;

        when(commentsService.findAllByPostID(postId)).thenReturn(commentsModels);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/comments?postId=1");
        ResultActions perform = mockMvc.perform(builder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(200 , status);
    }

    @Test
    public void testCreateNewComments() throws Exception{
        List<CommentsModel> commentsModels = new ArrayList<>();
        CommentsModel com = new CommentsModel();
        com.setId(600);
        com.setPostId(1);
        com.setName("id labore ex et quam laborum");
        com.setEmail("Eliseo@gardner.biz");
        com.setBody("laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium");
        long postId = 1;

        when(commentsService.save_Edit(com)).thenReturn(commentsModel);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/comments");
        ResultActions perform = mockMvc.perform(builder.content(objectMapper.writeValueAsString(com)));
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(200 , status);
    }


}
