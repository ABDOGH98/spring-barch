package com.springbatch.config;

import com.springbatch.dao.BanckTransactionRepository;
import com.springbatch.dao.BankTransaction;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


public class BanckTransactionItemWriter implements ItemWriter<BankTransaction> {

    @Autowired private BanckTransactionRepository banckTransactionRepository ;

    @Override
    public void write(List<? extends BankTransaction> list) throws Exception {
        banckTransactionRepository.saveAll(list);
    }
}
