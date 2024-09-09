package com.mashreq.money.transfer.core_money_transfer_adapter.client;

import com.mashreq.money.transfer.core_money_transfer_adapter.model.request.CoreMoneyTransferRequestData;
import com.mashreq.money.transfer.core_money_transfer_adapter.model.response.CoreMoneyTransferResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "coreMoneyTransferServiceClient",
        url = "${app.services.coreMoneyTransferService.url}"
)
public interface CoreMoneyTransferServiceClient {
    @PostMapping("/api/v1/local-transfer")
    ResponseEntity<CoreMoneyTransferResponseData> performMoneyTransfer(
            @RequestBody CoreMoneyTransferRequestData requestData
    );
}
