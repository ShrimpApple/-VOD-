package com.company.controller;

import com.company.domain.Subscription;
import com.company.domain.User;
import com.company.repository.SubscriptionRepository;
import com.company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    // 특정 사용자 구독 내역 조회 (예시)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Subscription>> getSubscriptionsByUser(@PathVariable Long userId) {
        // 실제로는 사용자 별로 필터링 해야 합니다.
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return ResponseEntity.ok(subscriptions);
    }

    // 구독 신청 (예시: 1개월 구독)
    @PostMapping("/user/{userId}")
    public ResponseEntity<Subscription> subscribeUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStartDate(LocalDateTime.now());
        subscription.setEndDate(LocalDateTime.now().plusMonths(1));
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return ResponseEntity.ok(savedSubscription);
    }
}
