package com.webpower.myapp.repository;

import com.webpower.myapp.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Material entity.
 * @author Lt'x
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialRepository extends JpaRepository<Material, Long>, JpaSpecificationExecutor<Material> {


    /**
     * 通过彩信素材名称查询
     *
     * @param materialName 彩信素材名称
     * @return 彩信素材
     */
    Material findByMaterialName(String materialName);


    /**
     * 更新彩信素材
     *
     * @param material 彩信素材
     */
    @Modifying
    @Query(value = "update Material m " +
        "set " +
        "m.materialName = :#{#material.materialName}, " +
        "m.updateBy = :#{#material.updateBy} , " +
        "m.updateName = :#{#material.updateName}, " +
        "m.updateDate = :#{#material.updateDate} " +
        "where m.id = :#{#material.id} and m.delFlag = 0")
    void updateMaterialInfo(@Param("material") Material material);


    /**
     * @param materialName 彩信素材id
     * @param id           彩信素材名称
     * @return 统计彩信素材名称为materialName的个数
     */
    int countByMaterialNameAndIdIsNot(String materialName, Long id);

    /**
     * 逻辑删除彩信素材
     *
     * @param id 彩信素材id
     */
    @Modifying
    @Query(value = "update Material m set m.delFlag = 1 where m.id = :#{#id}")
    void deleteMaterialById(@Param("id") Long id);

}
