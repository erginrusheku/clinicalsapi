package com.bharath.clinicals.util;

import com.bharath.clinicals.model.ClinicalData;

import java.util.List;

public class BMICalculator {

    public static void calculateBMI(ClinicalData eachEntry, List<ClinicalData> clinicalData) {
        if(eachEntry.getComponentName().equals("hw")){

            String[] heightAndWeight = eachEntry.getComponentValue().split("/");
            if(heightAndWeight != null && heightAndWeight.length > 1) {
                float heightInMetres = Float.parseFloat(heightAndWeight[0]) * 0.4536F;
                float bmi = Float.parseFloat(heightAndWeight[1]) / (heightInMetres * heightInMetres);
                ClinicalData bmiData = new ClinicalData();
                bmiData.setComponentName("bmi");
                bmiData.setComponentValue(Float.toString(bmi));
                clinicalData.add(bmiData);
            }
        }
    }
}
