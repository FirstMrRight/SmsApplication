package com.webpower.myapp.domain;

import com.webpower.myapp.domain.enumeration.Type;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * A Material.
 */
@Entity
@Table(name = "material")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = 0,max = 50)
    @Column(name = "material_name")
    private String materialName;

    @Column(name = "type")
    private Integer type;

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

    public Material id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialName() {
        return this.materialName;
    }

    public Material materialName(String materialName) {
        this.setMaterialName(materialName);
        return this;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Material type(Integer type) {
        this.setType(type);
        return this;
    }



    public String getUpdateBy() {
        return this.updateBy;
    }

    public Material updateBy(String updateBy) {
        this.setUpdateBy(updateBy);
        return this;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateName() {
        return this.updateName;
    }

    public Material updateName(String updateName) {
        this.setUpdateName(updateName);
        return this;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public Material createBy(String createBy) {
        this.setCreateBy(createBy);
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return this.createName;
    }

    public Material createName(String createName) {
        this.setCreateName(createName);
        return this;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
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

    public Material updateDate(Long updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }



    public Material createDate(Long createDate) {
        this.setCreateDate(createDate);
        return this;
    }


    public Material sequence(Integer sequence) {
        this.setSequence(sequence);
        return this;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getDelFlag() {
        return this.delFlag;
    }

    public Material delFlag(Integer delFlag) {
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
        if (!(o instanceof Material)) {
            return false;
        }
        return id != null && id.equals(((Material) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Material{" +
            "id=" + getId() +
            ", materialName='" + getMaterialName() + "'" +
            ", type='" + getType() + "'" +
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
