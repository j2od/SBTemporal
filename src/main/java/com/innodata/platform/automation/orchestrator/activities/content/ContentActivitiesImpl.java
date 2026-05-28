package com.innodata.platform.automation.orchestrator.activities.content;

import org.springframework.stereotype.Component;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("contentActivitiesImpl")
public class ContentActivitiesImpl implements ContentActivities {

    @Override
    public String extractMetadata(String filepath) {

        File file = new File(filepath);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("name", file.getAbsoluteFile().getName());
        metadata.put("size", file.getAbsoluteFile().length());
        System.out.println(metadata);

        try {
            return new ObjectMapper().writeValueAsString(metadata);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize metadata", e);
        }
    }
}