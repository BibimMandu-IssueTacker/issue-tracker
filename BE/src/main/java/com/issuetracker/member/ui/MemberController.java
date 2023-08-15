package com.issuetracker.member.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetracker.member.application.MemberService;
import com.issuetracker.member.ui.dto.MemberResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/{id}")
	public ResponseEntity<MemberResponse> showProfile(@PathVariable Long id) {
		return ResponseEntity.ok()
			.body(MemberResponse.from(memberService.findById(id)));
	}
}
