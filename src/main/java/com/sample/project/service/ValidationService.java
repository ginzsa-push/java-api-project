package com.sample.project.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.distribution.NormalDistribution;
import static java.util.stream.Collectors.partitioningBy;

import com.sample.project.model.ValidationResult;
import com.sample.project.model.DataPoint;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationService {

    private static final String REG = "\\b(day|month|year)s?\\b";
    private static final Pattern PATTERN = Pattern.compile(REG);
    private static final double PROBABILITY_THRESHOLD = 0.8;

    private boolean validateObject(ValidationResult to_validate) {
        // Your validation logic here
        Matcher matcher = PATTERN.matcher(to_validate.getTime_unit().trim().toLowerCase());
        return matcher.find();
    }

    private DataPoint convertDataPoint(ValidationResult validationResult) {
        String[] components = validationResult.getTime_unit().trim().split(" ");

        if (components[1].trim().equals("days")) {
            return new DataPoint(validationResult.getNumber(), Integer.parseInt(components[0]),
                    validationResult.getTime_unit());
        }

        if (components[1].trim().equals("months")) {
            return new DataPoint(validationResult.getNumber(), (Integer.parseInt(components[0]) * 30),
                    validationResult.getTime_unit());
        }

        if (components[1].trim().equals("years")) {
            return new DataPoint(validationResult.getNumber(), (Integer.parseInt(components[0]) * 365),
                    validationResult.getTime_unit());
        }

        return null;
    }
    
    private ValidationResult convertValidationResult(DataPoint point) {
        return new ValidationResult(point.getId(), point.getOriginalUnit());
    }

    private List<DataPoint> convertToDataPoints(List<ValidationResult> toConvert) {
        return toConvert.stream()
                .map(this::convertDataPoint)
                .collect(Collectors.toList());
    }
    
    private boolean isAboveThreshold(NormalDistribution normalDist, double probabilityThreshold, int unit) {
        // Probability that a value is less than or equal to this point
        double cumulativeProbability = normalDist.cumulativeProbability(unit);
        // Keep points in the upper tail (right outliers)
        return cumulativeProbability > probabilityThreshold;
    }

    // Using Normal Distribution for probability-based detection
    public List<ValidationResult> findOutliersUsingNormalDistribution(List<ValidationResult> data,
            double probabilityThreshold) {

        List<DataPoint> data_points = convertToDataPoints(data);
        double[] values = data_points.stream()
                .mapToDouble(DataPoint::getUnit)
                .toArray();
        
        DescriptiveStatistics stats = new DescriptiveStatistics(values);
        double mean = stats.getMean();
        double stdDev = stats.getStandardDeviation();

        NormalDistribution normalDist = new NormalDistribution(mean, stdDev);
        
        return data_points.stream()
                .filter(point -> isAboveThreshold(normalDist, probabilityThreshold, point.getUnit()))
                .map(this::convertValidationResult)
                .collect(Collectors.toList());    
    }
    
    public List<ValidationResult> validate(List<Map<String, String>> to_validate) {

        Map<Boolean, List<ValidationResult>> partitions = to_validate.stream()
                .map(map -> new ValidationResult(map.get("id"), map.get("time spent")))
                .collect(partitioningBy(x -> validateObject(x)));
        
        List<ValidationResult> neg_result = partitions.get(false);

        List<ValidationResult> rs = findOutliersUsingNormalDistribution(partitions.get(true), PROBABILITY_THRESHOLD);

        rs.addAll(neg_result);
        return rs;
    }   
}
