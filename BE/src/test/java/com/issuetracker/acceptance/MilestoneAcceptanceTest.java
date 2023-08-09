package com.issuetracker.acceptance;

import static com.issuetracker.util.steps.MilestoneSteps.*;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.issuetracker.milestone.ui.dto.MilestoneCreateRequest;
import com.issuetracker.milestone.ui.dto.MilestoneResponse;
import com.issuetracker.milestone.ui.dto.MilestonesResponse;
import com.issuetracker.util.AcceptanceTest;
import com.issuetracker.util.steps.MilestoneSteps;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MilestoneAcceptanceTest extends AcceptanceTest {

	/**
	 * Given 마일스톤 제목, 마일스톤 설명, 마일스톤 데드라인을 생성하고
	 * When 마일스톤을 작성 하면
	 * Then 마일스톤 목록 조회 시 생성한 마일스톤을 확인 할 수 있다.
	 */
	@Test
	void 마일스톤을_생성한다() {
		// given
		MilestoneCreateRequest milestoneCreateRequest = new MilestoneCreateRequest(
			"마일스톤 제목",
			"마일스톤 설명",
			"2023.08.09"
		);

		// when
		var response = MilestoneSteps.마일스톤_생성_요청(milestoneCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.CREATED);
		마일스톤_목록_조회_시_생성된_마일스톤을_검증(response, milestoneCreateRequest);
	}

	/**
	 * Given 마일스톤 제목, 마일스톤 설명, 마일스톤 데드라인을 생성하고
	 * When 마일스톤을 생성시 제목이 공백이면
	 * Then 400에러를 반환한다.
	 */
	@Test
	void 마일스톤을_생성시_제목을_입력하지_않으면_400에러를_반환한다() {
		// given
		MilestoneCreateRequest milestoneCreateRequest = new MilestoneCreateRequest(
			"",
			"마일스톤 설명",
			"2023.08.09"
		);

		// when
		var response = 마일스톤_생성_요청(milestoneCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Given 마일스톤 제목, 마일스톤 설명, 마일스톤 데드라인을 생성하고
	 * When 마일스톤을 생성시 마일스톤 설명은 없어도
	 * Then 마일스톤을 생성한다.
	 */
	@Test
	void 마일스톤을_생성시_마일스톤_설명은_없어도_마일스톤을_생성한다() {
		// given
		MilestoneCreateRequest milestoneCreateRequest = new MilestoneCreateRequest(
			"마일스톤 제목",
			null,
			"2023.08.09"
		);

		// when
		var response = 마일스톤_생성_요청(milestoneCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.CREATED);
		마일스톤_목록_조회_시_생성된_마일스톤을_검증(response, milestoneCreateRequest);
	}

	/**
	 * Given 마일스톤 제목, 마일스톤 설명, 마일스톤 데드라인을 생성하고
	 * When 마일스톤을 생성시 데드라인이 yyyy.MM.dd 형식이 아니면
	 * Then 400에러를 반환한다.
	 */
	@Test
	void 마일스톤을_생성시_색상이_데드라인_형식이_올바르지_않으면_400에러를_반환한다() {
		// given
		MilestoneCreateRequest milestoneCreateRequest = new MilestoneCreateRequest(
			"마일스톤 제목",
			"마일스톤 설명",
			"2023-09-09"
		);

		// when
		var response = 마일스톤_생성_요청(milestoneCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	private void 마일스톤_목록_조회_시_생성된_마일스톤을_검증(ExtractableResponse<Response> response,
		MilestoneCreateRequest milestoneCreateRequest) {
		var findResponse = 마일스톤_목록_조회_요청();
		List<MilestoneResponse> milestoneResponse = findResponse.as(MilestonesResponse.class).getMilestones();
		MilestoneResponse lastMilestoneResponse = milestoneResponse.get(milestoneResponse.size() - 1);

		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(lastMilestoneResponse.getId()).isEqualTo(response.jsonPath().getLong("id"));
		softAssertions.assertThat(lastMilestoneResponse.getTitle()).isEqualTo(milestoneCreateRequest.getTitle());
		softAssertions.assertThat(lastMilestoneResponse.getDescription())
			.isEqualTo(milestoneCreateRequest.getDescription());
		softAssertions.assertThat(lastMilestoneResponse.makeStringDeadline())
			.isEqualTo(milestoneCreateRequest.getDeadline());
		softAssertions.assertAll();
	}
}
