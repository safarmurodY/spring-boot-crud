package com.dataprizma.demoblog.repo;

import com.dataprizma.demoblog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
