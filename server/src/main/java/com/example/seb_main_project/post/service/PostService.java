package com.example.seb_main_project.post.service;


import com.example.seb_main_project.exception.BusinessLogicException;
import com.example.seb_main_project.exception.ExceptionCode;
import com.example.seb_main_project.member.entity.Member;
import com.example.seb_main_project.member.repository.MemberRepository;
import com.example.seb_main_project.post.entity.Post;
import com.example.seb_main_project.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    public Page<Post> showPosts(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());

        return postRepository.findAll(pageRequest);

    }


    public Post findPost(Integer postId) {

        return findVerifiedPost(postId);
    }

    private Post findVerifiedPost(Integer postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        return optionalPost.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
    }


    public Post createPost(Post post, Integer memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        post.setMember(member);

        return postRepository.save(post);

    }

    public Post updatePost(Post post) {
        Post showPost = findVerifiedPost(post.getPostId());

        Optional.ofNullable(post.getTitle())
                .ifPresent(showPost::setTitle);
        Optional.ofNullable(post.getContents())
                .ifPresent(showPost::setContents);
        Optional.ofNullable(post.getTags())
                .ifPresent(showPost::setTags);

        return postRepository.save(showPost);
    }


    public void deletePost(Integer postId) {

        Post post = findVerifiedPost(postId);
        postRepository.delete(post);
    }
}
