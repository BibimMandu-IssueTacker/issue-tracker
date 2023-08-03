package com.issuetracker.milestone.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetracker.milestone.application.MilestoneService;
import com.issuetracker.milestone.ui.dto.MilestonesSearchResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/milestones")
@RequiredArgsConstructor
public class MilestoneController {

	private final MilestoneService milestoneService;

	@GetMapping
	public ResponseEntity<MilestonesSearchResponse> showMilestones() {
		return ResponseEntity.ok().body(MilestonesSearchResponse.from(milestoneService.search()));
	}
}
