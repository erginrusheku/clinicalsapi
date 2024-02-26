package com.bharath.clinicals.controllers;

import com.bharath.clinicals.dto.ClinicalDataRequest;
import com.bharath.clinicals.model.ClinicalData;
import com.bharath.clinicals.model.Patient;
import com.bharath.clinicals.repos.ClinicalDataRepository;
import com.bharath.clinicals.repos.PatientRepository;
import com.bharath.clinicals.util.BMICalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController{
    @Autowired
    private ClinicalDataRepository clinicalDataRepository;
    @Autowired
    private PatientRepository patientRepository;
    @RequestMapping(value = "/clinicals", method = RequestMethod.POST)
    public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest request){
        Patient patient = patientRepository.findById(request.getPatientId()).get();
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName(request.getComponentName());
        clinicalData.setComponentValue(request.getComponentValue());
        clinicalData.setPatient(patient);
        return clinicalDataRepository.save(clinicalData);
    }

    @RequestMapping(value = "/clinicals/{patientId}/{componentName}", method = RequestMethod.GET)
    public List<ClinicalData> getClinicalData(@PathVariable int patientId, @PathVariable String componentName){
      if(componentName.equals("bmi")){
          componentName = "hw";
      }

        List<ClinicalData> clinicalData =clinicalDataRepository.findByPatientIdAndComponentNameOrderByMeasuredDateTime(patientId,componentName);
    List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
    for (ClinicalData eachEntry : duplicateClinicalData){
        BMICalculator.calculateBMI(eachEntry,clinicalData);
    }
    return clinicalData;
    }


}
