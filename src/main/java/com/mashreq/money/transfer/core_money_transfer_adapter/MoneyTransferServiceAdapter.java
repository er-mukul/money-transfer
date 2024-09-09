package com.mashreq.money.transfer.core_money_transfer_adapter;

import com.mashreq.money.transfer.core_money_transfer_adapter.client.CoreMoneyTransferServiceClient;
import com.mashreq.money.transfer.core_money_transfer_adapter.mapper.CoreMoneyTransferMapper;
import com.mashreq.money.transfer.core_money_transfer_adapter.model.request.CoreMoneyTransferRequestData;
import com.mashreq.money.transfer.core_money_transfer_adapter.model.response.CoreMoneyTransferResponseData;
import com.mashreq.money.transfer.core_money_transfer_service.model.CoreMoneyTransferRequest;
import com.mashreq.money.transfer.core_money_transfer_service.model.CoreMoneyTransferResponse;
import com.mashreq.money.transfer.ports.MoneyTransferPort;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MoneyTransferServiceAdapter implements MoneyTransferPort {
    private final CoreMoneyTransferServiceClient moneyTransferServiceClient;
    private final CoreMoneyTransferMapper coreMoneyTransferMapper;

    @Override
    public CoreMoneyTransferResponse performMoneyTransfer(CoreMoneyTransferRequest request) {
        CoreMoneyTransferResponseData transferResponse = CoreMoneyTransferResponseData.builder().build();
        String transactionNo  = null;
        try {
            CoreMoneyTransferRequestData requestData = coreMoneyTransferMapper.mapRequest(request);
            ResponseEntity<CoreMoneyTransferResponseData> response  = moneyTransferServiceClient.performMoneyTransfer(requestData);
            transactionNo = requestData.getTransactionNumber();
            if(response.getStatusCode().is2xxSuccessful()) {
                log.info("Money Transfer request processed for transactionNo: {}", transactionNo);
                transferResponse = response.getBody();
            } else if(HttpStatus.BAD_REQUEST.value() == response.getStatusCode().value()) {
                log.error("Bad request error occurred while doing money transfer for transactionNo: {}", transactionNo);
            } else {
                log.error("Error occurred while doing money transfer for transactionNo: {} with status{}", transactionNo, response.getStatusCode().value());
            }
        } catch (FeignException feignException) {
            log.error("Error occurred while doing money transfer for transactionNo: {} with status{} and message {}", transactionNo, feignException.status(), feignException.getMessage());
        }
        return coreMoneyTransferMapper.map(transferResponse,transactionNo);
    }
}
