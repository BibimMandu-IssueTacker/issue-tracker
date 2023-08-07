package com.issuetracker.issue.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthorSearchInformation {

	private Long id;
	private String nickname;
	private String profileImageUrl;

	public static AuthorSearchInformation from(Member author) {
		return new AuthorSearchInformation(
			author.getId(),
			author.getNickname(),
			author.getProfileImageUrl()
		);
	}

	public static List<AuthorSearchInformation> from(List<Member> authors) {
		return authors.stream()
			.map(AuthorSearchInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
