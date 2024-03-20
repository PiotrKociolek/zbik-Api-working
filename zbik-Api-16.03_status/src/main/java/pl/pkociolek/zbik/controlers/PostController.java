package pl.pkociolek.zbik.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pkociolek.zbik.model.PostVisibility;
import pl.pkociolek.zbik.model.dtos.request.CreateOrUpdatePostDto;
import pl.pkociolek.zbik.model.dtos.response.PostResponseDto;
import pl.pkociolek.zbik.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;


    @PutMapping(value = "/posts/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void createPost(@ModelAttribute final CreateOrUpdatePostDto createOrUpdatePostDto) {
        postService.createPost(createOrUpdatePostDto);
    }
    @PutMapping(value = "/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void updatePost(@PathVariable(value = "postId") String postId,
                           @ModelAttribute final CreateOrUpdatePostDto createOrUpdatePostDto) {
        createOrUpdatePostDto.setId(postId);
        postService.updatePost(createOrUpdatePostDto);
    }
    @DeleteMapping(value = "/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value =HttpStatus.OK )
    public void deletePost(@PathVariable final String postId){
        postService.deletePost(postId);
    }
    @GetMapping(value = "/posts/{postId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public PostResponseDto getPostsById(@PathVariable("postId") String postId){
       return postService.getPostById(postId);
    }
    @GetMapping(value = "/posts/list/{size}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Page<PostResponseDto> getAllPosts(@PathVariable("size") final int size, @PathVariable("page") final int page){
        return postService.getAllPosts(PageRequest.of(page,size));
    }

    @GetMapping(value = "/posts/list/visibility/{postVisibility}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    Page<PostResponseDto> getPostsByVisibility( @PathVariable("postVisibility") PostVisibility postVisibility, Pageable pageable){
      return postService.getPostsByVisibility(postVisibility,pageable);
    };

}
