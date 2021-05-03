package com.memegenerator.backend.config;

import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.data.entity.Tag;
import com.memegenerator.backend.data.entity.User;
import com.memegenerator.backend.web.dto.MemeDto;
import com.memegenerator.backend.web.dto.UserDto;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.MappingContext;
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

      modelMapper.createTypeMap(Meme.class, MemeDto.class);
      modelMapper.getTypeMap(Meme.class, MemeDto.class).setConverter(toMemeDto);

      modelMapper.createTypeMap(MemeDto.class, Meme.class);
      modelMapper.getTypeMap(MemeDto.class, Meme.class).setConverter(toMeme);

      modelMapper.createTypeMap(User.class, UserDto.class);
      modelMapper.getTypeMap(User.class, UserDto.class).setConverter(toUserDto);

      modelMapper.createTypeMap(UserDto.class, User.class);
      modelMapper.getTypeMap(UserDto.class, User.class).setConverter(toUser);

      return modelMapper;
   }

   Converter<Meme, MemeDto> toMemeDto = new Converter<Meme, MemeDto>() {
      public MemeDto convert(MappingContext<Meme, MemeDto> context) {
         ModelMapper modelmapper = new ModelMapper();

         Meme meme = context.getSource();

         MemeDto memeDto = new MemeDto();
         memeDto.id = meme.id;
         memeDto.title = meme.title;
         memeDto.categoryId = meme.category == null ? 0 : meme.category.id;
         memeDto.description = meme.description;
         memeDto.dislikes = meme.dislikes;
         memeDto.likes = meme.likes;
         memeDto.imageblob = meme.imageblob;
         memeDto.createdat = meme.createdat;
         memeDto.memestatus = meme.memestatus;

         memeDto.user = modelmapper.map(meme.user, UserDto.class);

         Object[] array = meme.tags.toArray();
         memeDto.tags = new Tag[array.length];

         System.arraycopy(array, 0, memeDto.tags, 0, array.length);

        return memeDto;
      }
    };

    Converter<MemeDto, Meme> toMeme = new Converter<MemeDto, Meme>() {
      public Meme convert(MappingContext<MemeDto, Meme> context) {
         MemeDto memeDto = context.getSource();

         Meme meme = context.getDestination();

         if(memeDto == null) return meme;

         if(meme == null){ 
            meme = new Meme();

            meme.description = memeDto.description;
            meme.imageblob = memeDto.imageblob;
            meme.title = memeDto.title;
         }

         meme.likes = memeDto.likes;
         meme.dislikes = memeDto.dislikes;

        return meme;
      }
    };

    Converter<User, UserDto> toUserDto = new Converter<User, UserDto>() {
      public UserDto convert(MappingContext<User, UserDto> context) {
         User user = context.getSource();

         UserDto userDto = new UserDto();
         userDto.id = user.id;
         userDto.username = user.username;
         userDto.email = user.email;
         userDto.activated = user.activated;
         userDto.banned = user.banned;
         userDto.createdat = user.createdat;
         userDto.points = user.points;

        return userDto;
      }
    };

    Converter<UserDto, User> toUser = new Converter<UserDto, User>() {
      public User convert(MappingContext<UserDto, User> context) {
         UserDto userDto = context.getSource();

         User user = context.getDestination();

         if(userDto == null) return user;

         if(user == null){ 
            user = new User();

            user.id = userDto.id;
            user.username = userDto.username;
            user.email = userDto.email;
            user.points = userDto.points;
         }

        return user;
      }
    };
}