package com.issuetracker.member.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.common.config.exception.CustomHttpException;
import com.issuetracker.common.config.exception.ErrorType;
import com.issuetracker.file.application.FileService;
import com.issuetracker.member.application.dto.MemberInformation;
import com.issuetracker.member.domain.Member;
import com.issuetracker.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final FileService fileService;

	public List<MemberInformation> searchAuthors() {
		return MemberInformation.from(memberRepository.search());
	}

	public MemberInformation findById(Long id) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new CustomHttpException(ErrorType.MEMBER_NOT_FOUND));
		return MemberInformation.from(member);
	}

}
