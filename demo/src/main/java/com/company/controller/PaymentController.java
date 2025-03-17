package com.company.controller;

import com.company.dto.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping("/charge")
    public ResponseEntity<?> chargePayment(@RequestBody PaymentRequest request) {
        // 실제 결제 연동이 아니라 모의 처리를 위한 예제입니다.
        return ResponseEntity.ok("Payment successful for amount: " + request.getAmount());
    }
}
