package com.issuetracker.issue.domain;

import java.util.Optional;

public interface IssueRepository {

	Long save(Issue issue);

	IssuesCountData findAllCount();

	int updateOpen(long id, boolean isOpe);
}
