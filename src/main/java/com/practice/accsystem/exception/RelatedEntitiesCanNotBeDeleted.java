package com.practice.accsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RelatedEntitiesCanNotBeDeleted extends RuntimeException {
    public RelatedEntitiesCanNotBeDeleted(String deletingEntityName, String relatedEntityName) {
        super(String.format("Entity '%s' can not be deleted because related entity '%s' can not be deleted. " +
                "Try to delete related entity or change its owner", deletingEntityName, relatedEntityName));
    }
}
