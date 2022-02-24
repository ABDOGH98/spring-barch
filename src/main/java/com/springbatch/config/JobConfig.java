package com.springbatch.config;

import com.springbatch.dao.BankTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class JobConfig {

    @Autowired private  JobBuilderFactory jobBuilderFactory;
    @Autowired private  StepBuilderFactory stepBuilderFactory;
    @Autowired private  ItemReader<BankTransaction> bankTransactionItemReader;
    @Autowired private  ItemWriter<BankTransaction> bankTransactionItemWriter;
    @Autowired private  ItemProcessor<BankTransaction, BankTransaction> bankTransactionItemProcessor;

    @Bean
    public Job backJob(){
        Step step1 = stepBuilderFactory.get("step-load-data")
                .<BankTransaction,BankTransaction>chunk(100)
                .reader(bankTransactionItemReader)
                .processor(bankTransactionItemProcessor)
                .writer(bankTransactionItemWriter)
                .build();
        return jobBuilderFactory.get("banck-data-loader-job").start(step1).build();
    }
}
