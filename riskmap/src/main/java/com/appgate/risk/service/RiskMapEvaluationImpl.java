package com.appgate.risk.service;

import com.appgate.risk.exception.MalformedRequestException;
import com.appgate.risk.model.RiskMapModel;
import com.appgate.risk.model.RiskThreshold;
import com.appgate.risk.repository.ProviderLevelServiceRepository;
import com.appgate.risk.repository.document.ProviderLevelServiceDocument;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;

@Service
public class RiskMapEvaluationImpl implements RiskMapEvaluation {

    private final ProviderLevelServiceRepository providerLevelServiceRepository;

    public RiskMapEvaluationImpl(ProviderLevelServiceRepository providerLevelServiceRepository) {
        this.providerLevelServiceRepository = providerLevelServiceRepository;
    }

    @Override
    public RiskMapModel calculateRisk(String provider, int rowMatrix, int columnMatrix) throws MalformedRequestException {
        if (rowMatrix <= 0 || columnMatrix <= 0 || provider == null || provider.trim().isEmpty()) {
            throw new MalformedRequestException("row and column doesn't same size that the matrix");
        }

        int[][] matrix = buildRiskMatrix(rowMatrix, columnMatrix);
        addHistoryLevelServiceProvider(matrix, rowMatrix, columnMatrix, provider);
        String[][] riskMatrix = calculateRiskMatrix(matrix, rowMatrix, columnMatrix);
        RiskMapModel riskMapModel = new RiskMapModel();
        riskMapModel.setProvider(provider);
        riskMapModel.setRiskValues(riskMatrix);
        riskMapModel.setServiceLevel(getAverageServiceLevelProvider(provider));
        return riskMapModel;
    }


    private int getAverageServiceLevelProvider(String provider) {
        ProviderLevelServiceDocument providerLevelServiceDocument = providerLevelServiceRepository.findByProvider(provider.trim());
        if (providerLevelServiceDocument != null) {
            int sum = 0;
            for (int level : providerLevelServiceDocument.getLevel()) {
                sum += level;
            }
            return sum / providerLevelServiceDocument.getLevel().size();
        }
        return 0;
    }

    /**
     * register history service level of a provider
     *
     * @param matrix
     * @param rowMatrix
     * @param columnMatrix
     * @param provider
     */
    private void addHistoryLevelServiceProvider(int[][] matrix, int rowMatrix, int columnMatrix, String provider) {
        int averageRiskProvider = calculateAverageRiskProvider(matrix, rowMatrix, columnMatrix);
        ProviderLevelServiceDocument providerLevelServiceDocument = providerLevelServiceRepository.findByProvider(provider.trim());
        if (providerLevelServiceDocument == null) {
            providerLevelServiceDocument = new ProviderLevelServiceDocument();
            providerLevelServiceDocument.setProvider(provider);
            providerLevelServiceDocument.setLevel(Arrays.asList(averageRiskProvider));
            providerLevelServiceRepository.save(providerLevelServiceDocument);
        } else {
            providerLevelServiceDocument.getLevel().add(averageRiskProvider);
            providerLevelServiceRepository.save(providerLevelServiceDocument);
        }
    }

    private int calculateAverageRiskProvider(int[][] matrix, int rowMatrix, int columnMatrix) {
        int sizeRowColumn = rowMatrix * columnMatrix;
        int sum = 0;
        for (int i = 0; i < rowMatrix; i++) {
            for (int j = 0; j < columnMatrix; j++) {
                sum += matrix[i][j];
            }
        }
        return sum / sizeRowColumn;
    }


    /**
     * get a result matrix with heat map
     *
     * @param matrix,       matrix to calculate heat map
     * @param rowMatrix,    number of row
     * @param columnMatrix, number of column
     * @return
     */
    private String[][] calculateRiskMatrix(int[][] matrix, int rowMatrix, int columnMatrix) {
        String[][] riskMatrix = new String[rowMatrix][columnMatrix];
        for (int i = 0; i < rowMatrix; i++) {
            for (int j = 0; j < columnMatrix; j++) {
                riskMatrix[i][j] = getValueRisk(matrix[i][j]);
            }
        }
        return riskMatrix;
    }

    /**
     * calculate risk according to configuration values range
     *
     * @param value, value of position in matrix to calculate heat map
     * @return
     */
    private String getValueRisk(int value) {
        if (value >= RiskThreshold.BAD.getMin() && value <= RiskThreshold.BAD.getMax()) {
            return String.valueOf(value).concat("-").concat(RiskThreshold.BAD.toString()).concat("-").concat(RiskThreshold.BAD.getWarm());
        }

        if (value >= RiskThreshold.REGULAR.getMin() && value <= RiskThreshold.REGULAR.getMax()) {
            return String.valueOf(value).concat("-").concat(RiskThreshold.REGULAR.toString()).concat("-").concat(RiskThreshold.REGULAR.getWarm());
        }

        if (value >= RiskThreshold.GOOD.getMin() && value <= RiskThreshold.GOOD.getMax()) {
            return String.valueOf(value).concat("-").concat(RiskThreshold.GOOD.toString()).concat("-").concat(RiskThreshold.GOOD.getWarm());
        }

        if (value >= RiskThreshold.GREAT.getMin() && value <= RiskThreshold.GREAT.getMax()) {
            return String.valueOf(value).concat("-").concat(RiskThreshold.GREAT.toString()).concat("-").concat(RiskThreshold.GREAT.getWarm());
        }

        return "NO RISK";
    }


    /**
     * generate random matrix with row and column
     *
     * @param rowMatrix,    number of rows to build matrix
     * @param columnMatrix, number of column to build matrix
     * @return
     */
    private int[][] buildRiskMatrix(int rowMatrix, int columnMatrix) {
        Random random = new Random();
        int min = 0;
        int max = 10;
        int[][] matrix = new int[rowMatrix][columnMatrix];
        for (int i = 0; i < rowMatrix; i++) {
            for (int j = 0; j < columnMatrix; j++) {
                matrix[i][j] = random.nextInt(max + 1 - min) + min;
            }
        }
        return matrix;
    }
}
