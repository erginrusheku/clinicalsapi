package com.bharath.clinicals.repos;

import com.bharath.clinicals.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
