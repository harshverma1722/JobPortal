package com.project.jobportal.Repository;

import com.project.jobportal.Entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile,Integer> {
}
