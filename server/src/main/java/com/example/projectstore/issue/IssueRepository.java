package com.example.projectstore.issue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssueRepository extends JpaRepository<Issue, UUID> {
    Page<Issue> findAllByProjectId(UUID projectId, Pageable page);

    List<Issue> findTop10ByCreatedBy_Username(@Param("username") String username);
}
