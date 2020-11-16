package com.manoj.app.sampleproject.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractDocument implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    private ObjectId id;
	
	@CreatedDate
	private LocalDateTime createdOn;
	
	@LastModifiedDate
    private LocalDateTime updatedOn;
	
	public String getId() {
        return id.toString();
    }
}
