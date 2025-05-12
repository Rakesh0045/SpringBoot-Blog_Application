package com.jt.jt_blog.service;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.jt.jt_blog.model.Blog;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {
    private static final String BLOG_TABLE = "blogs";
    private final JdbcTemplate jdbcTemplate;

    public List<Blog> getBlogs(){
        var sql = "SELECT * FROM " + BLOG_TABLE;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Blog.class));

        /*

        BeanPropertyRowMapper<>() extracts a complete row or multiple separate info/field/data from DB and bind to a Blog object
        Return type of query() is List<Blogs>

        It is a child class of functional interface RowMapper having one abstract method; <T> mapRow(resultSet, rowNum)

        queryForObject --> Extract and return single object or extract single data

        Both methods used to extract data from DB
        
        */

    }

    public void addBlog(Blog blog){
        var sql = "INSERT INTO "+BLOG_TABLE+" (heading,description) VALUES(?,?)";
        jdbcTemplate.update(sql, blog.getHeading(), blog.getDescription());

        //jdbcTemplate.update(query, Object... args) --> parameters are query and the input fields of required class bind to an object
    }

    public Blog getBlogById(int id) {
        var sql = "SELECT * FROM " +BLOG_TABLE+ " WHERE id="+id;

        Blog blog = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Blog.class) );
        return blog;

        //queryForObject --> Extract and return single object or extract single data


       /* 
       
       RowMapper<Blog> rowMapper = (resultSet, rowNum) -> {
            return new Blog(
                resultSet.getInt("id"),
                resultSet.getString("heading"),
                resultSet.getString(3));
        }; 

        Blog blog = jdbcTemplate.queryForObject(sql, rowMapper);
        return blog;

        * --> Actual behind the scene implementation of <T> mapRow(resultSet, rowNum) method

        * --> Child class of RowMapper i.e BeanPropertyRowMapper<>() makes the thing easy

        * --> BeanPropertyRowMapper<> can only be used in case where the DB table fields and Class instance variable fields are same

        * --> When they are different then it can not extract field from DB and bind to the Class objects

        ✅ BeanPropertyRowMapper<>() simplifies the RowMapper implementation:

        * It is a built-in implementation of the RowMapper interface.

        * It automatically maps columns from the ResultSet to the fields of the Java object using JavaBean conventions.

        ✅ Important Note:

        * BeanPropertyRowMapper<> can **only** be used when the database table column names 
          and the Java class field names (including case sensitivity in some DBs) **exactly match**.
        
        * If column names and field names are different, BeanPropertyRowMapper will fail to bind them properly.

        * In such cases, we must use a custom RowMapper (like the one above) to manually extract and set values.
       
       */
    }

    public void delete(int id){
        var sql = "DELETE FROM %s WHERE id=?".formatted(BLOG_TABLE);
        // jdbcTemplate.update(sql.formatted(BLOG_TABLE),id);
        jdbcTemplate.update(sql,id);
    }

}
