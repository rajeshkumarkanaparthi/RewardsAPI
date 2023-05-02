package com.rk.service;

import com.rk.entity.Transaction;
import com.rk.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RewardsService {
    private TransactionRepository transactionRepository;

    @Autowired
    public RewardsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Map<Integer, Map<String, Integer>> getRewards() {
        List<Transaction> transactions = transactionRepository.findAll();
        Map<Integer, Map<String, Integer>> allCustomerRewards = new HashMap<>();
        Map<Long, List<Transaction>> transactionsByCustomer = transactions.stream().collect(Collectors.groupingBy(Transaction::getCustomerId));
        transactionsByCustomer.forEach((customerId, transactions1) -> {
            Map<String, Integer> m = getRewardsForCustomer(customerId);
            allCustomerRewards.put(Math.toIntExact(customerId), m);
        });
        return allCustomerRewards;
    }

    public Map<String, Integer> getRewardsForCustomer(Long customerId) {
        List<Transaction> transactionsForCustomer = transactionRepository.findByCustomerId(customerId);
        Map<Object, Double> customerTransactionByMonth = null;
        Map<String, Integer> rewardResponse = new HashMap<>();
        if (!transactionsForCustomer.isEmpty()) {
            customerTransactionByMonth = transactionsForCustomer.stream().collect(Collectors.groupingBy(transaction -> transaction.getTransactionDate().getMonth(), Collectors.summingDouble(Transaction::getTransactionAmount)));
            double totalRewards = 0;
            for (Map.Entry<Object, Double> entry : customerTransactionByMonth.entrySet()) {
                System.out.println("Key = " + getMonthName((Integer) entry.getKey()) +
                        ", Value = " + calculateRewards(entry.getValue()));
                totalRewards += calculateRewards(entry.getValue());
                rewardResponse.put(getMonthName((Integer) entry.getKey()), calculateRewards(entry.getValue()));

            }
            rewardResponse.put("total", (int) totalRewards);
        }
        return rewardResponse;
    }

    private Integer calculateRewards(double amount) {
        int reward = 0;
        if (amount <= 50) {
            return reward;
        } else if (amount > 50 && amount <= 100) {
            reward += (amount - 50);
        } else if (amount > 100) {
            reward += 50;
            reward += (amount - 100) * 2;
        }
        return reward;
    }

    private String getMonthName(int monthIndex) {
        return new DateFormatSymbols().getMonths()[monthIndex].toString();
    }
}
