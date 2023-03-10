package com.practice.accsystem.service;

import com.practice.accsystem.entity.ExcelRecord;
import org.springframework.core.io.Resource;

import java.util.List;


public interface ExcelService {
    byte[] writeEntitiesInTemplateLineByLine(List<ExcelRecord> entities, Resource template);
}
