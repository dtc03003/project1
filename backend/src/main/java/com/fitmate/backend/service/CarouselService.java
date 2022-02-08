package com.fitmate.backend.service;

import com.fitmate.backend.dto.CarouselDto;
import com.fitmate.backend.entity.Carousel;
import com.fitmate.backend.repository.CarouselRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarouselService {
    private final CarouselRepository carouselRepository;

    @Transactional
    public String saveCarouselImg(String imgSrc){
        Carousel carousel = CarouselDto.toEntity(imgSrc);
        carouselRepository.save(carousel);
        return "save success";
    }

    @Transactional
    public String deleteCarouselImg(Long carouselImg){
        Carousel carousel = carouselRepository.findById(carouselImg).orElseThrow();
        carouselRepository.delete(carousel);
        return "delete success";
    }

    public List<Carousel> getAllCarousel(){
        return carouselRepository.findAll();
    }
}
