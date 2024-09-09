package com.mashreq.money.transfer.money_transfer_resource.mapper;

import com.mashreq.money.transfer.money_transfer_resource.model.request.MoneyTransferRequestDto;
import com.mashreq.money.transfer.money_transfer_resource.model.response.MoneyTransferResponseDto;
import com.mashreq.money.transfer.money_transfer_resource.model.response.MoneyTransferResponseFields;
import com.mashreq.money.transfer.money_transfer_service.model.request.MoneyTransferRequest;
import com.mashreq.money.transfer.money_transfer_service.model.response.MoneyTransferResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class MoneyTransferMapper {
    public MoneyTransferRequest mapRequest(MoneyTransferRequestDto request) {
            return MoneyTransferRequest.builder()
                    .cif(request.getCif())
                    .amount(new BigDecimal(request.getAmount()))
                    .sourceAccount(request.getSourceAccount())
                    .reason(request.getReason())
                    .notes(request.getNotes())
                    .currency(request.getCurrency())
                    .destinationAccount(request.getDestinationAccount())
                    .build();
    }

    public MoneyTransferResponseDto map(MoneyTransferResponse response) {
        return MoneyTransferResponseDto.builder()
                .status(response.getStatus())
                .data(response.getData() != null ? MoneyTransferResponseFields.builder()
                        .failureReason(response.getData().getFailureReason())
                        .timestamp(response.getData().getTimestamp())
                        .transactionNumber(response.getData().getTransactionNumber())
                        .transactionStatus(response.getData().getTransactionStatus())
                        .build() : null )
                .build();
    }
}
