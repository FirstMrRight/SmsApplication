package com.webpower.myapp.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A SplitScreen.
 */
@Entity
@Table(name = "split_screen")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SplitScreen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "material_id")
    private Long materialId;


    @Column(name = "content")
    private String content;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Max(80)
    @Column(name = "picture_size")
    private BigDecimal pictureSize;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_name")
    private String updateName;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_name")
    private String createName;

    @Column(name = "update_date")
    private Long updateDate;

    @Column(name = "create_date")
    private Long createDate;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "del_flag")
    private Integer delFlag;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SplitScreen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public SplitScreen materialId(Long materialId) {
        this.setMaterialId(materialId);
        return this;
    }


    public String getContent() {
        return this.content;
    }

    public SplitScreen content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictureUrl() {
        return this.pictureUrl;
    }

    public SplitScreen pictureUrl(String pictureUrl) {
        this.setPictureUrl(pictureUrl);
        return this;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }



    public SplitScreen pictureSize(BigDecimal pictureSize) {
        this.setPictureSize(pictureSize);
        return this;
    }

    public BigDecimal getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(BigDecimal pictureSize) {
        this.pictureSize = pictureSize;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public SplitScreen updateBy(String updateBy) {
        this.setUpdateBy(updateBy);
        return this;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateName() {
        return this.updateName;
    }

    public SplitScreen updateName(String updateName) {
        this.setUpdateName(updateName);
        return this;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public SplitScreen createBy(String createBy) {
        this.setCreateBy(createBy);
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return this.createName;
    }

    public SplitScreen createName(String createName) {
        this.setCreateName(createName);
        return this;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }


    public SplitScreen updateDate(Long updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public SplitScreen createDate(Long createDate) {
        this.setCreateDate(createDate);
        return this;
    }



    public Integer getSequence() {
        return this.sequence;
    }

    public SplitScreen sequence(Integer sequence) {
        this.setSequence(sequence);
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getDelFlag() {
        return this.delFlag;
    }

    public SplitScreen delFlag(Integer delFlag) {
        this.setDelFlag(delFlag);
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SplitScreen)) {
            return false;
        }
        return id != null && id.equals(((SplitScreen) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SplitScreen{" +
            "id=" + getId() +
            ", materialId='" + getMaterialId() + "'" +
            ", content='" + getContent() + "'" +
            ", pictureUrl='" + getPictureUrl() + "'" +
            ", pictureSize=" + getPictureSize() +
            ", updateBy='" + getUpdateBy() + "'" +
            ", updateName='" + getUpdateName() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createName='" + getCreateName() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", sequence=" + getSequence() +
            ", delFlag=" + getDelFlag() +
            "}";
    }
}
