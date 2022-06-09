package com.nadeemarif.reddit.repository;

import com.nadeemarif.reddit.model.Post;
import com.nadeemarif.reddit.model.User;
import com.nadeemarif.reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
