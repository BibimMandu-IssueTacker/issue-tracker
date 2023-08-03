package com.issuetracker.issue.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.issue.application.dto.AuthorSearchInformation;
import com.issuetracker.issue.application.dto.IssueCreateInputData;
import com.issuetracker.issue.application.dto.IssueCreateInformation;
import com.issuetracker.issue.application.dto.IssueSearchInputData;
import com.issuetracker.issue.application.dto.IssueSearchInformation;
import com.issuetracker.issue.domain.IssueMapper;
import com.issuetracker.issue.infrastrucure.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class IssueService {

	private final IssueMapper issueMapper;
	private final IssueCreator issueCreator;
	private final AuthorRepository authorRepository;

	@Transactional(readOnly = true)
	public List<IssueSearchInformation> search(IssueSearchInputData issueSearchData) {
		return IssueSearchInformation.from(issueMapper.search(issueSearchData.toIssueSearch()));
	}

	public IssueCreateInformation create(IssueCreateInputData issueCreateData) {
		return IssueCreateInformation.from(issueCreator.create(issueCreateData));
	}

	public List<AuthorSearchInformation> searchAuthors() {
		return AuthorSearchInformation.from(authorRepository.search());
	}
}
