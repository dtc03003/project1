package com.fitmate.backend.service;

import com.fitmate.backend.dto.TagDto;
import com.fitmate.backend.entity.Style;
import com.fitmate.backend.entity.Tag;
import com.fitmate.backend.repository.StyleRepository;
import com.fitmate.backend.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final StyleRepository styleRepository;

    @Transactional
    public String tagging(TagDto tagDto){
        Style style = styleRepository.getById(tagDto.getStyleId());
        for(int i=0; i<tagDto.getTag().size(); i++){
            tagRepository.save(TagDto.toEntity(style, tagDto.getTag().get(i)));
        }
        return "tagging success";
    }

    @Transactional
    public String untagging(TagDto tagDto) {
        Style style = styleRepository.getById(tagDto.getStyleId());
        Tag tag = tagRepository.findByStyleAndTag(style, tagDto.getTag().get(0)).orElseThrow();
        tagRepository.delete(tag);
        return "untagging success";
    }

    public List<String> getTagByStyle(Long styleId){
        Style style = styleRepository.getById(styleId);
        List<Tag> tagList = tagRepository.findAllByStyle(style);
        List<String> resultTagList = new ArrayList<String>();
        for(int i=0; i<tagList.size(); i++){
            resultTagList.add(tagList.get(i).getTag());
        }
        return resultTagList;
    }
}
