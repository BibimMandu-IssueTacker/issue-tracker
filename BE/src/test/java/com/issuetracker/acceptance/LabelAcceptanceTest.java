package com.issuetracker.acceptance;

import static com.issuetracker.util.steps.LabelSteps.*;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.issuetracker.label.ui.dto.LabelCreateRequest;
import com.issuetracker.label.ui.dto.LabelResponse;
import com.issuetracker.label.ui.dto.LabelsResponse;
import com.issuetracker.util.AcceptanceTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class LabelAcceptanceTest extends AcceptanceTest {

	/**
	 * Given 레이블 제목, 레이블 설명, 레이블 색상을 생성하고
	 * When 레이블을 작성 하면
	 * Then 레이블 목록 조회 시 생성한 레이블을 확인 할 수 있다.
	 */
	@Test
	void 레이블을_생성한다() {
		// when
		LabelCreateRequest labelCreateRequest = new LabelCreateRequest(
			"레이블 제목",
			"레이블 설명",
			"#ffffff"
		);

		var response = 레이블_생성_요청(labelCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.CREATED);
		레이블_목록_조회_시_생성된_레이블을_검증(response, labelCreateRequest);
	}

	/**
	 * Given 레이블 제목, 레이블 설명, 레이블 색상을 생성하고
	 * When 레이블을 생성시 제목이 공백이면
	 * Then 400에러를 반환한다.
	 */
	@Test
	void 레이블을_생성시_제목을_입력하지_않으면_400에러를_반환한다() {
		// when
		LabelCreateRequest labelCreateRequest = new LabelCreateRequest(
			"",
			"레이블 설명",
			"#ffffff"
		);

		var response = 레이블_생성_요청(labelCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Given 레이블 제목, 레이블 설명, 레이블 색상을 생성하고
	 * When 레이블을 생성시 레이블 설명은 없어도
	 * Then 레이블을 생성한다.
	 */
	@Test
	void 레이블을_생성시_레이블_설명은_없어도_레이블을_생성한다() {
		// when
		LabelCreateRequest labelCreateRequest = new LabelCreateRequest(
			"레이블 제목",
			null,
			"#ffffff"
		);

		var response = 레이블_생성_요청(labelCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.CREATED);
		레이블_목록_조회_시_생성된_레이블을_검증(response, labelCreateRequest);
	}

	/**
	 * Given 레이블 제목, 레이블 설명, 레이블 색상을 생성하고
	 * When 레이블을 생성시 색상이 HEX 코드가 아니면
	 * Then 400에러를 반환한다.
	 */
	@Test
	void 레이블을_생성시_색상이_HEX_코드가_아니면_400에러를_반환한다() {
		// when
		LabelCreateRequest labelCreateRequest = new LabelCreateRequest(
			"레이블 제목",
			"레이블 설명",
			"#HEX코드가아니다"
		);

		var response = 레이블_생성_요청(labelCreateRequest);

		// then
		응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
	}

	private void 레이블_목록_조회_시_생성된_레이블을_검증(ExtractableResponse<Response> response,
		LabelCreateRequest labelCreateRequest) {
		var findResponse = 레이블_목록_조회_요청();
		List<LabelResponse> labelResponse = findResponse.as(LabelsResponse.class).getLabels();
		LabelResponse lastLabelResponse = labelResponse.get(labelResponse.size() - 1);

		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(lastLabelResponse.getId()).isEqualTo(response.jsonPath().getLong("id"));
		softAssertions.assertThat(lastLabelResponse.getTitle()).isEqualTo(labelCreateRequest.getTitle());
		softAssertions.assertThat(lastLabelResponse.getColor()).isEqualTo(labelCreateRequest.getColor());
		softAssertions.assertAll();
	}
}
