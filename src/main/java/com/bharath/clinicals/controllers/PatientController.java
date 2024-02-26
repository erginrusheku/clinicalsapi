package com.bharath.clinicals.controllers;

import com.bharath.clinicals.model.ClinicalData;
import com.bharath.clinicals.model.Patient;
import com.bharath.clinicals.repos.PatientRepository;
import com.bharath.clinicals.util.BMICalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;
    Map<String,String> filters = new HashMap<>();

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public List<Patient> getPatients(){
        return patientRepository.findAll();
    }

    @RequestMapping(value = "/patients/{id}", method = RequestMethod.GET)
    public Patient getPatients(@PathVariable int id){
        return patientRepository.findById(id).get();
    }
    @RequestMapping(value = "/patients", method = RequestMethod.POST)
    public Patient savePatients(@RequestBody Patient patient){
        return patientRepository.save(patient);
    }

    @RequestMapping(value = "/patients/analyze/{id}", method = RequestMethod.GET)
    public Patient analyze(@PathVariable int id){
       Patient patient = patientRepository.findById(id).get();
       List<ClinicalData> clinicalData = patient.getClinicalData();
       ArrayList<ClinicalData> duplicateClinicalData =new ArrayList<>(clinicalData);
       for(ClinicalData eachEntry: duplicateClinicalData){

           if(filters.containsKey(eachEntry.getComponentName())){
            clinicalData.remove(eachEntry);
           continue;
           }else {
               filters.put(eachEntry.getComponentName(),null);
           }
           BMICalculator.calculateBMI(eachEntry, clinicalData);

       }
       filters.clear();
       return patient;
    }


}
