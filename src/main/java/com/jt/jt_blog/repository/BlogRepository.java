package com.jt.jt_blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jt.jt_blog.model.Blog;

public interface BlogRepository extends JpaRepository<Blog,Integer> {

}