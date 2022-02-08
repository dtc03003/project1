package com.fitmate.backend.service;

import com.fitmate.backend.entity.Style;
import com.fitmate.backend.entity.Tag;
import com.fitmate.backend.repository.PortfolioRepository;
import com.fitmate.backend.repository.StyleRepository;
import com.fitmate.backend.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StyleBookService {
    private final TagRepository tagRepository;
    private final StyleRepository styleRepository;

    public List<Style> searchByTag(List<String> tagList){
        if(tagList.size()==0){
            return styleRepository.findAll(Sort.by("createdAt").descending());
        } else {
            List<Style> styleList = styleRepository.findAll(Sort.by("createdAt").descending());
            List<Style> resultStyleList = new ArrayList<Style>();
            for (int i = 0; i < styleList.size(); i++) {
                List<Tag> tagByStyle = tagRepository.findAllByStyle(styleList.get(i));
                boolean checkTagList = true;
                for (int j = 0; j < tagList.size(); j++) {
                    boolean checkTag = false;
                    for (int k = 0; k < tagByStyle.size(); k++) {
                        if (tagByStyle.get(k).getTag().equals(tagList.get(j))) {
                            checkTag = true;
                            break;
                        }
                    }
                    if (!checkTag) {
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
}
