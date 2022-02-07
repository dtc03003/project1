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

    public List<Style> getStyleByTag(List<String> tagList){
        List<Style> styleList = styleRepository.findAll();
        List<Style> resultStyleList = new ArrayList<Style>();
        for(int i=0; i<styleList.size(); i++){
            List<Tag> tagByStyle = tagRepository.findAllByStyle(styleList.get(i));
            boolean checkTagList = true;
            for(int j=0; j<tagList.size(); j++){
                boolean checkTag = false;
                for(int k=0; k<tagByStyle.size(); k++){
                    if(tagByStyle.get(k).getTag().equals(tagList.get(j))){
                        checkTag = true;
                        break;
                    }
                }
                if(!checkTag) {
                    checkTagList = false;
                    break;
                }
            }
            if (checkTagList)
                resultStyleList.add(styleList.get(i));
        }
        return resultStyleList;
    }
}
