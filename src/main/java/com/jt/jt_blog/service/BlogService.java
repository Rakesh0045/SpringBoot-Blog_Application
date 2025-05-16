package com.jt.jt_blog.service;


import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;

import com.jt.jt_blog.model.Blog;
import com.jt.jt_blog.repository.BlogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {
    private static final String BLOG_TABLE = "blog";
    private final JdbcTemplate jdbcTemplate;
    private final BlogRepository blogRepository;

    public List<Blog> getBlogs(){
        // var sql = "SELECT * FROM " + BLOG_TABLE;
        // return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Blog.class));

        var blogs = blogRepository.findAll(); //this method returns list of Blog

        //findAll() --> Extract all the rows from the table

        return blogs; 

        /*

        BeanPropertyRowMapper<>() extracts a complete row or multiple separate info/field/data from DB and bind to a Blog object
        Return type of query() is List<Blogs>

        It is a child class of functional interface RowMapper having one abstract method; <T> mapRow(resultSet, rowNum)

        queryForObject --> Extract and return single object or extract single data

        Both methods used to extract data from DB
        
        */



    }

    public void addBlog(Blog blog){

        // var sql = "INSERT INTO "+BLOG_TABLE+" (heading,description) VALUES(?,?)";
        // jdbcTemplate.update(sql, blog.getHeading(), blog.getDescription());

        //jdbcTemplate.update(query, Object... args) --> parameters are query and the input fields of required class bind to an object
    
        Blog savedBlog = blogRepository.save(blog); 
        // save(Entity S) --> pass the ref of entity object. It insert a row into the table

    }

    public Blog getBlogById(int id) {
        // var sql = "SELECT * FROM " +BLOG_TABLE+ " WHERE id="+id;

        // Blog blog = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Blog.class) );
        // return blog;

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

        // Optional<Blog> optionalBlog = blogRepository.findById(id);

        //findById() --> returns a Optional object which avoid NullPointerException if we r fetching from null id

        // if(optionalBlog.isPresent()){
        //     Blog existingBlog = optionalBlog.get();

        //     //fetch object if the optionalBlog is not null

        //     return existingBlog;
        // }else{
        //     throw new RuntimeException("Blog does not exist with id "+id);
        // }

        // if(!optionalBlog.isPresent()){
        //     throw new RuntimeException("Blog does not exist with id "+id);
        // }

        // return optionalBlog.get(); 

        return blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog does not exist with id: "+id));

        // findById() --> It extracts the row by matching the primary key

    }

    public void delete(int id){
        // var sql = "DELETE FROM %s WHERE id=?".formatted(BLOG_TABLE);
        // jdbcTemplate.update(sql.formatted(BLOG_TABLE),id);
        // jdbcTemplate.update(sql,id);

        Blog existingBlog = getBlogById(id);
        blogRepository.delete(existingBlog);
    }

    public void updateBlog(Blog newBlog) {
    //    var id = blog.getId();
    //    var heading = blog.getHeading();
    //    var description = blog.getDescription();

    //    var sql = "UPDATE %s SET heading = ? , description = ? WHERE id = ?".formatted(BLOG_TABLE);
    //    jdbcTemplate.update(sql,heading,description,id);

        // Blog existinBlog = getBlogById(newBlog.getId());

        // existinBlog.setHeading(newBlog.getHeading());
        // existinBlog.setDescription(newBlog.getDescription());

        // blogRepository.save(existinBlog);

        blogRepository.save(newBlog); //If newBlog already exists then the fields get updated, if it is a new one, then it is inserted as new row

    }

}
