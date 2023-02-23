package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.entity.Applicant;
import com.backend.swp.apalary.model.entity.JobOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {
    Applicant findApplicantByIdAndStatus(Integer id, Status status);
    Applicant findApplicantById(Integer id);
    List<Applicant> findApplicantByStatus(Status status);
    List<Applicant> findApplicantByStatusAndJobOffering(Status status, JobOffering jobOffering);
    int countApplicantByJobOfferingAndStatus(JobOffering jobOffering, Status status);
}
