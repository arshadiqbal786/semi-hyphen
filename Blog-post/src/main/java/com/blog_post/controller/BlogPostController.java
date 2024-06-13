package com.blog_post.controller;


import com.blog_post.exception.ResourceNotFoundException;
import com.blog_post.model.BlogPost;
import com.blog_post.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createBlogPost(@Valid  @RequestBody BlogPost blogPost,BindingResult result) {
if (result.hasErrors()){
    return  new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
}
        return new ResponseEntity<>(blogPostService.createBlogPost(blogPost),HttpStatus.CREATED) ;
    }

    @GetMapping
    public List<BlogPost> getAllBlogPosts() {
        return blogPostService.getAllBlogPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getBlogPostById(@PathVariable(value = "id") Long blogPostId) {
        BlogPost blogPost = blogPostService.getBlogPostById(blogPostId).orElseThrow(() -> new ResourceNotFoundException("BlogPost", "id", blogPostId));
        return ResponseEntity.ok().body(blogPost);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable(value = "id") Long blogPostId, @Valid @RequestBody BlogPost blogPostDetails) {
        BlogPost updatedBlogPost = blogPostService.updateBlogPost(blogPostId, blogPostDetails);
        return ResponseEntity.ok(updatedBlogPost);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteBlogPost(@PathVariable(value = "id") Long blogPostId) {
        blogPostService.deleteBlogPost(blogPostId);
        return ResponseEntity.ok().build();
    }
}