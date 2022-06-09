package com.nadeemarif.reddit.service;

import com.nadeemarif.reddit.dto.SubredditDto;
import com.nadeemarif.reddit.exceptions.SpringRedditException;
import com.nadeemarif.reddit.mappers.SubredditMapper;
import com.nadeemarif.reddit.model.Subreddit;
import com.nadeemarif.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubRedditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto request){
        Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(request));
        request.setId(subreddit.getId());
        return request;
    }

//    private Subreddit mapSubredditDto(SubredditDto request) {
//        return Subreddit.builder()
//                .name(request.getName())
//                .description(request.getDescription())
//                .build();
//    }
//
//    private SubredditDto mapToDto (Subreddit subreddit){
//        return SubredditDto.builder()
//                .name(subreddit.getName())
//                .id(subreddit.getId())
//                .description(subreddit.getDescription())
//                .numberOfPosts(subreddit.getPosts().size())
//                .build();
//    }

    @Transactional
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit =  subredditRepository
                                        .findById(id)
                                        .orElseThrow(() -> new SpringRedditException("Subreddit not found !"));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
