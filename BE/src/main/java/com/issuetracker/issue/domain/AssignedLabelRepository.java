package com.issuetracker.issue.domain;

import java.util.List;

import com.issuetracker.label.domain.Label;

public interface AssignedLabelRepository {

	int[] saveAll(List<AssignedLabel> assignedLabels);

	List<Label> findAll();

	List<Label> findAllAssignedToIssue(long issueId);

	List<Label> findAllUnassignedToIssue(long issueId);
}
