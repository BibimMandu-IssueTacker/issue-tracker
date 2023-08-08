package com.issuetracker.issue.infrastrucure;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.issuetracker.issue.domain.IssueComment;
import com.issuetracker.issue.domain.IssueCommentRepository;

@Repository
public class JdbcIssueCommentRepository implements IssueCommentRepository {

	private static final String SAVE_SQL = "INSERT INTO issue_comment (content, create_at, author_id, issue_id) VALUES(:content, :createAt, :authorId, :issueId)";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcIssueCommentRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Override
	public long save(IssueComment issueComment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(issueComment);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(SAVE_SQL, param, keyHolder);
		return keyHolder.getKey().longValue();
	}
}
