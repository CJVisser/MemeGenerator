package com.memegenerator.backend.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

   /** 
    * @return ModelMapper
    */
   @Bean
   public ModelMapper modelMapper() {
      ModelMapper modelMapper = new ModelMapper();
      modelMapper.getConfiguration().setSkipNullEnabled(true);
      modelMapper.getConfiguration().setFieldMatchingEnabled(true);

      modelMapper.getConfiguration().setSourceNamingConvention(NamingConventions.NONE);
      modelMapper.getConfiguration().setDestinationNamingConvention(NamingConventions.NONE);

      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

      return modelMapper;
   }
}