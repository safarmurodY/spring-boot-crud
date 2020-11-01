package com.dataprizma.demoblog.controllers;

import com.dataprizma.demoblog.models.Post;
import com.dataprizma.demoblog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository repository;

    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<Post> posts = repository.findAll();
        model.addAttribute("post", posts);
        return "blog-main";
    }
    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }
    @PostMapping("blog/add")
    public String blogAddPost(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text, Model model){
        Post posts = new Post(title, anons, full_text);
        repository.save(posts);
        return "redirect:/blog";

    }
    @GetMapping("/blog/{id}")
    public String blogInfo(@PathVariable(value = "id") long id,  Model model){
        if (!repository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = repository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-info";
    }
    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id,  Model model){
        if (!repository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = repository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }
    @PostMapping("blog/{id}/edit")
    public String blogUpdate(@PathVariable(value = "id") long id,
                              @RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text, Model model){
        Post posts = repository.findById(id).orElseThrow();
        posts.setTitle(title);
        posts.setAnons(anons);
        posts.setFull_text(full_text);
        repository.save(posts);
        return "redirect:/blog";
    }
    @PostMapping("blog/{id}/remove")
    public String blogAddPost(@PathVariable(value = "id") long id, Model model){
        //Post posts = repository.findById(id).orElseThrow();
        repository.deleteById(id);
        return "redirect:/blog";

    }
}
