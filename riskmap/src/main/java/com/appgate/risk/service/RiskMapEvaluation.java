package com.appgate.risk.service;

import com.appgate.risk.exception.MalformedRequestException;
import com.appgate.risk.model.RiskMapModel;

public interface RiskMapEvaluation {

    RiskMapModel calculateRisk(String provider, int rowMatrix, int columnMatrix) throws MalformedRequestException;

}
