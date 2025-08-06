package com.ssafy.fullerting.config.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.ssafy.fullerting.bidLog.model.entity.BidLog;

@Component
public class BidLogModelListener extends AbstractMongoEventListener<BidLog> {

    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public BidLogModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<BidLog> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(sequenceGenerator.generateSequence(BidLog.SEQUENCE_NAME));
        }
    }
}
