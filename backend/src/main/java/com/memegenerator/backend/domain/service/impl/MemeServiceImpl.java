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
    
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Achievement achievement = determineAchievement(user);

        // If achievement is not empty, add it to the user
        if (achievement.getTitle() != null) {
            user.achievements.add(achievement);
            userRepository.save(user);
        }

        RequestResponse response = new RequestResponse("");
        response.success = false;

        if(user.isBanned()){
            response.errors.add("The user is banned and not allowed to create memes.");
            response.message = "You are banned and not allowed to create memes.";
            return response;
        }

        if (!userAllowedToCreate(userId)) {
            response.errors.add("User is not allowed to create the meme.");
            response.message = "You are not allowed to create the meme.";
            return response;
        }

        Meme meme = modelMapper.map(memeDto, Meme.class);
        meme.setCategory(memeDto.category);

        meme.setUser(user);

        for (Tag elementTag : memeDto.tags) {
            // Check if tag exists in the database
            Tag tag = tagRepository.findById(elementTag.getId()).orElseThrow(() -> new NoSuchElementException("Tag not found"));
            meme.getTags().add(tag);
        }

        if (meme.getUser().getPoints() >= 1000) {

            BufferedImage bufferedImage = createImageFromBytes(meme.getImageblob());
            BufferedImage bufferedImageWithWatermark = addTextWatermark("PREMIUM", bufferedImage);
            byte[] watermarkedMeme = createBytesFromImage(bufferedImageWithWatermark);
            meme.setImageblob(watermarkedMeme);
        }

        memeRepository.save(meme);

        response.message = "Successfully created the meme!";
        response.success = true;

        return response;
    }

    public boolean userAllowedToCreate(Long userId) throws NoSuchElementException {

        boolean result = false;

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        var currentDate = LocalDate.now();
        Integer memesAddedCount = memeRepository.countAddedRecordsTodayByUser(currentDate.toString(), userId);

        Integer userAmountToPost = 0;
        if (user.getPoints() < 100) {
            userAmountToPost = 1;
        } else if (user.getPoints() < 500) {
            userAmountToPost = 5;
        } else if (user.getPoints() < 1000) {
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

        return memeRepository.findById(memeId).orElseThrow(() -> new NoSuchElementException(MEME_NOT_FOUND));
    }

    /**
     * @param meme
     * @return Meme
     * @throws NoSuchElementException
     */
    public Meme updateMeme(Meme meme) throws NoSuchElementException {

        if (!memeRepository.findById(meme.getId()).isPresent()) {
            throw new NoSuchElementException(MEME_NOT_FOUND);
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

    private Achievement determineAchievement(User user) {

        int amountOfMemes = user.getMemes().size();
        Achievement achievement = new Achievement(null);

        switch (amountOfMemes) {
            case 0:
                achievement = achievementRepository.findByTitle("First Meme");
                break;

            case 4:
                achievement = achievementRepository.findByTitle("5 Memes");
                break;

            case 9:
                achievement = achievementRepository.findByTitle("10 Memes");
                break;

            case 24:
                achievement = achievementRepository.findByTitle("25 Memes");
                break;

            default:
                break;
        }

        return achievement;
    }

    public Meme flagMeme(long id) throws NoSuchElementException {

        Meme meme = getMemeById(id);

        meme.setFlag_points(meme.getFlag_points() + 1);

        meme.setMemestatus("reported");

        memeRepository.save(meme);

        return meme;
    }

    public void cancelMeme(Long memeId) {
        var meme = memeRepository.findById(memeId).orElseThrow(() -> new NoSuchElementException(MEME_NOT_FOUND));
        
        if (meme.getMemestatus().equals("") || meme.getMemestatus() == null || meme.getMemestatus().equals("reported")) {
            meme.setMemestatus("cancelled");
        }
        else if (meme.getMemestatus().equals("cancelled")) {
            meme.setMemestatus("");
        }
        else {
            meme.setMemestatus(null);
        }
        
        memeRepository.save(meme);
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
