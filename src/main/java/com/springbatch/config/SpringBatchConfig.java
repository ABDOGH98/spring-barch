package com.springbatch.config;

import com.springbatch.dao.BanckTransactionRepository;
import com.springbatch.dao.BankTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {


    @Bean
    public FlatFileItemReader<BankTransaction> bankTransactionItemReader(@Value("${inputFile}") Resource inputFile){
        System.out.println("test");
        FlatFileItemReader<BankTransaction> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setName("CSV-READER");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setResource(inputFile);
        flatFileItemReader.setLineMapper(lineMapper());

        return flatFileItemReader;

    }
    @Bean
    public LineMapper<BankTransaction> lineMapper(){
        DefaultLineMapper<BankTransaction> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineTokenizer.setNames("id","accountId","strTransactionDate","transactionType","amount");

        BeanWrapperFieldSetMapper<BankTransaction> fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(BankTransaction.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper ;
    }

    @Bean
    public ItemWriter<BankTransaction> bankTransactionItemWriter() {
        return new ItemWriter<BankTransaction>() {
            @Autowired private BanckTransactionRepository banckTransactionRepository ;
            @Override
            public void write(List<? extends BankTransaction> list) throws Exception {
                banckTransactionRepository.saveAll(list);
            }
        };
    }

}
