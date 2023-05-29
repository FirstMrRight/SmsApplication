package com.webpower.myapp.repository;

import com.webpower.myapp.domain.SplitScreen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the SplitScreen entity.
 * @author Lt'x
 */
@SuppressWarnings("unused")
@Repository
public interface SplitScreenRepository extends JpaRepository<SplitScreen, Long>, JpaSpecificationExecutor<SplitScreenRepository> {


    /**
     * 插入分屏信息
     * @param splitScreen 分屏信息
     */
    @Modifying
    @Query(value = "insert into split_screen (picture_url," +
        "content," +
        "create_by," +
        "update_by," +
        "create_name," +
        "update_name," +
        "create_date," +
        "update_date," +
        "`sequence`," +
        "material_id," +
        "picture_size," +
        "del_flag) values (" +
        ":#{#splitScreen.pictureUrl}," +
        " :#{#splitScreen.content}," +
        ":#{#splitScreen.createBy}," +
        ":#{#splitScreen.updateBy}," +
        ":#{#splitScreen.createName}," +
        ":#{#splitScreen.updateName}," +
        ":#{#splitScreen.createDate}," +
        ":#{#splitScreen.updateDate}," +
        "(SELECT COALESCE(MAX(c.sequence)+1, 1) FROM split_screen c " +
        "WHERE c.material_id = :#{#splitScreen.materialId}),:#{#splitScreen.materialId},:#{#splitScreen.pictureSize},:#{#splitScreen.delFlag})", nativeQuery = true)
    void insertSpiltScreen(@Param("splitScreen") SplitScreen splitScreen);


    /**
     * 根据素材id获取分屏信息
     * @param materialId 素材id
     * @return 分屏信息列表
     */
    List<SplitScreen> findByMaterialId(Long materialId);


    /**
     * 更新分屏排序信息
     * @param splitScreen 分屏
     */
    @Modifying
    @Query(value = "update SplitScreen s set s.sequence = :#{#splitScreen.sequence} where s.id = :#{#splitScreen.id}")
    void dragScreen(@Param("splitScreen") SplitScreen splitScreen);

    /**
     * 更新分屏信息
     * @param splitScreen 分屏信息
     */
    @Modifying
    @Query(value = "update SplitScreen s set " +
        "s.content = :#{#splitScreen.content} ," +
        "s.pictureUrl = :#{#splitScreen.pictureUrl}," +
        "s.pictureSize = :#{#splitScreen.pictureSize}," +
        "s.updateBy = :#{#splitScreen.updateBy}," +
        "s.updateName = :#{#splitScreen.updateName}," +
        "s.updateDate = :#{#splitScreen.updateDate}" +
        " where s.id = :#{#splitScreen.id}")
    void updateSplitScreen(@Param("splitScreen") SplitScreen splitScreen);


    /**
     * 通过materialId删除分屏
     * @param id materialId 彩信素材id
     */
    @Modifying
    @Query(value = "update SplitScreen set delFlag =1  where materialId =:#{#id}")
    void deleteSplitScreenByMaterialId(@Param("id") Long id);

    /**
     * 通过id删除分屏
     * @param id 分屏id
     */
    @Modifying
    @Query(value = "update SplitScreen set delFlag =1  where id =:#{#id}")
    void deleteSplitScreenById(@Param("id") Long id);

    /**
     * 统计彩信素材id下有几个分屏信息
     * @param materialId 彩信素材id
     * @return 分屏个数
     */
    @Query(value = "select count(s) from SplitScreen s WHERE s.materialId = :materialId and s.delFlag = 0")
    Integer countByMaterialId(@Param("materialId") Long materialId);
}
