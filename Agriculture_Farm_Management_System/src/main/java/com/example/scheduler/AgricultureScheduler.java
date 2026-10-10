package com.example.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.entity.Crop;
import com.example.repository.CropRepository;

@Component
public class AgricultureScheduler {

    private static final Logger logger =
            LoggerFactory.getLogger(AgricultureScheduler.class);

    @Autowired
    private CropRepository cropRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void checkCropsDueForHarvest() {

        LocalDate today = LocalDate.now();

        logger.info("Daily crop monitoring started for date: {}", today);

        List<Crop> crops = cropRepository
                .findByExpectedHarvestDateBeforeOrEqual(today);

        if (crops.isEmpty()) {

            logger.info("No crops are due for harvest today.");

        } else {

            logger.info(
                    "{} crop(s) are due for harvest.",
                    crops.size());

            for (Crop crop : crops) {

                logger.info(
                        "Crop ID: {}, Crop Name: {}, Expected Harvest Date: {}",
                        crop.getCropId(),
                        crop.getCropName(),
                        crop.getExpectedHarvestDate());
            }
        }

        logger.info("Daily crop monitoring completed.");
    }
}