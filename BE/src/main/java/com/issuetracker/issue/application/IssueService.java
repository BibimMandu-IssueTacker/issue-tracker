package com.issuetracker.issue.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.issue.application.dto.IssueAssigneeInformation;
import com.issuetracker.issue.application.dto.IssueCreateInformation;
import com.issuetracker.issue.application.dto.IssueCreateInputData;
import com.issuetracker.issue.application.dto.IssueLabelMappingInformation;
import com.issuetracker.issue.application.dto.IssueSearchInformation;
import com.issuetracker.issue.application.dto.IssueSearchInputData;
import com.issuetracker.issue.application.dto.IssuesCountInformation;
import com.issuetracker.issue.domain.AssignedLabelRepository;
import com.issuetracker.issue.domain.AssigneeRepository;
import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.domain.IssueMapper;
import com.issuetracker.issue.domain.IssueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueService {

	private final IssueValidator issueValidator;
	private final IssueMapper issueMapper;
	private final IssueRepository issueRepository;
	private final AssigneeRepository assigneeRepository;
	private final AssignedLabelRepository assignedLabelRepository;

	@Transactional
	public IssueCreateInformation create(IssueCreateInputData issueCreateData) {
		issueValidator.verifyCreate(issueCreateData);

		Issue issue = issueCreateData.toIssue(LocalDateTime.now());
		Long savedId = issueRepository.save(issue);
		assigneeRepository.saveAll(issueCreateData.toAssignees(savedId));
		assignedLabelRepository.saveAll(issueCreateData.toAssignedLabel(savedId));
		return IssueCreateInformation.from(savedId);
	}

	public IssuesCountInformation findIssuesCount() {
		return IssuesCountInformation.from(issueRepository.findAllCount());
	}

	public List<IssueSearchInformation> search(IssueSearchInputData issueSearchInputData) {
		return IssueSearchInformation.from(issueMapper.search(issueSearchInputData.toIssueSearch()));
	}

	public List<IssueAssigneeInformation> searchAssignee() {
		return IssueAssigneeInformation.from(assigneeRepository.findAll());
	}

	public List<IssueLabelMappingInformation> searchAssignedLabel() {
		return IssueLabelMappingInformation.from(assignedLabelRepository.findAll());
	}
}
