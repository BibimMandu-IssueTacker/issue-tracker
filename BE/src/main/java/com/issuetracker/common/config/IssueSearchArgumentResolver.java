package com.issuetracker.common.config;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.issuetracker.common.config.exception.CustomHttpException;
import com.issuetracker.common.config.exception.ErrorType;
import com.issuetracker.issue.ui.dto.IssueSearchRequest;

@Component
public class IssueSearchArgumentResolver implements HandlerMethodArgumentResolver {

	private static final List<String> ISSUE_SEARCH_REQUEST_VARIABLE_NAMES =
		Arrays.stream(IssueSearchRequest.class.getDeclaredFields())
			.map(Field::getName)
			.collect(Collectors.toUnmodifiableList());

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return IssueSearchRequest.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		validateQueryStringNotMatch(webRequest);

		String isOpen = webRequest.getParameter("isOpen");
		return new IssueSearchRequest(
			Objects.isNull(isOpen) ? null : Boolean.valueOf(isOpen),
			converterToListLong(webRequest.getParameterValues("assigneeIds")),
			converterToListLong(webRequest.getParameterValues("labelIds")),
			converterToLong(webRequest.getParameter("milestoneId")),
			converterToLong(webRequest.getParameter("authorId")),
			Boolean.valueOf(webRequest.getParameter("isCommentedByMe"))
		);
	}

	private static void validateQueryStringNotMatch(NativeWebRequest webRequest) {
		webRequest.getParameterMap()
			.keySet()
			.forEach(parameterName -> {
				if (ISSUE_SEARCH_REQUEST_VARIABLE_NAMES.stream()
					.noneMatch(variableName -> variableName.equals(parameterName))) {
					throw new CustomHttpException(ErrorType.QUERY_STRING_KEY_NOT_MATCH);
				}
		});
	}

	private List<Long> converterToListLong(String[] strings) {
		if (strings == null || Arrays.stream(strings).allMatch(s -> s.equals(""))) {
			return Collections.emptyList();
		}

		return Arrays.stream(strings)
			.map(this::converterToLong)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	private Long converterToLong(String parameter) {
		try{
			if (parameter == null || parameter.equals("")) {
				return null;
			}

			return Long.valueOf(parameter);
		} catch (NumberFormatException e) {
			throw new CustomHttpException(ErrorType.QUERY_STRING_VALUE_NOT_MATCH);
		}
	}
}
