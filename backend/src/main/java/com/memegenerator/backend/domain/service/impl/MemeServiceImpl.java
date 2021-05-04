package com.memegenerator.backend.domain.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;


import com.memegenerator.backend.data.entity.Achievement;
import com.memegenerator.backend.data.entity.Meme;
import com.memegenerator.backend.data.entity.User;
import com.memegenerator.backend.data.repository.AchievementRepository;
import javax.imageio.ImageIO;
import com.memegenerator.backend.data.entity.Tag;
import com.memegenerator.backend.data.repository.MemeRepository;
import com.memegenerator.backend.data.repository.UserRepository;
import com.memegenerator.backend.data.repository.TagRepository;
import com.memegenerator.backend.domain.service.MemeService;
import com.memegenerator.backend.web.dto.MemeDto;
import com.memegenerator.backend.web.dto.RequestResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemeServiceImpl implements MemeService {

    private static final String MEME_NOT_FOUND = "Meme not found";

    private final MemeRepository memeRepository;
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    /**
     * @param meme
     * @param userId
     * @return Meme
     * @throws NoSuchElementException
     */
    public RequestResponse createMeme(MemeDto memeDto, Long userId) throws NoSuchElementException {


        //meme.user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Achievement achievement = determineAchievement(user);

        // If achievement is not empty, add it to the user
        if(achievement.title != null){
            user.achievements.add(achievement);
            userRepository.save(user);
        }

        RequestResponse response = new RequestResponse();
        response.Success = false;

        if(!userAllowedToCreate(userId)){
            response.Errors.add("User is not allowed to create the meme.");
            response.Message = "You are not allowed to create more memes today.";
            return response;
        }

        Meme meme = modelMapper.map(memeDto, Meme.class);
        meme.category = memeDto.category;

        meme.user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException(MEME_NOT_FOUND));

        for (Tag elementTag : memeDto.tags) {
            // Check if tag exists in the database 
            Tag tag = tagRepository.findById(elementTag.id).orElseThrow(() -> new NoSuchElementException("Tag not found"));
            meme.tags.add(tag);
        }

        if (meme.user.points >= 1000) {

            BufferedImage bufferedImage = createImageFromBytes(meme.imageblob);
            BufferedImage bufferedImageWithWatermark = addTextWatermark("CREATED BY A MEMEKING", bufferedImage);
            byte[] watermarkedMeme = createBytesFromImage(bufferedImageWithWatermark);
            meme.imageblob = watermarkedMeme;
        }

        memeRepository.save(meme);

        response.Message = "Successfully created the meme!";
        response.Success = true;

        return response;
    }

    public boolean userAllowedToCreate(Long userId) throws NoSuchElementException {

        boolean result = false;

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        var currentDate = LocalDate.now();
        Integer memesAddedCount = memeRepository.countAddedRecordsTodayByUser(currentDate.toString(), userId);

        Integer userAmountToPost = 0;
        if (user.points < 100) {
            userAmountToPost = 1;
        } else if (user.points < 500) {
            userAmountToPost = 5;
        } else if (user.points < 1000) {
            userAmountToPost = 10;
        } else {
            userAmountToPost = -1;
            result = true;
        }

        if (userAmountToPost != -1) {
            result = userAmountToPost > memesAddedCount;
        }

        return result;
    }

    /**
     * @param memeId
     * @return Meme
     * @throws NoSuchElementException
     */
    public Meme getMemeById(long memeId) throws NoSuchElementException {

        return memeRepository.findById(memeId).orElseThrow(() -> new NoSuchElementException("Meme not found"));
    }

    /**
     * @param meme
     * @return Meme
     * @throws NoSuchElementException
     */
    public Meme updateMeme(Meme meme) throws NoSuchElementException {

        if (!memeRepository.findById(meme.id).isPresent()) {
            throw new NoSuchElementException("Meme not found");
        }

        return memeRepository.save(meme);
    }

    /**
     * @return List<Meme>
     */
    public List<Meme> getMemes() {

        List<Meme> allMemes = memeRepository.findAll();

        allMemes.sort(Comparator.comparing(Meme::getCreatedat).reversed());

        return allMemes;
    }

    private Achievement determineAchievement(User user){

        int amountOfMemes = user.getMemes().size();
        Achievement achievement = new Achievement();

        switch(amountOfMemes){
            case 0:
                achievement = achievementRepository.findByTitle("First Meme");
                break;
            
            case 9:
                achievement = achievementRepository.findByTitle("10 Memes");
                break;
            
            case 24:
                achievement = achievementRepository.findByTitle("25 Memes");
                break;
        }

        return achievement;
    }

    public Meme flagMeme(long id) throws NoSuchElementException {

        Meme meme = getMemeById(id);

        meme.flag_points += 1;

        memeRepository.save(meme);

        return meme;
    }

        /** 
     * @param imageData
     * @return BufferedImage
     */
    private BufferedImage createImageFromBytes(byte[] imageData) {

        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);

        try {

            return ImageIO.read(bais);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    /** 
     * @param text
     * @param sourceImage
     * @return BufferedImage
     */
    private BufferedImage addTextWatermark(String text, BufferedImage sourceImage) {

        Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
        g2d.setComposite(alphaChannel);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 64));
        FontMetrics fontMetrics = g2d.getFontMetrics();
        java.awt.geom.Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

        int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
        int centerY = sourceImage.getHeight() / 2;

        g2d.drawString(text, centerX, centerY);

        return sourceImage;
    }
    
    /** 
     * @param image
     * @return byte[]
     */
    private byte[] createBytesFromImage(BufferedImage image) {

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(image, "png", baos);

            byte[] imageBytes = baos.toByteArray();
            baos.close();
            return imageBytes;

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
