package com.issuetracker.member.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.issue.application.dto.AuthorSearchInformation;
import com.issuetracker.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;

	public List<AuthorSearchInformation> searchAuthors() {
		return AuthorSearchInformation.from(memberRepository.search());
	}
}
