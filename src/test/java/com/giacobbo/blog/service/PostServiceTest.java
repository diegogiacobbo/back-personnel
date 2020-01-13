package com.giacobbo.blog.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.giacobbo.blog.builder.PostFactory;
import com.giacobbo.blog.dto.PostDtoResponse;
import com.giacobbo.blog.model.Post;
import com.giacobbo.blog.repository.PostRepository;

@RunWith(SpringRunner.class)
public class PostServiceTest extends AbstractServiceTest {

	@Autowired
	private PostService postService;

	@Autowired
	private PostRepository postRepository;
	
	private LocalDateTime local = LocalDateTime.now();

	private Post post;
	
	@Before
	public void contextTests() {
		this.post = postRepository.save(PostFactory.create("Test title", "Test content", local, "hello word image", "hello word code"));
	}
	

	@Test
	public void shouldReturnPublicPost() {
		assertEquals(new ArrayList<PostDtoResponse>(), postService.findPublicPosts());
		postRepository.save(PostFactory.createPublic("Test title", "Test content", local));
		assertNotNull(postService.findPublicPosts());
	}
	
	@Test
	public void shouldReturnLastPost() {
		assertNotNull(postService.findLastPost());
	}
	
	@Test
	public void shouldReturnAllPost() {
		assertNotNull(postService.findAll());
	}

	@Test
	public void shouldReturnCreatedPost() {
		assertNotNull("Post shouldn't be null", post);
		assertThat(post.getContent(), equalTo("Test content"));
		assertThat(post.getTitle(), equalTo("Test title"));
		assertThat(post.getCreationDate(), equalTo(local));
		assertThat(post.getImage(), equalTo(Base64.getEncoder().encode("hello word image".getBytes())));
		assertThat(post.getContent_code(), equalTo("hello word code"));
	}

	@Test
	public void shouldReturnNullForNotExistingPost() {
		assertNotNull(postService.getPost(post.getId().toString()));
	}
	
}