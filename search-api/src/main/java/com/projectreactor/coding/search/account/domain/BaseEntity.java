package com.projectreactor.coding.search.account.domain;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
public abstract class BaseEntity implements Persistable<String> {

  @Id
  private String id;

  @CreatedBy
  private String createdBy;

  @CreatedDate
  private Date createdDate;

  @LastModifiedBy
  private String updatedBy;

  @LastModifiedDate
  private Date updatedDate;

  @Version
  private Long version;

  private Boolean delete = Boolean.FALSE;

}
