package com.blog_post.service;


import com.blog_post.exception.ResourceNotFoundException;
import com.blog_post.model.BlogPost;
import com.blog_post.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;
    @Transactional
    public BlogPost createBlogPost(BlogPost blogPost) {

        return blogPostRepository.save(blogPost);
    }

    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    public Optional<BlogPost> getBlogPostById(Long id) {
        return blogPostRepository.findById(id);
    }

    public BlogPost updateBlogPost(Long id, BlogPost blogPostDetails) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("BlogPost", "id", id));
        blogPost.setTitle(blogPostDetails.getTitle());
        blogPost.setContent(blogPostDetails.getContent());
        return blogPostRepository.save(blogPost);
    }

    public void deleteBlogPost(Long id) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("BlogPost", "id", id));
        blogPostRepository.delete(blogPost);
    }
}