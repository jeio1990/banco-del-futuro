package com.appgate.risk.controller;


import com.appgate.risk.controller.dto.RiskMapDTO;
import com.appgate.risk.controller.error.EntityApiError;
import com.appgate.risk.controller.error.EntityErrorType;
import com.appgate.risk.exception.MalformedRequestException;
import com.appgate.risk.model.RiskMapModel;
import com.appgate.risk.service.RiskMapEvaluation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/risk-map")
@Api("Risk Map Controller")
public class RiskEvaluationController {


    private final RiskMapEvaluation riskMapEvaluation;
    private static final Logger LOGGER = LoggerFactory.getLogger(RiskEvaluationController.class);

    public RiskEvaluationController(RiskMapEvaluation riskMapEvaluation) {
        this.riskMapEvaluation = riskMapEvaluation;
    }


    @PostMapping
    @ApiOperation(value = "Calculate a dynamic risk map and add level history about a provider service")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = RiskMapDTO.class),
            @ApiResponse(code = 400, message = "Bad request", response = EntityApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = EntityApiError.class)})
    public ResponseEntity<?> calculateProviderRisk(@RequestParam int row, @RequestParam int column, @RequestParam String provider) {
        try {
            RiskMapModel riskMapModel = riskMapEvaluation.calculateRisk(provider, row, column);
            if(riskMapModel != null) {
                RiskMapDTO riskMapDTO = new RiskMapDTO();
                riskMapDTO.setProvider(provider);
                riskMapDTO.setRiskValues(riskMapModel.getRiskValues());
                riskMapDTO.setServiceLevel(riskMapModel.getServiceLevel());
                return ResponseEntity.ok(riskMapDTO);
            }
            return ResponseEntity.ok().build();
        } catch (MalformedRequestException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new EntityApiError(EntityErrorType.MALFORMED_REQUEST, e.getMessage()));
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EntityApiError(EntityErrorType.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

}
