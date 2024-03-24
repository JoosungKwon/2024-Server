package com.example.demo.src.post.repository;

import com.example.demo.src.admin.model.request.AdminPostSearchReq;
import com.example.demo.src.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> findAllBy(AdminPostSearchReq adminPostSearchReq, Pageable page);
}
