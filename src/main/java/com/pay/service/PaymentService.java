package com.pay.service;

import com.pay.domain.PaymentDomain;
import com.pay.domain.UserDomain;
import com.pay.repository.PaymentRepository;
import com.pay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public void addIncome(Long user,
                          String content,
                          Long price) {
        UserDomain userDomain = userRepository.getOne(user);
        userDomain.setCapital(userDomain.getCapital() + price);
        userDomain.setIncome(userDomain.getIncome() + price);

        paymentRepository.save(PaymentDomain.builder()
                .kind("수입")
                .user(userRepository.save(userDomain))
                .content(content)
                .price(price)
                .build()
        );
    }

    @Transactional
    public void addOutcome(Long user,
                           String content,
                           Long price,
                           String method) {
        UserDomain userDomain = userRepository.getOne(user);
        userDomain.setCapital(userDomain.getCapital() - price);
        userDomain.setOutcome(userDomain.getOutcome() - price);

        paymentRepository.save(PaymentDomain.builder()
                .kind("지출")
                .user(userRepository.save(userDomain))
                .content(content)
                .price(-price)
                .method(method)
                .build()
        );
    }


    @Transactional
    public List gets(Long user) {
        return paymentRepository.findAllByUser_Index(user);
    }

}
