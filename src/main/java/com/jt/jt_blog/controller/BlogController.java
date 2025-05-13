package com.jt.jt_blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.jt.jt_blog.model.Blog;
import com.jt.jt_blog.service.BlogService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor // -> DI of BlogService injected into BlogController
public class BlogController {
    private final BlogService blogService;

    // @GetMapping("/")
    @GetMapping
    public String home(Model model){
        model.addAttribute("blogs", blogService.getBlogs());
        return "home";
    }

    @GetMapping("/form") //mapping
    public String form(){ //method name
        return "add-blog"; //html file
    }

    // @GetMapping("/add-blog") --> GET Exposes the query parameters
    @PostMapping("/add-blog") //POST METHOD secures the query parameters field, input form field must have POST method specified
    public String addBlog(@ModelAttribute Blog blog){
        // @RequestParam String heading, @RequestParam String description  --> @ModelAttribute better choice as it extract all parameters at once using the reference of concerned class

        //The name attribute of input query parameter field and the instance variable field of Blog class must be same otherwise @ModelAttribute will not work.  
        blogService.addBlog(blog);
        return "redirect:/"; //Explicitly maps the endpoint to / mapping
    }

    @GetMapping("/blog/{id}")
    public String getBlog(@PathVariable int id, Model model){
       
        var blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);

        return "blog-detail";
    }

    @GetMapping("/blog/delete/{id}")
    public String deleteBlog(@PathVariable int id, Model model){
        blogService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/blog/edit/{id}")
    public String editBlog(@PathVariable int id, Model model){
        var blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        return "edit-blog";
    }

    @PostMapping("/update-blog")
    public String updateBlog(@ModelAttribute Blog blog){
        blogService.updateBlog(blog);
        return "redirect:/";
    }
    
}
