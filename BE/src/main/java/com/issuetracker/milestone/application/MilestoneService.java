package com.issuetracker.milestone.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.milestone.application.dto.MilestoneSearchInformation;
import com.issuetracker.milestone.infrastructure.MilestoneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MilestoneService {

	private final MilestoneRepository milestoneRepository;

	public List<MilestoneSearchInformation> search() {
		return MilestoneSearchInformation.from(milestoneRepository.findAllForFilter());
	}
}
