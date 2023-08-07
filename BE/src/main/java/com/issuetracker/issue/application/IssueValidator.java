package com.issuetracker.issue.application;

import org.springframework.stereotype.Component;

import com.issuetracker.issue.application.dto.IssueCreateInputData;
import com.issuetracker.label.application.LabelValidator;
import com.issuetracker.member.application.MemberValidator;
import com.issuetracker.milestone.application.MilestoneValidator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IssueValidator {

	private final MilestoneValidator milestoneValidator;
	private final MemberValidator memberValidator;
	private final LabelValidator labelValidator;

	public void verifyCreate(IssueCreateInputData issueCreateInputData) {
		milestoneValidator.verifyMilestone(issueCreateInputData.getMilestoneId());
		memberValidator.verifyMember(issueCreateInputData.getAuthorId());
		memberValidator.verifyMembers(issueCreateInputData.getAssigneeIds());
		labelValidator.verifyLabels(issueCreateInputData.getLabelIds());
	}
}
