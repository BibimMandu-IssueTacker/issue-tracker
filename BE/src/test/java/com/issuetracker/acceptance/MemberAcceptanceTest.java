package com.issuetracker.acceptance;

import static com.issuetracker.util.fixture.MemberFixture.MEMBER1;
import static com.issuetracker.util.steps.MemberSteps.회원_정보_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.issuetracker.member.ui.dto.MemberResponse;
import com.issuetracker.util.AcceptanceTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MemberAcceptanceTest extends AcceptanceTest {

	/**
	 * Given 회원을 생성하고
	 * When 회원 정보를 조회하면
	 * Then 해당 회원 정보를 확인 할 수 있다.
	 */
	@Test
	void 회원_정보를_조회한다() {
		// when
		var response = 회원_정보_조회_요청(MEMBER1.getId());

		// then
		응답_상태코드_검증(response, HttpStatus.OK);
		회원_정보를_검증(response, MEMBER1.getId(), MEMBER1.getNickname(), MEMBER1.getProFileImageUrl());
	}

	/**
	 * When 존재하지 않는 회원 정보를 조회하면
	 * Then 요청이 실패한다
	 */
	@Test
	void 존재하지_않는_회원_정보를_조회하면_실패한다() {
		// given
		long 존재하지_않는_회원_아이디 = 20L;

		// when
		var response = 회원_정보_조회_요청(존재하지_않는_회원_아이디);

		// then
		응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
	}

	private void 회원_정보를_검증(ExtractableResponse<Response> response, Long id, String nickname, String profileImageUrl) {
		MemberResponse memberResponse = response.as(MemberResponse.class);

		Assertions.assertAll(
			() -> assertThat(memberResponse.getId()).isEqualTo(id),
			() -> assertThat(memberResponse.getNickname()).isEqualTo(nickname),
			() -> assertThat(memberResponse.getProfileImageUrl()).isEqualTo(profileImageUrl)
		);
	}
}
