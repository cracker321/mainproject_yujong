package com.example.seb_main_project.post.mapper;

import com.example.seb_main_project.post.dto.PostPatchDto;
import com.example.seb_main_project.post.dto.PostPostDto;
import com.example.seb_main_project.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PostMapper {

    Post postPostDtoToPost(PostPostDto postPostDto);

    Post postPatchDtoToPost(PostPatchDto postPatchDto);

    PostPostDto toPostResponseDto(Post post);

    List<PostPostDto> toPostResponseDto(List<Post> posts);
}
