package com.weilai.wiki.service;

import com.weilai.wiki.mapper.EbookSnapshotMapperCust;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EbookSnapshotService {
    @Resource
    private EbookSnapshotMapperCust ebookSnapshotMapperCust;

    public void genSnapshot() {
        ebookSnapshotMapperCust.genSnapshot();
    }
}