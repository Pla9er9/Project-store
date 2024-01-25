package com.example.projectstore.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IssueCommentRepository extends JpaRepository<IssueComment, UUID> {
}
