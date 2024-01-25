package com.example.projectstore.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssueRepository extends JpaRepository<Issue, UUID> {
    List<Issue> findAllByProjectId(UUID projectId);
}
