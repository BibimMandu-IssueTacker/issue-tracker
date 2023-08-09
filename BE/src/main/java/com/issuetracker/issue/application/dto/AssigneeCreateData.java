package com.issuetracker.issue.application.dto;

import com.issuetracker.issue.domain.Assignee;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AssigneeCreateData {

	private Long issueId;
	private Long memberId;

	public Assignee toAssignee() {
		return Assignee.builder()
			.issueId(issueId)
			.memberId(memberId)
			.build();
	}
}
