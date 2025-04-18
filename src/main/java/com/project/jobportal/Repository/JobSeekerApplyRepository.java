package com.project.jobportal.Repository;

import com.project.jobportal.Entity.JobPostActivity;
import com.project.jobportal.Entity.JobSeekerApply;
import com.project.jobportal.Entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply,Integer> {

    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);

    List<JobSeekerApply> findByJob(JobPostActivity job);

}
