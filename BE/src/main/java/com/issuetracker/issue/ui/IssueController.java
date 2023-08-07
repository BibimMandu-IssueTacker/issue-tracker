package com.issuetracker.issue.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetracker.issue.application.IssueService;
import com.issuetracker.issue.ui.dto.AuthorsSearchResponse;
import com.issuetracker.issue.ui.dto.IssueAssigneesResponse;
import com.issuetracker.issue.ui.dto.IssueCreateRequest;
import com.issuetracker.issue.ui.dto.IssueCreateResponse;
import com.issuetracker.issue.ui.dto.IssueLabelMappingsResponse;
import com.issuetracker.issue.ui.dto.IssueSearchRequest;
import com.issuetracker.issue.ui.dto.IssuesSearchResponse;
import com.issuetracker.member.application.MemberService;
import com.issuetracker.milestone.application.MilestoneService;
import com.issuetracker.milestone.ui.dto.MilestonesSearchResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

	private final IssueService issueService;
	private final MemberService memberService;
	private final MilestoneService milestoneService;

	@GetMapping
	public ResponseEntity<IssuesSearchResponse> showIssues(IssueSearchRequest issueSearchRequest) {
		IssuesSearchResponse issuesSearchResponse = IssuesSearchResponse.of(
			issueService.findIssuesCount(),
			issueService.search(issueSearchRequest.toIssueSearchData(1L))
		);
		return ResponseEntity.ok().body(issuesSearchResponse);
	}

	@PostMapping
	public ResponseEntity<IssueCreateResponse> createIssue(@RequestBody @Valid IssueCreateRequest issueCreateRequest) {
		IssueCreateResponse issueCreateResponse = IssueCreateResponse.from(issueService.create(issueCreateRequest.toIssueCreateData(1L)));
		return ResponseEntity.created(URI.create("/issues/" + issueCreateResponse.getId()))
			.body(issueCreateResponse);
	}
  
  	@GetMapping("/milestones")
	public ResponseEntity<MilestonesSearchResponse> showMilestonesForFilter() {
		return ResponseEntity.ok().body(MilestonesSearchResponse.from(milestoneService.searchMilestonesForFilter()));
	}

	@GetMapping("/authors")
	public ResponseEntity<AuthorsSearchResponse> showAuthors() {
		return ResponseEntity.ok().body(AuthorsSearchResponse.from(memberService.searchAuthors()));
  }
  
	@GetMapping("/assignees")
	public ResponseEntity<IssueAssigneesResponse> showAssignees() {
		IssueAssigneesResponse issueAssigneesResponse = IssueAssigneesResponse.from(issueService.searchAssignee());
		return ResponseEntity.ok().body(issueAssigneesResponse);
	}

	@GetMapping("/labels")
	public ResponseEntity<IssueLabelMappingsResponse> showLabels() {
		IssueLabelMappingsResponse issueLabelMappingsResponse = IssueLabelMappingsResponse.from(issueService.searchAssignedLabel());
		return ResponseEntity.ok().body(issueLabelMappingsResponse);
	}
}
