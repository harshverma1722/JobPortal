package com.project.jobportal.Repository;

import com.project.jobportal.Entity.JobPostActivity;
import com.project.jobportal.Entity.JobSeekerProfile;
import com.project.jobportal.Entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave,Integer> {

    List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);

    List<JobSeekerSave> findByJob(JobPostActivity job);
}
